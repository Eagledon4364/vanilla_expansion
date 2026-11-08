package com.chris.vanilla_expansion.entity.client.render.air_dragon;

import com.chris.vanilla_expansion.VanillaExpansion;
import com.chris.vanilla_expansion.entity.client.ModEntityModelLayers;
import com.chris.vanilla_expansion.entity.client.model.dragon.AirDragonModel;
import com.chris.vanilla_expansion.entity.server.DragonAnimal;
import com.chris.vanilla_expansion.entity.server.dragons.AirDragonEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EquipmentSlot;
import org.jetbrains.annotations.NotNull;

public class AirDragonRenderer extends MobRenderer<@NotNull AirDragonEntity, @NotNull AirDragonRenderState, @NotNull AirDragonModel> {

    private static final Identifier TEXTURE = Identifier.fromNamespaceAndPath(VanillaExpansion.MOD_ID, "textures/entity/air_dragon.png");
    private static final Identifier SADDLED_TEXTURE = Identifier.fromNamespaceAndPath(VanillaExpansion.MOD_ID, "textures/entity/air_dragon_saddled.png");

    public AirDragonRenderer(EntityRendererProvider.Context context) {
        super(context, new AirDragonModel(context.bakeLayer(ModEntityModelLayers.AIR_DRAGON)), 1f);
    }

    @Override
    public AirDragonRenderState createRenderState() {
        return new AirDragonRenderState();
    }
    public void extractRenderState(final AirDragonEntity entity, final AirDragonRenderState state, final float partialTicks) {
        super.extractRenderState(entity, state, partialTicks);
        state.isBaby = entity.isBaby();
        state.isFlying = entity.isFlying();
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
    public @NotNull Identifier getTextureLocation(AirDragonRenderState state) {
        if (state.isSaddled) {
            return SADDLED_TEXTURE;
        }
        return TEXTURE;
    }

}