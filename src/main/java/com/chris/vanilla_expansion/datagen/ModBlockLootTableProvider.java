package com.chris.vanilla_expansion.datagen;

import com.chris.vanilla_expansion.block.ModBlocks;
import net.fabricmc.fabric.api.block.v1.FabricBlock;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootSubProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;

import java.util.concurrent.CompletableFuture;

public class ModBlockLootTableProvider extends FabricBlockLootSubProvider {
    public ModBlockLootTableProvider(FabricPackOutput packOutput, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(packOutput, registriesFuture);
    }

    @Override
    public void generate() {
        var enchantmenhts = registries.lookupOrThrow(Registries.ENCHANTMENT);
        dropSelf(ModBlocks.RED_MARKER);
        dropSelf(ModBlocks.YELLOW_MARKER);
        dropSelf(ModBlocks.GREEN_MARKER);
        dropSelf(ModBlocks.CYAN_MARKER);
        dropSelf(ModBlocks.BLUE_MARKER);
        dropSelf(ModBlocks.MAGENTA_MARKER);
        dropSelf(ModBlocks.STEEL_BLOCK);
        dropSelf(ModBlocks.SAND_GENERATOR_BLOCK);

        dropSelf(ModBlocks.STORAGE_CONTROLLER);
        dropSelf(ModBlocks.STORAGE_INTERFACE);
        dropSelf(ModBlocks.STORAGE_TRIM);
        dropSelf(ModBlocks.STORAGE_CONTROLLER);
        dropSelf(ModBlocks.STORAGE_CRATE);
    }
}
