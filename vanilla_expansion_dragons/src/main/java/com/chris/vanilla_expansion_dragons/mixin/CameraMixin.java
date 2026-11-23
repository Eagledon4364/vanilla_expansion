package com.chris.vanilla_expansion_dragons.mixin;

import com.chris.vanilla_expansion_dragons.entity.server.ElderWingDragonEntity;
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

    @Inject(method = "update", at = @At("TAIL"))
    private void vanillaExpansionDragons$applyDragonFlightRoll(final DeltaTracker deltaTracker, CallbackInfo ci) {
        if (this.entity != null && this.entity.getVehicle() instanceof DragonAnimal dragon && dragon.isFlying()) {
            float partialTicks = this.getCameraEntityPartialTicks(deltaTracker);
            float rollDegrees = dragon.getLerpedRoll(partialTicks) * 0.2F;
            if (Math.abs(rollDegrees) > 0.001F) {
                this.rotation.rotateZ(rollDegrees * Mth.DEG_TO_RAD);
                FORWARDS.rotate(this.rotation, this.forwards);
                UP.rotate(this.rotation, this.up);
                LEFT.rotate(this.rotation, this.left);
                this.matrixPropertiesDirty |= 3;
            }
        }
    }
}