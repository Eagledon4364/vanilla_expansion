package com.chris.vanilla_expansion;

import com.chris.vanilla_expansion.block.ModBlockEntities;
import com.chris.vanilla_expansion.entity.ModEntities;
import com.chris.vanilla_expansion.entity.client.ModEntityModelLayers;
import com.chris.vanilla_expansion.entity.client.render.air_dragon.AirDragonRenderer;
import com.chris.vanilla_expansion.entity.client.render.earth_dragon.EarthDragonRenderer;
import com.chris.vanilla_expansion.entity.client.render.energy_dragon.EnergyDragonRenderer;
import com.chris.vanilla_expansion.entity.client.render.fire_dragon.FireDragonRenderer;
import com.chris.vanilla_expansion.entity.client.render.gecko.GeckoRenderer;
import com.chris.vanilla_expansion.entity.client.render.water_dragon.WaterDragonRenderer;
import com.chris.vanilla_expansion.item.custom.DragonStaff;
import com.chris.vanilla_expansion.networking.DragonStaffScrollPayload;
import com.chris.vanilla_expansion.networking.ModKeybindings;
import com.chris.vanilla_expansion.render.BackpackLayer;
import com.chris.vanilla_expansion.render.StorageCrateRenderer;
import com.chris.vanilla_expansion.screen.DragonInventoryScreen;
import com.chris.vanilla_expansion.screen.ModMenus;
import com.chris.vanilla_expansion.screen.ToolCraftingStationScreen;
import com.chris.vanilla_expansion.screen.backpack.BackpackScreen;
import com.chris.vanilla_expansion.screen.storage.*;
import com.chris.vanilla_expansion.util.ModClientEvents;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.LivingEntityRenderLayerRegistrationCallback;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.player.AvatarRenderer;
import net.minecraft.world.entity.player.Player;

public class VanillaExpansionClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ModClientEvents.registerTooltipEvents();
        ModEntityModelLayers.registerModelLayers();

        EntityRenderers.register(ModEntities.ENERGY_DRAGON, EnergyDragonRenderer::new);
        EntityRenderers.register(ModEntities.AIR_DRAGON, AirDragonRenderer::new);
        EntityRenderers.register(ModEntities.FIRE_DRAGON, FireDragonRenderer::new);
        EntityRenderers.register(ModEntities.WATER_DRAGON, WaterDragonRenderer::new);
        EntityRenderers.register(ModEntities.EARTH_DRAGON, EarthDragonRenderer::new);
        EntityRenderers.register(ModEntities.GECKO, GeckoRenderer::new);

        BlockEntityRenderers.register(ModBlockEntities.STORAGE_CRATE_BE, StorageCrateRenderer::new);

        ModKeybindings.register();
        MenuScreens.register(ModMenus.BACKPACK_MENU, BackpackScreen::new);
        MenuScreens.register(ModMenus.STORAGE_CRATE_MENU, StorageCrateScreen::new);
        MenuScreens.register(ModMenus.DRAGON_INVENTORY_MENU, DragonInventoryScreen::new);
        MenuScreens.register(ModMenus.STORAGE_INTERFACE_MENU, StorageInterfaceScreen::new);
        MenuScreens.register(ModMenus.CRAFTING_INTERFACE_MENU, CraftingInterfaceScreen::new);
        MenuScreens.register(ModMenus.TOOL_CRAFTING_STATION_MENU, ToolCraftingStationScreen::new);
        MenuScreens.register(ModMenus.STORAGE_IMPORTER_MENU, StorageImporterScreen::new);
        MenuScreens.register(ModMenus.STORAGE_EXPORTER_MENU, StorageExporterScreen::new);

        LivingEntityRenderLayerRegistrationCallback.EVENT.register((entityType, entityRenderer, registrationHelper, context) -> {
            if (entityRenderer instanceof AvatarRenderer<?> playerRenderer) {
                registrationHelper.register(new BackpackLayer(playerRenderer));
            }
        });
    }

    public static boolean handleMouseScroll(double verticalScroll) {
        if (ModKeybindings.handleMouseScroll(verticalScroll)) {
            return true;
        }

        Minecraft client = Minecraft.getInstance();
        Player player = client.player;
        if (player != null && player.isSecondaryUseActive()) {
            boolean holdingStaff = player.getMainHandItem().getItem() instanceof DragonStaff
                    || player.getOffhandItem().getItem() instanceof DragonStaff;

            if (holdingStaff) {
                int direction = verticalScroll > 0 ? 1 : -1;
                ClientPlayNetworking.send(new DragonStaffScrollPayload(direction));
                return true;
            }
        }
        return false;
    }
}