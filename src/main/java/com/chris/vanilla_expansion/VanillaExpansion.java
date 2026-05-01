package com.chris.vanilla_expansion;

import com.chris.vanilla_expansion.block.ModBlockEntities;
import com.chris.vanilla_expansion.block.ModBlocks;
import com.chris.vanilla_expansion.block.storage.StorageControllerBlockEntity;
import com.chris.vanilla_expansion.component.ModDataComponentTypes;
import com.chris.vanilla_expansion.entity.ModEntities;
import com.chris.vanilla_expansion.item.ModItemGroups;
import com.chris.vanilla_expansion.item.ModItems;
import com.chris.vanilla_expansion.networking.ModServerNetworking;
import com.chris.vanilla_expansion.screen.ModMenus;
import com.chris.vanilla_expansion.component.ModComponents;
import com.chris.vanilla_expansion.sound.ModSounds;
import com.chris.vanilla_expansion.util.api.StorageNetworkStorage;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.transfer.v1.item.ItemStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VanillaExpansion implements ModInitializer {
	public static final String MOD_ID = "vanilla_expansion";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
        ModServerNetworking.register();
        ModSounds.registerSounds();

        ModEntities.registerModEntities();
        ModEntities.registerAttributes();

        ModComponents.registerComponents();
        ModItems.registerModItems();
        ModItemGroups.register();
        ModDataComponentTypes.registerDataComponentTypes();

        ModItems.registerModItems();
        ModBlocks.registerModBlocks();
        ModBlockEntities.register();

        ModMenus.registerModMenus();
        ItemStorage.SIDED.registerForBlockEntities(
                (be, direction) -> new StorageNetworkStorage((StorageControllerBlockEntity) be),
                ModBlockEntities.STORAGE_CONTROLLER_BE
        );
    }

}