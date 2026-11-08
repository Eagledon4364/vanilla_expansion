package com.chris.vanilla_expansion_storage;


import com.chris.vanilla_expansion_storage.block.ModStorageBlockEntities;
import com.chris.vanilla_expansion_storage.menu.ModStorageMenus;
import com.chris.vanilla_expansion_storage.menu.storage.*;
import com.chris.vanilla_expansion_storage.render.StorageCrateRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;

public class VanillaExpansionStorageClient implements ClientModInitializer {


    @Override
    public void onInitializeClient() {

        BlockEntityRenderers.register(ModStorageBlockEntities.STORAGE_CRATE_BE, StorageCrateRenderer::new);
        MenuScreens.register(ModStorageMenus.STORAGE_CRATE_MENU, StorageCrateScreen::new);

        MenuScreens.register(ModStorageMenus.STORAGE_INTERFACE_MENU, StorageInterfaceScreen::new);
        MenuScreens.register(ModStorageMenus.CRAFTING_INTERFACE_MENU, CraftingInterfaceScreen::new);

        MenuScreens.register(ModStorageMenus.STORAGE_IMPORTER_MENU, StorageImporterScreen::new);
        MenuScreens.register(ModStorageMenus.STORAGE_EXPORTER_MENU, StorageExporterScreen::new);

    }


}
