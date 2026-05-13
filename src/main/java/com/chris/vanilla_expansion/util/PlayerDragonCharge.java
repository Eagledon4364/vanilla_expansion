package com.chris.vanilla_expansion.util;

import net.minecraft.world.entity.PlayerRideable;

public interface PlayerDragonCharge extends PlayerRideable {
    void onDragonCharge(int chargeAmount);


    void handleStartCharge(int chargeScale);

    void handleStopCharge();

}
