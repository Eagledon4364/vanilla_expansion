package com.chris.vanilla_expansion.screen.storage;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class StorageCrateScreen extends AbstractContainerScreen<@NotNull StorageCrateMenu> {
    private static final Identifier TEXTURE = Identifier.fromNamespaceAndPath("vanilla_expansion", "textures/gui/storage_crate.png");

    public StorageCrateScreen(StorageCrateMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title, 176, 166);
        this.inventoryLabelY = this.imageHeight - 94;
    }

    @Override
    protected void extractSlot(final GuiGraphicsExtractor graphics, final Slot slot, final int mouseX, final int mouseY) {
        if (slot.index == 0 && slot.container == this.getMenu().getContainer()) {
            ItemStack stack = slot.getItem();

            if (!stack.isEmpty()) {
                graphics.item(stack, slot.x, slot.y, slot.x + slot.y * this.imageWidth);

                long totalCount = 0;
                for (int i = 0; i < this.getMenu().getContainer().getContainerSize(); i++) {
                    totalCount += this.getMenu().getContainer().getItem(i).getCount();
                }

                String countText = String.valueOf(totalCount);

                int xOffset = 8 - (this.font.width(countText) / 2);
                int yOffset = 18;

                graphics.text(this.font, countText, slot.x + xOffset, slot.y + yOffset, 0xFFFFFFFF);

                return;
            }
        }
        super.extractSlot(graphics, slot, mouseX, mouseY);
    }

    @Override
    public void extractContents(@NotNull GuiGraphicsExtractor graphics, int mouseX, int mouseY, float delta) {
        graphics.blit(RenderPipelines.GUI_TEXTURED, TEXTURE, this.leftPos, this.topPos, 0, 0,
                this.imageWidth, this.imageHeight, 256, 256);
        super.extractContents(graphics, mouseX, mouseY, delta);
    }
}