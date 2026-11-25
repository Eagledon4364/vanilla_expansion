package com.chris.vanilla_expansion_dragons.mixin;

import com.chris.vanilla_expansion_dragons.entity.client.renderer.DragonRiderRenderStateAccess;
import com.chris.vanilla_expansion_dragons.entity.client.renderer.elderwing.ElderWingDragonRenderer;
import com.chris.vanilla_expansion_dragons.entity.server.ElderWingDragonEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.ClientAvatarEntity;
import net.minecraft.client.renderer.entity.player.AvatarRenderer;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.world.entity.Avatar;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AvatarRenderer.class)
public abstract class DragonRiderAvatarRendererMixin<
        AvatarlikeEntity extends Avatar & ClientAvatarEntity> {

    // Balanced between 0.0D (too low) and 29/16 = 1.8125D (too high)
    private static final double SEAT_Y_TWEAK = 0.95D;
    private static final double SEAT_Z_TWEAK = 1.0D / 16.0D;

    @Inject(
            method = "extractRenderState(Lnet/minecraft/world/entity/Avatar;Lnet/minecraft/client/renderer/entity/state/AvatarRenderState;F)V",
            at = @At("TAIL")
    )
    private void vanillaExpansionDragons$captureRiderState(
            AvatarlikeEntity entity,
            AvatarRenderState state,
            float partialTicks,
            CallbackInfo ci
    ) {
        if (!(state instanceof DragonRiderRenderStateAccess access)) {
            return;
        }

        if (entity instanceof Player player && player.getVehicle() instanceof ElderWingDragonEntity dragon) {
            access.vanillaExpansionDragons$setRider(dragon, player, partialTicks);
            access.vanillaExpansionDragons$setSaddleRotation(new Quaternionf());
        } else {
            access.vanillaExpansionDragons$setRider(null, null, partialTicks);
        }
    }

    @Inject(
            method = "setupRotations(Lnet/minecraft/client/renderer/entity/state/AvatarRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;FF)V",
            at = @At("HEAD"),
            cancellable = true
    )
    private void vanillaExpansionDragons$rotateRiderWithSaddle(
            AvatarRenderState state,
            PoseStack poseStack,
            float bodyRot,
            float entityScale,
            CallbackInfo ci
    ) {
        if (!(state instanceof DragonRiderRenderStateAccess access)
                || !access.vanillaExpansionDragons$hasSaddleTransform()) {
            return;
        }

        ElderWingDragonEntity dragon = access.vanillaExpansionDragons$getDragon();
        Player player = access.vanillaExpansionDragons$getPlayer();
        if (dragon == null || player == null) {
            return;
        }

        float partialTicks = access.vanillaExpansionDragons$getPartialTicks();
        if (!(Minecraft.getInstance()
                .getEntityRenderDispatcher()
                .getRenderer(dragon) instanceof ElderWingDragonRenderer dragonRenderer)) {
            return;
        }

        ElderWingDragonRenderer.SaddleLockPose saddlePose =
                dragonRenderer.getSaddleLockPose(dragon, partialTicks);

        Vec3 dragonPos = dragon.getPosition(partialTicks);
        Vec3 playerPos = player.getPosition(partialTicks);
        Vec3 saddleWorldPos = dragonPos.add(saddlePose.localOffset());

        Vec3 deltaFromPlayer = saddleWorldPos.subtract(playerPos);
        poseStack.translate(deltaFromPlayer.x, deltaFromPlayer.y, deltaFromPlayer.z);

        Quaternionf riderRotation = new Quaternionf(saddlePose.rotation());
        poseStack.mulPose(new Matrix4f().rotation(riderRotation));

        if (SEAT_Y_TWEAK != 0.0D || SEAT_Z_TWEAK != 0.0D) {
            poseStack.translate(0.0D, SEAT_Y_TWEAK, SEAT_Z_TWEAK);
        }

        ci.cancel();
    }
}