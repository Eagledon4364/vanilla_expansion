package com.chris.vanilla_expansion.storage_api.api;

import com.chris.vanilla_expansion.storage_api.storage.StorageEntry;
import net.minecraft.world.item.ItemStack;

import java.util.List;

/**
 *      GUI reads from this
 *      No LOGIC FOR INSERTION/EXTRACTION
 */
public interface IStorageView {

    List<StorageEntry> getVisibleEntries();

    long getStored(ItemStack stack);

    boolean contains(ItemStack stack);
}
