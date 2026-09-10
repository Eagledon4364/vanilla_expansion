package com.chris.vanilla_expansion.util;

import com.chris.vanilla_expansion.VanillaExpansion;
import com.chris.vanilla_expansion.component.CoreAffinityComponent;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;

public class DynamicAttributeHandler {

    private static final Identifier WATER_BLOCK_REACH_ID = Identifier.fromNamespaceAndPath(VanillaExpansion.MOD_ID, "water_block_reach");
    private static final Identifier WATER_ENTITY_REACH_ID = Identifier.fromNamespaceAndPath(VanillaExpansion.MOD_ID, "water_entity_reach");
    private static final Identifier AIR_MINING_SPEED_ID = Identifier.fromNamespaceAndPath(VanillaExpansion.MOD_ID, "air_mining_speed");

    public static void register() {
        ServerTickEvents.END_LEVEL_TICK.register(level -> {
            for (ServerPlayer player : level.players()) {
                ItemStack mainHand = player.getMainHandItem();
                CoreAffinityComponent component = mainHand.get(CoreAffinityComponent.KEY);

                boolean hasWater = component != null && (component.affinity() == CoreAffinityComponent.Affinity.WATER || component.affinity() == CoreAffinityComponent.Affinity.ENERGY);
                boolean hasAir = component != null && (component.affinity() == CoreAffinityComponent.Affinity.AIR || component.affinity() == CoreAffinityComponent.Affinity.ENERGY);

                // 1. Handle Water Reach Boost (+1.0 Block & Entity Reach)
                AttributeInstance blockReach = player.getAttribute(Attributes.BLOCK_INTERACTION_RANGE);
                AttributeInstance entityReach = player.getAttribute(Attributes.ENTITY_INTERACTION_RANGE);

                if (blockReach != null) {
                    if (hasWater) {
                        if (blockReach.getModifier(WATER_BLOCK_REACH_ID) == null) {
                            blockReach.addTransientModifier(new AttributeModifier(WATER_BLOCK_REACH_ID, 1.0, AttributeModifier.Operation.ADD_VALUE));
                        }
                    } else {
                        blockReach.removeModifier(WATER_BLOCK_REACH_ID);
                    }
                }

                if (entityReach != null) {
                    if (hasWater) {
                        if (entityReach.getModifier(WATER_ENTITY_REACH_ID) == null) {
                            entityReach.addTransientModifier(new AttributeModifier(WATER_ENTITY_REACH_ID, 1.0, AttributeModifier.Operation.ADD_VALUE));
                        }
                    } else {
                        entityReach.removeModifier(WATER_ENTITY_REACH_ID);
                    }
                }

                // 2. Handle Air Mining Speed Boost (+5.0 Mining Efficiency)
                AttributeInstance miningSpeed = player.getAttribute(Attributes.MINING_EFFICIENCY);
                if (miningSpeed != null) {
                    if (hasAir) {
                        if (miningSpeed.getModifier(AIR_MINING_SPEED_ID) == null) {
                            miningSpeed.addTransientModifier(new AttributeModifier(AIR_MINING_SPEED_ID, 5.0, AttributeModifier.Operation.ADD_VALUE));
                        }
                    } else {
                        miningSpeed.removeModifier(AIR_MINING_SPEED_ID);
                    }
                }
            }
        });
    }
}