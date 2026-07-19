package com.chris.vanilla_expansion.storage_api.storage;

import net.minecraft.world.item.ItemStack;

/**
 * Represents one item in network
 * @param stack
 * @param amount
 */
public record StorageEntry(
        ItemStack stack,
        long amount
) {

}
