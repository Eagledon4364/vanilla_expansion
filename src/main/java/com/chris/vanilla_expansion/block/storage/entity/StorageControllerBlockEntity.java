package com.chris.vanilla_expansion.block.storage.entity;

import com.chris.vanilla_expansion.block.ModBlockEntities;
import com.chris.vanilla_expansion.block.ModBlocks;
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

    /**
     * Clears all cached positions from this controller.
     * Call this when the controller block is destroyed or removed from the world.
     */
    public void clearNetwork() {
        this.connectedTrims.clear();
        this.connectedInventories.clear();
        this.connectedInterfaces.clear();
        this.setChanged();
    }

    /**
     * Scans the network starting from this controller.
     * Call this when a Trim, Interface, or Chest is placed/broken nearby.
     */
    public void scanNetwork() {
        if (this.level == null || this.level.isClientSide()) return;

        // Clear existing mappings before rebuild
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

                // 1. Trims extend the network wire
                if (state.is(ModBlocks.STORAGE_TRIM)) {
                    visited.add(neighbor);
                    connectedTrims.add(neighbor);
                    queue.add(neighbor);
                }
                // 2. Interfaces act as terminals (connected, but don't extend wires)
                else if (state.is(ModBlocks.STORAGE_INTERFACE)) {
                    visited.add(neighbor);
                    connectedInterfaces.add(neighbor);
                }
                // 3. Any standard Container (Chests, Barrels, Crate Mods)
                else if (be instanceof Container) {
                    visited.add(neighbor);
                    connectedInventories.add(neighbor);
                }
            }
        }
        this.setChanged();
    }

    /**
     * Aggregates item counts across all connected chests into one combined list.
     */
    public List<ItemStack> getNetworkItems() {
        List<ItemStack> combined = new ArrayList<>();
        if (this.level == null) return combined;

        for (BlockPos pos : this.connectedInventories) {
            BlockEntity be = level.getBlockEntity(pos);
            if (be instanceof Container container) {
                for (int slot = 0; slot < container.getContainerSize(); slot++) {
                    ItemStack stack = container.getItem(slot);
                    if (!stack.isEmpty()) {
                        mergeIntoList(combined, stack.copy());
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

    // --- Optional Getters ---

    public Set<BlockPos> getConnectedTrims() {
        return Collections.unmodifiableSet(connectedTrims);
    }

    public Set<BlockPos> getConnectedInventories() {
        return Collections.unmodifiableSet(connectedInventories);
    }

    public Set<BlockPos> getConnectedInterfaces() {
        return Collections.unmodifiableSet(connectedInterfaces);
    }
    /**
     * Gets the actual maximum stack limit supported by a container for insertion.
     */
    private int getEffectiveMaxStackSize(Container container, ItemStack stack) {
        if (container instanceof StorageCrateBlockEntity crate) {
            return crate.getMaxStackSize(); // Directly pulls your 2048 - 16384 capacity!
        }
        // Fallback for regular chests/barrels
        return container.getMaxStackSize();
    }

    /**
     * Inserts an ItemStack into connected network containers, allowing extended limits (e.g. 2048+).
     */
    public ItemStack insertItem(ItemStack stack) {
        if (stack.isEmpty()) {
            return ItemStack.EMPTY;
        }

        ItemStack copy = stack.copy();

        for (BlockPos containerPos : this.connectedInventories) {
            if (this.level.getBlockEntity(containerPos) instanceof Container container) {

                // =========================================================================
                // PHASE 1: Try adding to existing matching stacks in the container
                // =========================================================================
                for (int i = 0; i < container.getContainerSize(); i++) {
                    ItemStack slotStack = container.getItem(i);

                    if (!slotStack.isEmpty() && ItemStack.isSameItemSameComponents(slotStack, copy)) {
                        // Pull crate/container capacity directly ignoring item's 64 limit
                        int maxContainerCapacity = getEffectiveMaxStackSize(container, copy);
                        int spaceLeft = maxContainerCapacity - slotStack.getCount();

                        if (spaceLeft > 0) {
                            int insertAmount = Math.min(copy.getCount(), spaceLeft);
                            slotStack.grow(insertAmount);
                            copy.shrink(insertAmount);
                            container.setChanged();

                            if (copy.isEmpty()) {
                                return ItemStack.EMPTY; // Fully stored!
                            }
                        }
                    }
                }

                // =========================================================================
                // PHASE 2: Try inserting into empty slots in the container
                // =========================================================================
                for (int i = 0; i < container.getContainerSize(); i++) {
                    ItemStack slotStack = container.getItem(i);

                    if (slotStack.isEmpty()) {
                        int maxContainerCapacity = getEffectiveMaxStackSize(container, copy);
                        int insertAmount = Math.min(copy.getCount(), maxContainerCapacity);

                        // Create the stored stack manually to avoid split() capped at 64
                        ItemStack newStack = copy.copy();
                        newStack.setCount(insertAmount);

                        container.setItem(i, newStack);
                        copy.shrink(insertAmount);
                        container.setChanged();

                        if (copy.isEmpty()) {
                            return ItemStack.EMPTY; // Fully stored!
                        }
                    }
                }
            }
        }

        return copy;
    }
    /**
     * Extracts items matching targetStack from connected containers up to maxAmount.
     *
     * @param targetStack Item type to extract.
     * @param maxAmount   Maximum number of items to pull.
     * @return The extracted ItemStack.
     */
    public ItemStack extractItem(ItemStack targetStack, int maxAmount) {
        if (targetStack.isEmpty() || maxAmount <= 0) {
            return ItemStack.EMPTY;
        }

        ItemStack extractedResult = ItemStack.EMPTY;

        for (BlockPos containerPos : this.connectedInventories) {
            if (this.level.getBlockEntity(containerPos) instanceof Container container) {

                for (int i = 0; i < container.getContainerSize(); i++) {
                    ItemStack slotStack = container.getItem(i);

                    if (!slotStack.isEmpty() && ItemStack.isSameItemSameComponents(slotStack, targetStack)) {
                        int toExtract = Math.min(maxAmount - extractedResult.getCount(), slotStack.getCount());

                        if (extractedResult.isEmpty()) {
                            extractedResult = slotStack.split(toExtract);
                        } else {
                            extractedResult.grow(toExtract);
                            slotStack.shrink(toExtract);
                        }

                        container.setChanged();

                        if (extractedResult.getCount() >= maxAmount) {
                            return extractedResult; // Reached requested extraction amount
                        }
                    }
                }
            }
        }

        return extractedResult;
    }
}