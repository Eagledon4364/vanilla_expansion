package com.chris.vanilla_expansion.storage_api.api;

import com.chris.vanilla_expansion.storage_api.storage.StorageEntry;
import net.minecraft.world.item.ItemStack;

import java.util.List;

/**
 *  Combines all storage nodes
 *  acts as single inventory
 *
 */
public interface IStorageNetwork {

    long insert(ItemStack stack, boolean simulate);

    ItemStack extract(ItemStack stack, boolean simulate);

    long getStored(ItemStack stack);

    long getTotalCapacity();

    List<StorageEntry> getEntries();

    void markDirty();
}
