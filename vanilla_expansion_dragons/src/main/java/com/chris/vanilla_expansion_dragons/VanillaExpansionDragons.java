package com.chris.vanilla_expansion_dragons;


import com.chris.vanilla_expansion.recipe.ModRecipes;
import com.chris.vanilla_expansion_dragons.entity.ModDragons;
import com.chris.vanilla_expansion_dragons.menu.ModDragonMenus;
import net.fabricmc.api.ModInitializer;

public class VanillaExpansionDragons implements ModInitializer {

    public static final String MOD_ID = "vanilla_expansion_dragons";


    @Override
    public void onInitialize() {

        ModDragons.registerModEntities();
        ModDragons.registerAttributes();

        ModDragonMenus.registerModMenus();
    }
}