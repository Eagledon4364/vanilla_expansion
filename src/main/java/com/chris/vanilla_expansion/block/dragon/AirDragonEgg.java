package com.chris.vanilla_expansion.block.dragon;

import com.chris.vanilla_expansion.entity.ModEntities;
import com.chris.vanilla_expansion.entity.server.DragonAnimal;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class AirDragonEgg extends DragonEgg{
    public AirDragonEgg(Properties properties) {
        super(properties);
    }
    @Override
    protected void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston) {
        if (!level.isClientSide()) {
            // Added EntitySpawnReason.EVENT as the second parameter
            DragonAnimal energyDragon = ModEntities.AIR_DRAGON.create(level, EntitySpawnReason.EVENT);

            super.setDragon(energyDragon);
        }

        super.onPlace(state, level, pos, oldState, movedByPiston);
    }
}
