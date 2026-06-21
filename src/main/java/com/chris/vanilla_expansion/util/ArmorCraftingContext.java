package com.chris.vanilla_expansion.util;

import com.chris.vanilla_expansion.item.custom.EnergyDragonArmorItem;
import net.minecraft.world.item.ItemStack;

public class ArmorCraftingContext {
    private static final ThreadLocal<ItemStack> ACTIVE_STACK = new ThreadLocal<>();

    public static void setActiveStack(ItemStack stack) {
        ACTIVE_STACK.set(stack);
    }

    public static void clear() {
        ACTIVE_STACK.remove();
    }

    public static boolean isEnergyDragonArmorActive() {
        ItemStack stack = ACTIVE_STACK.get();
        return stack != null && !stack.isEmpty() && stack.getItem() instanceof EnergyDragonArmorItem;
    }
}