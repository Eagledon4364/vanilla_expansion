package com.chris.vanilla_expansion.item.inventory;


import com.chris.vanilla_expansion.block.entity.ImplementedInventory;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemContainerContents;

public class ItemStackInventory implements ImplementedInventory {
    private final ItemStack stack;
    private final NonNullList<ItemStack> items;

    public ItemStackInventory(ItemStack stack, int size) {
        this.stack = stack;
        this.items = NonNullList.withSize(size, ItemStack.EMPTY);

        // Load existing items from the Data Component
        ItemContainerContents contents = stack.get(DataComponents.CONTAINER);
        if (contents != null) {
            contents.copyInto(this.items);
        }
    }

    @Override
    public NonNullList<ItemStack> getItems() {
        return items;
    }

    @Override
    public void setChanged() {
        // Save the items back to the Data Component every time a change is made
        stack.set(DataComponents.CONTAINER, ItemContainerContents.fromItems(items));
    }
}