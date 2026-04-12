package com.chris.vanilla_expansion.util;

import com.chris.vanilla_expansion.item.custom.MagnetItem;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class MagnetSlot extends Slot {
    public MagnetSlot(Container container, int slot, int x, int y) {
        super(container, slot, x, y);
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        return stack.getItem() instanceof MagnetItem;
    }
}