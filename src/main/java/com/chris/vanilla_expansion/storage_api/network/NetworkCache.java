package com.chris.vanilla_expansion.storage_api.network;

import net.minecraft.world.item.Item;

import java.util.Map;

/**
 * Avoid scanning hundreds of drawers every tick
 */
public class NetworkCache {
    Map<Item, Long> storedItems;
    public void update() {}
    public void clear() {}
    public void getAmount() {}
}
