package com.chris.vanilla_expansion.block;

import com.chris.vanilla_expansion.block.entity.SandGeneratorBlockEntity;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.redstone.Orientation;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.Nullable;

public class SandGeneratorBlock extends BaseEntityBlock {
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;

    public SandGeneratorBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(
                this.stateDefinition.any().setValue(POWERED, false)
        );
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return simpleCodec(SandGeneratorBlock::new);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(@NotNull BlockPos worldPosition, @NotNull BlockState blockState) {
        return new SandGeneratorBlockEntity(worldPosition, blockState);
    }
    @Override
    protected @NotNull InteractionResult useItemOn(ItemStack stack,
                                                   BlockState state,
                                                   Level world,
                                                   BlockPos pos,
                                                   Player player,
                                                   InteractionHand hand,
                                                   BlockHitResult hit) {

        if (!(world.getBlockEntity(pos) instanceof SandGeneratorBlockEntity be))
            return InteractionResult.PASS;

        ItemStack held = player.getItemInHand(hand);

        if (held.isEmpty())
            return InteractionResult.SUCCESS;

        // -------------------------
        // Sand / Red Sand (Slot 0)
        // -------------------------

        if (held.is(Items.SAND) || held.is(Items.RED_SAND)) {

            ItemStack slot = be.getItem(0);

            if (slot.isEmpty()) {

                be.setItem(0, held.copyWithCount(1));
                held.shrink(1);
            }
            else if (ItemStack.isSameItemSameComponents(slot, held)
                    && slot.getCount() < slot.getMaxStackSize()) {

                slot.grow(1);
                held.shrink(1);
                be.setChanged();
            }

            return InteractionResult.SUCCESS;
        }

        // -------------------------
        // Gravel (Slot 1)
        // -------------------------

        if (held.is(Items.GRAVEL)) {

            ItemStack slot = be.getItem(1);

            if (slot.isEmpty()) {

                be.setItem(1, held.copyWithCount(1));
                held.shrink(1);
            }
            else if (ItemStack.isSameItemSameComponents(slot, held)
                    && slot.getCount() < slot.getMaxStackSize()) {

                slot.grow(1);
                held.shrink(1);
                be.setChanged();
            }

            return InteractionResult.SUCCESS;
        }

        return InteractionResult.SUCCESS;
    }
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(POWERED);
    }

    @Override
    protected void neighborChanged(BlockState state,
                                   Level level,
                                   BlockPos pos,
                                   Block block,
                                   @Nullable Orientation orientation,
                                   boolean movedByPiston) {
        if (level.isClientSide()) {
            return;
        }

        boolean powered = level.hasNeighborSignal(pos);

        if (powered != state.getValue(POWERED)) {
            level.setBlock(pos, state.setValue(POWERED, powered), Block.UPDATE_ALL);
        }
    }
    @Override
    protected void onPlace(BlockState state,
                           Level level,
                           BlockPos pos,
                           BlockState oldState,
                           boolean movedByPiston) {

        super.onPlace(state, level, pos, oldState, movedByPiston);

        if (!level.isClientSide()) {
            boolean powered = level.hasNeighborSignal(pos);

            if (powered != state.getValue(POWERED)) {
                level.setBlock(pos, state.setValue(POWERED, powered), Block.UPDATE_ALL);
            }
        }
    }
    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> type) {
        return createTickerHelper(type, ModBlockEntities.SANDGENERATOR_BE, SandGeneratorBlockEntity::tick);
    }
}
