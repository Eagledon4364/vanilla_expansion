package com.chris.vanilla_expansion_dragons.entity.client.renderer.elderwing;

import com.chris.vanilla_expansion_dragons.VanillaExpansionDragons;
import com.chris.vanilla_expansion_dragons.entity.client.model.ElderWingDragonModel;
import com.chris.vanilla_expansion_dragons.entity.server.ElderWingDragonEntity;
import com.chris.vanilla_expansion_dragons.entity.server.util.Gender;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.resources.ResourceManager;

public class ElderWingDragonRenderer extends MobRenderer<ElderWingDragonEntity, ElderWingDragonRenderState, ElderWingDragonModel> {

    private static final Identifier FALLBACK_TEXTURE = Identifier.fromNamespaceAndPath(
            VanillaExpansionDragons.MOD_ID,
            "textures/entities/elderwing/base_black_male.png"
    );

    public ElderWingDragonRenderer(EntityRendererProvider.Context context) {
        super(context, new ElderWingDragonModel(context.bakeLayer(ElderWingDragonModel.LAYER_LOCATION)), 0.8f);
    }

    @Override
    public Identifier getTextureLocation(ElderWingDragonRenderState state) {
        String variant = (state.variant == null || state.variant.isBlank()) ? "black" : state.variant.toLowerCase();
        Gender gender = state.gender != null ? state.gender : Gender.MALE;

        // Yellow uses a single shared texture ("base_yellow_male.png") for both male and female
        String suffix = "yellow".equals(variant) ? "male" : (gender == Gender.FEMALE ? "female" : "male");
        String oppositeSuffix = "female".equals(suffix) ? "male" : "female";

        Identifier primary = textureId(variant, suffix);
        Identifier sharedFallback = textureId(variant, oppositeSuffix);

        ResourceManager resourceManager = Minecraft.getInstance().getResourceManager();
        if (resourceManager.getResource(primary).isPresent()) {
            return primary;
        }
        if (resourceManager.getResource(sharedFallback).isPresent()) {
            return sharedFallback;
        }
        return FALLBACK_TEXTURE;
    }

    private static Identifier textureId(String variant, String genderSuffix) {
        return Identifier.fromNamespaceAndPath(
                VanillaExpansionDragons.MOD_ID,
                "textures/entities/elderwing/base_" + variant + "_" + genderSuffix + ".png"
        );
    }

    @Override
    public ElderWingDragonRenderState createRenderState() {
        return new ElderWingDragonRenderState();
    }

    @Override
    public void extractRenderState(ElderWingDragonEntity entity, ElderWingDragonRenderState state, float partialTicks) {
        super.extractRenderState(entity, state, partialTicks);
        state.variant = entity.getVariant();
        state.gender = entity.getGender();
        state.cosmeticStyle = entity.getActiveCosmeticStyle();
        state.hasWingsUnlocked = entity.hasWingsUnlocked();
        state.isSaddled = entity.isSaddled();
        state.isRidden = entity.isVehicle();
        state.isSitting = entity.isInSittingPose() || entity.getCommandMode() == ElderWingDragonEntity.CommandMode.SIT;
        state.isFlying = entity.isFlying();
        if (entity.getControllingPassenger() instanceof net.minecraft.world.entity.LivingEntity rider) {
            state.isThrustingFlight = Math.abs(rider.zza) > 0.01F;
        } else {
            state.isThrustingFlight = entity.getDeltaMovement().horizontalDistanceSqr() > 0.04D;
        }

        state.flyAnimationState.copyFrom(entity.flyAnimationState);
        state.glideAnimationState.copyFrom(entity.glideAnimationState);
        state.hoverAnimationState.copyFrom(entity.hoverAnimationState);
        state.idleAnimationState.copyFrom(entity.idleAnimationState);
        state.sitAnimationState.copyFrom(entity.sitAnimationState);
        state.sleepingAnimationState.copyFrom(entity.sleepingAnimationState);
        state.breathAnimationState.copyFrom(entity.breathAnimationState);
        state.breathAttackAnimationState.copyFrom(entity.breathAttackAnimationState);
    }
}