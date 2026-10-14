package com.chris.vanilla_expansion.block.storage.util;

import com.chris.vanilla_expansion.block.ModBlocks;
import com.chris.vanilla_expansion.block.storage.block.*;
import com.chris.vanilla_expansion.block.storage.entity.StorageCrateBlockEntity;
import net.fabricmc.fabric.api.transfer.v1.item.ItemStorage;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.storage.StorageView;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.*;

public class StorageNetworkHelper {

    /**
     * Checks if a block is part of the storage network grid (cables, controllers, trims, importers, exporters, interfaces, etc.)
     */
    public static boolean isNetworkNode(BlockState state) {
        return state.getBlock() instanceof StorageCable ||
                state.getBlock() instanceof StorageTrim ||
                state.getBlock() instanceof StorageController ||
                state.getBlock() instanceof StorageInterface ||
                state.getBlock() instanceof CraftingInterface ||
                state.getBlock() instanceof StorageImporter ||
                state.getBlock() instanceof StorageExporter ||
                state.getBlock() instanceof StorageCrateBlock;
    }

    /**
     * Scans outward from a starting position through connected network nodes to find inventories and interfaces.
     */
    public static NetworkScanResult scanNetwork(Level level, BlockPos startPos) {
        Set<BlockPos> networkNodes = new HashSet<>();
        Set<BlockPos> inventories = new HashSet<>();
        Set<BlockPos> interfaces = new HashSet<>();
        BlockPos foundController = null;

        Queue<BlockPos> queue = new ArrayDeque<>();
        Set<BlockPos> visited = new HashSet<>();

        queue.add(startPos);
        visited.add(startPos);

        while (!queue.isEmpty() && visited.size() < 512) {
            BlockPos current = queue.poll();
            BlockState currentState = level.getBlockState(current);

            if (isNetworkNode(currentState)) {
                networkNodes.add(current);
                if (currentState.getBlock() instanceof StorageController && foundController == null) {
                    foundController = current;
                }
            }

            for (Direction dir : Direction.values()) {
                BlockPos neighbor = current.relative(dir);
                if (visited.contains(neighbor)) continue;

                BlockState neighborState = level.getBlockState(neighbor);
                BlockEntity neighborBe = level.getBlockEntity(neighbor);

                // If it's a network node, continue traversing the grid wire
                if (isNetworkNode(neighborState)) {
                    visited.add(neighbor);
                    queue.add(neighbor);
                }
                // Otherwise, check if it's an inventory container touching the network
                else if (isInventory(level, neighbor, neighborBe)) {
                    visited.add(neighbor);
                    inventories.add(neighbor);
                }
            }
        }

        return new NetworkScanResult(foundController, networkNodes, inventories, interfaces);
    }

    private static boolean isInventory(Level level, BlockPos pos, BlockEntity be) {
        if (be instanceof Container) return true;
        for (Direction dir : Direction.values()) {
            if (ItemStorage.SIDED.find(level, pos, dir) != null) return true;
        }
        return false;
    }

    public static class NetworkScanResult {
        public final BlockPos controllerPos;
        public final Set<BlockPos> networkNodes;
        public final Set<BlockPos> inventories;
        public final Set<BlockPos> interfaces;

        public NetworkScanResult(BlockPos controllerPos, Set<BlockPos> networkNodes, Set<BlockPos> inventories, Set<BlockPos> interfaces) {
            this.controllerPos = controllerPos;
            this.networkNodes = networkNodes;
            this.inventories = inventories;
            this.interfaces = interfaces;
        }
    }

    /**
     * Inserts an item stack into the network, prioritizing matching filters (like locked Storage Crates).
     */
    public static ItemStack insertItemIntoNetwork(Level level, Set<BlockPos> inventories, ItemStack stack) {
        if (stack.isEmpty()) return ItemStack.EMPTY;
        ItemStack copy = stack.copy();

        // Pass 1: Prioritize matching filters / locked storage crates that already hold this item
        for (BlockPos pos : inventories) {
            BlockEntity be = level.getBlockEntity(pos);
            if (be instanceof StorageCrateBlockEntity crate && crate.isLocked()) {
                if (ItemStack.isSameItemSameComponents(crate.getLockFilter(), copy)) {
                    copy = insertIntoContainer(crate, copy);
                    if (copy.isEmpty()) return ItemStack.EMPTY;
                }
            }
        }

        // Pass 2: Insert into any valid remaining inventory / general space
        for (BlockPos pos : inventories) {
            BlockEntity be = level.getBlockEntity(pos);
            if (be instanceof StorageCrateBlockEntity crate && crate.isLocked()) continue; // already checked

            if (be instanceof Container container) {
                copy = insertIntoContainer(container, copy);
                if (copy.isEmpty()) return ItemStack.EMPTY;
            } else {
                for (Direction dir : Direction.values()) {
                    Storage<ItemVariant> storage = ItemStorage.SIDED.find(level, pos, dir);
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

    private static ItemStack insertIntoContainer(Container container, ItemStack stack) {
        for (int i = 0; i < container.getContainerSize(); i++) {
            if (!container.canPlaceItem(i, stack)) continue;
            ItemStack slotStack = container.getItem(i);

            if (!slotStack.isEmpty() && ItemStack.isSameItemSameComponents(slotStack, stack)) {
                int spaceLeft = getEffectiveMaxStackSize(container, stack, i) - slotStack.getCount();
                if (spaceLeft > 0) {
                    int insertAmount = Math.min(stack.getCount(), spaceLeft);
                    slotStack.grow(insertAmount);
                    stack.shrink(insertAmount);
                    container.setChanged();
                    if (stack.isEmpty()) return ItemStack.EMPTY;
                }
            }
        }

        for (int i = 0; i < container.getContainerSize(); i++) {
            if (!container.canPlaceItem(i, stack)) continue;
            if (container.getItem(i).isEmpty()) {
                int insertAmount = Math.min(stack.getCount(), getEffectiveMaxStackSize(container, stack, i));
                ItemStack newStack = stack.copy();
                newStack.setCount(insertAmount);
                container.setItem(i, newStack);
                stack.shrink(insertAmount);
                container.setChanged();
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
    /**
     * Extracts items matching the target stack from connected network inventories.
     */
    public static ItemStack extractItemFromNetwork(Level level, Set<BlockPos> inventories, ItemStack targetStack, int maxAmount) {
        if (targetStack.isEmpty() || maxAmount <= 0) return ItemStack.EMPTY;

        ItemStack extractedResult = ItemStack.EMPTY;

        for (BlockPos containerPos : inventories) {
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