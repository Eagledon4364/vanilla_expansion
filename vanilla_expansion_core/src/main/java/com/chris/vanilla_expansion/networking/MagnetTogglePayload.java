package com.chris.vanilla_expansion.networking;


import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.NotNull;

public record MagnetTogglePayload() implements CustomPacketPayload {
    public static final Type<@NotNull MagnetTogglePayload> TYPE =
            new Type<>(Identifier.fromNamespaceAndPath("vanilla_expansion", "magnet_toggle"));

    public static final StreamCodec<@NotNull ByteBuf, @NotNull MagnetTogglePayload> CODEC =
            StreamCodec.unit(new MagnetTogglePayload());

    @Override
    public @NotNull Type<? extends @NotNull CustomPacketPayload> type() {
        return TYPE;
    }
}