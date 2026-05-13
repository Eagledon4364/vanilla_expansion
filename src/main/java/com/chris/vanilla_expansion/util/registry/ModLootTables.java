package com.chris.vanilla_expansion.util.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.LootTable;

import java.util.HashSet;
import java.util.Set;

public class ModLootTables {
    private static final Set<ResourceKey<LootTable>> LOCATIONS = new HashSet();

    public static final ResourceKey<LootTable> ENERGY_DRAGON_SCALE = register("brush/dragon_scale");
    public static final ResourceKey<LootTable> FIRE_DRAGON_SCALE = register("brush/fire_dragon_scale");
    public static final ResourceKey<LootTable> AIR_DRAGON_SCALE = register("brush/air_dragon_scale");
    public static final ResourceKey<LootTable> EARTH_DRAGON_SCALE = register("brush/earth_dragon_scale");
    public static final ResourceKey<LootTable> WATER_DRAGON_SCALE = register("brush/water_dragon_scale");

    private static ResourceKey<LootTable> register(final ResourceKey<LootTable> location) {
        if (LOCATIONS.add(location)) {
            return location;
        } else {
            throw new IllegalArgumentException(location.identifier() + " is already a registered built-in loot table");
        }
    }

    private static ResourceKey<LootTable> register(final String location) {
        return register(ResourceKey.create(Registries.LOOT_TABLE, Identifier.withDefaultNamespace(location)));
    }
}
