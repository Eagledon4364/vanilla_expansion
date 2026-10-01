package com.chris.vanilla_expansion.networking;

import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.Identifier;

@Environment(EnvType.CLIENT)
public class ModKeybindings {

    // Category registration returning KeyMapping.Category
    public static final KeyMapping.Category MOD_CATEGORY = KeyMapping.Category.register(
            Identifier.fromNamespaceAndPath("vanilla_expansion", "main")
    );

    public static KeyMapping magnetToggleKey;
    public static KeyMapping openBackpackKey;
    public static KeyMapping veinMineKey;

    private static boolean wasVeinPressed = false;

    public static void register() {
        magnetToggleKey = KeyMappingHelper.registerKeyMapping(new KeyMapping(
                "key.vanilla_expansion.toggle_magnet",
                InputConstants.Type.KEYBOARD,
                InputConstants.KEY_M,
                MOD_CATEGORY
        ));

        openBackpackKey = KeyMappingHelper.registerKeyMapping(new KeyMapping(
                "key.vanilla_expansion.open_backpack",
                InputConstants.Type.KEYBOARD,
                InputConstants.KEY_B,
                MOD_CATEGORY
        ));

        veinMineKey = KeyMappingHelper.registerKeyMapping(new KeyMapping(
                "key.vanilla_expansion.vein_mine",
                InputConstants.Type.KEYBOARD,
                InputConstants.KEY_C,
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
                boolean isCtrlDown = Minecraft.getInstance().hasControlDown();

                int blockLimit = isCtrlDown ? 32 : 16;
                ClientPlayNetworking.send(new VeinMinePayload(blockLimit));
                wasVeinPressed = true;
            } else if (!isVeinPressed && wasVeinPressed) {
                ClientPlayNetworking.send(new VeinMinePayload(0));
                wasVeinPressed = false;
            }
        });
    }
}