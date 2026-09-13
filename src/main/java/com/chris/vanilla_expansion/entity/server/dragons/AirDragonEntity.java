package com.chris.vanilla_expansion.entity.server.dragons;

import com.chris.vanilla_expansion.block.ModBlocks;
import com.chris.vanilla_expansion.entity.server.DragonAnimal;
import com.chris.vanilla_expansion.sound.ModSounds;
import com.chris.vanilla_expansion.util.ModTags;
import com.chris.vanilla_expansion.util.registry.ModLootTables;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.Nullable;

public class AirDragonEntity extends DragonAnimal {

    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState walkAnimationState = new AnimationState();
    public final AnimationState hoverAnimationState = new AnimationState();
    public final AnimationState flyAnimationState = new AnimationState();
    public final AnimationState sleepingAnimationState = new AnimationState();
    public final AnimationState sitAnimationState = new AnimationState();

    public final AnimationState meleeAnimationState = new AnimationState();
    public final AnimationState fireAnimationState = new AnimationState();
    private int fireAnimationTimer = 0;
    private int flapTimer = 0;
    private int scaleTime;

    public AirDragonEntity(EntityType<? extends @NotNull AirDragonEntity> type, Level level) {
        super(type, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return DragonAnimal.createAttributes()
                .add(Attributes.MAX_HEALTH, 40.0D)
                .add(Attributes.ATTACK_DAMAGE, 6.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.25F)
                .add(Attributes.FLYING_SPEED, 2.0F)
                .add(Attributes.FOLLOW_RANGE, 64.0D)
                .add(Attributes.TEMPT_RANGE, 20.0D);
    }

    @Override
    public boolean canFly() {
        return true;
    }

    @Override
    public boolean canSwim() {
        return false;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.isFlying() && !this.isSleeping()) {
            if (this.flapTimer > 0) {
                this.flapTimer--;
            } else {
                this.playSound(ModSounds.DRAGON_WING_FLAP_1, 1.0F, 1.0F);
                this.flapTimer = 18;
            }
        }
        if (this.level().isClientSide()) {
            this.setupAnimationStates();

            if (this.fireAnimationTimer > 0) {
                this.fireAnimationTimer--;
                if (this.fireAnimationTimer <= 0) {
                    this.fireAnimationState.stop();
                }
            }
        } else {
            if (this.isOrderedToSit()) {
                this.setDeltaMovement(Vec3.ZERO);
                this.navigation.stop();

                if (this.getDragonState() != DragonState.SIT) {
                    this.setDragonState(DragonState.SIT);
                }
            }

            if (this.isVehicle() && !this.isOrderedToSit() && this.getControllingPassenger() instanceof LivingEntity driver) {
                this.setYRot(driver.getYRot());
                this.yRotO = this.getYRot();

                float clampedPitch = Mth.clamp(driver.getXRot() * 0.5F, -50.0F, 50.0F);
                this.setXRot(clampedPitch);
                this.xRotO = clampedPitch;

                if (this.isFlying()) {
                    this.resetFallDistance();
                }
            }
        }
    }

    private void setupAnimationStates() {
        boolean isSitting = this.isOrderedToSit() || this.getDragonState() == DragonState.SIT;

        if (isSitting) {
            if (this.walkAnimationState.isStarted()) this.stopAllMovementAnimations();
            if (this.sleepingAnimationState.isStarted()) this.sleepingAnimationState.stop();
            if (this.fireAnimationState.isStarted()) this.fireAnimationState.stop();

            this.sitAnimationState.startIfStopped(this.tickCount);
            return;
        } else {
            if (this.sitAnimationState.isStarted()) this.sitAnimationState.stop();
        }

        if (this.isSleeping()) {
            this.stopAllMovementAnimations();
            this.sleepingAnimationState.startIfStopped(this.tickCount);
            return;
        }

        if (this.isFlying()) {
            this.stopGroundedAnimations();
            if (this.getDeltaMovement().horizontalDistanceSqr() > 1.0E-4D) {
                this.flyAnimationState.startIfStopped(this.tickCount);
                this.hoverAnimationState.stop();
            } else {
                this.hoverAnimationState.startIfStopped(this.tickCount);
                this.flyAnimationState.stop();
            }
        } else {
            this.stopFlyingAnimations();
            if (this.getDeltaMovement().horizontalDistanceSqr() > 1.0E-4D) {
                this.walkAnimationState.startIfStopped(this.tickCount);
                this.idleAnimationState.stop();
            } else {
                this.idleAnimationState.startIfStopped(this.tickCount);
                this.walkAnimationState.stop();
            }
        }
    }

