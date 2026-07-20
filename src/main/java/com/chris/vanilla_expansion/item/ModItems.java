package com.chris.vanilla_expansion.item;

import com.chris.vanilla_expansion.VanillaExpansion;
import com.chris.vanilla_expansion.block.ModBlocks;
import com.chris.vanilla_expansion.entity.ModEntities;
import com.chris.vanilla_expansion.item.custom.*;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Unit;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.DamageResistant;
import net.minecraft.world.item.equipment.ArmorType;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Function;

public class ModItems {

    public static final Item ENERGY_DRAGON_SPAWN_EGG = register("energy_dragon_spawn_egg",
            SpawnEggItem::new,
            new Item.Properties().spawnEgg(ModEntities.ENERGY_DRAGON));

    public static final Item AIR_DRAGON_SPAWN_EGG = register("air_dragon_spawn_egg",
                SpawnEggItem::new,
                new Item.Properties().spawnEgg(ModEntities.AIR_DRAGON));

    public static final Item FIRE_DRAGON_SPAWN_EGG = register("fire_dragon_spawn_egg",
                SpawnEggItem::new,
                new Item.Properties().spawnEgg(ModEntities.FIRE_DRAGON));


    public static final Item STORAGE_UPGRADE = register("storage_upgrade", Item::new, new Item.Properties().stacksTo(1));
    public static final Item STACK_UPGRADE = register("stack_upgrade", Item::new, new Item.Properties().stacksTo(1));
    public static final Item CRAFTING_UPGRADE = register("crafting_upgrade", Item::new, new Item.Properties().stacksTo(1));

    public static final Item STORAGE_BLOCK_UPGRADE = register("storage_block_upgrade", Item::new, new Item.Properties().stacksTo(1));




    public static final Item PAXEL = register("paxel", settings ->
                    new PaxelItem(ModToolMaterials.PAXEL_MATERIAL, 4.0f, -2.4f, settings),
            new Item.Properties().stacksTo(1).fireResistant().rarity(Rarity.EPIC)
                    .delayedComponent(DataComponents.DAMAGE_RESISTANT, context -> new DamageResistant(context.getOrThrow(DamageTypeTags.IS_EXPLOSION)))
                    );

    public static final Item MAGNET = register("magnet", MagnetItem::new, new Item.Properties().stacksTo(1));


    public static final Item STEEL_SWORD = register("steel_sword", Item::new,
            new Item.Properties().sword(ModToolMaterials.STEEL, 3.0f, -2.4F).fireResistant().stacksTo(1));
    public static final Item STEEL_PICKAXE = register("steel_pickaxe", Item::new,
            new Item.Properties().pickaxe(ModToolMaterials.STEEL, 2.0f, -2.8f));
    public static final Item STEEL_AXE = register("steel_axe", settings ->
            new AxeItem(ModToolMaterials.STEEL, 4.0f, -3.0f, settings),
            new Item.Properties().fireResistant());
public static final Item STEEL_SHOVEL = register("steel_shovel", settings ->
            new ShovelItem(ModToolMaterials.STEEL, 2.0f, -3.0f, settings),
            new Item.Properties().fireResistant());

public static final Item STEEL_HOE = register("steel_hoe", settings ->
            new HoeItem(ModToolMaterials.STEEL, 2.0f, -3.0f, settings),
            new Item.Properties().fireResistant());

    public static final Item STEEL_INGOT = register("steel_ingot", Item::new, new Item.Properties());



    // ELEMENTAL DRAGON UPGRADE TEMPLATES
    public static final Item ENERGY_DRAGON_ARMOR_UPGRADE = register("energy_dragon_armor_upgrade",
            (properties) -> createDragonTemplate("energy_dragon", properties),
            new Item.Properties().rarity(Rarity.UNCOMMON)
    );

    public static final Item FIRE_DRAGON_ARMOR_UPGRADE = register("fire_dragon_armor_upgrade",
            (properties) -> createDragonTemplate("fire_dragon", properties),
            new Item.Properties().rarity(Rarity.UNCOMMON)
    );

    public static final Item AIR_DRAGON_ARMOR_UPGRADE = register("air_dragon_armor_upgrade",
            (properties) -> createDragonTemplate("air_dragon", properties),
            new Item.Properties().rarity(Rarity.UNCOMMON)
    );

