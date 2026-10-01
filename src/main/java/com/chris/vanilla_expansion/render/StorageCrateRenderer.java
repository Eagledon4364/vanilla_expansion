package com.chris.vanilla_expansion.render;

import com.chris.vanilla_expansion.block.storage.block.StorageCrateBlock;
import com.chris.vanilla_expansion.block.storage.entity.StorageCrateBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import it.unimi.dsi.fastutil.HashCommon;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.Nullable;

public class StorageCrateRenderer implements BlockEntityRenderer<@NotNull StorageCrateBlockEntity, @NotNull StorageCrateRenderState> {
    private final ItemModelResolver itemModelResolver;
    private final Font font;

    public StorageCrateRenderer(BlockEntityRendererProvider.Context context) {
        this.itemModelResolver = context.itemModelResolver();
        this.font = context.font();
    }

    @Override
    public StorageCrateRenderState createRenderState() {
        return new StorageCrateRenderState();
    }

    @Override
    public void extractRenderState(StorageCrateBlockEntity blockEntity, StorageCrateRenderState state,
                                   float partialTicks, @NotNull Vec3 cameraPosition,
                                   ModelFeatureRenderer.@Nullable CrumblingOverlay breakProgress) {
        BlockEntityRenderer.super.extractRenderState(blockEntity, state, partialTicks, cameraPosition, breakProgress);

        state.facing = blockEntity.getBlockState().getValue(StorageCrateBlock.FACING);
        state.font = this.font;

        NonNullList<@NotNull ItemStack> items = blockEntity.getItems();
        int seed = HashCommon.long2int(blockEntity.getBlockPos().asLong());
        ItemStack itemstack = items.getFirst();

        if (itemstack.isEmpty() && blockEntity.isLocked()) {
            itemstack = blockEntity.getLockFilter();
        }

        if (!itemstack.isEmpty()) {
            ItemStackRenderState itemStackRenderState = new ItemStackRenderState();
            this.itemModelResolver.updateForTopItem(itemStackRenderState, itemstack, ItemDisplayContext.GUI, blockEntity.level(), blockEntity, seed);
            state.items[0] = itemStackRenderState;
        } else {
            state.items[0] = null;
        }

        int count = items.getFirst().getCount();
        if (count > 0) {
            state.itemCountText = formatCount(count);
        } else if (blockEntity.isLocked()) {
            state.itemCountText = "0";
        } else {
            state.itemCountText = "";
        }
    }

    @Override
    public void submit(StorageCrateRenderState state, @NotNull PoseStack poseStack,
                       @NotNull SubmitNodeCollector submitNodeCollector, @NotNull CameraRenderState camera) {
        float yRot = state.facing.getAxis().isHorizontal() ? -state.facing.toYRot() : 180.0F;

        ItemStackRenderState itemStackRenderState = state.items[0];
        if (itemStackRenderState != null) {
            this.submitItem(state, itemStackRenderState, poseStack, submitNodeCollector, 0, yRot);
            this.submitText(state, poseStack, submitNodeCollector, yRot);
        }
    }

    private void submitItem(
            final StorageCrateRenderState state,
            final ItemStackRenderState itemStackRenderState,
            final PoseStack poseStack,
            final SubmitNodeCollector submitNodeCollector,
            final int slot,
            final float yRot
    ) {
        poseStack.pushPose();
        poseStack.translate(0.5F, 0.5F, 0.5F);

        poseStack.mulPose(new org.joml.Matrix4f().rotation(Axis.YP.rotationDegrees(yRot)));
        poseStack.translate(0.0, 0.0, 0.45);
        poseStack.scale(0.25F, 0.25F, 0.01F);

        AABB box = itemStackRenderState.getModelBoundingBox();
        double centerY = (box.minY + box.maxY) / 2.0;
        poseStack.translate(0.0, -centerY, 0.0);

        itemStackRenderState.submit(poseStack, submitNodeCollector, state.lightCoords, OverlayTexture.NO_OVERLAY, 0);
        poseStack.popPose();
    }

    private void submitText(
            final StorageCrateRenderState state,
            final PoseStack poseStack,
            final SubmitNodeCollector submitNodeCollector,
            final float yRot
    ) {
        if (state.font == null || state.itemCountText.isEmpty()) return;

        poseStack.pushPose();

        poseStack.translate(0.5F, 0.5F, 0.5F);

        poseStack.mulPose(new org.joml.Matrix4f().rotation(Axis.YP.rotationDegrees(yRot)));
        poseStack.translate(0.0F, -0.22F, 0.455F);
        poseStack.scale(0.012F, -0.012F, 0.012F);

        float width = state.font.width(state.itemCountText);
        float xOffset = -width / 2.0F;

        FormattedCharSequence textSequence = Component.literal(state.itemCountText).getVisualOrderText();

        submitNodeCollector.submitText(
                poseStack,
                xOffset,
                0.0F,
                textSequence,
                true,
                Font.DisplayMode.NORMAL,
                state.lightCoords,
                0xFFFFFFFF,
                0,
                0
        );

        poseStack.popPose();
    }

    private String formatCount(int count) {
        if (count >= 1_000_000) {
            return String.format("%.1fM", count / 1_000_000.0F);
        } else if (count >= 10_000) {
            return String.format("%.1fk", count / 1_000.0F);
        }
        return String.valueOf(count);
    }
}