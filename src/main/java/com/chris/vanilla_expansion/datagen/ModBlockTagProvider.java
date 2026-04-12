package com.chris.vanilla_expansion.datagen;

import com.chris.vanilla_expansion.block.ModBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceKey;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends FabricTagsProvider<FabricTagsProvider.@NotNull BlockTagsProvider> {
    public ModBlockTagProvider(FabricPackOutput output, ResourceKey registryKey, CompletableFuture registryLookupFuture) {
        super(output, registryKey, registryLookupFuture);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {

    }
}
