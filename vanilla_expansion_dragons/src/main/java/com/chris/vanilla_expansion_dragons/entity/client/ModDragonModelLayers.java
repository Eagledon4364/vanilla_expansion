package com.chris.vanilla_expansion_dragons.entity.client;

import com.chris.vanilla_expansion_dragons.VanillaExpansionDragons;
import com.chris.vanilla_expansion_dragons.entity.client.model.ElderWingDragonModel;
import net.fabricmc.fabric.api.client.rendering.v1.ModelLayerRegistry;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.Identifier;

public class ModDragonModelLayers {
    public static final ModelLayerLocation ELDERWING_DRAGON = createMain("elderwing_dragon");

    private static ModelLayerLocation createMain(String name) {
        return new ModelLayerLocation(Identifier.fromNamespaceAndPath(VanillaExpansionDragons.MOD_ID, name), "main");
    }

    public static void registerModelLayers() {
        ModelLayerRegistry.registerModelLayer(ModDragonModelLayers.ELDERWING_DRAGON, ElderWingDragonModel::createBodyLayer);
    }
}