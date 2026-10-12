package com.chris.vanilla_expansion.item;

import com.chris.vanilla_expansion.VanillaExpansion;
import com.chris.vanilla_expansion.block.ModBlocks;
import com.chris.vanilla_expansion.entity.ModEntities;
import com.chris.vanilla_expansion.item.custom.*;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Unit;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.DamageResistant;
import net.minecraft.world.item.component.ItemLore;
import net.minecraft.world.item.equipment.ArmorType;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Function;

public class ModItems {

    // SPAWN EGGS
    public static final Item ENERGY_DRAGON_SPAWN_EGG = registerItem("energy_dragon_spawn_egg",
            properties -> new SpawnEggItem(properties.spawnEgg(ModEntities.ENERGY_DRAGON)));
    public static final Item AIR_DRAGON_SPAWN_EGG = registerItem("air_dragon_spawn_egg",
            properties -> new SpawnEggItem(properties.spawnEgg(ModEntities.AIR_DRAGON)));
    public static final Item FIRE_DRAGON_SPAWN_EGG = registerItem("fire_dragon_spawn_egg",
            properties -> new SpawnEggItem(properties.spawnEgg(ModEntities.FIRE_DRAGON)));
    public static final Item WATER_DRAGON_SPAWN_EGG = registerItem("water_dragon_spawn_egg",
            properties -> new SpawnEggItem(properties.spawnEgg(ModEntities.WATER_DRAGON)));
    public static final Item EARTH_DRAGON_SPAWN_EGG = registerItem("earth_dragon_spawn_egg",
            properties -> new SpawnEggItem(properties.spawnEgg(ModEntities.EARTH_DRAGON)));

    // UPGRADES & TOOL HEADS
    public static final Item STORAGE_UPGRADE = registerItem("storage_upgrade", properties -> new Item(properties.stacksTo(1)));
    public static final Item STACK_UPGRADE = registerItem("stack_upgrade", properties -> new Item(properties.stacksTo(1)));
    public static final Item CRAFTING_UPGRADE = registerItem("crafting_upgrade", properties -> new Item(properties.stacksTo(1)));

    public static final Item STEEL_SWORD_TOOL_HEAD = registerItem("steel_sword_tool_head", properties -> new Item(properties.stacksTo(1)));
    public static final Item STEEL_PICKAXE_TOOL_HEAD = registerItem("steel_pickaxe_tool_head", properties -> new Item(properties.stacksTo(1)));
    public static final Item STEEL_AXE_TOOL_HEAD = registerItem("steel_axe_tool_head", properties -> new Item(properties.stacksTo(1)));
    public static final Item STEEL_SHOVEL_TOOL_HEAD = registerItem("steel_shovel_tool_head", properties -> new Item(properties.stacksTo(1)));
    public static final Item STEEL_HOE_TOOL_HEAD = registerItem("steel_hoe_tool_head", properties -> new Item(properties.stacksTo(1)));
    public static final Item PAXEL_TOOL_HEAD = registerItem("paxel_tool_head", properties -> new Item(properties.stacksTo(1)));

    public static final Item KEY = registerItem("key", properties -> new Item(properties.stacksTo(1)
            .component(DataComponents.LORE, new ItemLore(List.of(
                    Component.literal("Used to lock the item type in a storage crate")
                            .withStyle(ChatFormatting.GRAY)
            )))
    ));
    public static final Item STORAGE_BLOCK_UPGRADE = registerItem("storage_block_upgrade", properties -> new Item(properties.stacksTo(1)));

    // TOOLS
    public static final Item MAGNET = registerItem("magnet", properties -> new MagnetItem(properties.stacksTo(1)));

    public static final Item PAXEL = registerItem("paxel", properties ->
            new PaxelItem(ModToolMaterials.PAXEL_MATERIAL, 4.0f, -2.4f, properties));

    public static final Item STEEL_SWORD = registerItem("steel_sword", properties ->
            new Item(properties.sword(ModToolMaterials.STEEL, 3.0f, -2.4F).fireResistant().stacksTo(1)));

    public static final Item STEEL_PICKAXE = registerItem("steel_pickaxe", properties ->
            new Item(properties.pickaxe(ModToolMaterials.STEEL, -2.0f, -2.8f).fireResistant()));

    public static final Item STEEL_AXE = registerItem("steel_axe", properties ->
            new Item(properties.axe(ModToolMaterials.STEEL, 4.0f, -3.0f).fireResistant()));

