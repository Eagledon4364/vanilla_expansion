package com.chris.vanilla_expansion.util.api;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

public interface StorageNode {
    StorageNetwork getNetwork();
    void setNetwork(StorageNetwork network);

    BlockPos getPos();
    Level getLevel();
}