package com.chris.vanilla_expansion.event;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;

public class ClientEvents {

    public static boolean handleMouseScroll(double verticalScroll) {
        Minecraft client = Minecraft.getInstance();
        Player player = client.player;
        return false;
    }
}