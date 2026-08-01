package com.chris.vanilla_expansion.screen.storage;

import com.chris.vanilla_expansion.networking.C2SCraftingGridActionPayload;
import com.chris.vanilla_expansion.networking.C2SSyncStorageSearchPayload;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.input.CharacterEvent;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.state.gui.GuiTextRenderState;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix3x2f;

import java.util.ArrayList;
import java.util.List;

public class CraftingInterfaceScreen extends AbstractContainerScreen<@NotNull CraftingInterfaceMenu> {
    private static final Identifier TEXTURE = Identifier.fromNamespaceAndPath("vanilla_expansion", "textures/gui/crafting_interface.png");

    private static final Identifier SCROLLER_SPRITE = Identifier.withDefaultNamespace("container/creative_inventory/scroller");
    private static final Identifier SCROLLER_DISABLED_SPRITE = Identifier.withDefaultNamespace("container/creative_inventory/scroller_disabled");

    private static final Identifier OVERLAY_COUNT = Identifier.fromNamespaceAndPath("vanilla_expansion", "textures/gui/overlay_count.png");
    private static final Identifier OVERLAY_A = Identifier.fromNamespaceAndPath("vanilla_expansion", "textures/gui/overlay_a.png");
    private static final Identifier OVERLAY_MOD = Identifier.fromNamespaceAndPath("vanilla_expansion", "textures/gui/overlay_mod.png");

    private EditBox searchBox;
    private float scrollPosition = 0.0F;
    private boolean isScrolling = false;

    private Component pendingTooltip = null;

    private static final int SORT_BTN_X = 175;
    private static final int SORT_BTN_Y = 5;
    private static final int SORT_BTN_WIDTH = 12;
    private static final int SORT_BTN_HEIGHT = 10;

    private static final int SCROLLBAR_X = 175;
    private static final int SCROLLBAR_Y = 18;
    private static final int SCROLLBAR_WIDTH = 12;
    private static final int SCROLLBAR_HEIGHT = 110;
    private static final int THUMB_HEIGHT = 15;

    private static final int BTN_LEFT_X = 10;
    private static final int BTN_LEFT_SIZE = 14;

    private static final int BTN_BALANCE_Y = 113;
    private static final int BTN_ROTATE_Y = 131;
    private static final int BTN_CLEAR_PLAYER_Y = 149;

    private static final int BTN_CLEAR_STORAGE_X = 81;
    private static final int BTN_CLEAR_STORAGE_Y = 111;
    private static final int BTN_CLEAR_STORAGE_SIZE = 7;

