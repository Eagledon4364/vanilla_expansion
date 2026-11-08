package com.chris.vanilla_expansion_storage.item;

import com.chris.vanilla_expansion.VanillaExpansion;
import com.chris.vanilla_expansion_storage.VanillaExpansionStorage;
import com.chris.vanilla_expansion_storage.block.ModStorageBlocks;
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

public class ModStorageItemGroups {
    public static final ResourceKey<@NotNull CreativeModeTab> VE_STORAGE_GROUP = ResourceKey.create(
            Registries.CREATIVE_MODE_TAB,
            Identifier.fromNamespaceAndPath(VanillaExpansion.MOD_ID, "vanilla_expansion_storage_blocks")
    );

    public static final CreativeModeTab VANILLA_EXPANSION_STORAGE_BLOCKS = FabricCreativeModeTab.builder()
            .icon(() -> new ItemStack(ModStorageBlocks.STORAGE_CONTROLLER.asItem()))
            .title(Component.translatable("itemgroup.vanilla_expansion.vanilla_expansion_storage_blocks"))
            .build();

    public static void register() {

        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, VE_STORAGE_GROUP, VANILLA_EXPANSION_STORAGE_BLOCKS);
        CreativeModeTabEvents.modifyOutputEvent(VE_STORAGE_GROUP).register(entries -> {

            entries.accept(ModStorageItems.KEY);
            entries.accept(ModStorageItems.STORAGE_BLOCK_UPGRADE);

            entries.accept(ModStorageBlocks.STORAGE_CRATE);
            entries.accept(ModStorageBlocks.STORAGE_CONTROLLER);
            entries.accept(ModStorageBlocks.STORAGE_TRIM);
            entries.accept(ModStorageBlocks.STORAGE_INTERFACE);
            entries.accept(ModStorageBlocks.CRAFTING_INTERFACE);
            entries.accept(ModStorageBlocks.STORAGE_CABLE);
            entries.accept(ModStorageBlocks.STORAGE_EXPORTER);
            entries.accept(ModStorageBlocks.STORAGE_IMPORTER);

        });
    }
}
