package com.chris.vanilla_expansion.datagen;

import com.chris.vanilla_expansion.block.ModBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootSubProvider;
import net.minecraft.core.HolderLookup;

import java.util.concurrent.CompletableFuture;

public class ModBlockLootTableProvider extends FabricBlockLootSubProvider {
    public ModBlockLootTableProvider(FabricPackOutput packOutput, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(packOutput, registriesFuture);
    }

    @Override
    public void generate() {
        dropSelf(ModBlocks.RED_MARKER);
        dropSelf(ModBlocks.YELLOW_MARKER);
        dropSelf(ModBlocks.GREEN_MARKER);
        dropSelf(ModBlocks.CYAN_MARKER);
        dropSelf(ModBlocks.BLUE_MARKER);
        dropSelf(ModBlocks.MAGENTA_MARKER);
        dropSelf(ModBlocks.STEEL_BLOCK);
        dropSelf(ModBlocks.SAND_GENERATOR_BLOCK);

        dropSelf(ModBlocks.TOOL_CRAFTING_STATION);

        dropSelf(ModBlocks.AIR_DRAGON_EGG);
        dropSelf(ModBlocks.EARTH_DRAGON_EGG);
        dropSelf(ModBlocks.ENERGY_DRAGON_EGG);
        dropSelf(ModBlocks.FIRE_DRAGON_EGG);
        dropSelf(ModBlocks.WATER_DRAGON_EGG);
    }
}
