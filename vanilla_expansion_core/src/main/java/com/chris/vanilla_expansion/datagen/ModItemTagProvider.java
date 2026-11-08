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
        tag(ModTags.Items.DRAGON_FOOD)
                .forceAddTag(ItemTags.WOLF_FOOD);

        tag(ModTags.Items.STEEL_REPAIRABLE)
                .add(ModItems.getRK(ModItems.STEEL_INGOT));
        tag(ModTags.Items.PAXEL_REPAIR)
                .add(ModItems.getRK(ModItems.STEEL_INGOT));

        tag(ItemTags.NETHERITE_TOOL_MATERIALS)
                .add(ModItems.getRK(ModItems.ENERGY_DRAGON_SCALE))
                .add(ModItems.getRK(ModItems.FIRE_DRAGON_SCALE))
                .add(ModItems.getRK(ModItems.AIR_DRAGON_SCALE))
                .add(ModItems.getRK(ModItems.WATER_DRAGON_SCALE))
                .add(ModItems.getRK(ModItems.EARTH_DRAGON_SCALE));
        tag(ItemTags.HEAD_ARMOR)
                .add(ModItems.getRK(ModItems.ENERGY_DRAGON_HELMET))
                .add(ModItems.getRK(ModItems.FIRE_DRAGON_HELMET))
                .add(ModItems.getRK(ModItems.AIR_DRAGON_HELMET))
                .add(ModItems.getRK(ModItems.WATER_DRAGON_HELMET))
                .add(ModItems.getRK(ModItems.EARTH_DRAGON_HELMET));

        tag(ItemTags.CHEST_ARMOR)
                .add(ModItems.getRK(ModItems.ENERGY_DRAGON_CHESTPLATE))
                .add(ModItems.getRK(ModItems.FIRE_DRAGON_CHESTPLATE))
                .add(ModItems.getRK(ModItems.AIR_DRAGON_CHESTPLATE))
                .add(ModItems.getRK(ModItems.WATER_DRAGON_CHESTPLATE))
                .add(ModItems.getRK(ModItems.EARTH_DRAGON_CHESTPLATE));

        tag(ItemTags.LEG_ARMOR)
                .add(ModItems.getRK(ModItems.ENERGY_DRAGON_LEGGINGS))
                .add(ModItems.getRK(ModItems.FIRE_DRAGON_LEGGINGS))
                .add(ModItems.getRK(ModItems.AIR_DRAGON_LEGGINGS))
                .add(ModItems.getRK(ModItems.WATER_DRAGON_LEGGINGS))
                .add(ModItems.getRK(ModItems.EARTH_DRAGON_LEGGINGS));

        tag(ItemTags.FOOT_ARMOR)
                .add(ModItems.getRK(ModItems.ENERGY_DRAGON_BOOTS))
                .add(ModItems.getRK(ModItems.FIRE_DRAGON_BOOTS))
                .add(ModItems.getRK(ModItems.AIR_DRAGON_BOOTS))
                .add(ModItems.getRK(ModItems.WATER_DRAGON_BOOTS))
                .add(ModItems.getRK(ModItems.EARTH_DRAGON_BOOTS));

        tag(ItemTags.HEAD_ARMOR_ENCHANTABLE)
                .add(ModItems.getRK(ModItems.ENERGY_DRAGON_HELMET))
                .add(ModItems.getRK(ModItems.FIRE_DRAGON_HELMET))
                .add(ModItems.getRK(ModItems.AIR_DRAGON_HELMET))
                .add(ModItems.getRK(ModItems.WATER_DRAGON_HELMET))
                .add(ModItems.getRK(ModItems.EARTH_DRAGON_HELMET));

        tag(ItemTags.CHEST_ARMOR_ENCHANTABLE)
                .add(ModItems.getRK(ModItems.ENERGY_DRAGON_CHESTPLATE))
                .add(ModItems.getRK(ModItems.FIRE_DRAGON_CHESTPLATE))
                .add(ModItems.getRK(ModItems.AIR_DRAGON_CHESTPLATE))
                .add(ModItems.getRK(ModItems.WATER_DRAGON_CHESTPLATE))
                .add(ModItems.getRK(ModItems.EARTH_DRAGON_CHESTPLATE));

        tag(ItemTags.LEG_ARMOR_ENCHANTABLE)
                .add(ModItems.getRK(ModItems.ENERGY_DRAGON_LEGGINGS))
                .add(ModItems.getRK(ModItems.FIRE_DRAGON_LEGGINGS))
                .add(ModItems.getRK(ModItems.AIR_DRAGON_LEGGINGS))
                .add(ModItems.getRK(ModItems.WATER_DRAGON_LEGGINGS))
                .add(ModItems.getRK(ModItems.EARTH_DRAGON_LEGGINGS));

        tag(ItemTags.FOOT_ARMOR_ENCHANTABLE)
                .add(ModItems.getRK(ModItems.ENERGY_DRAGON_BOOTS))
                .add(ModItems.getRK(ModItems.FIRE_DRAGON_BOOTS))
                .add(ModItems.getRK(ModItems.AIR_DRAGON_BOOTS))
                .add(ModItems.getRK(ModItems.WATER_DRAGON_BOOTS))
                .add(ModItems.getRK(ModItems.EARTH_DRAGON_BOOTS));

        tag(ItemTags.ARMOR_ENCHANTABLE)
                .add(ModItems.getRK(ModItems.ENERGY_DRAGON_HELMET))
                .add(ModItems.getRK(ModItems.FIRE_DRAGON_HELMET))
                .add(ModItems.getRK(ModItems.AIR_DRAGON_HELMET))
                .add(ModItems.getRK(ModItems.WATER_DRAGON_HELMET))
                .add(ModItems.getRK(ModItems.EARTH_DRAGON_HELMET))
                .add(ModItems.getRK(ModItems.ENERGY_DRAGON_CHESTPLATE))
                .add(ModItems.getRK(ModItems.FIRE_DRAGON_CHESTPLATE))
                .add(ModItems.getRK(ModItems.AIR_DRAGON_CHESTPLATE))
                .add(ModItems.getRK(ModItems.WATER_DRAGON_CHESTPLATE))
                .add(ModItems.getRK(ModItems.EARTH_DRAGON_CHESTPLATE))
                .add(ModItems.getRK(ModItems.ENERGY_DRAGON_LEGGINGS))
                .add(ModItems.getRK(ModItems.FIRE_DRAGON_LEGGINGS))
                .add(ModItems.getRK(ModItems.AIR_DRAGON_LEGGINGS))
                .add(ModItems.getRK(ModItems.WATER_DRAGON_LEGGINGS))
                .add(ModItems.getRK(ModItems.EARTH_DRAGON_LEGGINGS))
                .add(ModItems.getRK(ModItems.ENERGY_DRAGON_BOOTS))
                .add(ModItems.getRK(ModItems.FIRE_DRAGON_BOOTS))
                .add(ModItems.getRK(ModItems.AIR_DRAGON_BOOTS))
                .add(ModItems.getRK(ModItems.WATER_DRAGON_BOOTS))
                .add(ModItems.getRK(ModItems.EARTH_DRAGON_BOOTS));
    }
}
