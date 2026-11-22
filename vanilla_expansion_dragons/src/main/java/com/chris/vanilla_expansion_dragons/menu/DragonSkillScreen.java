package com.chris.vanilla_expansion_dragons.menu;

import com.chris.vanilla_expansion_dragons.VanillaExpansionDragons;
import com.chris.vanilla_expansion_dragons.entity.server.ElderWingDragonEntity;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;

public class DragonSkillScreen extends AbstractContainerScreen<DragonSkillMenu> {

    private static final Identifier GUI_TEXTURE = Identifier.fromNamespaceAndPath(
            VanillaExpansionDragons.MOD_ID,
            "textures/gui/dragon_skill_gui.png"
    );

    private static final int TEXTURE_WIDTH = 256;
    private static final int TEXTURE_HEIGHT = 256;

    public DragonSkillScreen(@NotNull DragonSkillMenu menu, Inventory inventory, Component title) {
        this(menu, inventory, title, 176, 190);
    }

    public DragonSkillScreen(@NotNull DragonSkillMenu menu, Inventory inventory, Component title, int imageWidth, int imageHeight) {
        super(menu, inventory, title, imageWidth, imageHeight);
        this.titleLabelX = 8;
        this.titleLabelY = 6;
        this.inventoryLabelX = 8;
        this.inventoryLabelY = 98;
    }

    @Override
    protected void init() {
        super.init();

        this.addRenderableWidget(Button.builder(
                        Component.literal("Main"),
                        button -> {
                            if (this.minecraft != null && this.minecraft.gameMode != null) {
                                this.minecraft.gameMode.handleInventoryButtonClick(
                                        this.menu.containerId,
                                        DragonSkillMenu.BUTTON_OPEN_MAIN
                                );
                            }
                        }
                )
                .bounds(this.leftPos + 124, this.topPos + 4, 44, 14)
                .build());

        addStatPlusButton(ElderWingDragonEntity.StatType.HEALTH, DragonSkillMenu.BUTTON_UPGRADE_HEALTH, 22);
        addStatPlusButton(ElderWingDragonEntity.StatType.STAMINA, DragonSkillMenu.BUTTON_UPGRADE_STAMINA, 38);
        addStatPlusButton(ElderWingDragonEntity.StatType.STRENGTH, DragonSkillMenu.BUTTON_UPGRADE_STRENGTH, 54);
        addStatPlusButton(ElderWingDragonEntity.StatType.SPEED, DragonSkillMenu.BUTTON_UPGRADE_SPEED, 70);
        addStatPlusButton(ElderWingDragonEntity.StatType.BREATH, DragonSkillMenu.BUTTON_UPGRADE_BREATH, 86);
    }

    private void addStatPlusButton(ElderWingDragonEntity.StatType stat, int buttonId, int yOffset) {
        this.addRenderableWidget(Button.builder(
                        Component.literal("+"),
                        button -> {
                            if (this.minecraft != null && this.minecraft.gameMode != null && this.menu.getSkillPoints() > 0) {
                                this.minecraft.gameMode.handleInventoryButtonClick(
                                        this.menu.containerId,
                                        buttonId
                                );
                            }
                        }
                )
                .bounds(this.leftPos + 148, this.topPos + yOffset, 14, 12)
                .build());
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

        super.extractContents(graphics, mouseX, mouseY, partialTick);

        String pointsText = "Points: " + this.menu.getSkillPoints();
        graphics.text(this.font, Component.literal(pointsText), this.leftPos + 90, this.topPos + 20, -12566464, false);

        drawStatRow(graphics, "Health", ElderWingDragonEntity.StatType.HEALTH, 24);
        drawStatRow(graphics, "Stamina", ElderWingDragonEntity.StatType.STAMINA, 40);
        drawStatRow(graphics, "Strength", ElderWingDragonEntity.StatType.STRENGTH, 56);
        drawStatRow(graphics, "Speed", ElderWingDragonEntity.StatType.SPEED, 72);
        drawStatRow(graphics, "Breath", ElderWingDragonEntity.StatType.BREATH, 88);
    }

    private void drawStatRow(GuiGraphicsExtractor graphics, String label, ElderWingDragonEntity.StatType stat, int yOffset) {
        int pts = this.menu.getStatPoints(stat);
        graphics.text(this.font, Component.literal(label + ": " + pts), this.leftPos + 14, this.topPos + yOffset, -12566464, false);
    }

    @Override
    protected void extractLabels(@NotNull GuiGraphicsExtractor graphics, int mouseX, int mouseY) {
        graphics.text(this.font, this.title, this.titleLabelX, this.titleLabelY, -12566464, false);
        graphics.text(this.font, this.playerInventoryTitle, this.inventoryLabelX, this.inventoryLabelY, -12566464, false);
    }
}