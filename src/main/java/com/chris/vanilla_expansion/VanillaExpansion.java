package com.chris.vanilla_expansion;

import com.chris.vanilla_expansion.block.ModBlockEntities;
import com.chris.vanilla_expansion.block.ModBlocks;
import com.chris.vanilla_expansion.component.ModDataComponentTypes;
import com.chris.vanilla_expansion.entity.ModEntities;
import com.chris.vanilla_expansion.item.ModItemGroups;
import com.chris.vanilla_expansion.item.ModItems;
import com.chris.vanilla_expansion.networking.BackpackOpenPayload;
import com.chris.vanilla_expansion.networking.MagnetTogglePayload;
import com.chris.vanilla_expansion.networking.ModServerNetworking;
import com.chris.vanilla_expansion.screen.ModMenus;
import com.chris.vanilla_expansion.component.ModComponents;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VanillaExpansion implements ModInitializer {
	public static final String MOD_ID = "vanilla_expansion";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {

        PayloadTypeRegistry.clientboundConfiguration().register(MagnetTogglePayload.TYPE, MagnetTogglePayload.CODEC);
        PayloadTypeRegistry.serverboundPlay().register(MagnetTogglePayload.TYPE, MagnetTogglePayload.CODEC);


        PayloadTypeRegistry.serverboundPlay().register(BackpackOpenPayload.TYPE, BackpackOpenPayload.CODEC);

        ModServerNetworking.register();
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

    }

}