    public CraftingInterfaceScreen(CraftingInterfaceMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title, 195, 252);
        this.titleLabelY = -1000;
        this.inventoryLabelY = -1000;
    }

    @Override
    protected void init() {
        super.init();

        this.searchBox = new EditBox(this.font, this.leftPos + 82, this.topPos + 6, 80, 9, Component.literal("Search"));
        this.searchBox.setCanLoseFocus(false);
        this.searchBox.setFocused(true);
        this.searchBox.setTextColor(-1);
        this.searchBox.setBordered(false);
        this.searchBox.setMaxLength(30);
        this.searchBox.setResponder(this::onSearchChanged);
        this.addRenderableWidget(this.searchBox);
    }

    private void onSearchChanged(String text) {
        this.scrollPosition = 0.0F;
        syncFilterAndScroll(text, 0);
    }

    private void syncFilterAndScroll(String text, int rowOffset) {
        ClientPlayNetworking.send(new C2SSyncStorageSearchPayload(
                text,
                rowOffset,
                this.menu.getSortMode().ordinal()
        ));
    }

    private void sendCraftingGridAction(int actionType) {
        Minecraft.getInstance().getSoundManager().play(
                SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F)
        );
        ClientPlayNetworking.send(new C2SCraftingGridActionPayload(actionType));
    }

    private boolean canScroll() {
        return this.menu.getTotalRows() > CraftingInterfaceMenu.ROWS;
    }

    @Override
    public boolean charTyped(CharacterEvent event) {
        if (this.searchBox.charTyped(event)) {
            return true;
        }
        return super.charTyped(event);
    }

    @Override
    public boolean keyPressed(KeyEvent event) {
        if (this.searchBox.isFocused() && this.searchBox.isVisible() && !event.isEscape()) {
            if (this.searchBox.keyPressed(event)) {
                return true;
            }
            return true;
        }
        return super.keyPressed(event);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
        if (!canScroll()) {
            return false;
        }

        int totalRows = this.menu.getTotalRows();
        int maxScrollRows = totalRows - CraftingInterfaceMenu.ROWS;

        float scrollDelta = (float) (verticalAmount / (double) maxScrollRows);
        this.scrollPosition = Mth.clamp(this.scrollPosition - scrollDelta, 0.0F, 1.0F);

        int targetRow = Math.round(this.scrollPosition * maxScrollRows);
        syncFilterAndScroll(this.searchBox.getValue(), targetRow);
        return true;
    }

    @Override
    public boolean mouseClicked(MouseButtonEvent event, boolean doubleClick) {
        if (event.button() == 0) {
            double mouseX = event.x();
            double mouseY = event.y();

            // 1. Sort Button
            if (isHovering(SORT_BTN_X, SORT_BTN_Y, SORT_BTN_WIDTH, SORT_BTN_HEIGHT, mouseX, mouseY)) {
                CraftingInterfaceMenu.SortMode nextMode = this.menu.getSortMode().next();
                this.menu.setSortMode(nextMode);

                Minecraft.getInstance().getSoundManager().play(
                        SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F)
                );

                syncFilterAndScroll(this.searchBox.getValue(), this.menu.getScrollRowOffset());
                return true;
            }

            // 2. Crafting Action Buttons
            if (isHovering(BTN_LEFT_X, BTN_BALANCE_Y, BTN_LEFT_SIZE, BTN_LEFT_SIZE, mouseX, mouseY)) {
                sendCraftingGridAction(C2SCraftingGridActionPayload.ACTION_BALANCE);
                return true;
            }

            if (isHovering(BTN_LEFT_X, BTN_ROTATE_Y, BTN_LEFT_SIZE, BTN_LEFT_SIZE, mouseX, mouseY)) {
                sendCraftingGridAction(C2SCraftingGridActionPayload.ACTION_ROTATE);
                return true;
            }

            if (isHovering(BTN_LEFT_X, BTN_CLEAR_PLAYER_Y, BTN_LEFT_SIZE, BTN_LEFT_SIZE, mouseX, mouseY)) {
                sendCraftingGridAction(C2SCraftingGridActionPayload.ACTION_CLEAR_TO_PLAYER);
                return true;
            }

            if (isHovering(BTN_CLEAR_STORAGE_X, BTN_CLEAR_STORAGE_Y, BTN_CLEAR_STORAGE_SIZE, BTN_CLEAR_STORAGE_SIZE, mouseX, mouseY)) {
                sendCraftingGridAction(C2SCraftingGridActionPayload.ACTION_CLEAR_TO_GRID);
                return true;
            }

            // 3. Scrollbar Click Check
            if (isHovering(SCROLLBAR_X, SCROLLBAR_Y, SCROLLBAR_WIDTH, SCROLLBAR_HEIGHT, mouseX, mouseY)) {
                this.isScrolling = canScroll();
                if (this.isScrolling) {
                    updateScrollPosition(mouseY);
                }
                return true;
            }
        }
        return super.mouseClicked(event, doubleClick);
    }

    @Override
    protected boolean isHovering(int relativeX, int relativeY, int width, int height, double mouseX, double mouseY) {
        int minX = this.leftPos + relativeX;
        int minY = this.topPos + relativeY;
        return mouseX >= minX && mouseX < minX + width && mouseY >= minY && mouseY < minY + height;
    }

    @Override
    public boolean mouseDragged(MouseButtonEvent event, double dx, double dy) {
        if (this.isScrolling) {
            updateScrollPosition(event.y());
            return true;
        }
        return super.mouseDragged(event, dx, dy);
    }

    @Override
    public boolean mouseReleased(MouseButtonEvent event) {
        if (event.button() == 0) {
            this.isScrolling = false;
        }
        return super.mouseReleased(event);
    }

    private void updateScrollPosition(double mouseY) {
        int trackTop = this.topPos + SCROLLBAR_Y;
        int usableTrack = SCROLLBAR_HEIGHT - THUMB_HEIGHT;

        float relativeY = (float) (mouseY - trackTop - (THUMB_HEIGHT / 2.0F));
        this.scrollPosition = Mth.clamp(relativeY / (float) usableTrack, 0.0F, 1.0F);

        int maxScrollRows = Math.max(0, this.menu.getTotalRows() - CraftingInterfaceMenu.ROWS);
        int targetRow = Math.round(this.scrollPosition * maxScrollRows);

        syncFilterAndScroll(this.searchBox.getValue(), targetRow);
    }

    /**
     * Helper method to set a pending tooltip for the current frame.
     */
    protected void setCustomTooltip(Component tooltip) {
        this.pendingTooltip = tooltip;
    }

    @Override
    public void extractContents(@NotNull GuiGraphicsExtractor graphics, int mouseX, int mouseY, float delta) {
        // Reset pending tooltip at the beginning of frame
        this.pendingTooltip = null;

        // 1. Draw GUI Background
        graphics.blit(RenderPipelines.GUI_TEXTURED, TEXTURE, this.leftPos, this.topPos, 0, 0,
                this.imageWidth, this.imageHeight, 256, 256);

        // 2. Draw Scrollbar Thumb
        int thumbY = this.topPos + SCROLLBAR_Y + (int) (this.scrollPosition * (SCROLLBAR_HEIGHT - THUMB_HEIGHT));
        int thumbX = this.leftPos + SCROLLBAR_X;
        Identifier scrollerSprite = canScroll() ? SCROLLER_SPRITE : SCROLLER_DISABLED_SPRITE;

        graphics.blitSprite(RenderPipelines.GUI_TEXTURED, scrollerSprite, thumbX, thumbY, SCROLLBAR_WIDTH, THUMB_HEIGHT);

        // 3. Draw Sort Mode Overlay Icon
        Identifier overlayTexture = switch (this.menu.getSortMode()) {
            case COUNT -> OVERLAY_COUNT;
            case NAME -> OVERLAY_A;
            case MOD -> OVERLAY_MOD;
        };

        graphics.blit(
                RenderPipelines.GUI_TEXTURED,
                overlayTexture,
                this.leftPos + SORT_BTN_X,
                this.topPos + SORT_BTN_Y,
                0, 0,
                SORT_BTN_WIDTH,
                SORT_BTN_HEIGHT,
                SORT_BTN_WIDTH,
                SORT_BTN_HEIGHT
        );

        // 4. Draw Hover Highlights & Tooltips
        renderButtonWithTooltip(graphics, SORT_BTN_X, SORT_BTN_Y, SORT_BTN_WIDTH, SORT_BTN_HEIGHT, mouseX, mouseY,
                Component.translatable("gui.vanilla_expansion.crafting.sort"));

        renderButtonWithTooltip(graphics, BTN_LEFT_X, BTN_BALANCE_Y, BTN_LEFT_SIZE, BTN_LEFT_SIZE, mouseX, mouseY,
                Component.translatable("gui.vanilla_expansion.crafting.spread"));

        renderButtonWithTooltip(graphics, BTN_LEFT_X, BTN_ROTATE_Y, BTN_LEFT_SIZE, BTN_LEFT_SIZE, mouseX, mouseY,
                Component.translatable("gui.vanilla_expansion.crafting.rotate"));

        renderButtonWithTooltip(graphics, BTN_LEFT_X, BTN_CLEAR_PLAYER_Y, BTN_LEFT_SIZE, BTN_LEFT_SIZE, mouseX, mouseY,
                Component.translatable("gui.vanilla_expansion.crafting.clear_to_player"));

        renderButtonWithTooltip(graphics, BTN_CLEAR_STORAGE_X, BTN_CLEAR_STORAGE_Y, BTN_CLEAR_STORAGE_SIZE, BTN_CLEAR_STORAGE_SIZE, mouseX, mouseY,
                Component.translatable("gui.vanilla_expansion.crafting.clear_to_storage"));

        // 5. Super call handles labels, slots, and item tooltips
        super.extractContents(graphics, mouseX, mouseY, delta);

        // 6. Draw queued button tooltip on top if present
        if (this.pendingTooltip != null) {
            renderCustomTooltip(graphics, this.pendingTooltip, mouseX, mouseY);
        }
    }

    private void renderButtonWithTooltip(GuiGraphicsExtractor graphics, int relX, int relY, int width, int height, double mouseX, double mouseY, Component tooltipText) {
        if (isHovering(relX, relY, width, height, mouseX, mouseY)) {
            int x1 = this.leftPos + relX;
            int y1 = this.topPos + relY;
            int x2 = x1 + width;
            int y2 = y1 + height;

            graphics.fill(x1, y1, x2, y2, 0x80FFFFFF);
            setCustomTooltip(tooltipText);
        }
    }

    /**
     * Manual rendering of tooltips directly using GuiGraphicsExtractor primitives.
     */
    private void renderCustomTooltip(GuiGraphicsExtractor graphics, Component tooltipText, int mouseX, int mouseY) {
        FormattedCharSequence visualOrder = tooltipText.getVisualOrderText();
        int textWidth = this.font.width(visualOrder);
        int textHeight = 8;

        int tooltipX = mouseX + 12;
        int tooltipY = mouseY - 12;

        // Keep inside screen bounds
        if (tooltipX + textWidth + 6 > this.width) {
            tooltipX = mouseX - 16 - textWidth;
        }
        if (tooltipY + textHeight + 6 > this.height) {
            tooltipY = this.height - textHeight - 6;
        }

        int x1 = tooltipX - 3;
        int y1 = tooltipY - 3;
        int x2 = tooltipX + textWidth + 3;
        int y2 = tooltipY + textHeight + 3;

        // Tooltip Background & Border
        graphics.fill(x1, y1, x2, y2, 0xF0100010);
        graphics.outline(x1 - 1, y1 - 1, x2 - x1 + 2, y2 - y1 + 2, 0x505000FF);

        // Text rendering
        graphics.text(this.font, visualOrder, tooltipX, tooltipY, 0xFFFFFFFF, true);
    }

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

    @Override
    protected void extractSlots(GuiGraphicsExtractor graphics, int mouseX, int mouseY) {
        for (Slot slot : this.menu.slots) {
            if (slot.isActive()) {
                if (slot.index < CraftingInterfaceMenu.VIEWPORT_SIZE && slot.hasItem()) {
                    extractNetworkSlot(graphics, slot);
                } else {
                    this.extractSlot(graphics, slot, mouseX, mouseY);
                }
            }
        }
    }

    private void extractNetworkSlot(GuiGraphicsExtractor graphics, Slot slot) {
        int x = slot.x;
        int y = slot.y;
        ItemStack itemStack = slot.getItem();

        int seed = slot.x + slot.y * this.imageWidth;
        graphics.item(itemStack, x, y, seed);

        ItemStack singleCountStack = itemStack.copyWithCount(1);
        graphics.itemDecorations(this.font, singleCountStack, x, y, null);

        int realCount = itemStack.getCount();
        String countText = formatCount(realCount);

        int textX = x + 17 - this.font.width(countText);
        int textY = y + 9;

        graphics.guiRenderState.addText(new GuiTextRenderState(
                this.font,
                FormattedCharSequence.forward(countText, Style.EMPTY),
                new Matrix3x2f(graphics.pose()),
                textX, textY, 0xFFFFFFFF, 0, true, false,
                graphics.scissorStack.peek()
        ));
    }
}