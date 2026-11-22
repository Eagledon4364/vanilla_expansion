package com.chris.vanilla_expansion_dragons.entity.client.model;

import com.chris.vanilla_expansion_dragons.entity.client.ModDragonModelLayers;
import com.chris.vanilla_expansion_dragons.entity.client.animation.ElderWingDragonAnimation;
import com.chris.vanilla_expansion_dragons.entity.client.renderer.elderwing.ElderWingDragonRenderState;
import com.chris.vanilla_expansion_dragons.entity.server.util.CosmeticStyle;
import net.minecraft.client.animation.KeyframeAnimation;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ElderWingDragonModel extends EntityModel<@NotNull ElderWingDragonRenderState> {
    public static final ModelLayerLocation LAYER_LOCATION = ModDragonModelLayers.ELDERWING_DRAGON;
    private final ModelPart root;
    private final ModelPart hips;
    private final ModelPart fin_hips;
    private final ModelPart spike_hips;
    private final ModelPart tail;
    private final ModelPart fin_tail;
    private final ModelPart spike_tail;
    private final ModelPart tail1;
    private final ModelPart fin_tail1;
    private final ModelPart spike_tail1;
    private final ModelPart tail2;
    private final ModelPart fin_tail2;
    private final ModelPart spike_tail2;
    private final ModelPart tail3;
    private final ModelPart fin_tail3;
    private final ModelPart spike_tail3;
    private final ModelPart tail4;
    private final ModelPart left_tail_wing3;
    private final ModelPart left_tail_wing4;
    private final ModelPart left_tail_wing2;
    private final ModelPart left_tail_wing;
    private final ModelPart right_tail_wing4;
    private final ModelPart right_tail_wing3;
    private final ModelPart right_tail_wing2;
    private final ModelPart right_tail_wing;
    private final ModelPart fin_tail4;
    private final ModelPart spike_tail4;
    private final ModelPart left_back_leg;
    private final ModelPart left_back_mid_leg;
    private final ModelPart left_back_low_leg;
    private final ModelPart left_back_foot;
    private final ModelPart right_back_leg;
    private final ModelPart right_back_mid_leg;
    private final ModelPart right_back_low_leg;
    private final ModelPart right_back_foot;
    private final ModelPart chest;
    private final ModelPart left_wing;
    private final ModelPart left_wing_fin;
    private final ModelPart left_wing_mid;
    private final ModelPart left_wing_formem;
    private final ModelPart left_wing_tip;
    private final ModelPart left_wing_tip2;
    private final ModelPart left_wing_tip3;
    private final ModelPart left_wing_claw;
    private final ModelPart right_wing;
    private final ModelPart right_wing_fin;
    private final ModelPart right_wing_mid;
    private final ModelPart right_wing_formem;
    private final ModelPart right_wing_tip;
    private final ModelPart right_wing_tip2;
    private final ModelPart right_wing_tip3;
    private final ModelPart right_wing_claw;
    private final ModelPart fin_chest;
    private final ModelPart spike_chest;
    private final ModelPart neck;
    private final ModelPart fin_neck;
    private final ModelPart spike_neck;
    private final ModelPart head;
    private final ModelPart fin_head;
    private final ModelPart spike_head;
    private final ModelPart jaw;
    private final ModelPart spike_jaw;
    private final ModelPart fin_jaw;
    private final ModelPart saddle;
    private final ModelPart saddle_lock;
    private final ModelPart left_front_leg;
    private final ModelPart left_front_mid_leg;
    private final ModelPart left_front_foot;
    private final ModelPart right_front_leg;
    private final ModelPart right_front_mid_leg;
    private final ModelPart right_front_foot;

    private final List<ModelPart> finParts;
    private final List<ModelPart> spikeParts;

     private final KeyframeAnimation idleAnimation;
     private final KeyframeAnimation walkAnimation;
     private final KeyframeAnimation runAnimation;
     private final KeyframeAnimation hoverAnimation;
     private final KeyframeAnimation glideAnimation;
     private final KeyframeAnimation flyAnimation;
     private final KeyframeAnimation sleepAnimation;
     private final KeyframeAnimation sitAnimation;
     private final KeyframeAnimation breathAnimation;
     private final KeyframeAnimation breathAttackAnimation;
    public ElderWingDragonModel(ModelPart root) {
        super(root);
        this.root = root.getChild("root");
        this.hips = this.root.getChild("hips");
        this.fin_hips = this.hips.getChild("fin_hips");
        this.spike_hips = this.hips.getChild("spike_hips");
        this.tail = this.hips.getChild("tail");
        this.fin_tail = this.tail.getChild("fin_tail");
        this.spike_tail = this.tail.getChild("spike_tail");
        this.tail1 = this.tail.getChild("tail1");
        this.fin_tail1 = this.tail1.getChild("fin_tail1");
        this.spike_tail1 = this.tail1.getChild("spike_tail1");
        this.tail2 = this.tail1.getChild("tail2");
        this.fin_tail2 = this.tail2.getChild("fin_tail2");
        this.spike_tail2 = this.tail2.getChild("spike_tail2");
        this.tail3 = this.tail2.getChild("tail3");
        this.fin_tail3 = this.tail3.getChild("fin_tail3");
        this.spike_tail3 = this.tail3.getChild("spike_tail3");
        this.tail4 = this.tail3.getChild("tail4");
        this.left_tail_wing3 = this.tail4.getChild("left_tail_wing3");
        this.left_tail_wing4 = this.tail4.getChild("left_tail_wing4");
        this.left_tail_wing2 = this.tail4.getChild("left_tail_wing2");
        this.left_tail_wing = this.tail4.getChild("left_tail_wing");
        this.right_tail_wing4 = this.tail4.getChild("right_tail_wing4");
        this.right_tail_wing3 = this.tail4.getChild("right_tail_wing3");
        this.right_tail_wing2 = this.tail4.getChild("right_tail_wing2");
        this.right_tail_wing = this.tail4.getChild("right_tail_wing");
        this.fin_tail4 = this.tail4.getChild("fin_tail4");
        this.spike_tail4 = this.tail4.getChild("spike_tail4");
        this.left_back_leg = this.hips.getChild("left_back_leg");
        this.left_back_mid_leg = this.left_back_leg.getChild("left_back_mid_leg");
        this.left_back_low_leg = this.left_back_mid_leg.getChild("left_back_low_leg");
        this.left_back_foot = this.left_back_low_leg.getChild("left_back_foot");
        this.right_back_leg = this.hips.getChild("right_back_leg");
        this.right_back_mid_leg = this.right_back_leg.getChild("right_back_mid_leg");
        this.right_back_low_leg = this.right_back_mid_leg.getChild("right_back_low_leg");
        this.right_back_foot = this.right_back_low_leg.getChild("right_back_foot");
        this.chest = this.hips.getChild("chest");
        this.left_wing = this.chest.getChild("left_wing");
        this.left_wing_fin = this.left_wing.getChild("left_wing_fin");
        this.left_wing_mid = this.left_wing.getChild("left_wing_mid");
        this.left_wing_formem = this.left_wing_mid.getChild("left_wing_formem");
        this.left_wing_tip = this.left_wing_mid.getChild("left_wing_tip");
        this.left_wing_tip2 = this.left_wing_mid.getChild("left_wing_tip2");
        this.left_wing_tip3 = this.left_wing_mid.getChild("left_wing_tip3");
        this.left_wing_claw = this.left_wing_mid.getChild("left_wing_claw");
        this.right_wing = this.chest.getChild("right_wing");
        this.right_wing_fin = this.right_wing.getChild("right_wing_fin");
        this.right_wing_mid = this.right_wing.getChild("right_wing_mid");
        this.right_wing_formem = this.right_wing_mid.getChild("right_wing_formem");
        this.right_wing_tip = this.right_wing_mid.getChild("right_wing_tip");
        this.right_wing_tip2 = this.right_wing_mid.getChild("right_wing_tip2");
        this.right_wing_tip3 = this.right_wing_mid.getChild("right_wing_tip3");
        this.right_wing_claw = this.right_wing_mid.getChild("right_wing_claw");
        this.fin_chest = this.chest.getChild("fin_chest");
        this.spike_chest = this.chest.getChild("spike_chest");
        this.neck = this.chest.getChild("neck");
        this.fin_neck = this.neck.getChild("fin_neck");
        this.spike_neck = this.neck.getChild("spike_neck");
        this.head = this.neck.getChild("head");
        this.fin_head = this.head.getChild("fin_head");
        this.spike_head = this.head.getChild("spike_head");
        this.jaw = this.head.getChild("jaw");
        this.spike_jaw = this.jaw.getChild("spike_jaw");
        this.fin_jaw = this.jaw.getChild("fin_jaw");
        this.saddle = this.chest.getChild("saddle");
        this.saddle_lock = this.saddle.getChild("saddle_lock");
        this.left_front_leg = this.chest.getChild("left_front_leg");
        this.left_front_mid_leg = this.left_front_leg.getChild("left_front_mid_leg");
        this.left_front_foot = this.left_front_mid_leg.getChild("left_front_foot");
        this.right_front_leg = this.chest.getChild("right_front_leg");
        this.right_front_mid_leg = this.right_front_leg.getChild("right_front_mid_leg");
        this.right_front_foot = this.right_front_mid_leg.getChild("right_front_foot");
        this.finParts = List.of(
                this.fin_hips, this.fin_tail, this.fin_tail1, this.fin_tail2, this.fin_tail3, this.fin_tail4,
                this.fin_chest, this.fin_neck, this.fin_head, this.fin_jaw
        );

        this.spikeParts = List.of(
                this.spike_hips, this.spike_tail, this.spike_tail1, this.spike_tail2, this.spike_tail3, this.spike_tail4,
                this.spike_chest, this.spike_neck, this.spike_head, this.spike_jaw
        );

         this.idleAnimation = ElderWingDragonAnimation.IDLE.bake(this.root);
         this.walkAnimation = ElderWingDragonAnimation.WALK.bake(this.root);
         this.runAnimation = ElderWingDragonAnimation.RUN.bake(this.root);
         this.hoverAnimation = ElderWingDragonAnimation.HOVER.bake(this.root);
         this.glideAnimation = ElderWingDragonAnimation.GLIDE.bake(this.root);
         this.flyAnimation = ElderWingDragonAnimation.FLY_VELOCITY.bake(this.root);
         this.sleepAnimation = ElderWingDragonAnimation.SLEEP.bake(this.root);
         this.sitAnimation = ElderWingDragonAnimation.SIT.bake(this.root);
         this.breathAnimation = ElderWingDragonAnimation.MOUTH_BREATH.bake(this.root);
         this.breathAttackAnimation = ElderWingDragonAnimation.MOUTH_FIRE.bake(this.root);

    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 4.0F));

        PartDefinition hips = root.addOrReplaceChild("hips", CubeListBuilder.create().texOffs(252, 109).addBox(-4.0F, -5.0F, -11.3F, 8.0F, 8.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(258, 271).addBox(-3.0F, -6.0F, -15.0F, 6.0F, 8.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -22.0F, 15.0F));

        PartDefinition cube_r1 = hips.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(120, 290).addBox(-1.0F, 0.0F, 0.0F, 1.0F, 7.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.25F, -5.0F, -10.25F, -0.1745F, -0.0873F, 0.0873F));

        PartDefinition cube_r2 = hips.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(290, 213).addBox(0.0F, 0.0F, 0.0F, 1.0F, 7.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.25F, -5.0F, -10.25F, -0.1745F, 0.0873F, -0.0873F));

        PartDefinition cube_r3 = hips.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(46, 216).addBox(-10.0F, -8.0F, -5.5F, 11.0F, 8.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.5F, 5.0F, -2.0F, -0.2182F, 0.0F, 0.0F));

        PartDefinition cube_r4 = hips.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(222, 144).addBox(-5.0F, 0.0F, 0.0F, 5.0F, 2.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -7.05F, -10.075F, -0.2182F, -0.0393F, -0.1745F));

        PartDefinition cube_r5 = hips.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(224, 216).addBox(0.0F, 0.0F, 0.0F, 5.0F, 2.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -7.05F, -10.075F, -0.2182F, 0.0393F, 0.1745F));

        PartDefinition fin_hips = hips.addOrReplaceChild("fin_hips", CubeListBuilder.create(), PartPose.offset(0.0F, -2.0F, 2.0F));

        PartDefinition cube_r6 = fin_hips.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(128, 239).addBox(0.0F, -3.0F, 0.0F, 0.0F, 3.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -5.0F, -12.0F, -0.2182F, 0.0F, 0.0F));

        PartDefinition spike_hips = hips.addOrReplaceChild("spike_hips", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r7 = spike_hips.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(300, 204).addBox(-0.5F, -2.0F, 0.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -4.15F, 2.2F, -1.1345F, 0.0F, 0.0F));

        PartDefinition cube_r8 = spike_hips.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(300, 200).addBox(-0.5F, -2.0F, 0.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -5.1F, -1.8F, -1.1345F, 0.0F, 0.0F));

        PartDefinition cube_r9 = spike_hips.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(300, 131).addBox(-0.5F, -2.0F, 0.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -6.0F, -5.8F, -1.0472F, 0.0F, 0.0F));

        PartDefinition cube_r10 = spike_hips.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(300, 127).addBox(-0.5F, -2.0F, 0.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -6.95F, -9.8F, -1.0036F, 0.0F, 0.0F));

        PartDefinition tail = hips.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(180, 216).addBox(-4.0F, -2.95F, -0.15F, 8.0F, 3.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 1.5F, 4.0F));

        PartDefinition cube_r11 = tail.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(226, 21).addBox(0.0F, -6.0F, -15.0F, 6.0F, 6.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 4.275F, 14.85F, 0.0F, 0.0F, -0.7854F));

        PartDefinition cube_r12 = tail.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(236, 48).addBox(-4.0F, 0.0F, 0.0F, 4.0F, 2.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -4.7F, -0.15F, 0.0F, 0.0F, -0.4363F));

        PartDefinition cube_r13 = tail.addOrReplaceChild("cube_r13", CubeListBuilder.create().texOffs(38, 235).addBox(0.0F, 0.0F, 0.0F, 4.0F, 2.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -4.7F, -0.15F, 0.0F, 0.0F, 0.4363F));

        PartDefinition fin_tail = tail.addOrReplaceChild("fin_tail", CubeListBuilder.create().texOffs(134, 258).addBox(0.0F, -7.0F, 0.85F, 0.0F, 3.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -0.5F, 0.0F));

        PartDefinition spike_tail = tail.addOrReplaceChild("spike_tail", CubeListBuilder.create(), PartPose.offset(0.0F, -0.5F, -0.25F));

        PartDefinition cube_r14 = spike_tail.addOrReplaceChild("cube_r14", CubeListBuilder.create().texOffs(300, 249).addBox(-0.5F, -2.0F, 0.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -4.025F, 10.25F, -0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r15 = spike_tail.addOrReplaceChild("cube_r15", CubeListBuilder.create().texOffs(300, 245).addBox(-0.5F, -2.0F, 0.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -4.025F, 6.25F, -0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r16 = spike_tail.addOrReplaceChild("cube_r16", CubeListBuilder.create().texOffs(300, 208).addBox(-0.5F, -2.0F, 0.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -4.025F, 2.25F, -0.7854F, 0.0F, 0.0F));

        PartDefinition tail1 = tail.addOrReplaceChild("tail1", CubeListBuilder.create().texOffs(178, 0).addBox(-3.5F, -2.75F, -0.15F, 7.0F, 3.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 14.0F));

        PartDefinition cube_r17 = tail1.addOrReplaceChild("cube_r17", CubeListBuilder.create().texOffs(96, 192).addBox(-5.0F, -5.0F, -15.0F, 5.0F, 5.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 3.7F, 14.85F, 0.0F, 0.0F, 0.7854F));

        PartDefinition cube_r18 = tail1.addOrReplaceChild("cube_r18", CubeListBuilder.create().texOffs(186, 196).addBox(0.0F, 0.0F, 0.0F, 4.0F, 2.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -4.375F, -0.15F, 0.0F, 0.0F, 0.4363F));

        PartDefinition cube_r19 = tail1.addOrReplaceChild("cube_r19", CubeListBuilder.create().texOffs(142, 196).addBox(-4.0F, 0.0F, 0.0F, 4.0F, 2.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -4.375F, -0.15F, 0.0F, 0.0F, -0.4363F));

        PartDefinition fin_tail1 = tail1.addOrReplaceChild("fin_tail1", CubeListBuilder.create().texOffs(180, 233).addBox(0.0F, -4.875F, -0.175F, 0.0F, 3.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.5F, 0.0F));

        PartDefinition spike_tail1 = tail1.addOrReplaceChild("spike_tail1", CubeListBuilder.create(), PartPose.offset(0.0F, -0.5F, -0.25F));

        PartDefinition cube_r20 = spike_tail1.addOrReplaceChild("cube_r20", CubeListBuilder.create().texOffs(300, 265).addBox(-0.5F, -2.0F, 0.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -3.65F, 9.725F, -0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r21 = spike_tail1.addOrReplaceChild("cube_r21", CubeListBuilder.create().texOffs(300, 261).addBox(-0.5F, -2.0F, 0.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -3.65F, 5.025F, -0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r22 = spike_tail1.addOrReplaceChild("cube_r22", CubeListBuilder.create().texOffs(300, 257).addBox(-0.5F, -2.0F, 0.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -3.65F, 14.375F, -0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r23 = spike_tail1.addOrReplaceChild("cube_r23", CubeListBuilder.create().texOffs(300, 253).addBox(-0.5F, -2.0F, 0.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -3.65F, 0.375F, -0.7854F, 0.0F, 0.0F));

        PartDefinition tail2 = tail1.addOrReplaceChild("tail2", CubeListBuilder.create().texOffs(96, 170).addBox(-2.5F, -1.775F, 0.1F, 5.0F, 2.0F, 20.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 17.75F));

        PartDefinition cube_r24 = tail2.addOrReplaceChild("cube_r24", CubeListBuilder.create().texOffs(146, 170).addBox(-4.0F, -4.0F, -15.0F, 4.0F, 4.0F, 20.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.925F, 15.1F, 0.0F, 0.0F, 0.7854F));

        PartDefinition cube_r25 = tail2.addOrReplaceChild("cube_r25", CubeListBuilder.create().texOffs(48, 194).addBox(-3.0F, 0.0F, 0.0F, 3.0F, 2.0F, 20.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -3.4F, 0.1F, 0.0F, 0.0F, -0.4363F));

        PartDefinition cube_r26 = tail2.addOrReplaceChild("cube_r26", CubeListBuilder.create().texOffs(194, 169).addBox(0.0F, 0.0F, 0.0F, 3.0F, 1.0F, 20.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -3.4F, 0.1F, 0.0F, 0.0F, 0.4363F));

        PartDefinition fin_tail2 = tail2.addOrReplaceChild("fin_tail2", CubeListBuilder.create().texOffs(140, 216).addBox(0.0F, -5.875F, -0.175F, 0.0F, 3.0F, 20.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -0.5F, 0.25F));

        PartDefinition spike_tail2 = tail2.addOrReplaceChild("spike_tail2", CubeListBuilder.create(), PartPose.offset(0.0F, -0.5F, 0.0F));

        PartDefinition cube_r27 = spike_tail2.addOrReplaceChild("cube_r27", CubeListBuilder.create().texOffs(302, 94).addBox(-0.5F, -2.0F, 0.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -2.675F, 12.5F, -0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r28 = spike_tail2.addOrReplaceChild("cube_r28", CubeListBuilder.create().texOffs(302, 90).addBox(-0.5F, -2.0F, 0.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -2.675F, 8.5F, -0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r29 = spike_tail2.addOrReplaceChild("cube_r29", CubeListBuilder.create().texOffs(8, 301).addBox(-0.5F, -2.0F, 0.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -2.675F, 4.5F, -0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r30 = spike_tail2.addOrReplaceChild("cube_r30", CubeListBuilder.create().texOffs(300, 288).addBox(-0.5F, -2.0F, 0.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -2.675F, 16.5F, -0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r31 = spike_tail2.addOrReplaceChild("cube_r31", CubeListBuilder.create().texOffs(300, 284).addBox(-0.5F, -2.0F, 0.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -2.675F, 0.5F, -0.7854F, 0.0F, 0.0F));

        PartDefinition tail3 = tail2.addOrReplaceChild("tail3", CubeListBuilder.create().texOffs(48, 146).addBox(-1.5F, -1.275F, 0.1F, 3.0F, 2.0F, 22.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 20.0F));

        PartDefinition cube_r32 = tail3.addOrReplaceChild("cube_r32", CubeListBuilder.create().texOffs(178, 21).addBox(-2.0F, 0.0F, 0.0F, 2.0F, 1.0F, 22.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -2.575F, 0.05F, 0.0F, 0.0F, -0.4363F));

        PartDefinition cube_r33 = tail3.addOrReplaceChild("cube_r33", CubeListBuilder.create().texOffs(48, 170).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 22.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.775F, 0.05F, 0.0F, 0.0F, 0.7854F));

        PartDefinition cube_r34 = tail3.addOrReplaceChild("cube_r34", CubeListBuilder.create().texOffs(0, 173).addBox(0.0F, 0.0F, 0.0F, 2.0F, 1.0F, 22.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -2.575F, 0.05F, 0.0F, 0.0F, 0.4363F));

        PartDefinition fin_tail3 = tail3.addOrReplaceChild("fin_tail3", CubeListBuilder.create().texOffs(178, 144).addBox(0.0F, -5.0F, -0.2F, 0.0F, 3.0F, 22.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -0.5F, 0.25F));

        PartDefinition spike_tail3 = tail3.addOrReplaceChild("spike_tail3", CubeListBuilder.create(), PartPose.offset(0.0F, -0.5F, 0.0F));

        PartDefinition cube_r35 = spike_tail3.addOrReplaceChild("cube_r35", CubeListBuilder.create().texOffs(302, 212).addBox(-0.5F, -2.0F, 0.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.85F, 9.75F, -0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r36 = spike_tail3.addOrReplaceChild("cube_r36", CubeListBuilder.create().texOffs(210, 302).addBox(-0.5F, -2.0F, 0.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.85F, 14.5F, -0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r37 = spike_tail3.addOrReplaceChild("cube_r37", CubeListBuilder.create().texOffs(204, 302).addBox(-0.5F, -2.0F, 0.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.85F, 5.0F, -0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r38 = spike_tail3.addOrReplaceChild("cube_r38", CubeListBuilder.create().texOffs(126, 302).addBox(-0.5F, -2.0F, 0.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.85F, 18.75F, -0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r39 = spike_tail3.addOrReplaceChild("cube_r39", CubeListBuilder.create().texOffs(120, 302).addBox(-0.5F, -2.0F, 0.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.85F, 0.75F, -0.7854F, 0.0F, 0.0F));

        PartDefinition tail4 = tail3.addOrReplaceChild("tail4", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 22.0F));

        PartDefinition cube_r40 = tail4.addOrReplaceChild("cube_r40", CubeListBuilder.create().texOffs(126, 144).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 24.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.325F, 0.05F, 0.0F, 0.0F, -0.7854F));

        PartDefinition left_tail_wing3 = tail4.addOrReplaceChild("left_tail_wing3", CubeListBuilder.create(), PartPose.offset(1.0F, -0.5F, 2.25F));

        PartDefinition cube_r41 = left_tail_wing3.addOrReplaceChild("cube_r41", CubeListBuilder.create().texOffs(94, 215).addBox(-5.0F, 0.0F, 0.0F, 5.0F, 0.0F, 18.0F, new CubeDeformation(0.0F))
                .texOffs(90, 233).addBox(-1.0F, -0.5F, 0.0F, 1.0F, 1.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 10.0F, 0.0F, 0.6545F, 0.0F));

        PartDefinition left_tail_wing4 = tail4.addOrReplaceChild("left_tail_wing4", CubeListBuilder.create(), PartPose.offset(1.0F, -0.5F, 2.25F));

        PartDefinition cube_r42 = left_tail_wing4.addOrReplaceChild("cube_r42", CubeListBuilder.create().texOffs(256, 234).addBox(-4.0F, 0.0F, 0.0F, 4.0F, 0.0F, 11.0F, new CubeDeformation(0.0F))
                .texOffs(234, 271).addBox(-1.0F, -0.5F, 0.0F, 1.0F, 1.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 14.0F, 0.0F, 0.48F, 0.0F));

        PartDefinition left_tail_wing2 = tail4.addOrReplaceChild("left_tail_wing2", CubeListBuilder.create(), PartPose.offset(1.0F, -0.5F, 2.25F));

        PartDefinition cube_r43 = left_tail_wing2.addOrReplaceChild("cube_r43", CubeListBuilder.create().texOffs(240, 168).addBox(-1.0F, -0.5F, 0.0F, 1.0F, 1.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(230, 190).addBox(-6.0F, 0.0F, 0.0F, 6.0F, 0.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 5.0F, 0.0F, 0.829F, 0.0F));

        PartDefinition left_tail_wing = tail4.addOrReplaceChild("left_tail_wing", CubeListBuilder.create(), PartPose.offset(1.0F, -0.5F, 2.25F));

        PartDefinition cube_r44 = left_tail_wing.addOrReplaceChild("cube_r44", CubeListBuilder.create().texOffs(74, 252).addBox(-6.0F, 0.0F, 0.0F, 6.0F, 0.0F, 12.0F, new CubeDeformation(0.0F))
                .texOffs(266, 20).addBox(-1.0F, -0.5F, 0.0F, 1.0F, 1.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0036F, 0.0F));

        PartDefinition right_tail_wing4 = tail4.addOrReplaceChild("right_tail_wing4", CubeListBuilder.create(), PartPose.offset(-1.0F, -0.5F, 2.25F));

        PartDefinition cube_r45 = right_tail_wing4.addOrReplaceChild("cube_r45", CubeListBuilder.create().texOffs(270, 64).addBox(0.0F, -0.5F, 0.0F, 1.0F, 1.0F, 11.0F, new CubeDeformation(0.0F))
                .texOffs(256, 83).addBox(0.0F, 0.0F, 0.0F, 4.0F, 0.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 14.0F, 0.0F, -0.48F, 0.0F));

        PartDefinition right_tail_wing3 = tail4.addOrReplaceChild("right_tail_wing3", CubeListBuilder.create(), PartPose.offset(-1.0F, -0.5F, 2.25F));

        PartDefinition cube_r46 = right_tail_wing3.addOrReplaceChild("cube_r46", CubeListBuilder.create().texOffs(0, 216).addBox(0.0F, 0.0F, 0.0F, 5.0F, 0.0F, 18.0F, new CubeDeformation(0.0F))
                .texOffs(0, 234).addBox(0.0F, -0.5F, 0.0F, 1.0F, 1.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 10.0F, 0.0F, -0.6545F, 0.0F));

        PartDefinition right_tail_wing2 = tail4.addOrReplaceChild("right_tail_wing2", CubeListBuilder.create(), PartPose.offset(-1.0F, -0.5F, 2.25F));

        PartDefinition cube_r47 = right_tail_wing2.addOrReplaceChild("cube_r47", CubeListBuilder.create().texOffs(228, 0).addBox(0.0F, 0.0F, 0.0F, 6.0F, 0.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(236, 64).addBox(0.0F, -0.5F, 0.0F, 1.0F, 1.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 5.0F, 0.0F, -0.829F, 0.0F));

        PartDefinition right_tail_wing = tail4.addOrReplaceChild("right_tail_wing", CubeListBuilder.create(), PartPose.offset(-1.0F, -0.5F, 2.25F));

        PartDefinition cube_r48 = right_tail_wing.addOrReplaceChild("cube_r48", CubeListBuilder.create().texOffs(160, 268).addBox(0.0F, -0.5F, 0.0F, 1.0F, 1.0F, 12.0F, new CubeDeformation(0.0F))
                .texOffs(252, 97).addBox(0.0F, 0.0F, 0.0F, 6.0F, 0.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0036F, 0.0F));

        PartDefinition fin_tail4 = tail4.addOrReplaceChild("fin_tail4", CubeListBuilder.create().texOffs(0, 146).addBox(0.0F, -4.2F, -0.225F, 0.0F, 3.0F, 24.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -0.5F, 0.25F));

        PartDefinition spike_tail4 = tail4.addOrReplaceChild("spike_tail4", CubeListBuilder.create(), PartPose.offset(0.0F, -0.5F, -0.25F));

        PartDefinition cube_r49 = spike_tail4.addOrReplaceChild("cube_r49", CubeListBuilder.create().texOffs(304, 66).addBox(-0.5F, -2.0F, 0.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.75F, 19.8F, -0.8727F, 0.0F, 0.0F));

        PartDefinition cube_r50 = spike_tail4.addOrReplaceChild("cube_r50", CubeListBuilder.create().texOffs(304, 63).addBox(-0.5F, -2.0F, 0.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.625F, 22.55F, -0.8727F, 0.0F, 0.0F));

        PartDefinition cube_r51 = spike_tail4.addOrReplaceChild("cube_r51", CubeListBuilder.create().texOffs(212, 258).addBox(-0.5F, -3.0F, 0.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.325F, 16.3F, -0.8727F, 0.0F, 0.0F));

        PartDefinition cube_r52 = spike_tail4.addOrReplaceChild("cube_r52", CubeListBuilder.create().texOffs(34, 257).addBox(-0.5F, -3.0F, 0.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.325F, 13.3F, -0.8727F, 0.0F, 0.0F));

        PartDefinition cube_r53 = spike_tail4.addOrReplaceChild("cube_r53", CubeListBuilder.create().texOffs(212, 254).addBox(-0.5F, -3.0F, 0.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.325F, 10.3F, -0.8727F, 0.0F, 0.0F));

        PartDefinition cube_r54 = spike_tail4.addOrReplaceChild("cube_r54", CubeListBuilder.create().texOffs(34, 253).addBox(-0.5F, -3.0F, 0.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.475F, 7.275F, -0.8727F, 0.0F, 0.0F));

        PartDefinition cube_r55 = spike_tail4.addOrReplaceChild("cube_r55", CubeListBuilder.create().texOffs(90, 228).addBox(-0.5F, -3.0F, 0.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.575F, 3.8F, -0.8727F, 0.0F, 0.0F));

        PartDefinition cube_r56 = spike_tail4.addOrReplaceChild("cube_r56", CubeListBuilder.create().texOffs(90, 224).addBox(-0.5F, -3.0F, 0.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.7F, 0.55F, -0.8727F, 0.0F, 0.0F));

        PartDefinition left_back_leg = hips.addOrReplaceChild("left_back_leg", CubeListBuilder.create(), PartPose.offset(7.5F, 0.0F, -0.25F));

        PartDefinition cube_r57 = left_back_leg.addOrReplaceChild("cube_r57", CubeListBuilder.create().texOffs(148, 283).addBox(-2.0F, 0.0F, 0.0F, 4.0F, 11.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -3.1859F, -3.0903F, -0.0873F, 0.0F, 0.0F));

        PartDefinition cube_r58 = left_back_leg.addOrReplaceChild("cube_r58", CubeListBuilder.create().texOffs(252, 136).addBox(-2.0F, -6.0F, -2.0F, 4.0F, 6.0F, 2.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 9.5F, -0.25F, -0.5672F, 0.0F, 0.0F));

        PartDefinition cube_r59 = left_back_leg.addOrReplaceChild("cube_r59", CubeListBuilder.create().texOffs(180, 295).addBox(-2.0F, -12.0F, -4.0F, 4.0F, 7.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 10.1946F, 3.6892F, -0.1745F, 0.0F, 0.0F));

        PartDefinition cube_r60 = left_back_leg.addOrReplaceChild("cube_r60", CubeListBuilder.create().texOffs(76, 293).addBox(-1.0F, 0.0F, -2.0F, 1.0F, 11.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0113F, -2.4035F, 0.849F, -0.1745F, 0.0F, 0.0873F));

        PartDefinition cube_r61 = left_back_leg.addOrReplaceChild("cube_r61", CubeListBuilder.create().texOffs(74, 235).addBox(-2.0F, -12.0F, -4.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 9.5F, -0.25F, -0.1745F, 0.0F, 0.0F));

        PartDefinition left_back_mid_leg = left_back_leg.addOrReplaceChild("left_back_mid_leg", CubeListBuilder.create(), PartPose.offset(0.0F, 8.0F, -2.0F));

        PartDefinition cube_r62 = left_back_mid_leg.addOrReplaceChild("cube_r62", CubeListBuilder.create().texOffs(272, 210).addBox(-1.0F, -4.0F, -2.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 3.5F, 7.75F, -0.6981F, 0.0F, 0.0F));

        PartDefinition cube_r63 = left_back_mid_leg.addOrReplaceChild("cube_r63", CubeListBuilder.create().texOffs(98, 146).addBox(-2.0F, -4.0F, -10.0F, 4.0F, 4.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 3.5F, 7.75F, -0.1745F, 0.0F, 0.0F));

        PartDefinition left_back_low_leg = left_back_mid_leg.addOrReplaceChild("left_back_low_leg", CubeListBuilder.create(), PartPose.offset(0.0F, 2.75F, 5.0F));

        PartDefinition cube_r64 = left_back_low_leg.addOrReplaceChild("cube_r64", CubeListBuilder.create().texOffs(0, 297).addBox(-1.0F, -6.0F, -1.0F, 3.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, 9.5F, -4.75F, -0.3054F, 0.0F, 0.0F));

        PartDefinition cube_r65 = left_back_low_leg.addOrReplaceChild("cube_r65", CubeListBuilder.create().texOffs(190, 281).addBox(-2.0F, -10.0F, 0.0F, 4.0F, 10.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 9.25F, -4.75F, -0.1745F, 0.0F, 0.0F));

        PartDefinition cube_r66 = left_back_low_leg.addOrReplaceChild("cube_r66", CubeListBuilder.create().texOffs(174, 281).addBox(-2.0F, -10.0F, -4.0F, 4.0F, 10.0F, 4.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 10.0F, -0.75F, -0.3491F, 0.0F, 0.0F));

        PartDefinition left_back_foot = left_back_low_leg.addOrReplaceChild("left_back_foot", CubeListBuilder.create().texOffs(292, 149).addBox(-2.0F, 0.0F, -3.0F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(216, 302).addBox(-0.5F, 1.0F, -5.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(252, 302).addBox(-0.5F, 1.0F, 1.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 9.25F, -2.75F));

        PartDefinition cube_r67 = left_back_foot.addOrReplaceChild("cube_r67", CubeListBuilder.create().texOffs(302, 216).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 1.0F, -0.5236F, 0.0F, 0.0F));

        PartDefinition cube_r68 = left_back_foot.addOrReplaceChild("cube_r68", CubeListBuilder.create().texOffs(302, 222).addBox(-1.0F, 0.0F, -2.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, 0.0F, -3.0F, 0.5236F, 0.0F, 0.0F));

        PartDefinition cube_r69 = left_back_foot.addOrReplaceChild("cube_r69", CubeListBuilder.create().texOffs(222, 302).addBox(-1.0F, 0.0F, -2.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 0.0F, -3.0F, 0.5236F, 0.4363F, 0.0F));

        PartDefinition cube_r70 = left_back_foot.addOrReplaceChild("cube_r70", CubeListBuilder.create().texOffs(106, 287).addBox(-1.0F, -1.0F, -2.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 2.0F, -3.0F, 0.0F, 0.4363F, 0.0F));

        PartDefinition cube_r71 = left_back_foot.addOrReplaceChild("cube_r71", CubeListBuilder.create().texOffs(302, 219).addBox(0.0F, 0.0F, -2.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 0.0F, -3.0F, 0.5236F, -0.4363F, 0.0F));

        PartDefinition cube_r72 = left_back_foot.addOrReplaceChild("cube_r72", CubeListBuilder.create().texOffs(284, 61).addBox(0.0F, -1.0F, -2.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 2.0F, -3.0F, 0.0F, -0.4363F, 0.0F));

        PartDefinition right_back_leg = hips.addOrReplaceChild("right_back_leg", CubeListBuilder.create(), PartPose.offset(-7.5F, 0.0F, -0.25F));

        PartDefinition cube_r73 = right_back_leg.addOrReplaceChild("cube_r73", CubeListBuilder.create().texOffs(294, 61).addBox(-2.0F, 0.0F, 0.0F, 4.0F, 11.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -3.1859F, -3.0903F, -0.0873F, 0.0F, 0.0F));

        PartDefinition cube_r74 = right_back_leg.addOrReplaceChild("cube_r74", CubeListBuilder.create().texOffs(296, 46).addBox(-2.0F, -6.0F, -2.0F, 4.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 9.5F, -0.25F, -0.5672F, 0.0F, 0.0F));

        PartDefinition cube_r75 = right_back_leg.addOrReplaceChild("cube_r75", CubeListBuilder.create().texOffs(192, 295).addBox(-2.0F, -12.0F, -4.0F, 4.0F, 7.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 10.1946F, 3.6892F, -0.1745F, 0.0F, 0.0F));

        PartDefinition cube_r76 = right_back_leg.addOrReplaceChild("cube_r76", CubeListBuilder.create().texOffs(84, 293).addBox(0.0F, 0.0F, -2.0F, 1.0F, 11.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.0113F, -2.4035F, 0.849F, -0.1745F, 0.0F, -0.0873F));

        PartDefinition cube_r77 = right_back_leg.addOrReplaceChild("cube_r77", CubeListBuilder.create().texOffs(56, 265).addBox(-2.0F, -12.0F, -4.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 9.5F, -0.25F, -0.1745F, 0.0F, 0.0F));

        PartDefinition right_back_mid_leg = right_back_leg.addOrReplaceChild("right_back_mid_leg", CubeListBuilder.create(), PartPose.offset(0.0F, 8.0F, -2.0F));

        PartDefinition cube_r78 = right_back_mid_leg.addOrReplaceChild("cube_r78", CubeListBuilder.create().texOffs(112, 300).addBox(-1.0F, -4.0F, -2.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 3.5F, 7.75F, -0.6981F, 0.0F, 0.0F));

        PartDefinition cube_r79 = right_back_mid_leg.addOrReplaceChild("cube_r79", CubeListBuilder.create().texOffs(160, 254).addBox(-2.0F, -4.0F, -10.0F, 4.0F, 4.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 3.5F, 7.75F, -0.1745F, 0.0F, 0.0F));

        PartDefinition right_back_low_leg = right_back_mid_leg.addOrReplaceChild("right_back_low_leg", CubeListBuilder.create(), PartPose.offset(0.0F, 2.75F, 5.0F));

        PartDefinition cube_r80 = right_back_low_leg.addOrReplaceChild("cube_r80", CubeListBuilder.create().texOffs(298, 7).addBox(-2.0F, -6.0F, -1.0F, 3.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, 9.5F, -4.75F, -0.3054F, 0.0F, 0.0F));

        PartDefinition cube_r81 = right_back_low_leg.addOrReplaceChild("cube_r81", CubeListBuilder.create().texOffs(60, 282).addBox(-2.0F, -10.0F, 0.0F, 4.0F, 10.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 9.25F, -4.75F, -0.1745F, 0.0F, 0.0F));

        PartDefinition cube_r82 = right_back_low_leg.addOrReplaceChild("cube_r82", CubeListBuilder.create().texOffs(206, 281).addBox(-2.0F, -10.0F, -4.0F, 4.0F, 10.0F, 4.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 10.0F, -0.75F, -0.3491F, 0.0F, 0.0F));

        PartDefinition right_back_foot = right_back_low_leg.addOrReplaceChild("right_back_foot", CubeListBuilder.create().texOffs(28, 294).addBox(-2.0F, 0.0F, -3.0F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(70, 303).addBox(-0.5F, 1.0F, -5.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(144, 303).addBox(-0.5F, 1.0F, 1.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 9.25F, -2.75F));

        PartDefinition cube_r83 = right_back_foot.addOrReplaceChild("cube_r83", CubeListBuilder.create().texOffs(138, 303).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 1.0F, -0.5236F, 0.0F, 0.0F));

        PartDefinition cube_r84 = right_back_foot.addOrReplaceChild("cube_r84", CubeListBuilder.create().texOffs(132, 303).addBox(0.0F, 0.0F, -2.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, 0.0F, -3.0F, 0.5236F, 0.0F, 0.0F));

        PartDefinition cube_r85 = right_back_foot.addOrReplaceChild("cube_r85", CubeListBuilder.create().texOffs(50, 303).addBox(0.0F, 0.0F, -2.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 0.0F, -3.0F, 0.5236F, -0.4363F, 0.0F));

        PartDefinition cube_r86 = right_back_foot.addOrReplaceChild("cube_r86", CubeListBuilder.create().texOffs(44, 303).addBox(0.0F, -1.0F, -2.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 2.0F, -3.0F, 0.0F, -0.4363F, 0.0F));

        PartDefinition cube_r87 = right_back_foot.addOrReplaceChild("cube_r87", CubeListBuilder.create().texOffs(20, 303).addBox(-1.0F, 0.0F, -2.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 0.0F, -3.0F, 0.5236F, 0.4363F, 0.0F));

        PartDefinition cube_r88 = right_back_foot.addOrReplaceChild("cube_r88", CubeListBuilder.create().texOffs(14, 303).addBox(-1.0F, -1.0F, -2.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 2.0F, -3.0F, 0.0F, 0.4363F, 0.0F));

        PartDefinition chest = hips.addOrReplaceChild("chest", CubeListBuilder.create(), PartPose.offset(0.0F, -2.0F, -10.0F));

        PartDefinition torsoTop_r1 = chest.addOrReplaceChild("torsoTop_r1", CubeListBuilder.create().texOffs(110, 258).addBox(0.0F, 0.0F, 0.0F, 2.0F, 8.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(188, 254).addBox(10.6F, 0.0F, 0.0F, 2.0F, 8.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.3F, 1.325F, -21.55F, 0.3491F, 0.0F, 0.0F));

        PartDefinition torsoTop_r2 = chest.addOrReplaceChild("torsoTop_r2", CubeListBuilder.create().texOffs(280, 202).addBox(0.0F, 0.0F, 2.0F, 3.0F, 4.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-7.675F, 5.625F, -22.275F, -0.175F, 0.0125F, -0.3847F));

        PartDefinition torsoTop_r3 = chest.addOrReplaceChild("torsoTop_r3", CubeListBuilder.create().texOffs(274, 178).addBox(-1.0F, 0.0F, -2.0F, 2.0F, 4.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.825F, 6.025F, -13.05F, 0.3114F, 0.212F, -0.3089F));

        PartDefinition torsoTop_r4 = chest.addOrReplaceChild("torsoTop_r4", CubeListBuilder.create().texOffs(74, 264).addBox(0.0F, 0.0F, 0.0F, 1.0F, 8.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.225F, -1.0F, -12.0F, 0.1752F, 0.0859F, 0.0152F));

        PartDefinition torsoTop_r5 = chest.addOrReplaceChild("torsoTop_r5", CubeListBuilder.create().texOffs(132, 283).addBox(0.0F, 0.0F, -1.0F, 1.0F, 5.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(222, 283).addBox(12.7F, 0.0F, -1.0F, 1.0F, 5.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.85F, 2.35F, -18.3F, 0.3491F, 0.0F, 0.0F));

        PartDefinition torsoTop_r6 = chest.addOrReplaceChild("torsoTop_r6", CubeListBuilder.create().texOffs(274, 190).addBox(-1.0F, 0.0F, -2.0F, 2.0F, 4.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.825F, 6.025F, -13.05F, 0.3114F, -0.212F, 0.3089F));

        PartDefinition torsoTop_r7 = chest.addOrReplaceChild("torsoTop_r7", CubeListBuilder.create().texOffs(96, 276).addBox(-3.0F, 0.0F, 2.0F, 3.0F, 4.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(7.675F, 5.625F, -22.275F, -0.175F, -0.0125F, 0.3847F));

        PartDefinition torsoTop_r8 = chest.addOrReplaceChild("torsoTop_r8", CubeListBuilder.create().texOffs(0, 253).addBox(-5.0F, 0.0F, -17.0F, 10.0F, 3.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 3.9F, -0.55F, 0.1745F, 0.0F, 0.0F));

        PartDefinition torsoTop_r9 = chest.addOrReplaceChild("torsoTop_r9", CubeListBuilder.create().texOffs(216, 84).addBox(-4.5F, -1.0F, -11.0F, 9.0F, 2.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 5.0F, 0.0F, 0.1745F, 0.0F, 0.0F));

        PartDefinition torsoTop_r10 = chest.addOrReplaceChild("torsoTop_r10", CubeListBuilder.create().texOffs(212, 263).addBox(-1.0F, 0.0F, 0.0F, 1.0F, 8.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.225F, -1.0F, -12.0F, 0.1752F, -0.0859F, -0.0152F));

        PartDefinition torsoTop_r11 = chest.addOrReplaceChild("torsoTop_r11", CubeListBuilder.create().texOffs(0, 196).addBox(-5.0F, -2.0F, -11.0F, 10.0F, 8.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.0F, -1.0F, 0.0873F, 0.0F, 0.0F));

        PartDefinition cube_r89 = chest.addOrReplaceChild("cube_r89", CubeListBuilder.create().texOffs(252, 261).addBox(-5.0F, -1.0F, 0.0F, 10.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 6.0F, -22.55F, 0.5236F, 0.0F, 0.0F));

        PartDefinition cube_r90 = chest.addOrReplaceChild("cube_r90", CubeListBuilder.create().texOffs(278, 76).addBox(-4.0F, 0.0F, 0.0F, 8.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.875F, -25.0F, -0.0436F, 0.0F, 0.0F));

        PartDefinition cube_r91 = chest.addOrReplaceChild("cube_r91", CubeListBuilder.create().texOffs(240, 185).addBox(-3.0F, 0.0F, -4.0F, 6.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.0F, -21.5F, 0.5236F, 0.0F, 0.0F));

        PartDefinition cube_r92 = chest.addOrReplaceChild("cube_r92", CubeListBuilder.create().texOffs(282, 261).addBox(-0.0278F, -3.9598F, 0.0569F, 4.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.425F, 2.25F, -24.0F, -0.3204F, -0.98F, 0.8442F));

        PartDefinition cube_r93 = chest.addOrReplaceChild("cube_r93", CubeListBuilder.create().texOffs(276, 33).addBox(-4.0F, -4.0F, 0.0F, 4.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.425F, 2.25F, -24.0F, -0.3204F, 0.98F, -0.8442F));

        PartDefinition cube_r94 = chest.addOrReplaceChild("cube_r94", CubeListBuilder.create().texOffs(38, 251).addBox(-6.0F, 0.0F, 0.0F, 6.0F, 2.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -4.0F, -12.0F, 0.0873F, 0.0175F, -0.1745F));

        PartDefinition cube_r95 = chest.addOrReplaceChild("cube_r95", CubeListBuilder.create().texOffs(252, 249).addBox(-6.0F, 0.0F, -10.0F, 6.0F, 2.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -4.0F, -12.0F, 0.258F, 0.045F, -0.1687F));

        PartDefinition cube_r96 = chest.addOrReplaceChild("cube_r96", CubeListBuilder.create().texOffs(296, 155).addBox(-4.0F, -6.0F, 0.0F, 4.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.5F, 11.0F, -22.0F, 0.8345F, 0.3035F, 0.3185F));

        PartDefinition cube_r97 = chest.addOrReplaceChild("cube_r97", CubeListBuilder.create().texOffs(106, 290).addBox(0.0F, -4.0F, 0.0F, 1.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.5F, 6.0F, -25.0F, 0.0F, -0.3054F, 0.0F));

        PartDefinition cube_r98 = chest.addOrReplaceChild("cube_r98", CubeListBuilder.create().texOffs(266, 33).addBox(-6.0F, -6.0F, -1.0F, 3.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.5F, 11.0F, -22.0F, 0.9553F, 0.5236F, 0.6155F));

        PartDefinition cube_r99 = chest.addOrReplaceChild("cube_r99", CubeListBuilder.create().texOffs(28, 265).addBox(0.0F, -1.0F, 0.0F, 4.0F, 1.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, 11.875F, -13.625F, 0.3053F, 0.1719F, -0.4971F));

        PartDefinition cube_r100 = chest.addOrReplaceChild("cube_r100", CubeListBuilder.create().texOffs(272, 43).addBox(0.0F, -1.0F, 0.0F, 4.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, 10.5F, -21.5F, -0.1585F, -0.0735F, -0.4305F));

        PartDefinition cube_r101 = chest.addOrReplaceChild("cube_r101", CubeListBuilder.create().texOffs(264, 147).addBox(-4.0F, -1.0F, 0.0F, 4.0F, 1.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, 11.875F, -13.625F, 0.3053F, -0.1719F, 0.4971F));

        PartDefinition cube_r102 = chest.addOrReplaceChild("cube_r102", CubeListBuilder.create().texOffs(208, 190).addBox(-3.0F, -1.0F, 0.0F, 6.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 7.875F, -5.625F, 0.1745F, 0.0F, 0.0F));

        PartDefinition cube_r103 = chest.addOrReplaceChild("cube_r103", CubeListBuilder.create().texOffs(264, 136).addBox(-2.0F, -1.0F, 0.0F, 4.0F, 1.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 11.875F, -13.625F, 0.3491F, 0.0F, 0.0F));

        PartDefinition cube_r104 = chest.addOrReplaceChild("cube_r104", CubeListBuilder.create().texOffs(272, 0).addBox(-4.0F, -1.0F, 0.0F, 4.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, 10.5F, -21.5F, -0.1585F, 0.0735F, 0.4305F));

        PartDefinition cube_r105 = chest.addOrReplaceChild("cube_r105", CubeListBuilder.create().texOffs(98, 160).addBox(-2.0F, -1.0F, 0.0F, 4.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 10.5F, -21.5F, -0.1745F, 0.0F, 0.0F));

        PartDefinition cube_r106 = chest.addOrReplaceChild("cube_r106", CubeListBuilder.create().texOffs(216, 249).addBox(0.0F, 0.0F, 0.0F, 6.0F, 2.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -4.0F, -12.0F, 0.0873F, -0.0175F, 0.1745F));

        PartDefinition cube_r107 = chest.addOrReplaceChild("cube_r107", CubeListBuilder.create().texOffs(252, 124).addBox(0.0F, 0.0F, -10.0F, 6.0F, 2.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -4.0F, -12.0F, 0.258F, -0.045F, 0.1687F));

        PartDefinition cube_r108 = chest.addOrReplaceChild("cube_r108", CubeListBuilder.create().texOffs(70, 296).addBox(-1.0F, -6.0F, 0.0F, 2.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 10.5F, -21.5F, 0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r109 = chest.addOrReplaceChild("cube_r109", CubeListBuilder.create().texOffs(288, 90).addBox(-1.0F, -4.0F, 0.0F, 1.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.5F, 6.0F, -25.0F, 0.0F, 0.3054F, 0.0F));

        PartDefinition cube_r110 = chest.addOrReplaceChild("cube_r110", CubeListBuilder.create().texOffs(242, 296).addBox(3.0F, -6.0F, -1.0F, 3.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.5F, 11.0F, -22.0F, 0.9553F, -0.5236F, -0.6155F));

        PartDefinition cube_r111 = chest.addOrReplaceChild("cube_r111", CubeListBuilder.create().texOffs(58, 296).addBox(0.0F, -6.0F, 0.0F, 4.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.5F, 11.0F, -22.0F, 0.8345F, -0.3035F, -0.3185F));

        PartDefinition left_wing = chest.addOrReplaceChild("left_wing", CubeListBuilder.create(), PartPose.offset(5.35F, -0.5F, -20.225F));

        PartDefinition cube_r112 = left_wing.addOrReplaceChild("cube_r112", CubeListBuilder.create().texOffs(222, 162).addBox(0.0F, -1.0F, 0.0F, 22.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.65F, 0.0F, -0.775F, 0.0F, -0.6981F, 0.0F));

        PartDefinition left_wing_fin = left_wing.addOrReplaceChild("left_wing_fin", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.075F));

        PartDefinition cube_r113 = left_wing_fin.addOrReplaceChild("cube_r113", CubeListBuilder.create().texOffs(144, 48).addBox(-2.0F, 0.0F, 1.0F, 28.0F, 0.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.65F, 0.05F, -0.85F, 0.0F, -0.6981F, 0.0F));

        PartDefinition left_wing_mid = left_wing.addOrReplaceChild("left_wing_mid", CubeListBuilder.create(), PartPose.offset(16.225F, 0.0F, 14.9F));

        PartDefinition cube_r114 = left_wing_mid.addOrReplaceChild("cube_r114", CubeListBuilder.create().texOffs(144, 92).addBox(0.0F, -1.0F, -2.0F, 30.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.0076F, 0.0F, -0.0016F, 0.0F, 0.5672F, 0.0F));

        PartDefinition left_wing_formem = left_wing_mid.addOrReplaceChild("left_wing_formem", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r115 = left_wing_formem.addOrReplaceChild("cube_r115", CubeListBuilder.create().texOffs(126, 98).addBox(-14.9157F, 0.0F, -0.9463F, 40.0F, 0.0F, 23.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.1F, 0.05F, -2.675F, 0.0F, 0.5672F, 0.0F));

        PartDefinition left_wing_tip = left_wing_mid.addOrReplaceChild("left_wing_tip", CubeListBuilder.create(), PartPose.offset(24.325F, 0.0F, -17.8F));

        PartDefinition cube_r116 = left_wing_tip.addOrReplaceChild("cube_r116", CubeListBuilder.create().texOffs(144, 84).addBox(0.0F, -2.0F, -1.0F, 34.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(0.0F, -1.1F, 0.0F, 65.0F, 0.0F, 24.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.0F, 0.975F, 0.0F, 0.0873F, 0.0F));

        PartDefinition cube_r117 = left_wing_tip.addOrReplaceChild("cube_r117", CubeListBuilder.create().texOffs(144, 96).addBox(0.0F, -0.5F, 0.0F, 32.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(33.825F, 0.0F, -2.525F, 0.0F, -0.0873F, 0.0F));

        PartDefinition left_wing_tip2 = left_wing_mid.addOrReplaceChild("left_wing_tip2", CubeListBuilder.create().texOffs(222, 166).addBox(0.1F, -0.5F, 0.075F, 24.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 48).addBox(0.1F, 0.425F, 0.075F, 47.0F, 0.0F, 25.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(24.25F, 0.0F, -17.8F, 0.0F, -0.3927F, 0.0F));

        PartDefinition cube_r118 = left_wing_tip2.addOrReplaceChild("cube_r118", CubeListBuilder.create().texOffs(226, 41).addBox(0.0F, -0.5F, 0.0F, 24.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(24.1F, 0.0F, 0.075F, 0.0F, -0.1745F, 0.0F));

        PartDefinition left_wing_tip3 = left_wing_mid.addOrReplaceChild("left_wing_tip3", CubeListBuilder.create().texOffs(0, 98).addBox(0.8369F, 0.0F, 0.5801F, 39.0F, 0.0F, 24.0F, new CubeDeformation(0.0F))
                .texOffs(230, 210).addBox(0.823F, -0.5F, 0.5762F, 20.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(24.325F, 0.0F, -17.825F, 0.0F, -0.9599F, 0.0F));

        PartDefinition cube_r119 = left_wing_tip3.addOrReplaceChild("cube_r119", CubeListBuilder.create().texOffs(230, 212).addBox(0.0F, -0.5F, 0.0F, 20.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(20.823F, 0.0F, 0.5762F, 0.0F, -0.1745F, 0.0F));

        PartDefinition left_wing_claw = left_wing_mid.addOrReplaceChild("left_wing_claw", CubeListBuilder.create().texOffs(304, 60).addBox(0.0F, -1.0F, -2.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(142, 192).addBox(1.0F, -1.0F, -2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(24.25F, 0.5F, -17.725F, 0.0F, 0.0873F, 0.0F));

        PartDefinition right_wing = chest.addOrReplaceChild("right_wing", CubeListBuilder.create(), PartPose.offset(-5.35F, -0.5F, -20.225F));

        PartDefinition cube_r120 = right_wing.addOrReplaceChild("cube_r120", CubeListBuilder.create().texOffs(228, 16).addBox(-22.0F, -1.0F, 0.0F, 22.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.65F, 0.0F, -0.775F, 0.0F, 0.6981F, 0.0F));

        PartDefinition right_wing_fin = right_wing.addOrReplaceChild("right_wing_fin", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.075F));

        PartDefinition cube_r121 = right_wing_fin.addOrReplaceChild("cube_r121", CubeListBuilder.create().texOffs(144, 66).addBox(-26.0F, 0.0F, 1.0F, 28.0F, 0.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.65F, 0.05F, -0.85F, 0.0F, 0.6981F, 0.0F));

        PartDefinition right_wing_mid = right_wing.addOrReplaceChild("right_wing_mid", CubeListBuilder.create(), PartPose.offset(-16.225F, 0.0F, 14.9F));

        PartDefinition cube_r122 = right_wing_mid.addOrReplaceChild("cube_r122", CubeListBuilder.create().texOffs(178, 44).addBox(-30.0F, -1.0F, -2.0F, 30.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0076F, 0.0F, -0.0016F, 0.0F, -0.5672F, 0.0F));

        PartDefinition right_wing_formem = right_wing_mid.addOrReplaceChild("right_wing_formem", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r123 = right_wing_formem.addOrReplaceChild("cube_r123", CubeListBuilder.create().texOffs(126, 121).addBox(-25.0843F, 0.0F, -0.9463F, 40.0F, 0.0F, 23.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.1F, 0.05F, -2.675F, 0.0F, -0.5672F, 0.0F));

        PartDefinition right_wing_tip = right_wing_mid.addOrReplaceChild("right_wing_tip", CubeListBuilder.create(), PartPose.offset(-24.325F, 0.0F, -17.8F));

        PartDefinition cube_r124 = right_wing_tip.addOrReplaceChild("cube_r124", CubeListBuilder.create().texOffs(144, 88).addBox(-34.0F, -2.0F, -1.0F, 34.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 24).addBox(-65.0F, -1.1F, 0.0F, 65.0F, 0.0F, 24.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.0F, 0.975F, 0.0F, -0.0873F, 0.0F));

        PartDefinition cube_r125 = right_wing_tip.addOrReplaceChild("cube_r125", CubeListBuilder.create().texOffs(142, 194).addBox(-32.0F, -0.5F, 0.0F, 32.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-33.825F, 0.0F, -2.525F, 0.0F, 0.0873F, 0.0F));

        PartDefinition right_wing_tip2 = right_wing_mid.addOrReplaceChild("right_wing_tip2", CubeListBuilder.create().texOffs(230, 206).addBox(-24.1F, -0.5F, 0.075F, 24.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 73).addBox(-47.1F, 0.425F, 0.075F, 47.0F, 0.0F, 25.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-24.25F, 0.0F, -17.8F, 0.0F, 0.3927F, 0.0F));

        PartDefinition cube_r126 = right_wing_tip2.addOrReplaceChild("cube_r126", CubeListBuilder.create().texOffs(230, 208).addBox(-24.0F, -0.5F, 0.0F, 24.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-24.1F, 0.0F, 0.075F, 0.0F, 0.1745F, 0.0F));

        PartDefinition right_wing_tip3 = right_wing_mid.addOrReplaceChild("right_wing_tip3", CubeListBuilder.create().texOffs(0, 122).addBox(-39.8369F, 0.0F, 0.5801F, 39.0F, 0.0F, 24.0F, new CubeDeformation(0.0F))
                .texOffs(230, 214).addBox(-20.823F, -0.5F, 0.5762F, 20.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-24.325F, 0.0F, -17.825F, 0.0F, 0.9599F, 0.0F));

        PartDefinition cube_r127 = right_wing_tip3.addOrReplaceChild("cube_r127", CubeListBuilder.create().texOffs(236, 81).addBox(-20.0F, -0.5F, 0.0F, 20.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-20.823F, 0.0F, 0.5762F, 0.0F, 0.1745F, 0.0F));

        PartDefinition right_wing_claw = right_wing_mid.addOrReplaceChild("right_wing_claw", CubeListBuilder.create().texOffs(62, 304).addBox(-1.0F, -1.0F, -2.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(210, 96).addBox(-2.0F, -1.0F, -2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-24.25F, 0.5F, -17.725F, 0.0F, -0.0873F, 0.0F));

        PartDefinition fin_chest = chest.addOrReplaceChild("fin_chest", CubeListBuilder.create(), PartPose.offset(0.0F, -4.0F, 0.0F));

        PartDefinition cube_r128 = fin_chest.addOrReplaceChild("cube_r128", CubeListBuilder.create().texOffs(160, 239).addBox(0.0F, -3.0F, -10.0F, 0.0F, 3.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.05F, -11.475F, 0.2618F, 0.0F, 0.0F));

        PartDefinition cube_r129 = fin_chest.addOrReplaceChild("cube_r129", CubeListBuilder.create().texOffs(266, 216).addBox(0.0F, -3.0F, -12.0F, 0.0F, 3.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.0F, 0.0F, 0.0873F, 0.0F, 0.0F));

        PartDefinition spike_chest = chest.addOrReplaceChild("spike_chest", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r130 = spike_chest.addOrReplaceChild("cube_r130", CubeListBuilder.create().texOffs(38, 300).addBox(-0.5F, -2.0F, 0.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -4.625F, -4.3F, -0.6981F, 0.0F, 0.0F));

        PartDefinition cube_r131 = spike_chest.addOrReplaceChild("cube_r131", CubeListBuilder.create().texOffs(298, 241).addBox(-0.5F, -2.0F, 0.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -4.25F, -8.3F, -0.6981F, 0.0F, 0.0F));

        PartDefinition cube_r132 = spike_chest.addOrReplaceChild("cube_r132", CubeListBuilder.create().texOffs(298, 14).addBox(-0.5F, -2.0F, 0.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -3.9F, -12.3F, -0.6981F, 0.0F, 0.0F));

        PartDefinition cube_r133 = spike_chest.addOrReplaceChild("cube_r133", CubeListBuilder.create().texOffs(8, 297).addBox(-0.5F, -2.0F, 0.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -2.8F, -16.3F, -0.5236F, 0.0F, 0.0F));

        PartDefinition cube_r134 = spike_chest.addOrReplaceChild("cube_r134", CubeListBuilder.create().texOffs(274, 245).addBox(-0.5F, -2.0F, 0.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.65F, -20.3F, -0.4363F, 0.0F, 0.0F));

        PartDefinition cube_r135 = spike_chest.addOrReplaceChild("cube_r135", CubeListBuilder.create().texOffs(156, 303).addBox(-0.5F, -2.0F, 0.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, -23.3F, -0.4363F, 0.0F, 0.0F));

        PartDefinition neck = chest.addOrReplaceChild("neck", CubeListBuilder.create().texOffs(0, 263).addBox(-3.0F, -2.0F, -7.0F, 6.0F, 4.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(44, 276).addBox(-2.0F, -2.0F, 1.0F, 4.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 4.0F, -25.0F));

        PartDefinition cube_r136 = neck.addOrReplaceChild("cube_r136", CubeListBuilder.create().texOffs(28, 285).addBox(-1.0F, -2.0F, 0.0F, 1.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.25F, 1.0F, -7.0F, 0.0F, -0.0873F, 0.0F));

        PartDefinition cube_r137 = neck.addOrReplaceChild("cube_r137", CubeListBuilder.create().texOffs(134, 274).addBox(-4.0F, -1.0F, -8.0F, 4.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 3.0F, 1.0F, 0.0F, 0.0F, 0.1745F));

        PartDefinition cube_r138 = neck.addOrReplaceChild("cube_r138", CubeListBuilder.create().texOffs(272, 158).addBox(-4.0F, 0.0F, -8.0F, 4.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -3.0F, 1.0F, 0.0F, 0.0F, -0.1745F));

        PartDefinition cube_r139 = neck.addOrReplaceChild("cube_r139", CubeListBuilder.create().texOffs(272, 52).addBox(0.0F, 0.0F, -8.0F, 4.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -3.0F, 1.0F, 0.0F, 0.0F, 0.1745F));

        PartDefinition cube_r140 = neck.addOrReplaceChild("cube_r140", CubeListBuilder.create().texOffs(186, 272).addBox(0.0F, -1.0F, -8.0F, 4.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 3.0F, 1.0F, 0.0F, 0.0F, -0.1745F));

        PartDefinition cube_r141 = neck.addOrReplaceChild("cube_r141", CubeListBuilder.create().texOffs(284, 127).addBox(0.0F, -2.0F, 0.0F, 1.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.25F, 1.0F, -7.0F, 0.0F, 0.0873F, 0.0F));

        PartDefinition cube_r142 = neck.addOrReplaceChild("cube_r142", CubeListBuilder.create().texOffs(294, 178).addBox(-2.0F, 0.0F, 0.0F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -2.75F, -2.0F, 0.2618F, 0.0F, 0.0F));

        PartDefinition fin_neck = neck.addOrReplaceChild("fin_neck", CubeListBuilder.create().texOffs(284, 245).addBox(0.0F, -5.0F, -8.0F, 0.0F, 3.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.0F, 0.0F));

        PartDefinition spike_neck = neck.addOrReplaceChild("spike_neck", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r143 = spike_neck.addOrReplaceChild("cube_r143", CubeListBuilder.create().texOffs(228, 302).addBox(-0.5F, -2.0F, 0.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -2.9F, -1.5F, -0.6981F, 0.0F, 0.0F));

        PartDefinition cube_r144 = spike_neck.addOrReplaceChild("cube_r144", CubeListBuilder.create().texOffs(294, 73).addBox(-0.5F, -2.0F, 0.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -2.9F, -4.5F, -0.7854F, 0.0F, 0.0F));

        PartDefinition head = neck.addOrReplaceChild("head", CubeListBuilder.create().texOffs(22, 276).addBox(-1.5F, 1.075F, -13.0F, 3.0F, 1.0F, 8.0F, new CubeDeformation(-0.1F))
                .texOffs(258, 43).addBox(-1.5F, -5.0F, -6.0F, 3.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -7.0F));

        PartDefinition cube_r145 = head.addOrReplaceChild("cube_r145", CubeListBuilder.create().texOffs(146, 295).addBox(0.0F, 0.0F, 0.0F, 1.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.9F, -0.75F, -12.6F, 0.0F, -0.3491F, 0.0F));

        PartDefinition cube_r146 = head.addOrReplaceChild("cube_r146", CubeListBuilder.create().texOffs(296, 270).addBox(0.0F, 0.0F, 0.0F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.675F, -2.7F, -3.85F, 0.2438F, 0.0591F, 0.2649F));

        PartDefinition cube_r147 = head.addOrReplaceChild("cube_r147", CubeListBuilder.create().texOffs(128, 233).addBox(-2.0F, 0.0F, 0.0F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.675F, -2.7F, -3.85F, 0.2438F, -0.0591F, -0.2649F));

        PartDefinition cube_r148 = head.addOrReplaceChild("cube_r148", CubeListBuilder.create().texOffs(284, 120).addBox(-3.0F, 0.0F, 0.0F, 6.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -4.0F, -2.0F, -0.5236F, 0.0F, 0.0F));

        PartDefinition cube_r149 = head.addOrReplaceChild("cube_r149", CubeListBuilder.create().texOffs(282, 109).addBox(-2.0F, -4.0F, -6.0F, 2.0F, 4.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, 2.0F, 0.0F, 0.3758F, -0.1015F, -0.2247F));

        PartDefinition cube_r150 = head.addOrReplaceChild("cube_r150", CubeListBuilder.create().texOffs(76, 282).addBox(0.0F, -4.0F, -6.0F, 2.0F, 4.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, 2.0F, 0.0F, 0.3758F, 0.1015F, 0.2247F));

        PartDefinition cube_r151 = head.addOrReplaceChild("cube_r151", CubeListBuilder.create().texOffs(286, 83).addBox(-3.0F, 0.0F, 0.0F, 3.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.15F, 1.0F, -12.9F, 0.0F, 0.3491F, 0.0F));

        PartDefinition cube_r152 = head.addOrReplaceChild("cube_r152", CubeListBuilder.create().texOffs(282, 294).addBox(-1.0F, 0.0F, 0.0F, 1.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.9F, -0.75F, -12.6F, 0.0F, 0.3491F, 0.0F));

        PartDefinition cube_r153 = head.addOrReplaceChild("cube_r153", CubeListBuilder.create().texOffs(90, 220).addBox(-1.0F, 0.0F, 0.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, -0.975F, -12.95F, -0.1745F, 0.3927F, 0.0F));

        PartDefinition cube_r154 = head.addOrReplaceChild("cube_r154", CubeListBuilder.create().texOffs(274, 167).addBox(-2.0F, -2.0F, -6.0F, 4.0F, 4.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.0F, 0.0F, 0.2618F, 0.0F, 0.0F));

        PartDefinition cube_r155 = head.addOrReplaceChild("cube_r155", CubeListBuilder.create().texOffs(264, 158).addBox(0.0F, 0.0F, 0.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.7F, -2.55F, -8.275F, 0.4627F, -1.0487F, -0.2864F));

        PartDefinition cube_r156 = head.addOrReplaceChild("cube_r156", CubeListBuilder.create().texOffs(284, 285).addBox(0.0F, 0.0F, 0.0F, 3.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.7F, -1.175F, -8.575F, 0.3511F, -0.1677F, 0.0781F));

        PartDefinition cube_r157 = head.addOrReplaceChild("cube_r157", CubeListBuilder.create().texOffs(268, 285).addBox(-3.0F, 0.0F, 0.0F, 3.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.7F, -1.175F, -8.575F, 0.3511F, 0.1677F, -0.0781F));

        PartDefinition cube_r158 = head.addOrReplaceChild("cube_r158", CubeListBuilder.create().texOffs(268, 294).addBox(-3.0F, 0.0F, 0.0F, 3.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.5F, -4.5F, -6.0F, 0.0F, 0.0F, -0.2618F));

        PartDefinition cube_r159 = head.addOrReplaceChild("cube_r159", CubeListBuilder.create().texOffs(0, 304).addBox(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.725F, -1.225F, -8.775F, 0.095F, -0.4791F, -0.0213F));

        PartDefinition cube_r160 = head.addOrReplaceChild("cube_r160", CubeListBuilder.create().texOffs(150, 303).addBox(-1.0F, 0.0F, 0.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.725F, -1.225F, -8.775F, 0.095F, 0.4791F, 0.0213F));

        PartDefinition cube_r161 = head.addOrReplaceChild("cube_r161", CubeListBuilder.create().texOffs(270, 76).addBox(0.0F, 0.0F, 0.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(274, 202).addBox(6.4F, 0.0F, 0.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.7F, -3.05F, -7.45F, 0.4363F, 0.0F, 0.0F));

        PartDefinition cube_r162 = head.addOrReplaceChild("cube_r162", CubeListBuilder.create().texOffs(194, 190).addBox(0.0F, 0.0F, 0.0F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.35F, -2.8F, -8.8F, 0.5659F, -0.3883F, -0.3504F));

        PartDefinition cube_r163 = head.addOrReplaceChild("cube_r163", CubeListBuilder.create().texOffs(256, 245).addBox(-3.0F, 0.0F, 0.0F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.35F, -2.8F, -8.8F, 0.5659F, 0.3883F, 0.3504F));

        PartDefinition cube_r164 = head.addOrReplaceChild("cube_r164", CubeListBuilder.create().texOffs(208, 92).addBox(-2.0F, 0.0F, 0.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.7F, -2.55F, -8.275F, 0.4627F, 1.0487F, 0.2864F));

        PartDefinition cube_r165 = head.addOrReplaceChild("cube_r165", CubeListBuilder.create().texOffs(294, 192).addBox(0.0F, 0.0F, 0.0F, 3.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.5F, -4.5F, -6.0F, 0.0F, 0.0F, 0.2618F));

        PartDefinition cube_r166 = head.addOrReplaceChild("cube_r166", CubeListBuilder.create().texOffs(292, 26).addBox(-1.5F, 0.0F, -5.0F, 3.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -5.0F, -6.0F, 0.7418F, 0.0F, 0.0F));

        PartDefinition cube_r167 = head.addOrReplaceChild("cube_r167", CubeListBuilder.create().texOffs(294, 184).addBox(-1.5F, 0.0F, 0.0F, 3.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -5.0F, -2.0F, -0.3054F, 0.0F, 0.0F));

        PartDefinition cube_r168 = head.addOrReplaceChild("cube_r168", CubeListBuilder.create().texOffs(296, 163).addBox(0.0F, 0.0F, 0.0F, 2.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.975F, -0.925F, -12.35F, 0.1911F, -0.105F, -0.1524F));

        PartDefinition cube_r169 = head.addOrReplaceChild("cube_r169", CubeListBuilder.create().texOffs(296, 54).addBox(-2.0F, 0.0F, 0.0F, 2.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.975F, -0.925F, -12.35F, 0.1911F, 0.105F, 0.1524F));

        PartDefinition cube_r170 = head.addOrReplaceChild("cube_r170", CubeListBuilder.create().texOffs(234, 263).addBox(0.0F, 0.0F, 0.0F, 3.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.15F, 1.0F, -12.9F, 0.0F, -0.3491F, 0.0F));

        PartDefinition cube_r171 = head.addOrReplaceChild("cube_r171", CubeListBuilder.create().texOffs(90, 216).addBox(0.0F, 0.0F, 0.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, -0.975F, -12.95F, -0.1745F, -0.3927F, 0.0F));

        PartDefinition cube_r172 = head.addOrReplaceChild("cube_r172", CubeListBuilder.create().texOffs(260, 185).addBox(-1.0F, 0.0F, 0.0F, 2.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.0F, -13.0F, 0.2618F, 0.0F, 0.0F));

        PartDefinition cube_r173 = head.addOrReplaceChild("cube_r173", CubeListBuilder.create().texOffs(268, 245).addBox(-1.0F, 0.0F, 0.0F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.0F, -13.0F, -0.2618F, 0.0F, 0.0F));

        PartDefinition fin_head = head.addOrReplaceChild("fin_head", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 1.0F));

        PartDefinition cube_r174 = fin_head.addOrReplaceChild("cube_r174", CubeListBuilder.create().texOffs(296, 294).addBox(0.0F, 0.0F, 0.0F, 0.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.35F, -3.2F, -7.25F, 0.4371F, -0.7575F, -0.0217F));

        PartDefinition cube_r175 = fin_head.addOrReplaceChild("cube_r175", CubeListBuilder.create().texOffs(242, 43).addBox(0.0F, 0.0F, 0.0F, 3.0F, 0.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.35F, -3.975F, -6.075F, 0.5524F, -0.3614F, -0.1572F));

        PartDefinition cube_r176 = fin_head.addOrReplaceChild("cube_r176", CubeListBuilder.create().texOffs(296, 169).addBox(0.0F, 0.0F, 0.0F, 0.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.0F, -1.2F, -5.65F, -0.1737F, -0.7575F, -0.0217F));

        PartDefinition cube_r177 = fin_head.addOrReplaceChild("cube_r177", CubeListBuilder.create().texOffs(232, 296).addBox(0.0F, 0.0F, 0.0F, 0.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, -1.2F, -5.65F, -0.1737F, 0.7575F, 0.0217F));

        PartDefinition cube_r178 = fin_head.addOrReplaceChild("cube_r178", CubeListBuilder.create().texOffs(284, 256).addBox(-3.0F, 0.0F, 0.0F, 3.0F, 0.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.35F, -3.975F, -6.075F, 0.5524F, 0.3614F, 0.1572F));

        PartDefinition cube_r179 = fin_head.addOrReplaceChild("cube_r179", CubeListBuilder.create().texOffs(296, 276).addBox(0.0F, 0.0F, 0.0F, 0.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.35F, -3.2F, -7.25F, 0.4371F, 0.7575F, 0.0217F));

        PartDefinition spike_head = head.addOrReplaceChild("spike_head", CubeListBuilder.create(), PartPose.offset(0.0F, -1.0F, 0.0F));

        PartDefinition cube_r180 = spike_head.addOrReplaceChild("cube_r180", CubeListBuilder.create().texOffs(34, 261).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 4.025F, -2.05F, 0.8727F, 0.0F, 0.0F));

        PartDefinition cube_r181 = spike_head.addOrReplaceChild("cube_r181", CubeListBuilder.create().texOffs(304, 83).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 4.85F, -3.625F, 0.9163F, 0.0F, 0.0F));

        PartDefinition cube_r182 = spike_head.addOrReplaceChild("cube_r182", CubeListBuilder.create().texOffs(292, 155).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 5.35F, -5.825F, 0.9163F, 0.0F, 0.0F));

        PartDefinition cube_r183 = spike_head.addOrReplaceChild("cube_r183", CubeListBuilder.create().texOffs(296, 39).addBox(0.0F, 0.0F, 0.0F, 2.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.0F, -2.55F, -7.2F, 0.5412F, -0.4595F, -0.2605F));

        PartDefinition cube_r184 = spike_head.addOrReplaceChild("cube_r184", CubeListBuilder.create().texOffs(102, 300).addBox(0.0F, -1.0F, 0.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.05F, 1.75F, -6.875F, 0.0F, -0.6545F, 0.0F));

        PartDefinition cube_r185 = spike_head.addOrReplaceChild("cube_r185", CubeListBuilder.create().texOffs(92, 299).addBox(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.3F, 2.75F, -5.875F, -0.2814F, -0.7386F, 0.0305F));

        PartDefinition cube_r186 = spike_head.addOrReplaceChild("cube_r186", CubeListBuilder.create().texOffs(298, 231).addBox(0.0F, -1.0F, 0.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.05F, 0.75F, -6.375F, 0.2464F, -0.57F, 0.0268F));

        PartDefinition cube_r187 = spike_head.addOrReplaceChild("cube_r187", CubeListBuilder.create().texOffs(296, 0).addBox(0.0F, 0.0F, 0.0F, 2.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.375F, -3.175F, -2.975F, 0.616F, -0.2921F, -0.1106F));

        PartDefinition cube_r188 = spike_head.addOrReplaceChild("cube_r188", CubeListBuilder.create().texOffs(218, 295).addBox(0.0484F, 0.0843F, 0.126F, 2.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.4404F, -4.4665F, -3.66F, 0.5932F, -0.3082F, -0.1665F));

        PartDefinition cube_r189 = spike_head.addOrReplaceChild("cube_r189", CubeListBuilder.create().texOffs(252, 295).addBox(-2.0484F, 0.0843F, 0.126F, 2.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.4404F, -4.4665F, -3.66F, 0.5932F, 0.3082F, 0.1665F));

        PartDefinition cube_r190 = spike_head.addOrReplaceChild("cube_r190", CubeListBuilder.create().texOffs(14, 296).addBox(-2.0F, 0.0F, 0.0F, 2.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.375F, -3.175F, -2.975F, 0.616F, 0.2921F, 0.1106F));

        PartDefinition cube_r191 = spike_head.addOrReplaceChild("cube_r191", CubeListBuilder.create().texOffs(204, 295).addBox(-2.0F, 0.0F, 0.0F, 2.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, -2.55F, -7.2F, 0.5412F, 0.4595F, 0.2605F));

        PartDefinition cube_r192 = spike_head.addOrReplaceChild("cube_r192", CubeListBuilder.create().texOffs(112, 287).addBox(-0.5F, -2.0F, 0.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.55F, -10.125F, -0.4363F, 0.0F, 0.0F));

        PartDefinition cube_r193 = spike_head.addOrReplaceChild("cube_r193", CubeListBuilder.create().texOffs(298, 236).addBox(-1.0F, -1.0F, 0.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.05F, 0.75F, -6.375F, 0.2464F, 0.57F, -0.0268F));

        PartDefinition cube_r194 = spike_head.addOrReplaceChild("cube_r194", CubeListBuilder.create().texOffs(28, 300).addBox(-1.0F, 0.0F, 0.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.3F, 2.75F, -5.875F, -0.2814F, 0.7386F, -0.0305F));

        PartDefinition cube_r195 = spike_head.addOrReplaceChild("cube_r195", CubeListBuilder.create().texOffs(298, 78).addBox(-1.0F, -1.0F, 0.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.05F, 1.75F, -6.875F, 0.0F, 0.6545F, 0.0F));

        PartDefinition cube_r196 = spike_head.addOrReplaceChild("cube_r196", CubeListBuilder.create().texOffs(286, 213).addBox(-0.5F, -2.0F, 0.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.05F, -12.875F, -0.5672F, 0.0F, 0.0F));

        PartDefinition jaw = head.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(292, 136).addBox(-1.5F, 0.525F, -3.475F, 3.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 1.95F, -6.775F));

        PartDefinition cube_r197 = jaw.addOrReplaceChild("cube_r197", CubeListBuilder.create().texOffs(300, 113).addBox(0.0F, -1.0F, 0.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.75F, 2.275F, -2.475F, 0.0789F, -0.1624F, -0.3084F));

        PartDefinition cube_r198 = jaw.addOrReplaceChild("cube_r198", CubeListBuilder.create().texOffs(132, 295).addBox(0.0F, -2.0F, 0.0F, 1.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.45F, 2.025F, -5.2F, 0.0692F, -0.2527F, -0.2706F));

        PartDefinition cube_r199 = jaw.addOrReplaceChild("cube_r199", CubeListBuilder.create().texOffs(280, 245).addBox(0.0F, -2.0F, 0.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.35F, 1.95F, -5.475F, 0.381F, 0.0976F, -0.2386F));

        PartDefinition cube_r200 = jaw.addOrReplaceChild("cube_r200", CubeListBuilder.create().texOffs(298, 73).addBox(-1.0F, -2.0F, 0.0F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.825F, -6.1F, -0.2618F, 0.0F, 0.0F));

        PartDefinition cube_r201 = jaw.addOrReplaceChild("cube_r201", CubeListBuilder.create().texOffs(304, 14).addBox(-1.0F, 0.0F, 0.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.05F, -6.775F, 0.3491F, 0.0F, 0.0F));

        PartDefinition cube_r202 = jaw.addOrReplaceChild("cube_r202", CubeListBuilder.create().texOffs(284, 94).addBox(-1.0F, -2.0F, 0.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.35F, 1.95F, -5.475F, 0.381F, -0.0976F, 0.2386F));

        PartDefinition cube_r203 = jaw.addOrReplaceChild("cube_r203", CubeListBuilder.create().texOffs(44, 295).addBox(-1.0F, -2.0F, 0.0F, 1.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.45F, 2.025F, -5.2F, 0.0692F, 0.2527F, 0.2706F));

        PartDefinition cube_r204 = jaw.addOrReplaceChild("cube_r204", CubeListBuilder.create().texOffs(300, 108).addBox(-1.0F, -1.0F, 0.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.75F, 2.275F, -2.475F, 0.0789F, 0.1624F, 0.3084F));

        PartDefinition spike_jaw = jaw.addOrReplaceChild("spike_jaw", CubeListBuilder.create(), PartPose.offset(0.0F, 0.05F, 0.775F));

        PartDefinition cube_r205 = spike_jaw.addOrReplaceChild("cube_r205", CubeListBuilder.create().texOffs(290, 61).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.375F, -1.825F, 0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r206 = spike_jaw.addOrReplaceChild("cube_r206", CubeListBuilder.create().texOffs(304, 86).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.1F, -3.425F, 0.6109F, 0.0F, 0.0F));

        PartDefinition cube_r207 = spike_jaw.addOrReplaceChild("cube_r207", CubeListBuilder.create().texOffs(304, 69).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.85F, -5.125F, 0.5236F, 0.0F, 0.0F));

        PartDefinition fin_jaw = jaw.addOrReplaceChild("fin_jaw", CubeListBuilder.create(), PartPose.offset(0.0F, 0.05F, -0.225F));

        PartDefinition cube_r208 = fin_jaw.addOrReplaceChild("cube_r208", CubeListBuilder.create().texOffs(252, 284).addBox(0.0F, 0.0F, 0.0F, 0.0F, 3.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.0F, -5.0F, 0.0873F, 0.0F, 0.0F));

        PartDefinition saddle = chest.addOrReplaceChild("saddle", CubeListBuilder.create().texOffs(216, 234).addBox(-4.0F, -2.0F, -15.0F, 8.0F, 3.0F, 12.0F, new CubeDeformation(0.0F))
                .texOffs(38, 304).addBox(-3.0F, -3.0F, -14.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(56, 304).addBox(2.0F, -3.0F, -14.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -3.0F, 0.0F));

        PartDefinition cube_r209 = saddle.addOrReplaceChild("cube_r209", CubeListBuilder.create().texOffs(292, 18).addBox(0.0F, -2.0F, 0.0F, 2.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.95F, 2.8F, -19.7F, 0.3491F, -0.3491F, -0.0873F));

        PartDefinition cube_r210 = saddle.addOrReplaceChild("cube_r210", CubeListBuilder.create().texOffs(288, 100).addBox(-2.0F, -2.0F, 0.0F, 2.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.95F, 2.8F, -19.7F, 0.3491F, 0.3491F, 0.0873F));

        PartDefinition cube_r211 = saddle.addOrReplaceChild("cube_r211", CubeListBuilder.create().texOffs(256, 94).addBox(-3.0F, -1.0F, -2.0F, 6.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.575F, -3.075F, -0.1745F, 0.0F, 0.0F));

        PartDefinition saddle_lock = saddle.addOrReplaceChild("saddle_lock", CubeListBuilder.create(), PartPose.offset(0.0F, -2.0F, -8.0F));

        PartDefinition left_front_leg = chest.addOrReplaceChild("left_front_leg", CubeListBuilder.create(), PartPose.offset(8.75F, 5.0F, -17.5F));

        PartDefinition cube_r212 = left_front_leg.addOrReplaceChild("cube_r212", CubeListBuilder.create().texOffs(290, 225).addBox(-1.0F, -3.0F, 1.0F, 3.0F, 1.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(0, 275).addBox(-1.5F, -2.0F, 0.0F, 4.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, 1.85F, -1.75F, 0.5672F, 0.0F, 0.0F));

        PartDefinition cube_r213 = left_front_leg.addOrReplaceChild("cube_r213", CubeListBuilder.create().texOffs(160, 295).addBox(-2.0F, -7.9993F, -0.9962F, 2.0F, 8.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.15F, 6.3F, 5.425F, 0.6939F, 0.084F, -0.1005F));

        PartDefinition cube_r214 = left_front_leg.addOrReplaceChild("cube_r214", CubeListBuilder.create().texOffs(286, 231).addBox(-2.0F, -8.0F, -2.0F, 4.0F, 10.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.025F, 5.25F, 7.225F, 0.6109F, 0.0F, 0.0F));

        PartDefinition cube_r215 = left_front_leg.addOrReplaceChild("cube_r215", CubeListBuilder.create().texOffs(96, 264).addBox(-2.0F, -8.0F, 0.0F, 4.0F, 8.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.05F, 7.0F, 4.375F, 0.8727F, 0.0F, 0.0F));

        PartDefinition cube_r216 = left_front_leg.addOrReplaceChild("cube_r216", CubeListBuilder.create().texOffs(238, 283).addBox(-2.0F, -8.0F, 0.0F, 4.0F, 10.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.05F, 7.0F, 4.25F, 0.6981F, 0.0F, 0.0F));

        PartDefinition left_front_mid_leg = left_front_leg.addOrReplaceChild("left_front_mid_leg", CubeListBuilder.create().texOffs(116, 276).addBox(-2.1F, 0.0F, -2.15F, 4.0F, 10.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 7.0F, 6.5F));

        PartDefinition cube_r217 = left_front_mid_leg.addOrReplaceChild("cube_r217", CubeListBuilder.create().texOffs(122, 160).addBox(-0.5F, -5.0F, -1.0F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.0F, 1.75F, -0.5236F, 0.0F, 0.0F));

        PartDefinition cube_r218 = left_front_mid_leg.addOrReplaceChild("cube_r218", CubeListBuilder.create().texOffs(44, 202).addBox(-0.5F, -4.0F, -1.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 4.0F, 1.75F, -0.6109F, 0.0F, 0.0F));

        PartDefinition cube_r219 = left_front_mid_leg.addOrReplaceChild("cube_r219", CubeListBuilder.create().texOffs(122, 166).addBox(-0.5F, -3.0F, -1.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 6.0F, 1.75F, -0.6981F, 0.0F, 0.0F));

        PartDefinition cube_r220 = left_front_mid_leg.addOrReplaceChild("cube_r220", CubeListBuilder.create().texOffs(272, 9).addBox(-0.5F, -2.0F, -1.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 8.0F, 1.75F, -0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r221 = left_front_mid_leg.addOrReplaceChild("cube_r221", CubeListBuilder.create().texOffs(280, 271).addBox(-2.0F, 0.0F, 0.0F, 4.0F, 10.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, -2.25F, -0.1745F, 0.0F, 0.0F));

        PartDefinition left_front_foot = left_front_mid_leg.addOrReplaceChild("left_front_foot", CubeListBuilder.create().texOffs(110, 252).addBox(-2.1F, 0.0F, -2.25F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(272, 94).addBox(-0.6F, 1.0F, -4.25F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(272, 231).addBox(-0.6F, 1.0F, 1.75F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 10.0F, -1.0F));

        PartDefinition cube_r222 = left_front_foot.addOrReplaceChild("cube_r222", CubeListBuilder.create().texOffs(294, 302).addBox(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.6F, 0.0F, 1.75F, -0.5236F, 0.0F, 0.0F));

        PartDefinition cube_r223 = left_front_foot.addOrReplaceChild("cube_r223", CubeListBuilder.create().texOffs(282, 302).addBox(0.0F, 0.0F, -2.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.6F, 0.0F, -2.25F, 0.5236F, 0.0F, 0.0F));

        PartDefinition cube_r224 = left_front_foot.addOrReplaceChild("cube_r224", CubeListBuilder.create().texOffs(258, 302).addBox(-1.0F, 0.0F, -2.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.1F, 0.0F, -2.25F, 0.5236F, 0.2618F, 0.0F));

        PartDefinition cube_r225 = left_front_foot.addOrReplaceChild("cube_r225", CubeListBuilder.create().texOffs(272, 61).addBox(-1.0F, 0.0F, -2.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.1F, 1.0F, -2.25F, 0.0F, 0.2618F, 0.0F));

        PartDefinition cube_r226 = left_front_foot.addOrReplaceChild("cube_r226", CubeListBuilder.create().texOffs(270, 302).addBox(0.0F, 0.0F, -2.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.9F, 0.0F, -2.25F, 0.5236F, -0.2618F, 0.0F));

        PartDefinition cube_r227 = left_front_foot.addOrReplaceChild("cube_r227", CubeListBuilder.create().texOffs(266, 231).addBox(0.0F, 0.0F, -2.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.9F, 1.0F, -2.25F, 0.0F, -0.2618F, 0.0F));

        PartDefinition right_front_leg = chest.addOrReplaceChild("right_front_leg", CubeListBuilder.create(), PartPose.offset(-8.75F, 5.0F, -17.5F));

        PartDefinition cube_r228 = right_front_leg.addOrReplaceChild("cube_r228", CubeListBuilder.create().texOffs(294, 33).addBox(-2.0F, -3.0F, 1.0F, 3.0F, 1.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(276, 9).addBox(-2.5F, -2.0F, 0.0F, 4.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, 1.85F, -1.75F, 0.5672F, 0.0F, 0.0F));

        PartDefinition cube_r229 = right_front_leg.addOrReplaceChild("cube_r229", CubeListBuilder.create().texOffs(170, 295).addBox(0.0F, -7.9993F, -0.9962F, 2.0F, 8.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.15F, 6.3F, 5.425F, 0.6939F, -0.084F, 0.1005F));

        PartDefinition cube_r230 = right_front_leg.addOrReplaceChild("cube_r230", CubeListBuilder.create().texOffs(94, 287).addBox(-2.0F, -8.0F, -2.0F, 4.0F, 10.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.025F, 5.25F, 7.225F, 0.6109F, 0.0F, 0.0F));

        PartDefinition cube_r231 = right_front_leg.addOrReplaceChild("cube_r231", CubeListBuilder.create().texOffs(14, 285).addBox(-2.0F, -8.0F, 0.0F, 4.0F, 8.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.05F, 7.0F, 4.375F, 0.8727F, 0.0F, 0.0F));

        PartDefinition cube_r232 = right_front_leg.addOrReplaceChild("cube_r232", CubeListBuilder.create().texOffs(0, 284).addBox(-2.0F, -8.0F, 0.0F, 4.0F, 10.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.05F, 7.0F, 4.25F, 0.6981F, 0.0F, 0.0F));

        PartDefinition right_front_mid_leg = right_front_leg.addOrReplaceChild("right_front_mid_leg", CubeListBuilder.create().texOffs(44, 281).addBox(-1.9F, 0.0F, -2.15F, 4.0F, 10.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 7.0F, 6.5F));

        PartDefinition cube_r233 = right_front_mid_leg.addOrReplaceChild("cube_r233", CubeListBuilder.create().texOffs(44, 196).addBox(-0.5F, -5.0F, -1.0F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.0F, 1.75F, -0.5236F, 0.0F, 0.0F));

        PartDefinition cube_r234 = right_front_mid_leg.addOrReplaceChild("cube_r234", CubeListBuilder.create().texOffs(44, 207).addBox(-0.5F, -4.0F, -1.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 4.0F, 1.75F, -0.6109F, 0.0F, 0.0F));

        PartDefinition cube_r235 = right_front_mid_leg.addOrReplaceChild("cube_r235", CubeListBuilder.create().texOffs(44, 212).addBox(-0.5F, -3.0F, -1.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 6.0F, 1.75F, -0.6981F, 0.0F, 0.0F));

        PartDefinition cube_r236 = right_front_mid_leg.addOrReplaceChild("cube_r236", CubeListBuilder.create().texOffs(272, 12).addBox(-0.5F, -2.0F, -1.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 8.0F, 1.75F, -0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r237 = right_front_mid_leg.addOrReplaceChild("cube_r237", CubeListBuilder.create().texOffs(158, 281).addBox(-2.0F, 0.0F, 0.0F, 4.0F, 10.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, -2.25F, -0.1745F, 0.0F, 0.0F));

        PartDefinition right_front_foot = right_front_mid_leg.addOrReplaceChild("right_front_foot", CubeListBuilder.create().texOffs(292, 143).addBox(-1.9F, 0.0F, -2.25F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(288, 302).addBox(-0.4F, 1.0F, -4.25F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(300, 302).addBox(-0.4F, 1.0F, 1.75F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 10.0F, -1.0F));

        PartDefinition cube_r238 = right_front_foot.addOrReplaceChild("cube_r238", CubeListBuilder.create().texOffs(276, 302).addBox(-1.0F, 0.0F, -2.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.9F, 1.0F, -2.25F, 0.0F, 0.2618F, 0.0F));

        PartDefinition cube_r239 = right_front_foot.addOrReplaceChild("cube_r239", CubeListBuilder.create().texOffs(264, 302).addBox(0.0F, 0.0F, -2.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.1F, 1.0F, -2.25F, 0.0F, -0.2618F, 0.0F));

        PartDefinition cube_r240 = right_front_foot.addOrReplaceChild("cube_r240", CubeListBuilder.create().texOffs(280, 213).addBox(-1.0F, 0.0F, 0.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.6F, 0.0F, 1.75F, -0.5236F, 0.0F, 0.0F));

        PartDefinition cube_r241 = right_front_foot.addOrReplaceChild("cube_r241", CubeListBuilder.create().texOffs(278, 231).addBox(-1.0F, 0.0F, -2.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.6F, 0.0F, -2.25F, 0.5236F, 0.0F, 0.0F));

        PartDefinition cube_r242 = right_front_foot.addOrReplaceChild("cube_r242", CubeListBuilder.create().texOffs(278, 94).addBox(0.0F, 0.0F, -2.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.1F, 0.0F, -2.25F, 0.5236F, -0.2618F, 0.0F));

        PartDefinition cube_r243 = right_front_foot.addOrReplaceChild("cube_r243", CubeListBuilder.create().texOffs(278, 61).addBox(-1.0F, 0.0F, -2.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.9F, 0.0F, -2.25F, 0.5236F, 0.2618F, 0.0F));

        return LayerDefinition.create(meshdefinition, 512, 512);
    }

    @Override
    public void setupAnim(@NotNull ElderWingDragonRenderState state) {
        super.setupAnim(state);

        this.root.getAllParts().forEach(ModelPart::resetPose);
        float ageInTicks = state.ageInTicks;

        CosmeticStyle style = state.cosmeticStyle != null ? state.cosmeticStyle : CosmeticStyle.NONE;
        boolean showFins = style == CosmeticStyle.FINS;
        boolean showSpikes = style == CosmeticStyle.SPIKES;

        for (ModelPart fin : this.finParts) {
            fin.visible = showFins;
        }
        for (ModelPart spike : this.spikeParts) {
            spike.visible = showSpikes;
        }

        // Wings unlocked at level 20
        boolean showWings = state.hasWingsUnlocked;
        this.left_wing.visible = showWings;
        this.right_wing.visible = showWings;

        // Saddle visibility
        this.saddle.visible = state.isSaddled;
        this.saddle_lock.visible = state.isSaddled;

        this.root.xScale = 1.0F;
        this.root.yScale = 1.0F;
        this.root.zScale = 1.0F;

        if (state.isBaby) {
            float babyScale = 0.5F;
            this.root.xScale = babyScale;
            this.root.yScale = babyScale;
            this.root.zScale = babyScale;
            this.root.y = 24.0F;
        } else {
            this.root.y = 24.0F;
        }

        if (state.isSleeping) {
            this.sleepAnimation.apply(state.sleepingAnimationState, ageInTicks);
        } else if (state.isSitting) {
            this.sitAnimation.apply(state.sitAnimationState, ageInTicks);
        } else if (state.isFlying) {
            if (state.walkAnimationSpeed > 0.35F) {
                this.flyAnimation.apply(state.flyAnimationState, ageInTicks);
            } else if (state.walkAnimationSpeed > 0.05F) {
                this.glideAnimation.apply(state.glideAnimationState, ageInTicks);
            } else {
                this.hoverAnimation.apply(state.hoverAnimationState, ageInTicks);
            }
        } else {
        if (state.walkAnimationSpeed > 0.65F) {
            this.runAnimation.applyWalk(state.walkAnimationPos, state.walkAnimationSpeed, 1.0F, 2.5F);
        } else if (state.walkAnimationSpeed > 0.05F) {
            this.walkAnimation.applyWalk(state.walkAnimationPos, state.walkAnimationSpeed, 1.0F, 2.5F);
        } else {
            this.idleAnimation.apply(state.idleAnimationState, ageInTicks);
        }
    }

        if (state.breathAnimationState.isStarted()) {
            this.breathAnimation.apply(state.breathAnimationState, ageInTicks);
        }
        if (state.breathAttackAnimationState.isStarted()) {
            this.breathAttackAnimation.apply(state.breathAttackAnimationState, ageInTicks);
        }

        // Look-at tracking distributed across neck and its children (head and jaw)
        this.applyNeckLookAt(state);
    }

    private void applyNeckLookAt(@NotNull ElderWingDragonRenderState state) {
        if (state.isSleeping || state.isRidden) {
            return;
        }

        float lookStrength = state.isSitting ? 0.5F : 1.0F;
        float yawRad = state.yRot * ((float) Math.PI / 180.0F) * lookStrength;
        float pitchRad = state.xRot * ((float) Math.PI / 180.0F) * lookStrength;

        // Clamp so neck/head/jaw don't over-rotate past natural bounds
        float maxYaw = 0.75F;
        float maxPitch = 0.55F;
        float clampedYaw = net.minecraft.util.Mth.clamp(yawRad, -maxYaw, maxYaw);
        float clampedPitch = net.minecraft.util.Mth.clamp(pitchRad, -maxPitch, maxPitch);

        // Split rotation between neck and head so the curve looks natural;
        // jaw inherits head's local space and adds a subtle pitch follow
        this.neck.yRot += clampedYaw * 0.45F;
        this.neck.xRot += clampedPitch * 0.45F;

        this.head.yRot += clampedYaw * 0.55F;
        this.head.xRot += clampedPitch * 0.55F;

        this.jaw.xRot += clampedPitch * 0.10F;
    }

    public ModelPart getRoot() {
        return root;
    }

}