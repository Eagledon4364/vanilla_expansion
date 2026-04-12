package com.chris.vanilla_expansion.screen.storage;

import com.chris.vanilla_expansion.screen.ModMenus;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class StorageCrateMenu extends AbstractContainerMenu {
    private final Container container;

    public StorageCrateMenu(int syncId, Inventory playerInventory) {
        this(syncId, playerInventory, new SimpleContainer(32));
    }

    public StorageCrateMenu(int syncId, Inventory playerInventory, Container container) {
        super(ModMenus.STORAGE_CRATE_MENU, syncId);
        this.container = container;
        container.startOpen(playerInventory.player);
        setupSlots(playerInventory);
    }

    // NEW: Resolves "Cannot resolve method 'getContainer'" in StorageCrateScreen
    public Container getContainer() {
        return this.container;
    }

    private void setupSlots(Inventory playerInventory) {
        this.addSlot(new Slot(this.container, 0, 80, 36));
        // ... (standard player inventory/hotbar slots) ...
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
    }

    @Override
    public @NotNull ItemStack quickMoveStack(@NotNull Player player, int index) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);

        if (slot != null && slot.hasItem()) {
            ItemStack originalStack = slot.getItem();
            newStack = originalStack.copy();

            if (index < 1) { // From Crate
                if (!this.moveItemStackTo(originalStack, 1, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else { // To Crate
                ItemStack remaining = ((SimpleContainer)this.container).addItem(originalStack);
                originalStack.setCount(remaining.getCount());
                if (remaining.getCount() == newStack.getCount()) return ItemStack.EMPTY;
            }

            if (originalStack.isEmpty()) slot.set(ItemStack.EMPTY);
            else slot.setChanged();
        }
        return newStack;
    }

    @Override
    public boolean stillValid(@NotNull Player player) {
        return this.container.stillValid(player);
    }
}