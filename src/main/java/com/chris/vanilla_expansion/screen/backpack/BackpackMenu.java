package com.chris.vanilla_expansion.screen.backpack;

import com.chris.vanilla_expansion.item.ModItems;
import com.chris.vanilla_expansion.mixin.SlotAccessor;
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

public class BackpackMenu extends AbstractContainerMenu {
    public final Container mainInventory;
    public final Container upgradeInventory;

    public BackpackMenu(int syncId, Inventory playerInventory) {
        this(syncId, playerInventory, new SimpleContainer(54), new SimpleContainer(6));
    }

    public BackpackMenu(int syncId, Inventory playerInventory, Container mainInventory, Container upgradeInventory) {
        super(ModMenus.BACKPACK_MENU, syncId);
        this.mainInventory = mainInventory;
        this.upgradeInventory = upgradeInventory;

        // 1. STORAGE SLOTS (0-53)
        for (int j = 0; j < 6; ++j) {
            for (int i = 0; i < 9; ++i) {
                this.addSlot(new Slot(mainInventory, i + j * 9, 8 + i * 18, 18 + j * 18) {
                    @Override
                    public boolean mayPlace(@NotNull ItemStack stack) {
                        return !stack.is(ModItems.BACKPACK_ITEM);
                    }

                    @Override
                    public int getMaxStackSize() {
                        // Keep this as is—it controls what the slot can HOLD
                        return isStackUpgraded() ? 128 : 64;
                    }

                    @Override
                    public int getMaxStackSize(@NotNull ItemStack stack) {
                        // This is the "Capture" limit—set this to 64
                        // Even if the slot has 128, the cursor will aim for 64
                        return 64;
                    }

                    @Override
                    public @NotNull ItemStack remove(int amount) {
                        // Force the removal to never exceed 64 per click/drag
                        return super.remove(Math.min(amount, 64));
                    }
                });
            }
        }

        // 2. UPGRADE SLOTS (54-59)
        for (int k = 0; k < 6; ++k) {
            this.addSlot(new Slot(upgradeInventory, k, 177, 18 + k * 18) {
                @Override
                public boolean mayPlace(@NotNull ItemStack stack) {
                    return stack.is(ModItems.STORAGE_UPGRADE) || stack.is(ModItems.STACK_UPGRADE);
                }

                @Override
                public void setChanged() {
                    super.setChanged();
                    // Refresh positions immediately when an upgrade is toggled
                    updateSlotPositions();
                }
            });
        }

        addPlayerInventory(playerInventory);
        updateSlotPositions();
    }

    public boolean isStorageUpgraded() {
        for (int i = 0; i < upgradeInventory.getContainerSize(); i++) {
            if (upgradeInventory.getItem(i).is(ModItems.STORAGE_UPGRADE)) return true;
        }
        return false;
    }

    public boolean isStackUpgraded() {
        for (int i = 0; i < upgradeInventory.getContainerSize(); i++) {
            if (upgradeInventory.getItem(i).is(ModItems.STACK_UPGRADE)) return true;
        }
        return false;
    }

    @Override
    public void clicked(int slotIndex, int buttonNum, @NotNull ContainerInput containerInput, @NotNull Player player) {
        // Manual 128-stack merging for mouse clicks
        if (slotIndex >= 0 && slotIndex < 54 && isStackUpgraded()) {
            Slot slot = this.slots.get(slotIndex);
            ItemStack held = this.getCarried();
            ItemStack stackInSlot = slot.getItem();

            if (!held.isEmpty() && !stackInSlot.isEmpty() && ItemStack.isSameItemSameComponents(held, stackInSlot)) {
                int max = 128;
                int current = stackInSlot.getCount();

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

    public void updateSlotPositions() {
        boolean storageUpgraded = isStorageUpgraded();
        int playerInvY = storageUpgraded ? 140 : 84;

        for (int i = 0; i < 54; i++) {
            Slot slot = this.slots.get(i);
            // Hide indices 27-53 if storage upgrade is not present
            ((SlotAccessor) slot).setY((i >= 27 && !storageUpgraded) ? -2000 : 18 + (i / 9) * 18);
        }

        for (int i = 0; i < 6; i++) {
            Slot slot = this.slots.get(54 + i);
            ((SlotAccessor) slot).setY(18 + i * 18);
        }

        for (int i = 0; i < 36; i++) {
            Slot slot = this.slots.get(60 + i);
            int yPos = (i < 27) ? playerInvY + (i / 9) * 18 : playerInvY + 58;
            ((SlotAccessor) slot).setY(yPos);
        }
    }

    @Override
    public @NotNull ItemStack quickMoveStack(@NotNull Player player, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);

        if (slot != null && slot.hasItem()) {
            ItemStack originalStack = slot.getItem();
            itemstack = originalStack.copy();

            // 0-53: Backpack Storage
            if (index < 54) {
                if (!this.moveItemStackTo(originalStack, 60, 96, true)) return ItemStack.EMPTY;
            }
            // 54-59: Upgrades
            else if (index < 60) {
                if (!this.moveItemStackTo(originalStack, 60, 96, true)) return ItemStack.EMPTY;
            }
            // 60-95: Player Inventory
            else {
                // Block nesting backpacks via shift-click
                if (originalStack.is(ModItems.BACKPACK_ITEM)) {
                    return ItemStack.EMPTY;
                }

                // Handle upgrades moving into upgrade slots
                if (originalStack.is(ModItems.STORAGE_UPGRADE) || originalStack.is(ModItems.STACK_UPGRADE)) {
                    if (!this.moveItemStackTo(originalStack, 54, 60, false)) return ItemStack.EMPTY;
                } else {
                    int maxAllowed = isStackUpgraded() ? 128 : 64;
                    int storageLimit = isStorageUpgraded() ? 54 : 27;

                    // Manual merge loop for 128 capability
                    for (int i = 0; i < storageLimit; i++) {
                        Slot targetSlot = this.slots.get(i);
                        ItemStack targetStack = targetSlot.getItem();
                        if (!targetStack.isEmpty() && ItemStack.isSameItemSameComponents(originalStack, targetStack)) {
                            int current = targetStack.getCount();
                            if (current < maxAllowed) {
                                int toMove = Math.min(originalStack.getCount(), maxAllowed - current);
                                targetStack.grow(toMove);
                                originalStack.shrink(toMove);
                                targetSlot.setChanged();
                            }
                        }
                        if (originalStack.isEmpty()) break;
                    }

                    if (!originalStack.isEmpty()) {
                        if (!this.moveItemStackTo(originalStack, 0, storageLimit, false)) return ItemStack.EMPTY;
                    }
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
    public boolean stillValid(@NotNull Player player) { return true; }
}