package com.chris.vanilla_expansion.compat.jei;

import com.chris.vanilla_expansion.networking.C2SJeiRecipeTransferPayload;
import com.chris.vanilla_expansion.screen.ModMenus;
import com.chris.vanilla_expansion.screen.storage.CraftingInterfaceMenu;
import mezz.jei.api.constants.RecipeTypes;
import mezz.jei.api.gui.ingredient.IRecipeSlotView;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.transfer.IRecipeTransferError;
import mezz.jei.api.recipe.transfer.IRecipeTransferHandler;
import mezz.jei.api.recipe.transfer.IRecipeTransferHandlerHelper;
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

    private final IRecipeTransferHandlerHelper helper;

    public CraftingInterfaceJeiTransferHandler(IRecipeTransferHandlerHelper helper) {
        this.helper = helper;
    }

    @Override
    public Class<CraftingInterfaceMenu> getContainerClass() {
        return CraftingInterfaceMenu.class;
    }

    @Override
    public Optional<MenuType<CraftingInterfaceMenu>> getMenuType() {
        return Optional.of(ModMenus.CRAFTING_INTERFACE_MENU);
    }

    @Override
    public IRecipeType<RecipeHolder<CraftingRecipe>> getRecipeType() {
        return RecipeTypes.CRAFTING;
    }

    @Override
    public @Nullable IRecipeTransferError transferRecipe(
            CraftingInterfaceMenu menu,
            RecipeHolder<CraftingRecipe> recipe,
            IRecipeSlotsView recipeSlots,
            Player player,
            boolean maxTransfer,
            boolean doTransfer
    ) {
        List<IRecipeSlotView> inputSlots = recipeSlots.getSlotViews(RecipeIngredientRole.INPUT);

        List<List<ItemStack>> recipeInputs = new ArrayList<>(9);

        for (int i = 0; i < 9; i++) {
            List<ItemStack> acceptableInputs = new ArrayList<>();
            if (i < inputSlots.size()) {
                IRecipeSlotView slotView = inputSlots.get(i);

                slotView.getIngredients(mezz.jei.api.constants.VanillaTypes.ITEM_STACK)
                        .forEach(acceptableInputs::add);
            }
            recipeInputs.add(acceptableInputs);
        }

        if (doTransfer) {
            List<ItemStack> chosenGrid = new ArrayList<>();
            for (List<ItemStack> options : recipeInputs) {
                chosenGrid.add(options.isEmpty() ? ItemStack.EMPTY : options.get(0));
            }
            ClientPlayNetworking.send(new C2SJeiRecipeTransferPayload(chosenGrid, maxTransfer));
        }

        return null;
    }
}