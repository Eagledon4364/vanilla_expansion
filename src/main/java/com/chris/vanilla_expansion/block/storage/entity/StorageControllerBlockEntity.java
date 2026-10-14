package com.chris.vanilla_expansion.block.storage.entity;

import com.chris.vanilla_expansion.block.ModBlockEntities;
import com.chris.vanilla_expansion.block.storage.util.StorageNetworkHelper;
import net.fabricmc.fabric.api.transfer.v1.item.ItemStorage;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.storage.StorageView;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.*;

public class StorageControllerBlockEntity extends BlockEntity {
    private final Set<BlockPos> connectedTrims = new HashSet<>();
    private final Set<BlockPos> connectedInventories = new HashSet<>();
    private final Set<BlockPos> connectedInterfaces = new HashSet<>();

    public StorageControllerBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.STORAGE_CONTROLLER, pos, state);
    }

    public void clearNetwork() {
        this.connectedTrims.clear();
        this.connectedInventories.clear();
        this.connectedInterfaces.clear();
        this.setChanged();
    }

    public void scanNetwork() {
        if (this.level == null || this.level.isClientSide()) return;

        StorageNetworkHelper.NetworkScanResult result = StorageNetworkHelper.scanNetwork(this.level, this.worldPosition);

        this.connectedTrims.clear();
        this.connectedTrims.addAll(result.networkNodes);

        this.connectedInventories.clear();
        this.connectedInventories.addAll(result.inventories);

        this.connectedInterfaces.clear();
        this.connectedInterfaces.addAll(result.interfaces);

        this.setChanged();
    }

    public ItemStack insertItem(ItemStack stack) {
        return StorageNetworkHelper.insertItemIntoNetwork(this.level, this.connectedInventories, stack);
    }

    public ItemStack extractItem(ItemStack targetStack, int maxAmount) {
        return StorageNetworkHelper.extractItemFromNetwork(this.level, this.connectedInventories, targetStack, maxAmount);
    }

    public List<ItemStack> getNetworkItems() {
        List<ItemStack> combined = new ArrayList<>();
        if (this.level == null) return combined;

        for (BlockPos pos : this.connectedInventories) {
            BlockEntity be = level.getBlockEntity(pos);
            if (be == null) continue;

            if (be instanceof Container container) {
                if (container instanceof StorageCrateBlockEntity) {
                    ItemStack stack = container.getItem(0);
                    if (!stack.isEmpty()) {
                        mergeIntoList(combined, stack.copy());
                    }
                } else {
                    for (int slot = 0; slot < container.getContainerSize(); slot++) {
                        ItemStack stack = container.getItem(slot);
                        if (!stack.isEmpty()) {
                            mergeIntoList(combined, stack.copy());
                        }
                    }
                }
            } else {
                for (Direction dir : Direction.values()) {
                    Storage<ItemVariant> storage = ItemStorage.SIDED.find(level, pos, dir);
                    if (storage != null) {
                        try (Transaction tx = Transaction.openOuter()) {
                            for (StorageView<ItemVariant> view : storage) {
                                if (!view.isResourceBlank() && view.getAmount() > 0) {
                                    ItemStack stack = view.getResource().toStack((int) view.getAmount());
                                    mergeIntoList(combined, stack);
                                }
                            }
                        }
                        break;
                    }
                }
            }
        }
        return combined;
    }

    private void mergeIntoList(List<ItemStack> list, ItemStack stack) {
        for (ItemStack existing : list) {
            if (ItemStack.isSameItemSameComponents(existing, stack)) {
                existing.setCount(existing.getCount() + stack.getCount());
                return;
            }
        }
        list.add(stack);
    }

    public Set<BlockPos> getConnectedTrims() { return Collections.unmodifiableSet(connectedTrims); }
    public Set<BlockPos> getConnectedInventories() { return Collections.unmodifiableSet(connectedInventories); }
    public Set<BlockPos> getConnectedInterfaces() { return Collections.unmodifiableSet(connectedInterfaces); }
}