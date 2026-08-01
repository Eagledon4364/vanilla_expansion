package com.chris.vanilla_expansion.entity.server.dragons;

import com.chris.vanilla_expansion.block.ModBlocks;
import com.chris.vanilla_expansion.entity.ModEntities;
import com.chris.vanilla_expansion.entity.goals.DragonSleepGoal;
import com.chris.vanilla_expansion.entity.server.DragonAnimal;
import com.chris.vanilla_expansion.sound.ModSounds;
import com.chris.vanilla_expansion.util.ModTags;
import com.chris.vanilla_expansion.util.registry.ModLootTables;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.Nullable;

public class WaterDragonEntity extends DragonAnimal {

    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState walkAnimationState = new AnimationState();
    public final AnimationState sleepingAnimationState = new AnimationState();
    public final AnimationState sitAnimationState = new AnimationState();

    public final AnimationState meleeAnimationState = new AnimationState();
    private int scaleTime;

    public WaterDragonEntity(EntityType<? extends @NotNull WaterDragonEntity> type, Level level) {
        super(type, level);
    }
    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new TamableAnimal.TamableAnimalPanicGoal(1.5, DamageTypeTags.PANIC_ENVIRONMENTAL_CAUSES));
        this.goalSelector.addGoal(1, new SitWhenOrderedToGoal(this));
        this.goalSelector.addGoal(2, new DragonSleepGoal(this));

        this.goalSelector.addGoal(3, new BreedGoal(this, 1.1f));
        this.goalSelector.addGoal(4, new TemptGoal(this, 1.25D, Ingredient.of(Items.COD), false));
        this.goalSelector.addGoal(5, new MeleeAttackGoal(this, 1.0, true));
        this.goalSelector.addGoal(6, new FollowOwnerGoal(this, 1.0, 10.0F, 2.0F));

        this.goalSelector.addGoal(6, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new RandomSwimmingGoal(this, 1.0D, 1));

        this.goalSelector.addGoal(7, new FollowParentGoal(this, 1.1D));

        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));

        this.targetSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));
        this.targetSelector.addGoal(3, new HurtByTargetGoal(this));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return DragonAnimal.createAttributes()
                .add(Attributes.MAX_HEALTH, 35.0D)
                .add(Attributes.ATTACK_DAMAGE, 6.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.25F)
                .add(Attributes.FOLLOW_RANGE, 64.0D)
                .add(Attributes.TEMPT_RANGE, 20.0D);
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
    public boolean canFly() {
        return false;
    }
    @Override
    public boolean isFood(@NotNull ItemStack itemStack) {
        return itemStack.is(ModTags.Items.DRAGON_FOOD);
    }

    @Override
    public @Nullable AgeableMob getBreedOffspring(@NotNull ServerLevel level, @NotNull AgeableMob partner) {
        BlockPos pos = this.blockPosition();
        Containers.dropItemStack(level, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(ModBlocks.WATER_DRAGON_EGG));
        return null;
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
                this.dropFromEntityInteractLootTable(level, ModLootTables.WATER_DRAGON_SCALE, interactingEntity, tool, this::spawnAtLocation);
                this.playSound(SoundEvents.ARMADILLO_BRUSH);
                this.gameEvent(GameEvent.ENTITY_INTERACT);
            }

            return true;
        }
    }
}
