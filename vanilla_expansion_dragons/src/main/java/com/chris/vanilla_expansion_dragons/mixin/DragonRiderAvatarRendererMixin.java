
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
import org.joml.Vector3f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AvatarRenderer.class)
public abstract class DragonRiderAvatarRendererMixin<
        AvatarlikeEntity extends Avatar & ClientAvatarEntity> {


    @Inject(
            method = "setupRotations",
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

        ElderWingDragonEntity dragon =
                access.vanillaExpansionDragons$getDragon();

        Player player =
                access.vanillaExpansionDragons$getPlayer();

        if (dragon == null || player == null) {
            return;
        }

        float partialTicks =
                access.vanillaExpansionDragons$getPartialTicks();

        if (!(Minecraft.getInstance()
                .getEntityRenderDispatcher()
                .getRenderer(dragon) instanceof ElderWingDragonRenderer dragonRenderer)) {
            return;
        }

        // Get the animated saddle bone transform.
        Matrix4f saddleTransform =
                dragonRenderer.getSaddleLockTransform(dragon, partialTicks);

        Vector3f saddleLocalPosition =
                saddleTransform.getTranslation(new Vector3f());

        Quaternionf saddleRotation =
                saddleTransform.getUnnormalizedRotation(new Quaternionf());

        // Calculate the saddle position in world space.
        Vec3 saddleWorldPosition = dragon.getPosition(partialTicks).add(
                saddleLocalPosition.x,
                saddleLocalPosition.y,
                saddleLocalPosition.z
        );

        // Account for the player's model origin relative to the saddle.
        Vector3f playerModelOffset = new Vector3f(
                0.0F,
                -1.501F * state.scale * 0.9375F,
                0.0F
        );

        saddleRotation.transform(playerModelOffset);

        saddleWorldPosition = saddleWorldPosition.subtract(
                playerModelOffset.x,
                playerModelOffset.y,
                playerModelOffset.z
        );

        // Calculate the difference between the player's current
        // render position and the saddle's world position.
        Vec3 playerWorldPosition = player.getPosition(partialTicks);

        Vec3 movement = saddleWorldPosition.subtract(playerWorldPosition);

        // Apply translation BEFORE applying the saddle rotation.
        poseStack.translate(
                movement.x,
                movement.y,
                movement.z
        );

        // Preserve the existing rider orientation adjustment.
        Quaternionf adjustedRotation = new Quaternionf(saddleRotation);
        adjustedRotation.mul(
                new Quaternionf().rotationZ((float) Math.PI)
        );

        poseStack.mulPose(new Matrix4f().rotation(adjustedRotation));

        ci.cancel();
    }

}
