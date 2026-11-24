
package com.chris.vanilla_expansion_dragons.mixin;

import com.chris.vanilla_expansion_dragons.entity.server.util.DragonAnimal;
import com.chris.vanilla_expansion_dragons.networking.ModDragonKeyBindings;
import net.minecraft.client.MouseHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(MouseHandler.class)
public abstract class MouseHandlerMixin {

    @Redirect(
            method = "turnPlayer",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/player/LocalPlayer;turn(DD)V"
            )
    )
    private void vanillaExpansion$allowFullCameraRotation(
            LocalPlayer player,
            double yaw,
            double pitch
    ) {
        player.turn(yaw, pitch);
    }
}
