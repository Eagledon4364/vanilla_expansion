package com.chris.vanilla_expansion.screen.storage;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.state.gui.GuiTextRenderState;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.Identifier;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix3x2f;

import java.util.List;

public class StorageAccessScreen extends AbstractContainerScreen<@NotNull StorageAccessMenu> {
    private static final Identifier TEXTURE = Identifier.fromNamespaceAndPath("vanilla_expansion", "textures/gui/storage_access.png");

    public StorageAccessScreen(StorageAccessMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title, 176, 222);
        this.inventoryLabelY = this.imageHeight - 94;
    }

    @Override
    public void extractContents(@NotNull GuiGraphicsExtractor graphics, int mouseX, int mouseY, float delta) {
        graphics.blit(RenderPipelines.GUI_TEXTURED, TEXTURE, this.leftPos, this.topPos, 0.0f, 0.0f,
                 this.imageWidth, this.imageHeight, 256, 256);

        super.extractContents(graphics, mouseX, mouseY, delta);

        renderNetworkItemLabels(graphics);
    }

    private void renderNetworkItemLabels(GuiGraphicsExtractor graphics) {
        for (int i = 0; i < this.getMenu().slots.size(); i++) {
            Slot slot = this.getMenu().slots.get(i);

            // ONLY network slots
            if (slot.container == this.getMenu().getInventory()) {
                ItemStack stack = slot.getItem();

                if (!stack.isEmpty() && stack.getCount() > 64) {
                    renderCustomCount(graphics, slot, stack.getCount());
                }
            }
        }
    }

    private void renderCustomCount(GuiGraphicsExtractor graphics, Slot slot, int count) {
        String countText = String.valueOf(count);

        int x = this.leftPos + slot.x + 17 - this.font.width(countText);
        int y = this.topPos + slot.y + 9;

        graphics.guiRenderState.addText(new GuiTextRenderState(
                this.font,
                FormattedCharSequence.forward(countText, Style.EMPTY),
                new Matrix3x2f(graphics.pose()),
                x, y, 0xFFFFFFFF, 0, true, false,
                graphics.scissorStack.peek()
        ));
    }
}