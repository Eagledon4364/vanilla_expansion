package com.chris.vanilla_expansion;

import com.chris.vanilla_expansion.entity.ModEntities;
import com.chris.vanilla_expansion.entity.client.ModEntityModelLayers;
import com.chris.vanilla_expansion.entity.client.render.gecko.GeckoRenderer;
import com.chris.vanilla_expansion.networking.ModKeybindings;
import com.chris.vanilla_expansion.render.BackpackLayer;
import com.chris.vanilla_expansion.screen.ModMenus;
import com.chris.vanilla_expansion.screen.ToolCraftingStationScreen;
import com.chris.vanilla_expansion.screen.backpack.BackpackScreen;
import com.chris.vanilla_expansion.util.ModClientEvents;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.LivingEntityRenderLayerRegistrationCallback;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.player.AvatarRenderer;
import net.minecraft.world.entity.player.Player;

public class VanillaExpansionClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ModClientEvents.registerTooltipEvents();
        ModEntityModelLayers.registerModelLayers();
        EntityRenderers.register(ModEntities.GECKO, GeckoRenderer::new);

        ModKeybindings.register();
        MenuScreens.register(ModMenus.BACKPACK_MENU, BackpackScreen::new);
        MenuScreens.register(ModMenus.TOOL_CRAFTING_STATION_MENU, ToolCraftingStationScreen::new);

        LivingEntityRenderLayerRegistrationCallback.EVENT.register((entityType, entityRenderer, registrationHelper, context) -> {
            if (entityRenderer instanceof AvatarRenderer<?> playerRenderer) {
                registrationHelper.register(new BackpackLayer(playerRenderer));
            }
        });
    }

}