package com.chris.vanilla_expansion.entity.goals;


import com.chris.vanilla_expansion.entity.server.DragonAnimal;
import com.chris.vanilla_expansion.entity.server.dragons.EnergyDragonEntity;
import net.minecraft.world.entity.ai.goal.Goal;

public class DragonFlyGoal extends Goal {

    public DragonFlyGoal(DragonAnimal dragonAnimal) {
    }

    @Override
    public boolean canUse() {
        return false;
    }
}