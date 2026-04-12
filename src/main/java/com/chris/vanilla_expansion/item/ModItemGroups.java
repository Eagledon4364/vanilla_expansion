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
import net.minecraft.world.item.Items;

public class ModItemGroups {
    public static final ResourceKey<CreativeModeTab> VE_TOOL_GROUP = ResourceKey.create(
        Registries.CREATIVE_MODE_TAB,
            Identifier.fromNamespaceAndPath(VanillaExpansion.MOD_ID, "vanilla_expansion_tools")
    );
    public static final ResourceKey<CreativeModeTab> VE_ITEM_GROUP = ResourceKey.create(
            Registries.CREATIVE_MODE_TAB,
            Identifier.fromNamespaceAndPath(VanillaExpansion.MOD_ID, "vanilla_expansion_items")
    );
    public static final ResourceKey<CreativeModeTab> VE_BLOCK_GROUP = ResourceKey.create(
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
        });
        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, VE_BLOCK_GROUP, VANILLA_EXPANSION_BLOCKS);
        CreativeModeTabEvents.modifyOutputEvent(VE_BLOCK_GROUP).register(entries -> {
            entries.accept(ModBlocks.STEEL_BLOCK);
        });
    }
}
