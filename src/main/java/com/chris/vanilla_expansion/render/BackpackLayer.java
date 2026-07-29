package com.chris.vanilla_expansion.render;

import com.chris.vanilla_expansion.item.ModItems;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class BackpackLayer extends RenderLayer<@NotNull AvatarRenderState, @NotNull HumanoidModel<@NotNull AvatarRenderState>> {

    public BackpackLayer(RenderLayerParent<@NotNull AvatarRenderState, @NotNull HumanoidModel<@NotNull AvatarRenderState>> parent) {
        super(parent);
    }

    @Override
    public void submit(@NotNull PoseStack poseStack, @NotNull SubmitNodeCollector submitNodeCollector,
                       int lightCoords, AvatarRenderState state, float yRot, float xRot) {

        // 1. Retrieve the backpack stack extracted specifically for this entity
        if (!(state instanceof BackpackRenderState backpackState)) return;

        ItemStack backpackStack = backpackState.vanillaExpansion$getBackpack();

        if (!backpackStack.isEmpty() && backpackStack.is(ModItems.BACKPACK_ITEM)) {
            var mc = Minecraft.getInstance();
            var renderer = mc.getEntityRenderDispatcher().getItemInHandRenderer();

            poseStack.pushPose();
            this.getParentModel().body.translateAndRotate(poseStack);

            poseStack.translate(0.0D, 0.4D, 0.3D);
            poseStack.mulPose(com.mojang.math.Axis.XP.rotationDegrees(180f));

            // 2. Pass mc.player as the entity context so Minecraft can safely query entity.level()
            assert mc.player != null;
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