    public static final Item STEEL_SHOVEL = registerItem("steel_shovel", properties ->
            new Item(properties.shovel(ModToolMaterials.STEEL, -4.0f, 0.0f).fireResistant()));

    public static final Item STEEL_HOE = registerItem("steel_hoe", properties ->
            new Item(properties.hoe(ModToolMaterials.STEEL, -4.0f, 0.0f).fireResistant()));

    public static final Item STEEL_INGOT = registerItem("steel_ingot", Item::new);

    // ELEMENTAL DRAGON UPGRADE TEMPLATES
    public static final Item ENERGY_DRAGON_ARMOR_UPGRADE = registerItem("energy_dragon_armor_upgrade",
            properties -> createDragonTemplate("energy_dragon", properties.rarity(Rarity.UNCOMMON))
    );

    public static final Item FIRE_DRAGON_ARMOR_UPGRADE = registerItem("fire_dragon_armor_upgrade",
            properties -> createDragonTemplate("fire_dragon", properties.rarity(Rarity.UNCOMMON))
    );

    public static final Item AIR_DRAGON_ARMOR_UPGRADE = registerItem("air_dragon_armor_upgrade",
            properties -> createDragonTemplate("air_dragon", properties.rarity(Rarity.UNCOMMON))
    );

    public static final Item WATER_DRAGON_ARMOR_UPGRADE = registerItem("water_dragon_armor_upgrade",
            properties -> createDragonTemplate("water_dragon", properties.rarity(Rarity.UNCOMMON))
    );

    public static final Item EARTH_DRAGON_ARMOR_UPGRADE = registerItem("earth_dragon_armor_upgrade",
            properties -> createDragonTemplate("earth_dragon", properties.rarity(Rarity.UNCOMMON))
    );

    // CORES
    public static final Item ENERGY_CORE = registerItem("energy_core", Item::new);
    public static final Item FIRE_CORE = registerItem("fire_core", Item::new);
    public static final Item AIR_CORE = registerItem("air_core", Item::new);
    public static final Item WATER_CORE = registerItem("water_core", Item::new);
    public static final Item EARTH_CORE = registerItem("earth_core", Item::new);

    // DRAGON SCALES
    public static final Item ENERGY_DRAGON_SCALE = registerItem("energy_dragon_scale", Item::new);
    public static final Item FIRE_DRAGON_SCALE = registerItem("fire_dragon_scale", Item::new);
    public static final Item AIR_DRAGON_SCALE = registerItem("air_dragon_scale", Item::new);
    public static final Item WATER_DRAGON_SCALE = registerItem("water_dragon_scale", Item::new);
    public static final Item EARTH_DRAGON_SCALE = registerItem("earth_dragon_scale", Item::new);

    // MISC ITEMS
    public static final Item BACKPACK_ITEM = registerItem("backpack", properties ->
            new BackpackItem(ModBlocks.BACKPACK_BLOCK, properties.stacksTo(1).component(DataComponents.MAX_STACK_SIZE, 128))
    );

    public static final Item BLUEBERRY = registerItem("blueberries", properties ->
            new BlockItem(ModBlocks.BLUEBERRY_BUSH_BLOCK, properties.food(ModFoods.BLUEBERRY, ModFoods.BLUEBERRY_CONSUMABLE))
    );

    // ENERGY DRAGON ARMOR
    public static final Item ENERGY_DRAGON_HELMET = registerItem("energy_dragon_helmet", properties ->
            new EnergyDragonArmorItem(properties.humanoidArmor(ModArmorMaterials.ENERGY_DRAGON_ARMOR_MATERIAL, ArmorType.HELMET)
                    .durability(ArmorType.HELMET.getDurability(ModArmorMaterials.BASE_DURABILITY))
                    .enchantable(20).fireResistant()
                    .delayedComponent(DataComponents.DAMAGE_RESISTANT, context -> new DamageResistant(context.getOrThrow(DamageTypeTags.IS_EXPLOSION))))
    );

    public static final Item ENERGY_DRAGON_CHESTPLATE = registerItem("energy_dragon_chestplate", properties ->
            new EnergyDragonArmorItem(properties.humanoidArmor(ModArmorMaterials.ENERGY_DRAGON_ARMOR_MATERIAL, ArmorType.CHESTPLATE)
                    .durability(ArmorType.CHESTPLATE.getDurability(ModArmorMaterials.BASE_DURABILITY))
                    .component(DataComponents.GLIDER, Unit.INSTANCE)
                    .enchantable(20).fireResistant()
                    .delayedComponent(DataComponents.DAMAGE_RESISTANT, context -> new DamageResistant(context.getOrThrow(DamageTypeTags.IS_EXPLOSION))))
    );

