package com.chris.vanilla_expansion.mixin;

import com.chris.vanilla_expansion.util.PlayerDragonCharge;
import com.chris.vanilla_expansion.render.DragonChargeBarRenderer;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
public abstract class GuiMixin {
    @Shadow @Final private Minecraft minecraft;

    @Inject(method = "extractHotbarAndDecorations", at = @At("HEAD"))
    private void forceDragonBar(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker, CallbackInfo ci) {
        if (this.minecraft.player != null) {
            Entity vehicle = this.minecraft.player.getVehicle();

            if (vehicle instanceof PlayerDragonCharge dragon) {
                graphics.nextStratum();
                DragonChargeBarRenderer renderer = new DragonChargeBarRenderer(this.minecraft);
                renderer.extractBackground(graphics, deltaTracker);
                renderer.extractRenderState(graphics, deltaTracker);
            }
        }
    }
}