package com.chris.vanilla_expansion.block.storage.util;

import com.chris.vanilla_expansion.block.storage.block.*;
import com.chris.vanilla_expansion.block.storage.entity.StorageCrateBlockEntity;
import com.chris.vanilla_expansion.block.storage.entity.StorageExporterBlockEntity;
import net.fabricmc.fabric.api.transfer.v1.item.ItemStorage;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
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

    public static NetworkScanResult scanNetwork(Level level, BlockPos startPos) {
        Set<BlockPos> networkNodes = new HashSet<>();
        Set<BlockPos> inventories = new HashSet<>();
        Set<BlockPos> interfaces = new HashSet<>();
        BlockPos foundController = null;

        Queue<BlockPos> queue = new ArrayDeque<>();
        Set<BlockPos> visited = new HashSet<>();

        BlockState startState = level.getBlockState(startPos);
        if (startState.getBlock() instanceof StorageController) {
            foundController = startPos;
        }

        queue.add(startPos);
        visited.add(startPos);

        while (!queue.isEmpty() && visited.size() < 512) {
            BlockPos current = queue.poll();
            BlockState currentState = level.getBlockState(current);
            BlockEntity currentBe = level.getBlockEntity(current);

            if (isNetworkNode(currentState)) {
                networkNodes.add(current);
                if (currentState.getBlock() instanceof StorageController && foundController == null) {
                    foundController = current;
                }

                if (!isImporterOrExporter(currentState) && isInventory(level, current, currentBe)) {
                    if (!isImporterOrExporterTarget(level, current)) {
                        inventories.add(current);
                    }
                }
            }

            for (Direction dir : Direction.values()) {
                BlockPos neighbor = current.relative(dir);
                BlockState neighborState = level.getBlockState(neighbor);
                BlockEntity neighborBe = level.getBlockEntity(neighbor);

                if (isNetworkNode(neighborState)) {
                    if (!visited.contains(neighbor)) {
                        visited.add(neighbor);
                        queue.add(neighbor);
                    }
                }

                if (isInventory(level, neighbor, neighborBe)) {
                    if (!isImporterOrExporter(neighborState) && !isImporterOrExporterTarget(level, neighbor)) {
                        inventories.add(neighbor);
                    }
                }
            }
        }

        return new NetworkScanResult(foundController, networkNodes, inventories, interfaces);
    }

    private static boolean isImporterOrExporter(BlockState state) {
        return state.getBlock() instanceof StorageImporter || state.getBlock() instanceof StorageExporter;
    }

    private static boolean isImporterOrExporterTarget(Level level, BlockPos pos) {
        for (Direction dir : Direction.values()) {
            BlockPos nodePos = pos.relative(dir);
            BlockState nodeState = level.getBlockState(nodePos);

            if (nodeState.getBlock() instanceof StorageImporter) {
                if (nodeState.getValue(StorageImporter.FACING) == dir.getOpposite()) {
                    return true;
                }
            }
            if (nodeState.getBlock() instanceof StorageExporter) {
                if (nodeState.getValue(StorageExporter.FACING) == dir.getOpposite()) {
                    return true;
                }
            }
        }
        return false;
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

    public static ItemStack insertItemIntoNetwork(Level level, Set<BlockPos> inventories, ItemStack stack) {
        if (stack.isEmpty()) return ItemStack.EMPTY;
        ItemStack copy = stack.copy();

        for (BlockPos pos : inventories) {
            BlockEntity be = level.getBlockEntity(pos);
            if (be instanceof StorageCrateBlockEntity crate && crate.isLocked()) {
                if (ItemStack.isSameItemSameComponents(crate.getLockFilter(), copy)) {
                    copy = insertIntoContainer(crate, copy);
                    if (copy.isEmpty()) return ItemStack.EMPTY;
                }
            }
        }

        for (BlockPos pos : inventories) {
            BlockEntity be = level.getBlockEntity(pos);
            if (be instanceof StorageCrateBlockEntity crate && crate.isLocked()) continue;

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
                int effectiveMax = getEffectiveMaxStackSize(container, stack, i);
                int spaceLeft = effectiveMax - slotStack.getCount();

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
                int effectiveMax = getEffectiveMaxStackSize(container, stack, i);
                int insertAmount = Math.min(stack.getCount(), effectiveMax);

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