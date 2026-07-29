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
import net.minecraft.world.Container;
import net.minecraft.world.inventory.ResultContainer;
import net.minecraft.world.inventory.ResultSlot;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CraftingInterfaceMenu extends AbstractContainerMenu {
    public static final int VIEWPORT_SIZE = 45; // 9 cols x 5 rows
    public static final int ROWS = 5;

    // Slot Indices
    public static final int VIEWPORT_START = 0;
    public static final int VIEWPORT_END = 45;
    public static final int CRAFT_GRID_START = 45;
    public static final int CRAFT_GRID_END = 54;
    public static final int RESULT_SLOT_INDEX = 54;
    public static final int PLAYER_INV_START = 55;
    public static final int PLAYER_HOTBAR_END = 91;

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
                int calc = (int) Math.ceil(filteredNetworkItems.size() / 9.0);
                return calc;
            }
            return this.value;
        }

        @Override
        public void set(int value) {
            System.out.println("[DEBUG-MENU] totalRowsSlot received sync set value: " + value);
            this.value = value;
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

        System.out.println("[DEBUG-MENU] Initializing Menu (Side: " + (player.level().isClientSide() ? "CLIENT" : "SERVER") + ")");

        this.addDataSlot(this.totalRowsSlot);

        // 1. Storage Viewport Slots (0..44): X = 9, Y = 18
        for (int row = 0; row < ROWS; ++row) {
            for (int col = 0; col < 9; ++col) {
                this.addSlot(new NetworkSlot(networkContainer, col + row * 9, 9 + col * 18, 18 + row * 18));
            }
        }

        // 2. Wrap crafting matrix into TransientCraftingContainer
        this.craftSlots = new TransientCraftingContainer(this, 3, 3);

        // Populate saved matrix
        if (interfaceEntity != null) {
            SimpleContainer matrix = interfaceEntity.getCraftingMatrix();
            for (int i = 0; i < 9; i++) {
                this.craftSlots.setItem(i, matrix.getItem(i).copy());
            }
        }

        // 3. 3x3 Crafting Grid Slots (45..53): X = 27, Y = 112
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

        // 4. Crafting Result Slot (54): X = 121, Y = 130
        this.addSlot(new ResultSlot(playerInventory.player, this.craftSlots, this.resultSlots, 0, 121, 130));

        // 5. Player Main Inventory & Hotbar (55..90)
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

    @Override
    public boolean clickMenuButton(Player player, int id) {
        System.out.println("[DEBUG-MENU] clickMenuButton triggered on " +
                (player.level().isClientSide() ? "CLIENT" : "SERVER") + " with id/rowOffset: " + id);

        if (!player.level().isClientSide()) {
            applyFilterAndScroll(this.searchFilter, id);
            return true;
        }
        return super.clickMenuButton(player, id);
    }

    @Override
    public void slotsChanged(Container container) {
        if (this.player.level() instanceof ServerLevel serverLevel) {
            CraftingInput input = this.craftSlots.asCraftInput();
            ServerPlayer serverPlayer = (ServerPlayer) this.player;
            ItemStack result = ItemStack.EMPTY;

            Optional<RecipeHolder<CraftingRecipe>> maybeRecipe = serverLevel.getServer().getRecipeManager().getRecipeFor(RecipeType.CRAFTING, input, serverLevel);

            if (maybeRecipe.isPresent()) {
                RecipeHolder<CraftingRecipe> recipeHolder = maybeRecipe.get();
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
            //System.out.println("[DEBUG-MENU] refreshNetworkItems skipped (Client-side or Null BE)");
            return;
        }
        StorageControllerBlockEntity controller = interfaceEntity.getController();
        if (controller == null) {
            //System.out.println("[DEBUG-MENU] refreshNetworkItems skipped: Controller is NULL");
            return;
        }

        controller.scanNetwork();
        this.allNetworkItems.clear();
        this.allNetworkItems.addAll(controller.getNetworkItems());
        this.allNetworkItems.sort((a, b) -> Integer.compare(b.getCount(), a.getCount()));

        //System.out.println("[DEBUG-MENU] Refreshed items from Controller. Found total items: " + this.allNetworkItems.size());

        applyFilterAndScroll(this.searchFilter, this.scrollRowOffset);
    }

    public void applyFilterAndScroll(String search, int rowOffset) {
        if (interfaceEntity == null || interfaceEntity.getLevel() == null || interfaceEntity.getLevel().isClientSide()) {
            //System.out.println("[DEBUG-MENU] applyFilterAndScroll aborted on CLIENT or null level");
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

//        System.out.println(String.format(
//                "[DEBUG-MENU] applyFilterAndScroll Executing -> Total Filtered Items: %d | Requested Row: %d | Max Rows: %d | Clamped Offset: %d",
//                totalItems, rowOffset, maxScrollRows, this.scrollRowOffset
//        ));

        int startIndex = this.scrollRowOffset * 9;
        for (int i = 0; i < VIEWPORT_SIZE; i++) {
            int itemIndex = startIndex + i;
            if (itemIndex < this.filteredNetworkItems.size()) {
                this.networkContainer.setItem(i, this.filteredNetworkItems.get(itemIndex).copy());
            } else {
                this.networkContainer.setItem(i, ItemStack.EMPTY);
            }
        }

        //System.out.println("[DEBUG-MENU] Network container slots updated. Calling broadcastChanges().");
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
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);

        if (slot != null && slot.hasItem()) {
            ItemStack stackInSlot = slot.getItem();
            itemstack = stackInSlot.copy();

            if (index == RESULT_SLOT_INDEX) {
                if (!this.moveItemStackTo(stackInSlot, PLAYER_INV_START, PLAYER_HOTBAR_END, true)) {
                    return ItemStack.EMPTY;
                }
                slot.onQuickCraft(stackInSlot, itemstack);
            }
            else if (index >= PLAYER_INV_START) {
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

            if (stackInSlot.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(player, stackInSlot);
        }

        return itemstack;
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