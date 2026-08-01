package com.chris.vanilla_expansion.screen.storage;

import com.chris.vanilla_expansion.block.storage.entity.StorageControllerBlockEntity;
import com.chris.vanilla_expansion.block.storage.entity.StorageInterfaceBlockEntity;
import com.chris.vanilla_expansion.screen.ModMenus;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerInput;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class StorageInterfaceMenu extends AbstractContainerMenu {
    public static final int VIEWPORT_SIZE = 45; // 9 cols x 5 rows
    public static final int ROWS = 5;

    public enum SortMode {
        COUNT,
        NAME,
        MOD;

        public SortMode next() {
            return values()[(this.ordinal() + 1) % values().length];
        }

        public static SortMode fromOrdinal(int ordinal) {
            SortMode[] values = values();
            if (ordinal < 0 || ordinal >= values.length) return COUNT;
            return values[ordinal];
        }
    }

    private SortMode currentSortMode = SortMode.COUNT;

    private final BlockPos pos;
    private final StorageInterfaceBlockEntity interfaceEntity;

    public final SimpleContainer networkContainer = new SimpleContainer(VIEWPORT_SIZE);
    private final List<ItemStack> allNetworkItems = new ArrayList<>();
    private final List<ItemStack> filteredNetworkItems = new ArrayList<>();

    private int scrollRowOffset = 0;
    private String searchFilter = "";

    // Tracks total rows
    private final DataSlot totalRowsSlot = new DataSlot() {
        private int value = ROWS;

        @Override
        public int get() {
            if (interfaceEntity != null && !interfaceEntity.getLevel().isClientSide()) {
                return (int) Math.ceil(filteredNetworkItems.size() / 9.0);
            }
            return this.value;
        }

        @Override
        public void set(int value) {
            this.value = value;
        }
    };

    // Automatically syncs active SortMode between Server and Client GUI
    private final DataSlot sortModeSlot = new DataSlot() {
        @Override
        public int get() {
            if (interfaceEntity != null && !interfaceEntity.getLevel().isClientSide()) {
                return interfaceEntity.getSortMode().ordinal();
            }
            return currentSortMode.ordinal();
        }

        @Override
        public void set(int value) {
            currentSortMode = SortMode.fromOrdinal(value);
        }
    };

    public StorageInterfaceMenu(int syncId, Inventory playerInventory) {
        this(syncId, playerInventory, BlockPos.ZERO, null);
    }

    public StorageInterfaceMenu(int syncId, Inventory playerInventory, BlockPos pos, StorageInterfaceBlockEntity interfaceEntity) {
        super(ModMenus.STORAGE_INTERFACE_MENU, syncId);
        this.pos = pos;
        this.interfaceEntity = interfaceEntity;

        this.addDataSlot(this.totalRowsSlot);
        this.addDataSlot(this.sortModeSlot);

        if (interfaceEntity != null && interfaceEntity.getLevel() != null && !interfaceEntity.getLevel().isClientSide()) {
            this.currentSortMode = interfaceEntity.getSortMode();
            //System.out.println("[DEBUG-MENU] Menu initialized on SERVER with BE SortMode: " + this.currentSortMode);
        }

        // Storage Viewport Slots (5 rows): X = 9, Y = 18
        for (int row = 0; row < ROWS; ++row) {
            for (int col = 0; col < 9; ++col) {
                this.addSlot(new NetworkSlot(networkContainer, col + row * 9, 9 + col * 18, 18 + row * 18));
            }
        }

        // Add Player Inventory & Hotbar
        addPlayerInventory(playerInventory);

        if (interfaceEntity != null && interfaceEntity.getLevel() != null && !interfaceEntity.getLevel().isClientSide()) {
            refreshNetworkItems();
        }
    }

    private void addPlayerInventory(Inventory playerInventory) {
        for (int row = 0; row < 3; ++row) {
            for (int col = 0; col < 9; ++col) {
                this.addSlot(new Slot(playerInventory, col + row * 9 + 9, 9 + col * 18, 112 + row * 18));
            }
        }
        for (int col = 0; col < 9; ++col) {
            this.addSlot(new Slot(playerInventory, col, 9 + col * 18, 170));
        }
    }

    public void setSortMode(SortMode mode) {
        //System.out.println("[DEBUG-MENU] setSortMode called on Menu. New mode: " + mode);
        this.currentSortMode = mode;
        if (this.interfaceEntity != null) {
            this.interfaceEntity.setSortMode(mode);
        }
        refreshNetworkItems();
    }

    public SortMode getSortMode() {
        return this.currentSortMode;
    }

    public void refreshNetworkItems() {
        if (interfaceEntity == null || interfaceEntity.getLevel() == null || interfaceEntity.getLevel().isClientSide()) return;
        StorageControllerBlockEntity controller = interfaceEntity.getController();
        if (controller == null) return;

        controller.scanNetwork();
        this.allNetworkItems.clear();
        this.allNetworkItems.addAll(controller.getNetworkItems());

        //System.out.println("[DEBUG-MENU] Sorting " + allNetworkItems.size() + " items using mode: " + this.currentSortMode);

        // Dynamic Sorting Logic
        switch (this.currentSortMode) {
            case COUNT -> this.allNetworkItems.sort((a, b) -> Integer.compare(b.getCount(), a.getCount()));
            case NAME -> this.allNetworkItems.sort(Comparator.comparing(a -> a.getHoverName().getString().toLowerCase()));
            case MOD -> this.allNetworkItems.sort(Comparator.comparing(a -> BuiltInRegistries.ITEM.getKey(a.getItem()).getNamespace().toLowerCase()));
        }

        applyFilterAndScroll(this.searchFilter, this.scrollRowOffset);
    }

    public void applyFilterAndScroll(String search, int rowOffset) {
        if (interfaceEntity == null || interfaceEntity.getLevel() == null || interfaceEntity.getLevel().isClientSide()) return;

        this.searchFilter = search != null ? search.trim().toLowerCase() : "";

        this.filteredNetworkItems.clear();
        for (ItemStack stack : allNetworkItems) {
            if (matchesFilter(stack, this.searchFilter)) {
                this.filteredNetworkItems.add(stack);
            }
        }

        int maxScrollRows = Math.max(0, (int) Math.ceil(this.filteredNetworkItems.size() / 9.0) - ROWS);
        this.scrollRowOffset = Math.max(0, Math.min(rowOffset, maxScrollRows));

        int startIndex = this.scrollRowOffset * 9;
        for (int i = 0; i < VIEWPORT_SIZE; i++) {
            int itemIndex = startIndex + i;
            if (itemIndex < this.filteredNetworkItems.size()) {
                this.networkContainer.setItem(i, this.filteredNetworkItems.get(itemIndex).copy());
            } else {
                this.networkContainer.setItem(i, ItemStack.EMPTY);
            }
        }
        this.broadcastChanges();
    }

    private boolean matchesFilter(ItemStack stack, String query) {
        if (query.isEmpty()) return true;

        if (query.startsWith("@")) {
            String modQuery = query.substring(1);
            if (modQuery.isEmpty()) return true;
            String namespace = BuiltInRegistries.ITEM.getKey(stack.getItem()).getNamespace().toLowerCase();
            return namespace.contains(modQuery);
        }

        if (query.startsWith("#")) {
            String tagQuery = query.substring(1);
            if (tagQuery.isEmpty()) return true;

            Holder<Item> itemHolder = BuiltInRegistries.ITEM.wrapAsHolder(stack.getItem());
            return itemHolder.tags().anyMatch(tagKey -> {
                Identifier location = tagKey.location();
                return location.getPath().toLowerCase().contains(tagQuery) ||
                        location.toString().toLowerCase().contains(tagQuery);
            });
        }

        return stack.getHoverName().getString().toLowerCase().contains(query);
    }

    @Override
    public void clicked(int slotId, int button, ContainerInput clickType, Player player) {
        if (slotId >= 0 && slotId < VIEWPORT_SIZE) {
            if (!player.level().isClientSide() && interfaceEntity != null) {
                StorageControllerBlockEntity controller = interfaceEntity.getController();
                if (controller != null) {
                    ItemStack carried = getCarried();
                    Slot slot = getSlot(slotId);

                    if (clickType == ContainerInput.THROW) return;

                    if (clickType == ContainerInput.QUICK_MOVE) {
                        if (carried.isEmpty() && slot.hasItem()) {
                            ItemStack target = slot.getItem();
                            ItemStack extracted = controller.extractItem(target, target.getMaxStackSize());
                            if (!extracted.isEmpty()) {
                                setCarried(extracted);
                            }
                            refreshNetworkItems();
                        }
                        return;
                    }

                    if (!carried.isEmpty() && clickType == ContainerInput.PICKUP) {
                        ItemStack stackToInsert = (button == 1) ? carried.split(1) : carried.copy();
                        ItemStack remainder = controller.insertItem(stackToInsert);

                        if (button == 1) {
                            carried.grow(remainder.getCount());
                            setCarried(carried);
                        } else {
                            setCarried(remainder);
                        }
                        refreshNetworkItems();
                        return;
                    }

                    if (carried.isEmpty() && slot.hasItem() && clickType == ContainerInput.PICKUP) {
                        ItemStack targetStack = slot.getItem();
                        int amountToTake = (button == 1) ? Math.max(1, targetStack.getCount() / 2) : Math.min(targetStack.getCount(), targetStack.getMaxStackSize());
                        ItemStack extracted = controller.extractItem(targetStack, amountToTake);
                        if (!extracted.isEmpty()) {
                            setCarried(extracted);
                        }
                        refreshNetworkItems();
                        return;
                    }
                }
            }
            return;
        }

        super.clicked(slotId, button, clickType, player);
    }

    @Override
    public @NotNull ItemStack quickMoveStack(@NotNull Player player, int index) {
        Slot slot = this.slots.get(index);

        if (slot != null && slot.hasItem()) {
            ItemStack originalStack = slot.getItem();

            if (index >= VIEWPORT_SIZE) {
                if (interfaceEntity != null && interfaceEntity.getLevel() != null && !interfaceEntity.getLevel().isClientSide()) {
                    StorageControllerBlockEntity controller = interfaceEntity.getController();
                    if (controller != null) {
                        ItemStack remainder = controller.insertItem(originalStack.copy());

                        if (remainder.getCount() == originalStack.getCount()) {
                            return ItemStack.EMPTY;
                        }

                        slot.set(remainder);
                        slot.setChanged();
                        refreshNetworkItems();
                    }
                }
            }
        }
        return ItemStack.EMPTY;
    }


    @Override
    public boolean stillValid(@NotNull Player player) {
        return true;
    }


    public BlockPos getPos() {
        return pos;
    }

    public int getScrollRowOffset() {
        return this.scrollRowOffset;
    }

    public int getTotalRows() {
        return Math.max(ROWS, this.totalRowsSlot.get());
    }

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