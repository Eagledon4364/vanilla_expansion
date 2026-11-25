package com.chris.vanilla_expansion_dragons.entity.client.renderer.elderwing;

import com.chris.vanilla_expansion_dragons.VanillaExpansionDragons;
import com.chris.vanilla_expansion_dragons.entity.client.model.ElderWingDragonModel;
import com.chris.vanilla_expansion_dragons.entity.server.ElderWingDragonEntity;
import com.chris.vanilla_expansion_dragons.entity.server.util.DragonAnimal;
import com.chris.vanilla_expansion_dragons.entity.server.util.Gender;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

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

        state.flightPitch = entity.isFlying() ? entity.getLerpedDynamicPitch(partialTicks) : 0.0F;
        state.flightRoll = entity.isFlying() ? entity.getLerpedRoll(partialTicks) : 0.0F;

        state.isSitting = entity.isInSittingPose() || entity.getCommandMode() == DragonAnimal.CommandMode.SIT;
        state.isFlying = entity.isFlying();
        if (entity.getControllingPassenger() instanceof LivingEntity rider) {
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

        state.flightPitch = entity.isFlying()
                ? entity.getLerpedDynamicPitch(partialTicks)
                : 0.0F;

        state.flightRoll = entity.isFlying()
                ? entity.getLerpedRoll(partialTicks)
                : 0.0F;

        state.flightYawLean = entity.isFlying()
                ? entity.getLerpedYawLean(partialTicks)
                : 0.0F;
    }

    @Override
    protected void setupRotations(
            ElderWingDragonRenderState state,
            PoseStack poseStack,
            float bodyRot,
            float scale
    ) {
        super.setupRotations(state, poseStack, bodyRot, scale);

        if (state.isFlying) {
            float pitch = state.flightPitch;
            float roll = Mth.clamp(state.flightRoll, -90.0F, 90.0F);
            float yawLean = state.flightYawLean;

            poseStack.translate(0.0F, 1.0F, 0.0F);
            poseStack.mulPose(new Matrix4f().rotationX(-pitch * Mth.DEG_TO_RAD));
            poseStack.mulPose(new Matrix4f().rotationZ((roll + yawLean) * Mth.DEG_TO_RAD));
            poseStack.translate(0.0F, -1.0F, 0.0F);

        }
    }
    public Matrix4f getSaddleLockTransform(
            ElderWingDragonEntity entity,
            float partialTicks
    ) {
        ElderWingDragonRenderState state = this.createRenderState();

        this.extractRenderState(entity, state, partialTicks);
        this.getModel().setupAnim(state);

        PoseStack poseStack = new PoseStack();

        poseStack.scale(
                state.scale,
                state.scale,
                state.scale
        );

        this.setupRotations(
                state,
                poseStack,
                state.bodyRot,
                state.scale
        );

        poseStack.scale(-1.0F, -1.0F, 1.0F);

        this.scale(state, poseStack);

        poseStack.translate(0.0F, -1.501F, 0.0F);

        this.getModel().translateToSaddleLock(poseStack);
        System.out.println(
                "[DRAGON SADDLE] flying=" + state.isFlying
                        + " pitch=" + state.flightPitch
                        + " roll=" + state.flightRoll
                        + " yawLean=" + state.flightYawLean
                        + " transform=" + poseStack.last().pose()
        );
        return new Matrix4f(poseStack.last().pose());
    }
    public record SaddleLockPose(Vec3 localOffset, Quaternionf rotation) {
    }
    public SaddleLockPose getSaddleLockPose(ElderWingDragonEntity entity, float partialTicks) {
        ElderWingDragonRenderState state = this.createRenderState();
        this.extractRenderState(entity, state, partialTicks);
        this.getModel().setupAnim(state);

        // 1. Rotation pass: avoid negative scale so rotation stays a proper right-handed rotation
        PoseStack rotStack = new PoseStack();
        rotStack.scale(state.scale, state.scale, state.scale);
        this.setupRotations(state, rotStack, state.bodyRot, state.scale);
        this.scale(state, rotStack);
        this.getModel().translateToSaddleLock(rotStack);

        Quaternionf rotation = new Quaternionf();
        rotStack.last().pose().getUnnormalizedRotation(rotation);
        rotation.normalize();

        // 2. Position pass: match renderer transform and convert Y for world/pose space
        PoseStack posStack = new PoseStack();
        posStack.scale(state.scale, state.scale, state.scale);
        this.setupRotations(state, posStack, state.bodyRot, state.scale);
        posStack.scale(-1.0F, -1.0F, 1.0F);
        this.scale(state, posStack);
        this.getModel().translateToSaddleLock(posStack);

        Vector3f rawPos = posStack.last().pose().getTranslation(new Vector3f());
        Vec3 localOffset = new Vec3(rawPos.x, rawPos.y, rawPos.z);
        return new SaddleLockPose(localOffset, rotation);
    }
}