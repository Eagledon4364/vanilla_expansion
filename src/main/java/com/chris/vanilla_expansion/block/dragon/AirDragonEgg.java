package com.chris.vanilla_expansion.block.dragon;

import com.chris.vanilla_expansion.block.ModBlockEntities;
import com.chris.vanilla_expansion.entity.ModEntities;
import com.chris.vanilla_expansion.entity.server.DragonAnimal;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class AirDragonEgg extends DragonEgg {
    public AirDragonEgg(Properties properties) {
        super(properties);
    }

    @Override
    public EntityType<? extends DragonAnimal> getDragonType() {
        return ModEntities.AIR_DRAGON;
    }

    @Override
    public BlockEntityType<? extends DragonEggBlockEntity> getBlockEntityType() {
        return ModBlockEntities.AIR_DRAGON_EGG_BE;
    }
}