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

public class StorageExporterMenu extends AbstractContainerMenu {
    private final Container container;

    public StorageExporterMenu(int syncId, Inventory playerInventory) {
        this(syncId, playerInventory, new SimpleContainer(18));
    }

    public StorageExporterMenu(int syncId, Inventory playerInventory, Container container) {
        super(ModMenus.STORAGE_EXPORTER_MENU, syncId);
        checkContainerSize(container, 18);
        this.container = container;
        container.startOpen(playerInventory.player);

        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(container, i, 8 + i * 18, 18) {
                @Override
                public boolean mayPlace(@NotNull ItemStack stack) {
                    return true;
                }

                @Override
                public void set(@NotNull ItemStack stack) {
                    if (!stack.isEmpty()) {
                        ItemStack filterStack = stack.copy();
                        filterStack.setCount(1);
                        super.set(filterStack);
                    } else {
                        super.set(ItemStack.EMPTY);
                    }
                }
            });
        }

        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(container, i + 9, 8 + i * 18, 54));
        }

        for (int row = 0; row < 3; ++row) {
            for (int col = 0; col < 9; ++col) {
                this.addSlot(new Slot(playerInventory, col + row * 9 + 9, 8 + col * 18, 86 + row * 18));
            }
        }

        for (int col = 0; col < 9; ++col) {
            this.addSlot(new Slot(playerInventory, col, 8 + col * 18, 144));
        }
    }

    @Override
    public void clicked(int slotIndex, int button, @NotNull ContainerInput actionType, @NotNull Player player) {
        if (slotIndex >= 0 && slotIndex < 9) {
            Slot slot = this.getSlot(slotIndex);
            ItemStack carried = this.getCarried();

            if (actionType == ContainerInput.PICKUP) {
                if (carried.isEmpty()) {
                    slot.set(ItemStack.EMPTY);
                } else {
                    ItemStack filter = carried.copy();
                    filter.setCount(1);
                    slot.set(filter);
                }
            } else if (actionType == ContainerInput.CLONE && player.isCreative()) {
                if (!slot.hasItem()) {
                    slot.set(ItemStack.EMPTY);
                } else {
                    ItemStack clone = slot.getItem().copy();
                    clone.setCount(clone.getMaxStackSize());
                    this.setCarried(clone);
                }
            }
            return;
        }
        super.clicked(slotIndex, button, actionType, player);
    }

    @Override
    public boolean stillValid(@NotNull Player player) {
        return this.container.stillValid(player);
    }

    @Override
    public @NotNull ItemStack quickMoveStack(@NotNull Player player, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();

            if (index < 9) {
                return ItemStack.EMPTY;
            }

            if (index < 18) {
                if (!this.moveItemStackTo(itemstack1, 18, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(itemstack1, 9, 18, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }
        return itemstack;
    }
}