package com.chris.vanilla_expansion.block;

import com.chris.vanilla_expansion.VanillaExpansion;
import com.chris.vanilla_expansion.block.dragon.*;
import com.chris.vanilla_expansion.block.storage.block.*;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

public class ModBlocks {

    // MARKER BLOCKS
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

    // DRAGON EGGS
    public static final Block ENERGY_DRAGON_EGG = register("energy_dragon_egg",
            EnergyDragonEgg::new,
            BlockBehaviour.Properties.of().noOcclusion().strength(0.5f),
            true);
    public static final Block AIR_DRAGON_EGG = register("air_dragon_egg",
            AirDragonEgg::new,
            BlockBehaviour.Properties.of().noOcclusion().strength(0.5f),
            true);
    public static final Block FIRE_DRAGON_EGG = register("fire_dragon_egg",
            FireDragonEgg::new,
            BlockBehaviour.Properties.of().noOcclusion().strength(0.5f),
            true);
   public static final Block WATER_DRAGON_EGG = register("water_dragon_egg",
            WaterDragonEgg::new,
            BlockBehaviour.Properties.of().noOcclusion().strength(0.5f),
            true);
    public static final Block EARTH_DRAGON_EGG = register("earth_dragon_egg",
            EarthDragonEgg::new,
            BlockBehaviour.Properties.of().noOcclusion().strength(0.5f),
            true);

    // OTHER BLOCKS
    public static final Block BACKPACK_BLOCK = register("backpack_block",
            BackpackBlock::new,
            BlockBehaviour.Properties.of().strength(1.0f).noOcclusion(),
            true);

    public static final Block STORAGE_CRATE = register("storage_crate",
            StorageCrateBlock::new,
            BlockBehaviour.Properties.of().strength(1.0f).noOcclusion(),
            true);

    public static final Block SAND_GENERATOR_BLOCK = register("sand_generator",
            SandGeneratorBlock::new,
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

    public static final Block STEEL_BLOCK = register("steel_block",
            Block::new,
            BlockBehaviour.Properties.of().sound(SoundType.IRON).strength(6f),
            true);

    public static ResourceKey<@NotNull Block> getRK(Block block) {
        return BuiltInRegistries.BLOCK.getResourceKey(block).orElseThrow();
    }

    private static Block register(String name, Function<BlockBehaviour.Properties, Block> blockFactory,
                                  BlockBehaviour.Properties settings, boolean shouldRegisterItem) {
        ResourceKey<@NotNull Block> blockKey = keyOfBlock(name);

        // Pass blockKey into the existing settings chain:
        Block block = blockFactory.apply(settings.setId(blockKey));

        if (shouldRegisterItem) {
            ResourceKey<@NotNull Item> itemKey = keyOfItem(name);

            BlockItem blockItem = new BlockItem(block, new Item.Properties().setId(itemKey).useBlockDescriptionPrefix());
            Registry.register(BuiltInRegistries.ITEM, itemKey, blockItem);
        }

        return Registry.register(BuiltInRegistries.BLOCK, blockKey, block);
    }

    private static ResourceKey<@NotNull Block> keyOfBlock(String name) {
        return ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(VanillaExpansion.MOD_ID, name)); //
    }

    private static ResourceKey<@NotNull Item> keyOfItem(String name) {
        return ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(VanillaExpansion.MOD_ID, name)); //
    }

    public static void registerModBlocks() {
        // Triggers class loading
    }
}