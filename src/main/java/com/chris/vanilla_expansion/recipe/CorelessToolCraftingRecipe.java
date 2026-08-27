package com.chris.vanilla_expansion.recipe;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.PlacementInfo;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeBookCategory;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CorelessToolCraftingRecipe implements Recipe<ToolCraftingRecipeInput> {
    private final Ingredient toolHead;
    private final Ingredient handle;
    private final ItemStackTemplate result; // Use ItemStackTemplate instead of ItemStack

    public CorelessToolCraftingRecipe(Ingredient toolHead, Ingredient handle, ItemStackTemplate result) {
        this.toolHead = toolHead;
        this.handle = handle;
        this.result = result;
    }

    public Ingredient getToolHead() { return toolHead; }
    public Ingredient getHandle() { return handle; }
    public ItemStackTemplate getResultTemplate() { return result; }

    @Override
    public ItemStack assemble(ToolCraftingRecipeInput input) {
        return this.result.create(); // Safe to execute at runtime when the player crafts!
    }
    @Override
    public boolean matches(ToolCraftingRecipeInput input, Level level) {
        if (level.isClientSide()) return false;

        // Matches head and handle, and expects the core slot to be empty
        return this.toolHead.test(input.head()) &&
                this.handle.test(input.handle()) &&
                input.core().isEmpty();
    }

    // Inside CorelessToolCraftingRecipe.java
    @Override
    public PlacementInfo placementInfo() {
        // Provide the actual ingredients used in the recipe
        return PlacementInfo.create(List.of(this.toolHead, this.handle));
    }

    @Override public boolean showNotification() { return false; }
    @Override public String group() { return ""; }
    @Override public RecipeBookCategory recipeBookCategory() { return null; }

    @Override
    public @NotNull RecipeSerializer<? extends Recipe<ToolCraftingRecipeInput>> getSerializer() {
        return ModRecipes.CORELESS_TOOL_CRAFTING_SERIALIZER;
    }

    @Override
    public @NotNull RecipeType<? extends Recipe<ToolCraftingRecipeInput>> getType() {
        return ModRecipes.CORELESS_TOOL_CRAFTING_TYPE;
    }
}