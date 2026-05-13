package com.chris.vanilla_expansion.entity.client.model.dragon;

import com.chris.vanilla_expansion.VanillaExpansion;
import com.chris.vanilla_expansion.entity.client.animation.EnergyDragonAnimations;
import com.chris.vanilla_expansion.entity.client.animation.FireDragonAnimations;
import com.chris.vanilla_expansion.entity.client.render.energy_dragon.EnergyDragonRenderState;
import com.chris.vanilla_expansion.entity.client.render.fire_dragon.FireDragonRenderState;
import net.minecraft.client.animation.KeyframeAnimation;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.NotNull;

public class FireDragonModel extends EntityModel<@NotNull FireDragonRenderState> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(Identifier.fromNamespaceAndPath(VanillaExpansion.MOD_ID, "firedragon"), "main");
    private final ModelPart root;

    private final KeyframeAnimation idleAnimation;
    private final KeyframeAnimation walkAnimation;
    private final KeyframeAnimation flyAnimation;
    private final KeyframeAnimation hoverAnimation;
    private final KeyframeAnimation sleepAnimation;
    private final KeyframeAnimation sitAnimation;
    private final KeyframeAnimation meleeAnimation;
    private final KeyframeAnimation fireAnimation;
    public FireDragonModel(ModelPart root) {
        super(root);
        this.root = root.getChild("root");

        this.idleAnimation = FireDragonAnimations.IDLE.bake(root);
        this.walkAnimation = FireDragonAnimations.WALK.bake(root);
        this.flyAnimation = FireDragonAnimations.FLY.bake(root);
        this.hoverAnimation = FireDragonAnimations.HOVER.bake(root);
        this.sleepAnimation = FireDragonAnimations.SLEEPING.bake(root);
        this.sitAnimation = FireDragonAnimations.SIT.bake(root);
        this.fireAnimation = FireDragonAnimations.FIRE.bake(root);
        this.meleeAnimation = FireDragonAnimations.MELEE.bake(root);
    }

    public static LayerDefinition getTextureModelData() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition torso = root.addOrReplaceChild("torso", CubeListBuilder.create().texOffs(88, 200).addBox(-6.0F, 1.0F, -16.0F, 12.0F, 4.0F, 9.0F, new CubeDeformation(0.0F))
                .texOffs(160, 200).addBox(-3.0F, 5.0F, -15.0F, 6.0F, 1.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(200, 39).addBox(-7.0F, -7.0F, -16.0F, 14.0F, 8.0F, 9.0F, new CubeDeformation(0.0F))
                .texOffs(200, 0).addBox(-7.0F, -8.1F, -29.0F, 14.0F, 2.0F, 14.0F, new CubeDeformation(0.0F))
                .texOffs(34, 235).addBox(-5.0F, -7.1F, -33.0F, 10.0F, 8.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(26, 248).addBox(-4.0F, 0.9F, -33.0F, 8.0F, 4.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(64, 235).addBox(-2.0F, 4.9F, -33.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(192, 76).addBox(-6.0F, -6.0F, -7.0F, 12.0F, 11.0F, 14.0F, new CubeDeformation(0.1F))
                .texOffs(130, 200).addBox(0.7274F, -8.0706F, -28.9F, 7.0F, 14.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(240, 246).addBox(5.7274F, -8.0706F, -20.9F, 2.0F, 13.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(142, 238).addBox(-7.7274F, -8.0706F, -20.9F, 2.0F, 13.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(160, 206).addBox(-7.7274F, -8.0706F, -28.9F, 7.0F, 14.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -23.0F, 20.0F));

        PartDefinition cube_r1 = torso.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(134, 257).addBox(-3.0F, -6.0F, 0.0F, 3.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.25F, 3.0F, -28.0F, 0.0F, -0.0873F, -0.1745F));

        PartDefinition cube_r2 = torso.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(20, 257).addBox(0.0F, -6.0F, 0.0F, 3.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.25F, 3.0F, -28.0F, 0.0F, 0.0873F, 0.1745F));

        PartDefinition cube_r3 = torso.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(190, 214).addBox(-6.0F, -3.0F, 0.0F, 12.0F, 3.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 7.7362F, -0.4825F, 0.3491F, 0.0F, 0.0F));

        PartDefinition cube_r4 = torso.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(88, 213).addBox(-6.0F, -3.0F, 0.0F, 12.0F, 3.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 5.0F, -8.0F, -0.3491F, 0.0F, 0.0F));

        PartDefinition cube_r5 = torso.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(232, 152).addBox(-8.0F, -4.0F, 0.0F, 8.0F, 4.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 8.0F, -29.0F, 0.0F, 0.0F, 0.2618F));

        PartDefinition cube_r6 = torso.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(232, 140).addBox(0.0F, -4.0F, 0.0F, 8.0F, 4.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 8.0F, -29.0F, 0.0F, 0.0F, -0.2618F));

        PartDefinition cube_r7 = torso.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(244, 226).addBox(-8.0F, -2.0F, 0.0F, 8.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 7.0F, -21.0F, 0.0F, 0.0F, 0.2618F));

        PartDefinition cube_r8 = torso.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(244, 218).addBox(0.0F, -2.0F, 0.0F, 8.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 7.0F, -21.0F, 0.0F, 0.0F, -0.2618F));

        PartDefinition neck = torso.addOrReplaceChild("neck", CubeListBuilder.create(), PartPose.offset(0.0F, -2.0F, -33.0F));

        PartDefinition cube_r9 = neck.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(244, 88).addBox(-3.5F, -4.0F, -5.0F, 7.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 3.0F, -0.4F, -0.2618F, 0.0F, 0.0F));

        PartDefinition cube_r10 = neck.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(244, 76).addBox(-4.0F, -5.9052F, -6.1705F, 8.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.0F, 1.0F, -0.0873F, 0.0F, 0.0F));

        PartDefinition neckMid = neck.addOrReplaceChild("neckMid", CubeListBuilder.create().texOffs(244, 234).addBox(-3.5F, -3.9052F, -4.9205F, 7.0F, 7.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(260, 174).addBox(-3.0F, 2.5948F, -4.9205F, 6.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.0F, -4.5F));

        PartDefinition cube_r11 = neckMid.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(254, 100).addBox(-4.0F, -6.0F, -6.0F, 5.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.75F, 1.5F, 0.0F, -0.0873F, 0.0F));

        PartDefinition cube_r12 = neckMid.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(112, 252).addBox(-1.0F, -6.0F, -6.0F, 5.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.75F, 1.5F, 0.0F, 0.0873F, 0.0F));

        PartDefinition neck_end = neckMid.addOrReplaceChild("neck_end", CubeListBuilder.create().texOffs(216, 245).addBox(-3.5F, -3.4052F, -4.9205F, 7.0F, 7.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.0F, -5.0F));

        PartDefinition cube_r13 = neck_end.addOrReplaceChild("cube_r13", CubeListBuilder.create().texOffs(90, 252).addBox(-4.0F, -6.0F, -6.0F, 5.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 3.25F, 1.5F, 0.0F, -0.0873F, 0.0F));

        PartDefinition cube_r14 = neck_end.addOrReplaceChild("cube_r14", CubeListBuilder.create().texOffs(260, 181).addBox(-3.0F, -1.9052F, -5.1705F, 6.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 5.0F, 0.25F, -0.1745F, 0.0F, 0.0F));

        PartDefinition cube_r15 = neck_end.addOrReplaceChild("cube_r15", CubeListBuilder.create().texOffs(178, 251).addBox(-1.0F, -6.0F, -6.0F, 5.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 3.25F, 1.5F, 0.0F, 0.0873F, 0.0F));

        PartDefinition head = neck_end.addOrReplaceChild("head", CubeListBuilder.create().texOffs(114, 238).addBox(-4.0F, -4.0F, -5.0F, 8.0F, 8.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(256, 9).addBox(-3.0F, 4.0F, -6.0F, 6.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(76, 264).addBox(-3.0F, -3.0F, -7.0F, 6.0F, 7.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(220, 257).addBox(-2.0F, -2.0F, -13.0F, 4.0F, 4.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(184, 234).addBox(1.5F, -1.9F, -12.5F, 1.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(184, 228).addBox(-2.5F, -1.9F, -12.5F, 1.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -5.0F));

        PartDefinition cube_r16 = head.addOrReplaceChild("cube_r16", CubeListBuilder.create().texOffs(170, 263).addBox(0.0F, -2.0F, 0.0F, 1.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.5F, 1.0F, -2.0F, 0.0F, -0.4363F, 0.0F));

        PartDefinition cube_r17 = head.addOrReplaceChild("cube_r17", CubeListBuilder.create().texOffs(256, 256).addBox(0.0F, -2.0F, 0.0F, 3.0F, 2.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(246, 16).addBox(0.25F, -1.5F, 4.0F, 2.0F, 2.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.5F, -2.0F, -8.0F, 0.1745F, -0.3491F, 0.0F));

        PartDefinition cube_r18 = head.addOrReplaceChild("cube_r18", CubeListBuilder.create().texOffs(34, 224).addBox(0.0F, -2.0F, 0.0F, 1.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.5F, 4.0F, -2.0F, -0.3491F, -0.4363F, 0.0F));

        PartDefinition cube_r19 = head.addOrReplaceChild("cube_r19", CubeListBuilder.create().texOffs(246, 28).addBox(-2.25F, -1.5F, 4.0F, 2.0F, 2.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(256, 246).addBox(-3.0F, -2.0F, 0.0F, 3.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.5F, -2.0F, -8.0F, 0.1745F, 0.3491F, 0.0F));

        PartDefinition cube_r20 = head.addOrReplaceChild("cube_r20", CubeListBuilder.create().texOffs(114, 224).addBox(-1.0F, -2.0F, 0.0F, 1.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.5F, 4.0F, -2.0F, -0.3491F, 0.4363F, 0.0F));

        PartDefinition cube_r21 = head.addOrReplaceChild("cube_r21", CubeListBuilder.create().texOffs(152, 262).addBox(-1.0F, -2.0F, 0.0F, 1.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.5F, 1.0F, -2.0F, 0.0F, 0.4363F, 0.0F));

        PartDefinition cube_r22 = head.addOrReplaceChild("cube_r22", CubeListBuilder.create().texOffs(264, 140).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -4.0F, -6.5F, 0.4363F, 0.0F, 0.0F));

        PartDefinition cube_r23 = head.addOrReplaceChild("cube_r23", CubeListBuilder.create().texOffs(260, 188).addBox(-1.0F, 0.0F, 0.0F, 2.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -2.5F, -9.5F, 0.1745F, 0.0F, 0.0F));

        PartDefinition cube_r24 = head.addOrReplaceChild("cube_r24", CubeListBuilder.create().texOffs(34, 232).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -2.5F, -12.5F, 0.2618F, 0.0F, 0.0F));

        PartDefinition jaw = head.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(262, 112).addBox(-2.0F, 0.0F, -6.0F, 4.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(82, 220).addBox(1.5F, 0.1F, -5.5F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(152, 257).addBox(-2.5F, 0.1F, -5.5F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.0F, -7.0F));

        PartDefinition tail = torso.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(64, 240).addBox(0.0F, -7.5F, 0.0F, 0.0F, 3.0F, 13.0F, new CubeDeformation(0.0F))
                .texOffs(200, 16).addBox(-4.5F, -4.5F, -1.0F, 9.0F, 9.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.5F, 7.0F));

        PartDefinition tail1 = tail.addOrReplaceChild("tail1", CubeListBuilder.create().texOffs(0, 200).addBox(-4.0F, -4.0F, -1.0F, 8.0F, 8.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(190, 225).addBox(0.0F, -7.0F, 0.0F, 0.0F, 3.0F, 15.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 13.0F));

        PartDefinition tailMid = tail1.addOrReplaceChild("tailMid", CubeListBuilder.create().texOffs(48, 200).addBox(-3.0F, -3.0F, -1.0F, 6.0F, 6.0F, 14.0F, new CubeDeformation(0.0F))
                .texOffs(0, 239).addBox(0.0F, -6.0F, 0.0F, 0.0F, 3.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 15.0F));

        PartDefinition tailMid1 = tailMid.addOrReplaceChild("tailMid1", CubeListBuilder.create().texOffs(128, 222).addBox(-2.0F, -2.0F, -1.0F, 4.0F, 4.0F, 12.0F, new CubeDeformation(0.0F))
                .texOffs(246, 40).addBox(0.0F, -5.0F, 0.0F, 0.0F, 3.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 13.0F));

        PartDefinition tail_tip = tailMid1.addOrReplaceChild("tail_tip", CubeListBuilder.create().texOffs(82, 224).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 14.0F, new CubeDeformation(0.0F))
                .texOffs(184, 128).addBox(0.0F, -6.0F, 0.0F, 0.0F, 12.0F, 24.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 11.0F));

        PartDefinition left_wing = torso.addOrReplaceChild("left_wing", CubeListBuilder.create().texOffs(184, 243).addBox(4.0F, -2.0F, -2.0F, 12.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(192, 101).addBox(-1.0F, 0.0F, 2.0F, 17.0F, 0.0F, 14.0F, new CubeDeformation(0.0F))
                .texOffs(200, 257).addBox(-1.0F, -2.5F, -2.5F, 5.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(8.0F, -5.5F, -23.5F));

        PartDefinition leftMidWing = left_wing.addOrReplaceChild("leftMidWing", CubeListBuilder.create().texOffs(0, 168).addBox(1.0F, -0.1F, -10.0F, 14.0F, 0.0F, 32.0F, new CubeDeformation(0.0F)), PartPose.offset(15.0F, 0.0F, 0.0F));

        PartDefinition cube_r25 = leftMidWing.addOrReplaceChild("cube_r25", CubeListBuilder.create().texOffs(200, 56).addBox(0.0F, -4.0F, -4.0F, 18.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 2.0F, 2.0F, 0.0F, 0.6981F, 0.0F));

        PartDefinition leftMainFin = leftMidWing.addOrReplaceChild("leftMainFin", CubeListBuilder.create().texOffs(0, 64).addBox(-0.225F, 0.0F, 2.625F, 70.0F, 0.0F, 26.0F, new CubeDeformation(0.0F))
                .texOffs(184, 116).addBox(-0.0074F, -1.5F, -0.0094F, 36.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(114, 232).addBox(-0.0074F, -1.0F, -3.0094F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(254, 112).addBox(1.9926F, -1.0F, -3.0094F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(12.225F, 0.0F, -12.625F));

        PartDefinition cube_r26 = leftMainFin.addOrReplaceChild("cube_r26", CubeListBuilder.create().texOffs(184, 188).addBox(0.0F, -2.0F, 0.0F, 36.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(35.9927F, 1.0F, 0.4907F, 0.0F, -0.2618F, 0.0F));

        PartDefinition leftMidFin = leftMainFin.addOrReplaceChild("leftMidFin", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r27 = leftMidFin.addOrReplaceChild("cube_r27", CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, -1.9F, -1.0F, 68.0F, 0.0F, 32.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.225F, 2.0F, 2.625F, 0.0F, -0.3491F, 0.0F));

        PartDefinition cube_r28 = leftMidFin.addOrReplaceChild("cube_r28", CubeListBuilder.create().texOffs(184, 164).addBox(0.0F, -2.5F, 0.0F, 36.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.0074F, 1.5F, -0.0094F, 0.0F, -0.3491F, 0.0F));

        PartDefinition cube_r29 = leftMidFin.addOrReplaceChild("cube_r29", CubeListBuilder.create().texOffs(192, 64).addBox(0.0F, -2.5F, 0.0F, 36.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(33.8216F, 1.5F, 12.3034F, 0.0F, -0.6109F, 0.0F));

        PartDefinition left_innerFin = leftMainFin.addOrReplaceChild("left_innerFin", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r30 = left_innerFin.addOrReplaceChild("cube_r30", CubeListBuilder.create().texOffs(184, 168).addBox(0.0F, -2.5F, 0.0F, 36.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.0074F, 1.5F, -0.0094F, 0.0F, -0.8727F, 0.0F));

        PartDefinition cube_r31 = left_innerFin.addOrReplaceChild("cube_r31", CubeListBuilder.create().texOffs(184, 172).addBox(0.0F, -2.5F, 0.0F, 36.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(23.133F, 1.5F, 27.5682F, 0.0F, -1.1345F, 0.0F));

        PartDefinition cube_r32 = left_innerFin.addOrReplaceChild("cube_r32", CubeListBuilder.create().texOffs(0, 116).addBox(0.0F, -1.8F, -1.0F, 66.0F, 0.0F, 26.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.225F, 2.0F, 2.625F, 0.0F, -0.8727F, 0.0F));

        PartDefinition right_wing = torso.addOrReplaceChild("right_wing", CubeListBuilder.create().texOffs(244, 56).addBox(-16.0F, -2.0F, -2.0F, 12.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(184, 192).addBox(-16.0F, 0.0F, 2.0F, 17.0F, 0.0F, 14.0F, new CubeDeformation(0.0F))
                .texOffs(260, 164).addBox(-4.0F, -2.5F, -2.5F, 5.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(-8.0F, -5.5F, -23.5F));

        PartDefinition rightMidWing = right_wing.addOrReplaceChild("rightMidWing", CubeListBuilder.create().texOffs(92, 168).addBox(-15.0F, -0.1F, -10.0F, 14.0F, 0.0F, 32.0F, new CubeDeformation(0.0F)), PartPose.offset(-15.0F, 0.0F, 0.0F));

        PartDefinition cube_r33 = rightMidWing.addOrReplaceChild("cube_r33", CubeListBuilder.create().texOffs(190, 206).addBox(-18.0F, -4.0F, -4.0F, 18.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 2.0F, 2.0F, 0.0F, -0.6981F, 0.0F));

        PartDefinition rightMainFin = rightMidWing.addOrReplaceChild("rightMainFin", CubeListBuilder.create().texOffs(0, 90).addBox(-69.775F, 0.0F, 2.625F, 70.0F, 0.0F, 26.0F, new CubeDeformation(0.0F))
                .texOffs(184, 122).addBox(-35.9927F, -1.5F, -0.0094F, 36.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(92, 264).addBox(-1.9926F, -1.0F, -3.0094F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(264, 136).addBox(-3.9926F, -1.0F, -3.0094F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-12.225F, 0.0F, -12.625F));

        PartDefinition cube_r34 = rightMainFin.addOrReplaceChild("cube_r34", CubeListBuilder.create().texOffs(192, 72).addBox(-36.0F, -2.0F, 0.0F, 36.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-35.9927F, 1.0F, 0.4907F, 0.0F, 0.2618F, 0.0F));

        PartDefinition right_midfin = rightMainFin.addOrReplaceChild("right_midfin", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r35 = right_midfin.addOrReplaceChild("cube_r35", CubeListBuilder.create().texOffs(0, 32).addBox(-68.0F, -1.9F, -1.0F, 68.0F, 0.0F, 32.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.225F, 2.0F, 2.625F, 0.0F, 0.3491F, 0.0F));

        PartDefinition cube_r36 = right_midfin.addOrReplaceChild("cube_r36", CubeListBuilder.create().texOffs(184, 184).addBox(-36.0F, -2.5F, 0.0F, 36.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0074F, 1.5F, -0.0094F, 0.0F, 0.3491F, 0.0F));

        PartDefinition cube_r37 = right_midfin.addOrReplaceChild("cube_r37", CubeListBuilder.create().texOffs(192, 68).addBox(-36.0F, -2.5F, 0.0F, 36.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-33.8216F, 1.5F, 12.3034F, 0.0F, 0.6109F, 0.0F));

        PartDefinition right_innerFin = rightMainFin.addOrReplaceChild("right_innerFin", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r38 = right_innerFin.addOrReplaceChild("cube_r38", CubeListBuilder.create().texOffs(184, 180).addBox(-36.0F, -2.5F, 0.0F, 36.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0074F, 1.5F, -0.0094F, 0.0F, 0.8727F, 0.0F));

        PartDefinition cube_r39 = right_innerFin.addOrReplaceChild("cube_r39", CubeListBuilder.create().texOffs(184, 176).addBox(-36.0F, -2.5F, 0.0F, 36.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-23.133F, 1.5F, 27.5682F, 0.0F, 1.1345F, 0.0F));

        PartDefinition cube_r40 = right_innerFin.addOrReplaceChild("cube_r40", CubeListBuilder.create().texOffs(0, 142).addBox(-66.0F, -1.8F, -1.0F, 66.0F, 0.0F, 26.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.225F, 2.0F, 2.625F, 0.0F, 0.8727F, 0.0F));

        PartDefinition right_leg = root.addOrReplaceChild("right_leg", CubeListBuilder.create(), PartPose.offset(-6.0F, -24.0F, 20.0F));

        PartDefinition cube_r41 = right_leg.addOrReplaceChild("cube_r41", CubeListBuilder.create().texOffs(90, 240).addBox(-4.0F, -16.0F, 3.0F, 8.0F, 8.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(220, 225).addBox(-4.0F, -16.0F, -1.0F, 8.0F, 16.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.0F, 11.0F, -8.0F, -0.5236F, 0.0F, 0.0F));

        PartDefinition cube_r42 = right_leg.addOrReplaceChild("cube_r42", CubeListBuilder.create().texOffs(232, 128).addBox(-3.9F, -4.0F, -5.0F, 8.0F, 4.0F, 8.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(-4.1F, 9.1084F, -0.3993F, 0.5236F, 0.0F, 0.0F));

        PartDefinition right_joint = right_leg.addOrReplaceChild("right_joint", CubeListBuilder.create(), PartPose.offset(-4.0F, 10.0F, -9.0F));

        PartDefinition cube_r43 = right_joint.addOrReplaceChild("cube_r43", CubeListBuilder.create().texOffs(48, 220).addBox(-3.5F, -4.5F, -10.0F, 7.0F, 5.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 6.0F, 9.0F, -0.5236F, 0.0F, 0.0F));

        PartDefinition rightFoot = right_joint.addOrReplaceChild("rightFoot", CubeListBuilder.create(), PartPose.offset(0.0F, 4.0F, 9.0F));

        PartDefinition cube_r44 = rightFoot.addOrReplaceChild("cube_r44", CubeListBuilder.create().texOffs(26, 239).addBox(-1.0F, -13.0F, -2.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(200, 251).addBox(-3.0F, -2.0F, -5.0F, 6.0F, 4.0F, 2.0F, new CubeDeformation(0.1F))
                .texOffs(158, 248).addBox(-3.0F, -9.0F, -4.0F, 6.0F, 10.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 8.0F, 0.0F, -0.5236F, 0.0F, 0.0F));

        PartDefinition right_footB = rightFoot.addOrReplaceChild("right_footB", CubeListBuilder.create().texOffs(110, 264).addBox(-1.0F, 0.25F, -8.55F, 2.0F, 3.0F, 2.0F, new CubeDeformation(-0.25F))
                .texOffs(230, 218).addBox(-1.0F, 0.1F, -6.9F, 2.0F, 3.0F, 4.0F, new CubeDeformation(-0.1F))
                .texOffs(256, 0).addBox(-3.0F, 0.0F, -3.0F, 6.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 7.0F, -3.0F));

        PartDefinition cube_r45 = right_footB.addOrReplaceChild("cube_r45", CubeListBuilder.create().texOffs(76, 256).addBox(-0.1F, -2.9F, -3.9F, 2.0F, 3.0F, 4.0F, new CubeDeformation(-0.1F))
                .texOffs(118, 264).addBox(-0.1F, -2.75F, -5.55F, 2.0F, 3.0F, 2.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(1.0F, 3.0F, -3.0F, 0.0F, -0.2618F, 0.0F));

        PartDefinition cube_r46 = right_footB.addOrReplaceChild("cube_r46", CubeListBuilder.create().texOffs(52, 248).addBox(-1.9F, -2.9F, -3.9F, 2.0F, 3.0F, 4.0F, new CubeDeformation(-0.1F))
                .texOffs(134, 252).addBox(-1.9F, -2.75F, -5.55F, 2.0F, 3.0F, 2.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(-1.0F, 3.0F, -3.0F, 0.0F, 0.2618F, 0.0F));

        PartDefinition left_leg = root.addOrReplaceChild("left_leg", CubeListBuilder.create(), PartPose.offset(6.0F, -24.0F, 20.0F));

        PartDefinition cube_r47 = left_leg.addOrReplaceChild("cube_r47", CubeListBuilder.create().texOffs(246, 192).addBox(-4.0F, -16.0F, 3.0F, 8.0F, 8.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(160, 228).addBox(-4.0F, -16.0F, -1.0F, 8.0F, 16.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, 11.0F, -8.0F, -0.5236F, 0.0F, 0.0F));

        PartDefinition cube_r48 = left_leg.addOrReplaceChild("cube_r48", CubeListBuilder.create().texOffs(234, 206).addBox(-4.1F, -4.0F, -5.0F, 8.0F, 4.0F, 8.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(4.1F, 9.1084F, -0.3993F, 0.5236F, 0.0F, 0.0F));

        PartDefinition left_joint = left_leg.addOrReplaceChild("left_joint", CubeListBuilder.create(), PartPose.offset(4.0F, 10.0F, -9.0F));

        PartDefinition cube_r49 = left_joint.addOrReplaceChild("cube_r49", CubeListBuilder.create().texOffs(0, 224).addBox(-3.5F, -4.5F, -10.0F, 7.0F, 5.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 6.0F, 9.0F, -0.5236F, 0.0F, 0.0F));

        PartDefinition leftFoot = left_joint.addOrReplaceChild("leftFoot", CubeListBuilder.create(), PartPose.offset(0.0F, 4.0F, 9.0F));

        PartDefinition cube_r50 = leftFoot.addOrReplaceChild("cube_r50", CubeListBuilder.create().texOffs(102, 264).addBox(-1.0F, -13.0F, -2.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(262, 120).addBox(-3.0F, -2.0F, -5.0F, 6.0F, 4.0F, 2.0F, new CubeDeformation(0.1F))
                .texOffs(0, 255).addBox(-3.0F, -9.0F, -4.0F, 6.0F, 10.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 8.0F, 0.0F, -0.5236F, 0.0F, 0.0F));

        PartDefinition left_footB = leftFoot.addOrReplaceChild("left_footB", CubeListBuilder.create().texOffs(264, 126).addBox(-1.0F, 0.25F, -8.55F, 2.0F, 3.0F, 2.0F, new CubeDeformation(-0.25F))
                .texOffs(38, 257).addBox(-1.0F, 0.1F, -6.9F, 2.0F, 3.0F, 4.0F, new CubeDeformation(-0.1F))
                .texOffs(52, 256).addBox(-3.0F, 0.0F, -3.0F, 6.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 7.0F, -3.0F));

        PartDefinition cube_r51 = left_footB.addOrReplaceChild("cube_r51", CubeListBuilder.create().texOffs(38, 264).addBox(-1.9F, -2.9F, -3.9F, 2.0F, 3.0F, 4.0F, new CubeDeformation(-0.1F))
                .texOffs(264, 131).addBox(-1.9F, -2.75F, -5.55F, 2.0F, 3.0F, 2.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(-1.0F, 3.0F, -3.0F, 0.0F, 0.2618F, 0.0F));

        PartDefinition cube_r52 = left_footB.addOrReplaceChild("cube_r52", CubeListBuilder.create().texOffs(188, 263).addBox(-0.1F, -2.9F, -3.9F, 2.0F, 3.0F, 4.0F, new CubeDeformation(-0.1F))
                .texOffs(126, 264).addBox(-0.1F, -2.75F, -5.55F, 2.0F, 3.0F, 2.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(1.0F, 3.0F, -3.0F, 0.0F, -0.2618F, 0.0F));

        return LayerDefinition.create(meshdefinition, 512, 512);
    }
    @Override
    public void setupAnim(FireDragonRenderState state) {
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
