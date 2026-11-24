package com.chris.vanilla_expansion_dragons.mixin;

import com.chris.vanilla_expansion_dragons.entity.server.util.DragonAnimal;
import net.minecraft.client.Camera;
import net.minecraft.client.DeltaTracker;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.joml.Vector3fc;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(Camera.class)
public abstract class CameraMixin {

    @Shadow
    @Final
    private static Vector3fc FORWARDS;

    @Shadow
    @Final
    private static Vector3fc UP;

    @Shadow
    @Final
    private static Vector3fc LEFT;

    @Shadow
    private Entity entity;

    @Shadow
    private boolean detached;

    @Shadow
    private float xRot;

    @Shadow
    private Quaternionf rotation;

    @Shadow
    private Vector3f forwards;

    @Shadow
    private Vector3f up;

    @Shadow
    private Vector3f left;

    @Shadow
    private int matrixPropertiesDirty;

    @Shadow
    public abstract float getCameraEntityPartialTicks(final DeltaTracker deltaTracker);

    @Shadow
    protected abstract void move(final float forwards, final float up, final float right);

    @Inject(method = "update", at = @At("TAIL"))
    private void vanillaExpansionDragons$free3DFlightCamera(
            final DeltaTracker deltaTracker,
            final CallbackInfo ci
    ) {
        if (this.entity == null
                || !(this.entity.getVehicle() instanceof DragonAnimal dragon)
                || !dragon.isFlying()) {
            return;
        }

        float partialTicks = this.getCameraEntityPartialTicks(deltaTracker);

        float flightPitch = dragon.getLerpedDynamicPitch(partialTicks);
        float flightRoll = dragon.getLerpedRoll(partialTicks);
        float yawLean = dragon.getLerpedYawLean(partialTicks);

        // Compensate for vanilla's camera pitch limit.
        float vanillaPitch = Mth.clamp(this.xRot, -90.0F, 90.0F);
        float extraPitch = flightPitch - vanillaPitch;

        // Combine the dragon's bank with the subtle yaw-induced lean.
        float cameraRoll = flightRoll + yawLean;

        boolean updatedRotation = false;

        if (Math.abs(extraPitch) > 0.001F) {
            this.rotation.rotateX(extraPitch * Mth.DEG_TO_RAD);
            updatedRotation = true;
        }

        if (Math.abs(cameraRoll) > 0.001F) {
            this.rotation.rotateZ(cameraRoll * Mth.DEG_TO_RAD);
            updatedRotation = true;
        }

        if (updatedRotation) {
            FORWARDS.rotate(this.rotation, this.forwards);
            UP.rotate(this.rotation, this.up);
            LEFT.rotate(this.rotation, this.left);

            this.matrixPropertiesDirty |= 3;
        }

        if (!this.detached) {
            this.move(0.35F, 0.35F, 0.0F);
        } else {
            this.move(0.0F, 0.6F, 0.0F);
        }
    }
    @ModifyVariable(
            method = "getMaxZoom(F)F",
            at = @At("HEAD"),
            argsOnly = true
    )
    private float vanillaExpansionDragons$increaseDragonCameraDistance(float cameraDistance) {
        if (this.entity != null
                && this.entity.getVehicle() instanceof DragonAnimal) {
            return 11.0F;
        }

        return cameraDistance;
    }
}