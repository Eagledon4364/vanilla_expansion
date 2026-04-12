package com.chris.vanilla_expansion.entity.goals;

import com.chris.vanilla_expansion.block.ModBlocks;
import com.chris.vanilla_expansion.entity.server.DragonAnimal;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.EnumSet;

public class DragonSleepGoal extends Goal {
    private static final int MIN_SLEEP_TICKS = 2400;
    private int sleepTimer = 0;
    private static final int WAIT_TIME_BEFORE_SLEEP = 140; // ~7 seconds of idling
    private int countdown;
    protected final DragonAnimal dragon;

    public DragonSleepGoal(DragonAnimal dragon) {
        this.dragon = dragon;
        this.countdown = dragon.getRandom().nextInt(WAIT_TIME_BEFORE_SLEEP);
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK, Goal.Flag.JUMP));
    }

    @Override
    public boolean canUse() {
        // Trigger if still and on the right block
        return dragon.xxa == 0.0F && dragon.zza == 0.0F && canSleepOnCurrentBlock();
    }

    @Override
    public boolean canContinueToUse() {
        // If they haven't slept for 2 minutes yet, they CANNOT wake up
        if (sleepTimer < MIN_SLEEP_TICKS) {
            return true;
        }
        // After 2 minutes, wake up if player interacts or they move
        return dragon.isSleeping() && canSleepOnCurrentBlock();
    }


    private boolean canSleepOnCurrentBlock() {
        BlockPos pos = dragon.blockPosition().below();
        BlockState state = dragon.level().getBlockState(pos);

        // Check for Stone or Steel
        return state.is(Blocks.STONE) || state.is(ModBlocks.STEEL_BLOCK);
    }

    @Override
    public void start() {
        this.sleepTimer = 0;
        dragon.setSleeping(true);
        dragon.getNavigation().stop();
    }

    @Override
    public void tick() {
        if (dragon.isSleeping()) {
            this.sleepTimer++;
        }
    }

    @Override
    public void stop() {
        this.sleepTimer = 0;
        dragon.setSleeping(false);
    }
}