    private void stopGroundedAnimations() {
        this.idleAnimationState.stop();
        this.walkAnimationState.stop();
    }

    private void stopFlyingAnimations() {
        this.flyAnimationState.stop();
        this.hoverAnimationState.stop();
    }

    private void stopAllMovementAnimations() {
        this.idleAnimationState.stop();
        this.walkAnimationState.stop();
        this.flyAnimationState.stop();
        this.hoverAnimationState.stop();
    }

    @Override
    protected void positionRider(@NotNull Entity passenger, Entity.@NotNull MoveFunction moveFunction) {
        super.positionRider(passenger, moveFunction);

        if (this.hasPassenger(passenger)) {
            float yawRad = this.yBodyRot * ((float)Math.PI / 180F);

            double heightOffset = 0.6D;
            double forwardOffset = 0.4D;

            double x = Math.sin(yawRad) * -forwardOffset;
            double z = Math.cos(yawRad) * forwardOffset;

            moveFunction.accept(passenger, this.getX() + x, this.getY() + heightOffset, this.getZ() + z);
        }
    }

    @Override
    public boolean isFood(@NotNull ItemStack itemStack) {
        return itemStack.is(ModTags.Items.DRAGON_FOOD);
    }

    @Override
    public @Nullable AgeableMob getBreedOffspring(@NotNull ServerLevel level, @NotNull AgeableMob partner) {
        BlockPos pos = this.blockPosition();
        Containers.dropItemStack(level, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(ModBlocks.AIR_DRAGON_EGG));
        return null;
    }

    @Override
    public void handleEntityEvent(byte id) {
        if (id == 10) {
            this.fireAnimationState.stop();
            this.fireAnimationState.start(this.tickCount);
            this.fireAnimationTimer = 20;
        } else {
            super.handleEntityEvent(id);
        }
    }

    @Override
    protected @Nullable SoundEvent getDeathSound() {
        return SoundEvents.GENERIC_DEATH;
    }

    @Override
    protected @Nullable SoundEvent getHurtSound(@NotNull DamageSource source) {
        return ModSounds.DRAGON_GROWL1;
    }

    @Override
    protected @Nullable SoundEvent getAmbientSound() {
        return ModSounds.DRAGON_GROWL;
    }

    @Override
    public @NotNull InteractionResult mobInteract(Player player, @NotNull InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        if (itemStack.is(Items.BRUSH) && this.brushOffScute(player, itemStack)) {
            itemStack.hurtAndBreak(16, player, hand.asEquipmentSlot());
            return InteractionResult.SUCCESS;
        }
        return super.mobInteract(player, hand);
    }

    public boolean brushOffScute(@Nullable final Entity interactingEntity, final ItemStack tool) {
        if (this.isBaby()) {
            return false;
        } else {
            if (this.level() instanceof ServerLevel level) {
                this.dropFromEntityInteractLootTable(level, ModLootTables.AIR_DRAGON_SCALE, interactingEntity, tool, this::spawnAtLocation);
                this.playSound(SoundEvents.ARMADILLO_BRUSH);
                this.gameEvent(GameEvent.ENTITY_INTERACT);
            }

            return true;
        }
    }
}