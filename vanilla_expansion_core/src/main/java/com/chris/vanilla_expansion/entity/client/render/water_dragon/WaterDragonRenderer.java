package com.chris.vanilla_expansion.entity.client.render.water_dragon;

import com.chris.vanilla_expansion.VanillaExpansion;
import com.chris.vanilla_expansion.entity.client.ModEntityModelLayers;
import com.chris.vanilla_expansion.entity.client.model.dragon.WaterDragonModel;
import com.chris.vanilla_expansion.entity.server.DragonAnimal;
import com.chris.vanilla_expansion.entity.server.dragons.WaterDragonEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EquipmentSlot;
import org.jetbrains.annotations.NotNull;

public class WaterDragonRenderer extends MobRenderer<@NotNull WaterDragonEntity, @NotNull WaterDragonRenderState, @NotNull WaterDragonModel> {
    private static final Identifier TEXTURE = Identifier.fromNamespaceAndPath(VanillaExpansion.MOD_ID, "textures/entity/water_dragon.png");
    private static final Identifier SADDLED_TEXTURE = Identifier.fromNamespaceAndPath(VanillaExpansion.MOD_ID, "textures/entity/water_dragon_saddled.png");

    public WaterDragonRenderer(EntityRendererProvider.Context context) {
        super(context, new WaterDragonModel(context.bakeLayer(ModEntityModelLayers.WATER_DRAGON)), 0.5f);
    }

    @Override
    public WaterDragonRenderState createRenderState() {
        return new WaterDragonRenderState();
    }

    public void extractRenderState(final WaterDragonEntity entity, final WaterDragonRenderState state, final float partialTicks) {
        super.extractRenderState(entity, state, partialTicks);
        state.isBaby = entity.isBaby();
        state.isSleeping = entity.isSleeping();
        state.isSaddled = entity.isSaddled();
        state.saddle = entity.getItemBySlot(EquipmentSlot.SADDLE).copy();
        state.isRidden = entity.isVehicle();
        state.isSitting = entity.isOrderedToSit() || entity.getDragonState() == DragonAnimal.DragonState.SIT;


        state.idleAnimationState.copyFrom(entity.idleAnimationState);
        state.walkAnimationState.copyFrom(entity.walkAnimationState);
        state.sleepingAnimationState.copyFrom(entity.sleepingAnimationState);
        state.sitAnimationState.copyFrom(entity.sitAnimationState);
        state.meleeAnimationState.copyFrom(entity.meleeAnimationState);


    }

    @Override
    public @NotNull Identifier getTextureLocation(WaterDragonRenderState state) {
        if (state.isSaddled) {
            return SADDLED_TEXTURE;
        }
        return TEXTURE;
    }

}