    public static final Item WATER_DRAGON_ARMOR_UPGRADE = register("water_dragon_armor_upgrade",
            (properties) -> createDragonTemplate("water_dragon", properties),
            new Item.Properties().rarity(Rarity.UNCOMMON)
    );

    public static final Item EARTH_DRAGON_ARMOR_UPGRADE = register("earth_dragon_armor_upgrade",
            (properties) -> createDragonTemplate("earth_dragon", properties),
            new Item.Properties().rarity(Rarity.UNCOMMON)
    );


    public static final Item ENERGY_CORE = register("energy_core", Item::new, new Item.Properties());
    public static final Item FIRE_CORE = register("fire_core", Item::new, new Item.Properties());
    public static final Item AIR_CORE = register("air_core", Item::new, new Item.Properties());
    public static final Item WATER_CORE = register("water_core", Item::new, new Item.Properties());
    public static final Item EARTH_CORE = register("earth_core", Item::new, new Item.Properties());

    //DRAGON SCALES
    public static final Item ENERGY_DRAGON_SCALE = register("energy_dragon_scale", Item::new, new Item.Properties());
    public static final Item FIRE_DRAGON_SCALE = register("fire_dragon_scale", Item::new, new Item.Properties());
    public static final Item AIR_DRAGON_SCALE = register("air_dragon_scale", Item::new, new Item.Properties());
    public static final Item WATER_DRAGON_SCALE = register("water_dragon_scale", Item::new, new Item.Properties());
    public static final Item EARTH_DRAGON_SCALE = register("earth_dragon_scale", Item::new, new Item.Properties());

    public static final Item BACKPACK_ITEM = register(
            "backpack",
            (settings) -> new BackpackItem(ModBlocks.BACKPACK_BLOCK, settings),
            new Item.Properties().stacksTo(1).component(DataComponents.MAX_STACK_SIZE, 128).stacksTo(1)
    );
    //ENERGY DRAGON ARMOR
    public static final Item ENERGY_DRAGON_HELMET = register(
            "energy_dragon_helmet",
            EnergyDragonArmorItem::new,
            new Item.Properties().humanoidArmor(ModArmorMaterials.ENERGY_DRAGON_ARMOR_MATERIAL, ArmorType.HELMET)
                    .durability(ArmorType.HELMET.getDurability(ModArmorMaterials.BASE_DURABILITY))
                    .enchantable(20).fireResistant()
                    .delayedComponent(DataComponents.DAMAGE_RESISTANT, context -> new DamageResistant(context.getOrThrow(DamageTypeTags.IS_EXPLOSION)))
    );
    public static final Item ENERGY_DRAGON_CHESTPLATE = register(
            "energy_dragon_chestplate",
            EnergyDragonArmorItem::new,
            new Item.Properties().humanoidArmor(ModArmorMaterials.ENERGY_DRAGON_ARMOR_MATERIAL, ArmorType.CHESTPLATE)
                    .durability(ArmorType.CHESTPLATE.getDurability(ModArmorMaterials.BASE_DURABILITY))
                    .component(DataComponents.GLIDER, Unit.INSTANCE)
                    .enchantable(20).fireResistant()
                    .delayedComponent(DataComponents.DAMAGE_RESISTANT, context -> new DamageResistant(context.getOrThrow(DamageTypeTags.IS_EXPLOSION)))
    );
    public static final Item ENERGY_DRAGON_LEGGINGS = register(
            "energy_dragon_leggings",
            EnergyDragonArmorItem::new,
            new Item.Properties().humanoidArmor(ModArmorMaterials.ENERGY_DRAGON_ARMOR_MATERIAL, ArmorType.LEGGINGS)
                    .durability(ArmorType.LEGGINGS.getDurability(ModArmorMaterials.BASE_DURABILITY))
                    .enchantable(20).fireResistant()
                    .delayedComponent(DataComponents.DAMAGE_RESISTANT, context -> new DamageResistant(context.getOrThrow(DamageTypeTags.IS_EXPLOSION)))
    );
    public static final Item ENERGY_DRAGON_BOOTS = register(
            "energy_dragon_boots",
            EnergyDragonArmorItem::new,
            new Item.Properties().humanoidArmor(ModArmorMaterials.ENERGY_DRAGON_ARMOR_MATERIAL, ArmorType.BOOTS)
                    .durability(ArmorType.BOOTS.getDurability(ModArmorMaterials.BASE_DURABILITY))
                    .enchantable(20).fireResistant()
                    .delayedComponent(DataComponents.DAMAGE_RESISTANT, context -> new DamageResistant(context.getOrThrow(DamageTypeTags.IS_EXPLOSION)))
    );

