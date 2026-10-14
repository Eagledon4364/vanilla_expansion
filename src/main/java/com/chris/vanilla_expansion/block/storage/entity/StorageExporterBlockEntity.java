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
    // 18 slots: 0-8 = Filters, 9-17 = Buffer slots exposed to adjacent hoppers/pipes
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

        blockEntity.tickCount++;
        if (blockEntity.tickCount % 10 != 0) return;

        StorageNetworkHelper.NetworkScanResult scan = StorageNetworkHelper.scanNetwork(level, pos);
        if (scan.controllerPos == null) return;
        BlockEntity controllerBe = level.getBlockEntity(scan.controllerPos);
        if (!(controllerBe instanceof StorageControllerBlockEntity controller)) return;

        // For each filter slot (0-8), check corresponding buffer slot (9-17)
        for (int i = 0; i < 9; i++) {
            ItemStack filter = blockEntity.getItem(i);
            if (filter.isEmpty()) continue;

            int bufferSlot = i + 9;
            ItemStack bufferStack = blockEntity.getItem(bufferSlot);

            // If buffer slot is empty or has room for the filtered item
            int maxStack = Math.min(bufferStack.getMaxStackSize(), blockEntity.getMaxStackSize());
            if (bufferStack.isEmpty() || (ItemStack.isSameItemSameComponents(bufferStack, filter) && bufferStack.getCount() < maxStack)) {
                int needed = bufferStack.isEmpty() ? Math.min(64, filter.getMaxStackSize()) : (maxStack - bufferStack.getCount());

                // Extract from network
                ItemStack extracted = controller.extractItem(filter, needed);
                if (!extracted.isEmpty()) {
                    if (bufferStack.isEmpty()) {
                        blockEntity.setItem(bufferSlot, extracted);
                    } else {
                        bufferStack.grow(extracted.getCount());
                        blockEntity.setChanged();
                    }
                    break; // One transfer per tick cycle
                }
            }
        }
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