package com.chris.vanilla_expansion.util.api;

import com.chris.vanilla_expansion.block.storage.StorageControllerBlockEntity;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.storage.StorageView;
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class StorageNetworkStorage implements Storage<ItemVariant> {

    private final StorageControllerBlockEntity controller;

    public StorageNetworkStorage(StorageControllerBlockEntity controller) {
        this.controller = controller;
    }

    @Override
    public long insert(ItemVariant resource, long maxAmount, TransactionContext transaction) {
        ItemStack stack = resource.toStack((int) maxAmount);
        ItemStack remaining = controller.insertItem(stack);

        return maxAmount - remaining.getCount();
    }

    @Override
    public long extract(ItemVariant resource, long maxAmount, TransactionContext transaction) {
        ItemStack extracted = controller.extractItem(resource.toStack(1), (int) maxAmount);
        return extracted.getCount();
    }

    @Override
    public Iterator<StorageView<ItemVariant>> iterator() {
        Map<ItemStack, Integer> aggregated = controller.getNetwork().getAggregated();

        List<StorageView<ItemVariant>> views = new ArrayList<>();

        for (var entry : aggregated.entrySet()) {
            ItemVariant variant = ItemVariant.of(entry.getKey());

            views.add(new StorageView<>() {
                @Override
                public ItemVariant getResource() {
                    return variant;
                }

                @Override
                public long getAmount() {
                    return entry.getValue();
                }

                @Override
                public long getCapacity() {
                    return Long.MAX_VALUE;
                }

                @Override
                public long extract(ItemVariant resource, long maxAmount, TransactionContext transaction) {
                    return 0;
                }

                @Override
                public boolean isResourceBlank() {
                    return false;
                }
            });
        }

        return views.iterator();
    }
}