package com.chris.vanilla_expansion.storage_api.node;

import com.chris.vanilla_expansion.storage_api.api.IStorageNode;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;

/**
 * Shared Storage Logic
 */
public class AbstractStorageNode implements IStorageNode {
    ItemStack storedItem;
    long amount;
    long capacity;

    @Override
    public long insert(ItemStack stack, boolean simulate) {
        return 0;
    }

    @Override
    public ItemStack extract(ItemStack request, boolean simulate) {
        return null;
    }

    @Override
    public long getStoredAmount() {
        return 0;
    }

    @Override
    public long getCapacity() {
        return 0;
    }

    @Override
    public ItemStack getStoredItem() {
        return null;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean canStore(ItemStack stack) {
        return false;
    }

    @Override
    public BlockPos getPosition() {
        return null;
    }
}
