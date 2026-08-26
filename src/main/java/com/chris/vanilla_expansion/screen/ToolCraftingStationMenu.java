package com.chris.vanilla_expansion.screen;

import net.minecraft.world.Container;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractMountInventoryMenu;
import net.minecraft.world.item.ItemStack;

public class ToolCraftingStationMenu extends AbstractMountInventoryMenu {

    protected ToolCraftingStationMenu(int containerId, Inventory playerInventory, Container mountInventory, LivingEntity mount) {
        super(containerId, playerInventory, mountInventory, mount);
    }

    @Override
    protected boolean hasInventoryChanged(Container container) {
        return false;
    }

    @Override
    public ItemStack quickMoveStack(Player player, int slotIndex) {

        return null;
    }

}
