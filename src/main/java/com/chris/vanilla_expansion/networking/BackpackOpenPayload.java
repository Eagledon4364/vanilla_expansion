package com.chris.vanilla_expansion.networking;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

public record BackpackOpenPayload() implements CustomPacketPayload {
    public static final Type<BackpackOpenPayload> TYPE =
            new Type<>(Identifier.fromNamespaceAndPath("vanilla_expansion", "open_backpack"));

    public static final StreamCodec<ByteBuf, BackpackOpenPayload> CODEC =
            StreamCodec.unit(new BackpackOpenPayload());

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}