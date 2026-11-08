package com.chris.vanilla_expansion.world;

import com.chris.vanilla_expansion.VanillaExpansion;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;

import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class ModBiomeModifications {

    public static final ResourceKey<PlacedFeature> BLUEBERRY_BUSH_MEADOW = ResourceKey.create(
            Registries.PLACED_FEATURE,
            Identifier.fromNamespaceAndPath(VanillaExpansion.MOD_ID, "blueberry_bush_meadow")
    );

    public static void load() {
        BiomeModifications.addFeature(
                BiomeSelectors.includeByKey(Biomes.MEADOW),
                GenerationStep.Decoration.VEGETAL_DECORATION,
                BLUEBERRY_BUSH_MEADOW
        );
    }
}