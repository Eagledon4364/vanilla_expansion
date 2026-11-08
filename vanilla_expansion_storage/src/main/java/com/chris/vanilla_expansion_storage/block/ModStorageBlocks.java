package com.chris.vanilla_expansion_storage.block;

import com.chris.vanilla_expansion.VanillaExpansion;
import com.chris.vanilla_expansion.block.SandGeneratorBlock;
import com.chris.vanilla_expansion.config.VanillaExpansionConfig;
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

public class ModStorageBlocks {
    public static final Block STORAGE_CRATE = register("storage_crate",
            StorageCrateBlock::new,
            BlockBehaviour.Properties.of().strength(1.0f).noOcclusion(),
            true);

    public static final Block STORAGE_CABLE = register("storage_cable",
            StorageCable::new,
            BlockBehaviour.Properties.of().strength(0.5f).noOcclusion(),
            VanillaExpansionConfig.get().enableStorage);

    public static final Block STORAGE_IMPORTER = register("storage_importer",
            StorageImporter::new,
            BlockBehaviour.Properties.of().strength(0.5f).noOcclusion(),
            true);

    public static final Block STORAGE_EXPORTER = register("storage_exporter",
            StorageExporter::new,
            BlockBehaviour.Properties.of().strength(0.5f).noOcclusion(),
            true);

    public static final Block STORAGE_INTERFACE = register("storage_interface",
            StorageInterface::new,
            BlockBehaviour.Properties.of().strength(0.5f).noOcclusion(),
            true);

    public static final Block CRAFTING_INTERFACE = register("crafting_interface",
            CraftingInterface::new,
            BlockBehaviour.Properties.of().strength(0.5f).noOcclusion(),
            true);

    public static final Block STORAGE_CONTROLLER = register("storage_controller",
            StorageController::new,
            BlockBehaviour.Properties.of().strength(0.5f).noOcclusion(),
            true);

    public static final Block STORAGE_TRIM = register("storage_trim",
            StorageTrim::new,
            BlockBehaviour.Properties.of().strength(0.5f).noOcclusion(),
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
        return ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(VanillaExpansion.MOD_ID, name));
    }

    private static ResourceKey<@NotNull Item> keyOfItem(String name) {
        return ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(VanillaExpansion.MOD_ID, name));
    }

    public static void registerBlocks() {

    }
}
