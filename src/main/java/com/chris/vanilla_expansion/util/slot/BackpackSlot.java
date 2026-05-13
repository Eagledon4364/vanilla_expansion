package com.chris.vanilla_expansion.util.slot;

import com.chris.vanilla_expansion.item.ModItems;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class BackpackSlot extends Slot {

    public BackpackSlot(Container container, int slot, int x, int y) {
        super(container, slot, x, y);
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        return stack.is(ModItems.BACKPACK_ITEM.asItem());
    }

    @Override
    public int getMaxStackSize() {
        return 1;
    }

}