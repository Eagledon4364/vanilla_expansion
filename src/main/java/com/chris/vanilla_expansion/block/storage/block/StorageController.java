package com.chris.vanilla_expansion.block.storage.block;

import com.chris.vanilla_expansion.block.storage.entity.StorageControllerBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class StorageController extends Block implements EntityBlock {

    public StorageController(Properties properties) {
        super(properties);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new StorageControllerBlockEntity(pos, state);
    }

    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean isMoving) {
        super.onPlace(state, level, pos, oldState, isMoving);
        if (!level.isClientSide() && level.getBlockEntity(pos) instanceof StorageControllerBlockEntity controller) {
            controller.scanNetwork();
        }
    }

    @Override
    public BlockState playerWillDestroy(Level level, BlockPos pos, BlockState state, net.minecraft.world.entity.player.Player player) {
        if (!level.isClientSide() && level.getBlockEntity(pos) instanceof StorageControllerBlockEntity controller) {
            controller.clearNetwork();
        }
        return super.playerWillDestroy(level, pos, state, player);
    }
}