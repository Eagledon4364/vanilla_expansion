package com.chris.vanilla_expansion.mixin;

import com.chris.vanilla_expansion.render.BackpackRenderState;
import net.minecraft.client.entity.ClientAvatarEntity;
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
public abstract class AvatarRendererMixin<AvatarlikeEntity extends Avatar & ClientAvatarEntity> {

    @Inject(
            method = "extractRenderState(Lnet/minecraft/world/entity/Avatar;Lnet/minecraft/client/renderer/entity/state/AvatarRenderState;F)V",
            at = @At("TAIL")
    )
    private void extractBackpackState(AvatarlikeEntity entity, AvatarRenderState state, float partialTicks, CallbackInfo ci) {
        if (entity instanceof Player player && state instanceof BackpackRenderState backpackState) {
            ItemStack stack = player.getInventory().getItem(42);
            backpackState.vanillaExpansion$setBackpack(stack.copy());
        }
    }
}