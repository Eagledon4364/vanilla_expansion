package com.chris.vanilla_expansion.entity.client.render.energy_dragon;

import com.chris.vanilla_expansion.VanillaExpansion;
import com.chris.vanilla_expansion.entity.client.ModEntityModelLayers;
import com.chris.vanilla_expansion.entity.client.model.dragon.EnergyDragonModel;
import com.chris.vanilla_expansion.entity.server.DragonAnimal;
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

        if (state.isFlying) {
            state.yRot = entity.getYRot();
        }

    }

    @Override
    public @NotNull Identifier getTextureLocation(EnergyDragonRenderState state) {
        if (state.isSaddled) {
            return SADDLED_TEXTURE;
        }
        return TEXTURE;
    }

}
