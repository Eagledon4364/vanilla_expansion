package com.chris.vanilla_expansion.entity.client;

import com.chris.vanilla_expansion.VanillaExpansion;
import com.chris.vanilla_expansion.entity.client.model.AirDragonModel;
import com.chris.vanilla_expansion.entity.client.model.EnergyDragonModel;
import net.fabricmc.fabric.api.client.rendering.v1.ModelLayerRegistry;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.Identifier;

public class ModEntityModelLayers {
    public static final ModelLayerLocation ENERGY_DRAGON = createMain("energy_dragon");
public static final ModelLayerLocation AIR_DRAGON = createMain("air_dragon");

    private static ModelLayerLocation createMain(String name) {
        return new ModelLayerLocation(Identifier.fromNamespaceAndPath(VanillaExpansion.MOD_ID, name), "main");
    }

    public static void registerModelLayers() {
        ModelLayerRegistry.registerModelLayer(ModEntityModelLayers.ENERGY_DRAGON, EnergyDragonModel::getTextureModelData);
        ModelLayerRegistry.registerModelLayer(ModEntityModelLayers.AIR_DRAGON, AirDragonModel::getTextureModelData);
    }
}