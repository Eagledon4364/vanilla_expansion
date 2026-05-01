package com.chris.vanilla_expansion.block.storage;

import com.chris.vanilla_expansion.block.ModBlockEntities;
import com.chris.vanilla_expansion.block.ModBlocks;
import com.chris.vanilla_expansion.screen.storage.StorageAccessMenu;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.Nullable;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class StorageAccessBlock extends BaseEntityBlock {
    public static final MapCodec<StorageAccessBlock> CODEC = simpleCodec(StorageAccessBlock::new);
    public static final EnumProperty<@NotNull Direction> FACING = HorizontalDirectionalBlock.FACING;

    public StorageAccessBlock(BlockBehaviour.Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    @Override
    protected @NotNull MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    protected @NotNull InteractionResult useWithoutItem(@NotNull BlockState state,
                                                        @NotNull Level level,
                                                        @NotNull BlockPos pos,
                                                        @NotNull Player player,
                                                        @NotNull BlockHitResult hitResult) {

        if (!level.isClientSide()) {

            StorageControllerBlockEntity controller = findController(level, pos);

            if (controller != null) {
                BlockEntity be = level.getBlockEntity(pos);

                if (be instanceof StorageAccessBlockEntity access) {

                    access.setTargetController(controller.getBlockPos());

                    player.openMenu(new SimpleMenuProvider(
                            (id, inv, _) -> new StorageAccessMenu(id, inv, controller),
                            Component.literal("Storage")
                    ));
                }

            } else {
                player.sendSystemMessage(Component.literal("No Storage Controller found on network!"));
            }
        }

        return InteractionResult.SUCCESS;
    }

    private @Nullable StorageControllerBlockEntity findController(Level level, BlockPos startPos) {
        Queue<BlockPos> queue = new LinkedList<>();
        Set<BlockPos> visited = new HashSet<>();
        queue.add(startPos);
        visited.add(startPos);

        while (!queue.isEmpty()) {
            BlockPos current = queue.poll();
            for (Direction dir : Direction.values()) {
                BlockPos neighbor = current.relative(dir);
                if (visited.contains(neighbor)) continue;

                BlockEntity be = level.getBlockEntity(neighbor);
                BlockState state = level.getBlockState(neighbor);

                if (be instanceof StorageControllerBlockEntity controller) {
                    return controller;
                } else if (be instanceof StorageCrateBlockEntity || state.is(ModBlocks.STORAGE_CONNECT)) {
                    visited.add(neighbor);
                    queue.add(neighbor);
                }
            }
            if (visited.size() > 100) break;
        }
        return null;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return new StorageAccessBlockEntity(pos, state);
    }


    @Override
    public @NotNull RenderShape getRenderShape(@NotNull BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }


    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<@NotNull Block, @NotNull BlockState> builder) {
        builder.add(FACING);
    }
    @Override
    public <T extends BlockEntity> BlockEntityTicker<@NotNull T> getTicker(@NotNull Level level, @NotNull BlockState state, @NotNull BlockEntityType<@NotNull T> type) {
        return createTickerHelper(type, ModBlockEntities.STORAGE_CONTROLLER_BE, StorageControllerBlockEntity::tick);
    }
}