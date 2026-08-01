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
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jspecify.annotations.Nullable;

import java.util.*;

public class StorageInterfaceBlockEntity extends BlockEntity implements MenuProvider {
    private BlockPos controllerPos = null;
    private StorageInterfaceMenu.SortMode sortMode = StorageInterfaceMenu.SortMode.COUNT;

    public StorageInterfaceBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.STORAGE_INTERFACE, pos, state);
    }

    public StorageInterfaceMenu.SortMode getSortMode() {
        return this.sortMode;
    }

    public void setSortMode(StorageInterfaceMenu.SortMode mode) {
    //    System.out.println("[DEBUG-BE] Setting SortMode on BE at " + this.worldPosition + " to: " + mode);
        this.sortMode = mode;
        this.setChanged();
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);
        if (this.controllerPos != null) {
            output.store("ControllerPos", BlockPos.CODEC, this.controllerPos);
        }
    //    System.out.println("[DEBUG-BE] Saving NBT SortMode: " + this.sortMode.ordinal() + " (" + this.sortMode + ")");
        output.putInt("SortMode", this.sortMode.ordinal());
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        this.controllerPos = input.read("ControllerPos", BlockPos.CODEC).orElse(null);
        int sortOrdinal = input.getIntOr("SortMode", 0);
        this.sortMode = StorageInterfaceMenu.SortMode.fromOrdinal(sortOrdinal);
    //    System.out.println("[DEBUG-BE] Loaded NBT SortMode: " + sortOrdinal + " -> " + this.sortMode);
    }

    public void findController() {
        if (this.level == null || this.level.isClientSide()) return;

        Queue<BlockPos> queue = new ArrayDeque<>();
        Set<BlockPos> visited = new HashSet<>();

        queue.add(this.worldPosition);
        visited.add(this.worldPosition);

        while (!queue.isEmpty() && visited.size() < 256) {
            BlockPos current = queue.poll();

            for (Direction dir : Direction.values()) {
                BlockPos neighbor = current.relative(dir);
                if (visited.contains(neighbor)) continue;

                if (level.getBlockEntity(neighbor) instanceof StorageControllerBlockEntity controller) {
                    this.controllerPos = neighbor;
                    this.setChanged();
                    controller.scanNetwork();
                    return;
                }

                if (level.getBlockState(neighbor).is(ModBlocks.STORAGE_TRIM)) {
                    visited.add(neighbor);
                    queue.add(neighbor);
                }
            }
        }

        this.controllerPos = null;
        this.setChanged();
    }

    public void disconnect() {
        if (this.controllerPos != null && this.level != null) {
            if (level.getBlockEntity(this.controllerPos) instanceof StorageControllerBlockEntity controller) {
                controller.scanNetwork();
            }
        }
        this.controllerPos = null;
        this.setChanged();
    }

    public StorageControllerBlockEntity getController() {
        if (level == null || level.isClientSide()) return null;

        if (controllerPos != null && level.isLoaded(controllerPos) && level.getBlockEntity(controllerPos) instanceof StorageControllerBlockEntity controller) {
            return controller;
        }

        findController();

        if (controllerPos != null && level.getBlockEntity(controllerPos) instanceof StorageControllerBlockEntity controller) {
            return controller;
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
    //    System.out.println("[DEBUG-BE] Opening Menu for player " + player.getName().getString() + " with SortMode: " + this.sortMode);
        return new StorageInterfaceMenu(containerId, inventory, this.worldPosition, this);
    }
}