package com.chris.vanilla_expansion.entity.client.model;

import com.chris.vanilla_expansion.VanillaExpansion;
import com.chris.vanilla_expansion.entity.client.ModEntityModelLayers;
import com.chris.vanilla_expansion.entity.server.dragons.EnergyDragonEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EquipmentSlot;
import org.jetbrains.annotations.NotNull;

public class EnergyDragonRenderer extends MobRenderer<@NotNull EnergyDragonEntity, @NotNull EnergyDragonRenderState, @NotNull EnergyDragonModel> {

    private static final Identifier TEXTURE = Identifier.fromNamespaceAndPath(VanillaExpansion.MOD_ID, "textures/entity/energy_dragon.png");
    private static final Identifier SADDLED_TEXTURE = Identifier.fromNamespaceAndPath(VanillaExpansion.MOD_ID, "textures/entity/energy_dragon_saddled.png");

    public EnergyDragonRenderer(EntityRendererProvider.Context context) {
        super(context, new EnergyDragonModel(context.bakeLayer(ModEntityModelLayers.ENERGY_DRAGON)), 1f);
    }

    @Override
    public EnergyDragonRenderState createRenderState() {
        return new EnergyDragonRenderState();
    }
    public void extractRenderState(final EnergyDragonEntity entity, final EnergyDragonRenderState state, final float partialTicks) {
        super.extractRenderState(entity, state, partialTicks);
        state.isBaby = entity.isBaby();
        extractAdditionalState(entity, state, partialTicks);
        state.isFlying = entity.isFlying();
        state.xRot = entity.getXRot();
        state.isSleeping = entity.isSleeping(); // This tells the model to play the animation
        state.isSaddled = entity.isSaddled();

        if (state.isSleeping) {
            state.sleepingAnimationState.startIfStopped(entity.tickCount);
        } else {
            state.sleepingAnimationState.stop();
        }

        if (state.isFlying) {
            state.yRot = entity.getYRot();
        }
    }

    static void extractAdditionalState(EnergyDragonEntity entity, EnergyDragonRenderState state, float partialTicks) {
        state.saddle = entity.getItemBySlot(EquipmentSlot.SADDLE).copy();
        state.isRidden = entity.isVehicle();
        state.idleAnimationState.copyFrom(entity.idleAnimationState);
        state.walkAnimationState.copyFrom(entity.walkAnimationState);
        state.blinkAnimationState.copyFrom(entity.blinkAnimationState);
        state.hoverAnimationState.copyFrom(entity.hoverAnimationState);
        state.flyAnimationState.copyFrom(entity.flyAnimationState);
        state.sleepingAnimationState.copyFrom(entity.sleepingAnimationState);

    }
    @Override
    public @NotNull Identifier getTextureLocation(EnergyDragonRenderState state) {
        if (state.isSaddled) {
            return SADDLED_TEXTURE;
        }
        return TEXTURE;
    }

}
