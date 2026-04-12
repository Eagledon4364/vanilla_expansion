package com.chris.vanilla_expansion.networking;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.NotNull;

public record DragonFirePayload() implements CustomPacketPayload {
    public static final Type<@NotNull DragonFirePayload> TYPE =
            new Type<>(Identifier.fromNamespaceAndPath("vanilla_expansion", "dragon_fire"));

    public static final StreamCodec<@NotNull ByteBuf, @NotNull DragonFirePayload> CODEC =
            StreamCodec.unit(new DragonFirePayload());

    @Override
    public @NotNull Type<? extends @NotNull CustomPacketPayload> type() {
        return TYPE;
    }
}
