package com.chris.vanilla_expansion.event;

import com.chris.vanilla_expansion.item.custom.DragonStaff;
import com.chris.vanilla_expansion.networking.DragonStaffScrollPayload;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;

public class ClientEvents {

    public static boolean handleMouseScroll(double verticalScroll) {
        Minecraft client = Minecraft.getInstance();
        Player player = client.player;
        if (player != null && player.isSecondaryUseActive()) {
            boolean holdingStaff = player.getMainHandItem().getItem() instanceof DragonStaff
                    || player.getOffhandItem().getItem() instanceof DragonStaff;

            if (holdingStaff) {
                int direction = verticalScroll > 0 ? 1 : -1;
                ClientPlayNetworking.send(new DragonStaffScrollPayload(direction));
                return true; // Cancels hotbar slot changing
            }
        }
        return false;
    }
}