package com.chris.vanilla_expansion.networking;

import com.chris.vanilla_expansion.VanillaExpansion;
import com.chris.vanilla_expansion.screen.storage.CraftingInterfaceMenu;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;

public record C2SCraftingGridActionPayload(int actionType) implements CustomPacketPayload {
    public static final Type<C2SCraftingGridActionPayload> TYPE =
            new Type<>(Identifier.fromNamespaceAndPath(VanillaExpansion.MOD_ID, "crafting_grid_action"));

    public static final StreamCodec<RegistryFriendlyByteBuf, C2SCraftingGridActionPayload> STREAM_CODEC =
            StreamCodec.composite(
                    ByteBufCodecs.VAR_INT, C2SCraftingGridActionPayload::actionType,
                    C2SCraftingGridActionPayload::new
            );

    public static final int ACTION_CLEAR_TO_GRID = 0;   // Small 'x' button
    public static final int ACTION_CLEAR_TO_PLAYER = 1; // Down arrow
    public static final int ACTION_ROTATE = 2;          // Circular arrow
    public static final int ACTION_BALANCE = 3;         // 3x3 grid button

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(C2SCraftingGridActionPayload payload, ServerPlayer player) {
        if (player.containerMenu instanceof CraftingInterfaceMenu menu) {
            switch (payload.actionType()) {
                case ACTION_CLEAR_TO_GRID -> menu.clearGridToStorage(player);
                case ACTION_CLEAR_TO_PLAYER -> menu.clearGridToPlayer(player);
                case ACTION_ROTATE -> menu.rotateGrid();
                case ACTION_BALANCE -> menu.balanceGrid();
            }
        }
    }
}