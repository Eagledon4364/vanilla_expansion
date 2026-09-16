package com.chris.vanilla_expansion.entity.server.gecko;

import com.chris.vanilla_expansion.entity.server.DragonAnimal;
import com.chris.vanilla_expansion.item.ModItems;
import com.google.common.collect.ImmutableList;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.Nullable;

import java.util.List;

public class GeckoEntity extends TamableAnimal {
    private static final EntityDataAccessor<@NotNull Integer> STATE = SynchedEntityData.defineId(GeckoEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<@NotNull Boolean> SLEEPING = SynchedEntityData.defineId(GeckoEntity.class, EntityDataSerializers.BOOLEAN);

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
                    MemoryModuleType.IS_TEMPTED
            );


    protected GeckoEntity(EntityType<? extends TamableAnimal> type, Level level) {
        super(type, level);
    }

    @Override
    public boolean isFood(ItemStack itemStack) {
        return false;
    }

    @Override
    public @Nullable AgeableMob getBreedOffspring(ServerLevel level, AgeableMob partner) {
        return null;
    }
    protected Brain.Provider<GeckoEntity> brainProvider() {
        return Brain.provider(MEMORIES, SENSORS, GeckoBrain::createActivities);
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
    }

    public static AttributeSupplier.Builder createAttributes() {
        return TamableAnimal.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 10.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.01D)
                .add(Attributes.FOLLOW_RANGE, 20.0D);
    }
}
