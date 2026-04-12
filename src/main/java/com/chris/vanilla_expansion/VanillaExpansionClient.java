package com.chris.vanilla_expansion;


import com.chris.vanilla_expansion.entity.ModEntities;
import com.chris.vanilla_expansion.entity.client.ModEntityModelLayers;
import com.chris.vanilla_expansion.entity.client.model.EnergyDragonRenderer;
import com.chris.vanilla_expansion.networking.ModKeybindings;
import com.chris.vanilla_expansion.render.BackpackLayer;
import com.chris.vanilla_expansion.screen.DragonInventoryScreen;
import com.chris.vanilla_expansion.screen.backpack.BackpackScreen;
import com.chris.vanilla_expansion.screen.ModMenus;
import com.chris.vanilla_expansion.screen.storage.StorageCrateMenu;
import com.chris.vanilla_expansion.screen.storage.StorageCrateScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.LivingEntityRenderLayerRegistrationCallback;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.player.AvatarRenderer;

public class VanillaExpansionClient implements ClientModInitializer {



    @Override
    public void onInitializeClient() {

        ModEntityModelLayers.registerModelLayers();
        EntityRenderers.register(ModEntities.ENERGY_DRAGON, EnergyDragonRenderer::new);

        EntityRendererRegistry.register(ModEntities.ENERGY_DRAGON, EnergyDragonRenderer::new);


        ModKeybindings.register();
        // This tells Minecraft: "When the server opens BACKPACK_MENU, show the BackpackScreen."
        MenuScreens.register(ModMenus.BACKPACK_MENU, BackpackScreen::new);
        MenuScreens.register(ModMenus.STORAGE_CRATE_MENU, StorageCrateScreen::new);
        MenuScreens.register(ModMenus.DRAGON_INVENTORY_MENU, DragonInventoryScreen::new);


        LivingEntityRenderLayerRegistrationCallback.EVENT.register((entityType,
                                                                    entityRenderer,
                                                                    registrationHelper, context) -> {
            if (entityRenderer instanceof AvatarRenderer playerRenderer) {
                registrationHelper.register(new BackpackLayer(playerRenderer));
            }
        });
    }
}
