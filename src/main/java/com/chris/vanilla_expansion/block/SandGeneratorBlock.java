package com.chris.vanilla_expansion.block;

import com.chris.vanilla_expansion.block.entity.SandGeneratorBlockEntity;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.Nullable;

public class SandGeneratorBlock extends BaseEntityBlock {

    public SandGeneratorBlock(Properties properties) {
        super(properties);
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
    protected InteractionResult useItemOn(ItemStack stack, BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (!(world.getBlockEntity(pos) instanceof SandGeneratorBlockEntity sandGeneratorBlockEntity)) {
            return InteractionResult.PASS;
        }

        if (!player.getItemInHand(hand).isEmpty() && sandGeneratorBlockEntity.isEmpty()) {
            sandGeneratorBlockEntity.setItem(0, player.getItemInHand(hand).copy());
            player.getItemInHand(hand).setCount(0);
        }

        return InteractionResult.SUCCESS;
    }
}
