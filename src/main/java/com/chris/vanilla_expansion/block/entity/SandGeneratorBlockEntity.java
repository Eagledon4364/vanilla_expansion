package com.chris.vanilla_expansion.block.entity;

import com.chris.vanilla_expansion.block.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jetbrains.annotations.NotNull;

public class SandGeneratorBlockEntity extends BlockEntity implements ImplementedContainer {
    private final NonNullList<@NotNull ItemStack> items = NonNullList.withSize(1, ItemStack.EMPTY);
    private int timeSinceDropped = 0;

    public SandGeneratorBlockEntity(BlockPos worldPosition, BlockState blockState) {
        super(ModBlockEntities.SANDGENERATOR_BE, worldPosition, blockState);
    }

    public static void tick(Level world, BlockPos blockPos, BlockState blockState, SandGeneratorBlockEntity sandGeneratorBlockEntity) {
        if (sandGeneratorBlockEntity.isEmpty()) return;
        sandGeneratorBlockEntity.timeSinceDropped++;

        if (sandGeneratorBlockEntity.timeSinceDropped < 10) return;
        sandGeneratorBlockEntity.timeSinceDropped = 0;

        ItemStack duplicate = sandGeneratorBlockEntity.getItem(0).split(1);

        Block.popResourceFromFace(world, blockPos, Direction.UP, duplicate);
        Block.popResourceFromFace(world, blockPos, Direction.UP, duplicate);
    }
    @Override
    public NonNullList<ItemStack> getItems() {
        return this.items;
    }
    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        ContainerHelper.loadAllItems(input, this.items);
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        ContainerHelper.saveAllItems(output, this.items);
        super.saveAdditional(output);
    }

}
