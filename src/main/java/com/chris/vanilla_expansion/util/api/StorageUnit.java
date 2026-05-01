package com.chris.vanilla_expansion.util.api;

import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.minecraft.world.item.ItemStack;

import java.util.Set;

public interface StorageUnit {
    long insert(ItemStack stack, boolean simulate);
    ItemStack extract(ItemStack filter, long amount, boolean simulate);

    long getStoredAmount(ItemVariant variant);
    long getCapacity(ItemVariant variant);

    Set<ItemVariant> getStoredTypes();
}