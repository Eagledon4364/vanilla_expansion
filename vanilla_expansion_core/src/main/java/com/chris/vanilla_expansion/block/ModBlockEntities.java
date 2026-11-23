package com.chris.vanilla_expansion.block;

import com.chris.vanilla_expansion.VanillaExpansion;
import com.chris.vanilla_expansion.block.entity.BackpackBlockEntity;
import com.chris.vanilla_expansion.block.entity.SandGeneratorBlockEntity;
import com.chris.vanilla_expansion.block.entity.ToolCraftingStationBLockEntity;
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


    public static final BlockEntityType<@NotNull BackpackBlockEntity> BACKPACK_BLOCK_ENTITY =
            register("backpack_block", BackpackBlockEntity::new, ModBlocks.BACKPACK_BLOCK);

    public static final BlockEntityType<@NotNull SandGeneratorBlockEntity> SANDGENERATOR_BE =
            register("sand_generator_block", SandGeneratorBlockEntity::new, ModBlocks.SAND_GENERATOR_BLOCK);

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