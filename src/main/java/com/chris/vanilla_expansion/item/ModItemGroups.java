package com.chris.vanilla_expansion.item;


import com.chris.vanilla_expansion.VanillaExpansion;
import com.chris.vanilla_expansion.block.ModBlocks;
import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.fabricmc.fabric.api.creativetab.v1.FabricCreativeModeTab;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class ModItemGroups {
    public static final ResourceKey<@NotNull CreativeModeTab> VE_TOOL_GROUP = ResourceKey.create(
        Registries.CREATIVE_MODE_TAB,
            Identifier.fromNamespaceAndPath(VanillaExpansion.MOD_ID, "vanilla_expansion_tools")
    );
    public static final ResourceKey<@NotNull CreativeModeTab> VE_ITEM_GROUP = ResourceKey.create(
            Registries.CREATIVE_MODE_TAB,
            Identifier.fromNamespaceAndPath(VanillaExpansion.MOD_ID, "vanilla_expansion_items")
    );
    public static final ResourceKey<@NotNull CreativeModeTab> VE_BLOCK_GROUP = ResourceKey.create(
            Registries.CREATIVE_MODE_TAB,
            Identifier.fromNamespaceAndPath(VanillaExpansion.MOD_ID, "vanilla_expansion_blocks")
    );

    public static final CreativeModeTab VANILLA_EXPANSION_TOOLS = FabricCreativeModeTab.builder()
            .icon(() -> new ItemStack(ModItems.PAXEL))
            .title(Component.translatable("itemgroup.vanilla_expansion.vanilla_expansion_tools"))
            .build();

    public static final CreativeModeTab VANILLA_EXPANSION_ITEMS = FabricCreativeModeTab.builder()
            .icon(() -> new ItemStack(ModItems.ENERGY_CORE))
            .title(Component.translatable("itemgroup.vanilla_expansion.vanilla_expansion_items"))
            .build();

    public static final CreativeModeTab VANILLA_EXPANSION_BLOCKS = FabricCreativeModeTab.builder()
            .icon(() -> new ItemStack(ModBlocks.STEEL_BLOCK.asItem()))
            .title(Component.translatable("itemgroup.vanilla_expansion.vanilla_expansion_blocks"))
            .build();

    public static void register() {
        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, VE_TOOL_GROUP, VANILLA_EXPANSION_TOOLS);
        CreativeModeTabEvents.modifyOutputEvent(VE_TOOL_GROUP).register(entries -> {
            entries.accept(ModItems.PAXEL);
            entries.accept(ModItems.MAGNET);
            entries.accept(ModItems.STEEL_AXE);
            entries.accept(ModItems.STEEL_PICKAXE);
            entries.accept(ModItems.STEEL_SWORD);
            entries.accept(ModItems.STEEL_HOE);
            entries.accept(ModItems.STEEL_SHOVEL);


            entries.accept(ModItems.BACKPACK_ITEM);
            entries.accept(ModItems.STACK_UPGRADE);
            entries.accept(ModItems.STORAGE_UPGRADE);
            entries.accept(ModItems.CRAFTING_UPGRADE);

            entries.accept(ModItems.STORAGE_BLOCK_UPGRADE);
        });
        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, VE_ITEM_GROUP, VANILLA_EXPANSION_ITEMS);
        CreativeModeTabEvents.modifyOutputEvent(VE_ITEM_GROUP).register(entries -> {
            entries.accept(ModItems.ENERGY_CORE);
            entries.accept(ModItems.EARTH_CORE);
            entries.accept(ModItems.FIRE_CORE);
            entries.accept(ModItems.WATER_CORE);
            entries.accept(ModItems.AIR_CORE);
            entries.accept(ModItems.STEEL_INGOT);
            entries.accept(ModItems.ENERGY_DRAGON_SPAWN_EGG);
            entries.accept(ModItems.AIR_DRAGON_SPAWN_EGG);
            entries.accept(ModItems.FIRE_DRAGON_SPAWN_EGG);

            entries.accept(ModItems.ENERGY_DRAGON_SCALE);
            entries.accept(ModItems.FIRE_DRAGON_SCALE);
            entries.accept(ModItems.AIR_DRAGON_SCALE);
            entries.accept(ModItems.EARTH_DRAGON_SCALE);
            entries.accept(ModItems.WATER_DRAGON_SCALE);

            entries.accept(ModItems.ENERGY_DRAGON_ARMOR_UPGRADE);
            entries.accept(ModItems.FIRE_DRAGON_ARMOR_UPGRADE);
            entries.accept(ModItems.AIR_DRAGON_ARMOR_UPGRADE);
            entries.accept(ModItems.WATER_DRAGON_ARMOR_UPGRADE);
            entries.accept(ModItems.EARTH_DRAGON_ARMOR_UPGRADE);

            entries.accept(ModItems.ENERGY_DRAGON_HELMET);
            entries.accept(ModItems.ENERGY_DRAGON_CHESTPLATE);
            entries.accept(ModItems.ENERGY_DRAGON_LEGGINGS);
            entries.accept(ModItems.ENERGY_DRAGON_BOOTS);

            entries.accept(ModItems.FIRE_DRAGON_HELMET);
            entries.accept(ModItems.FIRE_DRAGON_CHESTPLATE);
            entries.accept(ModItems.FIRE_DRAGON_LEGGINGS);
            entries.accept(ModItems.FIRE_DRAGON_BOOTS);

            entries.accept(ModItems.AIR_DRAGON_HELMET);
            entries.accept(ModItems.AIR_DRAGON_CHESTPLATE);
            entries.accept(ModItems.AIR_DRAGON_LEGGINGS);
            entries.accept(ModItems.AIR_DRAGON_BOOTS);

            entries.accept(ModItems.WATER_DRAGON_HELMET);
            entries.accept(ModItems.WATER_DRAGON_CHESTPLATE);
            entries.accept(ModItems.WATER_DRAGON_LEGGINGS);
            entries.accept(ModItems.WATER_DRAGON_BOOTS);

            entries.accept(ModItems.EARTH_DRAGON_HELMET);
            entries.accept(ModItems.EARTH_DRAGON_CHESTPLATE);
            entries.accept(ModItems.EARTH_DRAGON_LEGGINGS);
            entries.accept(ModItems.EARTH_DRAGON_BOOTS);
        });
        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, VE_BLOCK_GROUP, VANILLA_EXPANSION_BLOCKS);
        CreativeModeTabEvents.modifyOutputEvent(VE_BLOCK_GROUP).register(entries -> {
            entries.accept(ModBlocks.STEEL_BLOCK);
            entries.accept(ModBlocks.STORAGE_CRATE);

            entries.accept(ModBlocks.STORAGE_CONTROLLER);
            entries.accept(ModBlocks.STORAGE_TRIM);
            entries.accept(ModBlocks.STORAGE_INTERFACE);

            entries.accept(ModBlocks.SAND_GENERATOR_BLOCK);
            entries.accept(ModBlocks.RED_MARKER);
            entries.accept(ModBlocks.YELLOW_MARKER);
            entries.accept(ModBlocks.GREEN_MARKER);
            entries.accept(ModBlocks.CYAN_MARKER);
            entries.accept(ModBlocks.BLUE_MARKER);
            entries.accept(ModBlocks.MAGENTA_MARKER);
        });
    }
}
