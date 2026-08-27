package com.chris.vanilla_expansion.datagen;

import com.chris.vanilla_expansion.block.ModBlocks;
import com.chris.vanilla_expansion.util.ModTags;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.BlockTags;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends FabricTagsProvider.BlockTagsProvider {

    public ModBlockTagProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registryLookupFuture) {
        super(output, registryLookupFuture);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider registries) {

        tag(ModTags.Blocks.PAXEL_MINEABLE)
                .forceAddTag(BlockTags.MINEABLE_WITH_AXE)
                .forceAddTag(BlockTags.MINEABLE_WITH_PICKAXE)
                .forceAddTag(BlockTags.MINEABLE_WITH_SHOVEL);

        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(ModBlocks.getRK(ModBlocks.RED_MARKER))
                .add(ModBlocks.getRK(ModBlocks.YELLOW_MARKER))
                .add(ModBlocks.getRK(ModBlocks.GREEN_MARKER))
                .add(ModBlocks.getRK(ModBlocks.CYAN_MARKER))
                .add(ModBlocks.getRK(ModBlocks.BLUE_MARKER))
                .add(ModBlocks.getRK(ModBlocks.MAGENTA_MARKER))

                .add(ModBlocks.getRK(ModBlocks.STORAGE_CRATE))
                .add(ModBlocks.getRK(ModBlocks.STORAGE_TRIM))
                .add(ModBlocks.getRK(ModBlocks.STORAGE_INTERFACE))
                .add(ModBlocks.getRK(ModBlocks.CRAFTING_INTERFACE))
                .add(ModBlocks.getRK(ModBlocks.STORAGE_CONTROLLER))

                .add(ModBlocks.getRK(ModBlocks.SAND_GENERATOR_BLOCK))
                .add(ModBlocks.getRK(ModBlocks.TOOL_CRAFTING_STATION))
                .add(ModBlocks.getRK(ModBlocks.STEEL_BLOCK));

    }
}
