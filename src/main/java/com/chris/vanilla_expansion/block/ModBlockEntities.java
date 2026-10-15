package com.chris.vanilla_expansion.block;

import com.chris.vanilla_expansion.VanillaExpansion;
import com.chris.vanilla_expansion.block.dragon.DragonEggBlockEntity;
import com.chris.vanilla_expansion.block.entity.BackpackBlockEntity;
import com.chris.vanilla_expansion.block.entity.SandGeneratorBlockEntity;
import com.chris.vanilla_expansion.block.entity.ToolCraftingStationBLockEntity;
import com.chris.vanilla_expansion.block.storage.entity.*;
import com.chris.vanilla_expansion.entity.ModEntities;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.transfer.v1.item.ContainerStorage;
import net.fabricmc.fabric.api.transfer.v1.item.ItemStorage;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import org.jetbrains.annotations.NotNull;

public class ModBlockEntities {

    public static void register() {
        ItemStorage.SIDED.registerForBlockEntity(ContainerStorage::of, BACKPACK_BLOCK_ENTITY);
    }


    public static final BlockEntityType<@NotNull DragonEggBlockEntity> ENERGY_DRAGON_EGG_BE =
            register("energy_dragon_egg", (pos, state) -> new DragonEggBlockEntity(
                    ModBlockEntities.ENERGY_DRAGON_EGG_BE, pos, state, ModEntities.ENERGY_DRAGON, 24000
            ), ModBlocks.ENERGY_DRAGON_EGG);

    public static final BlockEntityType<@NotNull DragonEggBlockEntity> AIR_DRAGON_EGG_BE =
            register("air_dragon_egg", (pos, state) -> new DragonEggBlockEntity(
                    ModBlockEntities.AIR_DRAGON_EGG_BE, pos, state, ModEntities.AIR_DRAGON, 24000
            ), ModBlocks.AIR_DRAGON_EGG);

    public static final BlockEntityType<@NotNull DragonEggBlockEntity> EARTH_DRAGON_EGG_BE =
            register("earth_dragon_egg", (pos, state) -> new DragonEggBlockEntity(
                    ModBlockEntities.EARTH_DRAGON_EGG_BE, pos, state, ModEntities.EARTH_DRAGON, 24000
            ), ModBlocks.EARTH_DRAGON_EGG);

    public static final BlockEntityType<@NotNull DragonEggBlockEntity> FIRE_DRAGON_EGG_BE =
            register("fire_dragon_egg", (pos, state) -> new DragonEggBlockEntity(
                    ModBlockEntities.FIRE_DRAGON_EGG_BE, pos, state, ModEntities.FIRE_DRAGON, 24000
            ), ModBlocks.FIRE_DRAGON_EGG);

    public static final BlockEntityType<@NotNull DragonEggBlockEntity> WATER_DRAGON_EGG_BE =
            register("water_dragon_egg", (pos, state) -> new DragonEggBlockEntity(
                    ModBlockEntities.WATER_DRAGON_EGG_BE, pos, state, ModEntities.WATER_DRAGON, 24000
            ), ModBlocks.WATER_DRAGON_EGG);

    public static final BlockEntityType<@NotNull StorageCrateBlockEntity> STORAGE_CRATE_BE = register("storage_crate",
            StorageCrateBlockEntity::new, ModBlocks.STORAGE_CRATE);

    public static final BlockEntityType<@NotNull BackpackBlockEntity> BACKPACK_BLOCK_ENTITY =
            register("backpack_block", BackpackBlockEntity::new, ModBlocks.BACKPACK_BLOCK);

    public static final BlockEntityType<@NotNull SandGeneratorBlockEntity> SANDGENERATOR_BE =
            register("sand_generator_block", SandGeneratorBlockEntity::new, ModBlocks.SAND_GENERATOR_BLOCK);

    public static final BlockEntityType<@NotNull StorageControllerBlockEntity> STORAGE_CONTROLLER =
            register("storage_controller_block", StorageControllerBlockEntity::new, ModBlocks.STORAGE_CONTROLLER);

    public static final BlockEntityType<@NotNull StorageInterfaceBlockEntity> STORAGE_INTERFACE =
            register("storage_interface_block", StorageInterfaceBlockEntity::new, ModBlocks.STORAGE_INTERFACE);

    public static final BlockEntityType<@NotNull CraftingInterfaceBlockEntity> CRAFTING_INTERFACE =
            register("crafting_interface_block", CraftingInterfaceBlockEntity::new, ModBlocks.CRAFTING_INTERFACE);

    public static final BlockEntityType<@NotNull StorageImporterBlockEntity> STORAGE_IMPORTER_BE =
            register("storage_importer", StorageImporterBlockEntity::new, ModBlocks.STORAGE_IMPORTER);

    public static final BlockEntityType<@NotNull StorageExporterBlockEntity> STORAGE_EXPORTER_BE =
            register("storage_exporter", StorageExporterBlockEntity::new, ModBlocks.STORAGE_EXPORTER);

    public static final BlockEntityType<@NotNull ToolCraftingStationBLockEntity> TOOL_CRAFTING_STATION_BE =
            register("tool_crafting_station_block", ToolCraftingStationBLockEntity::new, ModBlocks.TOOL_CRAFTING_STATION);

    private static <T extends BlockEntity> BlockEntityType<@NotNull T> register(
            String name,
            FabricBlockEntityTypeBuilder.Factory<? extends @NotNull T> entityFactory,
            Block... blocks
    ) {
        Identifier id = Identifier.fromNamespaceAndPath(VanillaExpansion.MOD_ID, name);
        return Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, id, FabricBlockEntityTypeBuilder.<T>create(entityFactory, blocks).build());
    }
}