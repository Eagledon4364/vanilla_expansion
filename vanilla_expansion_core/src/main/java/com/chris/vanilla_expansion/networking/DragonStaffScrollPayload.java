package com.chris.vanilla_expansion.networking;

import com.chris.vanilla_expansion.item.custom.DragonStaff;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

public record DragonStaffScrollPayload(int scrollDelta) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<DragonStaffScrollPayload> TYPE =
            new CustomPacketPayload.Type<>(Identifier.fromNamespaceAndPath("vanilla_expansion", "dragon_staff_scroll"));

    public static final StreamCodec<FriendlyByteBuf, DragonStaffScrollPayload> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.VAR_INT, DragonStaffScrollPayload::scrollDelta,
            DragonStaffScrollPayload::new
    );

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(DragonStaffScrollPayload payload, ServerPlayNetworking.Context context) {
        ServerPlayer player = context.player();
        context.server().execute(() -> {
            if (player.getMainHandItem().getItem() instanceof DragonStaff staff) {
                staff.cycleTargetMode(player, player.getMainHandItem(), payload.scrollDelta());
            } else if (player.getOffhandItem().getItem() instanceof DragonStaff staff) {
                staff.cycleTargetMode(player, player.getOffhandItem(), payload.scrollDelta());
            }
        });
    }
}