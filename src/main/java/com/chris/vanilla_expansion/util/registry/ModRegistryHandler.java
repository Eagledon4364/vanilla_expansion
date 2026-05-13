package com.chris.vanilla_expansion.util.registry;

import com.chris.vanilla_expansion.VanillaExpansion;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.equipment.EquipmentAsset;
import net.minecraft.world.item.equipment.EquipmentAssets;
import org.jetbrains.annotations.NotNull;

public class ModRegistryHandler {

    public static final ResourceKey<@NotNull EquipmentAsset> FIRE_DRAGON_ARMOR_MATERIAL_KEY = ResourceKey.create(EquipmentAssets.ROOT_ID,
            Identifier.fromNamespaceAndPath(VanillaExpansion.MOD_ID, "fire_dragon_armor"));
    public static final ResourceKey<@NotNull EquipmentAsset> AIR_DRAGON_ARMOR_MATERIAL_KEY = ResourceKey.create(EquipmentAssets.ROOT_ID,
            Identifier.fromNamespaceAndPath(VanillaExpansion.MOD_ID, "air_dragon_armor"));
    public static final ResourceKey<@NotNull EquipmentAsset> WATER_DRAGON_ARMOR_MATERIAL_KEY = ResourceKey.create(EquipmentAssets.ROOT_ID,
            Identifier.fromNamespaceAndPath(VanillaExpansion.MOD_ID, "water_dragon_armor"));
    public static final ResourceKey<@NotNull EquipmentAsset> EARTH_DRAGON_ARMOR_MATERIAL_KEY = ResourceKey.create(EquipmentAssets.ROOT_ID,
            Identifier.fromNamespaceAndPath(VanillaExpansion.MOD_ID, "earth_dragon_armor"));
    public static final ResourceKey<@NotNull EquipmentAsset> ENERGY_DRAGON_ARMOR_MATERIAL_KEY = ResourceKey.create(EquipmentAssets.ROOT_ID,
            Identifier.fromNamespaceAndPath(VanillaExpansion.MOD_ID, "energy_dragon_armor"));

}
