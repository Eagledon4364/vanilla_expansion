package com.chris.vanilla_expansion.component;

import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemContainerContents;

import java.util.ArrayList;
import java.util.List;

public class BackpackStorageHelper {

    /**
     * Converts a Container's slots directly into an explicit List<ItemStack>
     * so ItemContainerContents can consume it safely.
     */
    public static List<ItemStack> getItemList(Container container) {
        List<ItemStack> items = new ArrayList<>(container.getContainerSize());
        for (int i = 0; i < container.getContainerSize(); i++) {
            items.add(container.getItem(i));
        }
        return items;
    }

    public static void saveToStack(ItemStack stack, Container mainInv, Container upgradeInv) {
        stack.set(DataComponents.CONTAINER, ItemContainerContents.fromItems(getItemList(mainInv)));
        stack.set(ModDataComponentTypes.UPGRADE_DATA, ItemContainerContents.fromItems(getItemList(upgradeInv)));
    }

    public static void loadFromStack(ItemStack stack, Container mainInv, Container upgradeInv) {
        mainInv.clearContent();
        upgradeInv.clearContent();

        ItemContainerContents mainContents = stack.get(DataComponents.CONTAINER);
        if (mainContents != null) {
            NonNullList<ItemStack> list = NonNullList.withSize(mainInv.getContainerSize(), ItemStack.EMPTY);
            mainContents.copyInto(list);
            for (int i = 0; i < list.size(); i++) {
                mainInv.setItem(i, list.get(i));
            }
        }

        ItemContainerContents upgradeContents = stack.get(ModDataComponentTypes.UPGRADE_DATA);
        if (upgradeContents != null) {
            NonNullList<ItemStack> list = NonNullList.withSize(upgradeInv.getContainerSize(), ItemStack.EMPTY);
            upgradeContents.copyInto(list);
            for (int i = 0; i < list.size(); i++) {
                upgradeInv.setItem(i, list.get(i));
            }
        }
    }
}