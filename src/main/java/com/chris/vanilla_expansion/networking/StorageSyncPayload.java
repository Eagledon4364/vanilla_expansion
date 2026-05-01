package com.chris.vanilla_expansion.networking;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public record StorageSyncPayload(List<ItemStack> stacks, List<Integer> counts)
        implements CustomPacketPayload {

    public static final Type<StorageSyncPayload> TYPE =
            new Type<>(Identifier.fromNamespaceAndPath("vanilla_expansion", "storage_sync"));

    public static final StreamCodec<RegistryFriendlyByteBuf, StorageSyncPayload> CODEC =
            StreamCodec.of(StorageSyncPayload::encode, StorageSyncPayload::decode);

    private static StorageSyncPayload decode(RegistryFriendlyByteBuf buf) {
        int size = buf.readVarInt();

        List<ItemStack> stacks = new ArrayList<>();
        List<Integer> counts = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            stacks.add(ItemStack.STREAM_CODEC.decode(buf));
            counts.add(buf.readVarInt());
        }

        return new StorageSyncPayload(stacks, counts);
    }

    private static void encode(RegistryFriendlyByteBuf buf, StorageSyncPayload payload) {
        buf.writeVarInt(payload.stacks().size());

        for (int i = 0; i < payload.stacks().size(); i++) {
            ItemStack.STREAM_CODEC.encode(buf, payload.stacks().get(i));
            buf.writeVarInt(payload.counts().get(i));
        }
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}