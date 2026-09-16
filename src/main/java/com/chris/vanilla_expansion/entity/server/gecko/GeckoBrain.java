package com.chris.vanilla_expansion.entity.server.gecko;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.ActivityData;
import net.minecraft.world.entity.ai.behavior.*;
import net.minecraft.world.entity.schedule.Activity;

import java.util.List;
import java.util.Set;

public class GeckoBrain {

    public static List<ActivityData<GeckoEntity>> createActivities(GeckoEntity gecko) {
        return ImmutableList.of(
                createCoreActivity(),
                createIdleActivity()
        );
    }

    private static ActivityData<GeckoEntity> createCoreActivity() {
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

    private static ActivityData<GeckoEntity> createIdleActivity() {
        return new ActivityData<>(
                Activity.IDLE,
                ImmutableList.of(
                        Pair.of(1, new FollowTemptation(entity -> 1.25F)),
                        Pair.of(3, SetEntityLookTarget.create(entity -> entity instanceof LivingEntity, 6.0F)),
                        Pair.of(5, RandomStroll.stroll(1.0F))
                ),
                Set.of(),
                Set.of()
        );
    }
}
