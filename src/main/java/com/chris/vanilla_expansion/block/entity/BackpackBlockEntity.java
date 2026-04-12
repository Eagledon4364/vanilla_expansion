package com.chris.vanilla_expansion.block.entity;

import com.chris.vanilla_expansion.block.ModBlockEntities;
import com.chris.vanilla_expansion.screen.backpack.BackpackMenu;
import com.chris.vanilla_expansion.component.ModComponents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BackpackBlockEntity extends BlockEntity implements MenuProvider {

    public final SimpleContainer mainInventory = new SimpleContainer(54) {
        @Override
        public int getMaxStackSize() {
            return 1024;
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
        this.mainInventory.clearContent();
        ItemContainerContents mainContents = stack.get(DataComponents.CONTAINER);
        if (mainContents != null) {
            mainContents.copyInto(this.mainInventory.getItems());
        }
        this.upgradeInventory.clearContent();
        ItemContainerContents upgradeContents = stack.get(ModComponents.UPGRADE_DATA);
        if (upgradeContents != null) {
            upgradeContents.copyInto(this.upgradeInventory.getItems());
        }

        this.setChanged();
    }

    @Override
    protected void loadAdditional(@NotNull ValueInput input) {
        super.loadAdditional(input);

        ContainerHelper.loadAllItems(input, this.mainInventory.getItems());

        input.child("Upgrades").ifPresent(this::loadUpgradeItems);
    }

    private void loadUpgradeItems(ValueInput upgradeInput) {
        ContainerHelper.loadAllItems(upgradeInput, this.upgradeInventory.getItems());
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
        return Component.translatable("container.vanilla_expansion.backpack");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int syncId, @NotNull Inventory playerInventory, @NotNull Player player) {
        return new BackpackMenu(syncId, playerInventory, this.mainInventory, this.upgradeInventory);
    }
}