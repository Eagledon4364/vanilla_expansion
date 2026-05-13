package com.chris.vanilla_expansion.util;

import com.chris.vanilla_expansion.entity.server.DragonAnimal;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

public class DragonMoveControl extends MoveControl {
    private final DragonAnimal dragon;

    public DragonMoveControl(DragonAnimal dragon) {
        super(dragon);
        this.dragon = dragon;
    }

    @Override
    public void tick() {
        if (this.dragon.isVehicle() && this.dragon.getControllingPassenger() instanceof Player) {
            return;
        }
        if (dragon.isFlying()) {
            if (this.operation == MoveControl.Operation.MOVE_TO) {
                double dx = this.wantedX - dragon.getX();
                double dy = this.wantedY - dragon.getY();
                double dz = this.wantedZ - dragon.getZ();
                double distanceSq = dx * dx + dy * dy + dz * dz;

                if (distanceSq < 1.0D) {
                    this.operation = Operation.WAIT;
                    dragon.setDragonState(DragonAnimal.DragonState.HOVER);
                    return;
                }
                double distance = Math.sqrt(distanceSq);
                Vec3 directionVec = new Vec3(dx / distance, dy / distance, dz / distance);
                float targetYaw = (float) (Mth.atan2(dz, dx) * (180F / Math.PI)) - 90.0F;
                dragon.setYRot(this.rotlerp(dragon.getYRot(), targetYaw, dragon.getTurnSpeed()));
                dragon.yBodyRot = dragon.getYRot();

                float targetPitch = (float) (-(Mth.atan2(dy, Math.sqrt(dx * dx + dz * dz)) * (180F / Math.PI)));
                dragon.setXRot(this.rotlerp(dragon.getXRot(), targetPitch, dragon.getTurnSpeed()));

                float speedMultiplier = dragon.isInWater() ? (float) dragon.getSwimMultiplier() : 1.0F;
                float flightSpeedBase = 1.2F * speedMultiplier;

                float attrModifier = (float) (this.speedModifier * dragon.getAttributeValue(Attributes.FLYING_SPEED));
                float yawDiff = Math.abs(Mth.wrapDegrees(targetYaw - dragon.getYRot()));
                float turnDampening = Mth.clamp(1.0F - (yawDiff / 120.0F), 0.4F, 1.0F);

                float finalVelocity = flightSpeedBase * attrModifier * turnDampening;
                Vec3 travelVec = directionVec.scale(finalVelocity);
                dragon.setDeltaMovement(dragon.getDeltaMovement().lerp(travelVec, 0.15D));

                dragon.setDeltaMovement(dragon.getDeltaMovement().add(0, 0.08, 0));

            } else {
                dragon.setDeltaMovement(dragon.getDeltaMovement().scale(0.95));
                if (dragon.getDragonState() == DragonAnimal.DragonState.HOVER) {
                    dragon.setDeltaMovement(dragon.getDeltaMovement().add(0, 0.08, 0));
                }
            }
        } else {
            super.tick();
        }
    }
}