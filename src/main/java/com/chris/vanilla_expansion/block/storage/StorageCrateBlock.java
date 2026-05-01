package com.chris.vanilla_expansion.block.storage;


import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.Nullable;

import java.util.List;

public class StorageCrateBlock extends BaseEntityBlock {
    public static final EnumProperty<@NotNull Direction> FACING = HorizontalDirectionalBlock.FACING;
    public static final MapCodec<StorageCrateBlock> CODEC = simpleCodec(StorageCrateBlock::new);

    public StorageCrateBlock(BlockBehaviour.Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    @Override
    protected @NotNull MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
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
    public @NotNull List<ItemStack> getDrops(@NotNull BlockState state, LootParams.Builder builder) {
        BlockEntity be = builder.getOptionalParameter(LootContextParams.BLOCK_ENTITY);

        if (be instanceof StorageCrateBlockEntity crate) {
            ItemStack itemStack = new ItemStack(this);
            itemStack.set(DataComponents.CONTAINER,
                    ItemContainerContents.fromItems(crate.getItems()));

            return List.of(itemStack);
        }

        return super.getDrops(state, builder);
    }
    @Override
    protected boolean hasAnalogOutputSignal(@NotNull BlockState state) {
        return true;
    }

    @Override
    protected int getAnalogOutputSignal(@NotNull BlockState state, Level level, @NotNull BlockPos pos, @NotNull Direction direction) {
        return AbstractContainerMenu.getRedstoneSignalFromBlockEntity(level.getBlockEntity(pos));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<@NotNull Block, @NotNull BlockState> builder) {
        builder.add(FACING);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return new StorageCrateBlockEntity(pos, state);
    }

    @Override
    protected @NotNull InteractionResult useWithoutItem(@NotNull BlockState state,
                                                        @NotNull Level level, @NotNull BlockPos pos,
                                                        @NotNull Player player, @NotNull BlockHitResult hitResult) {
        if (hitResult.getDirection() != state.getValue(FACING)) {
            return InteractionResult.PASS;
        }
        if (!level.isClientSide()) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof StorageCrateBlockEntity crate) {
                player.openMenu(crate);
            }
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    protected @NotNull InteractionResult useItemOn(@NotNull ItemStack itemStack,
                                                   BlockState state, @NotNull Level level,
                                                   @NotNull BlockPos pos, @NotNull Player player,
                                                   @NotNull InteractionHand hand, BlockHitResult hitResult) {
        if (hitResult.getDirection() != state.getValue(FACING)) {
            return InteractionResult.PASS;
        }
        if (itemStack.getItem() instanceof BlockItem && player.isSecondaryUseActive()) {
            return InteractionResult.PASS;
        }

        return this.useWithoutItem(state, level, pos, player, hitResult);
    }
}