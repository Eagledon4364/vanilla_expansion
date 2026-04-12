package com.chris.vanilla_expansion.entity.server;

import com.chris.vanilla_expansion.screen.DragonInventoryMenu;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerEntity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.*;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gamerules.GameRules;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.Nullable;

import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;
import java.util.Optional;

public class DragonAnimal extends TamableAnimal implements HasCustomInventoryScreen, OwnableEntity, ContainerListener {
    protected SimpleContainer inventory;
    private int temper;
    private static final EntityDataAccessor<@NotNull Boolean> SADDLED = SynchedEntityData.defineId(DragonAnimal.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<@NotNull Boolean> FLYING = SynchedEntityData.defineId(DragonAnimal.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<@NotNull Boolean> SLEEPING = SynchedEntityData.defineId(DragonAnimal.class, EntityDataSerializers.BOOLEAN);
    private static final boolean DEFAULT_ORDERED_TO_SIT = false;
    protected static final EntityDataAccessor<@NotNull Byte> DATA_FLAGS_ID = SynchedEntityData.defineId(DragonAnimal.class, EntityDataSerializers.BYTE);
    protected static final EntityDataAccessor<@NotNull Optional<EntityReference<@NotNull LivingEntity>>> DATA_OWNERUUID_ID = SynchedEntityData.defineId(
            DragonAnimal.class, EntityDataSerializers.OPTIONAL_LIVING_ENTITY_REFERENCE
    );
    private boolean orderedToSit = false;

    protected DragonAnimal(EntityType<? extends @NotNull DragonAnimal> type, Level level) {
        super(type, level);
        this.initInventory();
    }

    @Override
    public boolean isFood(ItemStack itemStack) {
        return itemStack.is(ItemTags.FISHES);
    }

    @Override
    public @Nullable AgeableMob getBreedOffspring(@NotNull ServerLevel level, @NotNull AgeableMob partner) {
        return null;
    }

    @Override
    public @NotNull InteractionResult mobInteract(Player player, @NotNull InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);

        if (this.isFood(itemStack)) {
            float maxHealth = this.getMaxHealth();
            float currentHealth = this.getHealth();

            if (!this.isTame()) {
                this.usePlayerItem(player, hand, itemStack);
                this.modifyTemper(5);
                this.spawnTamingParticles(true);
                return InteractionResult.SUCCESS;
            }

            if (this.isTame() && currentHealth < maxHealth) {
                this.usePlayerItem(player, hand, itemStack);
                this.heal(2.0F);
                this.spawnTamingParticles(true);
                return InteractionResult.SUCCESS;
            }
        }

        if (player.isSecondaryUseActive() && this.isTame()) {
            this.openCustomInventoryScreen(player);
            return InteractionResult.SUCCESS;
        }

        if (!this.isVehicle() && !player.isSecondaryUseActive()) {
            if (!this.level().isClientSide()) {
                player.startRiding(this);
            }
            return InteractionResult.SUCCESS;
        }

        return super.mobInteract(player, hand);
    }

    @Override
    public @Nullable LivingEntity getControllingPassenger() {
        return this.getFirstPassenger() instanceof LivingEntity livingEntity ? livingEntity : null;
    }

    @Override
    public void travel(@NotNull Vec3 travelVector) {
        if (!this.isAlive()) return;
        LivingEntity driver = this.getControllingPassenger();
        if (this.isVehicle() && driver != null && this.isSaddled()) {
            this.setYRot(driver.getYRot());
            this.yRotO = this.getYRot();
            this.setXRot(driver.getXRot());
            this.xRotO = this.getXRot();
            this.setRot(this.getYRot(), this.getXRot());
            this.yBodyRot = this.getYRot();
            this.yHeadRot = this.yBodyRot;
            float flyingSpeed = 0.6F;
            float walkingSpeed = 0.3F;
            this.setSpeed(this.isFlying() ? flyingSpeed : walkingSpeed);

            if (this.isFlying()) {
                Vec3 lookVec = driver.getLookAngle();
                float forward = driver.zza;
                float side = driver.xxa;

                double xMove = 0, yMove = 0, zMove = 0;
                if (forward != 0 || side != 0) {
                    Vec3 moveVec = lookVec.scale(forward).add(lookVec.yRot((float) Math.PI / 2).scale(side));
                    xMove = moveVec.x * flyingSpeed;
                    zMove = moveVec.z * flyingSpeed;
                    yMove = lookVec.y * forward * flyingSpeed;
                }
                if (driver.isJumping()) yMove += 0.5;

                if (xMove == 0 && yMove == 0 && zMove == 0) {
                    this.setDeltaMovement(Vec3.ZERO);
                    super.travel(Vec3.ZERO);
                } else {
                    this.setDeltaMovement(xMove, yMove, zMove);
                    this.move(MoverType.SELF, this.getDeltaMovement());
                    super.travel(new Vec3(xMove, yMove, zMove));
                }

                if (this.onGround() && !driver.isJumping()) {
                    this.setFlying(false);
                }
            } else {
                if (driver.isJumping()) {
                    this.setFlying(true);
                    this.setDeltaMovement(this.getDeltaMovement().add(0, 0.5, 0));
                    super.travel(travelVector);
                } else {
                    super.travel(new Vec3(driver.xxa, travelVector.y, driver.zza));
                }
            }
        } else {
            if (this.isFlying()) {
                Vec3 velocity = this.getDeltaMovement();
                double lift = 0.08;
                double friction = 0.91;
                double newY = velocity.y;

                if (newY < 0) {
                    newY = (newY * 0.5) + lift;
                }

                this.setDeltaMovement(velocity.x * friction, newY, velocity.z * friction);
                this.move(MoverType.SELF, this.getDeltaMovement());

                if (this.onGround()) {
                    this.setFlying(false);
                }

                super.travel(this.getDeltaMovement());
            } else {
                this.setSpeed(0.25F);
                super.travel(travelVector);
            }
        }
    }

    @Override
    public void aiStep() {
        super.aiStep();

        if (this.isFlying()) {
            this.setIgnoreFallDamageFromCurrentImpulse(true, this.position());
            this.fallDistance = 0;
        }
        if (this.isFlying() && this.onGround()) {
            this.setFlying(false);
        }

        if (!this.level().isClientSide() && !this.isTame() && this.isVehicle()) {
            if (this.getRandom().nextInt(50) == 0) {
                // Check if Temper is high enough to succeed
                if (this.getTemper() > this.getRandom().nextInt(this.getMaxTemper())) {
                    this.tame((Player) this.getFirstPassenger());
                    this.level().broadcastEntityEvent(this, (byte) 7);
                } else {
                    this.modifyTemper(1);
                    this.level().broadcastEntityEvent(this, (byte) 6);
                    this.ejectPassengers();
                }
            }
        }
    }


    @Override
    public void handleEntityEvent(byte id) {
        if (id == EntityEvent.TAMING_SUCCEEDED) {
            this.spawnTamingParticles(true);
        } else if (id == EntityEvent.TAMING_FAILED) {
            this.spawnTamingParticles(false);
        } else {
            super.handleEntityEvent(id);
        }
    }

    protected void spawnTamingParticles(boolean success) {
        var particle = success ? net.minecraft.core.particles.ParticleTypes.HEART : net.minecraft.core.particles.ParticleTypes.SMOKE;
        for (int i = 0; i < 7; ++i) {
            double d = this.random.nextGaussian() * 0.02;
            double e = this.random.nextGaussian() * 0.02;
            double f = this.random.nextGaussian() * 0.02;
            this.level().addParticle(particle, this.getRandomX(1.0), this.getRandomY() + 0.5, this.getRandomZ(1.0), d, e, f);
        }
    }

    @Override
    public @Nullable EntityReference<@NotNull LivingEntity> getOwnerReference() {
        return null;
    }

    protected void initInventory() {
        this.inventory = new SimpleContainer(2) {
            @Override
            public void setChanged() {
                super.setChanged();
                DragonAnimal.this.containerChanged(this);
            }
        };

        this.updateContainerEquipment();
    }

    public void containerChanged(Container container) {
        this.updateContainerEquipment();

        this.needsSync = true;
    }

    protected void updateContainerEquipment() {
        if (!this.level().isClientSide()) {
            ItemStack saddleStack = this.inventory.getItem(0);
            this.setSaddled(!saddleStack.isEmpty() && saddleStack.is(Items.SADDLE));
        }
    }

    @Override
    public void openCustomInventoryScreen(@NotNull Player player) {
        if (!this.level().isClientSide() && (!this.isVehicle() || this.hasPassenger(player))) {
            player.openMenu(new SimpleMenuProvider((id, playerInv, p) ->
                    new DragonInventoryMenu(id, playerInv, this.inventory, this), this.getDisplayName()));
        }
    }

    @Override
    public void componentAdded(ContainerEvent e) {

    }

    @Override
    public void componentRemoved(ContainerEvent e) {

    }

    @Override
    public void addAdditionalSaveData(@NotNull ValueOutput output) {
        super.addAdditionalSaveData(output);
        output.putBoolean("IsFlying", this.isFlying());
        EntityReference<@NotNull LivingEntity> owner = this.getOwnerReference();
        EntityReference.store(owner, output, "Owner");
        output.putBoolean("Sitting", this.orderedToSit);
        output.putInt("Temper", this.temper);
        if (this.inventory != null) {
            var itemsList = output.list("dragon_inventory", ItemStack.CODEC);
            for (int i = 0; i < this.inventory.getContainerSize(); i++) {
                ItemStack stack = this.inventory.getItem(i);
                if (!stack.isEmpty()) {
                    itemsList.add(stack);
                }
            }
        }
    }

    @Override
    public void readAdditionalSaveData(@NotNull ValueInput input) {
        super.readAdditionalSaveData(input);
        boolean flyingState = input.getBooleanOr("IsFlying", false);
        this.setFlying(flyingState);
        this.temper = input.getIntOr("Temper", 0);

        input.list("dragon_inventory", ItemStack.CODEC).ifPresent(items -> {
            this.inventory.clearContent();
            int slot = 0;
            for (ItemStack stack : items) {
                if (slot < this.inventory.getContainerSize()) {
                    this.inventory.setItem(slot, stack);
                    slot++;
                }
            }
        });

        this.updateContainerSlots();
        if (this.isFlying()) {
            this.setNoGravity(true);
            this.needsSync = true;
        }
        EntityReference<@NotNull LivingEntity> owner = EntityReference.readWithOldOwnerConversion(input, "Owner", this.level());
        if (owner != null) {
            try {
                this.entityData.set(DATA_OWNERUUID_ID, Optional.of(owner));
                this.setTame(true, false);
            } catch (Throwable var4) {
                this.setTame(false, true);
            }
        } else {
            this.entityData.set(DATA_OWNERUUID_ID, Optional.empty());
            this.setTame(false, true);
        }

        this.orderedToSit = input.getBooleanOr("Sitting", false);
        this.setInSittingPose(this.orderedToSit);
    }

    public void updateContainerSlots() {
        ItemStack saddleStack = this.inventory.getItem(0);
        boolean hasSaddle = !saddleStack.isEmpty() && saddleStack.is(Items.SADDLE);

        this.setSaddled(hasSaddle);

        ItemStack armorStack = this.inventory.getItem(1);


        if (!this.level().isClientSide()) {
            this.needsSync = true;
        }
    }


    public boolean isTame() {
        return (this.entityData.get(DATA_FLAGS_ID) & 4) != 0;
    }

    public void setTame(final boolean isTame, final boolean includeSideEffects) {
        byte current = this.entityData.get(DATA_FLAGS_ID);
        if (isTame) {
            this.entityData.set(DATA_FLAGS_ID, (byte) (current | 4));
        } else {
            this.entityData.set(DATA_FLAGS_ID, (byte) (current & -5));
        }

        if (includeSideEffects) {
            this.applyTamingSideEffects();
        }
    }


    private void updateArmorMetadata(ItemStack stack) {
        //    if (stack.isEmpty()) {
        //    } else {
        //    }
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.@NotNull Builder builder) {
        super.defineSynchedData(builder);
        builder.define(SADDLED, false);
        builder.define(FLYING, false);
        builder.define(SLEEPING, false);
        builder.define(DATA_FLAGS_ID, (byte) 0);
        builder.define(DATA_OWNERUUID_ID, Optional.empty());
    }

    //sleeping
    public void setSleeping(boolean sleeping) {
        this.entityData.set(SLEEPING, sleeping);
        // When sleeping, the dragon shouldn't be standing or sitting
        if (sleeping) {
            this.setPose(Pose.SLEEPING);
        } else {
            this.setPose(Pose.STANDING);
        }
    }

    public boolean isInSittingPose() {
        return (this.entityData.get(DATA_FLAGS_ID) & 1) != 0;
    }

    public void setInSittingPose(final boolean value) {
        byte current = this.entityData.get(DATA_FLAGS_ID);
        if (value) {
            this.entityData.set(DATA_FLAGS_ID, (byte) (current | 1));
        } else {
            this.entityData.set(DATA_FLAGS_ID, (byte) (current & -2));
        }
    }

    public boolean isSleeping() {
        return this.entityData.get(SLEEPING);
    }

    //saddle
    public void setSaddled(boolean saddled) {
        this.entityData.set(SADDLED, saddled);
    }

    public boolean isSaddled() {
        return this.entityData.get(SADDLED);
    }

    //flying
    public void setFlying(boolean flying) {
        this.entityData.set(FLYING, flying);
        // This is the "Parking Brake" for the physics engine
        this.setNoGravity(flying);
        this.needsSync = true;
    }

    public boolean isFlying() {
        return this.entityData.get(FLYING);
    }

    @Override
    public void jumpFromGround() {
        if (this.isSaddled()) { // Only lift off if saddled
            this.setFlying(true);
            Vec3 delta = this.getDeltaMovement();
            // Lift the dragon up instead of a single jump burst
            this.setDeltaMovement(delta.x, 0.5, delta.z);
        } else {
            super.jumpFromGround();
        }
    }

    @Override
    public @NotNull Packet<@NotNull ClientGamePacketListener> getAddEntityPacket(@NotNull ServerEntity serverEntity) {
        return new ClientboundAddEntityPacket(this, serverEntity);
    }

    @Override
    public boolean isFallFlying() {
        return false;
    }

    @Override
    public boolean isOrderedToSit() {
        return orderedToSit;
    }

    @Override
    public void setOrderedToSit(boolean orderedToSit) {
        this.orderedToSit = orderedToSit;
    }

    public void tame(final @NotNull Player player) {
        this.setTame(true, true);
        this.setOwner(player);
        if (player instanceof ServerPlayer serverPlayer) {
            CriteriaTriggers.TAME_ANIMAL.trigger(serverPlayer, this);
        }
    }

    @Override
    public boolean canAttack(final @NotNull LivingEntity target) {
        return !this.isOwnedBy(target) && super.canAttack(target);
    }


    public void setOwner(@Nullable final LivingEntity owner) {
        this.entityData.set(DATA_OWNERUUID_ID, Optional.ofNullable(owner).map(EntityReference::of));
    }

    public void setOwnerReference(@Nullable final EntityReference<@NotNull LivingEntity> owner) {
        this.entityData.set(DATA_OWNERUUID_ID, Optional.ofNullable(owner));
    }

    @Override
    public void die(final @NotNull DamageSource source) {
        if (this.level() instanceof ServerLevel serverLevel
                && serverLevel.getGameRules().get(GameRules.SHOW_DEATH_MESSAGES)
                && this.getOwner() instanceof ServerPlayer serverPlayer) {
            serverPlayer.sendSystemMessage(this.getCombatTracker().getDeathMessage());
        }

        super.die(source);
    }

    @Override
    public void tryToTeleportToOwner() {

    }

    @Override
    protected void dropCustomDeathLoot(@NotNull ServerLevel level, @NotNull DamageSource damageSource, boolean recentlyHit) {
        super.dropCustomDeathLoot(level, damageSource, recentlyHit);
        if (this.isSaddled()) {
            this.spawnAtLocation(level, Items.SADDLE);

            this.setSaddled(false);
        }
    }

    protected int getMaxTemper() {
        return 100;
    }

    public void modifyTemper(int amount) {
        this.temper = Math.clamp(this.temper + amount, 0, this.getMaxTemper());
    }

    public int getTemper() {
        return this.temper;
    }


}