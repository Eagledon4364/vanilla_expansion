package com.chris.vanilla_expansion_dragons.blocks;

import com.chris.vanilla_expansion_dragons.entity.ModDragons;
import com.chris.vanilla_expansion_dragons.entity.server.util.DragonAnimal;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class ElderWingDragonEgg extends DragonEggBlock {

    public ElderWingDragonEgg(Properties properties) {
        super(properties);
    }

    @Override
    public EntityType<? extends DragonAnimal> getDragonType() {
        return ModDragons.ELDERWING_DRAGON;
    }

    @Override
    public BlockEntityType<? extends DragonEggBlockEntity> getBlockEntityType() {
        return ModDragonBlockEntities.ELDERWING_DRAGON_BE;
    }
}
