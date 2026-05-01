package com.chris.vanilla_expansion.entity.client.animation;

import net.minecraft.world.entity.PlayerRideable;

public interface PlayerDragonCharge extends PlayerRideable {
    void onDragonCharge(int chargeAmount);


    void handleStartCharge(int chargeScale);

    void handleStopCharge();

}
