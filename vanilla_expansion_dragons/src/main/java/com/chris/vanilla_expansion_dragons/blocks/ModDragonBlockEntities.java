package com.chris.vanilla_expansion_dragons.blocks;

import com.chris.vanilla_expansion_dragons.VanillaExpansionDragons;
import com.chris.vanilla_expansion_dragons.entity.ModDragons;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import org.jetbrains.annotations.NotNull;

public class ModDragonBlockEntities {
    public static void register() {

    }
    public static final BlockEntityType<@NotNull DragonEggBlockEntity> ELDERWING_DRAGON_BE =
            register("elderwing_dragon_egg", (pos, state) -> new DragonEggBlockEntity(
                    ModDragonBlockEntities.ELDERWING_DRAGON_BE, pos, state, ModDragons.ELDERWING_DRAGON, 24000
            ), ModDragonBlocks.ELDERWING_DRAGON_EGG);

    private static <T extends BlockEntity> BlockEntityType<@NotNull T> register(
            String name,
            FabricBlockEntityTypeBuilder.Factory<? extends @NotNull T> entityFactory,
            Block... blocks
    ) {
        Identifier id = Identifier.fromNamespaceAndPath(VanillaExpansionDragons.MOD_ID, name);
        return Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, id, FabricBlockEntityTypeBuilder.<T>create(entityFactory, blocks).build());
    }
}
