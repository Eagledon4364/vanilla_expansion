package com.chris.vanilla_expansion.compat.jei;

import com.chris.vanilla_expansion.networking.C2SJeiRecipeTransferPayload;
import com.chris.vanilla_expansion.screen.storage.CraftingInterfaceMenu;
import mezz.jei.api.constants.RecipeTypes;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.transfer.IRecipeTransferError;
import mezz.jei.api.recipe.transfer.IRecipeTransferHandler;
import mezz.jei.api.recipe.types.IRecipeType;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CraftingInterfaceJeiTransferHandler implements IRecipeTransferHandler<CraftingInterfaceMenu, RecipeHolder<CraftingRecipe>> {

    @Override
    public Class<CraftingInterfaceMenu> getContainerClass() {
        return CraftingInterfaceMenu.class;
    }

    @Override
    public Optional<MenuType<CraftingInterfaceMenu>> getMenuType() {
        return Optional.empty();
    }

    @Override
    @SuppressWarnings("unchecked")
    public IRecipeType<RecipeHolder<CraftingRecipe>> getRecipeType() {
        return (IRecipeType<RecipeHolder<CraftingRecipe>>) (Object) RecipeTypes.CRAFTING;
    }

    @Override
    public @Nullable IRecipeTransferError transferRecipe(
            CraftingInterfaceMenu container,
            RecipeHolder<CraftingRecipe> recipe,
            IRecipeSlotsView recipeSlots,
            Player player,
            boolean maxTransfer,
            boolean doTransfer
    ) {
        if (doTransfer) {
            List<ItemStack> grid = new ArrayList<>(9);
            var inputSlots = recipeSlots.getSlotViews(RecipeIngredientRole.INPUT);

            for (int i = 0; i < 9; i++) {
                if (i < inputSlots.size()) {
                    var slot = inputSlots.get(i);
                    ItemStack displayed = slot.getDisplayedItemStack().orElse(ItemStack.EMPTY);
                    grid.add(displayed.copy());
                } else {
                    grid.add(ItemStack.EMPTY);
                }
            }

            ClientPlayNetworking.send(new C2SJeiRecipeTransferPayload(grid, maxTransfer));
        }

        return null;
    }
}