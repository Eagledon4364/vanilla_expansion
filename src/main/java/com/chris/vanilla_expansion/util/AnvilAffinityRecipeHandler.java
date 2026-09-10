package com.chris.vanilla_expansion.util;

import com.chris.vanilla_expansion.component.CoreAffinityComponent;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class AnvilAffinityRecipeHandler {

    public static boolean processAnvilCombine(ItemStack left, ItemStack right, AnvilMenu menu) {
        if (left.isEmpty() || right.isEmpty()) {
            return false;
        }

        // 1. Ensure left item is a tool/weapon
        boolean isTool = left.has(DataComponents.TOOL) || left.isDamageableItem();
        if (!isTool) {
            return false;
        }

        // 2. Resolve affinity: First check if item has CoreAffinityComponent, otherwise fallback to item type recipes
        CoreAffinityComponent.Affinity targetAffinity = getAffinityFromStack(right);
        if (targetAffinity == null) {
            return false;
        }

        // 3. Prevent combining if tool already has this exact affinity
        CoreAffinityComponent existingComponent = left.get(CoreAffinityComponent.KEY);
        if (existingComponent != null && existingComponent.affinity() == targetAffinity) {
            return false;
        }

        // 4. Create the upgraded tool with the new affinity
        ItemStack output = left.copy();
        output.set(CoreAffinityComponent.KEY, new CoreAffinityComponent(targetAffinity));

        // 5. Update output slot and set XP cost
        menu.getSlot(2).set(output);

        return true;
    }

    /**
     * Maps raw ingredient items (or items with the component) to an Affinity
     */
    private static CoreAffinityComponent.Affinity getAffinityFromStack(ItemStack stack) {
        // Priority 1: Direct Component on item
        CoreAffinityComponent component = stack.get(CoreAffinityComponent.KEY);
        if (component != null) {
            return component.affinity();
        }

        // Priority 2: Standard Item Ingredients
        if (stack.is(Items.REDSTONE) || stack.is(Items.FIRE_CHARGE)) {
            return CoreAffinityComponent.Affinity.FIRE;
        } else if (stack.is(Items.LAPIS_LAZULI) || stack.is(Items.WATER_BUCKET)) {
            return CoreAffinityComponent.Affinity.WATER;
        } else if (stack.is(Items.FEATHER) || stack.is(Items.WIND_CHARGE)) {
            return CoreAffinityComponent.Affinity.AIR;
        } else if (stack.is(Items.EMERALD) || stack.is(Items.DIRT)) {
            return CoreAffinityComponent.Affinity.EARTH;
        } else if (stack.is(Items.NETHER_STAR) || stack.is(Items.DIAMOND)) {
            return CoreAffinityComponent.Affinity.ENERGY;
        }

        // Add custom modded item checks here if needed (e.g., ModItems.FIRE_CORE)

        return null;
    }
}