package com.chris.vanilla_expansion.networking;


import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

public record MagnetTogglePayload() implements CustomPacketPayload {
    public static final Type<MagnetTogglePayload> TYPE =
            new Type<>(Identifier.fromNamespaceAndPath("vanilla_expansion", "magnet_toggle"));

    public static final StreamCodec<ByteBuf, MagnetTogglePayload> CODEC =
            StreamCodec.unit(new MagnetTogglePayload());

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}