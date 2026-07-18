package com.chris.vanilla_expansion.block.entity;

import com.chris.vanilla_expansion.block.ModBlockEntities;
import com.chris.vanilla_expansion.block.SandGeneratorBlock;
import com.chris.vanilla_expansion.block.inventory.ImplementedContainer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SandGeneratorBlockEntity extends BlockEntity implements ImplementedContainer, WorldlyContainer {
    private final NonNullList<@NotNull ItemStack> items = NonNullList.withSize(3, ItemStack.EMPTY);
    private int timeSinceDropped = 0;
    private static final int[] INPUT_SLOTS = {0, 1};
    private static final int[] OUTPUT_SLOT = {2};


    public SandGeneratorBlockEntity(BlockPos worldPosition, BlockState blockState) {
        super(ModBlockEntities.SANDGENERATOR_BE, worldPosition, blockState);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, SandGeneratorBlockEntity be) {

        if (!state.getValue(SandGeneratorBlock.POWERED)) {
            be.timeSinceDropped = 0;
            return;
        }

        if (!be.canCraft()) {
            be.timeSinceDropped = 0;
            return;
        }

        be.timeSinceDropped++;

        if (be.timeSinceDropped < 10)
            return;

        be.timeSinceDropped = 0;

        ItemStack sand = be.getItem(0);
        ItemStack gravel = be.getItem(1);

        ItemStack result = new ItemStack(
                sand.is(Items.RED_SAND) ? Items.RED_SAND : Items.SAND,
                2
        );

        ItemStack output = be.getItem(2);

        if (!output.isEmpty()) {

            if (!ItemStack.isSameItemSameComponents(output, result))
                return;

            if (output.getCount() + result.getCount() > output.getMaxStackSize())
                return;
        }

        sand.shrink(1);
        gravel.shrink(1);

        if (sand.isEmpty())
            be.setItem(0, ItemStack.EMPTY);

        if (gravel.isEmpty())
            be.setItem(1, ItemStack.EMPTY);

        if (output.isEmpty()) {
            be.setItem(2, result);
        } else {
            output.grow(result.getCount());
        }

        be.setChanged();
    }

    public boolean canCraft() {

        ItemStack sand = getItem(0);
        ItemStack gravel = getItem(1);
        ItemStack output = getItem(2);

        if (sand.isEmpty() || gravel.isEmpty())
            return false;

        if (!(sand.is(Items.SAND) || sand.is(Items.RED_SAND)))
            return false;

        if (!gravel.is(Items.GRAVEL))
            return false;

        ItemStack result = new ItemStack(
                sand.is(Items.RED_SAND) ? Items.RED_SAND : Items.SAND,
                2
        );

        if (output.isEmpty())
            return true;

        return ItemStack.isSameItemSameComponents(output, result)
                && output.getCount() + result.getCount() <= output.getMaxStackSize();
    }

    @Override
    public NonNullList<@NotNull ItemStack> getItems() {
        return this.items;
    }
    @Override
    protected void loadAdditional(@NotNull ValueInput input) {
        super.loadAdditional(input);
        ContainerHelper.loadAllItems(input, this.items);
    }

    @Override
    protected void saveAdditional(@NotNull ValueOutput output) {
        ContainerHelper.saveAllItems(output, this.items);
        super.saveAdditional(output);
    }
    @Override
    public int @NotNull [] getSlotsForFace(@NotNull Direction direction) {
        return direction == Direction.DOWN ? OUTPUT_SLOT : INPUT_SLOTS;
    }

    @Override
    public boolean canPlaceItemThroughFace(int slot, @NotNull ItemStack stack, @Nullable Direction direction) {

        return switch (slot) {
            case 0 -> stack.is(Items.SAND) || stack.is(Items.RED_SAND);
            case 1 -> stack.is(Items.GRAVEL);
            default -> false;
        };
    }

    @Override
    public boolean canTakeItemThroughFace(int slot, @NotNull ItemStack stack, @NotNull Direction direction) {
        return direction == Direction.DOWN && slot == 2;
    }
}
