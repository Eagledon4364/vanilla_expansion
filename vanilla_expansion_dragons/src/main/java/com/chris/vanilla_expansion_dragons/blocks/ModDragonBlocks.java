package com.chris.vanilla_expansion_dragons.blocks;

import com.chris.vanilla_expansion_dragons.VanillaExpansionDragons;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

public class ModDragonBlocks {
    public static Block ELDERWING_DRAGON_EGG = register("elderwing_dragon_egg", ElderWingDragonEgg::new,
            BlockBehaviour.Properties.of().noOcclusion().strength(0.5f),
            true);

    public static ResourceKey<@NotNull Block> getRK(Block block) {
        return BuiltInRegistries.BLOCK.getResourceKey(block).orElseThrow();
    }

    private static Block register(String name, Function<BlockBehaviour.Properties, Block> blockFactory,
                                  BlockBehaviour.Properties settings, boolean shouldRegisterItem) {
        ResourceKey<@NotNull Block> blockKey = keyOfBlock(name);

        Block block = blockFactory.apply(settings.setId(blockKey));

        if (shouldRegisterItem) {
            ResourceKey<@NotNull Item> itemKey = keyOfItem(name);

            BlockItem blockItem = new BlockItem(block, new Item.Properties().setId(itemKey).useBlockDescriptionPrefix());
            Registry.register(BuiltInRegistries.ITEM, itemKey, blockItem);
        }

        return Registry.register(BuiltInRegistries.BLOCK, blockKey, block);
    }

    private static ResourceKey<@NotNull Block> keyOfBlock(String name) {
        return ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(VanillaExpansionDragons.MOD_ID, name));
    }

    private static ResourceKey<@NotNull Item> keyOfItem(String name) {
        return ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(VanillaExpansionDragons.MOD_ID, name));
    }

    public static void registerModBlocks() {
    }
}
