package com.chris.vanilla_expansion.render;

import com.chris.vanilla_expansion.entity.server.DragonAnimal;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.contextualbar.ContextualBarRenderer;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;

public class DragonChargeBarRenderer implements ContextualBarRenderer {
    private final Minecraft minecraft;
    private static final Identifier PROGRESS_SPRITE = Identifier.withDefaultNamespace("hud/jump_bar_progress");
    private static final Identifier BACKGROUND_SPRITE = Identifier.withDefaultNamespace("hud/jump_bar_background");

    public DragonChargeBarRenderer(Minecraft minecraft) {
        this.minecraft = minecraft;
    }

    @Override
    public void extractBackground(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker) {
        int x = graphics.guiWidth() / 2 - 91;
        int y = graphics.guiHeight() - 32 + 3;
        graphics.blitSprite(RenderPipelines.GUI_TEXTURED, BACKGROUND_SPRITE, x, y, 182, 5);
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker) {
        // System.out.println("Drawing Dragon Bar! Width: " + graphics.guiWidth()); // Debug check
        if (this.minecraft.player.getVehicle() instanceof DragonAnimal dragon) {
            int x = graphics.guiWidth() / 2 - 91;
            int y = graphics.guiHeight() - 32 + 3;

            float fill = dragon.getChargeBarFill();

            if (fill > 0.0F) {
                int fillWidth = (int)(fill * 182.0F);
                graphics.blitSprite(RenderPipelines.GUI_TEXTURED, PROGRESS_SPRITE, 182, 5, 0, 0, x, y, fillWidth, 5);
            }
        }
    }
}