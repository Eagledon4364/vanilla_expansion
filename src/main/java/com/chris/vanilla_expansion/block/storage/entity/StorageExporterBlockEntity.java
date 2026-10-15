package com.chris.vanilla_expansion.block.storage.entity;

import com.chris.vanilla_expansion.block.ModBlockEntities;
import com.chris.vanilla_expansion.block.inventory.ImplementedInventory;
import com.chris.vanilla_expansion.block.storage.block.StorageExporter;
import com.chris.vanilla_expansion.block.storage.util.StorageNetworkHelper;
import com.chris.vanilla_expansion.screen.storage.StorageExporterMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.Nullable;

public class StorageExporterBlockEntity extends BlockEntity implements ImplementedInventory, MenuProvider {

    // Slots 0-8: Filters, Slots 9-17: Buffer items
    private final NonNullList<@NotNull ItemStack> inventory = NonNullList.withSize(18, ItemStack.EMPTY);
    private int tickCount = 0;

    public StorageExporterBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.STORAGE_EXPORTER_BE, pos, state);
    }

    @Override
    public NonNullList<@NotNull ItemStack> getItems() {
        return this.inventory;
    }

    public static void tick(Level level, BlockPos pos, BlockState state, StorageExporterBlockEntity blockEntity) {
        if (level.isClientSide()) return;
        if (level.hasNeighborSignal(pos)) return;

        blockEntity.tickCount++;
        if (blockEntity.tickCount % 10 != 0) return;

        StorageNetworkHelper.NetworkScanResult scan = StorageNetworkHelper.scanNetwork(level, pos);
        if (scan.controllerPos == null) return;
        BlockEntity controllerBe = level.getBlockEntity(scan.controllerPos);
        if (!(controllerBe instanceof StorageControllerBlockEntity controller)) return;

        // Get target container in front of the exporter's FACING direction
        Direction facing = state.getValue(StorageExporter.FACING);
        BlockPos targetPos = pos.relative(facing);
        BlockEntity targetBe = level.getBlockEntity(targetPos);
        Container targetContainer = (targetBe instanceof Container c) ? c : null;

        // 1. Push items from buffer slots (9-17) into the target container
        if (targetContainer != null) {
            for (int i = 9; i < 18; i++) {
                ItemStack bufferStack = blockEntity.getItem(i);
                if (bufferStack.isEmpty()) continue;

                ItemStack remainder = insertIntoContainer(targetContainer, bufferStack.copy());
                int moved = bufferStack.getCount() - remainder.getCount();
                if (moved > 0) {
                    bufferStack.shrink(moved);
                    blockEntity.setChanged();
                    targetContainer.setChanged();
                }
            }
        }

        // 2. Extract items matching filters (0-8) from network and push/buffer them
        for (int i = 0; i < 9; i++) {
            ItemStack filter = blockEntity.getItem(i);
            if (filter.isEmpty()) continue;

            int bufferSlot = i + 9;
            ItemStack bufferStack = blockEntity.getItem(bufferSlot);

            int maxStack = Math.min(bufferStack.getMaxStackSize(), blockEntity.getMaxStackSize());
            if (bufferStack.isEmpty() || (ItemStack.isSameItemSameComponents(bufferStack, filter) && bufferStack.getCount() < maxStack)) {
                int needed = bufferStack.isEmpty() ? Math.min(64, filter.getMaxStackSize()) : (maxStack - bufferStack.getCount());

                ItemStack extracted = controller.extractItem(filter, needed);
                if (!extracted.isEmpty()) {
                    if (targetContainer != null) {
                        ItemStack remainder = insertIntoContainer(targetContainer, extracted);
                        if (!remainder.isEmpty()) {
                            addToBuffer(blockEntity, bufferSlot, remainder);
                        }
                        targetContainer.setChanged();
                    } else {
                        addToBuffer(blockEntity, bufferSlot, extracted);
                    }
                    blockEntity.setChanged();
                    break;
                }
            }
        }
    }

    private static void addToBuffer(StorageExporterBlockEntity exporter, int bufferSlot, ItemStack stack) {
        ItemStack buffer = exporter.getItem(bufferSlot);
        if (buffer.isEmpty()) {
            exporter.setItem(bufferSlot, stack);
        } else if (ItemStack.isSameItemSameComponents(buffer, stack)) {
            buffer.grow(stack.getCount());
        }
    }

    private static ItemStack insertIntoContainer(Container container, ItemStack stack) {
        // Pass 1: Try merging into existing matching slot
        for (int i = 0; i < container.getContainerSize(); i++) {
            if (!container.canPlaceItem(i, stack)) continue;
            ItemStack slotStack = container.getItem(i);

            if (!slotStack.isEmpty() && ItemStack.isSameItemSameComponents(slotStack, stack)) {
                int effectiveMax = getEffectiveMaxStackSize(container, stack, i);
                int spaceLeft = effectiveMax - slotStack.getCount();

                if (spaceLeft > 0) {
                    int insertAmount = Math.min(stack.getCount(), spaceLeft);
                    slotStack.grow(insertAmount);
                    stack.shrink(insertAmount);
                    if (stack.isEmpty()) return ItemStack.EMPTY;
                }
            }
        }

        // Pass 2: Insert into empty slot
        for (int i = 0; i < container.getContainerSize(); i++) {
            if (!container.canPlaceItem(i, stack)) continue;
            if (container.getItem(i).isEmpty()) {
                int effectiveMax = getEffectiveMaxStackSize(container, stack, i);
                int insertAmount = Math.min(stack.getCount(), effectiveMax);
                container.setItem(i, stack.copyWithCount(insertAmount));
                stack.shrink(insertAmount);
                if (stack.isEmpty()) return ItemStack.EMPTY;
            }
        }
        return stack;
    }

    private static int getEffectiveMaxStackSize(Container container, ItemStack stack, int slot) {
        if (container instanceof StorageCrateBlockEntity crate && slot == 0) {
            return crate.getMaxStackSize();
        }
        return Math.min(container.getMaxStackSize(), stack.getMaxStackSize());
    }

    @Override
    public boolean canPlaceItem(int slot, @NotNull ItemStack stack) {
        return true;
    }

    @Override
    protected void loadAdditional(@NotNull ValueInput input) {
        super.loadAdditional(input);
        ItemContainerContents contents = input.read("Items", ItemContainerContents.CODEC).orElse(ItemContainerContents.EMPTY);
        contents.copyInto(this.inventory);
    }

    @Override
    protected void saveAdditional(@NotNull ValueOutput output) {
        super.saveAdditional(output);
        output.store("Items", ItemContainerContents.CODEC, ItemContainerContents.fromItems(this.inventory));
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.literal("Storage Exporter");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int containerId, @NotNull Inventory playerInventory, @NotNull Player player) {
        return new StorageExporterMenu(containerId, playerInventory, this);
    }
}