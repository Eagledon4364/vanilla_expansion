package com.chris.vanilla_expansion.block;

import com.chris.vanilla_expansion.VanillaExpansion;
import com.chris.vanilla_expansion.block.entity.BackpackBlockEntity;
import com.chris.vanilla_expansion.block.storage.StorageCrateBlockEntity;
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

    private static <T extends BlockEntity> BlockEntityType<T> register(
            String name,
            FabricBlockEntityTypeBuilder.Factory<? extends @NotNull T> entityFactory,
            Block... blocks
    ) {
        Identifier id = Identifier.fromNamespaceAndPath(VanillaExpansion.MOD_ID, name);
        return Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, id, FabricBlockEntityTypeBuilder.<T>create(entityFactory, blocks).build());
    }
}
