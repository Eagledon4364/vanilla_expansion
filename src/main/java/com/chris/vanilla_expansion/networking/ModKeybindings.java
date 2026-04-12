package com.chris.vanilla_expansion.networking;

import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.KeyMapping;
import net.minecraft.resources.Identifier;
import org.lwjgl.glfw.GLFW;

public class ModKeybindings {
    public static final KeyMapping.Category MOD_CATEGORY = KeyMapping.Category.register(
            Identifier.fromNamespaceAndPath("vanilla_expansion", "main")
    );

    public static KeyMapping magnetToggleKey;
    public static KeyMapping openBackpackKey;
    public static KeyMapping dragonFire;

    public static void register() {
        magnetToggleKey = KeyMappingHelper.registerKeyMapping(new KeyMapping(
                "key.vanilla_expansion.toggle_magnet",
                InputConstants.Type.KEYSYM,
                GLFW.GLFW_KEY_M,
                MOD_CATEGORY
        ));

        openBackpackKey = KeyMappingHelper.registerKeyMapping(new KeyMapping(
                "key.vanilla_expansion.open_backpack",
                InputConstants.Type.KEYSYM,
                GLFW.GLFW_KEY_B,
                MOD_CATEGORY
        ));
        dragonFire = KeyMappingHelper.registerKeyMapping(new KeyMapping(
           "key.vanilla_expansion.dragon_fire",
           InputConstants.Type.KEYSYM,
           GLFW.GLFW_KEY_R,
           MOD_CATEGORY
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null) return;

            while (magnetToggleKey.consumeClick()) {
                ClientPlayNetworking.send(new MagnetTogglePayload());
            }

            while (openBackpackKey.consumeClick()) {
                ClientPlayNetworking.send(new BackpackOpenPayload());

            }
            while (dragonFire.consumeClick()) {
                ClientPlayNetworking.send(new DragonFirePayload());
            }
        });
    }
}