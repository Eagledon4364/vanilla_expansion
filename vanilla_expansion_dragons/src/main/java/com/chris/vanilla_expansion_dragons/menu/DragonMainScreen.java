package com.chris.vanilla_expansion_dragons.menu;

import com.chris.vanilla_expansion.entity.server.DragonAnimal;
import com.chris.vanilla_expansion_dragons.VanillaExpansionDragons;
import com.chris.vanilla_expansion_dragons.entity.server.ElderWingDragonEntity;
import com.chris.vanilla_expansion_dragons.entity.server.util.Gender;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;

public class DragonMainScreen extends AbstractContainerScreen<@NotNull DragonMainMenu> {

    private static final Identifier GUI_TEXTURE = Identifier.fromNamespaceAndPath(
            VanillaExpansionDragons.MOD_ID,
            "textures/gui/dragon_main_gui.png"
    );

    // Texture dimensions for dragon_main_gui.png
    private static final int TEXTURE_WIDTH = 256;
    private static final int TEXTURE_HEIGHT = 256;

    private static final int BAR_COLOR_MALE = 0xFF4CAF50;
    private static final int BAR_COLOR_FEMALE = 0xFFE91E63;

    private final DragonAnimal dragon;
    private EditBox nameBox;

    public DragonMainScreen(@NotNull DragonMainMenu menu, Inventory inventory, Component title) {
        this(menu, inventory, title, 176, 190);
    }

    public DragonMainScreen(@NotNull DragonMainMenu menu, Inventory inventory, Component title, int imageWidth, int imageHeight) {
        super(menu, inventory, title, imageWidth, imageHeight);
        this.dragon = menu.getDragon();
        this.titleLabelX = 8;
        this.titleLabelY = 6;
        this.inventoryLabelX = 8;
        this.inventoryLabelY = 98;
    }

    @Override
    protected void init() {
        super.init();

        this.nameBox = new EditBox(
                this.font,
                this.leftPos + 82,
                this.topPos + 32,
                84,
                12,
                Component.translatable("gui.vanilla_expansion_dragons.dragon_name")
        );
        this.nameBox.setMaxLength(32);
        this.nameBox.setBordered(false);
        this.nameBox.setTextColor(-1);
        if (this.dragon != null && this.dragon.hasCustomName()) {
            this.nameBox.setValue(this.dragon.getCustomName().getString());
        } else if (this.dragon != null) {
            this.nameBox.setValue(this.dragon.getName().getString());
        }
        this.addRenderableWidget(this.nameBox);

        // Button to switch to the Dragon Skill Menu (placed cleanly near the tab/header area)
        this.addRenderableWidget(Button.builder(
                        Component.literal("Skills"),
                        button -> {
                            if (this.minecraft != null && this.minecraft.gameMode != null) {
                                this.minecraft.gameMode.handleInventoryButtonClick(
                                        this.menu.containerId,
                                        DragonMainMenu.BUTTON_OPEN_SKILLS
                                );
                            }
                        }
                )
                .bounds(this.leftPos + 122, this.topPos + 4, 46, 14)
                .build());
    }

    private int getXpBarColor() {
        if (this.dragon instanceof ElderWingDragonEntity elder && elder.getGender() == Gender.FEMALE) {
            return BAR_COLOR_FEMALE;
        }
        return BAR_COLOR_MALE;
    }

    @Override
    public void extractContents(@NotNull GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTick) {
        graphics.blit(
                RenderPipelines.GUI_TEXTURED,
                GUI_TEXTURE,
                this.leftPos,
                this.topPos,
                0,
                0,
                this.imageWidth,
                this.imageHeight,
                TEXTURE_WIDTH,
                TEXTURE_HEIGHT
        );

        int barX = this.leftPos + 82;
        int barY = this.topPos + 54;
        int barWidth = 84;
        int barHeight = 10;

        float progress = Mth.clamp(
                (float) this.menu.getDragonXp() / Math.max(1, this.menu.getXpForNextLevel()),
                0.0F,
                1.0F
        );
        int fillWidth = Math.round(barWidth * progress);
        if (fillWidth > 0) {
            graphics.fill(barX, barY, barX + fillWidth, barY + barHeight, this.getXpBarColor());
        }

        String xpText = "Lv." + this.menu.getDragonLevel() + " (" + this.menu.getDragonXp() + "/" + this.menu.getXpForNextLevel() + ")";
        int textX = barX + (barWidth - this.font.width(xpText)) / 2;
        int textY = barY + (barHeight - this.font.lineHeight) / 2 + 1;
        graphics.text(this.font, Component.literal(xpText), textX, textY, -1, true);

        super.extractContents(graphics, mouseX, mouseY, partialTick);

        if (this.dragon != null) {
            InventoryScreen.extractEntityInInventoryFollowsMouse(
                    graphics,
                    this.leftPos + 16, this.topPos + 16,
                    this.leftPos + 64, this.topPos + 64,
                    22, 0.0625F,
                    (float) mouseX, (float) mouseY,
                    this.dragon
            );
        }
    }

    @Override
    protected void extractLabels(@NotNull GuiGraphicsExtractor graphics, int mouseX, int mouseY) {
        graphics.text(this.font, this.title, this.titleLabelX, this.titleLabelY, -12566464, false);
        graphics.text(this.font, this.playerInventoryTitle, this.inventoryLabelX, this.inventoryLabelY, -12566464, false);
    }

    public String getEnteredDragonName() {
        return this.nameBox != null ? this.nameBox.getValue().trim() : "";
    }

    @Override
    public boolean keyPressed(KeyEvent event) {
        if (this.nameBox != null && this.nameBox.isFocused()) {
            if (event.key() == 256) {
                this.nameBox.setFocused(false);
                return true;
            }
            return this.nameBox.keyPressed(event) || super.keyPressed(event);
        }
        return super.keyPressed(event);
    }
}