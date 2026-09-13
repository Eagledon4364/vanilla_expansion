package com.chris.vanilla_expansion.util;

import com.chris.vanilla_expansion.entity.server.DragonAnimal;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.phys.Vec3;

public class DragonMoveControl extends MoveControl {
    private final DragonAnimal dragon;

    public DragonMoveControl(DragonAnimal dragon) {
        super(dragon);
        this.dragon = dragon;
    }

    @Override
    public void tick() {
        if (this.dragon.isOrderedToSit() || this.dragon.isSleeping()) {
            this.dragon.setSpeed(0.0F);
            this.dragon.setDeltaMovement(this.dragon.getDeltaMovement().multiply(0.0D, 1.0D, 0.0D));
            return;
        }

        if (this.dragon.isVehicle()) {
            return;
        }

        if (this.dragon.canFly() && this.dragon.isFlying()) {
            handleFlightMovement();
            return;
        }

        if (this.dragon.canSwim() && this.dragon.isInWater()) {
            handleWaterMovement();
            return;
        }

        super.tick();
    }

    private void handleFlightMovement() {
        if (this.operation == MoveControl.Operation.MOVE_TO) {
            this.operation = Operation.WAIT;

            double dx = this.wantedX - this.dragon.getX();
            double dy = this.wantedY - this.dragon.getY();
            double dz = this.wantedZ - this.dragon.getZ();
            double distanceSq = dx * dx + dy * dy + dz * dz;

            if (distanceSq < 2.25D) {
                this.dragon.setDragonState(DragonAnimal.DragonState.HOVER);
                return;
            }

            double distance = Math.sqrt(distanceSq);
            Vec3 directionVec = new Vec3(dx / distance, dy / distance, dz / distance);

            // Yaw and pitch rotation
            float targetYaw = (float) (Mth.atan2(dz, dx) * (180F / Math.PI)) - 90.0F;
            this.dragon.setYRot(this.rotlerp(this.dragon.getYRot(), targetYaw, this.dragon.getTurnSpeed()));
            this.dragon.yBodyRot = this.dragon.getYRot();

            float targetPitch = (float) (-(Mth.atan2(dy, Math.sqrt(dx * dx + dz * dz)) * (180F / Math.PI)));
            this.dragon.setXRot(this.rotlerp(this.dragon.getXRot(), targetPitch, this.dragon.getTurnSpeed()));

            double baseFlySpeed = this.dragon.getAttributeValue(Attributes.FLYING_SPEED);
            float yawDiff = Math.abs(Mth.wrapDegrees(targetYaw - this.dragon.getYRot()));
            float turnDampening = Mth.clamp(1.0F - (yawDiff / 120.0F), 0.4F, 1.0F);

            double targetSpeed = baseFlySpeed * this.speedModifier * turnDampening;
            Vec3 travelVec = directionVec.scale(targetSpeed);

            this.dragon.setDeltaMovement(this.dragon.getDeltaMovement().lerp(travelVec, 0.2D));
            this.dragon.resetFallDistance();
        } else {
            // Hover dampening when no active target
            Vec3 currentVel = this.dragon.getDeltaMovement();
            Vec3 forwardLook = this.dragon.getLookAngle().scale(0.05D);
            this.dragon.setDeltaMovement(new Vec3(
                    currentVel.x * 0.9D + forwardLook.x,
                    currentVel.y * 0.8D,
                    currentVel.z * 0.9D + forwardLook.z
            ));
            this.dragon.resetFallDistance();
        }
    }

    private void handleWaterMovement() {
        if (this.operation == MoveControl.Operation.MOVE_TO) {
            double dx = this.wantedX - this.dragon.getX();
            double dy = this.wantedY - this.dragon.getY();
            double dz = this.wantedZ - this.dragon.getZ();
            double distance = Math.sqrt(dx * dx + dy * dy + dz * dz);

            if (distance < 0.5D) {
                this.dragon.setSpeed(0.0F);
                return;
            }

            float targetYaw = (float) (Mth.atan2(dz, dx) * (180F / Math.PI)) - 90.0F;
            this.dragon.setYRot(this.rotlerp(this.dragon.getYRot(), targetYaw, this.dragon.getTurnSpeed()));
            this.dragon.yBodyRot = this.dragon.getYRot();

            double swimSpeed = this.dragon.getAttributeValue(Attributes.MOVEMENT_SPEED) * this.dragon.getSwimMultiplier() * this.speedModifier;
            Vec3 swimVec = new Vec3(dx / distance, dy / distance, dz / distance).scale(swimSpeed);

            this.dragon.setDeltaMovement(this.dragon.getDeltaMovement().lerp(swimVec, 0.1D));
        } else {
            this.dragon.setSpeed(0.0F);
        }
    }
}