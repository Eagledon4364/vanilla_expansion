package com.chris.vanilla_expansion.block.storage;

import com.chris.vanilla_expansion.block.ModBlockEntities;
import com.chris.vanilla_expansion.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class StorageControllerBlockEntity extends BlockEntity {
    private StorageNetwork network = new StorageNetwork();
    private int scanTimer = 0;

    public StorageControllerBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.STORAGE_CONTROLLER_BE, pos, state);
    }

    public void scanNetwork() {
        if (this.level == null || this.level.isClientSide()) return;

        this.network = new StorageNetwork();

        Queue<BlockPos> queue = new LinkedList<>();
        Set<BlockPos> visited = new HashSet<>();

        queue.add(this.worldPosition);
        visited.add(this.worldPosition);

        while (!queue.isEmpty()) {
            BlockPos current = queue.poll();

            for (Direction dir : Direction.values()) {
                BlockPos neighborPos = current.relative(dir);
                if (!visited.add(neighborPos)) continue;

                BlockEntity be = level.getBlockEntity(neighborPos);
                BlockState state = level.getBlockState(neighborPos);

                if (be instanceof StorageCrateBlockEntity crate) {
                    network.addUnit(crate);
                    queue.add(neighborPos);
                }
                else if (state.is(ModBlocks.STORAGE_CONNECT)) {
                    queue.add(neighborPos);
                }
            }
        }

        setChanged();
    }

    public static void tick(Level level, BlockPos pos, BlockState state, StorageControllerBlockEntity be) {
        if (level.isClientSide()) return;

        be.scanTimer++;
        if (be.scanTimer >= 100) {
            be.scanNetwork();
            be.scanTimer = 0;
        }
    }

    public ItemStack insertItem(ItemStack stack) {
        return network.insert(stack);
    }

    public ItemStack extractItem(ItemStack filter, int amount) {
        return network.extract(filter, amount);
    }


    @Override
    protected void loadAdditional(@NotNull ValueInput input) {
        super.loadAdditional(input);
    }

    @Override
    protected void saveAdditional(@NotNull ValueOutput output) {
        super.saveAdditional(output);
    }
    public StorageNetwork getNetwork() {
        return this.network;
    }
}