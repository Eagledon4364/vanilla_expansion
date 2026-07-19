package com.chris.vanilla_expansion.storage_api.storage;

import net.minecraft.world.item.ItemStack;

/**
 * FUTURE COMPLETEABLE
 * @param stack
 * @param amount
 * @param simulated
 */
public record StorageTransaction(
        ItemStack stack,
        long amount,
        boolean simulated
)
{}