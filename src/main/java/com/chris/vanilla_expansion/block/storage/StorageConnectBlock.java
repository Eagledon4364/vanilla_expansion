package com.chris.vanilla_expansion.block.storage;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.jetbrains.annotations.NotNull;

public class StorageConnectBlock extends Block {
    public static final MapCodec<StorageConnectBlock> CODEC = simpleCodec(StorageConnectBlock::new);

    public StorageConnectBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Override
    protected @NotNull MapCodec<? extends Block> codec() {
        return CODEC;
    }
}