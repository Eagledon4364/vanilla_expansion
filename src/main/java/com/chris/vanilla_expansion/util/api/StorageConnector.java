package com.chris.vanilla_expansion.util.api;

import net.minecraft.core.Direction;

import java.util.Set;

public interface StorageConnector extends StorageNode {
    Set<Direction> getConnections();
}