package com.chris.vanilla_expansion_dragons;

import com.chris.vanilla_expansion_dragons.entity.ModDragons;
import com.chris.vanilla_expansion_dragons.entity.client.ModDragonModelLayers;
import com.chris.vanilla_expansion_dragons.entity.client.renderer.elderwing.ElderWingDragonRenderer;
import com.chris.vanilla_expansion_dragons.menu.*;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.entity.EntityRenderers;

public class VanillaExpansionDragonsClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ModDragonModelLayers.registerModelLayers();
        EntityRenderers.register(ModDragons.ELDERWING_DRAGON, ElderWingDragonRenderer::new);

        MenuScreens.register(
                ModDragonMenus.DRAGON_MAIN_MENU,
                (MenuScreens.ScreenConstructor<DragonMainMenu, DragonMainScreen>)
                        (menu, inventory, title) -> new DragonMainScreen(menu, inventory, title)
        );
        MenuScreens.register(
                ModDragonMenus.DRAGON_SKILL_MENU,
                (MenuScreens.ScreenConstructor<DragonSkillMenu, DragonSkillScreen>)
                        (menu, inventory, title) -> new DragonSkillScreen(menu, inventory, title)
        );
    }
}