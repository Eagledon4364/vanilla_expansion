package com.chris.vanilla_expansion.block;

import com.chris.vanilla_expansion.block.entity.SandGeneratorBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.Nullable;

public class SandGeneratorBlock  extends Block implements EntityBlock {

    public SandGeneratorBlock(Properties properties) {
        super(properties);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(@NotNull BlockPos worldPosition, @NotNull BlockState blockState) {
        return new SandGeneratorBlockEntity(worldPosition, blockState);
    }
}
