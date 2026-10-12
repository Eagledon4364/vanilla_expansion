package com.chris.vanilla_expansion.block.dragon;

import com.chris.vanilla_expansion.block.ModBlockEntities;
import com.chris.vanilla_expansion.entity.ModEntities;
import com.chris.vanilla_expansion.entity.server.DragonAnimal;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class WaterDragonEgg extends DragonEgg {
    public WaterDragonEgg(Properties properties) {
        super(properties);
    }

    @Override
    public EntityType<? extends DragonAnimal> getDragonType() {
        return ModEntities.WATER_DRAGON;
    }

    @Override
    public BlockEntityType<? extends DragonEggBlockEntity> getBlockEntityType() {
        return ModBlockEntities.WATER_DRAGON_EGG_BE;
    }
}