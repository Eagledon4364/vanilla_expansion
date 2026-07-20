package com.chris.vanilla_expansion.block.storage.entity;

import com.chris.vanilla_expansion.block.ModBlockEntities;
import com.chris.vanilla_expansion.block.ModBlocks;
import com.chris.vanilla_expansion.screen.storage.StorageInterfaceMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jspecify.annotations.Nullable;

import java.util.*;

public class StorageInterfaceBlockEntity extends BlockEntity implements MenuProvider {
    private BlockPos controllerPos = null;

    public StorageInterfaceBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.STORAGE_INTERFACE, pos, state);
    }
    /**
     * Searches out through neighboring trims to find and store the position
     * of the primary network StorageControllerBlockEntity.
     */
    public void findController() {
        if (this.level == null || this.level.isClientSide()) return;

        Queue<BlockPos> queue = new ArrayDeque<>();
        Set<BlockPos> visited = new HashSet<>();

        queue.add(this.worldPosition);
        visited.add(this.worldPosition);

        // Scan out through trim blocks up to 256 cabling nodes
        while (!queue.isEmpty() && visited.size() < 256) {
            BlockPos current = queue.poll();

            for (Direction dir : Direction.values()) {
                BlockPos neighbor = current.relative(dir);
                if (visited.contains(neighbor)) continue;

                // Case 1: Directly found the controller!
                if (level.getBlockEntity(neighbor) instanceof StorageControllerBlockEntity controller) {
                    this.controllerPos = neighbor;
                    this.setChanged();
                    controller.scanNetwork(); // Trigger network re-index
                    return;
                }

                // Case 2: Cable trim found — continue crawling along it
                if (level.getBlockState(neighbor).is(ModBlocks.STORAGE_TRIM)) {
                    visited.add(neighbor);
                    queue.add(neighbor);
                }
            }
        }

        // If BFS finished with no controller found, reset cached position
        this.controllerPos = null;
        this.setChanged();
    }

    /**
     * Clears the cached controller position. Call when this interface block is removed.
     */
    public void disconnect() {
        if (this.controllerPos != null && this.level != null) {
            if (level.getBlockEntity(this.controllerPos) instanceof StorageControllerBlockEntity controller) {
                // Request a rescan so the controller drops this interface from its set
                controller.scanNetwork();
            }
        }
        this.controllerPos = null;
        this.setChanged();
    }

    /**
     * Returns the cached controller instance if it still exists in the world.
     */
    public StorageControllerBlockEntity getController() {
        if (level == null) return null;

        // Check cached controller position first
        if (controllerPos != null && level.getBlockEntity(controllerPos) instanceof StorageControllerBlockEntity controller) {
            return controller;
        }

        // If lost, locate nearest connected controller via adjacent Trims
        for (Direction dir : Direction.values()) {
            BlockPos neighbor = worldPosition.relative(dir);
            if (level.getBlockEntity(neighbor) instanceof StorageControllerBlockEntity controller) {
                this.controllerPos = neighbor;
                return controller;
            }
        }
        return null;
    }

    public List<ItemStack> getAvailableItems() {
        StorageControllerBlockEntity controller = getController();
        if (controller != null) {
            return controller.getNetworkItems();
        }
        return Collections.emptyList();
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Storage Interface");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int containerId, Inventory inventory, Player player) {
        return new StorageInterfaceMenu(containerId, inventory, this.worldPosition, this);
    }
}