package com.chris.vanilla_expansion.entity.server;

import com.chris.vanilla_expansion.entity.server.ai.DragonFlyBehavior;
import com.chris.vanilla_expansion.entity.server.ai.DragonSleepBehavior;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.ActivityData;
import net.minecraft.world.entity.ai.behavior.*;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.schedule.Activity;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public class DragonBrain {

    public static List<ActivityData<DragonAnimal>> createActivities(DragonAnimal dragon) {
        return ImmutableList.of(
                createCoreActivity(),
                createIdleActivity(),
                createFightActivity()
        );
    }

    private static ActivityData<DragonAnimal> createCoreActivity() {
        return new ActivityData<>(
                Activity.CORE,
                ImmutableList.of(
                        Pair.of(0, new Swim<>(0.8F)),
                        Pair.of(1, new LookAtTargetSink(45, 90)),
                        Pair.of(2, new MoveToTargetSink())
                ),
                Set.of(),
                Set.of()
        );
    }

    private static ActivityData<DragonAnimal> createIdleActivity() {
        return new ActivityData<>(
                Activity.IDLE,
                ImmutableList.of(
                        Pair.of(0, new DragonSleepBehavior()), // Evaluates first during IDLE
                        Pair.of(1, new FollowTemptation(entity -> 1.25F)), // Follow player holding dragon food
                        Pair.of(2, new DragonFlyBehavior()),   // Flight logic for flying-capable dragons
                        Pair.of(3, SetEntityLookTarget.create(entity -> entity instanceof LivingEntity, 6.0F)),
                        Pair.of(4, StartAttacking.create(DragonBrain::findTarget)),
                        Pair.of(5, RandomStroll.stroll(1.0F))
                ),
                Set.of(),
                Set.of()
        );
    }

    private static ActivityData<DragonAnimal> createFightActivity() {
        return new ActivityData<>(
                Activity.FIGHT,
                ImmutableList.of(
                        Pair.of(0, StopAttackingIfTargetInvalid.create()),
                        Pair.of(1, MeleeAttack.create(20))
                ),
                ImmutableSet.of(
                        Pair.of(MemoryModuleType.ATTACK_TARGET, MemoryStatus.VALUE_PRESENT)
                ),
                Set.of()
        );
    }

    private static Optional<? extends LivingEntity> findTarget(ServerLevel level, DragonAnimal dragon) {
        if (dragon.isOrderedToSit() || dragon.isSleeping()) {
            return Optional.empty();
        }
        return dragon.getBrain().getMemory(MemoryModuleType.HURT_BY_ENTITY);
    }
}