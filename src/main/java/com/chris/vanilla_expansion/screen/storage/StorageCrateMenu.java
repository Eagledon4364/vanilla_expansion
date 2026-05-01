package com.chris.vanilla_expansion.screen.storage;

import com.chris.vanilla_expansion.screen.ModMenus;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerInput;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class StorageCrateMenu extends AbstractContainerMenu {
    private final Container container;
    private static final int CRATE_SIZE = 1;
    private static final int PLAYER_INV_START = 1;
    private static final int PLAYER_INV_END = 37;
    private static final int MAX_STORAGE = 2048;

    public StorageCrateMenu(int syncId, Inventory playerInventory) {
        this(syncId, playerInventory, new SimpleContainer(CRATE_SIZE));
    }

    public StorageCrateMenu(int syncId, Inventory playerInventory, Container container) {
        super(ModMenus.STORAGE_CRATE_MENU, syncId);
        checkContainerSize(container, CRATE_SIZE);
        this.container = container;
        container.startOpen(playerInventory.player);

        this.addSlot(new Slot(this.container, 0, 80, 36) {
            @Override
            public int getMaxStackSize() {
                return MAX_STORAGE;
            }

            @Override
            public int getMaxStackSize(@NotNull ItemStack stack) {
                return MAX_STORAGE;
            }

            @Override
            public @NotNull ItemStack remove(int amount) {
                return super.remove(Math.min(amount, 64));
            }
        });

        addPlayerInventory(playerInventory);
    }

    public Container getContainer() {
        return this.container;
    }

    @Override
    public void clicked(int slotIndex, int buttonNum, @NotNull ContainerInput containerInput, @NotNull Player player) {
        if (slotIndex == 0) {
            Slot slot = this.slots.getFirst();
            ItemStack held = this.getCarried();
            ItemStack stackInSlot = slot.getItem();

            if (!held.isEmpty() && !stackInSlot.isEmpty() && ItemStack.isSameItemSameComponents(held, stackInSlot)) {
                int current = stackInSlot.getCount();
                if (current < MAX_STORAGE) {
                    int toMove = Math.min(held.getCount(), MAX_STORAGE - current);
                    stackInSlot.grow(toMove);
                    held.shrink(toMove);
                    slot.setChanged();
                    if (held.isEmpty()) return;
                }
            }
        }
        super.clicked(slotIndex, buttonNum, containerInput, player);
    }

    @Override
    public @NotNull ItemStack quickMoveStack(@NotNull Player player, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);

        if (slot.hasItem()) {
            ItemStack originalStack = slot.getItem();
            itemstack = originalStack.copy();

            if (index == 0) {
                if (!this.moveItemStackTo(originalStack, PLAYER_INV_START, PLAYER_INV_END, true)) {
                    return ItemStack.EMPTY;
                }
            } else {
                Slot crateSlot = this.slots.getFirst();
                ItemStack crateStack = crateSlot.getItem();

                if (crateStack.isEmpty()) {
                    crateSlot.set(originalStack.copy());
                    originalStack.setCount(0);
                } else if (ItemStack.isSameItemSameComponents(originalStack, crateStack)) {

                    int current = crateStack.getCount();
                    if (current < MAX_STORAGE) {
                        int toMove = Math.min(originalStack.getCount(), MAX_STORAGE - current);
                        crateStack.grow(toMove);
                        originalStack.shrink(toMove);
                        crateSlot.setChanged();
                    }
                }

                if (originalStack.getCount() == itemstack.getCount()) {
                    return ItemStack.EMPTY;
                }
            }

            if (originalStack.isEmpty()) slot.set(ItemStack.EMPTY);
            else slot.setChanged();
        }
        return itemstack;
    }

    private void addPlayerInventory(Inventory playerInventory) {
        for (int row = 0; row < 3; ++row) {
            for (int col = 0; col < 9; ++col) {
                this.addSlot(new Slot(playerInventory, col + row * 9 + 9, 8 + col * 18, 84 + row * 18));
            }
        }
        for (int col = 0; col < 9; ++col) {
            this.addSlot(new Slot(playerInventory, col, 8 + col * 18, 142));
        }
    }

    @Override
    public boolean stillValid(@NotNull Player player) {
        return this.container.stillValid(player);
    }
}