package com.chris.vanilla_expansion.storage_api.network;

import com.chris.vanilla_expansion.storage_api.api.IStorageNetwork;
import com.chris.vanilla_expansion.storage_api.api.IStorageNode;
import com.chris.vanilla_expansion.storage_api.storage.StorageEntry;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.Map;

/**
 * Aggregate all nodes
 * cache item counts
 * Fast GUI access
 */
public class StorageNetwork implements IStorageNetwork {
    Map<Item, Long> itemCache;
    List<IStorageNode> nodes;
    @Override
    public long insert(ItemStack stack, boolean simulate) {
        return 0;
    }

    @Override
    public ItemStack extract(ItemStack stack, boolean simulate) {
        return null;
    }

    @Override
    public long getStored(ItemStack stack) {
        return 0;
    }

    @Override
    public long getTotalCapacity() {
        return 0;
    }

    @Override
    public List<StorageEntry> getEntries() {
        return List.of();
    }

    @Override
    public void markDirty() {

    }
    public void rebuildCache() {

    }
}