    //FIRE DRAGON ARMOR
    public static final Item FIRE_DRAGON_HELMET = register(
            "fire_dragon_helmet",
            DragonArmorItem::new,
            new Item.Properties().humanoidArmor(ModArmorMaterials.FIRE_DRAGON_ARMOR_MATERIAL, ArmorType.HELMET)
                    .durability(ArmorType.HELMET.getDurability(ModArmorMaterials.BASE_DURABILITY))
                    .enchantable(20).fireResistant()
                    .delayedComponent(DataComponents.DAMAGE_RESISTANT, context -> new DamageResistant(context.getOrThrow(DamageTypeTags.IS_EXPLOSION)))
    );
    public static final Item FIRE_DRAGON_CHESTPLATE = register(
            "fire_dragon_chestplate",
            DragonArmorItem::new,
            new Item.Properties().humanoidArmor(ModArmorMaterials.FIRE_DRAGON_ARMOR_MATERIAL, ArmorType.CHESTPLATE)
                    .durability(ArmorType.CHESTPLATE.getDurability(ModArmorMaterials.BASE_DURABILITY))
                    .enchantable(20).fireResistant()
                    .delayedComponent(DataComponents.DAMAGE_RESISTANT, context -> new DamageResistant(context.getOrThrow(DamageTypeTags.IS_EXPLOSION)))
    );
    public static final Item FIRE_DRAGON_LEGGINGS = register(
            "fire_dragon_leggings",
            DragonArmorItem::new,
            new Item.Properties().humanoidArmor(ModArmorMaterials.FIRE_DRAGON_ARMOR_MATERIAL, ArmorType.LEGGINGS)
                    .durability(ArmorType.LEGGINGS.getDurability(ModArmorMaterials.BASE_DURABILITY))
                    .enchantable(20).fireResistant()
                    .delayedComponent(DataComponents.DAMAGE_RESISTANT, context -> new DamageResistant(context.getOrThrow(DamageTypeTags.IS_EXPLOSION)))
    );
    public static final Item FIRE_DRAGON_BOOTS = register(
            "fire_dragon_boots",
            DragonArmorItem::new,
            new Item.Properties().humanoidArmor(ModArmorMaterials.FIRE_DRAGON_ARMOR_MATERIAL, ArmorType.BOOTS)
                    .durability(ArmorType.BOOTS.getDurability(ModArmorMaterials.BASE_DURABILITY))
                    .enchantable(20).fireResistant()
                    .delayedComponent(DataComponents.DAMAGE_RESISTANT, context -> new DamageResistant(context.getOrThrow(DamageTypeTags.IS_EXPLOSION)))
    );

