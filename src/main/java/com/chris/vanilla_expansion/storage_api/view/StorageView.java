package com.chris.vanilla_expansion.storage_api.view;

import com.chris.vanilla_expansion.storage_api.api.IStorageView;
import com.chris.vanilla_expansion.storage_api.storage.StorageEntry;
import net.minecraft.world.item.ItemStack;

import java.util.List;

/**
 * CLIENT SIDE ITEM LISTING
 */
public class StorageView implements IStorageView {
    @Override
    public List<StorageEntry> getVisibleEntries() {
        return List.of();
    }

    @Override
    public long getStored(ItemStack stack) {
        return 0;
    }

    @Override
    public boolean contains(ItemStack stack) {
        return false;
    }
}
