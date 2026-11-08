package com.chris.vanilla_expansion.screen;

import com.chris.vanilla_expansion.entity.server.DragonAnimal;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;

public class DragonInventoryScreen extends AbstractContainerScreen<@NotNull DragonInventoryMenu> {

    private static final Identifier GUI_TEXTURE = Identifier.withDefaultNamespace("textures/gui/container/horse.png");
    private final DragonAnimal dragon;


    public DragonInventoryScreen(DragonInventoryMenu menu, Inventory playerInv, Component title) {
        super(menu, playerInv, title);
        this.dragon = menu.getDragon();
    }
    @Override
    public void extractContents(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTick) {
        graphics.blit(RenderPipelines.GUI_TEXTURED, GUI_TEXTURE, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight, 256, 256);

        super.extractContents(graphics, mouseX, mouseY, partialTick);
        if (this.dragon != null) {
            InventoryScreen.extractEntityInInventoryFollowsMouse(
                    graphics,
                    this.leftPos + 26, this.topPos + 18, // x0, y0
                    this.leftPos + 78, this.topPos + 70, // x1, y1
                    30, 0.0625F,
                    (float)mouseX, (float)mouseY,
                    this.dragon
            );
        }
    }

    @Override
    protected void extractLabels(GuiGraphicsExtractor graphics, int mouseX, int mouseY) {
        graphics.text(this.font, this.title, this.titleLabelX, this.titleLabelY, -12566464, false);
        graphics.text(this.font, this.playerInventoryTitle, this.inventoryLabelX, this.inventoryLabelY, -12566464, false);
    }
}