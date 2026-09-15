package com.chris.vanilla_expansion.screen.backpack;

import com.chris.vanilla_expansion.VanillaExpansion;
import com.chris.vanilla_expansion.item.ModItems;
import com.chris.vanilla_expansion.mixin.SlotAccessor;
import com.chris.vanilla_expansion.screen.ModMenus;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class BackpackMenu extends AbstractContainerMenu {
    public final Container mainInventory;
    public final Container upgradeInventory;
    public final Player player;

    public final TransientCraftingContainer craftSlots = new TransientCraftingContainer(this, 3, 3);
    public final ResultContainer resultSlots = new ResultContainer();

    public BackpackMenu(int syncId, Inventory playerInventory) {
        this(syncId, playerInventory, new SimpleContainer(54), new SimpleContainer(6));
    }

    public BackpackMenu(int syncId, Inventory playerInventory, Container mainInventory, Container upgradeInventory) {
        super(ModMenus.BACKPACK_MENU, syncId);
        this.player = playerInventory.player;
        this.mainInventory = mainInventory;
        this.upgradeInventory = upgradeInventory;

        // 0..53: Main Backpack Storage Slots
        for (int j = 0; j < 6; ++j) {
            for (int i = 0; i < 9; ++i) {
                this.addSlot(new Slot(mainInventory, i + j * 9, 8 + i * 18, 18 + j * 18) {
                    @Override
                    public boolean mayPlace(@NotNull ItemStack stack) {
                        return !stack.is(ModItems.BACKPACK_ITEM);
                    }

                    @Override
                    public int getMaxStackSize() {
                        return getDynamicMaxStackSize(this.getItem());
                    }

                    @Override
                    public int getMaxStackSize(@NotNull ItemStack stack) {
                        return getDynamicMaxStackSize(stack);
                    }

                    @Override
                    public @NotNull ItemStack remove(int amount) {
                        return super.remove(Math.min(amount, 64));
                    }
                });
            }
        }

        // 54..59: Upgrade Slots (177, 18)
        for (int k = 0; k < 6; ++k) {
            this.addSlot(new Slot(upgradeInventory, k, 177, 18 + k * 18) {
                @Override
                public boolean mayPlace(@NotNull ItemStack stack) {
                    return stack.is(ModItems.STORAGE_UPGRADE) ||
                            stack.is(ModItems.STACK_UPGRADE) ||
                            stack.is(ModItems.CRAFTING_UPGRADE);
                }

                @Override
                public boolean mayPickup(Player player) {
                    ItemStack stack = this.getItem();
                    if (stack.is(ModItems.STACK_UPGRADE)) {
                        int remainingUpgrades = getStackUpgradeCount() - stack.getCount();
                        return canSafelyLowerStackUpgradeLimit(remainingUpgrades);
                    }
                    return super.mayPickup(player);
                }

                @Override
                public void setChanged() {
                    super.setChanged();
                    updateSlotPositions();
                }
            });
        }

        // 60: Result Slot
        this.addSlot(new ResultSlot(playerInventory.player, this.craftSlots, this.resultSlots, 0, 124, 103));

        // 61..69: 3x3 Crafting Grid Slots
        for (int row = 0; row < 3; ++row) {
            for (int col = 0; col < 3; ++col) {
                this.addSlot(new Slot(this.craftSlots, col + row * 3, 30 + col * 18, 85 + row * 18));
            }
        }

        // 70..105: Player Main Inventory & Hotbar
        addPlayerInventory(playerInventory);
        updateSlotPositions();
    }

    @Override
    public void slotsChanged(@NotNull Container container) {
        super.slotsChanged(container);
        if (container == this.craftSlots && this.player.level() instanceof ServerLevel serverLevel) {
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

    public int getStackUpgradeCount() {
        int count = 0;
        for (int i = 0; i < upgradeInventory.getContainerSize(); i++) {
            ItemStack stack = upgradeInventory.getItem(i);
            if (stack.is(ModItems.STACK_UPGRADE)) {
                count += stack.getCount();
            }
        }
        return count;
    }

    public boolean canSafelyLowerStackUpgradeLimit(int targetUpgradeCount) {
        int targetMax = calculateMaxForUpgradeCount(targetUpgradeCount, 64);
        int storageLimit = isStorageUpgraded() ? 54 : 27;

        for (int i = 0; i < storageLimit; i++) {
            ItemStack stack = mainInventory.getItem(i);
            if (!stack.isEmpty() && stack.getCount() > Math.max(stack.getMaxStackSize(), targetMax)) {
                return false;
            }
        }
        return true;
    }

    public int calculateMaxForUpgradeCount(int upgrades, int baseSize) {
        if (upgrades <= 0) return baseSize;
        long calculatedMax = (long) baseSize * (upgrades * 2L);
        return (int) Math.min(calculatedMax, VanillaExpansion.MAX_STACK_SIZE);
    }

    public int getDynamicMaxStackSize(ItemStack stack) {
        int baseSize = stack.isEmpty() ? 64 : stack.getMaxStackSize();
        return calculateMaxForUpgradeCount(getStackUpgradeCount(), baseSize);
    }

    public boolean isStorageUpgraded() {
        return hasUpgrade(ModItems.STORAGE_UPGRADE);
    }

    public boolean isCraftingUpgraded() {
        return hasUpgrade(ModItems.CRAFTING_UPGRADE);
    }

    public boolean isStackUpgraded() {
        return getStackUpgradeCount() > 0;
    }

    private boolean hasUpgrade(Item item) {
        for (int i = 0; i < upgradeInventory.getContainerSize(); i++) {
            if (upgradeInventory.getItem(i).is(item)) return true;
        }
        return false;
    }

    @Override
    public void clicked(int slotIndex, int buttonNum, @NotNull ContainerInput containerInput, @NotNull Player player) {
        if (slotIndex >= 0 && slotIndex < 54 && isStackUpgraded()) {
            Slot slot = this.slots.get(slotIndex);
            ItemStack held = this.getCarried();
            ItemStack stackInSlot = slot.getItem();

            if (!held.isEmpty() && !stackInSlot.isEmpty() && ItemStack.isSameItemSameComponents(held, stackInSlot)) {
                int max = getDynamicMaxStackSize(stackInSlot);
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
        boolean craftingUpgraded = isCraftingUpgraded();

        // 1. Storage slots (0..53)
        for (int i = 0; i < 54; i++) {
            Slot slot = this.slots.get(i);
            if (i >= 27 && !storageUpgraded) {
                ((SlotAccessor) slot).setY(-2000);
            } else {
                ((SlotAccessor) slot).setX(8 + (i % 9) * 18);
                ((SlotAccessor) slot).setY(18 + (i / 9) * 18);
            }
        }

        // 2. Upgrade slots (54..59)
        for (int i = 0; i < 6; i++) {
            Slot slot = this.slots.get(54 + i);
            if (i >= 3 && !storageUpgraded) {
                ((SlotAccessor) slot).setY(-2000);
            } else {
                ((SlotAccessor) slot).setX(177);
                ((SlotAccessor) slot).setY(18 + i * 18);
            }
        }

        // 3. Crafting Grid & Result Slot
        int craftGridY = storageUpgraded ? 130 : 85;
        int craftResultY = storageUpgraded ? 148 : 103;

        Slot resultSlot = this.slots.get(60);
        ((SlotAccessor) resultSlot).setX(124);
        ((SlotAccessor) resultSlot).setY(craftingUpgraded ? craftResultY : -2000);

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                Slot craftSlot = this.slots.get(61 + row * 3 + col);
                if (craftingUpgraded) {
                    ((SlotAccessor) craftSlot).setX(30 + col * 18);
                    ((SlotAccessor) craftSlot).setY(craftGridY + row * 18);
                } else {
                    ((SlotAccessor) craftSlot).setY(-2000);
                }
            }
        }

        // 4. Player Main Inventory & Hotbar (70..105)
        int playerInvY;
        int hotbarY;

        if (!storageUpgraded && !craftingUpgraded) {
            playerInvY = 84;
            hotbarY = 142;
        } else if (storageUpgraded && !craftingUpgraded) {
            playerInvY = 140;
            hotbarY = 198;
        } else if (!storageUpgraded && craftingUpgraded) {
            playerInvY = 152;
            hotbarY = 210;
        } else { // Storage Upgraded + Crafting Upgraded
            playerInvY = 190;
            hotbarY = 248;
        }

        for (int i = 0; i < 36; i++) {
            Slot slot = this.slots.get(70 + i);
            int yPos = (i < 27) ? playerInvY + (i / 9) * 18 : hotbarY;
            ((SlotAccessor) slot).setX(8 + (i % 9) * 18);
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

            if (index < 54) {
                if (!this.moveItemStackTo(originalStack, 70, 106, true)) return ItemStack.EMPTY;
            } else if (index < 60) {
                if (!this.moveItemStackTo(originalStack, 70, 106, true)) return ItemStack.EMPTY;
            } else if (index < 70) {
                if (!this.moveItemStackTo(originalStack, 70, 106, false)) return ItemStack.EMPTY;
            } else {
                if (originalStack.is(ModItems.BACKPACK_ITEM)) return ItemStack.EMPTY;

                if (originalStack.is(ModItems.STORAGE_UPGRADE) ||
                        originalStack.is(ModItems.STACK_UPGRADE) ||
                        originalStack.is(ModItems.CRAFTING_UPGRADE)) {
                    if (!this.moveItemStackTo(originalStack, 54, 60, false)) return ItemStack.EMPTY;
                } else {
                    int maxAllowed = getDynamicMaxStackSize(originalStack);
                    int storageLimit = isStorageUpgraded() ? 54 : 27;

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
    public void removed(Player player) {
        super.removed(player);
        this.clearContainer(player, this.craftSlots);
    }

    @Override
    public boolean stillValid(@NotNull Player player) {
        return true;
    }
}