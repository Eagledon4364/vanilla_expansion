package com.chris.vanilla_expansion.entity.server;

import com.chris.vanilla_expansion.util.DragonMoveControl;
import com.chris.vanilla_expansion.util.PlayerDragonCharge;
import com.chris.vanilla_expansion.screen.DragonInventoryMenu;
import com.chris.vanilla_expansion.sound.ModSounds;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.hurtingprojectile.LargeFireball;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.AbstractMountInventoryMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class DragonAnimal extends TamableAnimal implements HasCustomInventoryScreen, PlayerDragonCharge {
    private static final EntityDataAccessor<@NotNull Integer> STATE = SynchedEntityData.defineId(DragonAnimal.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<@NotNull Boolean> IS_FLYING = SynchedEntityData.defineId(DragonAnimal.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<@NotNull Boolean> SLEEPING = SynchedEntityData.defineId(DragonAnimal.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<@NotNull Boolean> SADDLED = SynchedEntityData.defineId(DragonAnimal.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<@NotNull Float> CHARGE = SynchedEntityData.defineId(DragonAnimal.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<@NotNull Float> DRAGON_PITCH = SynchedEntityData.defineId(DragonAnimal.class, EntityDataSerializers.FLOAT);


    private int currentHoldTicks = 0;
    private boolean isCharging = false;
    private int fireTickCooldown = 0;
    protected SimpleContainer inventory;

    protected DragonAnimal(EntityType<? extends @NotNull TamableAnimal> type, Level level) {
        super(type, level);
        this.createInventory();
        this.moveControl = new DragonMoveControl(this);
    }

    protected void createInventory() {
        this.inventory = new SimpleContainer(this.getInventorySize()) {
            @Override
            public void setChanged() {
                super.setChanged();
                DragonAnimal.this.updateContainerEquipment();
            }
        };

        this.updateContainerEquipment();
    }


    protected void updateContainerEquipment() {
        if (!this.level().isClientSide()) {
            this.setSaddled(!this.inventory.getItem(0).isEmpty() && this.inventory.getItem(0).is(Items.SADDLE));
        }
    }

    public final int getInventorySize() {
        return AbstractMountInventoryMenu.getInventorySize(this.getInventoryColumns());
    }

    public int getInventoryColumns() {
        return 1;
    }
    public static AttributeSupplier.Builder createAttributes() {
        return TamableAnimal.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 60.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.3D)
                .add(Attributes.FLYING_SPEED, 1.2D)
                .add(Attributes.FOLLOW_RANGE, 128.0D);
    }
    @Override
    protected void defineSynchedData(SynchedEntityData.@NotNull Builder builder) {
        super.defineSynchedData(builder);
        builder.define(STATE, DragonState.IDLE.ordinal());
        builder.define(IS_FLYING, false);
        builder.define(SADDLED, false);
        builder.define(SLEEPING, false);
        builder.define(CHARGE, 0.0F);
        builder.define(DRAGON_PITCH, 0.0F);
    }

    @Override
    public void addAdditionalSaveData(@NotNull ValueOutput output) {
        super.addAdditionalSaveData(output);
        output.putInt("DragonState", this.getDragonState().ordinal());
        ValueOutput.TypedOutputList<@NotNull ItemStack> itemList = output.list("Inventory", ItemStack.CODEC);
        for (int i = 0; i < this.inventory.getContainerSize(); i++) {
            ItemStack stack = this.inventory.getItem(i);
            if (!stack.isEmpty()) { // <--- The Critical Check
                itemList.add(stack);
            }
        }
    }

    @Override
    public void readAdditionalSaveData(@NotNull ValueInput input) {
        super.readAdditionalSaveData(input);
        int stateOrdinal = input.getIntOr("DragonState", DragonState.IDLE.ordinal());
        this.setDragonState(DragonState.values()[stateOrdinal]);
        input.list("Inventory", ItemStack.CODEC).ifPresent(list -> {
            int slot = 0;
            for (ItemStack stack : list) {
                if (slot < this.inventory.getContainerSize()) {
                    this.inventory.setItem(slot, stack);
                    slot++;
                }
            }});
        this.updateContainerEquipment();
    }

    public void setDragonState(DragonState state) {
        this.entityData.set(STATE, state.ordinal());
    }

    public DragonState getDragonState() {
        return DragonState.values()[this.entityData.get(STATE)];
    }

    public boolean isFlying() {
        return this.entityData.get(IS_FLYING);
    }

    public void setFlying(boolean flying) {
        if (!canFly() && flying) return;
        this.entityData.set(IS_FLYING, flying);

        if (flying) {
            if (getDragonState() != DragonState.FLY && getDragonState() != DragonState.HOVER) {
                setDragonState(DragonState.FLY);
            }
        } else {
            setDragonState(DragonState.IDLE);
            this.getMoveControl().setWantedPosition(this.getX(), this.getY(), this.getZ(), 0.0D);
        }
    }


    public abstract boolean canFly();

    public float getTurnSpeed() {
        float baseTurn = 8.0F;
        if (this.isFlying()) {
            if (this.isInWater()) return baseTurn * (float) getSwimMultiplier();

            double currentSpeed = this.getDeltaMovement().horizontalDistance();
            return Mth.lerp((float)currentSpeed / 1.2F, baseTurn, 2.5F);
        }
        return baseTurn;
    }

    public double getSwimMultiplier() {
        return 1.0D;
    }
    @Override
    public void setOrderedToSit(boolean sitting) {
        super.setOrderedToSit(sitting);
        this.setDragonState(sitting ? DragonState.SIT : DragonState.IDLE);

        if (this.level() instanceof ServerLevel) {
            this.navigation.stop();
        }
    }

    @Override
    public boolean shouldTryTeleportToOwner() {
        return false;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.fireTickCooldown > 0) {
            this.fireTickCooldown--;
        }
        if (!this.level().isClientSide()) {
            if (!this.isSleeping()) {
                if (this.isFlying() && this.onGround()) {
                    this.setFlying(false);
                }
                if (!this.onGround() && this.getDeltaMovement().y < -0.4 && !this.isFlying() && this.canFly()) {
                    this.setFlying(true);
                }
            }
            if (this.isCharging) {
                this.currentHoldTicks++;
                this.setCharge(Math.min(this.currentHoldTicks / 40.0f, 1.0f));
            }
        }
        if (!this.isFlying() || this.getDragonState() == DragonState.HOVER) {
            this.setXRot(Mth.lerp(0.1F, this.getXRot(), 0.0F));
        }
        if (this.level().isClientSide()) {
            float target = this.getDragonPitch();
            this.xRotO = this.getXRot();
            this.setXRot(Mth.lerp(0.2F, this.getXRot(), target));

            if (this.getDragonState() == DragonState.SHOOT) {
                float currentCharge = this.getCharge();
                if (currentCharge < 1.0f && currentCharge > 0) {
                    this.setCharge(currentCharge + 0.025f);
                }
            }
        }
    }

    @Override
    public void openCustomInventoryScreen(@NotNull Player player) {
        if (!this.level().isClientSide() && (!this.isVehicle() || this.hasPassenger(player)) && this.isTame()) {
            player.openMenu(new MenuProvider() {
                @Override
                public @NotNull Component getDisplayName() {
                    return DragonAnimal.this.getDisplayName();
                }

                @Override
                public AbstractContainerMenu createMenu(int id, @NotNull Inventory playerInv, @NotNull Player player) {
                    return new DragonInventoryMenu(id, playerInv, DragonAnimal.this.inventory, DragonAnimal.this);
                }
            });
        }
    }

    @Override
    public boolean isFood(@NotNull ItemStack stack) {
        return false;
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(@NotNull ServerLevel level, @NotNull AgeableMob partner) {
        return null;
    }
    @Nullable
    @Override
    public LivingEntity getControllingPassenger() {
        return this.getFirstPassenger() instanceof LivingEntity entity ? entity : null;
    }

    @Override
    public void travel(@NotNull Vec3 travelVector) {
        if (this.isAlive()) {
            if (this.isVehicle() && this.getControllingPassenger() instanceof Player player) {
                this.setYRot(player.getYRot());
                this.yRotO = this.getYRot();

                float clampedPitch = Mth.clamp(player.getXRot(), -15.0F, 40.0F);
                this.setDragonPitch(clampedPitch);


                this.setRot(this.getYRot(), this.getXRot());
                this.yBodyRot = this.getYRot();
                this.yHeadRot = this.yBodyRot;

                if (player.isJumping() && this.onGround() && this.canFly()) {
                    this.setFlying(true);
                    this.setDragonState(DragonState.FLY);
                    this.setDeltaMovement(this.getDeltaMovement().add(0, 0.5, 0));
                }
                if (player.isJumping() && !this.onGround() && this.canFly() && getDragonState() == DragonState.IDLE) {

                    this.setFlying(true);
                    this.setDragonState(DragonState.FLY);
                    this.setDeltaMovement(this.getDeltaMovement().add(0, 0.5, 0));
                }

                if (this.isFlying()) {
                    this.handleRiderFlight(player, player.xxa, player.zza);
                    if (!this.level().isClientSide()) {
                        this.calculateEntityAnimation(false);
                    }
                } else {
                    this.setSpeed((float) this.getAttributeValue(Attributes.MOVEMENT_SPEED));
                    super.travel(new Vec3(player.xxa * 0.5F, travelVector.y, player.zza));
                }
            } else {
                super.travel(travelVector);
            }
        }
    }

    private void handleRiderFlight(Player player, float xInput, float zInput) {
        Vec3 lookVec = player.getLookAngle();

        boolean isSprinting = player.isSprinting();
        double baseSpeed = 1.2;
        double speedMultiplier = isSprinting ? (baseSpeed * 5.0) : baseSpeed;
        double acceleration = isSprinting ? 0.2 : 0.1;

        boolean isAscending = player.isJumping();
        if (zInput > 0) {
            Vec3 moveVec = lookVec.scale(speedMultiplier);
            if (isAscending) {
                moveVec = moveVec.add(0, 0.8, 0);
            }

            this.setDeltaMovement(this.getDeltaMovement().lerp(moveVec, acceleration));

            if (this.getDragonState() != DragonState.FLY) {
                this.setDragonState(DragonState.FLY);
            }
        } else {
            double verticalMovement = 0.0;
            if (isAscending) {
                verticalMovement = 0.5;
            } else {
                verticalMovement = 0.0;
            }

            Vec3 currentVel = this.getDeltaMovement();
            this.setDeltaMovement(new Vec3(
                    currentVel.x * 0.9, // Horizontal friction
                    Mth.lerp(0.1, currentVel.y, verticalMovement),
                    currentVel.z * 0.9
            ));
            if (this.getDragonState() != DragonState.HOVER) {
                this.setDragonState(DragonState.HOVER);
            }
        }
        this.resetFallDistance();

        this.move(MoverType.SELF, this.getDeltaMovement());
        if (this.onGround()) {
            this.setFlying(false);
        }
    }

    @Override
    public @NotNull InteractionResult mobInteract(Player player, @NotNull InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if (this.isSleeping()) {
            if (!this.level().isClientSide()) {
                this.setSleeping(false);
            }
            return InteractionResult.SUCCESS;
        }
        if (!this.isTame() && this.isFood(itemstack)) {
            if (!this.level().isClientSide()) {
                if (this.random.nextInt(3) == 0) {
                    this.tame(player);
                    this.navigation.stop();
                    this.setTarget(null);
                    this.level().broadcastEntityEvent(this, (byte) 7);
                } else {
                    this.level().broadcastEntityEvent(this, (byte) 6);
                }
                if (!player.getAbilities().instabuild) {
                    itemstack.shrink(1);
                }
            }
            return InteractionResult.SUCCESS;
        }

        if (this.isTame() && this.isOwnedBy(player)) {
            if (this.isFood(itemstack) && this.getHealth() < this.getMaxHealth()) {
                this.feed(player, hand, itemstack, 2.0F, 2.0F);
                return InteractionResult.SUCCESS;
            }
            if (itemstack.is(Items.STICK) && !player.isSecondaryUseActive()) {
                if (!this.level().isClientSide()) {
                    boolean currentState = this.isOrderedToSit();
                    this.setOrderedToSit(!currentState);
                    this.setDragonState(!currentState ? DragonState.SIT : DragonState.IDLE);
                    this.navigation.stop();
                    this.setTarget(null);
                }
                return InteractionResult.SUCCESS;
            }
            if (player.isSecondaryUseActive()) {
                this.openCustomInventoryScreen(player);
                return InteractionResult.SUCCESS;
            }
            if (this.isSaddled() && !this.isBaby() && !itemstack.is(Items.STICK)) {
                if (!this.level().isClientSide()) {
                    player.startRiding(this);
                }
                return InteractionResult.SUCCESS;
            }
        }

        return super.mobInteract(player, hand);
    }

    public boolean isSaddled() {
        return this.entityData.get(SADDLED);
    }

    public void setSaddled(boolean saddled) {
        this.entityData.set(SADDLED, saddled);
    }
    public void performBreathAttack(LivingEntity target) {
        if (this.fireTickCooldown <= 0) {
            Vec3 mouthPos = this.position().add(this.getLookAngle().scale(2.0D)).add(0, this.getEyeHeight(), 0);
            Vec3 targetPos = new Vec3(target.getX(), target.getY(0.5D), target.getZ());
            Vec3 direction = targetPos.subtract(mouthPos).normalize();

            this.shootFireball(direction, 2);
            this.fireTickCooldown = 40;
        }
    }

    public void shootFireball(Vec3 direction, int power) {
        if (!this.level().isClientSide()) {
            Vec3 look = this.getLookAngle();
            double spawnX = this.getX() + look.x * 2.5D;
            double spawnY = this.getY() + (double)(this.getBbHeight() * 0.6F) + look.y;
            double spawnZ = this.getZ() + look.z * 2.5D;
            LargeFireball fireball = new LargeFireball(this.level(), this, direction.scale(0.2D), power);
            fireball.setPos(spawnX, spawnY, spawnZ);
            Vec3 dragonVel = this.getDeltaMovement();
            fireball.setDeltaMovement(dragonVel.add(direction.scale(1.5D)));
            this.level().broadcastEntityEvent(this, (byte) 10);
            this.level().addFreshEntity(fireball);
            this.playSound(ModSounds.ENERGY_DRAGON_FIRE, 1.0F, 1.0F);
            this.setDragonState(DragonState.SHOOT);
        }
    }

    public void setSleeping(boolean sleeping) {
        this.entityData.set(SLEEPING, sleeping);
        if (sleeping) {
            this.setDragonState(DragonState.SLEEP);
            this.navigation.stop();
            this.setTarget(null);
        } else {
            this.setDragonState(this.isOrderedToSit() ? DragonState.SIT : DragonState.IDLE);
        }
    }
    @Override
    protected void dropCustomDeathLoot(@NotNull ServerLevel level, @NotNull DamageSource source, boolean killedByPlayer) {
        super.dropCustomDeathLoot(level, source, killedByPlayer);
        if (this.inventory != null) {
            for (int i = 0; i < this.inventory.getContainerSize(); i++) {
                ItemStack stack = this.inventory.getItem(i);
                if (!stack.isEmpty()) {
                    this.spawnAtLocation(level, stack);
                }
            }
            this.inventory.clearContent();
        }
        if (this.isSaddled()) {
            this.spawnAtLocation(level, Items.SADDLE);
        }
    }

    public boolean isSleeping() {
        return this.entityData.get(SLEEPING);
    }
    @Override
    public boolean fireImmune() {
        return true;
    }

    @Override
    public boolean displayFireAnimation() {
        return false;
    }

    @Override
    public boolean canStandOnFluid(net.minecraft.world.level.material.FluidState state) {
        return state.is(net.minecraft.tags.FluidTags.LAVA);
    }

    public float getCharge() {
        return this.entityData.get(CHARGE);
    }

    public void setCharge(float charge) {
        this.entityData.set(CHARGE, Mth.clamp(charge, 0.0F, 1.0F));
    }

    @Override
    public void onDragonCharge(int chargeAmount) {
        this.setCharge(chargeAmount / 100.0f);
    }

    @Override
    public void handleStartCharge(int chargeScale) {
        this.isCharging = true;
        this.currentHoldTicks = 0;
        this.setDragonState(DragonState.SHOOT);
    }

    @Override
    public void handleStopCharge() {
        if (this.isCharging) {
            int power = 1;
            if (this.currentHoldTicks >= 40) power = 4;
            else if (this.currentHoldTicks >= 20) power = 3;
            else if (this.currentHoldTicks >= 10) power = 2;

            this.shootFireball(this.getLookAngle(), power);
            this.isCharging = false;
            this.currentHoldTicks = 0;
            this.setCharge(0.0f);
            this.setDragonState(DragonState.IDLE);
        }
    }

    public float getChargeBarFill() {
        return this.entityData.get(CHARGE);
    }

    public float getDragonPitch() {
        return this.entityData.get(DRAGON_PITCH);
    }

    public void setDragonPitch(float pitch) {
        this.entityData.set(DRAGON_PITCH, pitch);
    }
    public enum DragonState { IDLE, WALK, SIT, SLEEP, FLY, HOVER , SHOOT}
}