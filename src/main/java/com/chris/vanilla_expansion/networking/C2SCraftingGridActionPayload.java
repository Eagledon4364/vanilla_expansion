package com.chris.vanilla_expansion.networking;

import com.chris.vanilla_expansion.screen.storage.CraftingInterfaceMenu;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;

public record C2SCraftingGridActionPayload(ActionType action) implements CustomPacketPayload {

    public enum ActionType {
        CLEAR_TO_STORAGE,
        CLEAR_TO_PLAYER,
        ROTATE,
        BALANCE;

        public static ActionType fromOrdinal(int ordinal) {
            ActionType[] values = values();
            if (ordinal < 0 || ordinal >= values.length) return CLEAR_TO_STORAGE;
            return values[ordinal];
        }
    }

    public static final Type<C2SCraftingGridActionPayload> TYPE =
            new Type<>(Identifier.fromNamespaceAndPath("vanilla_expansion", "crafting_grid_action"));

    public static final StreamCodec<RegistryFriendlyByteBuf, C2SCraftingGridActionPayload> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.VAR_INT.map(ActionType::fromOrdinal, ActionType::ordinal),
            C2SCraftingGridActionPayload::action,
            C2SCraftingGridActionPayload::new
    );

    @Override
    public Type<C2SCraftingGridActionPayload> type() {
        return TYPE;
    }

    public static void handle(C2SCraftingGridActionPayload payload, ServerPlayer player) {
        if (player.containerMenu instanceof CraftingInterfaceMenu menu) {
            switch (payload.action()) {
                case CLEAR_TO_STORAGE -> menu.clearGridToStorage(player);
                case CLEAR_TO_PLAYER -> menu.clearGridToPlayer(player);
                case ROTATE -> menu.rotateGrid();
                case BALANCE -> menu.balanceGrid();
            }
        }
    }
}