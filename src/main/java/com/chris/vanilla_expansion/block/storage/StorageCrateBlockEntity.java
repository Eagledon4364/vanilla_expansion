package com.chris.vanilla_expansion.block.storage;

import com.chris.vanilla_expansion.block.ModBlockEntities;
import com.chris.vanilla_expansion.screen.storage.StorageCrateMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import com.mojang.serialization.Codec;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.util.ArrayList;
import java.util.List;

public class StorageCrateBlockEntity extends BlockEntity implements MenuProvider {

    public final SimpleContainer inventory = new SimpleContainer(32) {
        @Override
        public void setChanged() {
            refillVisibleSlot();
            super.setChanged();
            StorageCrateBlockEntity.this.setChanged();
        }

        private void refillVisibleSlot() {
            if (this.getItem(0).isEmpty()) {
                for (int i = 1; i < this.getContainerSize(); i++) {
                    ItemStack hiddenStack = this.getItem(i);
                    if (!hiddenStack.isEmpty()) {
                        this.items.set(0, hiddenStack.copy());
                        this.items.set(i, ItemStack.EMPTY);
                        break;
                    }
                }
            }
        }
    };

    public StorageCrateBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.STORAGE_CRATE_BE, pos, state);
    }

    @Override
    protected void saveAdditional(@NotNull ValueOutput output) {
        super.saveAdditional(output);
        List<ItemStack> toSave = new ArrayList<>();
        int total = 0;

        for (int i = 0; i < inventory.getContainerSize(); i++) {
            ItemStack stack = inventory.getItem(i);
            if (!stack.isEmpty()) {
                toSave.add(stack);
                total += stack.getCount();
            }
        }

        // Save the list of items
        output.store("Inventory", ItemStack.CODEC.listOf(), toSave);
        // Save the total as an integer to persist the 2048 count
        output.store("TrueCount", Codec.INT, total);
    }

    @Override
    protected void loadAdditional(@NotNull ValueInput input) {
        super.loadAdditional(input);
        this.inventory.clearContent();

        // Load items back into slots
        input.read("Inventory", ItemStack.CODEC.listOf()).ifPresent(list -> {
            for (int i = 0; i < Math.min(list.size(), 32); i++) {
                this.inventory.setItem(i, list.get(i).copy());
            }
        });

        // Apply the count override specifically for the client-side render
        if (this.level != null && this.level.isClientSide()) {
            input.read("TrueCount", Codec.INT).ifPresent(total -> {
                ItemStack main = this.inventory.getItem(0);
                if (!main.isEmpty()) {
                    main.setCount(total);
                }
            });
        }
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.translatable("container.vanilla_expansion.storage_crate");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int syncId, Inventory playerInventory, Player player) {
        return new StorageCrateMenu(syncId, playerInventory, this.inventory);
    }
}