package com.chris.vanilla_expansion.util.api;

import net.minecraft.core.BlockPos;

public interface NetworkUpdater {
    void markDirty(BlockPos pos);
    void rebuildIfNeeded();
}