package com.chris.vanilla_expansion.screen;

import com.chris.vanilla_expansion.recipe.CorelessToolCraftingRecipe;
import com.chris.vanilla_expansion.recipe.ModRecipes;
import com.chris.vanilla_expansion.recipe.ToolCraftingRecipe;
import com.chris.vanilla_expansion.recipe.ToolCraftingRecipeInput;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.ResultContainer;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class ToolCraftingStationMenu extends AbstractContainerMenu {

    private final Container container;
    private final ResultContainer resultContainer = new ResultContainer();
    private final Level level;
    private final ContainerLevelAccess access;

    public ToolCraftingStationMenu(int containerId, Inventory playerInventory) {
        this(containerId, playerInventory, new SimpleContainer(3), ContainerLevelAccess.NULL);
    }

    public ToolCraftingStationMenu(int containerId, Inventory playerInventory, Container container) {
        this(containerId, playerInventory, container, ContainerLevelAccess.NULL);
    }

    public ToolCraftingStationMenu(int containerId, Inventory playerInventory, Container container, ContainerLevelAccess access) {
        super(ModMenus.TOOL_CRAFTING_STATION_MENU, containerId);
        checkContainerSize(container, 3);
        this.container = container;
        this.access = access;
        this.level = playerInventory.player.level();

        container.startOpen(playerInventory.player);

        // --- INPUT SLOTS WITH AUTOMATIC MENU NOTIFICATION ---
        // Slot 0: Tool Head
        this.addSlot(new Slot(container, 0, 44, 32) {
            @Override
            public void setChanged() {
                super.setChanged();
                ToolCraftingStationMenu.this.slotsChanged(container);
            }
        });

        // Slot 1: Handle
        this.addSlot(new Slot(container, 1, 80, 57) {
            @Override
            public void setChanged() {
                super.setChanged();
                ToolCraftingStationMenu.this.slotsChanged(container);
            }
        });

        // Slot 2: Element Core
        this.addSlot(new Slot(container, 2, 116, 32) {
            @Override
            public void setChanged() {
                super.setChanged();
                ToolCraftingStationMenu.this.slotsChanged(container);
            }
        });

        // --- RESULT SLOT (Index 3) ---
        this.addSlot(new Slot(this.resultContainer, 0, 80, 32) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return false;
            }

            @Override
            public void onTake(Player player, ItemStack stack) {
                // Shrink input items on recipe execution
                ToolCraftingStationMenu.this.container.getItem(0).shrink(1);
                ToolCraftingStationMenu.this.container.getItem(1).shrink(1);

                if (!ToolCraftingStationMenu.this.container.getItem(2).isEmpty()) {
                    ToolCraftingStationMenu.this.container.getItem(2).shrink(1);
                }

                // Fire block entity setChanged() and menu slotsChanged() update sequence
                ToolCraftingStationMenu.this.container.setChanged();
                ToolCraftingStationMenu.this.slotsChanged(ToolCraftingStationMenu.this.container);
                super.onTake(player, stack);
            }
        });

        // Add standard 36-slot player inventory layout (Hotbar + Main Inv)
        this.addStandardInventorySlots(playerInventory, 8, 84);

        // Perform initial match evaluation
        this.slotsChanged(this.container);
    }

    @Override
    public void slotsChanged(@NotNull Container container) {
        super.slotsChanged(container);

        if (this.level instanceof ServerLevel serverLevel) {
            ToolCraftingRecipeInput input = new ToolCraftingRecipeInput(
                    this.container.getItem(0),
                    this.container.getItem(1),
                    this.container.getItem(2)
            );

            RecipeManager recipeManager = (RecipeManager) serverLevel.recipeAccess();

            // 1. Coreless Tool Recipe
            Optional<RecipeHolder<CorelessToolCraftingRecipe>> corelessMatch = recipeManager
                    .getRecipeFor(ModRecipes.CORELESS_TOOL_CRAFTING_TYPE, input, serverLevel);

            if (corelessMatch.isPresent()) {
                this.resultContainer.setItem(0, corelessMatch.get().value().assemble(input));
                this.broadcastChanges();
                return;
            }

            // 2. Core Tool Recipe
            Optional<RecipeHolder<ToolCraftingRecipe>> coreMatch = recipeManager
                    .getRecipeFor(ModRecipes.TOOL_CRAFTING_TYPE, input, serverLevel);

            if (coreMatch.isPresent()) {
                this.resultContainer.setItem(0, coreMatch.get().value().assemble(input));
                this.broadcastChanges();
                return;
            }

            // Clear output if no match
            this.resultContainer.setItem(0, ItemStack.EMPTY);
            this.broadcastChanges();
        }
    }

    @Override
    public boolean stillValid(@NotNull Player player) {
        return this.container.stillValid(player);
    }

    @Override
    public void removed(@NotNull Player player) {
        super.removed(player);
        this.container.stopOpen(player);
    }

    @Override
    public @NotNull ItemStack quickMoveStack(@NotNull Player player, int index) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);

        if (slot != null && slot.hasItem()) {
            ItemStack slotStack = slot.getItem();
            itemStack = slotStack.copy();

            if (index == 3) { // Result Slot -> Player Inventory
                if (!this.moveItemStackTo(slotStack, 4, 40, true)) {
                    return ItemStack.EMPTY;
                }
                slot.onQuickCraft(slotStack, itemStack);
            } else if (index < 3) { // Station Inputs -> Player Inventory
                if (!this.moveItemStackTo(slotStack, 4, 40, false)) {
                    return ItemStack.EMPTY;
                }
            } else { // Player Inventory -> Station Inputs
                if (!this.moveItemStackTo(slotStack, 0, 3, false)) {
                    return ItemStack.EMPTY;
                }
            }

            if (slotStack.isEmpty()) {
                slot.setByPlayer(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (slotStack.getCount() == itemStack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(player, slotStack);
        }

        return itemStack;
    }
}