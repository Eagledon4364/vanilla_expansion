package com.chris.vanilla_expansion.render;

import com.chris.vanilla_expansion.item.ModItems;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class BackpackLayer extends RenderLayer<AvatarRenderState, HumanoidModel<AvatarRenderState>> {

    private final ItemStackRenderState backpackRenderState = new ItemStackRenderState();

    public BackpackLayer(RenderLayerParent<AvatarRenderState, HumanoidModel<AvatarRenderState>> parent) {
        super(parent);
    }

    @Override
    public void submit(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int lightCoords, AvatarRenderState state, float yRot, float xRot) {
        var mc = Minecraft.getInstance();
        if (mc.player == null) return;

        ItemStack backpackStack = mc.player.getInventory().getItem(42);

        if (!backpackStack.isEmpty() && backpackStack.is(ModItems.BACKPACK_ITEM)) {
            var renderer = mc.getEntityRenderDispatcher().getItemInHandRenderer();

            poseStack.pushPose();
            this.getParentModel().body.translateAndRotate(poseStack);

            poseStack.translate(0.0D, 0.4D, 0.3D);
            poseStack.mulPose(com.mojang.math.Axis.XP.rotationDegrees(180f));


            renderer.renderItem(
                    mc.player,
                    backpackStack,
                    ItemDisplayContext.FIXED,
                    poseStack,
                    submitNodeCollector,
                    lightCoords
            );

            poseStack.popPose();
        }
    }
}