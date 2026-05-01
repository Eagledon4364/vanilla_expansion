package com.chris.vanilla_expansion.entity.client.model;


import com.chris.vanilla_expansion.VanillaExpansion;
import com.chris.vanilla_expansion.entity.client.animation.AirDragonAnimations;
import com.chris.vanilla_expansion.entity.client.animation.EnergyDragonAnimations;
import net.minecraft.client.animation.KeyframeAnimation;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.NotNull;

public class AirDragonModel extends EntityModel<@NotNull AirDragonRenderState> {

public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(Identifier.fromNamespaceAndPath(VanillaExpansion.MOD_ID, "airdragonmodel"), "main");

    private final KeyframeAnimation idleAnimation;
    private final KeyframeAnimation walkAnimation;
    private final KeyframeAnimation flyAnimation;
    private final KeyframeAnimation hoverAnimation;
    private final KeyframeAnimation sleepAnimation;
    private final KeyframeAnimation sitAnimation;
    private final KeyframeAnimation meleeAnimation;
    private final KeyframeAnimation fireAnimation;

    private final ModelPart root;


    private final ModelPart body;
private final ModelPart neck;
private final ModelPart head;
private final ModelPart jaw;

public AirDragonModel(ModelPart root) {
    super(root);
    this.root = root.getChild("root");
    this.body = this.root.getChild("body");
    this.neck = this.body.getChild("neck");
    this.head = this.neck.getChild("head");
    this.jaw = this.head.getChild("jaw");
    this.idleAnimation = AirDragonAnimations.IDLE.bake(root);
    this.walkAnimation = AirDragonAnimations.WALK.bake(root);
    this.flyAnimation = AirDragonAnimations.WALK.bake(root);
    this.hoverAnimation = AirDragonAnimations.HOVER.bake(root);
    this.sleepAnimation = AirDragonAnimations.SLEEPING.bake(root);
    this.sitAnimation = AirDragonAnimations.SIT.bake(root);
    this.fireAnimation = AirDragonAnimations.FIRE.bake(root);
    this.meleeAnimation = AirDragonAnimations.MELEE.bake(root);


}


public static LayerDefinition getTextureModelData() {
    MeshDefinition meshdefinition = new MeshDefinition();
    PartDefinition partdefinition = meshdefinition.getRoot();

    PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

    PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 39).addBox(-3.5F, 0.0F, 7.0F, 7.0F, 8.0F, 20.0F, new CubeDeformation(0.0F))
            .texOffs(52, 0).addBox(-4.0F, -0.5F, -8.0F, 8.0F, 9.0F, 16.0F, new CubeDeformation(0.0F))
            .texOffs(40, 69).addBox(0.0F, -3.0F, 8.0F, 0.0F, 3.0F, 19.0F, new CubeDeformation(0.0F))
            .texOffs(78, 69).addBox(0.0F, -3.5F, -8.0F, 0.0F, 3.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -19.0F, 0.0F));

    PartDefinition neck = body.addOrReplaceChild("neck", CubeListBuilder.create().texOffs(54, 25).addBox(-3.5F, -4.0F, -15.0F, 7.0F, 8.0F, 16.0F, new CubeDeformation(0.0F))
            .texOffs(100, 0).addBox(0.0F, -7.0F, -15.0F, 0.0F, 3.0F, 15.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 4.0F, -8.0F));

    PartDefinition head = neck.addOrReplaceChild("head", CubeListBuilder.create().texOffs(78, 88).addBox(-4.0F, -4.0F, -8.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
            .texOffs(100, 35).addBox(-2.5F, -1.0F, -14.0F, 5.0F, 4.0F, 6.0F, new CubeDeformation(0.0F))
            .texOffs(48, 119).addBox(-1.5F, -2.0F, -15.0F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
            .texOffs(34, 91).addBox(-4.0F, -4.0F, -8.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.1F))
            .texOffs(34, 89).addBox(-3.5F, 0.0F, -15.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .texOffs(66, 99).addBox(8.0F, -4.0F, -2.5F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
            .texOffs(110, 122).addBox(-9.0F, -4.0F, -2.5F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
            .texOffs(34, 87).addBox(1.5F, 0.0F, -15.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -15.0F));

    PartDefinition cube_r1 = head.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(24, 103).addBox(-1.0F, 0.0F, 0.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
            .texOffs(100, 45).addBox(-7.0F, 0.0F, 0.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.5F, 1.0F, -15.0F, 0.3491F, 0.0F, 0.0F));

    PartDefinition cube_r2 = head.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(116, 98).addBox(0.0F, -1.0F, 0.0F, 1.0F, 1.0F, 7.0F, new CubeDeformation(0.0F))
            .texOffs(116, 106).addBox(17.0F, -1.0F, 0.0F, 1.0F, 1.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-9.0F, -3.0F, -2.5F, 0.8727F, 0.0F, 0.0F));

    PartDefinition cube_r3 = head.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(96, 67).addBox(0.0F, -1.0F, 0.0F, 5.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, -3.0F, -4.0F, 0.0F, -0.2618F, 0.0F));

    PartDefinition cube_r4 = head.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(40, 67).addBox(-5.0F, -1.0F, 0.0F, 5.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.0F, -3.0F, -4.0F, 0.0F, 0.2618F, 0.0F));

    PartDefinition cube_r5 = head.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(66, 104).addBox(0.0F, -10.0F, 0.0F, 0.0F, 10.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.0F, 5.0F, -5.0F, 0.0F, -0.1745F, 0.0F));

    PartDefinition cube_r6 = head.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(0, 103).addBox(0.0F, -10.0F, 0.0F, 0.0F, 10.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, 5.0F, -5.0F, 0.0F, 0.1745F, 0.0F));

    PartDefinition jaw = head.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(110, 91).addBox(-2.5F, 0.0F, -6.0F, 5.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 3.0F, -8.0F));

    PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(0, 67).addBox(-3.0F, -3.0F, -1.0F, 6.0F, 6.0F, 14.0F, new CubeDeformation(0.0F))
            .texOffs(90, 104).addBox(0.0F, -6.0F, 0.0F, 0.0F, 3.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 4.0F, 27.0F));

    PartDefinition tailMid = tail.addOrReplaceChild("tailMid", CubeListBuilder.create().texOffs(54, 49).addBox(-2.5F, -2.0F, -1.0F, 5.0F, 4.0F, 16.0F, new CubeDeformation(0.0F))
            .texOffs(96, 49).addBox(0.0F, -5.0F, 0.0F, 0.0F, 3.0F, 15.0F, new CubeDeformation(0.0F))
            .texOffs(100, 18).addBox(0.0F, 2.0F, 0.0F, 0.0F, 2.0F, 15.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 13.0F));

    PartDefinition tailEnd = tailMid.addOrReplaceChild("tailEnd", CubeListBuilder.create().texOffs(0, 87).addBox(-2.0F, -1.5F, -1.0F, 4.0F, 3.0F, 13.0F, new CubeDeformation(0.0F))
            .texOffs(0, 0).addBox(0.0F, -8.0F, 0.0F, 0.0F, 13.0F, 26.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 15.0F));

    PartDefinition right_legF = body.addOrReplaceChild("right_legF", CubeListBuilder.create().texOffs(42, 107).addBox(-2.0F, -1.0F, -3.0F, 4.0F, 7.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(-4.0F, 6.0F, -4.0F));

    PartDefinition right_leftFB = right_legF.addOrReplaceChild("right_leftFB", CubeListBuilder.create().texOffs(36, 119).addBox(-1.5F, 1.0F, 2.0F, 3.0F, 5.0F, 3.0F, new CubeDeformation(0.0F))
            .texOffs(126, 54).addBox(1.0F, 4.0F, 0.0F, 0.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
            .texOffs(126, 59).addBox(-1.0F, 4.0F, 0.0F, 0.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
            .texOffs(64, 126).addBox(0.0F, 4.0F, 0.0F, 0.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
            .texOffs(16, 125).addBox(0.0F, 5.0F, 3.0F, 0.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 6.0F, -1.0F));

    PartDefinition cube_r7 = right_leftFB.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(122, 42).addBox(-1.5F, 0.0F, 0.0F, 3.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, -1.0F, 1.0036F, 0.0F, 0.0F));

    PartDefinition left_legF = body.addOrReplaceChild("left_legF", CubeListBuilder.create().texOffs(24, 107).addBox(-2.0F, -1.0F, -3.0F, 4.0F, 7.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(4.0F, 6.0F, -4.0F));

    PartDefinition left_legFB = left_legF.addOrReplaceChild("left_legFB", CubeListBuilder.create().texOffs(66, 91).addBox(-1.5F, 1.0F, 2.0F, 3.0F, 5.0F, 3.0F, new CubeDeformation(0.0F))
            .texOffs(60, 107).addBox(1.0F, 4.0F, 0.0F, 0.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
            .texOffs(110, 98).addBox(0.0F, 4.0F, 0.0F, 0.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
            .texOffs(0, 125).addBox(0.0F, 5.0F, 3.0F, 0.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
            .texOffs(60, 112).addBox(-1.0F, 4.0F, 0.0F, 0.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 6.0F, -1.0F));

    PartDefinition cube_r8 = left_legFB.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(90, 120).addBox(-1.5F, 0.0F, 0.0F, 3.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, -1.0F, 1.0036F, 0.0F, 0.0F));

    PartDefinition left_legB = body.addOrReplaceChild("left_legB", CubeListBuilder.create().texOffs(110, 67).addBox(-2.0F, -1.0F, -2.0F, 4.0F, 7.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(4.0F, 6.0F, 21.0F));

    PartDefinition left_legBB = left_legB.addOrReplaceChild("left_legBB", CubeListBuilder.create().texOffs(116, 114).addBox(-1.5F, 1.0F, 2.0F, 3.0F, 5.0F, 3.0F, new CubeDeformation(0.0F))
            .texOffs(8, 125).addBox(0.0F, 5.0F, 3.0F, 0.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
            .texOffs(52, 125).addBox(-1.0F, 4.0F, 0.0F, 0.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
            .texOffs(56, 125).addBox(0.0F, 4.0F, 0.0F, 0.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
            .texOffs(126, 49).addBox(1.0F, 4.0F, 0.0F, 0.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 6.0F, 0.0F));

    PartDefinition cube_r9 = left_legBB.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(100, 120).addBox(-1.5F, 0.0F, 0.0F, 3.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, -1.0F, 1.0036F, 0.0F, 0.0F));

    PartDefinition right_legB = body.addOrReplaceChild("right_legB", CubeListBuilder.create().texOffs(110, 79).addBox(-2.0F, -1.0F, -2.0F, 4.0F, 7.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(-4.0F, 6.0F, 21.0F));

    PartDefinition right_legBB = right_legB.addOrReplaceChild("right_legBB", CubeListBuilder.create().texOffs(24, 119).addBox(-1.5F, 1.0F, 2.0F, 3.0F, 5.0F, 3.0F, new CubeDeformation(0.0F))
            .texOffs(60, 117).addBox(-1.0F, 4.0F, 0.0F, 0.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
            .texOffs(60, 122).addBox(0.0F, 4.0F, 0.0F, 0.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
            .texOffs(48, 125).addBox(1.0F, 4.0F, 0.0F, 0.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
            .texOffs(120, 122).addBox(0.0F, 5.0F, 3.0F, 0.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 6.0F, 0.0F));

    PartDefinition cube_r10 = right_legBB.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(122, 35).addBox(-1.5F, 0.0F, 0.0F, 3.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, -1.0F, 1.0036F, 0.0F, 0.0F));

    return LayerDefinition.create(meshdefinition, 256, 256);
}

    @Override
    public void setupAnim(AirDragonRenderState state) {
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
            return;
        }
        else if (state.isSitting) {
            this.sitAnimation.apply(state.sitAnimationState, ageInTicks);
            return;
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
    }

    public ModelPart getRoot() {
        return root;
    }

}