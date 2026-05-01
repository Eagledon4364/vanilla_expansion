package com.chris.vanilla_expansion.block.storage;

import com.chris.vanilla_expansion.util.api.StorageUnit;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StorageNetwork {

    private final List<StorageUnit> units = new ArrayList<>();

    public void addUnit(StorageUnit unit) {
        units.add(unit);
    }

    public List<StorageUnit> getUnits() {
        return units;
    }

    public ItemStack insert(ItemStack stack) {
        ItemStack remaining = stack.copy();

        for (StorageUnit unit : units) {
            long inserted = unit.insert(remaining, false);
            remaining.shrink((int) inserted);
            if (remaining.isEmpty()) return ItemStack.EMPTY;
        }

        return remaining;
    }

    public ItemStack extract(ItemStack filter, int amount) {
        ItemStack result = ItemStack.EMPTY;
        int remaining = amount;

        for (StorageUnit unit : units) {
            ItemStack extracted = unit.extract(filter, remaining, false);

            if (!extracted.isEmpty()) {
                if (result.isEmpty()) result = extracted;
                else result.grow(extracted.getCount());

                remaining -= extracted.getCount();
                if (remaining <= 0) break;
            }
        }

        return result;
    }
    public Map<ItemStack, Integer> getAggregated() {
        Map<ItemStack, Integer> map = new HashMap<>();

        for (StorageUnit unit : units) {
            if (unit instanceof StorageCrateBlockEntity crate) {
                ItemStack stored = crate.getItems().getFirst();

                if (!stored.isEmpty()) {
                    ItemStack key = stored.copyWithCount(1);

                    boolean merged = false;

                    for (ItemStack existingKey : map.keySet()) {
                        if (ItemStack.isSameItemSameComponents(existingKey, key)) {
                            map.put(existingKey, map.get(existingKey) + stored.getCount());
                            merged = true;
                            break;
                        }
                    }

                    if (!merged) {
                        map.put(key, stored.getCount());
                    }
                }
            }
        }

        return map;
    }
}