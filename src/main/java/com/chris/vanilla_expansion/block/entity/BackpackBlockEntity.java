package com.chris.vanilla_expansion.block.entity;

import com.chris.vanilla_expansion.VanillaExpansion;
import com.chris.vanilla_expansion.block.ModBlockEntities;
import com.chris.vanilla_expansion.component.BackpackStorageHelper;
import com.chris.vanilla_expansion.item.ModItems;
import com.chris.vanilla_expansion.screen.backpack.BackpackMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.stream.IntStream;

public class BackpackBlockEntity extends RandomizableContainerBlockEntity implements WorldlyContainer {

    private static final int[] SLOTS = IntStream.range(0, 54).toArray();
    private static final Component DEFAULT_NAME = Component.translatable("container.vanilla_expansion.backpack");

    public final SimpleContainer mainInventory = new SimpleContainer(54) {
        @Override
        public int getMaxStackSize() {
            return VanillaExpansion.MAX_STACK_SIZE;
        }

        @Override
        public void setChanged() {
            super.setChanged();
            BackpackBlockEntity.this.setChanged();
        }
    };

    public final SimpleContainer upgradeInventory = new SimpleContainer(6) {
        @Override
        public void setChanged() {
            super.setChanged();
            BackpackBlockEntity.this.mainInventory.setChanged();
            BackpackBlockEntity.this.setChanged();
        }
    };

    public BackpackBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.BACKPACK_BLOCK_ENTITY, pos, state);
    }

    public void loadFromItemStack(ItemStack stack) {
        BackpackStorageHelper.loadFromStack(stack, this.mainInventory, this.upgradeInventory);
        this.setChanged();
    }

    @Override
    protected void loadAdditional(@NotNull ValueInput input) {
        super.loadAdditional(input);
        ContainerHelper.loadAllItems(input, this.mainInventory.getItems());
        input.child("Upgrades").ifPresent(upgradeInput ->
                ContainerHelper.loadAllItems(upgradeInput, this.upgradeInventory.getItems())
        );
    }

    @Override
    protected void saveAdditional(@NotNull ValueOutput output) {
        ContainerHelper.saveAllItems(output, this.mainInventory.getItems());
        ValueOutput upgradeOutput = output.child("Upgrades");
        ContainerHelper.saveAllItems(upgradeOutput, this.upgradeInventory.getItems());
        super.saveAdditional(output);
    }

    @Override
    public @NotNull Component getDisplayName() {
        return DEFAULT_NAME;
    }

    @Override
    protected Component getDefaultName() {
        return DEFAULT_NAME;
    }

    @Override
    protected NonNullList<ItemStack> getItems() {
        return this.mainInventory.getItems();
    }

    @Override
    protected void setItems(NonNullList<ItemStack> items) {
        for (int i = 0; i < items.size() && i < this.mainInventory.getContainerSize(); i++) {
            this.mainInventory.setItem(i, items.get(i));
        }
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int syncId, @NotNull Inventory playerInventory, @NotNull Player player) {
        return new BackpackMenu(syncId, playerInventory, this.mainInventory, this.upgradeInventory);
    }

    @Override
    protected AbstractContainerMenu createMenu(int containerId, Inventory inventory) {
        return new BackpackMenu(containerId, inventory, this.mainInventory, this.upgradeInventory);
    }

    @Override
    public int getContainerSize() {
        return this.mainInventory.getContainerSize();
    }

    @Override
    public boolean isEmpty() {
        return this.mainInventory.isEmpty();
    }

    @Override
    public @NotNull ItemStack getItem(int slot) {
        return this.mainInventory.getItem(slot);
    }

    @Override
    public @NotNull ItemStack removeItem(int slot, int amount) {
        ItemStack stack = this.mainInventory.removeItem(slot, amount);
        this.setChanged();
        return stack;
    }

    @Override
    public @NotNull ItemStack removeItemNoUpdate(int slot) {
        ItemStack stack = this.mainInventory.removeItemNoUpdate(slot);
        this.setChanged();
        return stack;
    }

    @Override
    public void setItem(int slot, @NotNull ItemStack stack) {
        this.mainInventory.setItem(slot, stack);
        this.setChanged();
    }

    @Override
    public boolean stillValid(@NotNull Player player) {
        return this.mainInventory.stillValid(player);
    }

    @Override
    public void clearContent() {
        this.mainInventory.clearContent();
    }

    @Override
    public int getMaxStackSize() {
        return this.mainInventory.getMaxStackSize();
    }

    @Override
    public int[] getSlotsForFace(Direction direction) {
        return SLOTS;
    }

    @Override
    public boolean canPlaceItemThroughFace(int slot, ItemStack itemStack, @org.jspecify.annotations.Nullable Direction direction) {
        return !itemStack.is(ModItems.BACKPACK_ITEM);
    }

    @Override
    public boolean canTakeItemThroughFace(int slot, ItemStack itemStack, Direction direction) {
        return true;
    }
}