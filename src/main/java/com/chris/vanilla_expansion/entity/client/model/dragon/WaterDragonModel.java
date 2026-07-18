package com.chris.vanilla_expansion.entity.client.model.dragon;


import com.chris.vanilla_expansion.VanillaExpansion;
import com.chris.vanilla_expansion.entity.client.render.energy_dragon.EnergyDragonRenderState;
import com.chris.vanilla_expansion.entity.client.render.water_dragon.WaterDragonRenderState;
import net.minecraft.client.animation.KeyframeAnimation;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.NotNull;



public class WaterDragonModel extends EntityModel<@NotNull WaterDragonRenderState> {
    // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
    public static final ModelLayerLocation LAYER_LOCATION =  new ModelLayerLocation(Identifier.fromNamespaceAndPath(VanillaExpansion.MOD_ID, "waterdragon"), "main");
    private final ModelPart root;

    public WaterDragonModel(ModelPart root) {
        super(root);
        this.root = root.getChild("root");
    }

    public static LayerDefinition getTextureModelData() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create().texOffs(0, 0).addBox(-5.0F, -8.0F, -7.0F, 10.0F, 8.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 17.0F, -2.0F));

        PartDefinition cube_r1 = root.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(90, 21).addBox(0.0F, -4.0F, -1.0F, 0.0F, 4.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -7.0F, 2.0F, 0.1745F, 0.0F, 0.0F));

        PartDefinition neck = root.addOrReplaceChild("neck", CubeListBuilder.create().texOffs(76, 60).addBox(-3.0F, -3.0F, -7.0F, 6.0F, 6.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -4.0F, -7.0F));

        PartDefinition neck2 = neck.addOrReplaceChild("neck2", CubeListBuilder.create().texOffs(76, 74).addBox(-2.5F, -2.5F, -7.0F, 5.0F, 5.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -7.0F));

        PartDefinition head = neck2.addOrReplaceChild("head", CubeListBuilder.create().texOffs(76, 87).addBox(-3.0F, -3.0F, -5.0F, 6.0F, 5.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(26, 94).addBox(-2.0F, -3.0F, -7.0F, 4.0F, 5.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(88, 53).addBox(-1.5F, -2.0F, -12.0F, 3.0F, 2.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(70, 88).addBox(1.0F, -1.9F, -11.5F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(70, 92).addBox(-2.0F, -1.9F, -11.5F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -6.5F));

        PartDefinition cube_r2 = head.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(70, 97).addBox(0.0F, -4.0F, 0.0F, 0.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.0F, 1.5F, -4.0F, 0.0873F, -0.2618F, 0.0F));

        PartDefinition cube_r3 = head.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(92, 15).addBox(-0.5F, 2.0F, 4.0F, 1.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -2.0F, -6.5F, 0.6981F, 0.0F, 0.0F));

        PartDefinition cube_r4 = head.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(90, 97).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.0F, -7.5F, -0.1745F, 0.0F, 0.0F));

        PartDefinition cube_r5 = head.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(80, 97).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -3.0F, -6.5F, 0.6981F, 0.0F, 0.0F));

        PartDefinition cube_r6 = head.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(12, 98).addBox(-1.0F, 0.0F, 0.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -2.5F, -8.5F, 0.1745F, 0.0F, 0.0F));

        PartDefinition cube_r7 = head.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(48, 98).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -2.25F, -11.5F, 0.2618F, 0.0F, 0.0F));

        PartDefinition cube_r8 = head.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(32, 88).addBox(-3.0F, -1.0F, 0.0F, 6.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.5F, -5.0F, -0.0873F, 0.0F, 0.0F));

        PartDefinition cube_r9 = head.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(38, 94).addBox(0.0F, -4.0F, 0.0F, 0.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, 1.5F, -4.0F, 0.0873F, 0.2618F, 0.0F));

        PartDefinition jaw = head.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(90, 33).addBox(-1.5F, 0.0F, -5.0F, 3.0F, 2.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(48, 94).addBox(1.0F, 0.1F, -4.5F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(20, 98).addBox(-2.0F, 0.1F, -4.5F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -7.0F));

        PartDefinition tail = root.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(48, 0).addBox(-4.0F, -3.5F, -1.0F, 8.0F, 7.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -4.0F, 7.0F));

        PartDefinition tail2 = tail.addOrReplaceChild("tail2", CubeListBuilder.create().texOffs(50, 21).addBox(-3.0F, -3.0F, -1.0F, 6.0F, 6.0F, 14.0F, new CubeDeformation(0.0F))
                .texOffs(0, 40).addBox(2.0F, 0.0F, -1.0F, 7.0F, 0.0F, 18.0F, new CubeDeformation(0.0F))
                .texOffs(0, 22).addBox(-9.0F, 0.0F, -1.0F, 7.0F, 0.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 13.0F));

        PartDefinition tail3 = tail2.addOrReplaceChild("tail3", CubeListBuilder.create().texOffs(50, 41).addBox(-2.5F, -2.5F, -1.0F, 5.0F, 5.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 13.0F));

        PartDefinition tail4 = tail3.addOrReplaceChild("tail4", CubeListBuilder.create().texOffs(0, 58).addBox(-2.0F, -2.0F, -1.0F, 4.0F, 4.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 13.0F));

        PartDefinition tail5 = tail4.addOrReplaceChild("tail5", CubeListBuilder.create().texOffs(0, 76).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 14.0F, new CubeDeformation(0.0F))
                .texOffs(36, 60).addBox(1.0F, 0.0F, -1.0F, 6.0F, 0.0F, 14.0F, new CubeDeformation(0.0F))
                .texOffs(36, 74).addBox(-7.0F, 0.0F, -1.0F, 6.0F, 0.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 13.0F));

        PartDefinition left_Leg = root.addOrReplaceChild("left_Leg", CubeListBuilder.create().texOffs(92, 9).addBox(-1.5F, 10.0F, -2.0F, 3.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(98, 87).addBox(-0.5F, 11.0F, -4.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(7.0F, -5.0F, -4.0F));

        PartDefinition cube_r10 = left_Leg.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(88, 41).addBox(-3.0F, -8.0F, -1.0F, 4.0F, 8.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 6.25F, 1.5F, 0.3491F, 0.0F, 0.0F));

        PartDefinition cube_r11 = left_Leg.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(92, 0).addBox(-1.0F, -6.0F, -3.0F, 3.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, 11.0F, 2.0F, -0.3491F, 0.0F, 0.0F));

        PartDefinition cube_r12 = left_Leg.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(60, 100).addBox(0.0F, -1.0F, -2.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.5F, 12.0F, 1.0F, 0.0F, 0.8727F, 0.0F));

        PartDefinition cube_r13 = left_Leg.addOrReplaceChild("cube_r13", CubeListBuilder.create().texOffs(100, 18).addBox(-1.0F, -1.0F, -2.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, 12.0F, -2.0F, 0.0F, 0.2618F, 0.0F));

        PartDefinition cube_r14 = left_Leg.addOrReplaceChild("cube_r14", CubeListBuilder.create().texOffs(98, 93).addBox(0.0F, -1.0F, -2.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, 12.0F, -2.0F, 0.0F, -0.2618F, 0.0F));

        PartDefinition right_Leg = root.addOrReplaceChild("right_Leg", CubeListBuilder.create().texOffs(100, 96).addBox(-0.5F, 11.0F, -4.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(12, 92).addBox(-1.5F, 10.0F, -2.0F, 3.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-7.0F, -5.0F, -4.0F));

        PartDefinition cube_r15 = right_Leg.addOrReplaceChild("cube_r15", CubeListBuilder.create().texOffs(54, 88).addBox(-1.0F, -8.0F, -1.0F, 4.0F, 8.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 6.25F, 1.5F, 0.3491F, 0.0F, 0.0F));

        PartDefinition cube_r16 = right_Leg.addOrReplaceChild("cube_r16", CubeListBuilder.create().texOffs(0, 92).addBox(-2.0F, -6.0F, -3.0F, 3.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, 11.0F, 2.0F, -0.3491F, 0.0F, 0.0F));

        PartDefinition cube_r17 = right_Leg.addOrReplaceChild("cube_r17", CubeListBuilder.create().texOffs(54, 100).addBox(-1.0F, -1.0F, -2.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.5F, 12.0F, 1.0F, 0.0F, -0.8727F, 0.0F));

        PartDefinition cube_r18 = right_Leg.addOrReplaceChild("cube_r18", CubeListBuilder.create().texOffs(100, 15).addBox(0.0F, -1.0F, -2.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, 12.0F, -2.0F, 0.0F, -0.2618F, 0.0F));

        PartDefinition cube_r19 = right_Leg.addOrReplaceChild("cube_r19", CubeListBuilder.create().texOffs(98, 90).addBox(-1.0F, -1.0F, -2.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, 12.0F, -2.0F, 0.0F, 0.2618F, 0.0F));

        return LayerDefinition.create(meshdefinition, 128, 128);
    }
    @Override
    public void setupAnim(WaterDragonRenderState state) {
        super.setupAnim(state);

        this.root.getAllParts().forEach(ModelPart::resetPose);
        float ageInTicks = state.ageInTicks;

        this.root.xScale = 1.0f;
        this.root.yScale = 1.0f;
        this.root.zScale = 1.0f;

        if (state.isBaby) {
            float babyScale = 0.5f;
            this.root.xScale = babyScale;
            this.root.yScale = babyScale;
            this.root.zScale = babyScale;
            this.root.y = 14.0f;
        } else {
            this.root.y = 16.0F;
        }
//
//        if (state.isSleeping) {
//            this.sleepAnimation.apply(state.sleepingAnimationState, ageInTicks);
//        }
//        else if (state.isSitting) {
//            this.sitAnimation.apply(state.sitAnimationState, ageInTicks);
//        }
//        else if (state.isFlying) {
//            if (state.walkAnimationSpeed > 0.05f) {
//                this.flyAnimation.apply(state.flyAnimationState, ageInTicks);
//            } else {
//                this.hoverAnimation.apply(state.hoverAnimationState, ageInTicks);
//            }
//        }
//        else {
//            this.walkAnimation.applyWalk(state.walkAnimationPos, state.walkAnimationSpeed, 1.0f, 2.5f);
//            this.idleAnimation.apply(state.idleAnimationState, ageInTicks);
//        }
//
//        this.fireAnimation.apply(state.fireAnimationState, ageInTicks);
//        this.meleeAnimation.apply(state.meleeAnimationState, ageInTicks);
//
//        if (state.isRidden && state.isFlying && !state.isBaby) {
//            float pitchRad = state.dragonPitch * ((float)Math.PI / 180F);
//
//            this.root.xRot += pitchRad;
//        }
    }

    public ModelPart getRoot() {
        return root;
    }
}