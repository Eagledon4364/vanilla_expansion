package com.chris.vanilla_expansion.entity.server.ai;

import com.chris.vanilla_expansion.entity.server.DragonAnimal;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.phys.Vec3;

import java.util.Map;

public class DragonFlyBehavior extends Behavior<DragonAnimal> {
    private int flightTicks;

    public DragonFlyBehavior() {
        super(Map.of(
                MemoryModuleType.WALK_TARGET, MemoryStatus.VALUE_ABSENT
        ));
    }

    @Override
    protected boolean checkExtraStartConditions(ServerLevel level, DragonAnimal dragon) {
        if (!dragon.canFly() || dragon.isFlying() || dragon.isVehicle() || dragon.isOrderedToSit() || dragon.isSleeping()) {
            return false;
        }
        return level.getRandom().nextInt(200) == 0;
    }

    @Override
    protected boolean canStillUse(ServerLevel level, DragonAnimal dragon, long gameTime) {
        return this.flightTicks > 0 && dragon.isFlying() && !dragon.isOrderedToSit() && !dragon.isSleeping() && !dragon.isVehicle();
    }

    @Override
    protected void start(ServerLevel level, DragonAnimal dragon, long gameTime) {
        this.flightTicks = 200 + level.getRandom().nextInt(200);
        dragon.setFlying(true);
        dragon.setDragonState(DragonAnimal.DragonState.FLY);

        dragon.setDeltaMovement(dragon.getDeltaMovement().add(0.0D, 0.5D, 0.0D));

        setNewFlightTarget(level, dragon);
    }

    @Override
    protected void tick(ServerLevel level, DragonAnimal dragon, long gameTime) {
        this.flightTicks--;

        if (dragon.getBrain().getMemory(MemoryModuleType.WALK_TARGET).isEmpty()) {
            setNewFlightTarget(level, dragon);
        }
    }

    @Override
    protected void stop(ServerLevel level, DragonAnimal dragon, long gameTime) {
        this.flightTicks = 0;
        dragon.getBrain().eraseMemory(MemoryModuleType.WALK_TARGET);

        dragon.setFlying(false);
        dragon.setDragonState(DragonAnimal.DragonState.WALK);
    }

    private void setNewFlightTarget(ServerLevel level, DragonAnimal dragon) {
        Vec3 currentPos = dragon.position();
        double targetX = currentPos.x + (level.getRandom().nextDouble() - 0.5D) * 30.0D;
        double targetY = currentPos.y + 5.0D + level.getRandom().nextDouble() * 8.0D;
        double targetZ = currentPos.z + (level.getRandom().nextDouble() - 0.5D) * 30.0D;

        dragon.getBrain().setMemory(MemoryModuleType.WALK_TARGET, new WalkTarget(new Vec3(targetX, targetY, targetZ), 1.2F, 2));
    }
}