    public static final Item ENERGY_DRAGON_LEGGINGS = registerItem("energy_dragon_leggings", properties ->
            new EnergyDragonArmorItem(properties.humanoidArmor(ModArmorMaterials.ENERGY_DRAGON_ARMOR_MATERIAL, ArmorType.LEGGINGS)
                    .durability(ArmorType.LEGGINGS.getDurability(ModArmorMaterials.BASE_DURABILITY))
                    .enchantable(20).fireResistant()
                    .delayedComponent(DataComponents.DAMAGE_RESISTANT, context -> new DamageResistant(context.getOrThrow(DamageTypeTags.IS_EXPLOSION))))
    );

    public static final Item ENERGY_DRAGON_BOOTS = registerItem("energy_dragon_boots", properties ->
            new EnergyDragonArmorItem(properties.humanoidArmor(ModArmorMaterials.ENERGY_DRAGON_ARMOR_MATERIAL, ArmorType.BOOTS)
                    .durability(ArmorType.BOOTS.getDurability(ModArmorMaterials.BASE_DURABILITY))
                    .enchantable(20).fireResistant()
                    .delayedComponent(DataComponents.DAMAGE_RESISTANT, context -> new DamageResistant(context.getOrThrow(DamageTypeTags.IS_EXPLOSION))))
    );

    // FIRE DRAGON ARMOR
    public static final Item FIRE_DRAGON_HELMET = registerItem("fire_dragon_helmet", properties ->
            new DragonArmorItem(properties.humanoidArmor(ModArmorMaterials.FIRE_DRAGON_ARMOR_MATERIAL, ArmorType.HELMET)
                    .durability(ArmorType.HELMET.getDurability(ModArmorMaterials.BASE_DURABILITY))
                    .enchantable(20).fireResistant()
                    .delayedComponent(DataComponents.DAMAGE_RESISTANT, context -> new DamageResistant(context.getOrThrow(DamageTypeTags.IS_EXPLOSION))))
    );

    public static final Item FIRE_DRAGON_CHESTPLATE = registerItem("fire_dragon_chestplate", properties ->
            new DragonArmorItem(properties.humanoidArmor(ModArmorMaterials.FIRE_DRAGON_ARMOR_MATERIAL, ArmorType.CHESTPLATE)
                    .durability(ArmorType.CHESTPLATE.getDurability(ModArmorMaterials.BASE_DURABILITY))
                    .enchantable(20).fireResistant()
                    .delayedComponent(DataComponents.DAMAGE_RESISTANT, context -> new DamageResistant(context.getOrThrow(DamageTypeTags.IS_EXPLOSION))))
    );

    public static final Item FIRE_DRAGON_LEGGINGS = registerItem("fire_dragon_leggings", properties ->
            new DragonArmorItem(properties.humanoidArmor(ModArmorMaterials.FIRE_DRAGON_ARMOR_MATERIAL, ArmorType.LEGGINGS)
                    .durability(ArmorType.LEGGINGS.getDurability(ModArmorMaterials.BASE_DURABILITY))
                    .enchantable(20).fireResistant()
                    .delayedComponent(DataComponents.DAMAGE_RESISTANT, context -> new DamageResistant(context.getOrThrow(DamageTypeTags.IS_EXPLOSION))))
    );

    public static final Item FIRE_DRAGON_BOOTS = registerItem("fire_dragon_boots", properties ->
            new DragonArmorItem(properties.humanoidArmor(ModArmorMaterials.FIRE_DRAGON_ARMOR_MATERIAL, ArmorType.BOOTS)
                    .durability(ArmorType.BOOTS.getDurability(ModArmorMaterials.BASE_DURABILITY))
                    .enchantable(20).fireResistant()
                    .delayedComponent(DataComponents.DAMAGE_RESISTANT, context -> new DamageResistant(context.getOrThrow(DamageTypeTags.IS_EXPLOSION))))
    );

