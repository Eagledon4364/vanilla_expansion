package com.chris.vanilla_expansion.screen.storage;

import com.chris.vanilla_expansion.block.storage.StorageControllerBlockEntity;
import com.chris.vanilla_expansion.networking.StorageSyncPayload;
import com.chris.vanilla_expansion.screen.ModMenus;
import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class StorageAccessMenu extends AbstractContainerMenu {

    private final StorageControllerBlockEntity controller;
    private final Container networkContainer;

    // CLIENT constructor
    public StorageAccessMenu(int id, Inventory playerInv) {
        super(ModMenus.STORAGE_ACCESS_MENU, id);

        this.controller = null;
        this.networkContainer = new SimpleContainer(54);

        buildSlots(playerInv);
    }

    // SERVER constructor
    public StorageAccessMenu(int id, Inventory playerInv, StorageControllerBlockEntity controller) {
        super(ModMenus.STORAGE_ACCESS_MENU, id);

        this.controller = controller;
        this.networkContainer = new SimpleContainer(54);

        buildSlots(playerInv);

        if (!playerInv.player.level().isClientSide()) {
            sendToClient((ServerPlayer) playerInv.player);
        }

        refreshNetworkSlots();
    }

    // =========================
    // SLOT BUILDING
    // =========================
    private void buildSlots(Inventory playerInv) {

        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 9; col++) {
                this.addSlot(new Slot(networkContainer, col + row * 9, 8 + col * 18, 18 + row * 18) {

                    @Override
                    public boolean mayPlace(ItemStack stack) {
                        return false; // cannot insert directly
                    }

                    @Override
                    public boolean mayPickup(Player player) {
                        return true; // allow extraction via click
                    }
                });
            }
        }

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                this.addSlot(new Slot(playerInv,
                        j + i * 9 + 9,
                        8 + j * 18,
                        140 + i * 18));
            }
        }

        for (int i = 0; i < 9; i++) {
            this.addSlot(new Slot(playerInv,
                    i,
                    8 + i * 18,
                    198));
        }
    }

    // =========================
    // NETWORK SYNC
    // =========================
    private void refreshNetworkSlots() {
        if (controller == null) return;

        networkContainer.clearContent();

        Map<ItemStack, Integer> aggregated = controller.getNetwork().getAggregated();

        int i = 0;
        for (Map.Entry<ItemStack, Integer> entry : aggregated.entrySet()) {
            if (i >= 54) break;

            ItemStack stack = entry.getKey().copyWithCount(entry.getValue());
            networkContainer.setItem(i++, stack);
        }
    }

    // =========================
    // SHIFT CLICK (FIXED SAFETY)
    // =========================
    @Override
    public ItemStack quickMoveStack(Player player, int index) {

        // 🔒 CLIENT GUARD (VERY IMPORTANT)
        if (player.level().isClientSide()) {
            return ItemStack.EMPTY;
        }

        if (controller == null) return ItemStack.EMPTY;

        Slot slot = slots.get(index);
        if (slot == null || !slot.hasItem()) return ItemStack.EMPTY;

        ItemStack stack = slot.getItem();

        // NETWORK → PLAYER
        if (index < 54) {

            ItemStack extracted = controller.extractItem(stack.copy(), stack.getCount());

            if (!moveItemStackTo(extracted, 54, slots.size(), true)) {
                return ItemStack.EMPTY;
            }

            return ItemStack.EMPTY;
        }

        // PLAYER → NETWORK
        ItemStack remaining = controller.insertItem(stack);
        slot.set(remaining);

        return ItemStack.EMPTY;
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }

    public void sendToClient(ServerPlayer player) {
        if (controller == null) return;

        Map<ItemStack, Integer> aggregated = controller.getNetwork().getAggregated();

        List<ItemStack> stacks = new ArrayList<>();
        List<Integer> counts = new ArrayList<>();

        for (Map.Entry<ItemStack, Integer> entry : aggregated.entrySet()) {
            stacks.add(entry.getKey().copy());
            counts.add(entry.getValue());
        }

        ServerPlayNetworking.send(player, new StorageSyncPayload(stacks, counts));
    }

    public void receiveSync(StorageSyncPayload payload) {
        networkContainer.clearContent();

        for (int i = 0; i < payload.stacks().size() && i < 54; i++) {
            ItemStack stack = payload.stacks().get(i).copy();
            stack.setCount(payload.counts().get(i));
            networkContainer.setItem(i, stack);
        }
    }

    public Container getInventory() {
        return networkContainer;
    }
}