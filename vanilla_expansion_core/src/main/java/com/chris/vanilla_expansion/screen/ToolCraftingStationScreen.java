package com.chris.vanilla_expansion.screen;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;

public class ToolCraftingStationScreen extends AbstractContainerScreen<@NotNull ToolCraftingStationMenu> {

    private static final Identifier TEXTURE = Identifier.fromNamespaceAndPath("vanilla_expansion", "textures/gui/tool_crafting_station.png");

    public ToolCraftingStationScreen(@NotNull ToolCraftingStationMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title, 176, 166);
        this.titleLabelY = 6;
        this.inventoryLabelY = this.imageHeight - 94;
    }

    @Override
    public void extractContents(@NotNull GuiGraphicsExtractor graphics, int mouseX, int mouseY, float delta) {
        graphics.blit(RenderPipelines.GUI_TEXTURED, TEXTURE, this.leftPos, this.topPos, 0, 0,
                this.imageWidth, this.imageHeight, 256, 256);

        super.extractContents(graphics, mouseX, mouseY, delta);
    }
}