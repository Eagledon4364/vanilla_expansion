package com.chris.vanilla_expansion.networking;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

public record C2SSyncStorageSearchPayload(String query, int scrollRow, int sortOrdinal) implements CustomPacketPayload {

    public static final Type<C2SSyncStorageSearchPayload> TYPE =
            new Type<>(Identifier.fromNamespaceAndPath("vanilla_expansion", "sync_storage_search"));

    public static final StreamCodec<RegistryFriendlyByteBuf, C2SSyncStorageSearchPayload> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8, C2SSyncStorageSearchPayload::query,
            ByteBufCodecs.VAR_INT, C2SSyncStorageSearchPayload::scrollRow,
            ByteBufCodecs.VAR_INT, C2SSyncStorageSearchPayload::sortOrdinal,
            C2SSyncStorageSearchPayload::new
    );

    @Override
    public Type<C2SSyncStorageSearchPayload> type() {
        return TYPE;
    }
}