package com.chris.vanilla_expansion.screen.storage;

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
import net.minecraft.core.BlockPos;
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

import static com.chris.vanilla_expansion.screen.storage.StorageInterfaceMenu.ROWS;

public class StorageInterfaceScreen extends AbstractContainerScreen<@NotNull StorageInterfaceMenu> {
    private static final Identifier TEXTURE = Identifier.fromNamespaceAndPath("vanilla_expansion", "textures/gui/storage_interface.png");

    private static final Identifier SCROLLER_SPRITE = Identifier.withDefaultNamespace("container/creative_inventory/scroller");
    private static final Identifier SCROLLER_DISABLED_SPRITE = Identifier.withDefaultNamespace("container/creative_inventory/scroller_disabled");

    private static final Identifier OVERLAY_COUNT = Identifier.fromNamespaceAndPath("vanilla_expansion", "textures/gui/overlay_count.png");
    private static final Identifier OVERLAY_A = Identifier.fromNamespaceAndPath("vanilla_expansion", "textures/gui/overlay_a.png");
    private static final Identifier OVERLAY_MOD = Identifier.fromNamespaceAndPath("vanilla_expansion", "textures/gui/overlay_mod.png");

    private EditBox searchBox;
    private float scrollPosition = 0.0F;
    private boolean isScrolling = false;

    private static final int SORT_BTN_X = 175;
    private static final int SORT_BTN_Y = 5;
    private static final int SORT_BTN_WIDTH = 12;
    private static final int SORT_BTN_HEIGHT = 12;

    private static final int SCROLLBAR_X = 175;
    private static final int SCROLLBAR_Y = 18;
    private static final int SCROLLBAR_WIDTH = 12;
    private static final int SCROLLBAR_HEIGHT = 110;
    private static final int THUMB_HEIGHT = 15;

    public StorageInterfaceScreen(StorageInterfaceMenu handler, Inventory inventory, Component title) {
        super(handler, inventory, title, 195, 195);
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

        //System.out.println("[DEBUG-SCREEN] Screen Initialized with Client SortMode: " + this.menu.getSortMode());
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

    private boolean canScroll() {
        return this.menu.getTotalRows() > ROWS;
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
        int maxScrollRows = totalRows - ROWS;

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

            // 1. Check Toggle Sort Overlay Button Click
            int sortMinX = this.leftPos + SORT_BTN_X;
            int sortMinY = this.topPos + SORT_BTN_Y;
            int sortMaxX = sortMinX + SORT_BTN_WIDTH;
            int sortMaxY = sortMinY + SORT_BTN_HEIGHT;

            if (mouseX >= sortMinX && mouseX <= sortMaxX && mouseY >= sortMinY && mouseY <= sortMaxY) {
                StorageInterfaceMenu.SortMode nextMode = this.menu.getSortMode().next();
                //System.out.println("[DEBUG-SCREEN] Sort Button Clicked! Transitioning from " + this.menu.getSortMode() + " -> " + nextMode);

                this.menu.setSortMode(nextMode);

                // Play click sound
                Minecraft.getInstance().getSoundManager().play(
                        SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F)
                );

                // Request immediate resync from server with current search text
                syncFilterAndScroll(this.searchBox.getValue(), this.menu.getScrollRowOffset());
                return true;
            }

            // 2. Check Scrollbar Click
            int scrollMinX = this.leftPos + SCROLLBAR_X;
            int scrollMinY = this.topPos + SCROLLBAR_Y;
            int scrollMaxX = scrollMinX + SCROLLBAR_WIDTH;
            int scrollMaxY = scrollMinY + SCROLLBAR_HEIGHT;

            if (mouseX >= scrollMinX && mouseX <= scrollMaxX && mouseY >= scrollMinY && mouseY <= scrollMaxY) {
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

        int maxScrollRows = Math.max(0, this.menu.getTotalRows() - ROWS);
        int targetRow = Math.round(this.scrollPosition * maxScrollRows);

        syncFilterAndScroll(this.searchBox.getValue(), targetRow);
    }

    @Override
    public void extractContents(@NotNull GuiGraphicsExtractor graphics, int mouseX, int mouseY, float delta) {
        // 1. Draw GUI Background
        graphics.blit(RenderPipelines.GUI_TEXTURED, TEXTURE, this.leftPos, this.topPos, 0, 0,
                this.imageWidth, this.imageHeight, 256, 256);

        // 2. Draw Scrollbar Thumb
        int thumbY = this.topPos + SCROLLBAR_Y + (int) (this.scrollPosition * (SCROLLBAR_HEIGHT - THUMB_HEIGHT));
        int thumbX = this.leftPos + SCROLLBAR_X;
        Identifier scrollerSprite = canScroll() ? SCROLLER_SPRITE : SCROLLER_DISABLED_SPRITE;

        graphics.blitSprite(RenderPipelines.GUI_TEXTURED, scrollerSprite, thumbX, thumbY, SCROLLBAR_WIDTH, THUMB_HEIGHT);

        // 3. Draw Current Sort Overlay Icon
        StorageInterfaceMenu.SortMode activeMode = this.menu.getSortMode();
        Identifier currentOverlay = switch (activeMode) {
            case COUNT -> OVERLAY_COUNT;
            case NAME -> OVERLAY_A;
            case MOD -> OVERLAY_MOD;
        };

        graphics.blit(
                RenderPipelines.GUI_TEXTURED,
                currentOverlay,
                this.leftPos + SORT_BTN_X,
                this.topPos + SORT_BTN_Y,
                0, 0,
                SORT_BTN_WIDTH,
                SORT_BTN_HEIGHT,
                SORT_BTN_WIDTH,
                SORT_BTN_HEIGHT
        );

        // 4. Super call handles labels, slots, and tooltips
        super.extractContents(graphics, mouseX, mouseY, delta);
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
                if (slot.index < StorageInterfaceMenu.VIEWPORT_SIZE && slot.hasItem()) {
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