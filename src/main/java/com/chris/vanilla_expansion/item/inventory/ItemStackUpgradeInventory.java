package com.chris.vanilla_expansion.item.inventory;
import com.chris.vanilla_expansion.component.ModComponents;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemContainerContents;

public class ItemStackUpgradeInventory extends SimpleContainer {
    private final ItemStack stack;

    public ItemStackUpgradeInventory(ItemStack stack, int size) {
        super(size);
        this.stack = stack;

        load();
    }

    private void load() {
        ItemContainerContents contents = stack.get(ModComponents.UPGRADE_DATA);
        if (contents != null) {
            contents.copyInto(this.items);
        }
    }

    @Override
    public void setChanged() {
        super.setChanged();
        stack.set(ModComponents.UPGRADE_DATA, ItemContainerContents.fromItems(this.items));
    }

}