package com.chris.vanilla_expansion.screen.storage;

import com.chris.vanilla_expansion.block.storage.entity.StorageControllerBlockEntity;
import com.chris.vanilla_expansion.block.storage.entity.StorageInterfaceBlockEntity;
import com.chris.vanilla_expansion.screen.ModMenus;
import net.minecraft.core.BlockPos;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerInput;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class StorageInterfaceMenu extends AbstractContainerMenu {
    private final BlockPos pos;
    private final StorageInterfaceBlockEntity interfaceEntity;
    public final SimpleContainer networkContainer = new SimpleContainer(54);

    // Client-side constructor
    public StorageInterfaceMenu(int syncId, Inventory playerInventory) {
        this(syncId, playerInventory, BlockPos.ZERO, null);
    }

    // Server-side constructor
    public StorageInterfaceMenu(int syncId, Inventory playerInventory, BlockPos pos, StorageInterfaceBlockEntity interfaceEntity) {
        super(ModMenus.STORAGE_INTERFACE_MENU, syncId);
        this.pos = pos;
        this.interfaceEntity = interfaceEntity;

        // Populate virtual slots with custom NetworkSlot logic
        for (int row = 0; row < 6; ++row) {
            for (int col = 0; col < 9; ++col) {
                this.addSlot(new NetworkSlot(networkContainer, col + row * 9, 8 + col * 18, 18 + row * 18));
            }
        }

        addPlayerInventory(playerInventory);

        if (interfaceEntity != null && !interfaceEntity.getLevel().isClientSide()) {
            refreshNetworkItems();
        }
    }

    /**
     * Re-scans the network and updates virtual slots from connected container inventories.
     */
    public void refreshNetworkItems() {
        if (interfaceEntity == null || interfaceEntity.getLevel().isClientSide()) return;
        StorageControllerBlockEntity controller = interfaceEntity.getController();
        if (controller == null) return;

        controller.scanNetwork();
        List<ItemStack> items = controller.getNetworkItems();

        for (int i = 0; i < 54; i++) {
            if (i < items.size()) {
                this.networkContainer.setItem(i, items.get(i).copy());
            } else {
                this.networkContainer.setItem(i, ItemStack.EMPTY);
            }
        }
        this.broadcastChanges();
    }

    private void addPlayerInventory(Inventory playerInventory) {
        for (int row = 0; row < 3; ++row) {
            for (int col = 0; col < 9; ++col) {
                this.addSlot(new Slot(playerInventory, col + row * 9 + 9, 8 + col * 18, 140 + row * 18));
            }
        }
        for (int col = 0; col < 9; ++col) {
            this.addSlot(new Slot(playerInventory, col, 8 + col * 18, 198));
        }
    }

    /**
     * Custom slot click handling for Network Grid (Slots 0 - 53)
     */
    @Override
    public void clicked(int slotId, int button, ContainerInput clickType, Player player) {
        // Intercept clicks on virtual storage slots (Server Side)
        if (slotId >= 0 && slotId < 54 && interfaceEntity != null && !interfaceEntity.getLevel().isClientSide()) {
            StorageControllerBlockEntity controller = interfaceEntity.getController();
            if (controller != null) {
                ItemStack carried = getCarried();
                Slot slot = getSlot(slotId);

                // -------------------------------------------------------------
                // 1. BLOCK DROPPING ITEMS WITH 'Q' / THROW KEY
                // -------------------------------------------------------------
                if (clickType == ContainerInput.THROW) {
                    return; // Strictly prevent dropping virtual network items into the world
                }

                // -------------------------------------------------------------
                // 2. SHIFT + LEFT-CLICK (Extract 1 item to HAND)
                // -------------------------------------------------------------
                if (clickType == ContainerInput.QUICK_MOVE) {
                    if (carried.isEmpty() && slot.hasItem()) {
                        ItemStack target = slot.getItem();
                        ItemStack extracted = controller.extractItem(target, 1);

                        if (!extracted.isEmpty()) {
                            setCarried(extracted);
                        }
                        refreshNetworkItems();
                    }
                    return; // Prevent default quickMoveStack execution
                }

                // -------------------------------------------------------------
                // 3. PLACING ITEMS (Player cursor holding item -> clicks slot)
                // -------------------------------------------------------------
                if (!carried.isEmpty() && clickType == ContainerInput.PICKUP) {
                    ItemStack stackToInsert = (button == 1) ? carried.split(1) : carried.copy();
                    ItemStack remainder = controller.insertItem(stackToInsert);

                    if (button == 1) {
                        // Put remainder back into cursor stack
                        carried.grow(remainder.getCount());
                        setCarried(carried);
                    } else {
                        setCarried(remainder);
                    }
                    refreshNetworkItems();
                    return;
                }

                // -------------------------------------------------------------
                // 4. LEFT-CLICK / RIGHT-CLICK (Hand empty -> takes 1 STACK to hand)
                // -------------------------------------------------------------
                if (carried.isEmpty() && slot.hasItem() && clickType == ContainerInput.PICKUP) {
                    ItemStack targetStack = slot.getItem();

                    // Left click (or Right click) extracts 1 full stack limit (e.g., up to 64 items)
                    int amountToTake = Math.min(targetStack.getCount(), targetStack.getMaxStackSize());

                    ItemStack extracted = controller.extractItem(targetStack, amountToTake);
                    if (!extracted.isEmpty()) {
                        setCarried(extracted);
                    }
                    refreshNetworkItems();
                    return;
                }
            }
        }

        super.clicked(slotId, button, clickType, player);
    }

    /**
     * Handles Shift-Clicking FROM Player Inventory (Slots 54-89) -> Network
     */
    @Override
    public @NotNull ItemStack quickMoveStack(@NotNull Player player, int index) {
        ItemStack resultStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);

        if (slot != null && slot.hasItem()) {
            ItemStack originalStack = slot.getItem();
            resultStack = originalStack.copy();

            // Shift-Clicking FROM Player Inventory (54-89) TO Network Controller
            if (index >= 54) {
                if (interfaceEntity != null && !interfaceEntity.getLevel().isClientSide()) {
                    StorageControllerBlockEntity controller = interfaceEntity.getController();
                    if (controller != null) {
                        ItemStack remainder = controller.insertItem(originalStack.copy());

                        // If chests are completely full, abort cleanly
                        if (remainder.getCount() == originalStack.getCount()) {
                            return ItemStack.EMPTY;
                        }

                        slot.set(remainder);
                        slot.setChanged();
                        refreshNetworkItems();
                        return resultStack;
                    }
                } else if (player.level().isClientSide()) {
                    slot.set(ItemStack.EMPTY);
                    return resultStack;
                }
            }
        }

        return ItemStack.EMPTY;
    }

    public BlockPos getPos() {
        return pos;
    }

    @Override
    public boolean stillValid(@NotNull Player player) {
        return true;
    }

    /**
     * Virtual Slot class allowing custom stack size rendering
     */
    private static class NetworkSlot extends Slot {
        public NetworkSlot(SimpleContainer container, int index, int x, int y) {
            super(container, index, x, y);
        }

        @Override
        public boolean mayPlace(@NotNull ItemStack stack) {
            return true;
        }

        @Override
        public int getMaxStackSize() {
            return Integer.MAX_VALUE;
        }

        @Override
        public int getMaxStackSize(@NotNull ItemStack stack) {
            return Integer.MAX_VALUE;
        }
    }
}