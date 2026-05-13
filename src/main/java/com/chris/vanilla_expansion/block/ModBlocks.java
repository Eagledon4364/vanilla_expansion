package com.chris.vanilla_expansion.block;

import com.chris.vanilla_expansion.VanillaExpansion;
import com.chris.vanilla_expansion.block.storage.StorageAccessBlock;
import com.chris.vanilla_expansion.block.storage.StorageConnectBlock;
import com.chris.vanilla_expansion.block.storage.StorageControllerBlock;
import com.chris.vanilla_expansion.block.storage.StorageCrateBlock;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

public class ModBlocks {

    //MARKER BLOCKS
    public static final Block RED_MARKER = register("red_marker",
            Block::new,
            BlockBehaviour.Properties.of().strength(0).instabreak().mapColor(MapColor.COLOR_RED),
            true);
    public static final Block YELLOW_MARKER = register("yellow_marker",
            Block::new,
            BlockBehaviour.Properties.of().strength(0).instabreak().mapColor(MapColor.COLOR_YELLOW),
            true);
    public static final Block GREEN_MARKER = register("green_marker",
            Block::new,
            BlockBehaviour.Properties.of().strength(0).instabreak().mapColor(MapColor.COLOR_GREEN),
            true);
    public static final Block CYAN_MARKER = register("cyan_marker",
            Block::new,
            BlockBehaviour.Properties.of().strength(0).instabreak().mapColor(MapColor.COLOR_CYAN),
            true);
    public static final Block BLUE_MARKER = register("blue_marker",
            Block::new,
            BlockBehaviour.Properties.of().strength(0).instabreak().mapColor(MapColor.COLOR_BLUE),
            true);
    public static final Block MAGENTA_MARKER = register("magenta_marker",
            Block::new,
            BlockBehaviour.Properties.of().strength(0).instabreak().mapColor(MapColor.COLOR_MAGENTA),
            true);



    //OTHER BLOCKS


    public static final Block BACKPACK_BLOCK = register(
            "backpack_block",
            BackpackBlock::new,
            BlockBehaviour.Properties.of().strength(1.0f).noOcclusion(),
            true
    );
    public static final Block STORAGE_CONNECT = register("storage_connect", StorageConnectBlock::new,
            BlockBehaviour.Properties.of().strength(1.0f).noOcclusion(),
            true
    );

    public static final Block STORAGE_CRATE = register("storage_crate", StorageCrateBlock::new,
            BlockBehaviour.Properties.of().strength(1.0f).noOcclusion(),
            true
    );

    // FIX: Swapped these to match their actual Block classes
    public static final Block STORAGE_CONTROLLER = register("storage_controller", StorageControllerBlock::new,
            BlockBehaviour.Properties.of().strength(1.0f).noOcclusion(),
            true
    );

    public static final Block STORAGE_ACCESS = register("storage_access", StorageAccessBlock::new,
            BlockBehaviour.Properties.of().strength(1.0f).noOcclusion(),
            true
    );

    public static Block STEEL_BLOCK = register("steel_block", Block::new,
            BlockBehaviour.Properties.of().sound(SoundType.IRON).strength(6f), true);

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
        return ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(VanillaExpansion.MOD_ID, name));
    }

    private static ResourceKey<@NotNull Item> keyOfItem(String name) {
        return ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(VanillaExpansion.MOD_ID, name));
    }

    public static void registerModBlocks() {
    }
}
