package com.chris.vanilla_expansion.screen;

import com.chris.vanilla_expansion.screen.backpack.BackpackMenu;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;

public class ToolCraftingStationScreen extends AbstractContainerScreen<@NotNull ToolCraftingStationMenu> {

    public ToolCraftingStationScreen(@NotNull ToolCraftingStationMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
    }

}
