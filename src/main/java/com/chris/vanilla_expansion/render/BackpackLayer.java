package com.chris.vanilla_expansion.render;

import com.chris.vanilla_expansion.item.ModItems;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;

public class BackpackLayer extends RenderLayer<@NotNull AvatarRenderState, @NotNull HumanoidModel<@NotNull AvatarRenderState>> {

    // Scratch state instance to prevent allocating new objects during render passes
    private final ItemStackRenderState backpackRenderState = new ItemStackRenderState();

    @SuppressWarnings("unchecked")
    public BackpackLayer(RenderLayerParent<@NotNull AvatarRenderState, ? extends HumanoidModel<@NotNull AvatarRenderState>> parent) {
        super((RenderLayerParent<@NotNull AvatarRenderState, @NotNull HumanoidModel<@NotNull AvatarRenderState>>) parent);
    }

    @Override
    public void submit(@NotNull PoseStack poseStack, @NotNull SubmitNodeCollector submitNodeCollector,
                       int lightCoords, AvatarRenderState state, float yRot, float xRot) {

        if (!(state instanceof BackpackRenderState backpackState)) return;

        ItemStack backpackStack = backpackState.vanillaExpansion$getBackpack();

        if (!backpackStack.isEmpty() && backpackStack.is(ModItems.BACKPACK_ITEM)) {
            var mc = Minecraft.getInstance();

            poseStack.pushPose();
            this.getParentModel().body.translateAndRotate(poseStack);

            poseStack.translate(0.0D, 0.4D, 0.3D);
            poseStack.mulPose(new Matrix4f().rotation(Axis.YP.rotationDegrees(180f)));
            poseStack.mulPose(new Matrix4f().rotation(Axis.ZP.rotationDegrees(180f)));

            mc.getItemModelResolver().updateForTopItem(
                    this.backpackRenderState,
                    backpackStack,
                    ItemDisplayContext.FIXED,
                    mc.level,
                    null,
                    0
            );

            this.backpackRenderState.submit(
                    poseStack,
                    submitNodeCollector,
                    lightCoords,
                    OverlayTexture.NO_OVERLAY,
                    0
            );

            poseStack.popPose();
        }
    }
}