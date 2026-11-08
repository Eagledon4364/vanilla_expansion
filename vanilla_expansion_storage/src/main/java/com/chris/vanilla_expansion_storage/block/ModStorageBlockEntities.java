package com.chris.vanilla_expansion_storage.block;

import com.chris.vanilla_expansion.VanillaExpansion;
import com.chris.vanilla_expansion.block.ModBlocks;
import com.chris.vanilla_expansion_storage.block.entity.*;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import org.jetbrains.annotations.NotNull;

public class ModStorageBlockEntities {

    public static final BlockEntityType<@NotNull StorageCrateBlockEntity> STORAGE_CRATE_BE = register("storage_crate",
            StorageCrateBlockEntity::new, ModStorageBlocks.STORAGE_CRATE);

    public static final BlockEntityType<@NotNull StorageControllerBlockEntity> STORAGE_CONTROLLER =
            register("storage_controller_block", StorageControllerBlockEntity::new, ModStorageBlocks.STORAGE_CONTROLLER);

    public static final BlockEntityType<@NotNull StorageInterfaceBlockEntity> STORAGE_INTERFACE =
            register("storage_interface_block", StorageInterfaceBlockEntity::new, ModStorageBlocks.STORAGE_INTERFACE);

    public static final BlockEntityType<@NotNull CraftingInterfaceBlockEntity> CRAFTING_INTERFACE =
            register("crafting_interface_block", CraftingInterfaceBlockEntity::new, ModStorageBlocks.CRAFTING_INTERFACE);

    public static final BlockEntityType<@NotNull StorageImporterBlockEntity> STORAGE_IMPORTER_BE =
            register("storage_importer", StorageImporterBlockEntity::new, ModStorageBlocks.STORAGE_IMPORTER);

    public static final BlockEntityType<@NotNull StorageExporterBlockEntity> STORAGE_EXPORTER_BE =
            register("storage_exporter", StorageExporterBlockEntity::new, ModStorageBlocks.STORAGE_EXPORTER);

    private static <T extends BlockEntity> BlockEntityType<@NotNull T> register(
            String name,
            FabricBlockEntityTypeBuilder.Factory<? extends @NotNull T> entityFactory,
            Block... blocks
    ) {
        Identifier id = Identifier.fromNamespaceAndPath(VanillaExpansion.MOD_ID, name);
        return Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, id, FabricBlockEntityTypeBuilder.<T>create(entityFactory, blocks).build());
    }

    public static void register() {

    }
}
