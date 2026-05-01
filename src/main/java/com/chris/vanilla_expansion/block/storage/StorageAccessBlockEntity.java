package com.chris.vanilla_expansion.block.storage;

import com.chris.vanilla_expansion.block.ModBlockEntities;
import com.chris.vanilla_expansion.screen.storage.StorageAccessMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.Nullable;

public class StorageAccessBlockEntity extends BlockEntity implements MenuProvider {

    private BlockPos controllerPos;

    public StorageAccessBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.STORAGE_ACCESS_BE, pos, state);
    }

    public void setTargetController(BlockPos pos) {
        this.controllerPos = pos;
    }

    public @Nullable StorageControllerBlockEntity getController() {
        if (level != null && controllerPos != null) {
            BlockEntity be = level.getBlockEntity(controllerPos);
            if (be instanceof StorageControllerBlockEntity controller) {
                return controller;
            }
        }
        return null;
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.literal("Storage System Access");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int containerId, @NotNull Inventory inventory, @NotNull Player player) {
        StorageControllerBlockEntity controller = getController();
        if (controller != null) {
            return new StorageAccessMenu(containerId, inventory, controller);
        }
        return null;
    }
    public BlockPos getControllerPos() {
        return controllerPos;
    }
}