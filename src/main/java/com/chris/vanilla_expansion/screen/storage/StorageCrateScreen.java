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

public class StorageCrateScreen extends AbstractContainerScreen<@NotNull StorageCrateMenu> {
    private static final Identifier TEXTURE = Identifier.fromNamespaceAndPath("vanilla_expansion", "textures/gui/storage_crate.png");

    public StorageCrateScreen(StorageCrateMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title, 176, 166);
        this.inventoryLabelY = this.imageHeight - 94;
    }

    @Override
    public void extractContents(@NotNull GuiGraphicsExtractor graphics, int mouseX, int mouseY, float delta) {
        graphics.blit(RenderPipelines.GUI_TEXTURED, TEXTURE, this.leftPos, this.topPos, 0, 0,
                this.imageWidth, this.imageHeight, 256, 256);

        super.extractContents(graphics, mouseX, mouseY, delta);

        renderHighCount(graphics);
    }

    private void renderHighCount(GuiGraphicsExtractor graphics) {
        Slot crateSlot = this.getMenu().slots.get(0);
        ItemStack stack = crateSlot.getItem();

        if (!stack.isEmpty() && stack.getCount() > 64) {
            String countText = String.valueOf(stack.getCount());

            int x = this.leftPos + crateSlot.x + 17 - this.font.width(countText);
            int y = this.topPos + crateSlot.y + 9;

            graphics.guiRenderState.addText(new GuiTextRenderState(
                    this.font,
                    FormattedCharSequence.forward(countText, Style.EMPTY),
                    new Matrix3x2f(graphics.pose()),
                    x, y, 0xFFFFFFFF, 0, true, false,
                    graphics.scissorStack.peek()
            ));
        }
    }
}