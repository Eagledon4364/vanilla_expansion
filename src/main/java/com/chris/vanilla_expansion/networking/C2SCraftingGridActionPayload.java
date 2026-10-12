package com.chris.vanilla_expansion.networking;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

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
}