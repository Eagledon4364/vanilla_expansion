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

public class StorageInterfaceScreen extends AbstractContainerScreen<@NotNull StorageInterfaceMenu> {
    private static final Identifier TEXTURE = Identifier.fromNamespaceAndPath("vanilla_expansion", "textures/gui/large_backpack.png");

    public StorageInterfaceScreen(StorageInterfaceMenu handler, Inventory inventory, Component title) {
        super(handler, inventory, title, 176, 222);
        this.inventoryLabelY = this.imageHeight - 94;
    }

    @Override
    public void extractContents(@NotNull GuiGraphicsExtractor graphics, int mouseX, int mouseY, float delta) {
        // Draw background texture
        graphics.blit(RenderPipelines.GUI_TEXTURED, TEXTURE, this.leftPos, this.topPos, 0, 0,
                this.imageWidth, this.imageHeight, 256, 256);

        super.extractContents(graphics, mouseX, mouseY, delta);

        // Custom stack count text rendering for Network View slots (0-53)
        for (int i = 0; i < 54; i++) {
            Slot slot = this.menu.slots.get(i);
            ItemStack stack = slot.getItem();

            if (!stack.isEmpty()) {
                String countText = formatCount(stack.getCount());

                // Align text to bottom-right of the slot
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

    /**
     * Formats large stack counts into readable labels (e.g. 1000 -> "1k")
     */
    private String formatCount(int count) {
        if (count >= 1_000_000) {
            return String.format("%.1fM", count / 1_000_000.0);
        } else if (count >= 10_000) {
            return (count / 1000) + "k";
        } else if (count >= 1_000) {
            return String.format("%.1fk", count / 1000.0);
        }
        return String.valueOf(count);
    }
}