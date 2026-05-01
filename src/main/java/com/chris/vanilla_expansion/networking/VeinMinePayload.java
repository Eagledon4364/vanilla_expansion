package com.chris.vanilla_expansion.networking;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.NotNull;

public record VeinMinePayload(boolean active) implements CustomPacketPayload {

    public static final Type<@NotNull VeinMinePayload> TYPE =
            new Type<>(Identifier.fromNamespaceAndPath("vanilla_expansion", "vein_mine"));

    public static final StreamCodec<@NotNull RegistryFriendlyByteBuf, @NotNull VeinMinePayload> CODEC =
            StreamCodec.of(
                    (buf, payload) -> buf.writeBoolean(payload.active()),
                    buf -> new VeinMinePayload(buf.readBoolean())
            );

    @Override
    public @NotNull Type<? extends @NotNull CustomPacketPayload> type() {
        return TYPE;
    }
}