package com.chris.vanilla_expansion_dragons.menu;

import com.chris.vanilla_expansion_dragons.VanillaExpansionDragons;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;

public class DragonMainScreen extends AbstractContainerScreen<@NotNull DragonMainMenu> {

    private static final Identifier GUI_TEXTURE = Identifier.fromNamespaceAndPath(
            VanillaExpansionDragons.MOD_ID,
            "textures/gui/dragon_main_gui.png"
    );

    public DragonMainScreen(@NotNull DragonMainMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
    }

    public DragonMainScreen(@NotNull DragonMainMenu menu, Inventory inventory, Component title, int imageWidth, int imageHeight) {
        super(menu, inventory, title, imageWidth, imageHeight);
    }
}