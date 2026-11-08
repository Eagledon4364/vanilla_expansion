package com.chris.vanilla_expansion_storage.networking;

import com.chris.vanilla_expansion.VanillaExpansion;
import com.chris.vanilla_expansion_storage.VanillaExpansionStorage;
import com.chris.vanilla_expansion_storage.menu.storage.CraftingInterfaceMenu;
import com.chris.vanilla_expansion_storage.menu.storage.StorageInterfaceMenu;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;

public class ModStorageServerNetworking {
    public static final Identifier STORAGE_SYNC_ID =
            Identifier.fromNamespaceAndPath(VanillaExpansion.MOD_ID, "storage_sync");

    public static void register() {
        PayloadTypeRegistry.serverboundPlay().register(C2SSyncStorageSearchPayload.TYPE, C2SSyncStorageSearchPayload.STREAM_CODEC);
        PayloadTypeRegistry.serverboundPlay().register(C2SCraftingGridActionPayload.TYPE, C2SCraftingGridActionPayload.STREAM_CODEC);
        PayloadTypeRegistry.serverboundPlay().register(C2SJeiRecipeTransferPayload.TYPE, C2SJeiRecipeTransferPayload.CODEC);

        PayloadTypeRegistry.clientboundPlay().register(StorageSyncPayload.TYPE, StorageSyncPayload.CODEC);


        // Search and Sort Sync Handler
        ServerPlayNetworking.registerGlobalReceiver(C2SSyncStorageSearchPayload.TYPE, (payload, context) -> {
            context.server().execute(() -> {
                ServerPlayer player = context.player();
                if (player.containerMenu instanceof StorageInterfaceMenu menu) {
                    StorageInterfaceMenu.SortMode newMode = StorageInterfaceMenu.SortMode.fromOrdinal(payload.sortOrdinal());
                    menu.setSortMode(newMode);
                    menu.applyFilterAndScroll(payload.query(), payload.scrollRow());
                } else if (player.containerMenu instanceof CraftingInterfaceMenu menu) {
                    CraftingInterfaceMenu.SortMode newMode = CraftingInterfaceMenu.SortMode.fromOrdinal(payload.sortOrdinal());
                    menu.setSortMode(newMode);
                    menu.applyFilterAndScroll(payload.query(), payload.scrollRow());
                } else {
                }
            });
        });

        // Crafting Grid Action Handler
        ServerPlayNetworking.registerGlobalReceiver(C2SCraftingGridActionPayload.TYPE, (payload, context) -> {
            context.server().execute(() -> {
                ServerPlayer player = context.player();
                if (player.containerMenu instanceof CraftingInterfaceMenu menu) {
                    switch (payload.action()) {
                        case CLEAR_TO_STORAGE -> menu.clearGridToStorage(player);
                        case CLEAR_TO_PLAYER -> menu.clearGridToPlayer(player);
                        case ROTATE -> menu.rotateGrid();
                        case BALANCE -> menu.balanceGrid();
                    }
                } else {
                }
            });
        });

        // JEI Recipe Transfer Handler
        ServerPlayNetworking.registerGlobalReceiver(C2SJeiRecipeTransferPayload.TYPE, (payload, context) -> {
            context.server().execute(() -> {
                ServerPlayer player = context.player();
                if (player.containerMenu instanceof CraftingInterfaceMenu menu) {
                    menu.handleJeiRecipeTransfer(player, payload.grid(), payload.maxTransfer());
                }
            });
        });

    }
}
