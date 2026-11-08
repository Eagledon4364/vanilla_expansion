package com.chris.vanilla_expansion.entity.server.gecko;

import com.chris.vanilla_expansion.entity.ModEntities;
import com.chris.vanilla_expansion.item.ModItems;
import com.google.common.collect.ImmutableList;
import com.mojang.logging.LogUtils;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.animal.parrot.ShoulderRidingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.storage.TagValueOutput;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.Nullable;
import org.slf4j.Logger;

import java.util.List;

public class GeckoEntity extends ShoulderRidingEntity {
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final int RIDE_COOLDOWN = 100;
    private int rideCooldownCounter;

    private static final EntityDataAccessor<@NotNull Integer> STATE = SynchedEntityData.defineId(GeckoEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<@NotNull Boolean> SLEEPING = SynchedEntityData.defineId(GeckoEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<@NotNull Integer> VARIANT = SynchedEntityData.defineId(GeckoEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<@NotNull Boolean> RIDING_SHOULDER = SynchedEntityData.defineId(GeckoEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<@NotNull Boolean> SITTING = SynchedEntityData.defineId(GeckoEntity.class, EntityDataSerializers.BOOLEAN);

    public final AnimationState walkAnimationState = new AnimationState();
    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState sleepStartAnimationState = new AnimationState();
    public final AnimationState sleepAnimationState = new AnimationState();
    public final AnimationState sleepStopAnimationState = new AnimationState();
    public final AnimationState mountAnimationState = new AnimationState();
    public final AnimationState sitAnimationState = new AnimationState();

    protected static final List<SensorType<? extends Sensor<? super GeckoEntity>>> SENSORS =
            ImmutableList.of(
                    SensorType.NEAREST_LIVING_ENTITIES,
                    SensorType.NEAREST_PLAYERS,
                    SensorType.HURT_BY,
                    SensorType.FOOD_TEMPTATIONS
            );

    protected static final List<MemoryModuleType<?>> MEMORIES =
            ImmutableList.of(
                    MemoryModuleType.WALK_TARGET,
                    MemoryModuleType.LOOK_TARGET,
                    MemoryModuleType.ATTACK_TARGET,
                    MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES,
                    MemoryModuleType.HURT_BY_ENTITY,
                    MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE,
                    MemoryModuleType.TEMPTING_PLAYER,
                    MemoryModuleType.TEMPTATION_COOLDOWN_TICKS,
                    MemoryModuleType.BREED_TARGET
            );

    public GeckoEntity(EntityType<? extends @NotNull GeckoEntity> type, Level level) {
        super(type, level);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(STATE, 0);
        builder.define(SLEEPING, false);
        builder.define(VARIANT, 0);
        builder.define(RIDING_SHOULDER, false);
        builder.define(SITTING, false);
    }

    public int getVariant() {
        return this.entityData.get(VARIANT);
    }

    public void setVariant(int variant) {
        this.entityData.set(VARIANT, variant);
    }

    public boolean isRidingShoulder() {
        return this.entityData.get(RIDING_SHOULDER);
    }

    public void setRidingShoulder(boolean ridingShoulder) {
        this.entityData.set(RIDING_SHOULDER, ridingShoulder);
    }

    public boolean isGeckoSitting() {
        return this.entityData.get(SITTING);
    }

    public void setGeckoSitting(boolean sitting) {
        this.entityData.set(SITTING, sitting);
    }

    @Override
    public boolean isSleeping() {
        return this.entityData.get(SLEEPING);
    }

    public void setSleeping(boolean sleeping) {
        this.entityData.set(SLEEPING, sleeping);
    }

    @Override
    public void addAdditionalSaveData(ValueOutput output) {
        super.addAdditionalSaveData(output);
        output.putInt("Variant", this.getVariant());
        output.putBoolean("IsSleeping", this.isSleeping());
        output.putBoolean("RidingShoulder", this.isRidingShoulder());
        output.putBoolean("IsSitting", this.isGeckoSitting());
    }

    @Override
    public void readAdditionalSaveData(ValueInput input) {
        super.readAdditionalSaveData(input);
        this.setVariant(input.getIntOr("Variant", 0));
        this.setSleeping(input.getBooleanOr("IsSleeping", false));
        this.setRidingShoulder(input.getBooleanOr("RidingShoulder", false));
        this.setGeckoSitting(input.getBooleanOr("IsSitting", false));
    }

    @Override
    public boolean isFood(ItemStack itemStack) {
        return itemStack.is(ModItems.BLUEBERRY);
    }

    @Override
    public @Nullable AgeableMob getBreedOffspring(@NotNull ServerLevel level, @NotNull AgeableMob partner) {
        GeckoEntity baby = ModEntities.GECKO.create(level, EntitySpawnReason.BREEDING);
        if (baby != null && partner instanceof GeckoEntity partnerGecko) {
            baby.setVariant(this.random.nextBoolean() ? this.getVariant() : partnerGecko.getVariant());
        }
        return baby;
    }

    @Override
    public boolean setEntityOnShoulder(ServerPlayer player) {
        try (ProblemReporter.ScopedCollector reporter = new ProblemReporter.ScopedCollector(this.problemPath(), LOGGER)) {
            TagValueOutput output = TagValueOutput.createWithContext(reporter, this.registryAccess());
            this.saveWithoutId(output);
            output.putString("id", this.getEncodeId());
            if (player.setEntityOnShoulder(output.buildResult())) {
                this.discard();
                return true;
            }
        }
        return false;
    }

    public boolean canSitOnShoulder() {
        return this.rideCooldownCounter > 100;
    }

    @Override
    public @NotNull InteractionResult mobInteract(Player player, @NotNull InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if (!this.isTame() && itemstack.is(ModItems.BLUEBERRY)) {
            if (!this.level().isClientSide()) {
                itemstack.consume(1, player);
                if (this.random.nextInt(3) == 0) {
                    this.tame(player);
                    this.navigation.stop();
                    this.setGeckoSitting(true);
                    this.level().broadcastEntityEvent(this, (byte) 7);
                } else {
                    this.level().broadcastEntityEvent(this, (byte) 6);
                }
            }
            return InteractionResult.SUCCESS;
        }

        if (this.isTame() && this.isFood(itemstack) && !this.isBaby() && !this.isInLove()) {
            if (!this.level().isClientSide()) {
                itemstack.consume(1, player);
                this.setInLove(player);
            }
            return InteractionResult.SUCCESS;
        }

        if (this.isTame() && this.isOwnedBy(player) && !this.isFood(itemstack)) {
            if (!this.level().isClientSide()) {
                if (player.isSecondaryUseActive()) {
                    boolean newSittingState = !this.isGeckoSitting();
                    this.setGeckoSitting(newSittingState);
                    this.navigation.stop();
                } else if (!this.isGeckoSitting() && this.canSitOnShoulder() && player instanceof ServerPlayer serverPlayer) {
                    this.setEntityOnShoulder(serverPlayer);
                }
            }
            return InteractionResult.SUCCESS;
        }

        return super.mobInteract(player, hand);
    }

    protected Brain.Provider<GeckoEntity> brainProvider() {
        return Brain.provider(SENSORS, GeckoBrain::createActivities);
    }

    @Override
    protected Brain<?> makeBrain(Brain.Packed packedBrain) {
        return this.brainProvider().makeBrain(this, packedBrain);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Brain<GeckoEntity> getBrain() {
        return (Brain<GeckoEntity>) super.getBrain();
    }

    @Override
    protected void customServerAiStep(ServerLevel level) {
        this.getBrain().tick(level, this);
        super.customServerAiStep(level);

        long time = level.getDefaultClockTime() % 24000;
        boolean isNight = time >= 13000 && time < 23000;

        if (isNight && !this.isGeckoSitting() && !this.isVehicle() && !this.isAggressive() && !this.isInLove()) {
            if (!this.isSleeping()) {
                this.setSleeping(true);
            }
            this.navigation.stop();
            this.getBrain().eraseMemory(MemoryModuleType.WALK_TARGET);
            this.setDeltaMovement(0.0D, this.getDeltaMovement().y, 0.0D);
        } else if (!isNight && this.isSleeping()) {
            this.setSleeping(false);
        }
    }

    private void setupAnimationStates() {
        boolean isSleeping = this.isSleeping();
        boolean isSitting = this.isGeckoSitting();
        boolean isMoving = this.walkAnimation.speed() > 0.05F && !isSitting && !isSleeping;

        this.mountAnimationState.animateWhen(this.isRidingShoulder(), this.tickCount);

        if (isSleeping) {
            this.sleepStartAnimationState.animateWhen(true, this.tickCount);
            this.sleepAnimationState.animateWhen(true, this.tickCount);
            this.sleepStopAnimationState.animateWhen(false, this.tickCount);
        } else {
            this.sleepStartAnimationState.animateWhen(false, this.tickCount);
            this.sleepAnimationState.animateWhen(false, this.tickCount);
            this.sleepStopAnimationState.animateWhen(false, this.tickCount);
        }

        this.sitAnimationState.animateWhen(!isSleeping && isSitting, this.tickCount);
        this.walkAnimationState.animateWhen(!isSleeping && !isSitting && isMoving, this.tickCount);
        this.idleAnimationState.animateWhen(!isSleeping && !isSitting && !isMoving, this.tickCount);
    }

    @Override
    public void tick() {
        ++this.rideCooldownCounter;
        super.tick();
        if (this.level().isClientSide()) {
            this.setupAnimationStates();
        } else {
            if (this.isGeckoSitting()) {
                this.navigation.stop();
                this.getBrain().eraseMemory(MemoryModuleType.WALK_TARGET);
                this.setDeltaMovement(0.0D, this.getDeltaMovement().y, 0.0D);
            }
        }
    }


    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, EntitySpawnReason spawnReason, @Nullable SpawnGroupData spawnGroupData) {
        spawnGroupData = super.finalizeSpawn(level, difficulty, spawnReason, spawnGroupData);
        this.setVariant(this.random.nextInt(4));
        return spawnGroupData;
    }
    public static AttributeSupplier.Builder createAttributes() {
        return ShoulderRidingEntity.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 10.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.18D)
                .add(Attributes.TEMPT_RANGE, 20.0D)
                .add(Attributes.FOLLOW_RANGE, 20.0D);
    }
}