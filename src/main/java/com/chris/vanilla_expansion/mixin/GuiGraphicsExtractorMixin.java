package com.chris.vanilla_expansion.mixin;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiGraphicsExtractor.class)
public abstract class GuiGraphicsExtractorMixin {

    @Shadow public abstract void text(Font font, @Nullable String str, int x, int y, int color, boolean dropShadow);

    @Inject(method = "itemCount", at = @At("HEAD"), cancellable = true)
    private void forceRenderCorrectCount(Font font, ItemStack stack, int x, int y, String countText, CallbackInfo ci) {
        if (!stack.isEmpty() && stack.getCount() > 1) {


            String amount = String.valueOf(stack.getCount());

            int drawX = x + 19 - 2 - font.width(amount);
            int drawY = y + 6 + 3;


            this.text(font, amount, drawX, drawY, -1, true);
            ci.cancel();
        }
    }
}