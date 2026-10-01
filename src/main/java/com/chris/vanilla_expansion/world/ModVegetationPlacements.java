package com.chris.vanilla_expansion.world;

import java.util.List;

import com.chris.vanilla_expansion.VanillaExpansion;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.RarityFilter;

public class ModVegetationPlacements {

    public static final ResourceKey<PlacedFeature> BLUEBERRY_BUSH_MEADOW = ResourceKey.create(
            Registries.PLACED_FEATURE,
            Identifier.fromNamespaceAndPath(VanillaExpansion.MOD_ID, "blueberry_bush_meadow")
    );

    public static void bootstrap(BootstrapContext<PlacedFeature> context) {
        HolderGetter<Feature> features = context.lookup(Registries.FEATURE);
        Holder<Feature> blueberryFeature = features.getOrThrow(ModVegetationFeatures.BLUEBERRY_BUSH);

        context.register(
                BLUEBERRY_BUSH_MEADOW,
                new PlacedFeature(
                        blueberryFeature,
                        List.of(
                                RarityFilter.onAverageOnceEvery(32),
                                InSquarePlacement.spread(),
                                PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
                                BiomeFilter.biome()
                        )
                )
        );
    }
}