package com.chris.vanilla_expansion.datagen;

import com.chris.vanilla_expansion.item.ModItems;
import com.chris.vanilla_expansion.util.ModTags;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.ItemTags;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends FabricTagsProvider.ItemTagsProvider {

    public ModItemTagProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registryLookupFuture) {
        super(output, registryLookupFuture);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider registries) {
        valueLookupBuilder(ModTags.Items.DRAGON_FOOD)
                .forceAddTag(ItemTags.WOLF_FOOD);

        valueLookupBuilder(ModTags.Items.STEEL_REPAIRABLE)
                .add(ModItems.STEEL_INGOT);
        valueLookupBuilder(ModTags.Items.PAXEL_REPAIR)
                .add(ModItems.STEEL_INGOT);

        valueLookupBuilder(ItemTags.NETHERITE_TOOL_MATERIALS)
                .add(ModItems.ENERGY_DRAGON_SCALE)
                .add(ModItems.FIRE_DRAGON_SCALE)
                .add(ModItems.AIR_DRAGON_SCALE)
                .add(ModItems.WATER_DRAGON_SCALE)
                .add(ModItems.EARTH_DRAGON_SCALE);
        valueLookupBuilder(ItemTags.HEAD_ARMOR)
                .add(ModItems.ENERGY_DRAGON_HELMET)
                .add(ModItems.FIRE_DRAGON_HELMET)
                .add(ModItems.AIR_DRAGON_HELMET)
                .add(ModItems.WATER_DRAGON_HELMET)
                .add(ModItems.EARTH_DRAGON_HELMET);

        valueLookupBuilder(ItemTags.CHEST_ARMOR)
                .add(ModItems.ENERGY_DRAGON_CHESTPLATE)
                .add(ModItems.FIRE_DRAGON_CHESTPLATE)
                .add(ModItems.AIR_DRAGON_CHESTPLATE)
                .add(ModItems.WATER_DRAGON_CHESTPLATE)
                .add(ModItems.EARTH_DRAGON_CHESTPLATE);

        valueLookupBuilder(ItemTags.LEG_ARMOR)
                .add(ModItems.ENERGY_DRAGON_LEGGINGS)
                .add(ModItems.FIRE_DRAGON_LEGGINGS)
                .add(ModItems.AIR_DRAGON_LEGGINGS)
                .add(ModItems.WATER_DRAGON_LEGGINGS)
                .add(ModItems.EARTH_DRAGON_LEGGINGS);

        valueLookupBuilder(ItemTags.FOOT_ARMOR)
                .add(ModItems.ENERGY_DRAGON_BOOTS)
                .add(ModItems.FIRE_DRAGON_BOOTS)
                .add(ModItems.AIR_DRAGON_BOOTS)
                .add(ModItems.WATER_DRAGON_BOOTS)
                .add(ModItems.EARTH_DRAGON_BOOTS);

        valueLookupBuilder(ItemTags.HEAD_ARMOR_ENCHANTABLE)
                .add(ModItems.ENERGY_DRAGON_HELMET)
                .add(ModItems.FIRE_DRAGON_HELMET)
                .add(ModItems.AIR_DRAGON_HELMET)
                .add(ModItems.WATER_DRAGON_HELMET)
                .add(ModItems.EARTH_DRAGON_HELMET);

        valueLookupBuilder(ItemTags.CHEST_ARMOR_ENCHANTABLE)
                .add(ModItems.ENERGY_DRAGON_CHESTPLATE)
                .add(ModItems.FIRE_DRAGON_CHESTPLATE)
                .add(ModItems.AIR_DRAGON_CHESTPLATE)
                .add(ModItems.WATER_DRAGON_CHESTPLATE)
                .add(ModItems.EARTH_DRAGON_CHESTPLATE);

        valueLookupBuilder(ItemTags.LEG_ARMOR_ENCHANTABLE)
                .add(ModItems.ENERGY_DRAGON_LEGGINGS)
                .add(ModItems.FIRE_DRAGON_LEGGINGS)
                .add(ModItems.AIR_DRAGON_LEGGINGS)
                .add(ModItems.WATER_DRAGON_LEGGINGS)
                .add(ModItems.EARTH_DRAGON_LEGGINGS);

        valueLookupBuilder(ItemTags.FOOT_ARMOR_ENCHANTABLE)
                .add(ModItems.ENERGY_DRAGON_BOOTS)
                .add(ModItems.FIRE_DRAGON_BOOTS)
                .add(ModItems.AIR_DRAGON_BOOTS)
                .add(ModItems.WATER_DRAGON_BOOTS)
                .add(ModItems.EARTH_DRAGON_BOOTS);

        valueLookupBuilder(ItemTags.ARMOR_ENCHANTABLE)
                .add(ModItems.ENERGY_DRAGON_HELMET)
                .add(ModItems.FIRE_DRAGON_HELMET)
                .add(ModItems.AIR_DRAGON_HELMET)
                .add(ModItems.WATER_DRAGON_HELMET)
                .add(ModItems.EARTH_DRAGON_HELMET)
                .add(ModItems.ENERGY_DRAGON_CHESTPLATE)
                .add(ModItems.FIRE_DRAGON_CHESTPLATE)
                .add(ModItems.AIR_DRAGON_CHESTPLATE)
                .add(ModItems.WATER_DRAGON_CHESTPLATE)
                .add(ModItems.EARTH_DRAGON_CHESTPLATE)
                .add(ModItems.ENERGY_DRAGON_LEGGINGS)
                .add(ModItems.FIRE_DRAGON_LEGGINGS)
                .add(ModItems.AIR_DRAGON_LEGGINGS)
                .add(ModItems.WATER_DRAGON_LEGGINGS)
                .add(ModItems.EARTH_DRAGON_LEGGINGS)
                .add(ModItems.ENERGY_DRAGON_BOOTS)
                .add(ModItems.FIRE_DRAGON_BOOTS)
                .add(ModItems.AIR_DRAGON_BOOTS)
                .add(ModItems.WATER_DRAGON_BOOTS)
                .add(ModItems.EARTH_DRAGON_BOOTS);
    }
}
