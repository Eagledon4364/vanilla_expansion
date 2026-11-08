package com.chris.vanilla_expansion.world;

import com.chris.vanilla_expansion.VanillaExpansion;
import com.chris.vanilla_expansion.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.SweetBerryBushBlock;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.SimpleBlockFeature;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public class ModVegetationFeatures {

    public static final ResourceKey<Feature> BLUEBERRY_BUSH = ResourceKey.create(
            Registries.FEATURE,
            Identifier.fromNamespaceAndPath(VanillaExpansion.MOD_ID, "blueberry_bush")
    );

    public static void bootstrap(BootstrapContext<Feature> context) {
        context.register(
                BLUEBERRY_BUSH,
                new SimpleBlockFeature(
                        BlockStateProvider.of(
                                ModBlocks.BLUEBERRY_BUSH_BLOCK.defaultBlockState().setValue(SweetBerryBushBlock.AGE, 3)
                        )
                )
        );
    }
}