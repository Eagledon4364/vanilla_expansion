package com.chris.vanilla_expansion.world.gen;

import com.chris.vanilla_expansion.entity.ModEntities;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.goat.Goat;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.Heightmap;

public class ModEntitySpawns {

    public static void registerModEntitySpawns() {
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(Biomes.MEADOW),
                MobCategory.CREATURE, ModEntities.ENERGY_DRAGON, 1, 1, 1);

        SpawnPlacements.register(ModEntities.ENERGY_DRAGON, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Animal::checkAnimalSpawnRules);


        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(Biomes.FROZEN_PEAKS, Biomes.JAGGED_PEAKS, Biomes.STONY_PEAKS),
                MobCategory.CREATURE, ModEntities.AIR_DRAGON, 5, 1, 1);

        SpawnPlacements.register(ModEntities.AIR_DRAGON, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Goat::checkGoatSpawnRules);


        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(Biomes.WOODED_BADLANDS),
                MobCategory.CREATURE, ModEntities.FIRE_DRAGON, 5, 1, 1);

        SpawnPlacements.register(ModEntities.FIRE_DRAGON, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Animal::checkAnimalSpawnRules);




    }
}
