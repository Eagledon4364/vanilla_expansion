package com.chris.vanilla_expansion_dragons.entity.server.util;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.phys.Vec3;

public class DragonMoveControl extends MoveControl<DragonAnimal> {

    public DragonMoveControl(DragonAnimal dragon) {
        super(dragon);
    }

    @Override
    public void tick() {
        if (this.mob.isOrderedToSit() || this.mob.isSleeping()) {
            this.mob.setSpeed(0.0F);
            this.mob.setDeltaMovement(this.mob.getDeltaMovement().multiply(0.0D, 1.0D, 0.0D));
            this.mob.setDragonRoll(Mth.lerp(0.25F, this.mob.getDragonRoll(), 0.0F));
            return;
        }

        if (this.mob.isVehicle()) {
            return;
        }

        if (this.mob.canFly() && this.mob.isFlying()) {
            handleFlightMovement();
            return;
        }

        if (this.mob.canSwim() && this.mob.isInWater()) {
            handleWaterMovement();
            return;
        }

        this.mob.setDragonRoll(Mth.lerp(0.25F, this.mob.getDragonRoll(), 0.0F));
        super.tick();
    }

    private void handleFlightMovement() {
        if (this.operation == Operation.MOVE_TO) {
            this.operation = Operation.WAIT;

            double dx = this.wantedX - this.mob.getX();
            double dy = this.wantedY - this.mob.getY();
            double dz = this.wantedZ - this.mob.getZ();
            double distanceSq = dx * dx + dy * dy + dz * dz;

            if (distanceSq < 2.25D) {
                this.mob.setDragonState(DragonState.HOVER);
                this.mob.setDragonRoll(Mth.lerp(0.2F, this.mob.getDragonRoll(), 0.0F));
                return;
            }

            double distance = Math.sqrt(distanceSq);
            Vec3 directionVec = new Vec3(dx / distance, dy / distance, dz / distance);

            float oldYaw = this.mob.getYRot();
            float targetYaw = (float) (Mth.atan2(dz, dx) * (180F / Math.PI)) - 90.0F;
            float newYaw = this.rotlerp(oldYaw, targetYaw, this.mob.getTurnSpeed());
            float yawDelta = Mth.wrapDegrees(newYaw - oldYaw);

            this.mob.setYRot(newYaw);
            this.mob.yBodyRot = this.mob.getYRot();
            this.mob.yHeadRot = this.mob.getYRot();

            float targetPitch = (float) (-(Mth.atan2(dy, Math.sqrt(dx * dx + dz * dz)) * (180F / Math.PI)));
            float clampedPitch = Mth.clamp(targetPitch, -65.0F, 65.0F);
            this.mob.setXRot(this.rotlerp(this.mob.getXRot(), clampedPitch, this.mob.getTurnSpeed()));
            this.mob.setDragonPitch(this.mob.getXRot());

            float bankRoll = Mth.clamp(yawDelta * 4.0F, -45.0F, 45.0F);
            this.mob.setDragonRoll(Mth.lerp(0.18F, this.mob.getDragonRoll(), bankRoll));

            double baseFlySpeed = this.mob.getAttributeValue(Attributes.FLYING_SPEED);
            float yawDiff = Math.abs(Mth.wrapDegrees(targetYaw - this.mob.getYRot()));
            float turnDampening = Mth.clamp(1.0F - (yawDiff / 120.0F), 0.4F, 1.0F);

            double targetSpeed = baseFlySpeed * this.speedModifier * turnDampening;
            Vec3 travelVec = directionVec.scale(targetSpeed);

            this.mob.setDeltaMovement(this.mob.getDeltaMovement().lerp(travelVec, 0.2D));
            this.mob.setDragonState(DragonState.FLY_VELOCITY);
            this.mob.resetFallDistance();
        } else {
            // Hover dampening when no active target
            Vec3 currentVel = this.mob.getDeltaMovement();
            Vec3 forwardLook = this.mob.getLookAngle().scale(0.05D);
            this.mob.setDeltaMovement(new Vec3(
                    currentVel.x * 0.9D + forwardLook.x,
                    currentVel.y * 0.8D,
                    currentVel.z * 0.9D + forwardLook.z
            ));
            this.mob.setDragonRoll(Mth.lerp(0.2F, this.mob.getDragonRoll(), 0.0F));
            this.mob.resetFallDistance();
        }
    }

    private void handleWaterMovement() {
        if (this.operation == Operation.MOVE_TO) {
            double dx = this.wantedX - this.mob.getX();
            double dy = this.wantedY - this.mob.getY();
            double dz = this.wantedZ - this.mob.getZ();
            double distance = Math.sqrt(dx * dx + dy * dy + dz * dz);

            if (distance < 0.5D) {
                this.mob.setSpeed(0.0F);
                return;
            }

            float targetYaw = (float) (Mth.atan2(dz, dx) * (180F / Math.PI)) - 90.0F;
            this.mob.setYRot(this.rotlerp(this.mob.getYRot(), targetYaw, this.mob.getTurnSpeed()));
            this.mob.yBodyRot = this.mob.getYRot();

            double swimSpeed = this.mob.getAttributeValue(Attributes.MOVEMENT_SPEED) * this.mob.getSwimMultiplier() * this.speedModifier;
            Vec3 swimVec = new Vec3(dx / distance, dy / distance, dz / distance).scale(swimSpeed);

            this.mob.setDeltaMovement(this.mob.getDeltaMovement().lerp(swimVec, 0.1D));
        } else {
            this.mob.setSpeed(0.0F);
        }
    }
}