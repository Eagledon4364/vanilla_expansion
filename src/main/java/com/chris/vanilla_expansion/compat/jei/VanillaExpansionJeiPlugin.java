package com.chris.vanilla_expansion.compat.jei;

import com.chris.vanilla_expansion.VanillaExpansion;
import com.chris.vanilla_expansion.screen.ModMenus;
import com.chris.vanilla_expansion.screen.storage.CraftingInterfaceMenu;
import com.chris.vanilla_expansion.screen.storage.CraftingInterfaceScreen;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.RecipeTypes;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeTransferRegistration;
import net.minecraft.resources.Identifier;


@JeiPlugin
public class VanillaExpansionJeiPlugin implements IModPlugin {

    @Override
    public Identifier getPluginUid() {
        return Identifier.fromNamespaceAndPath(VanillaExpansion.MOD_ID, "jei_plugin");
    }
    @Override
    public void registerRecipeTransferHandlers(IRecipeTransferRegistration registration) {
        registration.addRecipeTransferHandler(
                new CraftingInterfaceJeiTransferHandler(registration.getTransferHelper()),
                RecipeTypes.CRAFTING
        );
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        registration.addRecipeClickArea(
                CraftingInterfaceScreen.class,
                85, 128, 30, 20,
                RecipeTypes.CRAFTING
        );
    }
}