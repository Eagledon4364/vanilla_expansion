package com.chris.vanilla_expansion.block.entity;

import com.chris.vanilla_expansion.block.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class SandGeneratorBlockEntity extends BlockEntity implements ImplementedInventory {

    public SandGeneratorBlockEntity(BlockPos worldPosition, BlockState blockState) {
        super(ModBlockEntities.SANDGENERATOR_BE, worldPosition, blockState);
    }

    @Override
    public NonNullList<@NotNull ItemStack> getItems() {
        return null;
    }
}
