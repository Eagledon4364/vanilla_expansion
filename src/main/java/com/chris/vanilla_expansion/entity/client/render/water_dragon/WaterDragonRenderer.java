package com.chris.vanilla_expansion.entity.client.render.water_dragon;

import com.chris.vanilla_expansion.VanillaExpansion;
import com.chris.vanilla_expansion.entity.client.ModEntityModelLayers;
import com.chris.vanilla_expansion.entity.client.model.dragon.EnergyDragonModel;
import com.chris.vanilla_expansion.entity.client.model.dragon.WaterDragonModel;
import com.chris.vanilla_expansion.entity.client.render.energy_dragon.EnergyDragonRenderState;
import com.chris.vanilla_expansion.entity.server.DragonAnimal;
import com.chris.vanilla_expansion.entity.server.dragons.EnergyDragonEntity;
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
        super(context, new WaterDragonModel(context.bakeLayer(ModEntityModelLayers.WATER_DRAGON)), 1f);
    }

    @Override
    public WaterDragonRenderState createRenderState() {
        return new WaterDragonRenderState();
    }

    public void extractRenderState(final WaterDragonEntity entity, final WaterDragonRenderState state, final float partialTicks) {
        super.extractRenderState(entity, state, partialTicks);
        state.isBaby = entity.isBaby();

        state.isFlying = entity.isFlying();
        state.xRot = entity.getXRot();
        state.dragonPitch = entity.getDragonPitch();

        state.isSleeping = entity.isSleeping();
        state.isSaddled = entity.isSaddled();
        state.saddle = entity.getItemBySlot(EquipmentSlot.SADDLE).copy();
        state.isRidden = entity.isVehicle();
        state.isSitting = entity.isOrderedToSit() || entity.getDragonState() == DragonAnimal.DragonState.SIT;


        state.idleAnimationState.copyFrom(entity.idleAnimationState);
        state.walkAnimationState.copyFrom(entity.walkAnimationState);
        state.hoverAnimationState.copyFrom(entity.hoverAnimationState);
        state.flyAnimationState.copyFrom(entity.flyAnimationState);
        state.sleepingAnimationState.copyFrom(entity.sleepingAnimationState);
        state.sitAnimationState.copyFrom(entity.sitAnimationState);
        state.meleeAnimationState.copyFrom(entity.meleeAnimationState);
        state.fireAnimationState.copyFrom(entity.fireAnimationState);

        if (state.isFlying) {
            state.yRot = entity.getYRot();
        }

    }

    @Override
    public @NotNull Identifier getTextureLocation(WaterDragonRenderState state) {
        if (state.isSaddled) {
            return SADDLED_TEXTURE;
        }
        return TEXTURE;
    }

}
