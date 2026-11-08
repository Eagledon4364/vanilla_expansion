package com.chris.vanilla_expansion.mixin;

import com.chris.vanilla_expansion.item.ModItems;
import net.minecraft.client.renderer.entity.layers.CapeLayer;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CapeLayer.class)
public class MixinCapeLayer {

    @Inject(method = "submit", at = @At("HEAD"), cancellable = true)
    private void hideCapeWithEnergyDragonArmor(
            com.mojang.blaze3d.vertex.PoseStack poseStack,
            net.minecraft.client.renderer.SubmitNodeCollector submitNodeCollector,
            int lightCoords,
            AvatarRenderState avatarRenderState,
            float f,
            float g,
            CallbackInfo ci) {

        if (avatarRenderState.chestEquipment.is(ModItems.ENERGY_DRAGON_CHESTPLATE)) {

            ci.cancel();
        }
    }
}