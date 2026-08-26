package com.chris.vanilla_expansion.block;

import com.chris.vanilla_expansion.VanillaExpansion;
import com.chris.vanilla_expansion.block.entity.BackpackBlockEntity;
import com.chris.vanilla_expansion.block.entity.SandGeneratorBlockEntity;
import com.chris.vanilla_expansion.block.entity.ToolCraftingStationBLockEntity;
import com.chris.vanilla_expansion.block.storage.block.StorageTrim;
import com.chris.vanilla_expansion.block.storage.entity.CraftingInterfaceBlockEntity;
import com.chris.vanilla_expansion.block.storage.entity.StorageControllerBlockEntity;
import com.chris.vanilla_expansion.block.storage.entity.StorageCrateBlockEntity;
import com.chris.vanilla_expansion.block.storage.entity.StorageInterfaceBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import org.jetbrains.annotations.NotNull;

public class ModBlockEntities {

    public static void register() {}

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
