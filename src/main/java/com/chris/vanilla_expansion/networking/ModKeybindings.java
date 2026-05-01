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
    public static KeyMapping veinMineKey;

    private static boolean wasVeinPressed = false;
    private static boolean wasDragonFirePressed = false;

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
        veinMineKey = KeyMappingHelper.registerKeyMapping(new KeyMapping(
                "key.vanilla_expansion.vein_mine",
                InputConstants.Type.KEYSYM,
                GLFW.GLFW_KEY_C,
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


            boolean isVeinPressed = veinMineKey.isDown();

            if (isVeinPressed && !wasVeinPressed) {
                ClientPlayNetworking.send(new VeinMinePayload(true));
                wasVeinPressed = true;
            } else if (!isVeinPressed && wasVeinPressed) {
                ClientPlayNetworking.send(new VeinMinePayload(false));
                wasVeinPressed = false;
            }

            boolean isPressed = dragonFire.isDown();
            if (isPressed && !wasDragonFirePressed) {
                ClientPlayNetworking.send(new DragonFirePayload(true));
                wasDragonFirePressed = true;
            } else if (!isPressed && wasDragonFirePressed) {
                ClientPlayNetworking.send(new DragonFirePayload(false));
                wasDragonFirePressed = false;
            }
        });
    }
}