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
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.Nullable;

public class EarthDragonEntity extends DragonAnimal {

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

    public EarthDragonEntity(EntityType<? extends @NotNull EarthDragonEntity> type, Level level) {
        super(type, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return DragonAnimal.createAttributes()
                .add(Attributes.MAX_HEALTH, 60.0D)
                .add(Attributes.ATTACK_DAMAGE, 6.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.18F)
                .add(Attributes.FOLLOW_RANGE, 64.0D)
                .add(Attributes.TEMPT_RANGE, 20.0D);
    }

    @Override
    public boolean canFly() {
        return false;
    }

    @Override
    public boolean canSwim() {
        return false;
    }

    @Override
    public void tick() {
        super.tick();

        // Client-side: Handle animations
        if (this.level().isClientSide()) {
            this.setupAnimationStates();
        } else {
            // Server-side: Handle sitting movement locks
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
            }
        }
    }

    private void setupAnimationStates() {
        boolean isSitting = this.isOrderedToSit() || this.getDragonState() == DragonState.SIT;

        if (isSitting) {
            // Only stop if they aren't already stopped
            if (this.walkAnimationState.isStarted()) this.stopAllMovementAnimations();
            if (this.sleepingAnimationState.isStarted()) this.sleepingAnimationState.stop();

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

        if (this.getDeltaMovement().horizontalDistanceSqr() > 1.0E-4D) {
            this.walkAnimationState.startIfStopped(this.tickCount);
            this.idleAnimationState.stop();
        } else {
            this.idleAnimationState.startIfStopped(this.tickCount);
            this.walkAnimationState.stop();
        }
    }

    private void stopGroundedAnimations() {
        this.idleAnimationState.stop();
        this.walkAnimationState.stop();
    }

    private void stopAllMovementAnimations() {
        this.idleAnimationState.stop();
        this.walkAnimationState.stop();
    }

    @Override
    public void addAdditionalSaveData(@NotNull ValueOutput output) {
        super.addAdditionalSaveData(output);
        output.putInt("scale_time", this.scaleTime);
    }

    @Override
    public void readAdditionalSaveData(@NotNull ValueInput input) {
        super.readAdditionalSaveData(input);
        input.getInt("scale_time").ifPresent(time -> this.scaleTime = time);
    }

    @Override
    public boolean isFood(@NotNull ItemStack itemStack) {
        return itemStack.is(ModTags.Items.DRAGON_FOOD);
    }

    @Override
    public @Nullable AgeableMob getBreedOffspring(@NotNull ServerLevel level, @NotNull AgeableMob partner) {
        BlockPos pos = this.blockPosition();
        Containers.dropItemStack(level, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(ModBlocks.EARTH_DRAGON_EGG));
        return null;
    }

    @Override
    public void handleEntityEvent(byte id) {
        if (id == 10) {
            if (this.level().isClientSide()) {
                this.fireAnimationState.stop();
                this.fireAnimationState.start(this.tickCount);
                this.fireAnimationTimer = 20;
            }
            this.playSound(ModSounds.ENERGY_DRAGON_FIRE, 1.0F, 1.0F);
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
                this.dropFromEntityInteractLootTable(level, ModLootTables.EARTH_DRAGON_SCALE, interactingEntity, tool, this::spawnAtLocation);
                this.playSound(SoundEvents.ARMADILLO_BRUSH);
                this.gameEvent(GameEvent.ENTITY_INTERACT);
            }

            return true;
        }
    }
}