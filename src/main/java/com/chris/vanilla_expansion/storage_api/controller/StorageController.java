package com.chris.vanilla_expansion.storage_api.controller;

import com.chris.vanilla_expansion.storage_api.api.IStorageController;
import com.chris.vanilla_expansion.storage_api.api.IStorageNetwork;
import com.chris.vanilla_expansion.storage_api.api.IStorageNode;
import com.chris.vanilla_expansion.storage_api.network.StorageNetwork;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Owns network
 * handles rescans
 * MAKE THIS INTO A BLOCK ENTITY AT BLOCK/STORAGE/ENTITY
 */
public class StorageController implements IStorageController {
    StorageNetwork network;
    Set<IStorageNode> nodes;
    public void tick() {

    }

    @Override
    public void addNode(IStorageNode node) {

    }

    @Override
    public void removeNode(IStorageNode node) {

    }

    @Override
    public Collection<IStorageNode> getNodes() {
        return List.of();
    }

    @Override
    public IStorageNetwork getNetwork() {
        return null;
    }

    @Override
    public void rebuildNetwork() {

    }
}
