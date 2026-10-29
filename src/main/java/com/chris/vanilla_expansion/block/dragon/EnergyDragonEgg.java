package com.chris.vanilla_expansion.block.dragon;

import com.chris.vanilla_expansion.block.ModBlockEntities;
import com.chris.vanilla_expansion.entity.ModEntities;
import com.chris.vanilla_expansion.entity.server.DragonAnimal;
import com.mojang.serialization.MapCodec;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class EnergyDragonEgg extends DragonEgg {
    public EnergyDragonEgg(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return simpleCodec(EnergyDragonEgg::new);
    }

    @Override
    public EntityType<? extends DragonAnimal> getDragonType() {
        return ModEntities.ENERGY_DRAGON;
    }

    @Override
    public BlockEntityType<? extends DragonEggBlockEntity> getBlockEntityType() {
        return ModBlockEntities.ENERGY_DRAGON_EGG_BE;
    }
}