package com.chris.vanilla_expansion.recipe;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;

public record ToolCraftingRecipeInput(ItemStack head, ItemStack handle, ItemStack core) implements RecipeInput {

    @Override
    public ItemStack getItem(int index) {
        return switch (index) {
            case 0 -> this.head;
            case 1 -> this.handle;
            case 2 -> this.core;
            default -> throw new IllegalArgumentException("No item for index " + index);
        };
    }

    @Override
    public int size() {
        return 3;
    }

    @Override
    public boolean isEmpty() {
        return this.head.isEmpty() && this.handle.isEmpty() && this.core.isEmpty();
    }
}