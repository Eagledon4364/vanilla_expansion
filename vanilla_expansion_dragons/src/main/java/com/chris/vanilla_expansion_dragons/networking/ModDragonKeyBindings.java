
package com.chris.vanilla_expansion_dragons.networking;

import com.chris.vanilla_expansion_dragons.entity.server.util.DragonAnimal;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.Minecraft;

@Environment(EnvType.CLIENT)
public class ModDragonKeyBindings {

    private static double accumulatedYaw;
    private static double accumulatedPitch;

    public static void register() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null
                    || !(client.player.getVehicle() instanceof DragonAnimal dragon)
                    || !dragon.isFlying()) {

                accumulatedYaw = 0.0D;
                accumulatedPitch = 0.0D;
                return;
            }

            if (Math.abs(accumulatedYaw) > 0.0001D
                    || Math.abs(accumulatedPitch) > 0.0001D) {

                ClientPlayNetworking.send(
                        new DragonFlightInputPayload(
                                (float) accumulatedYaw,
                                (float) accumulatedPitch
                        )
                );

                accumulatedYaw = 0.0D;
                accumulatedPitch = 0.0D;
            }
        });
    }

    public static void captureMouseInput(double yaw, double pitch) {
        Minecraft client = Minecraft.getInstance();

        if (client.player == null
                || !(client.player.getVehicle() instanceof DragonAnimal dragon)
                || !dragon.isFlying()) {
            return;
        }

        accumulatedYaw += yaw;
        accumulatedPitch += pitch;
    }
}
