package com.chris.vanilla_expansion.entity.client.render.gecko;

import com.chris.vanilla_expansion.VanillaExpansion;
import com.chris.vanilla_expansion.entity.client.model.GeckoModel;
import com.chris.vanilla_expansion.entity.server.gecko.GeckoEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.NotNull;

public class GeckoRenderer extends MobRenderer<GeckoEntity, GeckoRenderState, GeckoModel> {

    private static final Identifier TEXTURE_GREEN = Identifier.fromNamespaceAndPath(VanillaExpansion.MOD_ID, "textures/entity/gecko/base_green.png");
    private static final Identifier TEXTURE_WHITE = Identifier.fromNamespaceAndPath(VanillaExpansion.MOD_ID, "textures/entity/gecko/base_white.png");
    private static final Identifier TEXTURE_BROWN = Identifier.fromNamespaceAndPath(VanillaExpansion.MOD_ID, "textures/entity/gecko/base_brown.png");
    private static final Identifier TEXTURE_YELLOW = Identifier.fromNamespaceAndPath(VanillaExpansion.MOD_ID, "textures/entity/gecko/base_yellow.png");

    public GeckoRenderer(EntityRendererProvider.Context context) {
        super(context, new GeckoModel(context.bakeLayer(GeckoModel.LAYER_LOCATION)), 0.4f);
    }

    @Override
    public @NotNull GeckoRenderState createRenderState() {
        return new GeckoRenderState();
    }

    @Override
    public void extractRenderState(@NotNull GeckoEntity entity, @NotNull GeckoRenderState state, float partialTick) {
        super.extractRenderState(entity, state, partialTick);
        state.variant = entity.getVariant();
        state.isSleeping = entity.isSleeping();
        state.isRidingShoulder = entity.isRidingShoulder() || entity.isPassenger();
        state.isSitting = entity.isGeckoSitting();

        state.walkAnimationState.copyFrom(entity.walkAnimationState);
        state.idleAnimationState.copyFrom(entity.idleAnimationState);
        state.sleepStartAnimationState.copyFrom(entity.sleepStartAnimationState);
        state.sleepAnimationState.copyFrom(entity.sleepAnimationState);
        state.sleepStopAnimationState.copyFrom(entity.sleepStopAnimationState);
        state.mountAnimationState.copyFrom(entity.mountAnimationState);
        state.sitAnimationState.copyFrom(entity.sitAnimationState);
    }

    @Override
    public @NotNull Identifier getTextureLocation(@NotNull GeckoRenderState state) {
        return getVariantTexture(state.variant);
    }

    public static Identifier getVariantTexture(int variant) {
        return switch (variant) {
            case 1 -> TEXTURE_WHITE;
            case 2 -> TEXTURE_BROWN;
            case 3 -> TEXTURE_YELLOW;
            default -> TEXTURE_GREEN;
        };
    }
}