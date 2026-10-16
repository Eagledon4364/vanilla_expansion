package com.chris.vanilla_expansion.entity.client.model;

import com.chris.vanilla_expansion.VanillaExpansion;
import com.chris.vanilla_expansion.entity.client.animation.GeckoAnimations;
import com.chris.vanilla_expansion.entity.client.render.gecko.GeckoRenderState;
import net.minecraft.client.animation.KeyframeAnimation;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.NotNull;

public class GeckoModel extends EntityModel<@NotNull GeckoRenderState> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(Identifier.fromNamespaceAndPath(VanillaExpansion.MOD_ID, "gecko_model"), "main");

    private final KeyframeAnimation idleAnimation;
    private final KeyframeAnimation walkAnimation;
    private final KeyframeAnimation sleepStartAnimation;
    private final KeyframeAnimation sleepAnimation;
    private final KeyframeAnimation sleepStopAnimation;
    private final KeyframeAnimation mountAnimation;

    private final ModelPart root;
    private final ModelPart body;
    private final ModelPart head;
    private final ModelPart tail;
    private final ModelPart l1;
    private final ModelPart l2;
    private final ModelPart r1;
    private final ModelPart r2;
    private final ModelPart tongue;

    public GeckoModel(ModelPart root) {
        super(root);
        this.root = root.getChild("root");
        this.body = this.root.getChild("body");
        this.head = this.body.getChild("head");
        this.tail = this.body.getChild("tail");
        this.l1 = this.body.getChild("l1");
        this.l2 = this.body.getChild("l2");
        this.r1 = this.body.getChild("r1");
        this.r2 = this.body.getChild("r2");
        this.tongue = this.head.getChild("tongue");

        this.idleAnimation = GeckoAnimations.IDLE.bake(root);
        this.walkAnimation = GeckoAnimations.WALK.bake(root);
        this.sleepStartAnimation = GeckoAnimations.SLEEP_START.bake(root);
        this.sleepAnimation = GeckoAnimations.SLEEP.bake(root);
        this.sleepStopAnimation = GeckoAnimations.SLEEP_STOP.bake(root);
        this.mountAnimation = GeckoAnimations.GECKO_MOUNT.bake(root);
    }

    public static LayerDefinition getTextureModelData() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -4.0F, -4.0F, 6.0F, 4.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(24, 24).addBox(-2.0F, -2.0F, -4.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(16, 25).addBox(-1.0F, 0.0F, -5.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -3.0F, -4.0F));

        PartDefinition tongue = head.addOrReplaceChild("tongue", CubeListBuilder.create().texOffs(-1, -1).addBox(-1.0F, 0.0F, -3.0F, 2.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 1.0F, -2.0F));

        PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(0, 14).addBox(-2.0F, -1.0F, -1.0F, 4.0F, 3.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(24, 14).addBox(-1.0F, 0.0F, 6.0F, 2.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.0F, 6.0F));

        PartDefinition l1 = body.addOrReplaceChild("l1", CubeListBuilder.create().texOffs(32, 4).addBox(0.0F, -2.0F, -1.0F, 6.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(32, 8).addBox(4.0F, 0.0F, -2.0F, 3.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(3.0F, 0.0F, -3.0F));

        PartDefinition l2 = body.addOrReplaceChild("l2", CubeListBuilder.create().texOffs(0, 25).addBox(0.0F, -2.0F, -1.0F, 6.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(32, 11).addBox(4.0F, 0.0F, -2.0F, 3.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(3.0F, 0.0F, 4.0F));

        PartDefinition r1 = body.addOrReplaceChild("r1", CubeListBuilder.create().texOffs(32, 0).addBox(-6.0F, -2.0F, -1.0F, 6.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(28, 32).addBox(-7.0F, 0.0F, -2.0F, 3.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.0F, 0.0F, -3.0F));

        PartDefinition r2 = body.addOrReplaceChild("r2", CubeListBuilder.create().texOffs(0, 29).addBox(-6.0F, -2.0F, -1.0F, 6.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(16, 32).addBox(-7.0F, 0.0F, -2.0F, 3.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.0F, 0.0F, 4.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void setupAnim(GeckoRenderState state) {
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

        if (state.isRidingShoulder) {
            this.mountAnimation.apply(state.mountAnimationState, ageInTicks);
            return;
        } else if (state.isSleeping) {
            this.sleepAnimation.apply(state.sleepAnimationState, ageInTicks);
            return;
        } else {
            if (state.walkAnimationSpeed > 0.01F) {
                this.walkAnimation.applyWalk(state.walkAnimationPos, state.walkAnimationSpeed, 2.0F, 2.5F);
            } else {
                this.idleAnimation.apply(state.idleAnimationState, ageInTicks);
            }
        }
    }

    public ModelPart getRoot() {
        return root;
    }
}