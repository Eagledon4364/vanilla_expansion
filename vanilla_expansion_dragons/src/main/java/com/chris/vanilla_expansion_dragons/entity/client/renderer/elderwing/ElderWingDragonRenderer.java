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
        Gender opposite = (gender == Gender.MALE) ? Gender.FEMALE : Gender.MALE;

        //Identifier primary = textureId(variant, gender.textureSuffix);
        //Identifier sharedFallback = textureId(variant, opposite.textureSuffix);

        ResourceManager resourceManager = Minecraft.getInstance().getResourceManager();
//        if (resourceManager.getResource(primary).isPresent()) {
//            return primary;
//        }
//        if (resourceManager.getResource(sharedFallback).isPresent()) {
//            return sharedFallback;
//        }
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
        //state.variant = entity.getVariant();
        //state.gender = entity.getGender();
        //state.cosmeticStyle = entity.getActiveCosmeticStyle();
        state.isSaddled = entity.isSaddled();
        state.isRidden = entity.isVehicle();
        state.isSitting = entity.isInSittingPose();

//        state.idleAnimationState.copyFrom(entity.idleAnimationState);
//        state.sitAnimationState.copyFrom(entity.sitAnimationState);
//        state.sleepingAnimationState.copyFrom(entity.sleepingAnimationState);
//        state.flyAnimationState.copyFrom(entity.flyAnimationState);
//        state.hoverAnimationState.copyFrom(entity.hoverAnimationState);
//        state.glideAnimationState.copyFrom(entity.glideAnimationState);
//        state.breathAnimationState.copyFrom(entity.breathAnimationState);
//        state.breathAttackAnimationState.copyFrom(entity.breathAttackAnimationState);
    }
}