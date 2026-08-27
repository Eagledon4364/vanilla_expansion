package com.chris.vanilla_expansion.recipe;

import net.minecraft.core.HolderLookup;
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

public class ToolCraftingRecipe implements Recipe<ToolCraftingRecipeInput> {
    private final Ingredient toolHead;
    private final Ingredient handle;
    private final Ingredient elementCore;
    private final ItemStackTemplate result;

    public ToolCraftingRecipe(Ingredient toolHead, Ingredient handle, Ingredient elementCore, ItemStackTemplate result) {
        this.toolHead = toolHead;
        this.handle = handle;
        this.elementCore = elementCore;
        this.result = result;
    }

    public Ingredient getToolHead() { return toolHead; }
    public Ingredient getHandle() { return handle; }
    public Ingredient getElementCore() { return elementCore; }
    public ItemStackTemplate getResultTemplate() { return result; }

    @Override
    public boolean matches(ToolCraftingRecipeInput input, Level level) {
        if (level.isClientSide()) return false;

        return this.toolHead.test(input.head()) &&
                this.handle.test(input.handle()) &&
                this.elementCore.test(input.core());
    }

    @Override
    public ItemStack assemble(ToolCraftingRecipeInput input) {
        return this.result.create();
    }


    @Override
    public boolean showNotification() {
        return false;
    }

    @Override
    public String group() {
        return "";
    }

    // Inside ToolCraftingRecipe.java
    @Override
    public PlacementInfo placementInfo() {
        // Provide all three ingredients used in the recipe
        return PlacementInfo.create(List.of(this.toolHead, this.handle, this.elementCore));
    }

    @Override
    public RecipeBookCategory recipeBookCategory() {
        return null;
    }

    @Override
    public @NotNull RecipeSerializer<? extends Recipe<ToolCraftingRecipeInput>> getSerializer() {
        return ModRecipes.TOOL_CRAFTING_SERIALIZER;
    }

    @Override
    public @NotNull RecipeType<? extends Recipe<ToolCraftingRecipeInput>> getType() {
        return ModRecipes.TOOL_CRAFTING_TYPE;
    }
}