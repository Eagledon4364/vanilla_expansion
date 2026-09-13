package com.chris.vanilla_expansion.entity.server.ai;

import com.chris.vanilla_expansion.block.ModBlocks;
import com.chris.vanilla_expansion.entity.server.DragonAnimal;
import com.google.common.collect.ImmutableMap;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class DragonSleepBehavior extends Behavior<DragonAnimal> {
    private static final int MIN_SLEEP_TICKS = 2400;
    private int sleepTimer = 0;

    public DragonSleepBehavior() {
        // Run during IDLE activity; ensure entity is not actively navigating to a walk target
        super(ImmutableMap.of(
                MemoryModuleType.WALK_TARGET, MemoryStatus.VALUE_ABSENT
        ));
    }

    @Override
    protected boolean checkExtraStartConditions(ServerLevel level, DragonAnimal dragon) {
        return !dragon.isFlying()
                && dragon.getDeltaMovement().horizontalDistanceSqr() < 0.001D
                && canSleepOnCurrentBlock(level, dragon);
    }

    @Override
    protected boolean canStillUse(ServerLevel level, DragonAnimal dragon, long gameTime) {
        if (this.sleepTimer < MIN_SLEEP_TICKS) {
            return true;
        }
        return dragon.isSleeping() && canSleepOnCurrentBlock(level, dragon);
    }

    @Override
    protected void start(ServerLevel level, DragonAnimal dragon, long gameTime) {
        this.sleepTimer = 0;
        dragon.setSleeping(true);
        dragon.getNavigation().stop();
    }

    @Override
    protected void tick(ServerLevel level, DragonAnimal dragon, long gameTime) {
        if (dragon.isSleeping()) {
            this.sleepTimer++;
        }
    }

    @Override
    protected void stop(ServerLevel level, DragonAnimal dragon, long gameTime) {
        this.sleepTimer = 0;
        dragon.setSleeping(false);
    }

    private boolean canSleepOnCurrentBlock(ServerLevel level, DragonAnimal dragon) {
        BlockPos pos = dragon.blockPosition().below();
        BlockState state = level.getBlockState(pos);

        return state.is(Blocks.STONE)
                || state.is(ModBlocks.STEEL_BLOCK)
                || state.is(Blocks.STONE_BRICKS);
    }
}