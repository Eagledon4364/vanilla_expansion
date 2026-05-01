package com.chris.vanilla_expansion.entity.client.model;


import com.chris.vanilla_expansion.VanillaExpansion;
import com.chris.vanilla_expansion.entity.client.animation.EnergyDragonAnimations;
import net.minecraft.client.animation.KeyframeAnimation;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;


public class EnergyDragonModel extends EntityModel<@NotNull EnergyDragonRenderState> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(Identifier.fromNamespaceAndPath(VanillaExpansion.MOD_ID, "energydragon"), "main");

    private final KeyframeAnimation idleAnimation;
    private final KeyframeAnimation walkAnimation;
    private final KeyframeAnimation flyAnimation;
    private final KeyframeAnimation hoverAnimation;
    private final KeyframeAnimation sleepAnimation;
    private final KeyframeAnimation sitAnimation;
    private final KeyframeAnimation meleeAnimation;
    private final KeyframeAnimation fireAnimation;

    private final ModelPart root;



	public EnergyDragonModel(ModelPart root) {
        super(root);
        this.root = root.getChild("root");
        this.idleAnimation = EnergyDragonAnimations.IDLE.bake(root);
        this.walkAnimation = EnergyDragonAnimations.WALK.bake(root);
        this.flyAnimation = EnergyDragonAnimations.FLY.bake(root);
        this.hoverAnimation = EnergyDragonAnimations.HOVER.bake(root);
        this.sleepAnimation = EnergyDragonAnimations.SLEEPING.bake(root);
        this.sitAnimation = EnergyDragonAnimations.SIT.bake(root);
        this.fireAnimation = EnergyDragonAnimations.FIRE.bake(root);
        this.meleeAnimation = EnergyDragonAnimations.MELEE.bake(root);

	}

	public static LayerDefinition getTextureModelData() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 8.0F, 5.0F));

        PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(78, 346).addBox(-4.0F, 0.725F, -16.6F, 8.0F, 4.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(320, 228).addBox(-3.5F, -4.1414F, 9.8317F, 7.0F, 3.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r1 = body.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(342, 96).addBox(-0.5F, -2.0F, 0.0F, 4.0F, 2.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, -0.5955F, -1.6226F, 0.1745F, 0.0F, -0.2182F));

        PartDefinition cube_r2 = body.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 348).addBox(0.0F, -2.0F, -2.0F, 5.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.5F, 3.125F, -10.225F, 0.1745F, 0.0436F, -0.2618F));

        PartDefinition cube_r3 = body.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(230, 274).addBox(0.0F, -2.0F, 0.0F, 4.0F, 3.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, -3.0955F, 9.1274F, -0.0873F, -0.2182F, -0.2182F));

        PartDefinition cube_r4 = body.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(262, 346).addBox(0.0F, -1.0F, 0.0F, 6.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -13.0377F, 12.3789F, -0.1745F, -0.2618F, 0.0873F));

        PartDefinition cube_r5 = body.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(368, 61).addBox(-2.0F, -8.0F, 0.0F, 2.0F, 8.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.55F, -4.525F, 13.875F, -0.0436F, -0.2618F, 0.0F));

        PartDefinition cube_r6 = body.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(272, 363).addBox(0.0F, -8.0F, 0.0F, 2.0F, 8.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.55F, -4.525F, 13.875F, -0.0436F, 0.2618F, 0.0F));

        PartDefinition cube_r7 = body.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(0, 372).addBox(-2.5F, -8.0F, -1.0F, 5.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -3.6F, 25.0F, -0.0873F, 0.0F, 0.0F));

        PartDefinition cube_r8 = body.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(348, 181).addBox(0.0F, -8.0F, 0.0F, 2.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.3F, -4.0F, 17.85F, -0.0886F, -0.2175F, 0.0154F));

        PartDefinition cube_r9 = body.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(348, 140).addBox(-2.0F, -8.0F, 0.0F, 2.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.3F, -4.0F, 17.85F, -0.0886F, 0.2175F, -0.0154F));

        PartDefinition cube_r10 = body.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(132, 364).addBox(-1.0F, -7.0F, -1.0F, 2.0F, 7.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.0F, -4.5F, 9.0F, 0.0F, -0.0873F, 0.0F));

        PartDefinition cube_r11 = body.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(346, 171).addBox(-7.0F, -10.0F, 0.0F, 6.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.175F, -1.925F, 1.15F, 0.0873F, -0.0873F, 0.2618F));

        PartDefinition cube_r12 = body.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(102, 293).addBox(-5.0F, -1.0F, 0.0F, 5.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.375F, -11.4F, 8.05F, 0.0F, -0.0873F, 0.2618F));

        PartDefinition cube_r13 = body.addOrReplaceChild("cube_r13", CubeListBuilder.create().texOffs(42, 350).addBox(-1.0F, -7.0F, 0.0F, 2.0F, 7.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.5F, -3.5F, 1.0F, 0.1309F, -0.0873F, 0.0F));

        PartDefinition cube_r14 = body.addOrReplaceChild("cube_r14", CubeListBuilder.create().texOffs(346, 161).addBox(1.0F, -10.0F, 0.0F, 6.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.175F, -1.925F, 1.15F, 0.0873F, 0.0873F, -0.2618F));

        PartDefinition cube_r15 = body.addOrReplaceChild("cube_r15", CubeListBuilder.create().texOffs(350, 120).addBox(-1.0F, -7.0F, 0.0F, 2.0F, 7.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.5F, -3.5F, 1.0F, 0.1309F, 0.0873F, 0.0F));

        PartDefinition cube_r16 = body.addOrReplaceChild("cube_r16", CubeListBuilder.create().texOffs(194, 300).addBox(0.0F, -1.0F, 0.0F, 5.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.375F, -11.4F, 8.05F, 0.0F, 0.0873F, -0.2618F));

        PartDefinition cube_r17 = body.addOrReplaceChild("cube_r17", CubeListBuilder.create().texOffs(116, 362).addBox(-1.0F, -7.0F, -1.0F, 2.0F, 7.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.0F, -4.5F, 9.0F, 0.0F, 0.0873F, 0.0F));

        PartDefinition cube_r18 = body.addOrReplaceChild("cube_r18", CubeListBuilder.create().texOffs(184, 347).addBox(-1.0F, -8.0F, 0.0F, 2.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.25F, -0.775F, -5.775F, 0.1745F, -0.1745F, 0.0F));

        PartDefinition cube_r19 = body.addOrReplaceChild("cube_r19", CubeListBuilder.create().texOffs(164, 347).addBox(-1.0F, -8.0F, 0.0F, 2.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-8.25F, -0.775F, -5.775F, 0.1745F, 0.1745F, 0.0F));

        PartDefinition cube_r20 = body.addOrReplaceChild("cube_r20", CubeListBuilder.create().texOffs(358, 356).addBox(0.0F, -2.0F, 0.0F, 5.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.5F, 1.875F, -4.725F, 0.48F, 0.0436F, -0.2618F));

        PartDefinition cube_r21 = body.addOrReplaceChild("cube_r21", CubeListBuilder.create().texOffs(110, 346).addBox(0.0F, -8.0F, -2.0F, 4.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(212, 345).addBox(-15.0F, -8.0F, -2.0F, 4.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.5F, 0.375F, -10.725F, 0.1745F, 0.0F, 0.0F));

        PartDefinition cube_r22 = body.addOrReplaceChild("cube_r22", CubeListBuilder.create().texOffs(228, 379).addBox(0.0F, -2.0F, -2.0F, 3.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.5F, 2.875F, -13.975F, -0.1745F, 0.0436F, -0.2618F));

        PartDefinition cube_r23 = body.addOrReplaceChild("cube_r23", CubeListBuilder.create().texOffs(198, 369).addBox(0.0F, -6.0F, -1.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.5F, 1.0F, -18.0F, -0.1745F, 0.2618F, -0.0873F));

        PartDefinition cube_r24 = body.addOrReplaceChild("cube_r24", CubeListBuilder.create().texOffs(366, 226).addBox(0.0F, 0.0F, 0.0F, 3.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.25F, -7.75F, -19.75F, 0.3491F, -0.1745F, 0.4363F));

        PartDefinition cube_r25 = body.addOrReplaceChild("cube_r25", CubeListBuilder.create().texOffs(366, 12).addBox(-3.0F, 0.0F, 0.0F, 3.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.25F, -7.75F, -19.75F, 0.3491F, 0.1745F, -0.4363F));

        PartDefinition cube_r26 = body.addOrReplaceChild("cube_r26", CubeListBuilder.create().texOffs(240, 307).addBox(-4.0F, -2.0F, 0.0F, 8.0F, 2.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -9.1F, -14.55F, 0.1745F, 0.0F, 0.0F));

        PartDefinition cube_r27 = body.addOrReplaceChild("cube_r27", CubeListBuilder.create().texOffs(336, 316).addBox(-6.0F, -1.0F, 0.0F, 6.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -13.0377F, 12.3789F, -0.1745F, 0.2618F, -0.0873F));

        PartDefinition cube_r28 = body.addOrReplaceChild("cube_r28", CubeListBuilder.create().texOffs(336, 61).addBox(0.0F, -7.0F, 0.0F, 0.0F, 5.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(324, 271).addBox(-3.0F, -2.0F, 0.0F, 6.0F, 2.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -12.7877F, 10.3039F, -0.1745F, 0.0F, 0.0F));

        PartDefinition cube_r29 = body.addOrReplaceChild("cube_r29", CubeListBuilder.create().texOffs(212, 328).addBox(0.0F, -7.0F, 0.0F, 0.0F, 5.0F, 12.0F, new CubeDeformation(0.0F))
                .texOffs(336, 304).addBox(-3.5F, -2.0F, 0.0F, 7.0F, 2.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -11.8556F, 1.0339F, 0.0873F, 0.0F, 0.0F));

        PartDefinition cube_r30 = body.addOrReplaceChild("cube_r30", CubeListBuilder.create().texOffs(236, 346).addBox(-3.0F, -2.0F, 0.0F, 6.0F, 4.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -5.75F, -20.25F, 0.48F, 0.0F, 0.0F));

        PartDefinition cube_r31 = body.addOrReplaceChild("cube_r31", CubeListBuilder.create().texOffs(366, 298).addBox(-3.0F, -2.0F, 0.0F, 6.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -7.0F, -24.0F, -0.2618F, 0.0F, 0.0F));

        PartDefinition cube_r32 = body.addOrReplaceChild("cube_r32", CubeListBuilder.create().texOffs(382, 6).addBox(-1.0F, 0.0F, 0.0F, 1.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(0, 382).addBox(-13.0F, 0.0F, 0.0F, 1.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.5F, -7.75F, -16.25F, 0.3927F, 0.0F, 0.0F));

        PartDefinition cube_r33 = body.addOrReplaceChild("cube_r33", CubeListBuilder.create().texOffs(382, 0).addBox(-2.0F, 0.0F, 0.0F, 2.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(180, 381).addBox(-13.0F, 0.0F, 0.0F, 2.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.5F, -7.75F, -16.25F, -0.6109F, 0.0F, 0.0F));

        PartDefinition cube_r34 = body.addOrReplaceChild("cube_r34", CubeListBuilder.create().texOffs(300, 184).addBox(-5.0F, -1.0F, -2.0F, 8.0F, 3.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.5F, -9.2807F, -12.5545F, 0.1745F, -0.0873F, 0.1745F));

        PartDefinition cube_r35 = body.addOrReplaceChild("cube_r35", CubeListBuilder.create().texOffs(0, 304).addBox(-3.0F, -1.0F, -2.0F, 8.0F, 3.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.5F, -9.2807F, -12.5545F, 0.1745F, 0.0873F, -0.1745F));

        PartDefinition cube_r36 = body.addOrReplaceChild("cube_r36", CubeListBuilder.create().texOffs(302, 358).addBox(0.0F, -6.0F, -1.0F, 3.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.15F, -2.0F, -24.7F, -0.4363F, 0.2618F, -0.0873F));

        PartDefinition cube_r37 = body.addOrReplaceChild("cube_r37", CubeListBuilder.create().texOffs(16, 369).addBox(0.0F, -6.0F, -1.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.85F, 0.0F, -20.75F, -0.2618F, 0.3491F, -0.0873F));

        PartDefinition cube_r38 = body.addOrReplaceChild("cube_r38", CubeListBuilder.create().texOffs(286, 358).addBox(0.0F, -8.0F, -2.0F, 2.0F, 8.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(320, 358).addBox(-13.0F, -8.0F, -2.0F, 2.0F, 8.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.5F, 1.375F, -13.725F, 0.1745F, 0.0F, 0.0F));

        PartDefinition cube_r39 = body.addOrReplaceChild("cube_r39", CubeListBuilder.create().texOffs(74, 370).addBox(-2.5F, -3.0F, 0.0F, 5.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.9F, -24.0F, -0.3491F, 0.0F, 0.0F));

        PartDefinition cube_r40 = body.addOrReplaceChild("cube_r40", CubeListBuilder.create().texOffs(334, 289).addBox(-4.0F, -2.0F, 0.0F, 4.0F, 3.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.0F, -3.0955F, 9.1274F, -0.0873F, 0.2182F, 0.2182F));

        PartDefinition cube_r41 = body.addOrReplaceChild("cube_r41", CubeListBuilder.create().texOffs(338, 82).addBox(-3.5F, -2.0F, 0.0F, 4.0F, 2.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.0F, -0.5955F, -1.6226F, 0.1745F, 0.0F, 0.2182F));

        PartDefinition cube_r42 = body.addOrReplaceChild("cube_r42", CubeListBuilder.create().texOffs(334, 46).addBox(-3.5F, -3.0F, 0.0F, 7.0F, 3.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.4045F, -1.6226F, 0.0873F, 0.0F, 0.0F));

        PartDefinition cube_r43 = body.addOrReplaceChild("cube_r43", CubeListBuilder.create().texOffs(66, 220).addBox(-3.5F, -3.0F, 0.0F, 7.0F, 3.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 4.475F, -8.6F, 0.3927F, 0.0F, 0.0F));

        PartDefinition cube_r44 = body.addOrReplaceChild("cube_r44", CubeListBuilder.create().texOffs(364, 346).addBox(-3.0F, -4.0F, 0.0F, 6.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 3.725F, -20.35F, -0.1745F, 0.0F, 0.0F));

        PartDefinition cube_r45 = body.addOrReplaceChild("cube_r45", CubeListBuilder.create().texOffs(136, 377).addBox(-2.0F, -5.0F, 0.0F, 4.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -3.5F, -27.75F, -0.4363F, 0.0F, 0.0F));

        PartDefinition cube_r46 = body.addOrReplaceChild("cube_r46", CubeListBuilder.create().texOffs(372, 54).addBox(-2.0F, -3.0F, 0.0F, 4.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, -27.0F, -0.4363F, 0.0F, 0.0F));

        PartDefinition cube_r47 = body.addOrReplaceChild("cube_r47", CubeListBuilder.create().texOffs(78, 358).addBox(-3.0F, -6.0F, -1.0F, 3.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.15F, -2.0F, -24.7F, -0.4363F, -0.2618F, 0.0873F));

        PartDefinition cube_r48 = body.addOrReplaceChild("cube_r48", CubeListBuilder.create().texOffs(368, 271).addBox(-4.0F, -6.0F, -1.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.85F, 0.0F, -20.75F, -0.2618F, -0.3491F, 0.0873F));

        PartDefinition cube_r49 = body.addOrReplaceChild("cube_r49", CubeListBuilder.create().texOffs(242, 380).addBox(-3.0F, -2.0F, -2.0F, 3.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.5F, 2.875F, -13.975F, -0.1745F, -0.0436F, 0.2618F));

        PartDefinition cube_r50 = body.addOrReplaceChild("cube_r50", CubeListBuilder.create().texOffs(204, 361).addBox(-5.0F, -2.0F, 0.0F, 5.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.5F, 1.875F, -4.725F, 0.48F, -0.0436F, 0.2618F));

        PartDefinition cube_r51 = body.addOrReplaceChild("cube_r51", CubeListBuilder.create().texOffs(338, 346).addBox(-5.0F, -2.0F, -2.0F, 5.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.5F, 3.125F, -10.225F, 0.1745F, -0.0436F, 0.2618F));

        PartDefinition cube_r52 = body.addOrReplaceChild("cube_r52", CubeListBuilder.create().texOffs(368, 181).addBox(-4.0F, -6.0F, -1.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.5F, 1.0F, -18.0F, -0.1745F, -0.2618F, 0.0873F));

        PartDefinition neck = body.addOrReplaceChild("neck", CubeListBuilder.create().texOffs(314, 346).addBox(-2.5F, -1.0195F, -7.1749F, 5.0F, 5.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(360, 376).addBox(-2.5F, -1.0195F, -8.4249F, 5.0F, 5.0F, 2.0F, new CubeDeformation(-0.25F))
                .texOffs(368, 383).addBox(-3.5F, 0.9805F, -6.1749F, 1.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(142, 384).addBox(-3.5F, 0.9805F, -3.1749F, 1.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(384, 186).addBox(2.5F, 0.9805F, -3.1749F, 1.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(384, 191).addBox(2.5F, 0.9805F, -6.1749F, 1.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(374, 90).addBox(-2.0F, -0.7695F, -1.1749F, 4.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -4.5F, -25.0F, -0.8727F, 0.0F, 0.0F));

        PartDefinition cube_r53 = neck.addOrReplaceChild("cube_r53", CubeListBuilder.create().texOffs(342, 383).addBox(-1.0F, -4.7189F, -1.2678F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.9088F, -1.8767F, 0.4363F, 0.0F, 0.0F));

        PartDefinition cube_r54 = neck.addOrReplaceChild("cube_r54", CubeListBuilder.create().texOffs(352, 383).addBox(-1.0F, -4.7189F, -1.2678F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.9088F, -4.8767F, 0.4363F, 0.0F, 0.0F));

        PartDefinition cube_r55 = neck.addOrReplaceChild("cube_r55", CubeListBuilder.create().texOffs(252, 363).addBox(-0.6595F, -2.8121F, -2.2981F, 3.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.75F, 0.1588F, -4.8767F, 0.0F, 0.0F, 0.3491F));

        PartDefinition cube_r56 = neck.addOrReplaceChild("cube_r56", CubeListBuilder.create().texOffs(364, 255).addBox(0.4991F, -2.8627F, -2.2981F, 3.0F, 1.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 6.9088F, -4.8767F, 0.0F, 0.0F, -0.2618F));

        PartDefinition cube_r57 = neck.addOrReplaceChild("cube_r57", CubeListBuilder.create().texOffs(364, 247).addBox(-3.4991F, -2.8627F, -2.2981F, 3.0F, 1.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 6.9088F, -4.8767F, 0.0F, 0.0F, 0.2618F));

        PartDefinition cube_r58 = neck.addOrReplaceChild("cube_r58", CubeListBuilder.create().texOffs(178, 363).addBox(-2.3405F, -2.8121F, -2.2981F, 3.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.75F, 0.1588F, -4.8767F, 0.0F, 0.0F, -0.3491F));

        PartDefinition neck_segment = neck.addOrReplaceChild("neck_segment", CubeListBuilder.create().texOffs(290, 346).addBox(-2.5F, -1.4064F, -7.2036F, 5.0F, 5.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(346, 376).addBox(-2.5F, -1.4064F, -8.4536F, 5.0F, 5.0F, 2.0F, new CubeDeformation(-0.25F))
                .texOffs(96, 313).addBox(-3.5F, 0.5936F, -6.2036F, 1.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(362, 156).addBox(-3.5F, 0.5936F, -3.2036F, 1.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(158, 356).addBox(2.5F, 0.5936F, -3.2036F, 1.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(198, 363).addBox(2.5F, 0.5936F, -6.2036F, 1.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.4088F, -7.3767F, -0.0873F, 0.0F, 0.0F));

        PartDefinition cube_r59 = neck_segment.addOrReplaceChild("cube_r59", CubeListBuilder.create().texOffs(332, 383).addBox(-1.0F, -4.5981F, -1.5F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.3143F, -1.7461F, 0.4363F, 0.0F, 0.0F));

        PartDefinition cube_r60 = neck_segment.addOrReplaceChild("cube_r60", CubeListBuilder.create().texOffs(262, 383).addBox(-1.0F, -4.5981F, -1.5F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.3143F, -4.7461F, 0.4363F, 0.0F, 0.0F));

        PartDefinition cube_r61 = neck_segment.addOrReplaceChild("cube_r61", CubeListBuilder.create().texOffs(158, 363).addBox(-0.5885F, -2.617F, -2.4575F, 3.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.75F, -0.4357F, -4.7461F, 0.0F, 0.0F, 0.3491F));

        PartDefinition cube_r62 = neck_segment.addOrReplaceChild("cube_r62", CubeListBuilder.create().texOffs(364, 205).addBox(0.4454F, -2.6621F, -2.4575F, 3.0F, 1.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 6.3143F, -4.7461F, 0.0F, 0.0F, -0.2618F));

        PartDefinition cube_r63 = neck_segment.addOrReplaceChild("cube_r63", CubeListBuilder.create().texOffs(364, 197).addBox(-3.4454F, -2.6621F, -2.4575F, 3.0F, 1.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 6.3143F, -4.7461F, 0.0F, 0.0F, 0.2618F));

        PartDefinition cube_r64 = neck_segment.addOrReplaceChild("cube_r64", CubeListBuilder.create().texOffs(96, 362).addBox(-2.4115F, -2.617F, -2.4575F, 3.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.75F, -0.4357F, -4.7461F, 0.0F, 0.0F, -0.3491F));

        PartDefinition head = neck_segment.addOrReplaceChild("head", CubeListBuilder.create().texOffs(190, 372).addBox(-1.0F, -2.391F, -15.8103F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(366, 236).addBox(-0.25F, -2.391F, -15.4103F, 3.0F, 3.0F, 6.0F, new CubeDeformation(-0.25F))
                .texOffs(366, 289).addBox(-2.75F, -2.391F, -15.4103F, 3.0F, 3.0F, 6.0F, new CubeDeformation(-0.25F))
                .texOffs(318, 203).addBox(-3.0F, -0.841F, -3.4103F, 6.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(350, 135).addBox(-3.0F, -1.141F, -9.5103F, 6.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(372, 213).addBox(-4.5F, -3.641F, -8.5603F, 2.0F, 3.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(248, 372).addBox(2.5F, -3.641F, -8.5603F, 2.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0643F, -7.2461F, 0.9599F, 0.0F, 0.0F));

        PartDefinition cube_r65 = head.addOrReplaceChild("cube_r65", CubeListBuilder.create().texOffs(374, 98).addBox(0.0F, -5.9544F, -0.5209F, 0.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.641F, -6.3103F, -0.1745F, 0.0F, 0.0F));

        PartDefinition cube_r66 = head.addOrReplaceChild("cube_r66", CubeListBuilder.create().texOffs(370, 312).addBox(-0.7757F, -2.8479F, -0.5365F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.341F, -1.1603F, -0.1745F, 0.0436F, 0.2618F));

        PartDefinition cube_r67 = head.addOrReplaceChild("cube_r67", CubeListBuilder.create().texOffs(212, 383).addBox(-2.237F, -2.9154F, -0.6668F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.5F, -0.641F, -3.5603F, -0.2618F, -0.4363F, 0.0873F));

        PartDefinition cube_r68 = head.addOrReplaceChild("cube_r68", CubeListBuilder.create().texOffs(108, 382).addBox(0.237F, -2.9154F, -0.6668F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.5F, -0.641F, -3.5603F, -0.2618F, 0.4363F, -0.0873F));

        PartDefinition cube_r69 = head.addOrReplaceChild("cube_r69", CubeListBuilder.create().texOffs(338, 356).addBox(-1.0F, -6.0F, 0.0F, 2.0F, 3.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.75F, 5.109F, -9.5603F, 0.0F, -0.1745F, 0.0F));

        PartDefinition cube_r70 = head.addOrReplaceChild("cube_r70", CubeListBuilder.create().texOffs(298, 228).addBox(-1.0F, -6.0F, 0.0F, 2.0F, 3.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.75F, 5.109F, -9.5603F, 0.0F, 0.1745F, 0.0F));

        PartDefinition cube_r71 = head.addOrReplaceChild("cube_r71", CubeListBuilder.create().texOffs(366, 331).addBox(1.0261F, -3.8191F, 0.0F, 4.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 6.359F, -9.4103F, 0.0F, 0.0F, -0.3491F));

        PartDefinition cube_r72 = head.addOrReplaceChild("cube_r72", CubeListBuilder.create().texOffs(348, 216).addBox(-3.0F, -2.9544F, -0.5209F, 6.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.891F, -6.2103F, -0.1745F, 0.0F, 0.0F));

        PartDefinition cube_r73 = head.addOrReplaceChild("cube_r73", CubeListBuilder.create().texOffs(366, 324).addBox(-5.0261F, -3.8191F, 0.0F, 4.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 6.359F, -9.4103F, 0.0F, 0.0F, 0.3491F));

        PartDefinition cube_r74 = head.addOrReplaceChild("cube_r74", CubeListBuilder.create().texOffs(96, 358).addBox(-2.2243F, -2.8479F, -0.5365F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.341F, -1.1603F, -0.1745F, -0.0436F, -0.2618F));

        PartDefinition cube_r75 = head.addOrReplaceChild("cube_r75", CubeListBuilder.create().texOffs(370, 128).addBox(-2.5F, -6.9544F, -1.5209F, 5.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 4.659F, -0.9103F, -0.1745F, 0.0F, 0.0F));

        PartDefinition cube_r76 = head.addOrReplaceChild("cube_r76", CubeListBuilder.create().texOffs(300, 203).addBox(-2.5F, -2.9544F, 0.5209F, 5.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.141F, -10.1103F, 0.1745F, 0.0F, 0.0F));

        PartDefinition cube_r77 = head.addOrReplaceChild("cube_r77", CubeListBuilder.create().texOffs(160, 381).addBox(-2.125F, -5.25F, 0.2165F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(7.1651F, 2.859F, -7.7433F, 0.0F, 0.1745F, 0.0F));

        PartDefinition cube_r78 = head.addOrReplaceChild("cube_r78", CubeListBuilder.create().texOffs(370, 304).addBox(-1.0F, -4.8191F, 0.0261F, 2.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, 1.109F, -7.3103F, 0.3491F, 0.4363F, 0.0F));

        PartDefinition cube_r79 = head.addOrReplaceChild("cube_r79", CubeListBuilder.create().texOffs(48, 308).addBox(-1.7765F, -5.799F, -0.75F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.5767F, -10.4009F, 0.3482F, -0.2618F, 0.0F, 0.2618F));

        PartDefinition cube_r80 = head.addOrReplaceChild("cube_r80", CubeListBuilder.create().texOffs(48, 304).addBox(0.7765F, -5.799F, -0.75F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.5767F, -10.4009F, 0.3482F, -0.2618F, 0.0F, -0.2618F));

        PartDefinition cube_r81 = head.addOrReplaceChild("cube_r81", CubeListBuilder.create().texOffs(262, 376).addBox(0.2605F, -4.2747F, 1.9385F, 2.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.904F, -2.8304F, -2.7677F, 0.6981F, 0.0873F, -0.0873F));

        PartDefinition cube_r82 = head.addOrReplaceChild("cube_r82", CubeListBuilder.create().texOffs(14, 379).addBox(-1.0F, -4.7189F, 1.2679F, 2.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.75F, -0.891F, -7.0603F, 0.4363F, 0.2618F, 0.0F));

        PartDefinition cube_r83 = head.addOrReplaceChild("cube_r83", CubeListBuilder.create().texOffs(52, 286).addBox(0.6384F, -2.8758F, 2.385F, 2.0F, 2.0F, 5.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(2.9581F, -6.2443F, 0.2206F, 1.2217F, -0.1745F, -0.3054F));

        PartDefinition cube_r84 = head.addOrReplaceChild("cube_r84", CubeListBuilder.create().texOffs(216, 300).addBox(-2.6384F, -2.8758F, 2.385F, 2.0F, 2.0F, 5.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(-2.9581F, -6.2443F, 0.2206F, 1.2217F, 0.1745F, 0.3054F));

        PartDefinition cube_r85 = head.addOrReplaceChild("cube_r85", CubeListBuilder.create().texOffs(214, 376).addBox(-2.2605F, -4.2747F, 1.9385F, 2.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.904F, -2.8304F, -2.7677F, 0.6981F, -0.0873F, 0.0873F));

        PartDefinition cube_r86 = head.addOrReplaceChild("cube_r86", CubeListBuilder.create().texOffs(374, 378).addBox(-1.0F, -4.7189F, 1.2679F, 2.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.75F, -0.891F, -7.0603F, 0.4363F, -0.2618F, 0.0F));

        PartDefinition cube_r87 = head.addOrReplaceChild("cube_r87", CubeListBuilder.create().texOffs(108, 375).addBox(0.2526F, -4.5543F, 1.5529F, 2.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.932F, -0.4771F, -2.936F, 0.5236F, 0.2618F, -0.0873F));

        PartDefinition cube_r88 = head.addOrReplaceChild("cube_r88", CubeListBuilder.create().texOffs(332, 376).addBox(-2.2526F, -4.5543F, 1.5529F, 2.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.932F, -0.4771F, -2.936F, 0.5236F, -0.2618F, 0.0873F));

        PartDefinition cube_r89 = head.addOrReplaceChild("cube_r89", CubeListBuilder.create().texOffs(302, 370).addBox(-1.0F, -4.8191F, 0.0261F, 2.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.0F, 1.109F, -7.3103F, 0.3491F, -0.4363F, 0.0F));

        PartDefinition cube_r90 = head.addOrReplaceChild("cube_r90", CubeListBuilder.create().texOffs(108, 371).addBox(0.25F, -5.25F, 0.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(5.0F, 2.859F, -8.8103F, 0.0F, -0.5236F, 0.0F));

        PartDefinition cube_r91 = head.addOrReplaceChild("cube_r91", CubeListBuilder.create().texOffs(314, 380).addBox(-1.875F, -2.9886F, 0.228F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(7.8922F, 0.359F, -3.5221F, 0.0873F, 0.0873F, 0.0F));

        PartDefinition cube_r92 = head.addOrReplaceChild("cube_r92", CubeListBuilder.create().texOffs(148, 381).addBox(-0.125F, -2.9886F, 0.228F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-7.8922F, 0.359F, -3.5221F, 0.0873F, -0.0873F, 0.0F));

        PartDefinition cube_r93 = head.addOrReplaceChild("cube_r93", CubeListBuilder.create().texOffs(380, 354).addBox(0.125F, -5.25F, 0.2165F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(-7.1651F, 2.859F, -7.7433F, 0.0F, -0.1745F, 0.0F));

        PartDefinition cube_r94 = head.addOrReplaceChild("cube_r94", CubeListBuilder.create().texOffs(204, 357).addBox(-2.25F, -5.25F, 0.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(-5.0F, 2.859F, -8.8103F, 0.0F, 0.5236F, 0.0F));

        PartDefinition cube_r95 = head.addOrReplaceChild("cube_r95", CubeListBuilder.create().texOffs(384, 233).addBox(-0.75F, -4.7243F, -0.6416F, 1.0F, 2.0F, 2.0F, new CubeDeformation(-0.25F))
                .texOffs(384, 229).addBox(-4.55F, -4.7243F, -0.6416F, 1.0F, 2.0F, 2.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(2.15F, 1.359F, -10.3103F, -0.1309F, 0.0F, 0.0F));

        PartDefinition cube_r96 = head.addOrReplaceChild("cube_r96", CubeListBuilder.create().texOffs(320, 247).addBox(-0.75F, -4.6478F, -1.0265F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(4.3F, 1.009F, -9.0103F, -0.2618F, -0.4363F, 0.0F));

        PartDefinition cube_r97 = head.addOrReplaceChild("cube_r97", CubeListBuilder.create().texOffs(262, 126).addBox(-2.25F, -3.75F, -0.25F, 3.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(-2.4F, 1.259F, -9.9603F, 0.0F, 0.4363F, 0.0F));

        PartDefinition cube_r98 = head.addOrReplaceChild("cube_r98", CubeListBuilder.create().texOffs(320, 250).addBox(-0.25F, -4.6478F, -1.0265F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(-4.3F, 1.009F, -9.0103F, -0.2618F, 0.4363F, 0.0F));

        PartDefinition cube_r99 = head.addOrReplaceChild("cube_r99", CubeListBuilder.create().texOffs(314, 101).addBox(-1.0F, -5.0F, -1.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.5F, 1.609F, -8.3103F, 0.0F, -0.4363F, 0.0F));

        PartDefinition cube_r100 = head.addOrReplaceChild("cube_r100", CubeListBuilder.create().texOffs(308, 101).addBox(-1.0F, -5.0F, -1.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.5F, 1.609F, -8.3103F, 0.0F, 0.4363F, 0.0F));

        PartDefinition cube_r101 = head.addOrReplaceChild("cube_r101", CubeListBuilder.create().texOffs(288, 380).addBox(-1.75F, -3.1478F, 0.5265F, 2.0F, 2.0F, 4.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(2.75F, -1.391F, -10.8103F, 0.2618F, 0.2618F, 0.0F));

        PartDefinition cube_r102 = head.addOrReplaceChild("cube_r102", CubeListBuilder.create().texOffs(302, 101).addBox(0.0F, -5.0F, 0.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.25F, 3.109F, -10.3103F, 0.0F, -0.8727F, 0.0F));

        PartDefinition cube_r103 = head.addOrReplaceChild("cube_r103", CubeListBuilder.create().texOffs(290, 101).addBox(0.9013F, -2.8612F, -0.0393F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.25F, 1.109F, -10.3103F, 0.3609F, -0.8601F, -0.4786F));

        PartDefinition cube_r104 = head.addOrReplaceChild("cube_r104", CubeListBuilder.create().texOffs(296, 101).addBox(-2.9013F, -2.8612F, -0.0393F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.25F, 1.109F, -10.3103F, 0.3609F, 0.8601F, 0.4786F));

        PartDefinition cube_r105 = head.addOrReplaceChild("cube_r105", CubeListBuilder.create().texOffs(284, 101).addBox(-2.0F, -5.0F, 0.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.25F, 3.109F, -10.3103F, 0.0F, 0.8727F, 0.0F));

        PartDefinition cube_r106 = head.addOrReplaceChild("cube_r106", CubeListBuilder.create().texOffs(32, 369).addBox(-0.75F, -4.5691F, -0.2239F, 1.0F, 2.0F, 4.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(-1.25F, -0.141F, -9.3103F, 0.3491F, -0.2618F, 0.0F));

        PartDefinition cube_r107 = head.addOrReplaceChild("cube_r107", CubeListBuilder.create().texOffs(252, 357).addBox(-0.25F, -4.5691F, -0.2239F, 1.0F, 2.0F, 4.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(1.25F, -0.141F, -9.3103F, 0.3491F, 0.2618F, 0.0F));

        PartDefinition cube_r108 = head.addOrReplaceChild("cube_r108", CubeListBuilder.create().texOffs(276, 380).addBox(-0.25F, -3.1478F, 0.5265F, 2.0F, 2.0F, 4.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(-2.75F, -1.391F, -10.8103F, 0.2618F, -0.2618F, 0.0F));

        PartDefinition cube_r109 = head.addOrReplaceChild("cube_r109", CubeListBuilder.create().texOffs(382, 66).addBox(0.2227F, -3.1478F, -0.634F, 2.0F, 2.0F, 3.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(-3.0F, -1.641F, -9.8103F, 0.4053F, -0.8841F, -0.2511F));

        PartDefinition cube_r110 = head.addOrReplaceChild("cube_r110", CubeListBuilder.create().texOffs(382, 61).addBox(-2.2227F, -3.1478F, -0.634F, 2.0F, 2.0F, 3.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(3.0F, -1.641F, -9.8103F, 0.4053F, 0.8841F, 0.2511F));

        PartDefinition cube_r111 = head.addOrReplaceChild("cube_r111", CubeListBuilder.create().texOffs(290, 126).addBox(-2.0F, -4.0F, -1.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, 3.109F, -9.9603F, 0.0F, 0.3491F, 0.0F));

        PartDefinition cube_r112 = head.addOrReplaceChild("cube_r112", CubeListBuilder.create().texOffs(246, 66).addBox(-2.0F, -3.8978F, -0.2235F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, 2.109F, -9.6603F, 0.2618F, 0.3491F, 0.0F));

        PartDefinition cube_r113 = head.addOrReplaceChild("cube_r113", CubeListBuilder.create().texOffs(254, 66).addBox(-1.0F, -3.8978F, -0.2235F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, 2.109F, -9.6603F, 0.2618F, -0.3491F, 0.0F));

        PartDefinition cube_r114 = head.addOrReplaceChild("cube_r114", CubeListBuilder.create().texOffs(284, 126).addBox(0.0F, -4.0F, -1.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, 3.109F, -9.9603F, 0.0F, -0.3491F, 0.0F));

        PartDefinition cube_r115 = head.addOrReplaceChild("cube_r115", CubeListBuilder.create().texOffs(384, 225).addBox(-1.3981F, -3.0216F, -0.25F, 1.0F, 2.0F, 2.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(-2.5F, 1.784F, -12.1603F, 0.0F, 0.0F, 0.3927F));

        PartDefinition cube_r116 = head.addOrReplaceChild("cube_r116", CubeListBuilder.create().texOffs(384, 221).addBox(-1.1521F, -3.1111F, -0.25F, 1.0F, 2.0F, 2.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(-2.5F, 1.759F, -13.4103F, 0.0F, 0.0F, 0.3054F));

        PartDefinition cube_r117 = head.addOrReplaceChild("cube_r117", CubeListBuilder.create().texOffs(384, 206).addBox(-0.8993F, -3.1789F, -0.25F, 1.0F, 2.0F, 2.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(-2.5F, 1.784F, -14.6603F, 0.0F, 0.0F, 0.2182F));

        PartDefinition cube_r118 = head.addOrReplaceChild("cube_r118", CubeListBuilder.create().texOffs(382, 312).addBox(-0.1007F, -3.1789F, -0.25F, 1.0F, 2.0F, 2.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(2.5F, 1.784F, -14.6603F, 0.0F, 0.0F, -0.2182F));

        PartDefinition cube_r119 = head.addOrReplaceChild("cube_r119", CubeListBuilder.create().texOffs(380, 360).addBox(0.1521F, -3.1111F, -0.25F, 1.0F, 2.0F, 2.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(2.5F, 1.759F, -13.4103F, 0.0F, 0.0F, -0.3054F));

        PartDefinition cube_r120 = head.addOrReplaceChild("cube_r120", CubeListBuilder.create().texOffs(276, 376).addBox(0.3981F, -3.0216F, -0.25F, 1.0F, 2.0F, 2.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(2.5F, 1.784F, -12.1603F, 0.0F, 0.0F, -0.3927F));

        PartDefinition cube_r121 = head.addOrReplaceChild("cube_r121", CubeListBuilder.create().texOffs(162, 372).addBox(-0.65F, -3.8978F, 0.5265F, 1.0F, 3.0F, 6.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(-1.1F, 1.609F, -14.6603F, 0.2618F, -0.0873F, 0.0F));

        PartDefinition cube_r122 = head.addOrReplaceChild("cube_r122", CubeListBuilder.create().texOffs(374, 364).addBox(-0.3809F, -3.2215F, 0.1412F, 2.0F, 2.0F, 5.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(-2.4F, 0.859F, -15.1603F, 0.1309F, 0.0F, 0.0436F));

        PartDefinition cube_r123 = head.addOrReplaceChild("cube_r123", CubeListBuilder.create().texOffs(148, 372).addBox(-0.35F, -3.8978F, 0.5265F, 1.0F, 3.0F, 6.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(1.1F, 1.609F, -14.6603F, 0.2618F, 0.0873F, 0.0F));

        PartDefinition cube_r124 = head.addOrReplaceChild("cube_r124", CubeListBuilder.create().texOffs(374, 371).addBox(-1.6191F, -3.2215F, 0.1412F, 2.0F, 2.0F, 5.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(2.4F, 0.859F, -15.1603F, 0.1309F, 0.0F, -0.0436F));

        PartDefinition cube_r125 = head.addOrReplaceChild("cube_r125", CubeListBuilder.create().texOffs(374, 161).addBox(-2.0F, -2.9544F, 0.5209F, 2.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 0.0355F, -14.9911F, 0.1745F, 0.0873F, 0.0F));

        PartDefinition cube_r126 = head.addOrReplaceChild("cube_r126", CubeListBuilder.create().texOffs(374, 168).addBox(0.0F, -2.9544F, 0.5209F, 2.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 0.0355F, -14.9911F, 0.1745F, -0.0873F, 0.0F));

        PartDefinition cube_r127 = head.addOrReplaceChild("cube_r127", CubeListBuilder.create().texOffs(374, 175).addBox(-1.0F, -2.9544F, 0.5209F, 2.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0355F, -14.9911F, 0.1745F, 0.0F, 0.0F));

        PartDefinition cube_r128 = head.addOrReplaceChild("cube_r128", CubeListBuilder.create().texOffs(278, 126).addBox(-1.0F, -2.4575F, 1.7207F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.609F, -15.8103F, 0.6109F, 0.0F, 0.0F));

        PartDefinition cube_r129 = head.addOrReplaceChild("cube_r129", CubeListBuilder.create().texOffs(384, 17).addBox(-1.75F, -5.75F, -0.25F, 2.0F, 3.0F, 2.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(-1.0F, 3.359F, -15.5603F, 0.0F, 0.3491F, 0.0F));

        PartDefinition cube_r130 = head.addOrReplaceChild("cube_r130", CubeListBuilder.create().texOffs(384, 12).addBox(-0.25F, -5.75F, -0.25F, 2.0F, 3.0F, 2.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(1.0F, 3.359F, -15.5603F, 0.0F, -0.3491F, 0.0F));

        PartDefinition cube_r131 = head.addOrReplaceChild("cube_r131", CubeListBuilder.create().texOffs(270, 126).addBox(-0.75F, -3.75F, -0.25F, 3.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(2.4F, 1.259F, -9.9603F, 0.0F, -0.4363F, 0.0F));

        PartDefinition cube_r132 = head.addOrReplaceChild("cube_r132", CubeListBuilder.create().texOffs(262, 66).addBox(0.1295F, -3.618F, -0.2809F, 3.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(2.4F, 0.259F, -9.7603F, 0.1745F, -0.5411F, -0.3491F));

        PartDefinition cube_r133 = head.addOrReplaceChild("cube_r133", CubeListBuilder.create().texOffs(270, 66).addBox(-3.1295F, -3.618F, -0.2809F, 3.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(-2.4F, 0.259F, -9.7603F, 0.1745F, 0.5411F, 0.3491F));

        PartDefinition jaw = head.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(262, 372).addBox(-1.0F, 0.0F, -6.75F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(368, 38).addBox(-2.5F, 0.0F, -5.75F, 3.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(368, 74).addBox(-0.5F, 0.0F, -5.75F, 3.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.359F, -8.8103F));

        PartDefinition cube_r134 = jaw.addOrReplaceChild("cube_r134", CubeListBuilder.create().texOffs(176, 372).addBox(0.0F, -2.9886F, -0.2615F, 0.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 4.75F, -6.25F, -0.0873F, 0.0F, 0.0F));

        PartDefinition cube_r135 = jaw.addOrReplaceChild("cube_r135", CubeListBuilder.create().texOffs(332, 101).addBox(0.0F, -4.0F, 0.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 4.0F, -6.75F, 0.0F, -0.7854F, 0.0F));

        PartDefinition cube_r136 = jaw.addOrReplaceChild("cube_r136", CubeListBuilder.create().texOffs(326, 46).addBox(0.0F, -5.0F, 0.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.75F, 4.75F, -1.75F, 0.0F, -0.3491F, 0.0F));

        PartDefinition cube_r137 = jaw.addOrReplaceChild("cube_r137", CubeListBuilder.create().texOffs(320, 101).addBox(-2.0F, -5.0F, 0.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.75F, 4.75F, -1.75F, 0.0F, 0.3491F, 0.0F));

        PartDefinition cube_r138 = jaw.addOrReplaceChild("cube_r138", CubeListBuilder.create().texOffs(326, 101).addBox(-2.0F, -4.0F, 0.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 4.0F, -6.75F, 0.0F, 0.7854F, 0.0F));

        PartDefinition cube_r139 = jaw.addOrReplaceChild("cube_r139", CubeListBuilder.create().texOffs(136, 320).addBox(-1.0F, -3.9886F, -0.2615F, 2.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 5.25F, -5.75F, -0.0873F, 0.0F, 0.0F));

        PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offset(0.0F, -7.0F, 26.0F));

        PartDefinition cube_r140 = tail.addOrReplaceChild("cube_r140", CubeListBuilder.create().texOffs(342, 110).addBox(-3.5F, -2.0F, 0.0F, 7.0F, 2.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(66, 235).addBox(-3.5F, -1.75F, 15.95F, 7.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(134, 346).addBox(-3.5F, -2.25F, 8.0F, 7.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 5.3586F, -0.9183F, -0.1745F, 0.0F, 0.0F));

        PartDefinition cube_r141 = tail.addOrReplaceChild("cube_r141", CubeListBuilder.create().texOffs(152, 300).addBox(0.0F, -10.0F, -1.0F, 0.0F, 6.0F, 21.0F, new CubeDeformation(0.0F))
                .texOffs(264, 241).addBox(-4.0F, -4.0F, 0.0F, 8.0F, 8.0F, 20.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.1745F, 0.0F, 0.0F));

        PartDefinition tail1 = tail.addOrReplaceChild("tail1", CubeListBuilder.create(), PartPose.offset(0.0F, 3.5F, 18.75F));

        PartDefinition cube_r142 = tail1.addOrReplaceChild("cube_r142", CubeListBuilder.create().texOffs(284, 49).addBox(-2.5F, 0.0F, 0.0F, 5.0F, 2.0F, 20.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.0F, -0.175F, -0.0873F, 0.0F, 0.0F));

        PartDefinition cube_r143 = tail1.addOrReplaceChild("cube_r143", CubeListBuilder.create().texOffs(96, 320).addBox(0.0F, -9.0F, 0.0F, 0.0F, 6.0F, 20.0F, new CubeDeformation(0.0F))
                .texOffs(126, 274).addBox(-3.0F, -3.0F, 0.0F, 6.0F, 6.0F, 20.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.0873F, 0.0F, 0.0F));

        PartDefinition tail2 = tail1.addOrReplaceChild("tail2", CubeListBuilder.create().texOffs(278, 0).addBox(-2.5F, -2.0F, 0.0F, 5.0F, 5.0F, 20.0F, new CubeDeformation(0.0F))
                .texOffs(0, 323).addBox(0.0F, -7.0F, 0.0F, 0.0F, 5.0F, 20.0F, new CubeDeformation(0.0F))
                .texOffs(300, 140).addBox(-2.0F, 3.0F, 0.0F, 4.0F, 1.0F, 20.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 1.25F, 19.5F));

        PartDefinition tail3 = tail2.addOrReplaceChild("tail3", CubeListBuilder.create(), PartPose.offset(0.0F, 0.5F, 19.0F));

        PartDefinition cube_r144 = tail3.addOrReplaceChild("cube_r144", CubeListBuilder.create().texOffs(194, 307).addBox(-1.5F, 1.25F, 0.0F, 3.0F, 1.0F, 20.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.1309F, 0.0F, 0.0F));

        PartDefinition cube_r145 = tail3.addOrReplaceChild("cube_r145", CubeListBuilder.create().texOffs(324, 247).addBox(0.0F, -4.0F, 0.0F, 0.0F, 4.0F, 20.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -2.25F, 0.0F, 0.0873F, 0.0F, 0.0F));

        PartDefinition cube_r146 = tail3.addOrReplaceChild("cube_r146", CubeListBuilder.create().texOffs(278, 25).addBox(-2.0F, -1.75F, 0.0F, 4.0F, 4.0F, 20.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, 0.0F, 0.0873F, 0.0F, 0.0F));

        PartDefinition tail4 = tail3.addOrReplaceChild("tail4", CubeListBuilder.create(), PartPose.offset(0.0F, -1.9F, 18.975F));

        PartDefinition cube_r147 = tail4.addOrReplaceChild("cube_r147", CubeListBuilder.create().texOffs(300, 161).addBox(-1.5F, -1.5F, 0.0F, 3.0F, 3.0F, 20.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0873F, 0.0F, 0.0F));

        PartDefinition tail5 = tail4.addOrReplaceChild("tail5", CubeListBuilder.create(), PartPose.offset(0.0F, -1.75F, 19.0F));

        PartDefinition cube_r148 = tail5.addOrReplaceChild("cube_r148", CubeListBuilder.create().texOffs(48, 313).addBox(0.0F, 0.0F, -12.0F, 6.0F, 0.0F, 18.0F, new CubeDeformation(0.0F))
                .texOffs(288, 307).addBox(-8.0F, 0.0F, -12.0F, 6.0F, 0.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, -2.0F, 22.0F, 0.0873F, 0.0F, 0.0F));

        PartDefinition cube_r149 = tail5.addOrReplaceChild("cube_r149", CubeListBuilder.create().texOffs(178, 274).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 24.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0873F, 0.0F, 0.0F));

        PartDefinition right_TailFin = tail5.addOrReplaceChild("right_TailFin", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0873F, 0.0F, 0.0F));

        PartDefinition cube_r150 = right_TailFin.addOrReplaceChild("cube_r150", CubeListBuilder.create().texOffs(300, 378).addBox(-1.0F, -1.0F, 0.0F, 1.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-15.774F, 0.0F, 13.4317F, 0.0F, -0.3491F, 0.0F));

        PartDefinition cube_r151 = right_TailFin.addOrReplaceChild("cube_r151", CubeListBuilder.create().texOffs(52, 293).addBox(0.0F, -0.5F, 0.0F, 5.0F, 0.0F, 20.0F, new CubeDeformation(0.0F))
                .texOffs(240, 325).addBox(-1.0F, -1.0F, 0.0F, 1.0F, 1.0F, 20.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.75F, 0.0F, 1.0F, 0.0F, -0.8727F, 0.0F));

        PartDefinition right_tailFin1 = right_TailFin.addOrReplaceChild("right_tailFin1", CubeListBuilder.create(), PartPose.offset(-1.0F, 0.0F, 0.0F));

        PartDefinition cube_r152 = right_tailFin1.addOrReplaceChild("cube_r152", CubeListBuilder.create().texOffs(298, 208).addBox(0.0F, -0.5F, 0.0F, 5.0F, 0.0F, 20.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.25F, 0.0F, 0.0F, 0.0F, -0.5236F, 0.0F));

        PartDefinition cube_r153 = right_tailFin1.addOrReplaceChild("cube_r153", CubeListBuilder.create().texOffs(372, 46).addBox(-1.0F, -1.0F, 0.0F, 1.0F, 1.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-9.6501F, 0.0F, 18.0793F, 0.0F, -0.2618F, 0.0F));

        PartDefinition cube_r154 = right_tailFin1.addOrReplaceChild("cube_r154", CubeListBuilder.create().texOffs(324, 325).addBox(-1.0F, -1.0F, 0.0F, 1.0F, 1.0F, 20.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.25F, 0.0F, 1.0F, 0.0F, -0.5236F, 0.0F));

        PartDefinition left_TailFin = tail5.addOrReplaceChild("left_TailFin", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0873F, 0.0F, 0.0F));

        PartDefinition cube_r155 = left_TailFin.addOrReplaceChild("cube_r155", CubeListBuilder.create().texOffs(282, 325).addBox(0.0F, -1.0F, 0.0F, 1.0F, 1.0F, 20.0F, new CubeDeformation(0.0F))
                .texOffs(300, 120).addBox(-5.0F, -0.5F, 0.0F, 5.0F, 0.0F, 20.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.75F, 0.0F, 1.0F, 0.0F, 0.8727F, 0.0F));

        PartDefinition cube_r156 = left_TailFin.addOrReplaceChild("cube_r156", CubeListBuilder.create().texOffs(122, 377).addBox(-3.8568F, 0.0F, 4.7375F, 1.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(17.7779F, -1.0F, 7.6609F, 0.0F, 0.3491F, 0.0F));

        PartDefinition left_tailFin1 = left_TailFin.addOrReplaceChild("left_tailFin1", CubeListBuilder.create(), PartPose.offset(1.0F, 0.0F, 0.0F));

        PartDefinition cube_r157 = left_tailFin1.addOrReplaceChild("cube_r157", CubeListBuilder.create().texOffs(102, 300).addBox(-5.0F, -0.5F, 0.0F, 5.0F, 0.0F, 20.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.25F, 0.0F, 0.0F, 0.0F, 0.5236F, 0.0F));

        PartDefinition cube_r158 = left_tailFin1.addOrReplaceChild("cube_r158", CubeListBuilder.create().texOffs(372, 110).addBox(0.0F, -1.0F, 0.0F, 1.0F, 1.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(9.6501F, 0.0F, 18.0793F, 0.0F, 0.2618F, 0.0F));

        PartDefinition cube_r159 = left_tailFin1.addOrReplaceChild("cube_r159", CubeListBuilder.create().texOffs(326, 25).addBox(0.0F, -1.0F, 0.0F, 1.0F, 1.0F, 20.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.25F, 0.0F, 1.0F, 0.0F, 0.5236F, 0.0F));

        PartDefinition IK_TAIL = tail5.addOrReplaceChild("IK_TAIL", CubeListBuilder.create(), PartPose.offset(0.0F, -2.0F, 24.0F));

        PartDefinition right_midfin = tail2.addOrReplaceChild("right_midfin", CubeListBuilder.create(), PartPose.offsetAndRotation(-2.0F, 0.0F, 0.0F, 0.0F, -0.2182F, 0.0F));

        PartDefinition cube_r160 = right_midfin.addOrReplaceChild("cube_r160", CubeListBuilder.create().texOffs(0, 286).addBox(0.0F, -0.5F, 0.0F, 8.0F, 0.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.0F, 0.0F, 0.0F, -0.6109F, 0.0F));

        PartDefinition cube_r161 = right_midfin.addOrReplaceChild("cube_r161", CubeListBuilder.create().texOffs(46, 377).addBox(0.0F, -1.0F, 0.0F, 1.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-11.3244F, 2.0F, 14.7447F, 0.0F, 0.1745F, 0.0F));

        PartDefinition cube_r162 = right_midfin.addOrReplaceChild("cube_r162", CubeListBuilder.create().texOffs(136, 327).addBox(0.0F, -1.0F, 0.0F, 1.0F, 1.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 2.0F, 0.0F, 0.0F, -0.6109F, 0.0F));

        PartDefinition bone3 = right_midfin.addOrReplaceChild("bone3", CubeListBuilder.create(), PartPose.offsetAndRotation(-1.0F, 0.0F, 0.0F, 0.0F, 0.5236F, 0.0F));

        PartDefinition cube_r163 = bone3.addOrReplaceChild("cube_r163", CubeListBuilder.create().texOffs(60, 377).addBox(0.0F, -1.0F, 0.0F, 1.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-11.5702F, 0.0F, 13.7888F, 0.0F, 0.0873F, 0.0F));

        PartDefinition cube_r164 = bone3.addOrReplaceChild("cube_r164", CubeListBuilder.create().texOffs(230, 289).addBox(0.0F, -0.5F, 0.0F, 8.0F, 0.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 0.0F, 0.0F, 0.0F, -0.6981F, 0.0F));

        PartDefinition cube_r165 = bone3.addOrReplaceChild("cube_r165", CubeListBuilder.create().texOffs(174, 328).addBox(0.0F, -1.0F, 0.0F, 1.0F, 1.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.6981F, 0.0F));

        PartDefinition left_midfin = tail2.addOrReplaceChild("left_midfin", CubeListBuilder.create(), PartPose.offsetAndRotation(2.0F, 0.0F, 0.0F, 0.0F, 0.2182F, 0.0F));

        PartDefinition cube_r166 = left_midfin.addOrReplaceChild("cube_r166", CubeListBuilder.create().texOffs(40, 331).addBox(-1.0F, -1.0F, 0.0F, 1.0F, 1.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 2.0F, 0.0F, 0.0F, 0.6109F, 0.0F));

        PartDefinition cube_r167 = left_midfin.addOrReplaceChild("cube_r167", CubeListBuilder.create().texOffs(282, 289).addBox(-8.0F, -0.5F, 0.0F, 8.0F, 0.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.0F, 0.0F, 0.0F, 0.6109F, 0.0F));

        PartDefinition cube_r168 = left_midfin.addOrReplaceChild("cube_r168", CubeListBuilder.create().texOffs(74, 377).addBox(-1.0F, -1.0F, 0.0F, 1.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(11.3244F, 2.0F, 14.7447F, 0.0F, -0.1745F, 0.0F));

        PartDefinition bone4 = left_midfin.addOrReplaceChild("bone4", CubeListBuilder.create(), PartPose.offsetAndRotation(1.0F, 0.0F, 0.0F, 0.0F, -0.5236F, 0.0F));

        PartDefinition cube_r169 = bone4.addOrReplaceChild("cube_r169", CubeListBuilder.create().texOffs(328, 0).addBox(-1.0F, -1.0F, 0.0F, 1.0F, 1.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.6981F, 0.0F));

        PartDefinition cube_r170 = bone4.addOrReplaceChild("cube_r170", CubeListBuilder.create().texOffs(32, 377).addBox(-1.0F, -1.0F, 0.0F, 1.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(11.5702F, 0.0F, 13.7888F, 0.0F, -0.0873F, 0.0F));

        PartDefinition cube_r171 = bone4.addOrReplaceChild("cube_r171", CubeListBuilder.create().texOffs(284, 71).addBox(-8.0F, -0.5F, 0.0F, 8.0F, 0.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 0.0F, 0.0F, 0.0F, 0.6981F, 0.0F));

        PartDefinition right_frontLeg = body.addOrReplaceChild("right_frontLeg", CubeListBuilder.create(), PartPose.offset(-9.5F, -3.0F, -14.0F));

        PartDefinition cube_r172 = right_frontLeg.addOrReplaceChild("cube_r172", CubeListBuilder.create().texOffs(40, 323).addBox(0.0F, -4.0F, -1.0F, 1.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.0F, 2.55F, 1.55F, 0.6109F, 0.0F, 0.0F));

        PartDefinition cube_r173 = right_frontLeg.addOrReplaceChild("cube_r173", CubeListBuilder.create().texOffs(384, 253).addBox(0.0F, -2.0F, -2.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -1.3484F, 1.0F, 1.7453F, 0.0F, 0.0F));

        PartDefinition cube_r174 = right_frontLeg.addOrReplaceChild("cube_r174", CubeListBuilder.create().texOffs(60, 384).addBox(0.0F, -2.0F, -2.0F, 1.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.4087F, 0.6579F, 1.2217F, 0.0F, 0.0F));

        PartDefinition cube_r175 = right_frontLeg.addOrReplaceChild("cube_r175", CubeListBuilder.create().texOffs(368, 281).addBox(-4.0F, -3.0F, 0.0F, 5.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.4177F, 3.0001F, -2.72F, 0.4363F, 0.0F, 0.0F));

        PartDefinition cube_r176 = right_frontLeg.addOrReplaceChild("cube_r176", CubeListBuilder.create().texOffs(368, 155).addBox(-4.0F, -3.0F, 0.0F, 5.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.4177F, 4.8264F, -0.3399F, 0.9163F, 0.0F, 0.0F));

        PartDefinition cube_r177 = right_frontLeg.addOrReplaceChild("cube_r177", CubeListBuilder.create().texOffs(370, 82).addBox(-4.0F, 0.5F, 0.0F, 5.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.4177F, -4.7088F, -1.5952F, -0.4451F, 0.0F, 0.0F));

        PartDefinition cube_r178 = right_frontLeg.addOrReplaceChild("cube_r178", CubeListBuilder.create().texOffs(364, 263).addBox(-4.0F, 0.0F, -5.0F, 5.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.4177F, -2.9987F, 3.1032F, -0.3491F, 0.0F, 0.0F));

        PartDefinition cube_r179 = right_frontLeg.addOrReplaceChild("cube_r179", CubeListBuilder.create().texOffs(336, 367).addBox(-4.0F, -5.0F, -4.0F, 5.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.4177F, 1.9585F, 3.7559F, 0.1309F, 0.0F, 0.0F));

        PartDefinition cube_r180 = right_frontLeg.addOrReplaceChild("cube_r180", CubeListBuilder.create().texOffs(366, 338).addBox(-4.0F, -2.0F, 0.0F, 5.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.4177F, 4.8264F, -0.3399F, 0.6109F, 0.0F, 0.0F));

        PartDefinition cube_r181 = right_frontLeg.addOrReplaceChild("cube_r181", CubeListBuilder.create().texOffs(368, 135).addBox(0.0F, -6.0F, -4.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0823F, 7.0854F, 6.6753F, 0.6109F, 0.0F, 0.0F));

        PartDefinition right_lowerLeg = right_frontLeg.addOrReplaceChild("right_lowerLeg", CubeListBuilder.create(), PartPose.offset(0.0F, 6.75F, 4.5F));

        PartDefinition cube_r182 = right_lowerLeg.addOrReplaceChild("cube_r182", CubeListBuilder.create().texOffs(204, 379).addBox(0.0F, -6.0F, 0.0F, 0.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 4.0F, 0.0F, -0.2182F, 0.0F, 0.0F));

        PartDefinition cube_r183 = right_lowerLeg.addOrReplaceChild("cube_r183", CubeListBuilder.create().texOffs(384, 196).addBox(-1.0F, -4.0F, -1.0F, 2.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.05F, 3.0138F, -2.4204F, -0.2618F, 0.0F, 0.0F));

        PartDefinition cube_r184 = right_lowerLeg.addOrReplaceChild("cube_r184", CubeListBuilder.create().texOffs(278, 59).addBox(-1.0F, -6.0F, 0.0F, 1.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.05F, 9.0F, -3.025F, -0.2618F, 0.0F, -0.1745F));

        PartDefinition cube_r185 = right_lowerLeg.addOrReplaceChild("cube_r185", CubeListBuilder.create().texOffs(236, 357).addBox(-3.0F, -10.0F, -1.0F, 4.0F, 10.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.95F, 9.0F, -3.025F, -0.2618F, 0.0F, 0.0F));

        PartDefinition right_foot = right_lowerLeg.addOrReplaceChild("right_foot", CubeListBuilder.create().texOffs(384, 237).addBox(-0.5F, 1.25F, -5.75F, 1.0F, 2.0F, 2.0F, new CubeDeformation(-0.25F))
                .texOffs(28, 384).addBox(-0.5F, 1.0F, -4.5F, 1.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 9.25F, -2.0F));

        PartDefinition cube_r186 = right_foot.addOrReplaceChild("cube_r186", CubeListBuilder.create().texOffs(88, 379).addBox(-3.0F, -3.0F, -1.0F, 4.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 2.5F, -1.0F, -0.1745F, 0.0F, 0.0F));

        PartDefinition cube_r187 = right_foot.addOrReplaceChild("cube_r187", CubeListBuilder.create().texOffs(52, 384).addBox(-1.0F, -2.0F, 0.0F, 1.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.5F, 3.0F, 1.5F, 0.0F, -2.0071F, 0.0F));

        PartDefinition cube_r188 = right_foot.addOrReplaceChild("cube_r188", CubeListBuilder.create().texOffs(44, 384).addBox(0.0F, -2.0F, 0.0F, 1.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.5657F, 3.0F, -3.9771F, 0.0F, 0.3491F, 0.0F));

        PartDefinition cube_r189 = right_foot.addOrReplaceChild("cube_r189", CubeListBuilder.create().texOffs(36, 384).addBox(0.0F, -2.0F, 0.0F, 1.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.6261F, 3.0F, -4.3191F, 0.0F, -0.3491F, 0.0F));

        PartDefinition cube_r190 = right_foot.addOrReplaceChild("cube_r190", CubeListBuilder.create().texOffs(384, 249).addBox(-0.25F, -1.75F, -1.75F, 1.0F, 2.0F, 2.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(3.4F, 3.0F, 0.6F, 0.0F, -2.0071F, 0.0F));

        PartDefinition cube_r191 = right_foot.addOrReplaceChild("cube_r191", CubeListBuilder.create().texOffs(384, 245).addBox(-0.75F, -1.75F, -1.75F, 1.0F, 2.0F, 2.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(-1.75F, 3.0F, -3.75F, 0.0F, 0.3491F, 0.0F));

        PartDefinition cube_r192 = right_foot.addOrReplaceChild("cube_r192", CubeListBuilder.create().texOffs(384, 241).addBox(-0.75F, -1.75F, -1.75F, 1.0F, 2.0F, 2.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(2.25F, 3.0F, -3.5F, 0.0F, -0.3491F, 0.0F));

        PartDefinition right_backLeg = body.addOrReplaceChild("right_backLeg", CubeListBuilder.create(), PartPose.offset(-8.5F, -7.0F, 16.0F));

        PartDefinition cube_r193 = right_backLeg.addOrReplaceChild("cube_r193", CubeListBuilder.create().texOffs(256, 380).addBox(0.0F, -8.0F, 0.0F, 1.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.5F, 0.0F, -4.0F, -0.8727F, 0.0F, 0.0F));

        PartDefinition cube_r194 = right_backLeg.addOrReplaceChild("cube_r194", CubeListBuilder.create().texOffs(278, 49).addBox(0.0F, -8.0F, 0.0F, 1.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.5F, 3.0F, -3.0F, -0.8727F, 0.0F, 0.0F));

        PartDefinition cube_r195 = right_backLeg.addOrReplaceChild("cube_r195", CubeListBuilder.create().texOffs(334, 203).addBox(-5.0F, -3.0F, -2.0F, 5.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.5F, 5.6924F, 0.7944F, -0.9163F, 0.0F, 0.0F));

        PartDefinition cube_r196 = right_backLeg.addOrReplaceChild("cube_r196", CubeListBuilder.create().texOffs(92, 371).addBox(-5.0F, -5.0F, -3.0F, 5.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.5F, 12.2313F, -4.2433F, -0.9163F, 0.0F, 0.0F));

        PartDefinition cube_r197 = right_backLeg.addOrReplaceChild("cube_r197", CubeListBuilder.create().texOffs(286, 372).addBox(-5.0F, 0.0F, 0.0F, 5.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.5F, -2.4471F, 2.7955F, -0.2618F, 0.0F, 0.0F));

        PartDefinition cube_r198 = right_backLeg.addOrReplaceChild("cube_r198", CubeListBuilder.create().texOffs(78, 331).addBox(-5.0F, 0.0F, 0.0F, 5.0F, 12.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.5F, -3.2235F, -0.1022F, -0.2618F, 0.0F, 0.0F));

        PartDefinition cube_r199 = right_backLeg.addOrReplaceChild("cube_r199", CubeListBuilder.create().texOffs(348, 197).addBox(-5.0F, 0.0F, 0.0F, 5.0F, 16.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.5F, -4.0F, -3.0F, -0.2618F, 0.0F, 0.0F));

        PartDefinition right_backLowerLeg = right_backLeg.addOrReplaceChild("right_backLowerLeg", CubeListBuilder.create(), PartPose.offset(0.0F, 11.0F, -4.0F));

        PartDefinition cube_r200 = right_backLowerLeg.addOrReplaceChild("cube_r200", CubeListBuilder.create().texOffs(226, 361).addBox(-3.0F, -1.0F, 2.0F, 4.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 3.0642F, -2.5711F, 0.8727F, 0.0F, 0.0F));

        PartDefinition cube_r201 = right_backLowerLeg.addOrReplaceChild("cube_r201", CubeListBuilder.create().texOffs(358, 364).addBox(-3.0F, 0.0F, -1.0F, 4.0F, 8.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 0.0F, 0.0F, 0.8727F, 0.0F, 0.0F));

        PartDefinition right_topfoot = right_backLowerLeg.addOrReplaceChild("right_topfoot", CubeListBuilder.create(), PartPose.offset(0.0F, 4.5F, 6.5F));

        PartDefinition cube_r202 = right_topfoot.addOrReplaceChild("cube_r202", CubeListBuilder.create().texOffs(204, 347).addBox(0.0F, -6.0F, 0.0F, 0.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.0F, 0.0F, -0.2182F, 0.0F, 0.0F));

        PartDefinition cube_r203 = right_topfoot.addOrReplaceChild("cube_r203", CubeListBuilder.create().texOffs(42, 365).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 8.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.5F, 0.0F, -0.2182F, 0.0F, 0.0F));

        PartDefinition cube_r204 = right_topfoot.addOrReplaceChild("cube_r204", CubeListBuilder.create().texOffs(384, 273).addBox(-1.0F, -2.0F, 0.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, 5.5F, -0.5F, -0.6109F, 0.0F, 0.0F));

        PartDefinition cube_r205 = right_topfoot.addOrReplaceChild("cube_r205", CubeListBuilder.create().texOffs(384, 269).addBox(-0.25F, -0.85F, -1.75F, 1.0F, 2.0F, 2.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(-0.25F, 6.5F, 2.6F, -0.6109F, 0.0F, 0.0F));

        PartDefinition right_foot2 = right_topfoot.addOrReplaceChild("right_foot2", CubeListBuilder.create().texOffs(384, 257).addBox(-0.75F, 0.25F, -4.75F, 1.0F, 2.0F, 2.0F, new CubeDeformation(-0.25F))
                .texOffs(68, 384).addBox(-0.75F, 0.0F, -3.5F, 1.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.25F, 5.5F, -2.0F));

        PartDefinition cube_r206 = right_foot2.addOrReplaceChild("cube_r206", CubeListBuilder.create().texOffs(88, 235).addBox(-1.5F, 0.0F, 0.0F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.25F, -0.4F, -1.0F, -0.0873F, 0.0F, 0.0F));

        PartDefinition cube_r207 = right_foot2.addOrReplaceChild("cube_r207", CubeListBuilder.create().texOffs(118, 384).addBox(0.0F, -2.0F, 0.0F, 1.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.8157F, 2.0F, -2.9771F, 0.0F, 0.3491F, 0.0F));

        PartDefinition cube_r208 = right_foot2.addOrReplaceChild("cube_r208", CubeListBuilder.create().texOffs(76, 384).addBox(0.0F, -2.0F, 0.0F, 1.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.3761F, 2.0F, -3.3191F, 0.0F, -0.3491F, 0.0F));

        PartDefinition cube_r209 = right_foot2.addOrReplaceChild("cube_r209", CubeListBuilder.create().texOffs(384, 265).addBox(-0.75F, -1.75F, -1.75F, 1.0F, 2.0F, 2.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(-2.0F, 2.0F, -2.75F, 0.0F, 0.3491F, 0.0F));

        PartDefinition cube_r210 = right_foot2.addOrReplaceChild("cube_r210", CubeListBuilder.create().texOffs(384, 261).addBox(-0.75F, -1.75F, -1.75F, 1.0F, 2.0F, 2.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(2.0F, 2.0F, -2.5F, 0.0F, -0.3491F, 0.0F));

        PartDefinition left_backLeg = body.addOrReplaceChild("left_backLeg", CubeListBuilder.create(), PartPose.offset(8.5F, -7.0F, 16.0F));

        PartDefinition cube_r211 = left_backLeg.addOrReplaceChild("cube_r211", CubeListBuilder.create().texOffs(326, 380).addBox(-1.0F, -8.0F, 0.0F, 1.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.5F, 3.0F, -3.0F, -0.8727F, 0.0F, 0.0F));

        PartDefinition cube_r212 = left_backLeg.addOrReplaceChild("cube_r212", CubeListBuilder.create().texOffs(102, 379).addBox(-1.0F, -8.0F, 0.0F, 1.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.5F, 0.0F, -4.0F, -0.8727F, 0.0F, 0.0F));

        PartDefinition cube_r213 = left_backLeg.addOrReplaceChild("cube_r213", CubeListBuilder.create().texOffs(348, 156).addBox(0.0F, -3.0F, -2.0F, 5.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.5F, 5.6924F, 0.7944F, -0.9163F, 0.0F, 0.0F));

        PartDefinition cube_r214 = left_backLeg.addOrReplaceChild("cube_r214", CubeListBuilder.create().texOffs(232, 371).addBox(0.0F, -5.0F, -3.0F, 5.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.5F, 12.2313F, -4.2433F, -0.9163F, 0.0F, 0.0F));

        PartDefinition cube_r215 = left_backLeg.addOrReplaceChild("cube_r215", CubeListBuilder.create().texOffs(318, 372).addBox(0.0F, 0.0F, 0.0F, 5.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.5F, -2.4471F, 2.7955F, -0.2618F, 0.0F, 0.0F));

        PartDefinition cube_r216 = left_backLeg.addOrReplaceChild("cube_r216", CubeListBuilder.create().texOffs(62, 350).addBox(0.0F, 0.0F, 0.0F, 5.0F, 12.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.5F, -3.2235F, -0.1022F, -0.2618F, 0.0F, 0.0F));

        PartDefinition cube_r217 = left_backLeg.addOrReplaceChild("cube_r217", CubeListBuilder.create().texOffs(26, 350).addBox(0.0F, 0.0F, 0.0F, 5.0F, 16.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.5F, -4.0F, -3.0F, -0.2618F, 0.0F, 0.0F));

        PartDefinition left_backLowerLeg = left_backLeg.addOrReplaceChild("left_backLowerLeg", CubeListBuilder.create(), PartPose.offset(0.0F, 11.0F, -4.0F));

        PartDefinition cube_r218 = left_backLowerLeg.addOrReplaceChild("cube_r218", CubeListBuilder.create().texOffs(148, 364).addBox(-1.0F, -1.0F, 2.0F, 4.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 3.0642F, -2.5711F, 0.8727F, 0.0F, 0.0F));

        PartDefinition cube_r219 = left_backLowerLeg.addOrReplaceChild("cube_r219", CubeListBuilder.create().texOffs(58, 365).addBox(-1.0F, 0.0F, -1.0F, 4.0F, 8.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 0.0F, 0.0F, 0.8727F, 0.0F, 0.0F));

        PartDefinition left_topfoot = left_backLowerLeg.addOrReplaceChild("left_topfoot", CubeListBuilder.create(), PartPose.offset(0.0F, 4.5F, 6.5F));

        PartDefinition cube_r220 = left_topfoot.addOrReplaceChild("cube_r220", CubeListBuilder.create().texOffs(16, 358).addBox(0.0F, -6.0F, 0.0F, 0.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.0F, 0.0F, -0.2182F, 0.0F, 0.0F));

        PartDefinition cube_r221 = left_topfoot.addOrReplaceChild("cube_r221", CubeListBuilder.create().texOffs(366, 0).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 8.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.5F, 0.0F, -0.2182F, 0.0F, 0.0F));

        PartDefinition cube_r222 = left_topfoot.addOrReplaceChild("cube_r222", CubeListBuilder.create().texOffs(384, 288).addBox(0.0F, -2.0F, 0.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, 5.5F, -0.5F, -0.6109F, 0.0F, 0.0F));

        PartDefinition cube_r223 = left_topfoot.addOrReplaceChild("cube_r223", CubeListBuilder.create().texOffs(384, 277).addBox(-0.75F, -0.85F, -1.75F, 1.0F, 2.0F, 2.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(0.25F, 6.5F, 2.6F, -0.6109F, 0.0F, 0.0F));

        PartDefinition left_foot2 = left_topfoot.addOrReplaceChild("left_foot2", CubeListBuilder.create().texOffs(384, 292).addBox(-0.25F, 0.25F, -4.75F, 1.0F, 2.0F, 2.0F, new CubeDeformation(-0.25F))
                .texOffs(126, 384).addBox(-0.25F, 0.0F, -3.5F, 1.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.25F, 5.5F, -2.0F));

        PartDefinition cube_r224 = left_foot2.addOrReplaceChild("cube_r224", CubeListBuilder.create().texOffs(372, 221).addBox(-1.5F, 0.0F, 0.0F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.25F, -0.4F, -1.0F, -0.0873F, 0.0F, 0.0F));

        PartDefinition cube_r225 = left_foot2.addOrReplaceChild("cube_r225", CubeListBuilder.create().texOffs(384, 135).addBox(-1.0F, -2.0F, 0.0F, 1.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.8157F, 2.0F, -2.9771F, 0.0F, -0.3491F, 0.0F));

        PartDefinition cube_r226 = left_foot2.addOrReplaceChild("cube_r226", CubeListBuilder.create().texOffs(134, 384).addBox(-1.0F, -2.0F, 0.0F, 1.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.3761F, 2.0F, -3.3191F, 0.0F, 0.3491F, 0.0F));

        PartDefinition cube_r227 = left_foot2.addOrReplaceChild("cube_r227", CubeListBuilder.create().texOffs(384, 320).addBox(-0.25F, -1.75F, -1.75F, 1.0F, 2.0F, 2.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(2.0F, 2.0F, -2.75F, 0.0F, -0.3491F, 0.0F));

        PartDefinition cube_r228 = left_foot2.addOrReplaceChild("cube_r228", CubeListBuilder.create().texOffs(384, 316).addBox(-0.25F, -1.75F, -1.75F, 1.0F, 2.0F, 2.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(-2.0F, 2.0F, -2.5F, 0.0F, 0.3491F, 0.0F));

        PartDefinition left_frontLeg = body.addOrReplaceChild("left_frontLeg", CubeListBuilder.create(), PartPose.offset(9.5F, -3.0F, -14.0F));

        PartDefinition cube_r229 = left_frontLeg.addOrReplaceChild("cube_r229", CubeListBuilder.create().texOffs(362, 383).addBox(-1.0F, -4.0F, -1.0F, 1.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, 2.55F, 1.55F, 0.6109F, 0.0F, 0.0F));

        PartDefinition cube_r230 = left_frontLeg.addOrReplaceChild("cube_r230", CubeListBuilder.create().texOffs(96, 385).addBox(-1.0F, -2.0F, -2.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.9F, -1.3484F, 1.0F, 1.7453F, 0.0F, 0.0F));

        PartDefinition cube_r231 = left_frontLeg.addOrReplaceChild("cube_r231", CubeListBuilder.create().texOffs(384, 181).addBox(-1.0F, -2.0F, -2.0F, 1.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.9F, -0.4087F, 0.6579F, 1.2217F, 0.0F, 0.0F));

        PartDefinition cube_r232 = left_frontLeg.addOrReplaceChild("cube_r232", CubeListBuilder.create().texOffs(214, 369).addBox(-1.0F, -3.0F, 0.0F, 5.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.4177F, 3.0001F, -2.72F, 0.4363F, 0.0F, 0.0F));

        PartDefinition cube_r233 = left_frontLeg.addOrReplaceChild("cube_r233", CubeListBuilder.create().texOffs(368, 191).addBox(-1.0F, -3.0F, 0.0F, 5.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.4177F, 4.8264F, -0.3399F, 0.9163F, 0.0F, 0.0F));

        PartDefinition cube_r234 = left_frontLeg.addOrReplaceChild("cube_r234", CubeListBuilder.create().texOffs(370, 120).addBox(-1.0F, 0.5F, 0.0F, 5.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.4177F, -4.7088F, -1.5952F, -0.4451F, 0.0F, 0.0F));

        PartDefinition cube_r235 = left_frontLeg.addOrReplaceChild("cube_r235", CubeListBuilder.create().texOffs(364, 316).addBox(-1.0F, 0.0F, -5.0F, 5.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.4177F, -2.9987F, 3.1032F, -0.3491F, 0.0F, 0.0F));

        PartDefinition cube_r236 = left_frontLeg.addOrReplaceChild("cube_r236", CubeListBuilder.create().texOffs(368, 29).addBox(-1.0F, -5.0F, -4.0F, 5.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.4177F, 1.9585F, 3.7559F, 0.1309F, 0.0F, 0.0F));

        PartDefinition cube_r237 = left_frontLeg.addOrReplaceChild("cube_r237", CubeListBuilder.create().texOffs(368, 22).addBox(-1.0F, -2.0F, 0.0F, 5.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.4177F, 4.8264F, -0.3399F, 0.6109F, 0.0F, 0.0F));

        PartDefinition cube_r238 = left_frontLeg.addOrReplaceChild("cube_r238", CubeListBuilder.create().texOffs(368, 145).addBox(-4.0F, -6.0F, -4.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0823F, 7.0854F, 6.6753F, 0.6109F, 0.0F, 0.0F));

        PartDefinition left_lowerLeg = left_frontLeg.addOrReplaceChild("left_lowerLeg", CubeListBuilder.create(), PartPose.offset(0.0F, 6.75F, 4.5F));

        PartDefinition cube_r239 = left_lowerLeg.addOrReplaceChild("cube_r239", CubeListBuilder.create().texOffs(172, 381).addBox(0.0F, -6.0F, 0.0F, 0.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 4.0F, 0.0F, -0.2182F, 0.0F, 0.0F));

        PartDefinition cube_r240 = left_lowerLeg.addOrReplaceChild("cube_r240", CubeListBuilder.create().texOffs(384, 201).addBox(-1.0F, -4.0F, -1.0F, 2.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.05F, 3.0138F, -2.4204F, -0.2618F, 0.0F, 0.0F));

        PartDefinition cube_r241 = left_lowerLeg.addOrReplaceChild("cube_r241", CubeListBuilder.create().texOffs(222, 383).addBox(0.0F, -6.0F, 0.0F, 1.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.05F, 9.0F, -3.025F, -0.2618F, 0.0F, 0.1745F));

        PartDefinition cube_r242 = left_lowerLeg.addOrReplaceChild("cube_r242", CubeListBuilder.create().texOffs(0, 358).addBox(-1.0F, -10.0F, -1.0F, 4.0F, 10.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.95F, 9.0F, -3.025F, -0.2618F, 0.0F, 0.0F));

        PartDefinition left_foot = left_lowerLeg.addOrReplaceChild("left_foot", CubeListBuilder.create().texOffs(384, 345).addBox(-0.5F, 1.25F, -5.75F, 1.0F, 2.0F, 2.0F, new CubeDeformation(-0.25F))
                .texOffs(384, 140).addBox(-0.5F, 1.0F, -4.5F, 1.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 9.25F, -2.0F));

        PartDefinition cube_r243 = left_foot.addOrReplaceChild("cube_r243", CubeListBuilder.create().texOffs(190, 379).addBox(-1.0F, -3.0F, -1.0F, 4.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 2.5F, -1.0F, -0.1745F, 0.0F, 0.0F));

        PartDefinition cube_r244 = left_foot.addOrReplaceChild("cube_r244", CubeListBuilder.create().texOffs(384, 155).addBox(0.0F, -2.0F, 0.0F, 1.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.5F, 3.0F, 1.5F, 0.0F, 2.0071F, 0.0F));

        PartDefinition cube_r245 = left_foot.addOrReplaceChild("cube_r245", CubeListBuilder.create().texOffs(384, 150).addBox(-1.0F, -2.0F, 0.0F, 1.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.5657F, 3.0F, -3.9771F, 0.0F, -0.3491F, 0.0F));

        PartDefinition cube_r246 = left_foot.addOrReplaceChild("cube_r246", CubeListBuilder.create().texOffs(384, 145).addBox(-1.0F, -2.0F, 0.0F, 1.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.6261F, 3.0F, -4.3191F, 0.0F, 0.3491F, 0.0F));

        PartDefinition cube_r247 = left_foot.addOrReplaceChild("cube_r247", CubeListBuilder.create().texOffs(90, 385).addBox(-0.75F, -1.75F, -1.75F, 1.0F, 2.0F, 2.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(-3.4F, 3.0F, 0.6F, 0.0F, 2.0071F, 0.0F));

        PartDefinition cube_r248 = left_foot.addOrReplaceChild("cube_r248", CubeListBuilder.create().texOffs(84, 385).addBox(-0.25F, -1.75F, -1.75F, 1.0F, 2.0F, 2.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(1.75F, 3.0F, -3.75F, 0.0F, -0.3491F, 0.0F));

        PartDefinition cube_r249 = left_foot.addOrReplaceChild("cube_r249", CubeListBuilder.create().texOffs(384, 349).addBox(-0.25F, -1.75F, -1.75F, 1.0F, 2.0F, 2.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(-2.25F, 3.0F, -3.5F, 0.0F, 0.3491F, 0.0F));

        PartDefinition right_wing = body.addOrReplaceChild("right_wing", CubeListBuilder.create().texOffs(262, 355).addBox(-8.0F, -2.0F, -2.0F, 8.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(264, 269).addBox(-8.0F, 0.0F, 2.0F, 10.0F, 0.0F, 20.0F, new CubeDeformation(0.0F)), PartPose.offset(-8.0F, -7.0F, -11.0F));

        PartDefinition right_wing2 = right_wing.addOrReplaceChild("right_wing2", CubeListBuilder.create(), PartPose.offset(-8.0F, 0.0F, 0.0F));

        PartDefinition cube_r250 = right_wing2.addOrReplaceChild("cube_r250", CubeListBuilder.create().texOffs(262, 120).addBox(-16.0F, -1.5F, -1.5F, 16.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 0.0F, 0.0F, 0.0F, 0.2618F, 0.0F));

        PartDefinition right_wing3 = right_wing2.addOrReplaceChild("right_wing3", CubeListBuilder.create(), PartPose.offset(-14.0F, 0.0F, 4.0F));

        PartDefinition cube_r251 = right_wing3.addOrReplaceChild("cube_r251", CubeListBuilder.create().texOffs(284, 89).addBox(-24.0F, -1.5F, -1.5F, 24.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.4464F, 0.0F, 0.1804F, 0.0F, -0.3491F, 0.0F));

        PartDefinition bone = right_wing3.addOrReplaceChild("bone", CubeListBuilder.create().texOffs(0, 220).addBox(19.3965F, -0.5F, 8.2304F, 1.0F, 1.0F, 32.0F, new CubeDeformation(0.0F))
                .texOffs(180, 128).addBox(0.0F, 0.0F, 0.0F, 20.0F, 0.0F, 40.0F, new CubeDeformation(0.0F)), PartPose.offset(-20.0F, 0.0F, -8.0F));

        PartDefinition right_wingFin = right_wing3.addOrReplaceChild("right_wingFin", CubeListBuilder.create().texOffs(262, 112).addBox(-36.1035F, -1.0F, -1.0695F, 36.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-72.1035F, 0.0F, 0.9305F, 72.0F, 0.0F, 34.0F, new CubeDeformation(0.0F)), PartPose.offset(-21.0F, 0.0F, -8.0F));

        PartDefinition cube_r252 = right_wingFin.addOrReplaceChild("cube_r252", CubeListBuilder.create().texOffs(262, 104).addBox(-38.0F, -1.0F, -1.0F, 38.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-35.8447F, 0.0F, -0.1036F, 0.0F, 0.2618F, 0.0F));

        PartDefinition right_wingRib = right_wingFin.addOrReplaceChild("right_wingRib", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r253 = right_wingRib.addOrReplaceChild("cube_r253", CubeListBuilder.create().texOffs(66, 240).addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 32.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.1786F, 0.0F, -0.0196F, 0.0F, -1.0472F, 0.0F));

        PartDefinition cube_r254 = right_wingRib.addOrReplaceChild("cube_r254", CubeListBuilder.create().texOffs(0, 68).addBox(-62.0F, 1.0F, -0.5F, 62.0F, 0.0F, 30.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.1035F, -1.0F, 0.9305F, 0.0F, 0.5236F, 0.0F));

        PartDefinition cube_r255 = right_wingRib.addOrReplaceChild("cube_r255", CubeListBuilder.create().texOffs(100, 207).addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 32.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-27.82F, 0.0F, 15.9305F, 0.0F, -0.8727F, 0.0F));

        PartDefinition right_wingRib2 = right_wingFin.addOrReplaceChild("right_wingRib2", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r256 = right_wingRib2.addOrReplaceChild("cube_r256", CubeListBuilder.create().texOffs(166, 208).addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 32.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-18.4894F, 0.0F, 26.1178F, 0.0F, -0.4363F, 0.0F));

        PartDefinition cube_r257 = right_wingRib2.addOrReplaceChild("cube_r257", CubeListBuilder.create().texOffs(132, 241).addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 32.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.1786F, 0.0F, -0.0196F, 0.0F, -0.6109F, 0.0F));

        PartDefinition cube_r258 = right_wingRib2.addOrReplaceChild("cube_r258", CubeListBuilder.create().texOffs(0, 128).addBox(-62.0F, 1.0F, -0.5F, 62.0F, 0.0F, 28.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.1035F, -1.0F, 0.9305F, 0.0F, 0.9599F, 0.0F));

        PartDefinition right_wingTip = right_wingFin.addOrReplaceChild("right_wingTip", CubeListBuilder.create(), PartPose.offset(-72.0F, 0.0F, 9.75F));

        PartDefinition cube_r259 = right_wingTip.addOrReplaceChild("cube_r259", CubeListBuilder.create().texOffs(212, 66).addBox(-16.0F, -0.5F, -0.5F, 16.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.1714F, 0.0F, -0.1946F, 0.0F, 0.2618F, 0.0F));

        PartDefinition cube_r260 = right_wingTip.addOrReplaceChild("cube_r260", CubeListBuilder.create().texOffs(100, 184).addBox(-16.0F, 0.0F, -0.5F, 16.0F, 0.0F, 23.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.1714F, 0.0F, 0.8054F, 0.0F, 0.2618F, 0.0F));

        PartDefinition bone5 = right_wing2.addOrReplaceChild("bone5", CubeListBuilder.create().texOffs(0, 184).addBox(-14.0F, 0.0F, 0.0F, 14.0F, 0.0F, 36.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition left_wing = body.addOrReplaceChild("left_wing", CubeListBuilder.create().texOffs(134, 356).addBox(0.0F, -2.0F, -2.0F, 8.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(66, 273).addBox(-2.0F, 0.0F, 2.0F, 10.0F, 0.0F, 20.0F, new CubeDeformation(0.0F)), PartPose.offset(8.0F, -7.0F, -11.0F));

        PartDefinition left_wing2 = left_wing.addOrReplaceChild("left_wing2", CubeListBuilder.create(), PartPose.offset(8.0F, 0.0F, 0.0F));

        PartDefinition cube_r261 = left_wing2.addOrReplaceChild("cube_r261", CubeListBuilder.create().texOffs(328, 19).addBox(0.0F, -1.5F, -1.5F, 16.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 0.0F, 0.0F, 0.0F, -0.2618F, 0.0F));

        PartDefinition left_wing3 = left_wing2.addOrReplaceChild("left_wing3", CubeListBuilder.create(), PartPose.offset(14.0F, 0.0F, 4.0F));

        PartDefinition cube_r262 = left_wing3.addOrReplaceChild("cube_r262", CubeListBuilder.create().texOffs(284, 95).addBox(0.0F, -1.5F, -1.5F, 24.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.4464F, 0.0F, 0.1804F, 0.0F, 0.3491F, 0.0F));

        PartDefinition bone2 = left_wing3.addOrReplaceChild("bone2", CubeListBuilder.create().texOffs(232, 208).addBox(-20.3965F, -0.5F, 8.2304F, 1.0F, 1.0F, 32.0F, new CubeDeformation(0.0F))
                .texOffs(180, 168).addBox(-20.0F, 0.0F, 0.0F, 20.0F, 0.0F, 40.0F, new CubeDeformation(0.0F)), PartPose.offset(20.0F, 0.0F, -8.0F));

        PartDefinition left_wingFin = left_wing3.addOrReplaceChild("left_wingFin", CubeListBuilder.create().texOffs(262, 116).addBox(0.1035F, -1.0F, -1.0695F, 36.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 34).addBox(0.1035F, 0.0F, 0.9305F, 72.0F, 0.0F, 34.0F, new CubeDeformation(0.0F)), PartPose.offset(21.0F, 0.0F, -8.0F));

        PartDefinition cube_r263 = left_wingFin.addOrReplaceChild("cube_r263", CubeListBuilder.create().texOffs(262, 108).addBox(0.0F, -1.0F, -1.0F, 38.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(35.8447F, 0.0F, -0.1036F, 0.0F, -0.2618F, 0.0F));

        PartDefinition left_wingRib = left_wingFin.addOrReplaceChild("left_wingRib", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r264 = left_wingRib.addOrReplaceChild("cube_r264", CubeListBuilder.create().texOffs(198, 241).addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 32.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.1786F, 0.0F, -0.0196F, 0.0F, 1.0472F, 0.0F));

        PartDefinition cube_r265 = left_wingRib.addOrReplaceChild("cube_r265", CubeListBuilder.create().texOffs(0, 98).addBox(0.0F, 1.0F, -0.5F, 62.0F, 0.0F, 30.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.1035F, -1.0F, 0.9305F, 0.0F, -0.5236F, 0.0F));

        PartDefinition cube_r266 = left_wingRib.addOrReplaceChild("cube_r266", CubeListBuilder.create().texOffs(212, 0).addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 32.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(27.82F, 0.0F, 15.9305F, 0.0F, 0.8727F, 0.0F));

        PartDefinition left_wingRib2 = left_wingFin.addOrReplaceChild("left_wingRib2", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r267 = left_wingRib2.addOrReplaceChild("cube_r267", CubeListBuilder.create().texOffs(212, 33).addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 32.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(18.4894F, 0.0F, 26.1178F, 0.0F, 0.4363F, 0.0F));

        PartDefinition cube_r268 = left_wingRib2.addOrReplaceChild("cube_r268", CubeListBuilder.create().texOffs(0, 253).addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 32.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.1786F, 0.0F, -0.0196F, 0.0F, 0.6109F, 0.0F));

        PartDefinition cube_r269 = left_wingRib2.addOrReplaceChild("cube_r269", CubeListBuilder.create().texOffs(0, 156).addBox(0.0F, 1.0F, -0.5F, 62.0F, 0.0F, 28.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.1035F, -1.0F, 0.9305F, 0.0F, -0.9599F, 0.0F));

        PartDefinition left_wingTip = left_wingFin.addOrReplaceChild("left_wingTip", CubeListBuilder.create(), PartPose.offset(72.0F, 0.0F, 9.75F));

        PartDefinition cube_r270 = left_wingTip.addOrReplaceChild("cube_r270", CubeListBuilder.create().texOffs(66, 233).addBox(0.0F, -0.5F, -0.5F, 16.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.1714F, 0.0F, -0.1946F, 0.0F, -0.2618F, 0.0F));

        PartDefinition cube_r271 = left_wingTip.addOrReplaceChild("cube_r271", CubeListBuilder.create().texOffs(184, 104).addBox(0.0F, 0.0F, -0.5F, 16.0F, 0.0F, 23.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.1714F, 0.0F, 0.8054F, 0.0F, -0.2618F, 0.0F));

        PartDefinition bone6 = left_wing2.addOrReplaceChild("bone6", CubeListBuilder.create().texOffs(184, 68).addBox(0.0F, 0.0F, 0.0F, 14.0F, 0.0F, 36.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));


        return LayerDefinition.create(meshdefinition, 512, 512);
	}

    @Override
    public void setupAnim(EnergyDragonRenderState state) {
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
            this.root.y = 8.0F;
        }

        if (state.isSleeping) {
            this.sleepAnimation.apply(state.sleepingAnimationState, ageInTicks);
        }
        else if (state.isSitting) {
            this.sitAnimation.apply(state.sitAnimationState, ageInTicks);
        }
        else if (state.isFlying) {
            if (state.walkAnimationSpeed > 0.05f) {
                this.flyAnimation.apply(state.flyAnimationState, ageInTicks);
            } else {
                this.hoverAnimation.apply(state.hoverAnimationState, ageInTicks);
            }
        }
        else {
            this.walkAnimation.applyWalk(state.walkAnimationPos, state.walkAnimationSpeed, 1.0f, 2.5f);
            this.idleAnimation.apply(state.idleAnimationState, ageInTicks);
        }

        this.fireAnimation.apply(state.fireAnimationState, ageInTicks);
        this.meleeAnimation.apply(state.meleeAnimationState, ageInTicks);

        if (state.isRidden && state.isFlying && !state.isBaby) {
            float pitchRad = state.dragonPitch * ((float)Math.PI / 180F);

            this.root.xRot += pitchRad;
        }
    }

    public ModelPart getRoot() {
        return root;
    }
}