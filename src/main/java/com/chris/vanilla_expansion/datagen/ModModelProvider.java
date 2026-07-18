package com.chris.vanilla_expansion.datagen;

import com.chris.vanilla_expansion.block.ModBlocks;
import com.chris.vanilla_expansion.item.ModItems;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.model.ModelTemplate;
import net.minecraft.client.data.models.model.ModelTemplates;

public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricPackOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockModelGenerators blockModelGenerators) {
        blockModelGenerators.createTrivialCube(ModBlocks.RED_MARKER);
        blockModelGenerators.createTrivialCube(ModBlocks.YELLOW_MARKER);
        blockModelGenerators.createTrivialCube(ModBlocks.GREEN_MARKER);
        blockModelGenerators.createTrivialCube(ModBlocks.CYAN_MARKER);
        blockModelGenerators.createTrivialCube(ModBlocks.BLUE_MARKER);
        blockModelGenerators.createTrivialCube(ModBlocks.MAGENTA_MARKER);
        blockModelGenerators.createTrivialCube(ModBlocks.SAND_GENERATOR_BLOCK);

        blockModelGenerators.createTrivialCube(ModBlocks.STORAGE_CONTROLLER);
        blockModelGenerators.createTrivialCube(ModBlocks.STORAGE_TRIM);
    }

    @Override
    public void generateItemModels(ItemModelGenerators itemModelGenerators) {
        itemModelGenerators.generateFlatItem(ModItems.ENERGY_DRAGON_SCALE, ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerators.generateFlatItem(ModItems.FIRE_DRAGON_SCALE, ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerators.generateFlatItem(ModItems.EARTH_DRAGON_SCALE, ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerators.generateFlatItem(ModItems.AIR_DRAGON_SCALE, ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerators.generateFlatItem(ModItems.WATER_DRAGON_SCALE, ModelTemplates.FLAT_HANDHELD_ITEM);

        itemModelGenerators.generateFlatItem(ModItems.ENERGY_DRAGON_HELMET, ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(ModItems.ENERGY_DRAGON_CHESTPLATE, ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(ModItems.ENERGY_DRAGON_LEGGINGS, ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(ModItems.ENERGY_DRAGON_BOOTS, ModelTemplates.FLAT_ITEM);

        itemModelGenerators.generateFlatItem(ModItems.FIRE_DRAGON_HELMET, ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(ModItems.FIRE_DRAGON_CHESTPLATE, ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(ModItems.FIRE_DRAGON_LEGGINGS, ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(ModItems.FIRE_DRAGON_BOOTS, ModelTemplates.FLAT_ITEM);

        itemModelGenerators.generateFlatItem(ModItems.AIR_DRAGON_HELMET, ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(ModItems.AIR_DRAGON_CHESTPLATE, ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(ModItems.AIR_DRAGON_LEGGINGS, ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(ModItems.AIR_DRAGON_BOOTS, ModelTemplates.FLAT_ITEM);

        itemModelGenerators.generateFlatItem(ModItems.WATER_DRAGON_HELMET, ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(ModItems.WATER_DRAGON_CHESTPLATE, ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(ModItems.WATER_DRAGON_LEGGINGS, ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(ModItems.WATER_DRAGON_BOOTS, ModelTemplates.FLAT_ITEM);

        itemModelGenerators.generateFlatItem(ModItems.EARTH_DRAGON_HELMET, ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(ModItems.EARTH_DRAGON_CHESTPLATE, ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(ModItems.EARTH_DRAGON_LEGGINGS, ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(ModItems.EARTH_DRAGON_BOOTS, ModelTemplates.FLAT_ITEM);

        itemModelGenerators.generateFlatItem(ModItems.ENERGY_DRAGON_ARMOR_UPGRADE, ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(ModItems.FIRE_DRAGON_ARMOR_UPGRADE, ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(ModItems.AIR_DRAGON_ARMOR_UPGRADE, ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(ModItems.WATER_DRAGON_ARMOR_UPGRADE, ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(ModItems.EARTH_DRAGON_ARMOR_UPGRADE, ModelTemplates.FLAT_ITEM);
    }
}