    // AIR DRAGON ARMOR
    public static final Item AIR_DRAGON_HELMET = registerItem("air_dragon_helmet", properties ->
            new DragonArmorItem(properties.humanoidArmor(ModArmorMaterials.AIR_DRAGON_ARMOR_MATERIAL, ArmorType.HELMET)
                    .durability(ArmorType.HELMET.getDurability(ModArmorMaterials.BASE_DURABILITY))
                    .enchantable(20).fireResistant()
                    .delayedComponent(DataComponents.DAMAGE_RESISTANT, context -> new DamageResistant(context.getOrThrow(DamageTypeTags.IS_EXPLOSION))))
    );

    public static final Item AIR_DRAGON_CHESTPLATE = registerItem("air_dragon_chestplate", properties ->
            new DragonArmorItem(properties.humanoidArmor(ModArmorMaterials.AIR_DRAGON_ARMOR_MATERIAL, ArmorType.CHESTPLATE)
                    .durability(ArmorType.CHESTPLATE.getDurability(ModArmorMaterials.BASE_DURABILITY))
                    .enchantable(20).fireResistant()
                    .delayedComponent(DataComponents.DAMAGE_RESISTANT, context -> new DamageResistant(context.getOrThrow(DamageTypeTags.IS_EXPLOSION))))
    );

    public static final Item AIR_DRAGON_LEGGINGS = registerItem("air_dragon_leggings", properties ->
            new DragonArmorItem(properties.humanoidArmor(ModArmorMaterials.AIR_DRAGON_ARMOR_MATERIAL, ArmorType.LEGGINGS)
                    .durability(ArmorType.LEGGINGS.getDurability(ModArmorMaterials.BASE_DURABILITY))
                    .enchantable(20).fireResistant()
                    .delayedComponent(DataComponents.DAMAGE_RESISTANT, context -> new DamageResistant(context.getOrThrow(DamageTypeTags.IS_EXPLOSION))))
    );

    public static final Item AIR_DRAGON_BOOTS = registerItem("air_dragon_boots", properties ->
            new DragonArmorItem(properties.humanoidArmor(ModArmorMaterials.AIR_DRAGON_ARMOR_MATERIAL, ArmorType.BOOTS)
                    .durability(ArmorType.BOOTS.getDurability(ModArmorMaterials.BASE_DURABILITY))
                    .enchantable(20).fireResistant()
                    .delayedComponent(DataComponents.DAMAGE_RESISTANT, context -> new DamageResistant(context.getOrThrow(DamageTypeTags.IS_EXPLOSION))))
    );

    // WATER DRAGON ARMOR
    public static final Item WATER_DRAGON_HELMET = registerItem("water_dragon_helmet", properties ->
            new DragonArmorItem(properties.humanoidArmor(ModArmorMaterials.WATER_DRAGON_ARMOR_MATERIAL, ArmorType.HELMET)
                    .durability(ArmorType.HELMET.getDurability(ModArmorMaterials.BASE_DURABILITY))
                    .enchantable(20).fireResistant()
                    .delayedComponent(DataComponents.DAMAGE_RESISTANT, context -> new DamageResistant(context.getOrThrow(DamageTypeTags.IS_EXPLOSION))))
    );

    public static final Item WATER_DRAGON_CHESTPLATE = registerItem("water_dragon_chestplate", properties ->
            new DragonArmorItem(properties.humanoidArmor(ModArmorMaterials.WATER_DRAGON_ARMOR_MATERIAL, ArmorType.CHESTPLATE)
                    .durability(ArmorType.CHESTPLATE.getDurability(ModArmorMaterials.BASE_DURABILITY))
                    .enchantable(20).fireResistant()
                    .delayedComponent(DataComponents.DAMAGE_RESISTANT, context -> new DamageResistant(context.getOrThrow(DamageTypeTags.IS_EXPLOSION))))
    );

    public static final Item WATER_DRAGON_LEGGINGS = registerItem("water_dragon_leggings", properties ->
            new DragonArmorItem(properties.humanoidArmor(ModArmorMaterials.WATER_DRAGON_ARMOR_MATERIAL, ArmorType.LEGGINGS)
                    .durability(ArmorType.LEGGINGS.getDurability(ModArmorMaterials.BASE_DURABILITY))
                    .enchantable(20).fireResistant()
                    .delayedComponent(DataComponents.DAMAGE_RESISTANT, context -> new DamageResistant(context.getOrThrow(DamageTypeTags.IS_EXPLOSION))))
    );

