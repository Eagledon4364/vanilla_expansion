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
    private static final Identifier CRAFTING_BASE = Identifier.fromNamespaceAndPath("vanilla_expansion", "textures/gui/backpack_crafting_base.png");
    private static final Identifier CRAFTING_LARGE = Identifier.fromNamespaceAndPath("vanilla_expansion", "textures/gui/backpack_crafting_large.png");

    private boolean wasStorageUpgraded;
    private boolean wasCraftingUpgraded;

    public BackpackScreen(BackpackMenu handler, Inventory inventory, Component title) {
        super(handler, inventory, title, 200, calculateScreenHeight(handler.isStorageUpgraded(), handler.isCraftingUpgraded()));
        this.inventoryLabelY = calculateInventoryLabelY(handler.isStorageUpgraded(), handler.isCraftingUpgraded());
        this.wasStorageUpgraded = handler.isStorageUpgraded();
        this.wasCraftingUpgraded = handler.isCraftingUpgraded();
    }

    private static int calculateScreenHeight(boolean storageUpgraded, boolean craftingUpgraded) {
        if (!storageUpgraded && !craftingUpgraded) return 166;
        if (storageUpgraded && !craftingUpgraded) return 222;
        if (!storageUpgraded && craftingUpgraded) return 234;
        return 272;
    }

    private static int calculateInventoryLabelY(boolean storageUpgraded, boolean craftingUpgraded) {
        if (!storageUpgraded && !craftingUpgraded) return 74;
        if (storageUpgraded && !craftingUpgraded) return 130;
        if (!storageUpgraded && craftingUpgraded) return 142;
        return 180;
    }

    @Override
    public void extractContents(@NotNull GuiGraphicsExtractor graphics, int mouseX, int mouseY, float delta) {
        boolean isStorageUpgraded = this.getMenu().isStorageUpgraded();
        boolean isCraftingUpgraded = this.getMenu().isCraftingUpgraded();

        if (isStorageUpgraded != this.wasStorageUpgraded || isCraftingUpgraded != this.wasCraftingUpgraded) {
            updateLayoutDimensions(isStorageUpgraded, isCraftingUpgraded);
            this.wasStorageUpgraded = isStorageUpgraded;
            this.wasCraftingUpgraded = isCraftingUpgraded;
        }

        Identifier texture = getTexture(isStorageUpgraded, isCraftingUpgraded);
        int textureHeight = (isStorageUpgraded && isCraftingUpgraded) ? 272 : 256;

        graphics.blit(RenderPipelines.GUI_TEXTURED, texture, this.leftPos, this.topPos, 0, 0,
                this.imageWidth, this.imageHeight, 256, textureHeight);

        super.extractContents(graphics, mouseX, mouseY, delta);

        if (this.getMenu().isStackUpgraded()) {
            renderExtendedStackCounts(graphics);
        }
    }

    private Identifier getTexture(boolean storageUpgraded, boolean craftingUpgraded) {
        if (craftingUpgraded) {
            return storageUpgraded ? CRAFTING_LARGE : CRAFTING_BASE;
        }
        return storageUpgraded ? LARGE_TEXTURE : BASE_TEXTURE;
    }

    private void updateLayoutDimensions(boolean isStorageUpgraded, boolean isCraftingUpgraded) {
        this.getMenu().updateSlotPositions();
        AbstractContainerScreenAccessor screenAccessor = (AbstractContainerScreenAccessor) this;
        int newHeight = calculateScreenHeight(isStorageUpgraded, isCraftingUpgraded);

        screenAccessor.setImageHeight(newHeight);
        screenAccessor.setInventoryLabelY(calculateInventoryLabelY(isStorageUpgraded, isCraftingUpgraded));
        this.leftPos = (this.width - this.imageWidth) / 2;
        this.topPos = (this.height - newHeight) / 2;
    }

    private void renderExtendedStackCounts(GuiGraphicsExtractor graphics) {
        for (Slot slot : this.menu.slots) {
            ItemStack stack = slot.getItem();
            if (!stack.isEmpty() && stack.getCount() > 99) {
                String countText = String.valueOf(stack.getCount());
                int x = this.leftPos + slot.x + 19 - 2 - this.font.width(countText);
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
    }
}