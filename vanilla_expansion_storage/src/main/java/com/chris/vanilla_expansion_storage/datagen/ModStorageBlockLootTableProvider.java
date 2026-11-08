package com.chris.vanilla_expansion_storage.datagen;

import com.chris.vanilla_expansion.block.ModBlocks;
import com.chris.vanilla_expansion_storage.block.ModStorageBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootSubProvider;
import net.minecraft.core.HolderLookup;

import java.util.concurrent.CompletableFuture;

public class ModStorageBlockLootTableProvider extends FabricBlockLootSubProvider {

    public ModStorageBlockLootTableProvider(FabricPackOutput packOutput, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(packOutput, registriesFuture);
    }

    @Override
    public void generate() {

        dropSelf(ModStorageBlocks.STORAGE_CONTROLLER);
        dropSelf(ModStorageBlocks.STORAGE_INTERFACE);
        dropSelf(ModStorageBlocks.CRAFTING_INTERFACE);
        dropSelf(ModStorageBlocks.STORAGE_TRIM);
        dropSelf(ModStorageBlocks.STORAGE_CONTROLLER);
        dropSelf(ModStorageBlocks.STORAGE_CRATE);
        dropSelf(ModStorageBlocks.STORAGE_CABLE);
        dropSelf(ModStorageBlocks.STORAGE_EXPORTER);
        dropSelf(ModStorageBlocks.STORAGE_IMPORTER);
    }
}
