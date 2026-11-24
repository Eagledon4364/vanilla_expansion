
package com.chris.vanilla_expansion_dragons.networking;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.NotNull;

public record DragonFlightInputPayload(
        float yaw,
        float pitch
) implements CustomPacketPayload {

    public static final Type<@NotNull DragonFlightInputPayload> TYPE =
            new Type<>(Identifier.fromNamespaceAndPath(
                    "vanilla_expansion_dragons",
                    "dragon_flight_input"
            ));

    public static final StreamCodec<
            @NotNull RegistryFriendlyByteBuf,
            @NotNull DragonFlightInputPayload
            > CODEC = StreamCodec.of(
            (buf, payload) -> {
                buf.writeFloat(payload.yaw());
                buf.writeFloat(payload.pitch());
            },
            buf -> new DragonFlightInputPayload(
                    buf.readFloat(),
                    buf.readFloat()
            )
    );

    @Override
    public @NotNull Type<? extends @NotNull CustomPacketPayload> type() {
        return TYPE;
    }
}
