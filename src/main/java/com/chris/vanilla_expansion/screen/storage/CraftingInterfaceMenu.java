package com.chris.vanilla_expansion.screen.storage;

import com.chris.vanilla_expansion.block.storage.entity.CraftingInterfaceBlockEntity;
import com.chris.vanilla_expansion.block.storage.entity.StorageControllerBlockEntity;
import com.chris.vanilla_expansion.screen.ModMenus;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Prediction;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class CraftingInterfaceMenu extends AbstractContainerMenu {
    public static final int VIEWPORT_SIZE = 45;
    public static final int ROWS = 5;

    public static final int VIEWPORT_START = 0;
    public static final int VIEWPORT_END = 45;
    public static final int CRAFT_GRID_START = 45;
    public static final int CRAFT_GRID_END = 54;
    public static final int RESULT_SLOT_INDEX = 54;
    public static final int PLAYER_INV_START = 55;
    public static final int PLAYER_HOTBAR_END = 91;

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
    private final CraftingInterfaceBlockEntity interfaceEntity;
    private final Player player;

    public final SimpleContainer networkContainer = new SimpleContainer(VIEWPORT_SIZE);
    private final CraftingContainer craftSlots;
    private final ResultContainer resultSlots = new ResultContainer();

    private final List<ItemStack> allNetworkItems = new ArrayList<>();
    private final List<ItemStack> filteredNetworkItems = new ArrayList<>();

    private int scrollRowOffset = 0;
    private String searchFilter = "";

    private final DataSlot totalRowsSlot = new DataSlot() {
        private int value = ROWS;

        @Override
        public int get() {
            if (interfaceEntity != null && interfaceEntity.getLevel() != null && !interfaceEntity.getLevel().isClientSide()) {
                return (int) Math.ceil(filteredNetworkItems.size() / 9.0);
            }
            return this.value;
        }

        @Override
        public void set(int value) {
            this.value = value;
        }
    };

    private final DataSlot sortModeSlot = new DataSlot() {
        @Override
        public int get() {
            if (interfaceEntity != null && interfaceEntity.getLevel() != null && !interfaceEntity.getLevel().isClientSide()) {
                return interfaceEntity.getSortMode().ordinal();
            }
            return currentSortMode.ordinal();
        }

        @Override
        public void set(int value) {
            currentSortMode = SortMode.fromOrdinal(value);
        }
    };

    public CraftingInterfaceMenu(int syncId, Inventory playerInventory) {
        this(syncId, playerInventory, BlockPos.ZERO, null);
    }

    public CraftingInterfaceMenu(int syncId, Inventory playerInventory, BlockPos pos, CraftingInterfaceBlockEntity interfaceEntity) {
        super(ModMenus.CRAFTING_INTERFACE_MENU, syncId);
        this.pos = pos;
        this.interfaceEntity = interfaceEntity;
        this.player = playerInventory.player;

        this.addDataSlot(this.totalRowsSlot);
        this.addDataSlot(this.sortModeSlot);

        if (interfaceEntity != null && interfaceEntity.getLevel() != null && !interfaceEntity.getLevel().isClientSide()) {
            this.currentSortMode = interfaceEntity.getSortMode();
        }

        for (int row = 0; row < ROWS; ++row) {
            for (int col = 0; col < 9; ++col) {
                this.addSlot(new NetworkSlot(networkContainer, col + row * 9, 9 + col * 18, 18 + row * 18));
            }
        }

        this.craftSlots = new TransientCraftingContainer(this, 3, 3);

        if (interfaceEntity != null) {
            SimpleContainer matrix = interfaceEntity.getCraftingMatrix();
            for (int i = 0; i < 9; i++) {
                this.craftSlots.setItem(i, matrix.getItem(i).copy());
            }
        }

        for (int row = 0; row < 3; ++row) {
            for (int col = 0; col < 3; ++col) {
                int slotIndex = col + row * 3;
                this.addSlot(new Slot(this.craftSlots, slotIndex, 27 + col * 18, 112 + row * 18) {
                    @Override
                    public void setChanged() {
                        super.setChanged();
                        if (CraftingInterfaceMenu.this.interfaceEntity != null) {
                            CraftingInterfaceMenu.this.interfaceEntity.getCraftingMatrix().setItem(slotIndex, getItem());
                        }
                        CraftingInterfaceMenu.this.slotsChanged(craftSlots);
                    }
                });
            }
        }

        // Result Slot (54)
        this.addSlot(new ResultSlot(playerInventory.player, this.craftSlots, this.resultSlots, 0, 121, 130) {
            @Override
            public void onTake(Player player, ItemStack stack) {
                ItemStack[] targets = new ItemStack[9];
                for (int i = 0; i < 9; i++) {
                    targets[i] = CraftingInterfaceMenu.this.craftSlots.getItem(i).copy();
                }

                super.onTake(player, stack);

                if (player.level() instanceof ServerLevel) {
                    StorageControllerBlockEntity controller = interfaceEntity != null ? interfaceEntity.getController() : null;

                    for (int i = 0; i < 9; i++) {
                        ItemStack gridStack = CraftingInterfaceMenu.this.craftSlots.getItem(i);
                        ItemStack target = targets[i];

                        if (gridStack.isEmpty() && !target.isEmpty()) {
                            ItemStack extracted = ItemStack.EMPTY;

                            if (controller != null) {
                                for (ItemStack networkStack : controller.getNetworkItems()) {
                                    if (ItemStack.isSameItem(networkStack, target)) {
                                        extracted = controller.extractItem(networkStack, 1);
                                        if (!extracted.isEmpty()) break;
                                    }
                                }
                            }

                            if (extracted.isEmpty()) {
                                Inventory inv = player.getInventory();
                                for (int slot = 0; slot < inv.getContainerSize(); slot++) {
                                    ItemStack invStack = inv.getItem(slot);
                                    if (!invStack.isEmpty() && ItemStack.isSameItem(invStack, target)) {
                                        extracted = inv.removeItem(slot, 1);
                                        break;
                                    }
                                }
                            }

                            if (!extracted.isEmpty()) {
                                CraftingInterfaceMenu.this.craftSlots.setItem(i, extracted);
                            }
                        }
                    }

                    CraftingInterfaceMenu.this.syncCraftingMatrixToEntity();
                    CraftingInterfaceMenu.this.slotsChanged(CraftingInterfaceMenu.this.craftSlots);
                }
            }
        });

        addPlayerInventory(playerInventory);

        if (interfaceEntity != null && interfaceEntity.getLevel() != null && !interfaceEntity.getLevel().isClientSide()) {
            refreshNetworkItems();
        }

        slotsChanged(this.craftSlots);
    }

    private void addPlayerInventory(Inventory playerInventory) {
        for (int row = 0; row < 3; ++row) {
            for (int col = 0; col < 9; ++col) {
                this.addSlot(new Slot(playerInventory, col + row * 9 + 9, 9 + col * 18, 170 + row * 18));
            }
        }
        for (int col = 0; col < 9; ++col) {
            this.addSlot(new Slot(playerInventory, col, 9 + col * 18, 228));
        }
    }

    public void setSortMode(SortMode mode) {
        this.currentSortMode = mode;
        if (this.interfaceEntity != null) {
            this.interfaceEntity.setSortMode(mode);
        }
        refreshNetworkItems();
    }

    public SortMode getSortMode() {
        return this.currentSortMode;
    }

    @Override
    public void removed(@NotNull Player player) {
        super.removed(player);
        if (!player.level().isClientSide() && interfaceEntity != null) {
            refreshNetworkItems();
        }
    }

    @Override
    public boolean clickMenuButton(Player player, int id) {
        if (!player.level().isClientSide()) {
            applyFilterAndScroll(this.searchFilter, id);
            return true;
        }
        return super.clickMenuButton(player, id);
    }

    @Override
    public void slotsChanged(@NotNull Container container) {
        if (this.player.level() instanceof ServerLevel serverLevel) {
            CraftingInput input = this.craftSlots.asCraftInput();
            ServerPlayer serverPlayer = (ServerPlayer) this.player;
            ItemStack result = ItemStack.EMPTY;

            Optional<RecipeHolder<@NotNull CraftingRecipe>> maybeRecipe = serverLevel.getServer().getRecipeManager().getRecipeFor(RecipeType.CRAFTING, input, serverLevel);

            if (maybeRecipe.isPresent()) {
                RecipeHolder<@NotNull CraftingRecipe> recipeHolder = maybeRecipe.get();
                CraftingRecipe craftingRecipe = recipeHolder.value();
                if (this.resultSlots.setRecipeUsed(serverPlayer, recipeHolder)) {
                    ItemStack recipeResult = craftingRecipe.assemble(input);
                    if (recipeResult.isItemEnabled(serverLevel.enabledFeatures())) {
                        result = recipeResult;
                    }
                }
            }

            this.resultSlots.setItem(0, result);
            this.broadcastChanges();
        }
    }

    public void refreshNetworkItems() {
        if (interfaceEntity == null || interfaceEntity.getLevel() == null || interfaceEntity.getLevel().isClientSide()) {
            return;
        }
        StorageControllerBlockEntity controller = interfaceEntity.getController();
        if (controller == null) {
            return;
        }

        controller.scanNetwork();
        this.allNetworkItems.clear();
        this.allNetworkItems.addAll(controller.getNetworkItems());

        switch (this.currentSortMode) {
            case COUNT -> this.allNetworkItems.sort((a, b) -> Integer.compare(b.getCount(), a.getCount()));
            case NAME -> this.allNetworkItems.sort(Comparator.comparing(a -> a.getHoverName().getString().toLowerCase()));
            case MOD -> this.allNetworkItems.sort(Comparator.comparing(a -> BuiltInRegistries.ITEM.getKey(a.getItem()).getNamespace().toLowerCase()));
        }

        applyFilterAndScroll(this.searchFilter, this.scrollRowOffset);
    }

    public void applyFilterAndScroll(String search, int rowOffset) {
        if (interfaceEntity == null || interfaceEntity.getLevel() == null || interfaceEntity.getLevel().isClientSide()) {
            return;
        }

        this.searchFilter = search != null ? search.trim().toLowerCase() : "";

        this.filteredNetworkItems.clear();
        for (ItemStack stack : allNetworkItems) {
            if (matchesFilter(stack, this.searchFilter)) {
                this.filteredNetworkItems.add(stack);
            }
        }

        int totalItems = this.filteredNetworkItems.size();
        int maxScrollRows = Math.max(0, (int) Math.ceil(totalItems / 9.0) - ROWS);
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

    private void syncCraftingMatrixToEntity() {
        if (this.interfaceEntity != null) {
            SimpleContainer matrix = this.interfaceEntity.getCraftingMatrix();
            for (int i = 0; i < 9; i++) {
                matrix.setItem(i, this.craftSlots.getItem(i).copy());
            }
        }
    }

    public void clearGridToStorage(Player player) {
        StorageControllerBlockEntity controller = this.interfaceEntity != null ? this.interfaceEntity.getController() : null;

        for (int i = 0; i < 9; i++) {
            ItemStack stack = this.craftSlots.getItem(i);
            if (!stack.isEmpty()) {
                if (controller != null) {
                    ItemStack remainder = controller.insertItem(stack);
                    this.craftSlots.setItem(i, remainder);
                } else {
                    if (!player.getInventory().add(stack)) {
                        player.drop(stack, false, Prediction.PREDICTED);
                    }
                    this.craftSlots.setItem(i, ItemStack.EMPTY);
                }
            }
        }

        syncCraftingMatrixToEntity();
        slotsChanged(this.craftSlots);
        this.broadcastChanges();
        refreshNetworkItems();
    }

    public void clearGridToPlayer(Player player) {
        for (int i = 0; i < 9; i++) {
            ItemStack stack = this.craftSlots.getItem(i);
            if (!stack.isEmpty()) {
                if (!player.getInventory().add(stack)) {
                    player.drop(stack, false, Prediction.PREDICTED);
                }
                this.craftSlots.setItem(i, ItemStack.EMPTY);
            }
        }

        syncCraftingMatrixToEntity();
        slotsChanged(this.craftSlots);
        this.broadcastChanges();
    }

    public void rotateGrid() {
        int[] rotationMap = {6, 3, 0, 7, 4, 1, 8, 5, 2};
        ItemStack[] newGrid = new ItemStack[9];

        for (int i = 0; i < 9; i++) {
            newGrid[i] = this.craftSlots.getItem(rotationMap[i]).copy();
        }

        for (int i = 0; i < 9; i++) {
            this.craftSlots.setItem(i, newGrid[i]);
        }

        syncCraftingMatrixToEntity();
        slotsChanged(this.craftSlots);
        this.broadcastChanges();
    }

    public void balanceGrid() {
        Map<Item, List<Integer>> itemSlots = new HashMap<>();

        for (int i = 0; i < 9; i++) {
            ItemStack stack = this.craftSlots.getItem(i);
            if (!stack.isEmpty()) {
                itemSlots.computeIfAbsent(stack.getItem(), k -> new ArrayList<>()).add(i);
            }
        }

        for (List<Integer> slots : itemSlots.values()) {
            int totalCount = 0;
            for (int slotIdx : slots) {
                totalCount += this.craftSlots.getItem(slotIdx).getCount();
            }

            int basePerSlot = totalCount / slots.size();
            int remainder = totalCount % slots.size();

            for (int i = 0; i < slots.size(); i++) {
                int slotIdx = slots.get(i);
                int count = basePerSlot + (i < remainder ? 1 : 0);
                ItemStack stack = this.craftSlots.getItem(slotIdx);
                stack.setCount(count);
                this.craftSlots.setItem(slotIdx, stack);
            }
        }

        syncCraftingMatrixToEntity();
        slotsChanged(this.craftSlots);
        this.broadcastChanges();
    }

    @Override
    public void clicked(int slotId, int button, @NotNull ContainerInput clickType, @NotNull Player player) {
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
        ItemStack originalStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);

        if (slot != null && slot.hasItem()) {
            ItemStack stackInSlot = slot.getItem();
            originalStack = stackInSlot.copy();

            if (index == RESULT_SLOT_INDEX) {
                int maxCraftAmount = stackInSlot.getMaxStackSize();
                int totalCrafted = 0;

                while (slot.hasItem() && totalCrafted < maxCraftAmount) {
                    ItemStack currentResult = slot.getItem().copy();
                    int craftCount = currentResult.getCount();

                    if (totalCrafted + craftCount > maxCraftAmount) {
                        break;
                    }

                    boolean moved = this.moveItemStackTo(currentResult, PLAYER_INV_START, PLAYER_HOTBAR_END, true);

                    if (!moved) {
                        break;
                    }

                    totalCrafted += craftCount;

                    slot.onQuickCraft(currentResult, currentResult);
                    slot.onTake(player, currentResult);

                    if (!slot.hasItem()) {
                        break;
                    }
                }

                if (interfaceEntity != null && interfaceEntity.getLevel() != null && !interfaceEntity.getLevel().isClientSide()) {
                    refreshNetworkItems();
                }

                return ItemStack.EMPTY;
            } else if (index >= PLAYER_INV_START) {
                if (interfaceEntity != null && interfaceEntity.getLevel() != null && !interfaceEntity.getLevel().isClientSide()) {
                    StorageControllerBlockEntity controller = interfaceEntity.getController();
                    if (controller != null) {
                        ItemStack remainder = controller.insertItem(stackInSlot.copy());
                        slot.set(remainder);
                        slot.setChanged();
                        refreshNetworkItems();
                        return ItemStack.EMPTY;
                    }
                }
            }

            if (stackInSlot.isEmpty()) {
                slot.setByPlayer(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (stackInSlot.getCount() == originalStack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(player, stackInSlot);
        }

        return originalStack;
    }

    public int getScrollRowOffset() {
        return this.scrollRowOffset;
    }

    public BlockPos getPos() {
        return pos;
    }

    @Override
    public boolean stillValid(@NotNull Player player) {
        return true;
    }

    public int getTotalRows() {
        return Math.max(ROWS, this.totalRowsSlot.get());
    }

    public CraftingInterfaceBlockEntity getInterfaceEntity() {
        return this.interfaceEntity;
    }

    public CraftingContainer getCraftSlots() {
        return this.craftSlots;
    }

    public void handleJeiRecipeTransfer(ServerPlayer player, List<ItemStack> targetGrid, boolean maxTransfer) {
        if (interfaceEntity == null || interfaceEntity.getLevel() == null || interfaceEntity.getLevel().isClientSide()) return;

        StorageControllerBlockEntity controller = interfaceEntity.getController();

        clearGridToStorage(player);

        for (int i = 0; i < 9 && i < targetGrid.size(); i++) {
            ItemStack target = targetGrid.get(i);
            if (target.isEmpty()) continue;

            ItemStack extracted = ItemStack.EMPTY;

            if (controller != null) {
                for (ItemStack networkStack : controller.getNetworkItems()) {
                    if (ItemStack.isSameItem(networkStack, target)) {
                        extracted = controller.extractItem(networkStack, 1);
                        if (!extracted.isEmpty()) break;
                    }
                }
            }

            if (extracted.isEmpty()) {
                Inventory inv = player.getInventory();
                for (int slot = 0; slot < inv.getContainerSize(); slot++) {
                    ItemStack invStack = inv.getItem(slot);
                    if (!invStack.isEmpty() && ItemStack.isSameItem(invStack, target)) {
                        extracted = inv.removeItem(slot, 1);
                        break;
                    }
                }
            }

            if (!extracted.isEmpty()) {
                this.craftSlots.setItem(i, extracted);
            }
        }

        syncCraftingMatrixToEntity();
        slotsChanged(this.craftSlots);
        this.broadcastChanges();
        refreshNetworkItems();
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