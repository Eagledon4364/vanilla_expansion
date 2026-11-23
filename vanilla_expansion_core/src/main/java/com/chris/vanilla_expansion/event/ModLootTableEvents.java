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
        });
    }
}