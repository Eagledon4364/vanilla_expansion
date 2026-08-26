package com.chris.vanilla_expansion.block.entity;

import com.chris.vanilla_expansion.block.ModBlockEntities;
import com.chris.vanilla_expansion.block.inventory.ImplementedContainer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class ToolCraftingStationBLockEntity extends BlockEntity implements ImplementedContainer {


    public ToolCraftingStationBLockEntity(BlockPos worldPosition, BlockState blockState) {
        super(ModBlockEntities.TOOL_CRAFTING_STATION_BE, worldPosition, blockState);
    }

    @Override
    public NonNullList<@NotNull ItemStack> getItems() {
        return null;
    }
}
