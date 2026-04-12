package com.chris.vanilla_expansion.item;

import com.chris.vanilla_expansion.VanillaExpansion;
import com.chris.vanilla_expansion.block.ModBlocks;
import com.chris.vanilla_expansion.component.ModDataComponentTypes;
import com.chris.vanilla_expansion.entity.ModEntities;
import com.chris.vanilla_expansion.item.custom.BackpackItem;
import com.chris.vanilla_expansion.item.custom.MagnetItem;
import com.chris.vanilla_expansion.item.custom.PaxelItem;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.DamageResistant;

import java.util.function.Function;

public class ModItems {

    public static final Item ENERGY_DRAGON_SPAWN_EGG = register("energy_dragon_spawn_egg",
            SpawnEggItem::new,
            new Item.Properties().spawnEgg(ModEntities.ENERGY_DRAGON));


    public static final Item STORAGE_UPGRADE = register("storage_upgrade", Item::new, new Item.Properties().stacksTo(1));
    public static final Item STACK_UPGRADE = register("stack_upgrade", Item::new, new Item.Properties().stacksTo(1));
    public static final Item CRAFTING_UPGRADE = register("crafting_upgrade", Item::new, new Item.Properties().stacksTo(1));




    public static final Item PAXEL = register("paxel", settings ->
                    new PaxelItem(ModToolMaterials.PAXEL_MATERIAL, 4.0f, -2.4f, settings),
            new Item.Properties().stacksTo(1).fireResistant().rarity(Rarity.EPIC)
                    .delayedComponent(DataComponents.DAMAGE_RESISTANT, context -> new DamageResistant(context.getOrThrow(DamageTypeTags.IS_EXPLOSION)))
                    );

    public static final Item MAGNET = register("magnet", MagnetItem::new, new Item.Properties().stacksTo(1));


    public static final Item STEEL_SWORD = register("steel_sword", Item::new,
            new Item.Properties().sword(ModToolMaterials.STEEL, 3.0f, -2.4f).fireResistant().stacksTo(1));
    public static final Item STEEL_PICKAXE = register("steel_pickaxe", Item::new,
            new Item.Properties().pickaxe(ModToolMaterials.STEEL, 1.0f, -2.8f));
    public static final Item STEEL_AXE = register("steel_axe", settings ->
            new AxeItem(ModToolMaterials.STEEL, 5.0f, -3.0f, settings),
            new Item.Properties().fireResistant());
public static final Item STEEL_SHOVEL = register("steel_shovel", settings ->
            new ShovelItem(ModToolMaterials.STEEL, 5.0f, -3.0f, settings),
            new Item.Properties().fireResistant());

public static final Item STEEL_HOE = register("steel_hoe", settings ->
            new HoeItem(ModToolMaterials.STEEL, 5.0f, -3.0f, settings),
            new Item.Properties().fireResistant());







    public static final Item STEEL_INGOT = register("steel_ingot", Item::new, new Item.Properties());

    public static final Item ENERGY_CORE = register("energy_core", Item::new, new Item.Properties());
    public static final Item FIRE_CORE = register("fire_core", Item::new, new Item.Properties());
    public static final Item AIR_CORE = register("air_core", Item::new, new Item.Properties());
    public static final Item WATER_CORE = register("water_core", Item::new, new Item.Properties());
    public static final Item EARTH_CORE = register("earth_core", Item::new, new Item.Properties());

    public static final Item BACKPACK_ITEM = register(
            "backpack",
            (settings) -> new BackpackItem(ModBlocks.BACKPACK_BLOCK, settings),
            new Item.Properties().stacksTo(1).component(DataComponents.MAX_STACK_SIZE, 128).stacksTo(1)
    );





    public static <T extends Item> T register(String name, Function<Item.Properties, T> itemFactory, Item.Properties settings) {
        ResourceKey<Item> itemKey = ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(VanillaExpansion.MOD_ID, name));
        T item = itemFactory.apply(settings.setId(itemKey));
        Registry.register(BuiltInRegistries.ITEM, itemKey, item);
        return item;
    }

    public static void registerModItems() {

    }
}

