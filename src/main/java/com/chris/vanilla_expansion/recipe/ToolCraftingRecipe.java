package com.chris.vanilla_expansion.recipe;

import com.chris.vanilla_expansion.component.CoreAffinityComponent;
import com.chris.vanilla_expansion.component.CoreAffinityComponent.Affinity;
import com.chris.vanilla_expansion.item.ModItems; // Replace with your actual ModItems class import
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
        ItemStack output = this.result.create();
        ItemStack coreStack = input.core();

        // Determine affinity from the core item placed in the input
        Affinity affinity = null;

        if (coreStack.is(ModItems.FIRE_CORE)) {
            affinity = Affinity.FIRE;
        } else if (coreStack.is(ModItems.EARTH_CORE)) {
            affinity = Affinity.EARTH;
        } else if (coreStack.is(ModItems.WATER_CORE)) {
            affinity = Affinity.WATER;
        } else if (coreStack.is(ModItems.AIR_CORE)) {
            affinity = Affinity.AIR;
        } else if (coreStack.is(ModItems.ENERGY_CORE)) {
            affinity = Affinity.ENERGY;
        }

        // Apply component if a matching affinity was found
        if (affinity != null) {
            output.set(CoreAffinityComponent.KEY, new CoreAffinityComponent(affinity));
        }

        return output;
    }

    @Override
    public boolean showNotification() {
        return false;
    }

    @Override
    public String group() {
        return "";
    }

    @Override
    public PlacementInfo placementInfo() {
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