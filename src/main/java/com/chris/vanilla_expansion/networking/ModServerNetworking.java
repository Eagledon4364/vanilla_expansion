package com.chris.vanilla_expansion.networking;

import com.chris.vanilla_expansion.VanillaExpansion;
import com.chris.vanilla_expansion.block.inventory.ItemStackUpgradeInventory;
import com.chris.vanilla_expansion.component.ModDataComponentTypes;
import com.chris.vanilla_expansion.item.ModItems;
import com.chris.vanilla_expansion.item.custom.BackpackItem;
import com.chris.vanilla_expansion.block.inventory.ItemStackInventory;
import com.chris.vanilla_expansion.networking.C2SSyncStorageSearchPayload;
import com.chris.vanilla_expansion.screen.backpack.BackpackMenu;
import com.chris.vanilla_expansion.screen.storage.CraftingInterfaceMenu;
import com.chris.vanilla_expansion.screen.storage.StorageInterfaceMenu;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class ModServerNetworking {
    public static final Identifier STORAGE_SYNC_ID =
            Identifier.fromNamespaceAndPath(VanillaExpansion.MOD_ID, "storage_sync");

    // Maps Player UUID -> max blocks allowed to mine (0 = inactive)
    private static final Map<UUID, Integer> veinMiningPlayers = new HashMap<>();

    public static void register() {
        // Register Payload Types (Serverbound)
        PayloadTypeRegistry.serverboundPlay().register(BackpackOpenPayload.TYPE, BackpackOpenPayload.CODEC);
        PayloadTypeRegistry.serverboundPlay().register(MagnetTogglePayload.TYPE, MagnetTogglePayload.CODEC);
        PayloadTypeRegistry.serverboundPlay().register(VeinMinePayload.TYPE, VeinMinePayload.CODEC);
        PayloadTypeRegistry.serverboundPlay().register(C2SSyncStorageSearchPayload.TYPE, C2SSyncStorageSearchPayload.STREAM_CODEC);

        // Register Payload Types (Clientbound)
        PayloadTypeRegistry.clientboundPlay().register(StorageSyncPayload.TYPE, StorageSyncPayload.CODEC);

        // Register Receiver for Search Payload
        // Register Receiver for Search & Sort Payload
        ServerPlayNetworking.registerGlobalReceiver(C2SSyncStorageSearchPayload.TYPE, (payload, context) -> {
            context.server().execute(() -> {
                ServerPlayer player = context.player();
                if (player.containerMenu instanceof StorageInterfaceMenu menu) {
                    // 1. Update sort mode on server menu and block entity
                    StorageInterfaceMenu.SortMode newMode = StorageInterfaceMenu.SortMode.fromOrdinal(payload.sortOrdinal());
                    menu.setSortMode(newMode);

                    // 2. Refresh filtering/scrolling
                    menu.applyFilterAndScroll(payload.query(), payload.scrollRow());
                } else if (player.containerMenu instanceof CraftingInterfaceMenu menu) {
                    // 1. Update sort mode on server menu and block entity
                    CraftingInterfaceMenu.SortMode newMode = CraftingInterfaceMenu.SortMode.fromOrdinal(payload.sortOrdinal());
                    menu.setSortMode(newMode);

                    // 2. Refresh filtering/scrolling
                    menu.applyFilterAndScroll(payload.query(), payload.scrollRow());
                }
            });
        });
        PayloadTypeRegistry.serverboundPlay().register(
                C2SCraftingGridActionPayload.TYPE,
                C2SCraftingGridActionPayload.STREAM_CODEC
        );

        ServerPlayNetworking.registerGlobalReceiver(C2SCraftingGridActionPayload.TYPE, (payload, context) -> {
            context.server().execute(() -> {
                C2SCraftingGridActionPayload.handle(payload, context.player());
            });
        });

        ServerPlayNetworking.registerGlobalReceiver(BackpackOpenPayload.TYPE, (payload, context) -> {
            context.server().execute(() -> {
                ServerPlayer player = context.player();
                if (!player.isAlive() || player.isRemoved()) return;
                ItemStack backpackStack = player.getInventory().getItem(42);
                if (!(backpackStack.getItem() instanceof BackpackItem)) return;
                if (player.containerMenu instanceof BackpackMenu) {
                    player.closeContainer();
                    return;
                }
                if (backpackStack.getItem() instanceof BackpackItem) {
                    player.openMenu(new MenuProvider() {
                        @Override
                        public @NotNull Component getDisplayName() {
                            return Component.translatable("container.vanilla_expansion.backpack");
                        }
                        @Override
                        public AbstractContainerMenu createMenu(int id, @NotNull Inventory inv, Player p) {
                            ItemStackInventory mainInv = new ItemStackInventory(backpackStack, 54);
                            ItemStackUpgradeInventory upgradeInv = new ItemStackUpgradeInventory(backpackStack, 6);
                            return new BackpackMenu(id, inv, mainInv, upgradeInv);
                        }
                    });
                }
            });
        });

        ServerPlayNetworking.registerGlobalReceiver(VeinMinePayload.TYPE, (payload, context) -> {
            context.server().execute(() -> {
                UUID id = context.player().getUUID();

                if (payload.maxBlocks() > 0) {
                    veinMiningPlayers.put(id, payload.maxBlocks());
                } else {
                    veinMiningPlayers.remove(id);
                }
            });
        });

        PlayerBlockBreakEvents.BEFORE.register((world, player, pos, state, blockEntity) -> {
            if (world.isClientSide()) return true;
            if (!(player instanceof ServerPlayer serverPlayer)) return true;

            Integer maxLimit = veinMiningPlayers.get(player.getUUID());
            if (maxLimit == null || maxLimit <= 0) return true;

            veinMine((ServerLevel) world, pos, state.getBlock(), serverPlayer, maxLimit);

            return true;
        });

        ServerPlayNetworking.registerGlobalReceiver(MagnetTogglePayload.TYPE, (payload, context) -> {
            context.server().execute(() -> {
                ItemStack stack = context.player().getInventory().getItem(41);
                if (stack.is(ModItems.MAGNET)) {
                    boolean active = stack.getOrDefault(ModDataComponentTypes.IS_ACTIVE, false);
                    stack.set(ModDataComponentTypes.IS_ACTIVE, !active);
                    context.player().sendOverlayMessage(
                            Component.literal("Magnet: " + (active ? "§cOFF" : "§aON"))
                    );
                }
            });
        });
    }

    private static void veinMine(ServerLevel level,
                                 BlockPos origin,
                                 Block targetBlock,
                                 ServerPlayer player,
                                 int maxBlocks) {

        Set<BlockPos> visited = new HashSet<>();
        Queue<BlockPos> queue = new ArrayDeque<>();
        queue.add(origin);
        int mined = 0;

        while (!queue.isEmpty() && mined < maxBlocks) {
            BlockPos pos = queue.poll();
            if (visited.contains(pos)) continue;
            visited.add(pos);
            BlockState state = level.getBlockState(pos);
            if (state.getBlock() != targetBlock) continue;

            BlockEntity be = level.getBlockEntity(pos);
            ItemStack tool = player.getMainHandItem();

            if (!tool.isCorrectToolForDrops(state)) continue;

            tool.mineBlock(level, state, pos, player);

            Block.dropResources(state, level, pos, be, player, tool);

            level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);

            mined++;

            // 3x3x3 neighborhood search (orthogonal + diagonal)
            for (int dx = -1; dx <= 1; dx++) {
                for (int dy = -1; dy <= 1; dy++) {
                    for (int dz = -1; dz <= 1; dz++) {
                        if (dx == 0 && dy == 0 && dz == 0) continue;

                        BlockPos next = pos.offset(dx, dy, dz);

                        if (!visited.contains(next)) {
                            queue.add(next);
                        }
                    }
                }
            }
        }
    }
}