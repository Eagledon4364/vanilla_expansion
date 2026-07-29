package com.chris.vanilla_expansion.entity.client.model.dragon;

import com.chris.vanilla_expansion.VanillaExpansion;
import com.chris.vanilla_expansion.entity.client.animation.EarthDragonAnimations;
import com.chris.vanilla_expansion.entity.client.animation.WaterDragonAnimations;
import com.chris.vanilla_expansion.entity.client.render.earth_dragon.EarthDragonRenderState;
import com.chris.vanilla_expansion.entity.client.render.water_dragon.WaterDragonRenderState;
import net.minecraft.client.animation.KeyframeAnimation;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.NotNull;

public class EarthDragonModel extends EntityModel<@NotNull EarthDragonRenderState> {
    // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(Identifier.fromNamespaceAndPath(VanillaExpansion.MOD_ID, "earthdragon"), "main");
    private final ModelPart root;

    private final KeyframeAnimation idleAnimation;
    private final KeyframeAnimation walkAnimation;
    private final KeyframeAnimation sleepAnimation;
    private final KeyframeAnimation sitAnimation;
    private final KeyframeAnimation meleeAnimation;

    public EarthDragonModel(ModelPart root) {
        super(root);
        this.root = root.getChild("root");
        //TODO change to earth animations
        this.idleAnimation = EarthDragonAnimations.IDLE.bake(root);
        this.walkAnimation = EarthDragonAnimations.WALK.bake(root);
        this.sleepAnimation = EarthDragonAnimations.SLEEP.bake(root);
        this.sitAnimation = EarthDragonAnimations.SIT.bake(root);
        this.meleeAnimation = EarthDragonAnimations.MELEE.bake(root);
    }

    public static LayerDefinition getTextureModelData() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-7.0F, -14.0F, -11.0F, 14.0F, 14.0F, 20.0F, new CubeDeformation(0.0F))
                .texOffs(0, 34).addBox(-6.0F, -13.0F, 8.0F, 12.0F, 12.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -6.0F, 0.0F));

        PartDefinition neck = body.addOrReplaceChild("neck", CubeListBuilder.create().texOffs(60, 60).addBox(-4.0F, -4.0F, -11.0F, 8.0F, 8.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -8.0F, -11.0F));

        PartDefinition head = neck.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 64).addBox(-5.0F, -5.0F, -6.0F, 10.0F, 10.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(0, 80).addBox(-4.0F, -3.0F, -14.0F, 8.0F, 3.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -10.0F));

        PartDefinition jaw = head.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(68, 14).addBox(-4.0F, 0.0F, -8.0F, 8.0F, 5.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -6.0F));

        PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(60, 34).addBox(-5.0F, -5.0F, -1.0F, 10.0F, 10.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(68, 0).addBox(-7.0F, -6.0F, 15.0F, 14.0F, 12.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(32, 64).addBox(-1.0F, -1.0F, 16.0F, 2.0F, 2.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -7.0F, 26.0F));

        PartDefinition cube_r1 = tail.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(26, 92).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, 0.0F, 16.0F, 0.0436F, -0.0873F, 0.0F));

        PartDefinition cube_r2 = tail.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(32, 78).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, 0.0F, 16.0F, -0.0873F, 0.0873F, 0.0F));

        PartDefinition cube_r3 = tail.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(104, 95).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, 2.0F, 17.0F, -0.1805F, 0.2582F, -0.0427F));

        PartDefinition cube_r4 = tail.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(66, 108).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, 2.0F, 17.0F, -0.0883F, -0.1742F, 0.0077F));

        PartDefinition cube_r5 = tail.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(60, 80).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, -2.0F, 17.0F, 0.1309F, -0.1745F, 0.0F));

        PartDefinition cube_r6 = tail.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(100, 60).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, -2.0F, 17.0F, 0.2233F, 0.2129F, 0.0479F));

        PartDefinition cube_r7 = tail.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(78, 95).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 3.0F, 17.0F, -0.1309F, 0.0F, 0.0F));

        PartDefinition cube_r8 = tail.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(52, 94).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.0F, 17.0F, 0.0873F, 0.0F, 0.0F));

        PartDefinition left_bleg = body.addOrReplaceChild("left_bleg", CubeListBuilder.create().texOffs(100, 0).addBox(6.0F, -3.0F, -3.0F, 6.0F, 8.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(46, 107).addBox(6.0F, 5.0F, -2.0F, 5.0F, 8.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -7.0F, 16.0F));

        PartDefinition right_bleg = body.addOrReplaceChild("right_bleg", CubeListBuilder.create().texOffs(100, 14).addBox(-12.0F, -3.0F, -3.0F, 6.0F, 8.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(104, 107).addBox(-11.0F, 5.0F, -2.0F, 5.0F, 8.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -7.0F, 16.0F));

        PartDefinition left_fleg = body.addOrReplaceChild("left_fleg", CubeListBuilder.create().texOffs(26, 105).addBox(7.0F, 5.0F, -3.0F, 5.0F, 10.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(68, 27).addBox(11.0F, 13.0F, -4.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(68, 30).addBox(9.0F, 13.0F, -4.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(72, 27).addBox(7.0F, 13.0F, -4.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(88, 80).addBox(7.0F, -3.0F, -4.0F, 6.0F, 8.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -9.0F, -5.0F));

        PartDefinition right_fleg = body.addOrReplaceChild("right_fleg", CubeListBuilder.create().texOffs(0, 91).addBox(-13.0F, -3.0F, -4.0F, 6.0F, 8.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(72, 30).addBox(-12.0F, 13.0F, -4.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 106).addBox(-12.0F, 5.0F, -3.0F, 5.0F, 10.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(76, 27).addBox(-10.0F, 13.0F, -4.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(76, 30).addBox(-8.0F, 13.0F, -4.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -9.0F, -5.0F));

        return LayerDefinition.create(meshdefinition, 128, 128);
    }

    @Override
    public void setupAnim(EarthDragonRenderState state) {
        super.setupAnim(state);

        this.root.getAllParts().forEach(ModelPart::resetPose);
        float ageInTicks = state.ageInTicks;

        if (state.isBaby) {
            float babyScale = 0.5f;
            this.root.xScale = babyScale;
            this.root.yScale = babyScale;
            this.root.zScale = babyScale;
            
        } else {
            this.root.xScale = 1.0f;
            this.root.yScale = 1.0f;
            this.root.zScale = 1.0f;

        }

        // 3. Apply Keyframe Animations
        if (state.isSleeping) {
            this.sleepAnimation.apply(state.sleepingAnimationState, ageInTicks);
        } else if (state.isSitting) {
            this.sitAnimation.apply(state.sitAnimationState, ageInTicks);
        } else {
            this.walkAnimation.applyWalk(state.walkAnimationPos, state.walkAnimationSpeed, 1.0f, 2.5f);
            this.idleAnimation.apply(state.idleAnimationState, ageInTicks);
        }

        this.meleeAnimation.apply(state.meleeAnimationState, ageInTicks);
    }

    public ModelPart getRoot() {
        return root;
    }
}
