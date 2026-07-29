package com.chris.vanilla_expansion.screen.storage;

import com.chris.vanilla_expansion.block.storage.entity.StorageCrateBlockEntity;
import com.chris.vanilla_expansion.item.ModItems;
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
    private static final int STORAGE_SLOT = 0;
    private static final int UPGRADE_SLOT_START = 1;
    private static final int UPGRADE_SLOT_COUNT = 3;
    private static final int CRATE_SIZE = 1 + UPGRADE_SLOT_COUNT; // 4 total
    private static final int PLAYER_INV_START = CRATE_SIZE;
    private static final int PLAYER_INV_END = CRATE_SIZE + 36;

    public StorageCrateMenu(int syncId, Inventory playerInventory) {
        this(syncId, playerInventory, new SimpleContainer(CRATE_SIZE));
    }

    public StorageCrateMenu(int syncId, Inventory playerInventory, Container container) {
        super(ModMenus.STORAGE_CRATE_MENU, syncId);
        checkContainerSize(container, CRATE_SIZE);
        this.container = container;
        container.startOpen(playerInventory.player);

        // Main storage slot with lock filter check
        this.addSlot(new Slot(this.container, STORAGE_SLOT, 80, 36) {
            @Override
            public boolean mayPlace(@NotNull ItemStack stack) {
                // Delegated directly to container logic (handles isLocked and lockFilter)
                return StorageCrateMenu.this.container.canPlaceItem(STORAGE_SLOT, stack);
            }

            @Override
            public int getMaxStackSize() {
                return StorageCrateMenu.this.container.getMaxStackSize();
            }

            @Override
            public int getMaxStackSize(@NotNull ItemStack stack) {
                return StorageCrateMenu.this.container.getMaxStackSize();
            }

            @Override
            public @NotNull ItemStack remove(int amount) {
                return super.remove(Math.min(amount, 64));
            }
        });

        // Upgrade slots (top-right, stacked vertically)
        for (int i = 0; i < UPGRADE_SLOT_COUNT; i++) {
            final int slotIndex = UPGRADE_SLOT_START + i;
            this.addSlot(new Slot(this.container, slotIndex, 152, 18 + i * 18) {
                @Override
                public boolean mayPlace(@NotNull ItemStack stack) {
                    return stack.getItem() == ModItems.STORAGE_BLOCK_UPGRADE;
                }

                @Override
                public int getMaxStackSize() {
                    return 1;
                }

                @Override
                public boolean mayPickup(@NotNull Player player) {
                    if (!this.hasItem()) return true;
                    if (!(StorageCrateMenu.this.container instanceof StorageCrateBlockEntity crate)) {
                        return true;
                    }
                    int storedCount = crate.getItem(STORAGE_SLOT).getCount();
                    int capacityWithoutThis = crate.getMaxStackSizeExcludingSlot(slotIndex);
                    return storedCount <= capacityWithoutThis;
                }
            });
        }

        addPlayerInventory(playerInventory);
    }

    public Container getContainer() {
        return this.container;
    }

    @Override
    public void clicked(int slotIndex, int buttonNum, @NotNull ContainerInput containerInput, @NotNull Player player) {
        if (slotIndex == STORAGE_SLOT) {
            Slot slot = this.slots.get(STORAGE_SLOT);
            ItemStack held = this.getCarried();

            // Check if held item is allowed to be placed into slot 0
            if (!held.isEmpty() && !slot.mayPlace(held)) {
                return; // Disallow cursor placement if held item violates lock filter
            }

            ItemStack stackInSlot = slot.getItem();

            if (!held.isEmpty() && !stackInSlot.isEmpty() && ItemStack.isSameItemSameComponents(held, stackInSlot)) {
                int current = stackInSlot.getCount();
                int max = this.container.getMaxStackSize();
                if (current < max) {
                    int toMove = Math.min(held.getCount(), max - current);
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

            if (index < PLAYER_INV_START) {
                // Moving out of the crate (storage or upgrade slot) into the player inventory
                if (!this.moveItemStackTo(originalStack, PLAYER_INV_START, PLAYER_INV_END, true)) {
                    return ItemStack.EMPTY;
                }
            } else {
                // Moving from player inventory into the crate
                if (originalStack.getItem() == ModItems.STORAGE_BLOCK_UPGRADE) {
                    // Try to fill an empty upgrade slot first
                    if (!this.moveItemStackTo(originalStack, UPGRADE_SLOT_START, UPGRADE_SLOT_START + UPGRADE_SLOT_COUNT, false)) {
                        return ItemStack.EMPTY;
                    }
                } else {
                    Slot crateSlot = this.slots.get(STORAGE_SLOT);

                    // Check if item is valid for insertion (respecting lockFilter and current stack)
                    if (!crateSlot.mayPlace(originalStack)) {
                        return ItemStack.EMPTY;
                    }

                    ItemStack crateStack = crateSlot.getItem();

                    if (crateStack.isEmpty()) {
                        int max = this.container.getMaxStackSize();
                        int toMove = Math.min(originalStack.getCount(), max);

                        crateSlot.set(originalStack.copyWithCount(toMove));
                        originalStack.shrink(toMove);
                        crateSlot.setChanged();
                    } else if (ItemStack.isSameItemSameComponents(originalStack, crateStack)) {
                        int current = crateStack.getCount();
                        int max = this.container.getMaxStackSize();
                        if (current < max) {
                            int toMove = Math.min(originalStack.getCount(), max - current);
                            crateStack.grow(toMove);
                            originalStack.shrink(toMove);
                            crateSlot.setChanged();
                        }
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