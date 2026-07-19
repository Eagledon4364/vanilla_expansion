package com.chris.vanilla_expansion.storage_api.api;

import java.util.Collection;

/**
 *      Tracks connected nodes
 *      Builds network
 *      Provides access to the network
 */
public interface IStorageController {
    void addNode(IStorageNode node);

    void removeNode(IStorageNode node);

    Collection<IStorageNode> getNodes();

    IStorageNetwork getNetwork();

    void rebuildNetwork();
}
