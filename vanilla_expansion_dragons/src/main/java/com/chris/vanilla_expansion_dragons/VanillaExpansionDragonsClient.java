package com.chris.vanilla_expansion_dragons;

import com.chris.vanilla_expansion_dragons.entity.ModDragons;
import com.chris.vanilla_expansion_dragons.entity.client.ModDragonModelLayers;
import com.chris.vanilla_expansion_dragons.entity.client.renderer.elderwing.ElderWingDragonRenderer;
import com.chris.vanilla_expansion_dragons.menu.DragonMainScreen;
import com.chris.vanilla_expansion_dragons.menu.DragonSkillScreen;
import com.chris.vanilla_expansion_dragons.menu.ModDragonMenus;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.entity.EntityRenderers;

public class VanillaExpansionDragonsClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ModDragonModelLayers.registerModelLayers();
        EntityRenderers.register(ModDragons.ELDERWING_DRAGON, ElderWingDragonRenderer::new);

        MenuScreens.register(ModDragonMenus.DRAGON_MAIN_MENU, DragonMainScreen::new);
        MenuScreens.register(ModDragonMenus.DRAGON_SKILL_MENU, DragonSkillScreen::new);

    }
}