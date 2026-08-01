package com.chris.vanilla_expansion.networking;

import com.chris.vanilla_expansion.VanillaExpansion;
import com.chris.vanilla_expansion.screen.storage.CraftingInterfaceMenu;
import com.chris.vanilla_expansion.screen.storage.StorageInterfaceMenu;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;

public record C2SSyncStorageSearchPayload(String query, int scrollRow, int sortOrdinal) implements CustomPacketPayload {
    public static final Type<C2SSyncStorageSearchPayload> TYPE = new Type<>(Identifier.fromNamespaceAndPath(VanillaExpansion.MOD_ID, "sync_storage_search"));

    public static final StreamCodec<RegistryFriendlyByteBuf, C2SSyncStorageSearchPayload> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8, C2SSyncStorageSearchPayload::query,
            ByteBufCodecs.VAR_INT, C2SSyncStorageSearchPayload::scrollRow,
            ByteBufCodecs.VAR_INT, C2SSyncStorageSearchPayload::sortOrdinal,
            C2SSyncStorageSearchPayload::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(C2SSyncStorageSearchPayload payload, ServerPlayer player) {
        if (player.containerMenu instanceof StorageInterfaceMenu menu) {
            StorageInterfaceMenu.SortMode newMode = StorageInterfaceMenu.SortMode.fromOrdinal(payload.sortOrdinal());
            menu.setSortMode(newMode);
            menu.applyFilterAndScroll(payload.query(), payload.scrollRow());
        } else if (player.containerMenu instanceof CraftingInterfaceMenu menu) {
            CraftingInterfaceMenu.SortMode newMode = CraftingInterfaceMenu.SortMode.fromOrdinal(payload.sortOrdinal());
            menu.setSortMode(newMode);
            menu.applyFilterAndScroll(payload.query(), payload.scrollRow());
        }
    }
}