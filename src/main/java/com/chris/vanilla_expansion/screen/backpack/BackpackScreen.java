package com.chris.vanilla_expansion.screen.backpack;

import com.chris.vanilla_expansion.mixin.AbstractContainerScreenAccessor;
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

public class BackpackScreen extends AbstractContainerScreen<@NotNull BackpackMenu> {
    private static final Identifier BASE_TEXTURE = Identifier.fromNamespaceAndPath("vanilla_expansion", "textures/gui/backpack.png");
    private static final Identifier LARGE_TEXTURE = Identifier.fromNamespaceAndPath("vanilla_expansion", "textures/gui/large_backpack.png");
    private boolean wasUpgraded = false;

    public BackpackScreen(BackpackMenu handler, Inventory inventory, Component title) {
        // FIX: Use isStorageUpgraded() to determine initial height
        super(handler, inventory, title, 202, handler.isStorageUpgraded() ? 222 : 166);
        this.inventoryLabelY = this.imageHeight - 94;
    }

    @Override
    public void extractContents(@NotNull GuiGraphicsExtractor graphics, int mouseX, int mouseY, float delta) {
        boolean isStorageUpgraded = this.getMenu().isStorageUpgraded();

        if (isStorageUpgraded != wasUpgraded) {
            this.getMenu().updateSlotPositions();
            AbstractContainerScreenAccessor screenAccessor = (AbstractContainerScreenAccessor) this;
            int newHeight = isStorageUpgraded ? 222 : 166;
            screenAccessor.setImageHeight(newHeight);
            screenAccessor.setInventoryLabelY(newHeight - 94);
            this.leftPos = (this.width - this.imageWidth) / 2;
            this.topPos = (this.height - newHeight) / 2;
            wasUpgraded = isStorageUpgraded;
        }

        Identifier texture = isStorageUpgraded ? LARGE_TEXTURE : BASE_TEXTURE;
        graphics.blit(RenderPipelines.GUI_TEXTURED, texture, this.leftPos, this.topPos, 0, 0,
                this.imageWidth, isStorageUpgraded ? 222 : 166, 256, 256);

        super.extractContents(graphics, mouseX, mouseY, delta);

        if (this.getMenu().isStackUpgraded()) {
            for (Slot slot : this.menu.slots) {
                ItemStack stack = slot.getItem();
                if (!stack.isEmpty() && stack.getCount() > 99) {
                    String countText = String.valueOf(stack.getCount());
                    int x = this.leftPos + slot.x + 19 - 2 - this.font.width(countText);
                    int y = this.topPos + slot.y + 6 + 3;

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
    }
}