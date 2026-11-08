package com.chris.vanilla_expansion_storage.datagen;

import com.chris.vanilla_expansion_storage.block.ModStorageBlocks;
import com.chris.vanilla_expansion_storage.item.ModStorageItems;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.model.ModelTemplates;

public class ModStorageModelProvider extends FabricModelProvider {
    public ModStorageModelProvider(FabricPackOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockModelGenerators blockModelGenerators) {

        blockModelGenerators.createTrivialCube(ModStorageBlocks.STORAGE_CONTROLLER);
        blockModelGenerators.createTrivialCube(ModStorageBlocks.STORAGE_TRIM);
    }

    @Override
    public void generateItemModels(ItemModelGenerators itemModelGenerators) {
        itemModelGenerators.generateFlatItem(ModStorageItems.KEY, ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(ModStorageItems.STORAGE_BLOCK_UPGRADE, ModelTemplates.FLAT_ITEM);

    }
}
