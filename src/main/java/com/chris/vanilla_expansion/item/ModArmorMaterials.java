package com.chris.vanilla_expansion.item;

import com.chris.vanilla_expansion.util.ModTags;
import com.chris.vanilla_expansion.util.registry.ModRegistryHandler;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;

import java.util.Map;

public class ModArmorMaterials {
    public static final int BASE_DURABILITY = 50;
    public static final float TOUGHNESS = 4.0f;
    public static final float KNOCKBACK_RESISTANCE = 0.2f;

    public static final ArmorMaterial FIRE_DRAGON_ARMOR_MATERIAL = new ArmorMaterial(
      BASE_DURABILITY,
      Map.of(
              ArmorType.HELMET, 3,
              ArmorType.CHESTPLATE, 8,
              ArmorType.LEGGINGS, 6,
              ArmorType.BOOTS, 3
      ),
        20,
            SoundEvents.ARMOR_EQUIP_NETHERITE,
            TOUGHNESS,
            KNOCKBACK_RESISTANCE,
            ModTags.Items.REPAIRS_FIRE_DRAGON_ARMOR,
            ModRegistryHandler.FIRE_DRAGON_ARMOR_MATERIAL_KEY
    );
    public static final ArmorMaterial AIR_DRAGON_ARMOR_MATERIAL = new ArmorMaterial(
      BASE_DURABILITY,
      Map.of(
              ArmorType.HELMET, 3,
              ArmorType.CHESTPLATE, 8,
              ArmorType.LEGGINGS, 6,
              ArmorType.BOOTS, 3
      ),
        20,
            SoundEvents.ARMOR_EQUIP_NETHERITE,
            TOUGHNESS,
            KNOCKBACK_RESISTANCE,
            ModTags.Items.REPAIRS_AIR_DRAGON_ARMOR,
            ModRegistryHandler.AIR_DRAGON_ARMOR_MATERIAL_KEY
    );
    public static final ArmorMaterial WATER_DRAGON_ARMOR_MATERIAL = new ArmorMaterial(
      BASE_DURABILITY,
      Map.of(
              ArmorType.HELMET, 3,
              ArmorType.CHESTPLATE, 8,
              ArmorType.LEGGINGS, 6,
              ArmorType.BOOTS, 3
      ),
        20,
            SoundEvents.ARMOR_EQUIP_NETHERITE,
            TOUGHNESS,
            KNOCKBACK_RESISTANCE,
            ModTags.Items.REPAIRS_WATER_DRAGON_ARMOR,
            ModRegistryHandler.WATER_DRAGON_ARMOR_MATERIAL_KEY
    );
    public static final ArmorMaterial EARTH_DRAGON_ARMOR_MATERIAL = new ArmorMaterial(
      BASE_DURABILITY,
      Map.of(
              ArmorType.HELMET, 3,
              ArmorType.CHESTPLATE, 8,
              ArmorType.LEGGINGS, 6,
              ArmorType.BOOTS, 3
      ),
        20,
            SoundEvents.ARMOR_EQUIP_NETHERITE,
            TOUGHNESS,
            KNOCKBACK_RESISTANCE,
            ModTags.Items.REPAIRS_EARTH_DRAGON_ARMOR,
            ModRegistryHandler.EARTH_DRAGON_ARMOR_MATERIAL_KEY
    );
    public static final ArmorMaterial ENERGY_DRAGON_ARMOR_MATERIAL = new ArmorMaterial(
      100,
      Map.of(
              ArmorType.HELMET, 3,
              ArmorType.CHESTPLATE, 8,
              ArmorType.LEGGINGS, 6,
              ArmorType.BOOTS, 3
      ),
        20,
            SoundEvents.ARMOR_EQUIP_NETHERITE,
            TOUGHNESS,
            KNOCKBACK_RESISTANCE,
            ModTags.Items.REPAIRS_ENERGY_DRAGON_ARMOR,
            ModRegistryHandler.ENERGY_DRAGON_ARMOR_MATERIAL_KEY
    );


}
