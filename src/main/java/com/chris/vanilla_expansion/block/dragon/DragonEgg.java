package com.chris.vanilla_expansion.block.dragon;

import com.chris.vanilla_expansion.entity.server.DragonAnimal;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class DragonEgg extends BaseEntityBlock {

    protected static final int MAXTIME = 24000;
    protected static final int MINTIME = 16800;
    protected static final VoxelShape EGG_SHAPE = Block.box(4.0D, 0.0D, 4.0D, 12.0D, 10.0D, 12.0D);

    public DragonEgg(Properties properties) {
        super(properties.noOcclusion());
    }

    @Override
    protected @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return EGG_SHAPE;
    }

    @Override
    protected @NotNull RenderShape getRenderShape(@NotNull BlockState state) {
        return RenderShape.MODEL;
    }

    public abstract EntityType<? extends DragonAnimal> getDragonType();
    public abstract BlockEntityType<? extends DragonEggBlockEntity> getBlockEntityType();

    @Override
    public @Nullable BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        int initialTime = MINTIME + RandomSource.create().nextInt(MAXTIME - MINTIME);
        return new DragonEggBlockEntity(getBlockEntityType(), pos, state, getDragonType(), initialTime);
    }

    @Override
    public void setPlacedBy(@NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState state, @Nullable LivingEntity placer, @NotNull ItemStack stack) {
        super.setPlacedBy(level, pos, state, placer, stack);
        if (!level.isClientSide() && placer instanceof Player player && level.getBlockEntity(pos) instanceof DragonEggBlockEntity eggBe) {
            eggBe.setOwnerUuid(player.getUUID());
        }
    }

    @Override
    protected @NotNull InteractionResult useWithoutItem(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull BlockHitResult hitResult) {
        if (!level.isClientSide() && level.getBlockEntity(pos) instanceof DragonEggBlockEntity blockEntity) {
            blockEntity.showTimeToPlayer(player);
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> blockEntityType) {
        return level.isClientSide() ? null : (lvl, pos, st, be) -> {
            if (be instanceof DragonEggBlockEntity eggBe) {
                DragonEggBlockEntity.serverTick(lvl, pos, st, eggBe);
            }
        };
    }
}