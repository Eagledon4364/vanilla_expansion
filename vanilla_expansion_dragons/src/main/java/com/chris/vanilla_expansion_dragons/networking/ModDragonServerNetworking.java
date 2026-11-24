
package com.chris.vanilla_expansion_dragons.networking;

import com.chris.vanilla_expansion_dragons.entity.server.util.DragonAnimal;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.level.ServerPlayer;

public class ModDragonServerNetworking {

    public static void register() {
        PayloadTypeRegistry.serverboundPlay().register(
                DragonFlightInputPayload.TYPE,
                DragonFlightInputPayload.CODEC
        );

        ServerPlayNetworking.registerGlobalReceiver(
                DragonFlightInputPayload.TYPE,
                (payload, context) -> {
                    context.server().execute(() -> {
                        ServerPlayer player = context.player();

                        if (!player.isAlive() || player.isRemoved()) {
                            return;
                        }

                        if (!Float.isFinite(payload.yaw())
                                || !Float.isFinite(payload.pitch())) {
                            return;
                        }

                        if (player.getVehicle() instanceof DragonAnimal dragon
                                && dragon.isFlying()
                                && dragon.getControllingPassenger() == player) {

                            dragon.addRiderMouseInput(
                                    payload.yaw(),
                                    payload.pitch()
                            );
                        }
                    });
                }
        );
    }
}
