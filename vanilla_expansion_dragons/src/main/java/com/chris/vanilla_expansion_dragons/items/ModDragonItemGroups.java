package com.chris.vanilla_expansion_dragons.items;

import com.chris.vanilla_expansion.VanillaExpansion;
import com.chris.vanilla_expansion.block.ModBlocks;
import com.chris.vanilla_expansion.item.ModItems;
import com.chris.vanilla_expansion_dragons.VanillaExpansionDragons;
import com.chris.vanilla_expansion_dragons.blocks.ModDragonBlocks;
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

public class ModDragonItemGroups {
    public static final ResourceKey<@NotNull CreativeModeTab> VED_DRAGONS_GROUP = ResourceKey.create(
            Registries.CREATIVE_MODE_TAB,
            Identifier.fromNamespaceAndPath(VanillaExpansionDragons.MOD_ID, "vanilla_expansion_dragons")
    );

    public static final CreativeModeTab VANILLA_EXPANSION_DRAGONS = FabricCreativeModeTab.builder()
            .icon(() -> new ItemStack(ModDragonBlocks.ELDERWING_DRAGON_EGG.asItem()))
            .title(Component.translatable("itemgroup.vanilla_expansion_dragons.vanilla_expansion_dragons"))
            .build();

    public static void register() {
        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, VED_DRAGONS_GROUP, VANILLA_EXPANSION_DRAGONS);
        CreativeModeTabEvents.modifyOutputEvent(VED_DRAGONS_GROUP).register(entries -> {
            entries.accept(ModDragonBlocks.ELDERWING_DRAGON_EGG);
        });
    }
}