    //AIR DRAGON ARMOR
    public static final Item AIR_DRAGON_HELMET = register(
            "air_dragon_helmet",
            DragonArmorItem::new,
            new Item.Properties().humanoidArmor(ModArmorMaterials.AIR_DRAGON_ARMOR_MATERIAL, ArmorType.HELMET)
                    .durability(ArmorType.HELMET.getDurability(ModArmorMaterials.BASE_DURABILITY))
                    .enchantable(20).fireResistant()
                    .delayedComponent(DataComponents.DAMAGE_RESISTANT, context -> new DamageResistant(context.getOrThrow(DamageTypeTags.IS_EXPLOSION)))
    );
    public static final Item AIR_DRAGON_CHESTPLATE = register(
            "air_dragon_chestplate",
            DragonArmorItem::new,
            new Item.Properties().humanoidArmor(ModArmorMaterials.AIR_DRAGON_ARMOR_MATERIAL, ArmorType.CHESTPLATE)
                    .durability(ArmorType.CHESTPLATE.getDurability(ModArmorMaterials.BASE_DURABILITY))
                    .enchantable(20).fireResistant()
                    .delayedComponent(DataComponents.DAMAGE_RESISTANT, context -> new DamageResistant(context.getOrThrow(DamageTypeTags.IS_EXPLOSION)))
    );
    public static final Item AIR_DRAGON_LEGGINGS = register(
            "air_dragon_leggings",
            DragonArmorItem::new,
            new Item.Properties().humanoidArmor(ModArmorMaterials.AIR_DRAGON_ARMOR_MATERIAL, ArmorType.LEGGINGS)
                    .durability(ArmorType.LEGGINGS.getDurability(ModArmorMaterials.BASE_DURABILITY))
                    .enchantable(20).fireResistant()
                    .delayedComponent(DataComponents.DAMAGE_RESISTANT, context -> new DamageResistant(context.getOrThrow(DamageTypeTags.IS_EXPLOSION)))
    );
    public static final Item AIR_DRAGON_BOOTS = register(
            "air_dragon_boots",
            DragonArmorItem::new,
            new Item.Properties().humanoidArmor(ModArmorMaterials.AIR_DRAGON_ARMOR_MATERIAL, ArmorType.BOOTS)
                    .durability(ArmorType.BOOTS.getDurability(ModArmorMaterials.BASE_DURABILITY))
                    .enchantable(20).fireResistant()
                    .delayedComponent(DataComponents.DAMAGE_RESISTANT, context -> new DamageResistant(context.getOrThrow(DamageTypeTags.IS_EXPLOSION)))
    );

    //WATER DRAGON ARMOR
    public static final Item WATER_DRAGON_HELMET = register(
            "water_dragon_helmet",
            DragonArmorItem::new,
            new Item.Properties().humanoidArmor(ModArmorMaterials.WATER_DRAGON_ARMOR_MATERIAL, ArmorType.HELMET)
                    .durability(ArmorType.HELMET.getDurability(ModArmorMaterials.BASE_DURABILITY))
                    .enchantable(20).fireResistant()
                    .delayedComponent(DataComponents.DAMAGE_RESISTANT, context -> new DamageResistant(context.getOrThrow(DamageTypeTags.IS_EXPLOSION)))
    );
    public static final Item WATER_DRAGON_CHESTPLATE = register(
            "water_dragon_chestplate",
            DragonArmorItem::new,
            new Item.Properties().humanoidArmor(ModArmorMaterials.WATER_DRAGON_ARMOR_MATERIAL, ArmorType.CHESTPLATE)
                    .durability(ArmorType.CHESTPLATE.getDurability(ModArmorMaterials.BASE_DURABILITY))
                    .enchantable(20).fireResistant()
                    .delayedComponent(DataComponents.DAMAGE_RESISTANT, context -> new DamageResistant(context.getOrThrow(DamageTypeTags.IS_EXPLOSION)))
    );
    public static final Item WATER_DRAGON_LEGGINGS = register(
            "water_dragon_leggings",
            DragonArmorItem::new,
            new Item.Properties().humanoidArmor(ModArmorMaterials.WATER_DRAGON_ARMOR_MATERIAL, ArmorType.LEGGINGS)
                    .durability(ArmorType.LEGGINGS.getDurability(ModArmorMaterials.BASE_DURABILITY))
                    .enchantable(20).fireResistant()
                    .delayedComponent(DataComponents.DAMAGE_RESISTANT, context -> new DamageResistant(context.getOrThrow(DamageTypeTags.IS_EXPLOSION)))
    );
    public static final Item WATER_DRAGON_BOOTS = register(
            "water_dragon_boots",
            DragonArmorItem::new,
            new Item.Properties().humanoidArmor(ModArmorMaterials.WATER_DRAGON_ARMOR_MATERIAL, ArmorType.BOOTS)
                    .durability(ArmorType.BOOTS.getDurability(ModArmorMaterials.BASE_DURABILITY))
                    .enchantable(20).fireResistant()
                    .delayedComponent(DataComponents.DAMAGE_RESISTANT, context -> new DamageResistant(context.getOrThrow(DamageTypeTags.IS_EXPLOSION)))
    );

