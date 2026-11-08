package com.chris.vanilla_expansion.networking;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.NotNull;

public record BackpackOpenPayload() implements CustomPacketPayload {
    public static final Type<@NotNull BackpackOpenPayload> TYPE =
            new Type<>(Identifier.fromNamespaceAndPath("vanilla_expansion", "open_backpack"));

    public static final StreamCodec<@NotNull ByteBuf, @NotNull BackpackOpenPayload> CODEC =
            StreamCodec.unit(new BackpackOpenPayload());

    @Override
    public @NotNull Type<? extends @NotNull CustomPacketPayload> type() {
        return TYPE;
    }
}