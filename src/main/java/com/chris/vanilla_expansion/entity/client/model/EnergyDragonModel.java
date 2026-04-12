package com.chris.vanilla_expansion.entity.client.model;


import com.chris.vanilla_expansion.VanillaExpansion;
import com.chris.vanilla_expansion.entity.client.animation.EnergyDragonAnimations;
import com.chris.vanilla_expansion.entity.server.dragons.EnergyDragonEntity;
import net.minecraft.client.animation.KeyframeAnimation;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.Identifier;


public class EnergyDragonModel extends EntityModel<EnergyDragonRenderState> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(Identifier.fromNamespaceAndPath(VanillaExpansion.MOD_ID, "energydragon"), "main");

    private KeyframeAnimation idleAnimation;
    private final KeyframeAnimation walkAnimation;
    private final KeyframeAnimation flyAnimation;
    private final KeyframeAnimation hoverAnimation;
    private final KeyframeAnimation sleepAnimation;
    private final KeyframeAnimation blinkAnimation;

    private final ModelPart root;
    private final ModelPart seat;



	public EnergyDragonModel(ModelPart root) {
        super(root);
        this.root = root.getChild("root");
        this.seat = this.root.getChild("seat");
        this.idleAnimation = EnergyDragonAnimations.IDLE.bake(root);
        this.walkAnimation = EnergyDragonAnimations.WALK.bake(root);
        this.flyAnimation = EnergyDragonAnimations.FLY.bake(root);
        this.hoverAnimation = EnergyDragonAnimations.HOVER.bake(root);
        this.sleepAnimation = EnergyDragonAnimations.SLEEPING.bake(root);
        this.blinkAnimation = EnergyDragonAnimations.BLINK.bake(root);
	}

	public static LayerDefinition getTextureModelData() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 4.0F, 0.0F));

        PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r1 = body.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(72, 301).addBox(-1.0F, -3.0F, 0.0F, 2.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -6.0F, 9.0F, -0.8727F, 0.0F, 0.0F));

        PartDefinition cube_r2 = body.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(282, 251).addBox(-1.0F, -4.0F, 0.0F, 2.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -6.0F, 2.0F, -0.8727F, 0.0F, 0.0F));

        PartDefinition cube_r3 = body.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(282, 243).addBox(-1.0F, -4.0F, 0.0F, 2.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -6.0F, -5.0F, -0.6981F, 0.0F, 0.0F));

        PartDefinition torsob_r1 = body.addOrReplaceChild("torsob_r1", CubeListBuilder.create().texOffs(246, 356).addBox(-5.5F, -4.0F, 0.0F, 11.0F, 4.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 5.0F, 1.0F, 0.2618F, 0.0F, 0.0F));

        PartDefinition torso_r1 = body.addOrReplaceChild("torso_r1", CubeListBuilder.create().texOffs(326, 277).addBox(-6.0F, 0.0F, 0.0F, 12.0F, 9.0F, 17.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -7.0F, 0.0F, -0.0873F, 0.0F, 0.0F));

        PartDefinition chest_r1 = body.addOrReplaceChild("chest_r1", CubeListBuilder.create().texOffs(246, 328).addBox(-7.0F, 0.0F, -14.0F, 14.0F, 14.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -7.0F, 0.0F, 0.0873F, 0.0F, 0.0F));

        PartDefinition BLLeg = body.addOrReplaceChild("BLLeg", CubeListBuilder.create(), PartPose.offset(6.0F, 0.0F, 13.0F));

        PartDefinition cube_r4 = BLLeg.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(312, 70).addBox(-3.0F, -10.0F, -1.0F, 4.0F, 10.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.5F, 7.85F, -3.0F, -0.2618F, -0.2618F, 0.0F));

        PartDefinition cube_r5 = BLLeg.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(86, 252).addBox(-4.8105F, 1.161F, 0.0514F, 4.0F, 7.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.6821F, 6.6696F, -4.1483F, 0.6109F, -0.2618F, 0.0F));

        PartDefinition BLLowerLeg = BLLeg.addOrReplaceChild("BLLowerLeg", CubeListBuilder.create(), PartPose.offset(-1.0F, 11.0F, 1.0F));

        PartDefinition cube_r6 = BLLowerLeg.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(58, 329).addBox(0.0F, -5.0F, 0.0F, 0.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, 5.5F, 2.6F, -0.0873F, -0.2618F, 0.0F));

        PartDefinition cube_r7 = BLLowerLeg.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(64, 369).addBox(-4.0F, -7.0F, -1.0F, 5.0F, 7.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, 7.1F, -1.0F, -0.0873F, -0.2618F, 0.0F));

        PartDefinition BLFoot = BLLowerLeg.addOrReplaceChild("BLFoot", CubeListBuilder.create(), PartPose.offset(0.0F, 6.0F, 2.0F));

        PartDefinition toe_r1 = BLFoot.addOrReplaceChild("toe_r1", CubeListBuilder.create().texOffs(312, 84).addBox(0.0F, 0.0F, -3.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(-1.625F, -0.1F, -1.75F, 0.8442F, 0.6709F, 0.4815F));

        PartDefinition foot_r1 = BLFoot.addOrReplaceChild("foot_r1", CubeListBuilder.create().texOffs(338, 123).addBox(-4.0F, -3.0F, -1.0F, 5.0F, 3.0F, 5.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(2.0F, 3.0F, -3.0F, 0.0F, -0.2618F, 0.0F));

        PartDefinition toe_r2 = BLFoot.addOrReplaceChild("toe_r2", CubeListBuilder.create().texOffs(226, 57).addBox(0.0F, 0.0F, -3.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.375F, 0.65F, -4.0F, 0.3491F, 0.2318F, 0.0F));

        PartDefinition toe_r3 = BLFoot.addOrReplaceChild("toe_r3", CubeListBuilder.create().texOffs(282, 259).addBox(-1.0F, 0.0F, -3.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.525F, 0.65F, -3.0F, 0.3491F, -0.7059F, 0.0F));

        PartDefinition toe_r4 = BLFoot.addOrReplaceChild("toe_r4", CubeListBuilder.create().texOffs(224, 94).addBox(0.0F, 0.0F, -3.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.125F, 0.65F, -4.0F, 0.3491F, -0.2618F, 0.0F));

        PartDefinition BRLeg = body.addOrReplaceChild("BRLeg", CubeListBuilder.create(), PartPose.offset(-6.0F, 0.0F, 13.0F));

        PartDefinition cube_r8 = BRLeg.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(42, 352).addBox(-1.0F, -10.0F, -1.0F, 4.0F, 10.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.5F, 7.85F, -3.0F, -0.2618F, 0.2618F, 0.0F));

        PartDefinition cube_r9 = BRLeg.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(262, 369).addBox(0.8105F, 1.161F, 0.0514F, 4.0F, 7.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.6821F, 6.6696F, -4.1483F, 0.6109F, 0.2618F, 0.0F));

        PartDefinition BRLowerLeg = BRLeg.addOrReplaceChild("BRLowerLeg", CubeListBuilder.create(), PartPose.offset(0.0F, 11.0F, 1.0F));

        PartDefinition cube_r10 = BRLowerLeg.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(64, 329).addBox(0.0F, -5.0F, 0.0F, 0.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.5F, 5.5F, 2.6F, -0.0873F, 0.2618F, 0.0F));

        PartDefinition cube_r11 = BRLowerLeg.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(84, 369).addBox(-1.0F, -7.0F, -1.0F, 5.0F, 7.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 7.1F, -1.0F, -0.0873F, 0.2618F, 0.0F));

        PartDefinition BRFoot = BRLowerLeg.addOrReplaceChild("BRFoot", CubeListBuilder.create(), PartPose.offset(1.0F, 6.0F, 2.0F));

        PartDefinition toe_r5 = BRFoot.addOrReplaceChild("toe_r5", CubeListBuilder.create().texOffs(328, 84).addBox(-1.0F, 0.0F, -3.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(1.625F, -0.1F, -1.75F, 0.8442F, -0.6709F, -0.4815F));

        PartDefinition foot_r2 = BRFoot.addOrReplaceChild("foot_r2", CubeListBuilder.create().texOffs(364, 151).addBox(-1.0F, -3.0F, -1.0F, 5.0F, 3.0F, 5.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(-2.0F, 3.0F, -3.0F, 0.0F, 0.2618F, 0.0F));

        PartDefinition toe_r6 = BRFoot.addOrReplaceChild("toe_r6", CubeListBuilder.create().texOffs(230, 323).addBox(-1.0F, 0.0F, -3.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.375F, 0.65F, -4.0F, 0.3491F, -0.2318F, 0.0F));

        PartDefinition toe_r7 = BRFoot.addOrReplaceChild("toe_r7", CubeListBuilder.create().texOffs(320, 84).addBox(0.0F, 0.0F, -3.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.525F, 0.65F, -3.0F, 0.3491F, 0.7059F, 0.0F));

        PartDefinition toe_r8 = BRFoot.addOrReplaceChild("toe_r8", CubeListBuilder.create().texOffs(72, 313).addBox(-1.0F, 0.0F, -3.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.125F, 0.65F, -4.0F, 0.3491F, 0.2618F, 0.0F));

        PartDefinition FLLeg = body.addOrReplaceChild("FLLeg", CubeListBuilder.create(), PartPose.offset(7.0F, 4.0F, -8.0F));

        PartDefinition cube_r12 = FLLeg.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(286, 356).addBox(-4.0F, -9.0F, -4.0F, 4.0F, 9.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.95F, 6.0F, 4.5F, 0.4363F, 0.2618F, 0.0F));

        PartDefinition FLLowerLeg = FLLeg.addOrReplaceChild("FLLowerLeg", CubeListBuilder.create(), PartPose.offset(1.0F, 6.0F, 3.0F));

        PartDefinition cube_r13 = FLLowerLeg.addOrReplaceChild("cube_r13", CubeListBuilder.create().texOffs(234, 201).addBox(0.0F, -7.0F, 0.0F, 0.0F, 7.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.4F, 8.0F, 0.5F, -0.2618F, 0.2618F, 0.0F));

        PartDefinition cube_r14 = FLLowerLeg.addOrReplaceChild("cube_r14", CubeListBuilder.create().texOffs(222, 357).addBox(-4.0F, -9.0F, -1.0F, 5.0F, 9.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.1F, 8.1F, -3.75F, -0.2618F, 0.2618F, 0.0F));

        PartDefinition FLFoot = FLLowerLeg.addOrReplaceChild("FLFoot", CubeListBuilder.create(), PartPose.offset(0.0F, 7.0F, 0.0F));

        PartDefinition foot_r3 = FLFoot.addOrReplaceChild("foot_r3", CubeListBuilder.create().texOffs(120, 369).addBox(-4.0F, -3.0F, -1.0F, 5.0F, 3.0F, 5.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(0.0F, 3.0F, -4.0F, 0.0F, 0.2618F, 0.0F));

        PartDefinition toe_r9 = FLFoot.addOrReplaceChild("toe_r9", CubeListBuilder.create().texOffs(178, 364).addBox(0.0F, 0.0F, -3.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(-2.625F, -0.1F, -0.75F, 0.8442F, 1.1945F, 0.4815F));

        PartDefinition toe_r10 = FLFoot.addOrReplaceChild("toe_r10", CubeListBuilder.create().texOffs(58, 342).addBox(0.0F, 0.0F, -3.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.375F, 0.4F, -3.0F, 0.3491F, 0.7554F, 0.0F));

        PartDefinition toe_r11 = FLFoot.addOrReplaceChild("toe_r11", CubeListBuilder.create().texOffs(362, 343).addBox(-1.0F, 0.0F, -3.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.525F, 0.4F, -4.0F, 0.3491F, -0.1823F, 0.0F));

        PartDefinition toe_r12 = FLFoot.addOrReplaceChild("toe_r12", CubeListBuilder.create().texOffs(58, 338).addBox(0.0F, 0.0F, -3.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.075F, 0.4F, -4.0F, 0.3491F, 0.2618F, 0.0F));

        PartDefinition FRLeg = body.addOrReplaceChild("FRLeg", CubeListBuilder.create(), PartPose.offset(-7.0F, 4.0F, -8.0F));

        PartDefinition cube_r15 = FRLeg.addOrReplaceChild("cube_r15", CubeListBuilder.create().texOffs(104, 369).addBox(0.0F, -9.0F, -4.0F, 4.0F, 9.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.95F, 6.0F, 4.5F, 0.4363F, -0.2618F, 0.0F));

        PartDefinition FRLowerLeg = FRLeg.addOrReplaceChild("FRLowerLeg", CubeListBuilder.create(), PartPose.offset(-1.0F, 6.0F, 3.0F));

        PartDefinition cube_r16 = FRLowerLeg.addOrReplaceChild("cube_r16", CubeListBuilder.create().texOffs(278, 51).addBox(0.0F, -7.0F, 0.0F, 0.0F, 7.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.4F, 8.0F, 0.5F, -0.2618F, -0.2618F, 0.0F));

        PartDefinition cube_r17 = FRLowerLeg.addOrReplaceChild("cube_r17", CubeListBuilder.create().texOffs(366, 217).addBox(-1.0F, -9.0F, -1.0F, 5.0F, 9.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.1F, 8.1F, -3.75F, -0.2618F, -0.2618F, 0.0F));

        PartDefinition FRFoot = FRLowerLeg.addOrReplaceChild("FRFoot", CubeListBuilder.create(), PartPose.offset(0.0F, 7.0F, 1.0F));

        PartDefinition toe_r13 = FRFoot.addOrReplaceChild("toe_r13", CubeListBuilder.create().texOffs(370, 343).addBox(-1.0F, 0.0F, -3.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(2.625F, -0.1F, -1.75F, 0.8442F, -1.1945F, -0.4815F));

        PartDefinition toe_r14 = FRFoot.addOrReplaceChild("toe_r14", CubeListBuilder.create().texOffs(294, 369).addBox(0.0F, 0.0F, -3.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.525F, 0.4F, -5.0F, 0.3491F, 0.1823F, 0.0F));

        PartDefinition toe_r15 = FRFoot.addOrReplaceChild("toe_r15", CubeListBuilder.create().texOffs(140, 369).addBox(-1.0F, 0.0F, -3.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.375F, 0.4F, -4.0F, 0.3491F, -0.7554F, 0.0F));

        PartDefinition toe_r16 = FRFoot.addOrReplaceChild("toe_r16", CubeListBuilder.create().texOffs(178, 368).addBox(-1.0F, 0.0F, -3.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.075F, 0.4F, -5.0F, 0.3491F, -0.2618F, 0.0F));

        PartDefinition foot_r4 = FRFoot.addOrReplaceChild("foot_r4", CubeListBuilder.create().texOffs(242, 369).addBox(-1.0F, -3.0F, -1.0F, 5.0F, 3.0F, 5.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(0.0F, 3.0F, -5.0F, 0.0F, -0.2618F, 0.0F));

        PartDefinition neck = body.addOrReplaceChild("neck", CubeListBuilder.create().texOffs(362, 328).addBox(-5.0F, -5.0F, -3.0F, 10.0F, 10.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -13.0F));

        PartDefinition cube_r18 = neck.addOrReplaceChild("cube_r18", CubeListBuilder.create().texOffs(362, 267).addBox(-3.5F, 0.0F, -7.0F, 7.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -3.9F, -2.0F, -0.1745F, 0.0F, 0.0F));

        PartDefinition cube_r19 = neck.addOrReplaceChild("cube_r19", CubeListBuilder.create().texOffs(186, 357).addBox(-4.0F, -6.0F, -10.0F, 8.0F, 6.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 4.5F, -2.0F, -0.3927F, 0.0F, 0.0F));

        PartDefinition head = neck.addOrReplaceChild("head", CubeListBuilder.create().texOffs(362, 243).addBox(-5.0F, -3.2F, -8.9F, 10.0F, 5.0F, 9.0F, new CubeDeformation(0.0F))
                .texOffs(182, 57).addBox(-1.5F, -3.15F, -12.42F, 3.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.0F, -9.0F));

        PartDefinition cube_r20 = head.addOrReplaceChild("cube_r20", CubeListBuilder.create().texOffs(370, 303).addBox(-1.5F, 0.0F, 0.0F, 3.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.2F, -14.4F, 0.0873F, 0.0F, 0.0F));

        PartDefinition cube_r21 = head.addOrReplaceChild("cube_r21", CubeListBuilder.create().texOffs(72, 295).addBox(-1.5F, 0.0F, -4.0F, 3.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -3.2F, -12.4F, 1.0472F, 0.0F, 0.0F));

        PartDefinition cube_r22 = head.addOrReplaceChild("cube_r22", CubeListBuilder.create().texOffs(278, 369).addBox(0.0F, 0.0F, -5.0F, 3.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.0F, -3.1F, -8.9F, 0.0F, -0.7854F, 0.0F));

        PartDefinition cube_r23 = head.addOrReplaceChild("cube_r23", CubeListBuilder.create().texOffs(234, 88).addBox(-3.0F, 0.0F, -5.0F, 3.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.0F, -3.1F, -8.9F, 0.0F, 0.7854F, 0.0F));

        PartDefinition jaw = head.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(362, 257).addBox(-5.0F, 0.0F, -8.9F, 10.0F, 1.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 1.8F, 0.0F));

        PartDefinition cube_r24 = jaw.addOrReplaceChild("cube_r24", CubeListBuilder.create().texOffs(150, 364).addBox(-3.5F, -1.0F, -3.5F, 7.0F, 1.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.0F, -8.9F, 0.0F, -0.7854F, 0.0F));

        PartDefinition Bteeth = jaw.addOrReplaceChild("Bteeth", CubeListBuilder.create().texOffs(252, 159).addBox(-4.0F, -21.2F, -29.9F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(254, 159).addBox(-4.0F, -21.2F, -27.9F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(256, 159).addBox(4.0F, -21.2F, -29.9F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(256, 217).addBox(4.0F, -21.2F, -27.9F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 20.2F, 22.0F));

        PartDefinition tooth_r1 = Bteeth.addOrReplaceChild("tooth_r1", CubeListBuilder.create().texOffs(260, 159).addBox(0.0F, -1.0F, 0.0F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.5F, -20.2F, -33.4F, 0.0F, -0.7854F, 0.0F));

        PartDefinition tooth_r2 = Bteeth.addOrReplaceChild("tooth_r2", CubeListBuilder.create().texOffs(258, 217).addBox(0.0F, -1.0F, 0.0F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.5F, -20.2F, -33.4F, 0.0F, 0.7854F, 0.0F));

        PartDefinition tooth_r3 = Bteeth.addOrReplaceChild("tooth_r3", CubeListBuilder.create().texOffs(258, 159).addBox(0.0F, -1.0F, 0.0F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, -20.2F, -31.9F, 0.0F, 0.7854F, 0.0F));

        PartDefinition tooth_r4 = Bteeth.addOrReplaceChild("tooth_r4", CubeListBuilder.create().texOffs(254, 217).addBox(0.0F, -1.0F, 0.0F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.0F, -20.2F, -31.9F, 0.0F, -0.7854F, 0.0F));

        PartDefinition ears = head.addOrReplaceChild("ears", CubeListBuilder.create(), PartPose.offset(0.0F, -3.0F, -1.0F));

        PartDefinition cube_r25 = ears.addOrReplaceChild("cube_r25", CubeListBuilder.create().texOffs(370, 319).addBox(0.0F, -1.0F, 0.0F, 2.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.05F, 0.7F, 0.0F, 0.4363F, -0.1745F, -0.0436F));

        PartDefinition cube_r26 = ears.addOrReplaceChild("cube_r26", CubeListBuilder.create().texOffs(370, 311).addBox(-2.0F, -1.0F, 0.0F, 2.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.05F, 0.7F, 0.0F, 0.4363F, 0.1745F, 0.0436F));

        PartDefinition eyes = head.addOrReplaceChild("eyes", CubeListBuilder.create(), PartPose.offset(0.0F, 22.0F, 22.0F));

        PartDefinition REye_r1 = eyes.addOrReplaceChild("REye_r1", CubeListBuilder.create().texOffs(214, 94).addBox(0.25F, 0.0F, -0.75F, 4.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, -24.1F, -34.0F, 0.0F, -0.7854F, 0.0F));

        PartDefinition LEye_r1 = eyes.addOrReplaceChild("LEye_r1", CubeListBuilder.create().texOffs(216, 57).addBox(-4.25F, 0.0F, -0.75F, 4.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, -24.1F, -34.0F, 0.0F, 0.7854F, 0.0F));

        PartDefinition eyeLTRIGHT = eyes.addOrReplaceChild("eyeLTRIGHT", CubeListBuilder.create(), PartPose.offset(-2.5F, -24.5F, -32.5F));

        PartDefinition cube_r27 = eyeLTRIGHT.addOrReplaceChild("cube_r27", CubeListBuilder.create().texOffs(244, 217).addBox(-1.0F, -1.0F, -1.0F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.75F, 1.0F, 0.9F, 0.0F, 0.7854F, 0.0F));

        PartDefinition eyeLTLEFT = eyes.addOrReplaceChild("eyeLTLEFT", CubeListBuilder.create(), PartPose.offset(2.5F, -24.5F, -32.5F));

        PartDefinition cube_r28 = eyeLTLEFT.addOrReplaceChild("cube_r28", CubeListBuilder.create().texOffs(234, 59).addBox(-3.0F, -1.0F, -1.0F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.75F, 1.0F, 0.9F, 0.0F, -0.7854F, 0.0F));

        PartDefinition eyeLBRIGHT = eyes.addOrReplaceChild("eyeLBRIGHT", CubeListBuilder.create(), PartPose.offset(-2.5F, -20.5F, -32.5F));

        PartDefinition cube_r29 = eyeLBRIGHT.addOrReplaceChild("cube_r29", CubeListBuilder.create().texOffs(244, 59).addBox(-1.0F, -1.0F, -1.0F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.75F, 0.0F, 0.9F, 0.0F, 0.7854F, 0.0F));

        PartDefinition eyeLBLEFT = eyes.addOrReplaceChild("eyeLBLEFT", CubeListBuilder.create(), PartPose.offset(2.5F, -20.5F, -32.5F));

        PartDefinition cube_r30 = eyeLBLEFT.addOrReplaceChild("cube_r30", CubeListBuilder.create().texOffs(234, 217).addBox(-3.0F, -1.0F, -1.0F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.75F, 0.0F, 0.9F, 0.0F, -0.7854F, 0.0F));

        PartDefinition TopTeeth = head.addOrReplaceChild("TopTeeth", CubeListBuilder.create().texOffs(232, 94).addBox(-4.0F, 1.8F, -7.9F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(232, 96).addBox(-4.0F, 1.8F, -5.9F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(250, 88).addBox(4.0F, 1.8F, -7.9F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(250, 90).addBox(4.0F, 1.8F, -5.9F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition tooth_r5 = TopTeeth.addOrReplaceChild("tooth_r5", CubeListBuilder.create().texOffs(250, 96).addBox(0.0F, -1.0F, 0.0F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.5F, 2.8F, -11.4F, 0.0F, -0.7854F, 0.0F));

        PartDefinition tooth_r6 = TopTeeth.addOrReplaceChild("tooth_r6", CubeListBuilder.create().texOffs(250, 94).addBox(0.0F, -1.0F, 0.0F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.5F, 2.8F, -11.4F, 0.0F, 0.7854F, 0.0F));

        PartDefinition tooth_r7 = TopTeeth.addOrReplaceChild("tooth_r7", CubeListBuilder.create().texOffs(250, 92).addBox(0.0F, -1.0F, 0.0F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, 2.8F, -9.9F, 0.0F, 0.7854F, 0.0F));

        PartDefinition tooth_r8 = TopTeeth.addOrReplaceChild("tooth_r8", CubeListBuilder.create().texOffs(234, 57).addBox(0.0F, -1.0F, 0.0F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.0F, 2.8F, -9.9F, 0.0F, -0.7854F, 0.0F));

        PartDefinition spikes = head.addOrReplaceChild("spikes", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r31 = spikes.addOrReplaceChild("cube_r31", CubeListBuilder.create().texOffs(162, 372).addBox(-0.5F, -2.0F, 0.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.9F, -3.0F, 0.3491F, 0.0F, 0.0F));

        PartDefinition cube_r32 = spikes.addOrReplaceChild("cube_r32", CubeListBuilder.create().texOffs(156, 372).addBox(-0.5F, -2.0F, 0.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.65F, -7.0F, 0.2618F, 0.0F, 0.0F));

        PartDefinition cube_r33 = spikes.addOrReplaceChild("cube_r33", CubeListBuilder.create().texOffs(80, 313).addBox(-0.5F, -2.0F, 0.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.5F, -11.0F, 0.1745F, 0.0F, 0.0F));

        PartDefinition cube_r34 = spikes.addOrReplaceChild("cube_r34", CubeListBuilder.create().texOffs(348, 371).addBox(-1.0F, -1.0F, 0.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.0F, 0.0F, 0.0F, 0.0F, -0.2618F, 0.0F));

        PartDefinition cube_r35 = spikes.addOrReplaceChild("cube_r35", CubeListBuilder.create().texOffs(148, 372).addBox(-1.0F, -1.0F, 0.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.0F, 1.0F, -6.0F, 0.0F, -0.6109F, 0.0F));

        PartDefinition cube_r36 = spikes.addOrReplaceChild("cube_r36", CubeListBuilder.create().texOffs(356, 371).addBox(-1.0F, -1.0F, 0.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.0359F, 0.9021F, 0.1341F, 0.3054F, -0.2618F, 0.0F));

        PartDefinition cube_r37 = spikes.addOrReplaceChild("cube_r37", CubeListBuilder.create().texOffs(260, 59).addBox(0.0F, -0.5F, -0.1F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.9F, 1.8F, -0.9F, -0.4363F, -0.3491F, -0.0436F));

        PartDefinition cube_r38 = spikes.addOrReplaceChild("cube_r38", CubeListBuilder.create().texOffs(364, 371).addBox(0.0F, -1.0F, 0.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, 1.0F, -6.0F, 0.0F, 0.6109F, 0.0F));

        PartDefinition cube_r39 = spikes.addOrReplaceChild("cube_r39", CubeListBuilder.create().texOffs(254, 59).addBox(-1.0F, -0.5F, -0.1F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.9F, 1.8F, -0.9F, -0.4363F, 0.3491F, 0.0436F));

        PartDefinition cube_r40 = spikes.addOrReplaceChild("cube_r40", CubeListBuilder.create().texOffs(230, 371).addBox(0.0F, -1.0F, 0.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0359F, 0.9021F, 0.1341F, 0.3054F, 0.2618F, 0.0F));

        PartDefinition cube_r41 = spikes.addOrReplaceChild("cube_r41", CubeListBuilder.create().texOffs(222, 371).addBox(0.0F, -1.0F, 0.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, 0.0F, 0.0F, 0.0F, 0.2618F, 0.0F));

        PartDefinition LWing = body.addOrReplaceChild("LWing", CubeListBuilder.create(), PartPose.offset(7.0F, -4.0F, -11.0F));

        PartDefinition cube_r42 = LWing.addOrReplaceChild("cube_r42", CubeListBuilder.create().texOffs(236, 51).addBox(0.0F, -2.0F, -2.0F, 17.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, 0.25F, 0.0F, 0.0F, -0.1745F, 0.0F));

        PartDefinition LWingMid = LWing.addOrReplaceChild("LWingMid", CubeListBuilder.create().texOffs(240, 161).addBox(-1.0F, 0.26F, -3.0F, 24.0F, 0.0F, 28.0F, new CubeDeformation(0.0F)), PartPose.offset(16.0F, 0.0F, 3.0F));

        PartDefinition cube_r43 = LWingMid.addOrReplaceChild("cube_r43", CubeListBuilder.create().texOffs(196, 57).addBox(0.0F, -1.0F, -4.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(22.0F, 0.75F, -5.0F, 0.0F, -0.9599F, 0.0F));

        PartDefinition cube_r44 = LWingMid.addOrReplaceChild("cube_r44", CubeListBuilder.create().texOffs(180, 88).addBox(0.0F, -3.0F, -1.0F, 24.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 1.75F, -0.25F, 0.0F, 0.1745F, 0.0F));

        PartDefinition LWingEnd = LWingMid.addOrReplaceChild("LWingEnd", CubeListBuilder.create(), PartPose.offset(23.0F, 0.0F, -4.0F));

        PartDefinition cube_r45 = LWingEnd.addOrReplaceChild("cube_r45", CubeListBuilder.create().texOffs(0, 62).addBox(0.0F, 0.0F, 0.0F, 72.0F, 0.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 0.25F, 1.0F, 0.0F, -0.0873F, 0.0F));

        PartDefinition cube_r46 = LWingEnd.addOrReplaceChild("cube_r46", CubeListBuilder.create().texOffs(180, 62).addBox(0.0F, -1.0F, -1.0F, 72.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 0.25F, 0.0F, 0.0F, -0.0873F, 0.0F));

        PartDefinition LWingTip = LWingEnd.addOrReplaceChild("LWingTip", CubeListBuilder.create(), PartPose.offset(70.0F, 0.0F, 7.0F));

        PartDefinition cube_r47 = LWingTip.addOrReplaceChild("cube_r47", CubeListBuilder.create().texOffs(338, 109).addBox(0.0F, 0.0F, 0.0F, 16.0F, 0.0F, 14.0F, new CubeDeformation(0.0F))
                .texOffs(180, 94).addBox(0.0F, -0.49F, -1.0F, 16.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.24F, -0.3F, 0.0F, -0.1745F, 0.0F));

        PartDefinition wingFlapL1 = LWingEnd.addOrReplaceChild("wingFlapL1", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r48 = wingFlapL1.addOrReplaceChild("cube_r48", CubeListBuilder.create().texOffs(86, 278).addBox(0.0F, -1.0F, 0.0F, 1.0F, 1.0F, 38.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.75F, 0.75F, 1.0F, 0.0F, -0.4363F, 0.0F));

        PartDefinition cube_r49 = wingFlapL1.addOrReplaceChild("cube_r49", CubeListBuilder.create().texOffs(86, 264).addBox(0.0F, 0.0F, -13.5F, 38.0F, 0.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 0.25F, 1.0F, 0.0F, -2.0071F, 0.0F));

        PartDefinition wingFlapL2 = LWingEnd.addOrReplaceChild("wingFlapL2", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r50 = wingFlapL2.addOrReplaceChild("cube_r50", CubeListBuilder.create().texOffs(0, 252).addBox(-1.0F, -1.0F, 0.0F, 1.0F, 1.0F, 42.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.75F, 0.75F, 1.0F, 0.0F, -0.1745F, 0.0F));

        PartDefinition cube_r51 = wingFlapL2.addOrReplaceChild("cube_r51", CubeListBuilder.create().texOffs(102, 219).addBox(0.0F, -1.0F, 0.0F, 1.0F, 1.0F, 44.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.75F, 0.75F, 1.0F, 0.0F, 0.1745F, 0.0F));

        PartDefinition cube_r52 = wingFlapL2.addOrReplaceChild("cube_r52", CubeListBuilder.create().texOffs(252, 131).addBox(0.0F, 0.0F, 0.0F, 42.0F, 0.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 0.25F, 1.0F, 0.0F, -1.3963F, 0.0F));

        PartDefinition wingFlapL3 = LWingEnd.addOrReplaceChild("wingFlapL3", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r53 = wingFlapL3.addOrReplaceChild("cube_r53", CubeListBuilder.create().texOffs(180, 70).addBox(0.0F, 0.0F, 0.0F, 48.0F, 0.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 0.25F, 1.0F, 0.0F, -0.9599F, 0.0F));

        PartDefinition cube_r54 = wingFlapL3.addOrReplaceChild("cube_r54", CubeListBuilder.create().texOffs(182, 0).addBox(0.0F, -1.0F, 0.0F, 1.0F, 1.0F, 50.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.75F, 0.75F, 1.0F, 0.0F, 0.6109F, 0.0F));

        PartDefinition wingFlapL4 = LWingEnd.addOrReplaceChild("wingFlapL4", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r55 = wingFlapL4.addOrReplaceChild("cube_r55", CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, 0.0F, -11.0F, 60.0F, 0.0F, 31.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 0.25F, 1.0F, 0.0F, -0.5236F, 0.0F));

        PartDefinition cube_r56 = wingFlapL4.addOrReplaceChild("cube_r56", CubeListBuilder.create().texOffs(0, 98).addBox(0.0F, -1.0F, 0.0F, 1.0F, 1.0F, 62.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.75F, 0.75F, 1.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition LWMmembrane = LWing.addOrReplaceChild("LWMmembrane", CubeListBuilder.create().texOffs(242, 278).addBox(-1.0F, 0.26F, 2.0F, 16.0F, 0.0F, 26.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition RWing = body.addOrReplaceChild("RWing", CubeListBuilder.create(), PartPose.offset(-7.0F, -4.0F, -11.0F));

        PartDefinition cube_r57 = RWing.addOrReplaceChild("cube_r57", CubeListBuilder.create().texOffs(188, 317).addBox(-17.0F, -2.0F, -2.0F, 17.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, 0.25F, 0.0F, 0.0F, 0.1745F, 0.0F));

        PartDefinition RWingMid = RWing.addOrReplaceChild("RWingMid", CubeListBuilder.create().texOffs(240, 189).addBox(-23.0F, 0.26F, -3.0F, 24.0F, 0.0F, 28.0F, new CubeDeformation(0.0F)), PartPose.offset(-16.0F, 0.0F, 3.0F));

        PartDefinition cube_r58 = RWingMid.addOrReplaceChild("cube_r58", CubeListBuilder.create().texOffs(182, 51).addBox(-24.0F, -3.0F, -1.0F, 24.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 1.75F, -0.25F, 0.0F, -0.1745F, 0.0F));

        PartDefinition cube_r59 = RWingMid.addOrReplaceChild("cube_r59", CubeListBuilder.create().texOffs(206, 57).addBox(-1.0F, -1.0F, -4.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-22.0F, 0.75F, -5.0F, 0.0F, 0.9599F, 0.0F));

        PartDefinition RWingEnd = RWingMid.addOrReplaceChild("RWingEnd", CubeListBuilder.create(), PartPose.offset(-23.0F, 0.0F, -4.0F));

        PartDefinition cube_r60 = RWingEnd.addOrReplaceChild("cube_r60", CubeListBuilder.create().texOffs(0, 80).addBox(-72.0F, 0.0F, 0.0F, 72.0F, 0.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 0.25F, 1.0F, 0.0F, 0.0873F, 0.0F));

        PartDefinition cube_r61 = RWingEnd.addOrReplaceChild("cube_r61", CubeListBuilder.create().texOffs(180, 66).addBox(-72.0F, -1.0F, -1.0F, 72.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 0.25F, 0.0F, 0.0F, 0.0873F, 0.0F));

        PartDefinition RWingTip = RWingEnd.addOrReplaceChild("RWingTip", CubeListBuilder.create(), PartPose.offset(-70.0F, 0.0F, 7.0F));

        PartDefinition cube_r62 = RWingTip.addOrReplaceChild("cube_r62", CubeListBuilder.create().texOffs(344, 184).addBox(-16.0F, 0.0F, 0.0F, 16.0F, 0.0F, 14.0F, new CubeDeformation(0.0F))
                .texOffs(180, 96).addBox(-16.0F, -0.49F, -1.0F, 16.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.24F, -0.3F, 0.0F, 0.1745F, 0.0F));

        PartDefinition wingFlapR1 = RWingEnd.addOrReplaceChild("wingFlapR1", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r63 = wingFlapR1.addOrReplaceChild("cube_r63", CubeListBuilder.create().texOffs(190, 264).addBox(-38.0F, 0.0F, -13.5F, 38.0F, 0.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 0.25F, 1.0F, 0.0F, 2.0071F, 0.0F));

        PartDefinition cube_r64 = wingFlapR1.addOrReplaceChild("cube_r64", CubeListBuilder.create().texOffs(164, 278).addBox(-1.0F, -1.0F, 0.0F, 1.0F, 1.0F, 38.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.75F, 0.75F, 1.0F, 0.0F, 0.4363F, 0.0F));

        PartDefinition wingFlapR2 = RWingEnd.addOrReplaceChild("wingFlapR2", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r65 = wingFlapR2.addOrReplaceChild("cube_r65", CubeListBuilder.create().texOffs(252, 88).addBox(-1.0F, -1.0F, 0.0F, 1.0F, 1.0F, 42.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.75F, 0.75F, 1.0F, 0.0F, 0.1745F, 0.0F));

        PartDefinition cube_r66 = wingFlapR2.addOrReplaceChild("cube_r66", CubeListBuilder.create().texOffs(252, 145).addBox(-42.0F, 0.0F, 0.0F, 42.0F, 0.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 0.25F, 1.0F, 0.0F, 1.3963F, 0.0F));

        PartDefinition cube_r67 = wingFlapR2.addOrReplaceChild("cube_r67", CubeListBuilder.create().texOffs(192, 219).addBox(-1.0F, -1.0F, 0.0F, 1.0F, 1.0F, 44.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.75F, 0.75F, 1.0F, 0.0F, -0.1745F, 0.0F));

        PartDefinition wingFlapR3 = RWingEnd.addOrReplaceChild("wingFlapR3", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r68 = wingFlapR3.addOrReplaceChild("cube_r68", CubeListBuilder.create().texOffs(102, 201).addBox(-48.0F, 0.0F, 0.0F, 48.0F, 0.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 0.25F, 1.0F, 0.0F, 0.9599F, 0.0F));

        PartDefinition cube_r69 = wingFlapR3.addOrReplaceChild("cube_r69", CubeListBuilder.create().texOffs(0, 201).addBox(-1.0F, -1.0F, 0.0F, 1.0F, 1.0F, 50.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.75F, 0.75F, 1.0F, 0.0F, -0.6109F, 0.0F));

        PartDefinition wingFlapR4 = RWingEnd.addOrReplaceChild("wingFlapR4", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r70 = wingFlapR4.addOrReplaceChild("cube_r70", CubeListBuilder.create().texOffs(0, 31).addBox(-60.0F, 0.0F, -11.0F, 60.0F, 0.0F, 31.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 0.25F, 1.0F, 0.0F, 0.5236F, 0.0F));

        PartDefinition cube_r71 = wingFlapR4.addOrReplaceChild("cube_r71", CubeListBuilder.create().texOffs(126, 98).addBox(-1.0F, -1.0F, 0.0F, 1.0F, 1.0F, 62.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.75F, 0.75F, 1.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition RWMmembrane = RWing.addOrReplaceChild("RWMmembrane", CubeListBuilder.create().texOffs(282, 217).addBox(-15.0F, 0.26F, 2.0F, 16.0F, 0.0F, 26.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition Tail = body.addOrReplaceChild("Tail", CubeListBuilder.create(), PartPose.offset(0.0F, -1.0F, 16.0F));

        PartDefinition cube_r72 = Tail.addOrReplaceChild("cube_r72", CubeListBuilder.create().texOffs(58, 323).addBox(-1.0F, -3.0F, 0.0F, 2.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.75F, 11.0F, -0.9599F, 0.0F, 0.0F));

        PartDefinition cube_r73 = Tail.addOrReplaceChild("cube_r73", CubeListBuilder.create().texOffs(230, 317).addBox(-1.0F, -3.0F, 0.0F, 2.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -2.75F, 5.45F, -0.9163F, 0.0F, 0.0F));

        PartDefinition cube_r74 = Tail.addOrReplaceChild("cube_r74", CubeListBuilder.create().texOffs(72, 307).addBox(-1.0F, -3.0F, 0.0F, 2.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -3.75F, -0.5F, -0.9163F, 0.0F, 0.0F));

        PartDefinition tail1_r1 = Tail.addOrReplaceChild("tail1_r1", CubeListBuilder.create().texOffs(302, 350).addBox(-4.0F, 0.0F, 0.0F, 8.0F, 7.0F, 15.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -3.65F, 0.0F, -0.1309F, 0.0F, 0.0F));

        PartDefinition Tail2 = Tail.addOrReplaceChild("Tail2", CubeListBuilder.create(), PartPose.offset(0.0F, 1.5F, 13.5F));

        PartDefinition cube_r75 = Tail2.addOrReplaceChild("cube_r75", CubeListBuilder.create().texOffs(66, 338).addBox(0.0F, -2.0F, 0.0F, 0.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.25F, 12.25F, -0.1745F, 0.0F, 0.0F));

        PartDefinition cube_r76 = Tail2.addOrReplaceChild("cube_r76", CubeListBuilder.create().texOffs(358, 123).addBox(0.0F, -2.0F, 0.0F, 0.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.4F, 7.2F, -0.1745F, 0.0F, 0.0F));

        PartDefinition cube_r77 = Tail2.addOrReplaceChild("cube_r77", CubeListBuilder.create().texOffs(234, 211).addBox(0.0F, -2.0F, 0.0F, 0.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -2.4F, 2.5F, -0.1745F, 0.0F, 0.0F));

        PartDefinition tail2_r1 = Tail2.addOrReplaceChild("tail2_r1", CubeListBuilder.create().texOffs(348, 350).addBox(-3.0F, 0.0F, 0.0F, 6.0F, 6.0F, 15.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -2.85F, 0.35F, -0.1745F, 0.0F, 0.0F));

        PartDefinition Tail3 = Tail2.addOrReplaceChild("Tail3", CubeListBuilder.create(), PartPose.offset(0.0F, 2.4F, 14.25F));

        PartDefinition cube_r78 = Tail3.addOrReplaceChild("cube_r78", CubeListBuilder.create().texOffs(82, 305).addBox(0.0F, -2.0F, -1.0F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.15F, 13.8F, -0.1309F, 0.0F, 0.0F));

        PartDefinition cube_r79 = Tail3.addOrReplaceChild("cube_r79", CubeListBuilder.create().texOffs(82, 301).addBox(0.0F, -2.0F, -1.0F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.9F, 8.85F, -0.1309F, 0.0F, 0.0F));

        PartDefinition cube_r80 = Tail3.addOrReplaceChild("cube_r80", CubeListBuilder.create().texOffs(290, 259).addBox(0.0F, -2.0F, -1.0F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.5F, 3.9F, -0.1309F, 0.0F, 0.0F));

        PartDefinition tail3_r1 = Tail3.addOrReplaceChild("tail3_r1", CubeListBuilder.create().texOffs(0, 352).addBox(-2.5F, 0.0F, 0.0F, 5.0F, 5.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -2.3F, -0.05F, -0.1309F, 0.0F, 0.0F));

        PartDefinition Tail4 = Tail3.addOrReplaceChild("Tail4", CubeListBuilder.create(), PartPose.offset(0.0F, 2.1F, 15.0F));

        PartDefinition cube_r81 = Tail4.addOrReplaceChild("cube_r81", CubeListBuilder.create().texOffs(68, 323).addBox(0.0F, -2.0F, 0.0F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.8F, 8.95F, -0.0873F, 0.0F, 0.0F));

        PartDefinition cube_r82 = Tail4.addOrReplaceChild("cube_r82", CubeListBuilder.create().texOffs(82, 309).addBox(0.0F, -2.0F, 0.0F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.3F, 2.95F, -0.0873F, 0.0F, 0.0F));

        PartDefinition tail4_r1 = Tail4.addOrReplaceChild("tail4_r1", CubeListBuilder.create().texOffs(356, 42).addBox(-2.0F, 0.0F, 0.0F, 4.0F, 4.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.85F, 0.0F, -0.0873F, 0.0F, 0.0F));

        PartDefinition Tail5 = Tail4.addOrReplaceChild("Tail5", CubeListBuilder.create().texOffs(344, 198).addBox(-1.5F, -1.5F, 0.0F, 3.0F, 3.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(150, 346).addBox(-1.0F, -1.0F, 15.0F, 2.0F, 2.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(352, 25).addBox(0.0F, -2.65F, 1.0F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(352, 29).addBox(0.0F, -2.65F, 7.5F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(242, 357).addBox(0.0F, -2.65F, 14.0F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(238, 323).addBox(0.0F, -2.15F, 21.0F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(352, 21).addBox(0.0F, -2.15F, 28.0F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 1.5F, 15.25F));

        PartDefinition TLFin = Tail5.addOrReplaceChild("TLFin", CubeListBuilder.create(), PartPose.offset(1.6F, 0.25F, 0.0F));

        PartDefinition cube_r83 = TLFin.addOrReplaceChild("cube_r83", CubeListBuilder.create().texOffs(284, 0).addBox(0.0F, -1.0F, 0.0F, 1.0F, 1.0F, 33.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.6F, 0.25F, 13.0F, 0.0F, 0.6109F, -0.0873F));

        PartDefinition cube_r84 = TLFin.addOrReplaceChild("cube_r84", CubeListBuilder.create().texOffs(72, 317).addBox(0.0F, -1.0F, 0.0F, 1.0F, 1.0F, 28.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.6F, 0.25F, 8.0F, 0.0F, 0.7854F, -0.0873F));

        PartDefinition cube_r85 = TLFin.addOrReplaceChild("cube_r85", CubeListBuilder.create().texOffs(352, 0).addBox(0.0F, 2.0F, 0.0F, 1.0F, 1.0F, 20.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.6F, -2.75F, 4.0F, 0.0F, 0.9599F, -0.0873F));

        PartDefinition cube_r86 = TLFin.addOrReplaceChild("cube_r86", CubeListBuilder.create().texOffs(364, 123).addBox(0.0F, -1.0F, 0.0F, 1.0F, 1.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.6F, 0.25F, 21.0F, 0.0F, 0.1745F, -0.0873F));

        PartDefinition tailfinL_r1 = TLFin.addOrReplaceChild("tailfinL_r1", CubeListBuilder.create().texOffs(0, 161).addBox(0.0F, 0.0F, 0.0F, 20.0F, 0.0F, 40.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.6F, -0.25F, 0.0F, 0.0F, 0.0F, -0.0873F));

        PartDefinition cube_r87 = TLFin.addOrReplaceChild("cube_r87", CubeListBuilder.create().texOffs(366, 231).addBox(0.0F, -1.0F, 0.0F, 1.0F, 1.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 0.0F, 0.0F, 0.0F, 1.1345F, -0.0873F));

        PartDefinition TRFin = Tail5.addOrReplaceChild("TRFin", CubeListBuilder.create(), PartPose.offset(-0.6F, 0.25F, 0.0F));

        PartDefinition cube_r88 = TRFin.addOrReplaceChild("cube_r88", CubeListBuilder.create().texOffs(130, 317).addBox(-1.0F, -1.0F, 0.0F, 1.0F, 1.0F, 28.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.6F, 0.25F, 8.0F, 0.0F, -0.7854F, 0.0873F));

        PartDefinition cube_r89 = TRFin.addOrReplaceChild("cube_r89", CubeListBuilder.create().texOffs(294, 243).addBox(-1.0F, -1.0F, 0.0F, 1.0F, 1.0F, 33.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.6F, 0.25F, 13.0F, 0.0F, -0.6109F, 0.0873F));

        PartDefinition cube_r90 = TRFin.addOrReplaceChild("cube_r90", CubeListBuilder.create().texOffs(356, 21).addBox(-1.0F, 2.0F, 0.0F, 1.0F, 1.0F, 20.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.6F, -2.75F, 4.0F, 0.0F, -0.9599F, 0.0873F));

        PartDefinition cube_r91 = TRFin.addOrReplaceChild("cube_r91", CubeListBuilder.create().texOffs(364, 137).addBox(-1.0F, -1.0F, 0.0F, 1.0F, 1.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.6F, 0.25F, 21.0F, 0.0F, -0.1745F, 0.0873F));

        PartDefinition tailfinR_r1 = TRFin.addOrReplaceChild("tailfinR_r1", CubeListBuilder.create().texOffs(120, 161).addBox(-20.0F, 0.0F, 0.0F, 20.0F, 0.0F, 40.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.6F, -0.25F, 0.0F, 0.0F, 0.0F, 0.0873F));

        PartDefinition cube_r92 = TRFin.addOrReplaceChild("cube_r92", CubeListBuilder.create().texOffs(42, 369).addBox(-1.0F, -1.0F, 0.0F, 1.0F, 1.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.1345F, 0.0873F));

        PartDefinition LMidWing = body.addOrReplaceChild("LMidWing", CubeListBuilder.create(), PartPose.offset(4.0F, -1.0F, 17.0F));

        PartDefinition LMW1 = LMidWing.addOrReplaceChild("LMW1", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r93 = LMW1.addOrReplaceChild("cube_r93", CubeListBuilder.create().texOffs(284, 34).addBox(-8.0F, 0.0F, 0.0F, 8.0F, 0.0F, 28.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, 0.0F, -0.1309F, 1.0472F, 0.0F));

        PartDefinition cube_r94 = LMW1.addOrReplaceChild("cube_r94", CubeListBuilder.create().texOffs(0, 323).addBox(-1.0F, -1.0F, 0.0F, 1.0F, 1.0F, 28.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.1309F, 1.0472F, 0.0F));

        PartDefinition LMW2 = LMidWing.addOrReplaceChild("LMW2", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r95 = LMW2.addOrReplaceChild("cube_r95", CubeListBuilder.create().texOffs(242, 304).addBox(-8.0F, 0.0F, 0.0F, 8.0F, 0.0F, 24.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, 0.0F, -0.1309F, 0.6981F, 0.0F));

        PartDefinition cube_r96 = LMW2.addOrReplaceChild("cube_r96", CubeListBuilder.create().texOffs(338, 84).addBox(-1.0F, -1.0F, 0.0F, 1.0F, 1.0F, 24.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.1309F, 0.6981F, 0.0F));

        PartDefinition LMW3 = LMidWing.addOrReplaceChild("LMW3", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r97 = LMW3.addOrReplaceChild("cube_r97", CubeListBuilder.create().texOffs(58, 346).addBox(-1.0F, -2.0F, 0.0F, 1.0F, 1.0F, 22.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.0F, 0.0F, -0.1309F, 0.3491F, 0.0F));

        PartDefinition cube_r98 = LMW3.addOrReplaceChild("cube_r98", CubeListBuilder.create().texOffs(328, 62).addBox(-8.0F, 0.0F, 0.0F, 8.0F, 0.0F, 22.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, 0.0F, -0.1309F, 0.3491F, 0.0F));

        PartDefinition RMidWing = body.addOrReplaceChild("RMidWing", CubeListBuilder.create(), PartPose.offset(-4.0F, -1.0F, 17.0F));

        PartDefinition RMW1 = RMidWing.addOrReplaceChild("RMW1", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r99 = RMW1.addOrReplaceChild("cube_r99", CubeListBuilder.create().texOffs(0, 295).addBox(0.0F, 0.0F, 0.0F, 8.0F, 0.0F, 28.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, 0.0F, -0.1309F, -1.0472F, 0.0F));

        PartDefinition cube_r100 = RMW1.addOrReplaceChild("cube_r100", CubeListBuilder.create().texOffs(188, 328).addBox(0.0F, -1.0F, 0.0F, 1.0F, 1.0F, 28.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.1309F, -1.0472F, 0.0F));

        PartDefinition RMW2 = RMidWing.addOrReplaceChild("RMW2", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r101 = RMW2.addOrReplaceChild("cube_r101", CubeListBuilder.create().texOffs(306, 304).addBox(0.0F, 0.0F, 0.0F, 8.0F, 0.0F, 24.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, 0.0F, -0.1309F, -0.6981F, 0.0F));

        PartDefinition cube_r102 = RMW2.addOrReplaceChild("cube_r102", CubeListBuilder.create().texOffs(344, 159).addBox(0.0F, -1.0F, 0.0F, 1.0F, 1.0F, 24.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.1309F, -0.6981F, 0.0F));

        PartDefinition RMW3 = RMidWing.addOrReplaceChild("RMW3", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r103 = RMW3.addOrReplaceChild("cube_r103", CubeListBuilder.create().texOffs(302, 328).addBox(0.0F, 0.0F, 0.0F, 8.0F, 0.0F, 22.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, 0.0F, -0.1309F, -0.3491F, 0.0F));

        PartDefinition cube_r104 = RMW3.addOrReplaceChild("cube_r104", CubeListBuilder.create().texOffs(104, 346).addBox(0.0F, -2.0F, 0.0F, 1.0F, 1.0F, 22.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.0F, 0.0F, -0.1309F, -0.3491F, 0.0F));

        PartDefinition seat = root.addOrReplaceChild("seat", CubeListBuilder.create(), PartPose.offset(0.0F, -6.0F, -5.0F));

        return LayerDefinition.create(meshdefinition, 512, 512);
	}

    @Override
    public void setupAnim(EnergyDragonRenderState state) {
        super.setupAnim(state);

        // --- Scale and Transformation Logic ---
        this.root.xScale = 1.0f;
        this.root.yScale = 1.0f;
        this.root.zScale = 1.0f;

        if (state.isBaby) {
            float babyScale = 0.5f;
            this.root.xScale = babyScale;
            this.root.yScale = babyScale;
            this.root.zScale = babyScale;
            this.root.y = 14.0f;
        }

        // Pitch/Tilt Logic
        if (state.isRidden && state.isFlying && !state.isBaby) {
            this.root.yRot = 0.0f;
            this.root.xRot = state.xRot * ((float)Math.PI / 180F);
        } else {
            this.root.xRot = 0.0f;
        }

        float ageInTicks = state.ageInTicks;

        // --- Animation Logic ---

        if (state.isSleeping) {
            // Only apply sleep and blink.
            // This prevents wings from trying to "flap" or "idle" while tucked for sleep.
            this.sleepAnimation.apply(state.sleepingAnimationState, ageInTicks);
        } else {
            // 1. Walk Animation (Only on ground)
            if (!state.isFlying) {
                this.walkAnimation.applyWalk(state.walkAnimationPos, state.walkAnimationSpeed, 1.0f, 2.5f);
            }

            // 2. Base State Animations (Only when awake)
            this.idleAnimation.apply(state.idleAnimationState, ageInTicks);
            this.flyAnimation.apply(state.flyAnimationState, ageInTicks);
            this.hoverAnimation.apply(state.hoverAnimationState, ageInTicks);
        }

        // 3. The Blink Animation
        // Applied outside the 'if' because dragons can blink while awake OR asleep!
        this.blinkAnimation.apply(state.blinkAnimationState, ageInTicks);
    }

    public ModelPart getRoot() {
        return root;
    }
}