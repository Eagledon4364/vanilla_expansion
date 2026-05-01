package com.chris.vanilla_expansion.util.api;

import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.minecraft.world.item.ItemStack;

import java.util.Collection;
import java.util.Map;

public interface StorageNetwork {
    void addNode(StorageNode node);
    void removeNode(StorageNode node);

    Collection<StorageUnit> getStorageUnits();

    long insert(ItemStack stack, boolean simulate);
    ItemStack extract(ItemStack filter, long amount, boolean simulate);

    Map<ItemVariant, Long> getAggregatedStorage();
}