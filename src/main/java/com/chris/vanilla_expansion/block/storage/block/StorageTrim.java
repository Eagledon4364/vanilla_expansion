package com.chris.vanilla_expansion.block.storage.block;

import com.chris.vanilla_expansion.block.storage.entity.StorageControllerBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.*;

public class StorageTrim extends Block {

    public StorageTrim(Properties properties) {
        super(properties);
    }

    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean isMoving) {
        super.onPlace(state, level, pos, oldState, isMoving);
        if (!level.isClientSide()) {
            notifyConnectedNetwork(level, pos);
        }
    }

    @Override
    public BlockState playerWillDestroy(Level level, BlockPos pos, BlockState state, net.minecraft.world.entity.player.Player player) {
        if (!level.isClientSide()) {
            notifyConnectedNetwork(level, pos);
        }
        return super.playerWillDestroy(level, pos, state, player);
    }

    /**
     * Finds any controller attached along this cable path and orders it to re-index the network.
     */
    private void notifyConnectedNetwork(Level level, BlockPos startPos) {
        Queue<BlockPos> queue = new ArrayDeque<>();
        Set<BlockPos> visited = new HashSet<>();

        queue.add(startPos);
        visited.add(startPos);

        // Scan up to 128 cable nodes to find connected controllers
        while (!queue.isEmpty() && visited.size() < 128) {
            BlockPos current = queue.poll();

            for (Direction direction : Direction.values()) {
                BlockPos neighbor = current.relative(direction);
                if (visited.contains(neighbor)) continue;

                if (level.getBlockEntity(neighbor) instanceof StorageControllerBlockEntity controller) {
                    controller.scanNetwork();
                    return; // Updated controller found
                }

                if (level.getBlockState(neighbor).getBlock() instanceof StorageTrim) {
                    visited.add(neighbor);
                    queue.add(neighbor);
                }
            }
        }
    }
}