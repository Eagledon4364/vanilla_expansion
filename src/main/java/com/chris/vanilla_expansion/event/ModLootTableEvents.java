package com.chris.vanilla_expansion.event;

import com.chris.vanilla_expansion.block.ModBlocks;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;

public class ModLootTableEvents {

    public static void registerEvents() {
        LootTableEvents.MODIFY.register((key, tableBuilder, source, registries) -> {
            if (source.isBuiltin()) {

                if (BuiltInLootTables.END_CITY_TREASURE.equals(key)) {
                    LootPool.Builder poolBuilder = LootPool.lootPool()
                            .when(LootItemRandomChanceCondition.randomChance(0.75f))
                            .add(LootItem.lootTableItem(ModBlocks.ENERGY_DRAGON_EGG));

                    tableBuilder.withPool(poolBuilder);
                }

                if (BuiltInLootTables.BASTION_TREASURE.equals(key)) {
                    LootPool.Builder poolBuilder = LootPool.lootPool()
                            .add(LootItem.lootTableItem(ModBlocks.FIRE_DRAGON_EGG));

                    tableBuilder.withPool(poolBuilder);
                }

                if (BuiltInLootTables.BURIED_TREASURE.equals(key)) {
                    LootPool.Builder poolBuilder = LootPool.lootPool()
                            .add(LootItem.lootTableItem(ModBlocks.WATER_DRAGON_EGG));

                    tableBuilder.withPool(poolBuilder);
                }

                if (BuiltInLootTables.ANCIENT_CITY.equals(key)) {
                    LootPool.Builder poolBuilder = LootPool.lootPool()
                            .when(LootItemRandomChanceCondition.randomChance(0.50f))
                            .add(LootItem.lootTableItem(ModBlocks.EARTH_DRAGON_EGG));

                    tableBuilder.withPool(poolBuilder);
                }

                if (BuiltInLootTables.PILLAGER_OUTPOST.equals(key)) {
                    LootPool.Builder poolBuilder = LootPool.lootPool()
                            .add(LootItem.lootTableItem(ModBlocks.AIR_DRAGON_EGG));

                    tableBuilder.withPool(poolBuilder);
                }
            }
        });
    }
}