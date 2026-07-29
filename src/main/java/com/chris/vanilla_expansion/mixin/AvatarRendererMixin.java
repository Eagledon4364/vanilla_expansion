package com.chris.vanilla_expansion.mixin;

import com.chris.vanilla_expansion.render.BackpackRenderState;
import net.minecraft.client.renderer.entity.player.AvatarRenderer;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.world.entity.Avatar;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AvatarRenderer.class)
public abstract class AvatarRendererMixin {

    @Inject(method = "extractRenderState", at = @At("TAIL"))
    private void extractBackpackState(Avatar avatar, AvatarRenderState state, float partialTick, CallbackInfo ci) {
        if (avatar instanceof Player player && state instanceof BackpackRenderState backpackState) {
            ItemStack stack = player.getInventory().getItem(42);
            backpackState.vanillaExpansion$setBackpack(stack.copy());
        }
    }
}