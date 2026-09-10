package com.chris.vanilla_expansion.util;

import com.chris.vanilla_expansion.VanillaExpansion;
import com.chris.vanilla_expansion.component.CoreAffinityComponent;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.level.block.Block;

import java.util.List;
import java.util.Optional;

public class ComponentEffects {

    public static void register() {
        registerFireSmeltEvent();
    }

    /**
     * Call this when crafting/creating an item with a CoreAffinityComponent
     */
    public static ItemStack applyAffinityAttributes(ItemStack stack) {
        CoreAffinityComponent component = stack.get(CoreAffinityComponent.KEY);
        if (component == null) return stack;

        ItemAttributeModifiers currentModifiers = stack.getOrDefault(DataComponents.ATTRIBUTE_MODIFIERS, ItemAttributeModifiers.EMPTY);
        ItemAttributeModifiers.Builder builder = ItemAttributeModifiers.builder();

        for (ItemAttributeModifiers.Entry entry : currentModifiers.modifiers()) {
            builder.add(entry.attribute(), entry.modifier(), entry.slot());
        }

        switch (component.affinity()) {
            case AIR -> applyAir(builder);
            case WATER -> applyWater(builder);
            case ENERGY -> {
                applyAir(builder);
                applyWater(builder);
            }
            default -> {}
        }

        stack.set(DataComponents.ATTRIBUTE_MODIFIERS, builder.build());
        return stack;
    }

    private static void applyAir(ItemAttributeModifiers.Builder builder) {
        builder.add(
                Attributes.MINING_EFFICIENCY,
                new AttributeModifier(
                        Identifier.fromNamespaceAndPath(VanillaExpansion.MOD_ID, "air_mining_speed"),
                        5.0,
                        AttributeModifier.Operation.ADD_VALUE
                ),
                EquipmentSlotGroup.MAINHAND
        );
    }

    private static void applyWater(ItemAttributeModifiers.Builder builder) {
        // Extends block place/break distance (+1.0 block)
        builder.add(
                Attributes.BLOCK_INTERACTION_RANGE,
                new AttributeModifier(
                        Identifier.fromNamespaceAndPath(VanillaExpansion.MOD_ID, "water_block_reach"),
                        1.0,
                        AttributeModifier.Operation.ADD_VALUE
                ),
                EquipmentSlotGroup.MAINHAND
        );

        // Extends attack/entity interaction distance (+1.0 block)
        builder.add(
                Attributes.ENTITY_INTERACTION_RANGE,
                new AttributeModifier(
                        Identifier.fromNamespaceAndPath(VanillaExpansion.MOD_ID, "water_entity_reach"),
                        1.0,
                        AttributeModifier.Operation.ADD_VALUE
                ),
                EquipmentSlotGroup.MAINHAND
        );
    }
    /**
     * Fixed Fire Auto-Smelting (Prevents double block breaks)
     */
    private static void registerFireSmeltEvent() {
        PlayerBlockBreakEvents.BEFORE.register((level, player, pos, state, blockEntity) -> {
            ItemStack tool = player.getMainHandItem();
            CoreAffinityComponent component = tool.get(CoreAffinityComponent.KEY);

            if (component != null && component.affinity() == CoreAffinityComponent.Affinity.FIRE && level instanceof ServerLevel serverLevel) {
                if (!state.requiresCorrectToolForDrops() || tool.isCorrectToolForDrops(state)) {
                    List<ItemStack> drops = Block.getDrops(state, serverLevel, pos, blockEntity, player, tool);

                    boolean smeltedAny = false;
                    RecipeManager recipeManager = (RecipeManager) serverLevel.recipeAccess();

                    for (ItemStack drop : drops) {
                        SingleRecipeInput input = new SingleRecipeInput(drop);
                        Optional<RecipeHolder<SmeltingRecipe>> recipeHolder = recipeManager
                                .getRecipeFor(RecipeType.SMELTING, input, serverLevel);

                        if (recipeHolder.isPresent()) {
                            SmeltingRecipe recipe = recipeHolder.get().value();

                            ItemStack result = recipe.assemble(input).copy();

                            if (!result.isEmpty()) {
                                result.setCount(drop.getCount());
                                Block.popResource(serverLevel, pos, result);
                                smeltedAny = true;
                                continue;
                            }
                        }

                        Block.popResource(serverLevel, pos, drop);
                    }

                    if (smeltedAny) {
                        serverLevel.removeBlock(pos, false);
                        tool.hurtAndBreak(1, player, EquipmentSlot.MAINHAND);
                        return false;
                    }
                }
            }
            return true;
        });
    }

    public static boolean shouldCancelDurabilityDamage(ItemStack stack) {
        CoreAffinityComponent component = stack.get(CoreAffinityComponent.KEY);
        if (component != null) {
            CoreAffinityComponent.Affinity affinity = component.affinity();
            if (affinity == CoreAffinityComponent.Affinity.EARTH || affinity == CoreAffinityComponent.Affinity.ENERGY) {
                return Math.random() < 0.20;
            }
        }
        return false;
    }
}