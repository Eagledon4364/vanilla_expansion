package com.chris.vanilla_expansion.block.storage.entity;

import com.chris.vanilla_expansion.block.ModBlockEntities;
import com.chris.vanilla_expansion.block.ModBlocks;
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

        this.connectedTrims.clear();
        this.connectedInventories.clear();
        this.connectedInterfaces.clear();

        Queue<BlockPos> queue = new LinkedList<>();
        Set<BlockPos> visited = new HashSet<>();

        queue.add(this.worldPosition);
        visited.add(this.worldPosition);

        while (!queue.isEmpty()) {
            BlockPos current = queue.poll();

            for (Direction dir : Direction.values()) {
                BlockPos neighbor = current.relative(dir);
                if (visited.contains(neighbor)) continue;

                BlockState state = level.getBlockState(neighbor);
                BlockEntity be = level.getBlockEntity(neighbor);

                if (state.is(ModBlocks.STORAGE_TRIM)) {
                    visited.add(neighbor);
                    connectedTrims.add(neighbor);
                    queue.add(neighbor);
                } else if (state.is(ModBlocks.STORAGE_INTERFACE)) {
                    visited.add(neighbor);
                    connectedInterfaces.add(neighbor);
                }
                // Check standard Container OR Fabric ItemStorage lookup for mods like Storage Drawers
                else {
                    boolean hasItemStorage = false;
                    if (be instanceof Container) {
                        hasItemStorage = true;
                    } else {
                        // Check if any side exposes an ItemStorage
                        for (Direction checkDir : Direction.values()) {
                            if (ItemStorage.SIDED.find(level, neighbor, checkDir) != null) {
                                hasItemStorage = true;
                                break;
                            }
                        }
                    }

                    if (hasItemStorage) {
                        visited.add(neighbor);
                        connectedInventories.add(neighbor);
                    }
                }
            }
        }
        this.setChanged();
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

    public ItemStack insertItem(ItemStack stack) {
        if (stack.isEmpty()) return ItemStack.EMPTY;
        ItemStack copy = stack.copy();

        for (BlockPos containerPos : this.connectedInventories) {
            BlockEntity be = level.getBlockEntity(containerPos);
            if (be == null) continue;

            if (be instanceof Container container) {
                for (int i = 0; i < container.getContainerSize(); i++) {
                    if (!container.canPlaceItem(i, copy)) continue;
                    ItemStack slotStack = container.getItem(i);

                    if (!slotStack.isEmpty() && ItemStack.isSameItemSameComponents(slotStack, copy)) {
                        int spaceLeft = getEffectiveMaxStackSize(container, copy, i) - slotStack.getCount();
                        if (spaceLeft > 0) {
                            int insertAmount = Math.min(copy.getCount(), spaceLeft);
                            slotStack.grow(insertAmount);
                            copy.shrink(insertAmount);
                            container.setChanged();
                            if (copy.isEmpty()) return ItemStack.EMPTY;
                        }
                    }
                }

                for (int i = 0; i < container.getContainerSize(); i++) {
                    if (!container.canPlaceItem(i, copy)) continue;
                    if (container.getItem(i).isEmpty()) {
                        int insertAmount = Math.min(copy.getCount(), getEffectiveMaxStackSize(container, copy, i));
                        ItemStack newStack = copy.copy();
                        newStack.setCount(insertAmount);
                        container.setItem(i, newStack);
                        copy.shrink(insertAmount);
                        container.setChanged();
                        if (copy.isEmpty()) return ItemStack.EMPTY;
                    }
                }
            } else {
                for (Direction dir : Direction.values()) {
                    Storage<ItemVariant> storage = ItemStorage.SIDED.find(level, containerPos, dir);
                    if (storage != null) {
                        try (Transaction tx = Transaction.openOuter()) {
                            long inserted = storage.insert(ItemVariant.of(copy), copy.getCount(), tx);
                            if (inserted > 0) {
                                tx.commit();
                                copy.shrink((int) inserted);
                                if (copy.isEmpty()) return ItemStack.EMPTY;
                            }
                        }
                        break;
                    }
                }
            }
        }
        return copy;
    }

    private int getEffectiveMaxStackSize(Container container, ItemStack stack, int slot) {
        if (container instanceof StorageCrateBlockEntity crate && slot == 0) {
            return crate.getMaxStackSize();
        }
        return Math.min(container.getMaxStackSize(), stack.getMaxStackSize());
    }

    public ItemStack extractItem(ItemStack targetStack, int maxAmount) {
        if (targetStack.isEmpty() || maxAmount <= 0) return ItemStack.EMPTY;

        ItemStack extractedResult = ItemStack.EMPTY;

        for (BlockPos containerPos : this.connectedInventories) {
            BlockEntity be = level.getBlockEntity(containerPos);
            if (be == null) continue;

            if (be instanceof Container container) {
                int maxSlotsToScan = (container instanceof StorageCrateBlockEntity) ? 1 : container.getContainerSize();

                for (int i = 0; i < maxSlotsToScan; i++) {
                    ItemStack slotStack = container.getItem(i);
                    if (!slotStack.isEmpty() && ItemStack.isSameItemSameComponents(slotStack, targetStack)) {
                        int needed = maxAmount - extractedResult.getCount();
                        int toExtract = Math.min(needed, slotStack.getCount());

                        if (extractedResult.isEmpty()) {
                            extractedResult = slotStack.split(toExtract);
                        } else {
                            extractedResult.grow(toExtract);
                            slotStack.shrink(toExtract);
                        }

                        container.setChanged();
                        if (extractedResult.getCount() >= maxAmount) return extractedResult;
                    }
                }
            } else {
                for (Direction dir : Direction.values()) {
                    Storage<ItemVariant> storage = ItemStorage.SIDED.find(level, containerPos, dir);
                    if (storage != null) {
                        try (Transaction tx = Transaction.openOuter()) {
                            long needed = maxAmount - extractedResult.getCount();
                            ItemVariant variant = ItemVariant.of(targetStack);

                            long extracted = storage.extract(variant, needed, tx);
                            if (extracted > 0) {
                                tx.commit();
                                ItemStack pulledStack = variant.toStack((int) extracted);
                                if (extractedResult.isEmpty()) {
                                    extractedResult = pulledStack;
                                } else {
                                    extractedResult.grow(pulledStack.getCount());
                                }

                                if (extractedResult.getCount() >= maxAmount) return extractedResult;
                            }
                        }
                        break;
                    }
                }
            }
        }
        return extractedResult;
    }
}