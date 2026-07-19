package com.chris.vanilla_expansion.storage_api.api;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
/**         Stores 1 item type
*           Holds capacity of 2048 to 16384 items
*           Handles insertion and extraction locally
*/
public interface IStorageNode {
    long insert(ItemStack stack, boolean simulate);

    ItemStack extract(ItemStack request, boolean simulate);

    long getStoredAmount();

    long getCapacity();

    ItemStack getStoredItem();

    boolean isEmpty();

    boolean canStore(ItemStack stack);

    BlockPos getPosition();
}