    public static final Item WATER_DRAGON_BOOTS = registerItem("water_dragon_boots", properties ->
            new DragonArmorItem(properties.humanoidArmor(ModArmorMaterials.WATER_DRAGON_ARMOR_MATERIAL, ArmorType.BOOTS)
                    .durability(ArmorType.BOOTS.getDurability(ModArmorMaterials.BASE_DURABILITY))
                    .enchantable(20).fireResistant()
                    .delayedComponent(DataComponents.DAMAGE_RESISTANT, context -> new DamageResistant(context.getOrThrow(DamageTypeTags.IS_EXPLOSION))))
    );

    // EARTH DRAGON ARMOR
    public static final Item EARTH_DRAGON_HELMET = registerItem("earth_dragon_helmet", properties ->
            new DragonArmorItem(properties.humanoidArmor(ModArmorMaterials.EARTH_DRAGON_ARMOR_MATERIAL, ArmorType.HELMET)
                    .durability(ArmorType.HELMET.getDurability(ModArmorMaterials.BASE_DURABILITY))
                    .enchantable(20).fireResistant()
                    .delayedComponent(DataComponents.DAMAGE_RESISTANT, context -> new DamageResistant(context.getOrThrow(DamageTypeTags.IS_EXPLOSION))))
    );

    public static final Item EARTH_DRAGON_CHESTPLATE = registerItem("earth_dragon_chestplate", properties ->
            new DragonArmorItem(properties.humanoidArmor(ModArmorMaterials.EARTH_DRAGON_ARMOR_MATERIAL, ArmorType.CHESTPLATE)
                    .durability(ArmorType.CHESTPLATE.getDurability(ModArmorMaterials.BASE_DURABILITY))
                    .enchantable(20).fireResistant()
                    .delayedComponent(DataComponents.DAMAGE_RESISTANT, context -> new DamageResistant(context.getOrThrow(DamageTypeTags.IS_EXPLOSION))))
    );

    public static final Item EARTH_DRAGON_LEGGINGS = registerItem("earth_dragon_leggings", properties ->
            new DragonArmorItem(properties.humanoidArmor(ModArmorMaterials.EARTH_DRAGON_ARMOR_MATERIAL, ArmorType.LEGGINGS)
                    .durability(ArmorType.LEGGINGS.getDurability(ModArmorMaterials.BASE_DURABILITY))
                    .enchantable(20).fireResistant()
                    .delayedComponent(DataComponents.DAMAGE_RESISTANT, context -> new DamageResistant(context.getOrThrow(DamageTypeTags.IS_EXPLOSION))))
    );

    public static final Item EARTH_DRAGON_BOOTS = registerItem("earth_dragon_boots", properties ->
            new DragonArmorItem(properties.humanoidArmor(ModArmorMaterials.EARTH_DRAGON_ARMOR_MATERIAL, ArmorType.BOOTS)
                    .durability(ArmorType.BOOTS.getDurability(ModArmorMaterials.BASE_DURABILITY))
                    .enchantable(20).fireResistant()
                    .delayedComponent(DataComponents.DAMAGE_RESISTANT, context -> new DamageResistant(context.getOrThrow(DamageTypeTags.IS_EXPLOSION))))
    );

    public static ResourceKey<@NotNull Item> getRK(Item item) {
        return BuiltInRegistries.ITEM.getResourceKey(item).get();
    }

    private static Item registerItem(String name, Function<Item.Properties, Item> function) {
        return Registry.register(BuiltInRegistries.ITEM, Identifier.fromNamespaceAndPath(VanillaExpansion.MOD_ID, name),
                function.apply(new Item.Properties().setId(ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(VanillaExpansion.MOD_ID, name)))));
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
                Component.translatable("upgrade." + VanillaExpansion.MOD_ID + "." + elementKey + ".applies_to").withStyle(ChatFormatting.BLUE),
                Component.translatable("upgrade." + VanillaExpansion.MOD_ID + "." + elementKey + ".ingredients").withStyle(ChatFormatting.BLUE),
                Component.translatable("upgrade." + VanillaExpansion.MOD_ID + "." + elementKey + ".base_slot_description"),
                Component.translatable("upgrade." + VanillaExpansion.MOD_ID + "." + elementKey + ".additions_slot_description"),
                createDragonUpgradeIconList(),
                createDragonUpgradeMaterialList(),
                properties
        );
    }

    public static void registerModItems() {
    }
}