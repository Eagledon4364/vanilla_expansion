package com.chris.vanilla_expansion.storage_api.storage;

import net.minecraft.world.item.ItemStack;

/**
 * Result of insert/extract
 * @param transferred
 * @param remainder
 */
public record StorageResult(
        long transferred,
        ItemStack remainder
)
{}