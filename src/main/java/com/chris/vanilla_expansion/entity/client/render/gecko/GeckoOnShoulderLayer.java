package com.chris.vanilla_expansion.entity.client.render.gecko;

import com.chris.vanilla_expansion.entity.client.model.GeckoModel;
import com.chris.vanilla_expansion.render.GeckoShoulderState;
import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public class GeckoOnShoulderLayer extends RenderLayer<@NotNull AvatarRenderState, @NotNull HumanoidModel<@NotNull AvatarRenderState>> {
    private final GeckoModel model;

    // Scratch render state instance to prevent allocations during render passes
    private final GeckoRenderState geckoRenderState = new GeckoRenderState();

    @SuppressWarnings("unchecked")
    public GeckoOnShoulderLayer(RenderLayerParent<@NotNull AvatarRenderState, ? extends HumanoidModel<@NotNull AvatarRenderState>> parent, EntityModelSet modelSet) {
        super((RenderLayerParent<@NotNull AvatarRenderState, @NotNull HumanoidModel<@NotNull AvatarRenderState>>) parent);
        this.model = new GeckoModel(modelSet.bakeLayer(GeckoModel.LAYER_LOCATION));
    }

    @Override
    public void submit(@NotNull PoseStack poseStack, @NotNull SubmitNodeCollector submitNodeCollector,
                       int lightCoords, AvatarRenderState state, float yRot, float xRot) {
        if (!(state instanceof GeckoShoulderState geckoState)) return;

        geckoState.vanillaExpansion$getLeftShoulderGeckoVariant().ifPresent(variant ->
                renderGecko(poseStack, submitNodeCollector, lightCoords, state, (Integer) variant, true)
        );
        geckoState.vanillaExpansion$getRightShoulderGeckoVariant().ifPresent(variant ->
                renderGecko(poseStack, submitNodeCollector, lightCoords, state, (Integer) variant, false)
        );
    }

    private void renderGecko(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int lightCoords,
                             AvatarRenderState playerState, int variant, boolean isLeft) {
        poseStack.pushPose();

        // Lock transform to player's body part just like the backpack layer
        this.getParentModel().body.translateAndRotate(poseStack);

        // Translate to left/right shoulder position relative to the body
        poseStack.translate(isLeft ? 0.35D : -0.35D, playerState.isCrouching ? -0.15D : 0.0D, -0.15D);

        this.geckoRenderState.ageInTicks = playerState.ageInTicks;
        this.geckoRenderState.isRidingShoulder = true;
        this.geckoRenderState.variant = variant;
        this.geckoRenderState.mountAnimationState.startIfStopped(0);

        Identifier texture = GeckoRenderer.getVariantTexture(variant);

        submitNodeCollector.submitModel(
                this.model,
                this.geckoRenderState,
                poseStack,
                texture,
                lightCoords,
                OverlayTexture.NO_OVERLAY,
                playerState.outlineColor
        );

        poseStack.popPose();
    }
}