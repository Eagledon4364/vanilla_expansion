package com.chris.vanilla_expansion_storage;

import com.chris.vanilla_expansion.event.ModLootTableEvents;
import com.chris.vanilla_expansion.screen.ModMenus;
import com.chris.vanilla_expansion_storage.block.ModStorageBlockEntities;
import com.chris.vanilla_expansion_storage.block.ModStorageBlocks;
import com.chris.vanilla_expansion_storage.item.ModStorageItemGroups;
import com.chris.vanilla_expansion_storage.item.ModStorageItems;
import com.chris.vanilla_expansion_storage.menu.ModStorageMenus;
import com.chris.vanilla_expansion_storage.networking.ModStorageServerNetworking;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VanillaExpansionStorage implements ModInitializer {

    public static final String MOD_ID = "vanilla_expansion_storage";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("Initializing {}", MOD_ID);
        ModStorageServerNetworking.register();
        ModStorageBlocks.registerBlocks();
        ModStorageItems.registerModItems();

        ModStorageBlockEntities.register();
        ModStorageMenus.registerModMenus();

        ModStorageItemGroups.register();

        ModLootTableEvents.registerEvents();

    }

}
