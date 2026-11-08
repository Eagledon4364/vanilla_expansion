package com.chris.vanilla_expansion_storage.datagen;

import com.chris.vanilla_expansion.block.ModBlocks;
import com.chris.vanilla_expansion_storage.block.ModStorageBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.BlockTags;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class ModStorageBlockTagProvider extends FabricTagsProvider.BlockTagsProvider {

    public ModStorageBlockTagProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registryLookupFuture) {
        super(output, registryLookupFuture);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider registries) {

        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(ModBlocks.getRK(ModStorageBlocks.STORAGE_CRATE))
                .add(ModBlocks.getRK(ModStorageBlocks.STORAGE_TRIM))
                .add(ModBlocks.getRK(ModStorageBlocks.STORAGE_INTERFACE))
                .add(ModBlocks.getRK(ModStorageBlocks.CRAFTING_INTERFACE))
                .add(ModBlocks.getRK(ModStorageBlocks.STORAGE_CONTROLLER))
                .add(ModBlocks.getRK(ModStorageBlocks.STORAGE_CABLE))
                .add(ModBlocks.getRK(ModStorageBlocks.STORAGE_EXPORTER))
                .add(ModBlocks.getRK(ModStorageBlocks.STORAGE_IMPORTER));

    }
}