    //EARTH DRAGON ARMOR
    public static final Item EARTH_DRAGON_HELMET = register(
            "earth_dragon_helmet",
            DragonArmorItem::new,
            new Item.Properties().humanoidArmor(ModArmorMaterials.EARTH_DRAGON_ARMOR_MATERIAL, ArmorType.HELMET)
                    .durability(ArmorType.HELMET.getDurability(ModArmorMaterials.BASE_DURABILITY))
                    .enchantable(20).fireResistant()
                    .delayedComponent(DataComponents.DAMAGE_RESISTANT, context -> new DamageResistant(context.getOrThrow(DamageTypeTags.IS_EXPLOSION)))
    );
    public static final Item EARTH_DRAGON_CHESTPLATE = register(
            "earth_dragon_chestplate",
            DragonArmorItem::new,
            new Item.Properties().humanoidArmor(ModArmorMaterials.EARTH_DRAGON_ARMOR_MATERIAL, ArmorType.CHESTPLATE)
                    .durability(ArmorType.CHESTPLATE.getDurability(ModArmorMaterials.BASE_DURABILITY))
                    .enchantable(20).fireResistant()
                    .delayedComponent(DataComponents.DAMAGE_RESISTANT, context -> new DamageResistant(context.getOrThrow(DamageTypeTags.IS_EXPLOSION)))
    );
    public static final Item EARTH_DRAGON_LEGGINGS = register(
            "earth_dragon_leggings",
            DragonArmorItem::new,
            new Item.Properties().humanoidArmor(ModArmorMaterials.EARTH_DRAGON_ARMOR_MATERIAL, ArmorType.LEGGINGS)
                    .durability(ArmorType.LEGGINGS.getDurability(ModArmorMaterials.BASE_DURABILITY))
                    .enchantable(20).fireResistant()
                    .delayedComponent(DataComponents.DAMAGE_RESISTANT, context -> new DamageResistant(context.getOrThrow(DamageTypeTags.IS_EXPLOSION)))
    );
    public static final Item EARTH_DRAGON_BOOTS = register(
            "earth_dragon_boots",
            DragonArmorItem::new,
            new Item.Properties().humanoidArmor(ModArmorMaterials.EARTH_DRAGON_ARMOR_MATERIAL, ArmorType.BOOTS)
                    .durability(ArmorType.BOOTS.getDurability(ModArmorMaterials.BASE_DURABILITY))
                    .enchantable(20).fireResistant()
                    .delayedComponent(DataComponents.DAMAGE_RESISTANT, context -> new DamageResistant(context.getOrThrow(DamageTypeTags.IS_EXPLOSION)))
    );

    public static ResourceKey<@NotNull Item> getRK(Item item) {
        return BuiltInRegistries.ITEM.getResourceKey(item).get();
    }


    public static <T extends Item> T register(String name, Function<Item.Properties, T> itemFactory, Item.Properties settings) {
        ResourceKey<@NotNull Item> itemKey = ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(VanillaExpansion.MOD_ID, name));
        T item = itemFactory.apply(settings.setId(itemKey));
        Registry.register(BuiltInRegistries.ITEM, itemKey, item);
        return item;
    }
    private static List<Identifier> createDragonUpgradeIconList() {
        return List.of(
                Identifier.withDefaultNamespace("container/slot/helmet"),
                Identifier.withDefaultNamespace("container/slot/chestplate"),
                Identifier.withDefaultNamespace("container/slot/leggings"),
                Identifier.withDefaultNamespace("container/slot/boots")
        );
    }

    private static List<Identifier> createDragonUpgradeMaterialList() {
        return List.of(Identifier.withDefaultNamespace("container/slot/ingot"));
    }

    private static SmithingTemplateItem createDragonTemplate(String elementKey, Item.Properties properties) {
        return new SmithingTemplateItem(
                net.minecraft.network.chat.Component.translatable("upgrade." + VanillaExpansion.MOD_ID + "." + elementKey + ".applies_to").withStyle(net.minecraft.ChatFormatting.BLUE),
                net.minecraft.network.chat.Component.translatable("upgrade." + VanillaExpansion.MOD_ID + "." + elementKey + ".ingredients").withStyle(net.minecraft.ChatFormatting.BLUE),
                net.minecraft.network.chat.Component.translatable("upgrade." + VanillaExpansion.MOD_ID + "." + elementKey + ".base_slot_description"),
                net.minecraft.network.chat.Component.translatable("upgrade." + VanillaExpansion.MOD_ID + "." + elementKey + ".additions_slot_description"),
                createDragonUpgradeIconList(),
                createDragonUpgradeMaterialList(),
                properties
        );
    }
    public static void registerModItems() {

    }
}

