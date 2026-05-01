package com.chris.vanilla_expansion.util.api;

import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;

public interface StorageTransfer {
    long insert(ItemVariant variant, long amount, boolean simulate);
    long extract(ItemVariant variant, long amount, boolean simulate);
}