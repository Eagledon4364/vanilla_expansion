package com.chris.vanilla_expansion.entity.client.model.elementals;// Made with Blockbench 5.1.3
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import org.jetbrains.annotations.NotNull;

public class EarthElementalModel extends EntityModel<@NotNull LivingEntityRenderState> {

    private final ModelPart main;
	private final ModelPart body;
	private final ModelPart head;
	private final ModelPart LArm;
	private final ModelPart RArm;
	private final ModelPart LLeg;
	private final ModelPart RLeg;
	private final ModelPart core;

	public EarthElementalModel(ModelPart root) {
        super(root);
        this.main = root.getChild("main");
		this.body = this.main.getChild("body");
		this.head = this.body.getChild("head");
		this.LArm = this.body.getChild("LArm");
		this.RArm = this.body.getChild("RArm");
		this.LLeg = this.body.getChild("LLeg");
		this.RLeg = this.body.getChild("RLeg");
		this.core = this.body.getChild("core");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition main = partdefinition.addOrReplaceChild("main", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition body = main.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-5.0F, -3.0F, -4.0F, 10.0F, 6.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -7.0F, 0.0F));

		PartDefinition cube_r1 = body.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(16, 28).addBox(-1.0F, -4.0F, -1.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, 0.0F, 4.0F, -0.4363F, 0.0F, -0.5236F));

		PartDefinition cube_r2 = body.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(28, 14).addBox(0.0F, -4.0F, -1.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, 0.0F, 4.0F, -0.4363F, 0.0F, 0.5236F));

		PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 14).addBox(-4.0F, -3.0F, -3.0F, 8.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -3.0F, 0.0F));

		PartDefinition LArm = body.addOrReplaceChild("LArm", CubeListBuilder.create().texOffs(20, 23).addBox(0.0F, 0.0F, -1.0F, 1.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(5.0F, -1.0F, 0.0F));

		PartDefinition RArm = body.addOrReplaceChild("RArm", CubeListBuilder.create().texOffs(26, 23).addBox(-1.0F, 0.0F, -1.0F, 1.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-5.0F, -1.0F, 0.0F));

		PartDefinition LLeg = body.addOrReplaceChild("LLeg", CubeListBuilder.create().texOffs(8, 28).addBox(-1.0F, 3.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(10, 23).addBox(-1.0F, 5.0F, -2.0F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(3.0F, 0.0F, 0.0F));

		PartDefinition RLeg = body.addOrReplaceChild("RLeg", CubeListBuilder.create().texOffs(0, 23).addBox(-1.0F, 5.0F, -2.0F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(0, 28).addBox(-1.0F, 3.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.0F, 0.0F, 0.0F));

		PartDefinition core = body.addOrReplaceChild("core", CubeListBuilder.create().texOffs(28, 19).addBox(-1.0F, -1.0F, -5.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}
}