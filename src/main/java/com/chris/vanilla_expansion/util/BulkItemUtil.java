package com.chris.vanilla_expansion.util;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class BulkItemUtil {

    /**
     * Creates an ItemStack that completely ignores vanilla stack limits.
     * This is essential for bulk storage like the Storage Crate.
     */
    public static ItemStack createOverstackedStack(String item_id, int count) {
        if (item_id == null || count <= 0) {
            return ItemStack.EMPTY;
        }

        Identifier id = Identifier.parse(item_id);
        Optional<Item> item = BuiltInRegistries.ITEM.get(id).map(holder -> holder.value());

        if (item.isPresent()) {
            // STEP 1: Create a stack of 1 (this constructor has no clamping logic yet)
            ItemStack stack = new ItemStack(item.get(), 1);

            // STEP 2: Force the high count directly onto the stack.
            // This bypasses the Math.min(count, max_stack) logic used in other constructors.
            stack.setCount(count);
            return stack;
        }

        return ItemStack.EMPTY;
    }
}