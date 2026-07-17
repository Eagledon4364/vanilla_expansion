package com.chris.vanilla_expansion.render;


import com.chris.vanilla_expansion.block.storage.block.StorageCrateBlock;
import com.chris.vanilla_expansion.block.storage.entity.StorageCrateBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import it.unimi.dsi.fastutil.HashCommon;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.Nullable;

public class StorageCrateRenderer implements BlockEntityRenderer<@NotNull StorageCrateBlockEntity, @NotNull StorageCrateRenderState> {

    private final ItemModelResolver itemModelResolver;
    public StorageCrateRenderer(BlockEntityRendererProvider.Context context) {
        this.itemModelResolver = context.itemModelResolver();
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
        NonNullList<@NotNull ItemStack> items = blockEntity.getItems();
        int seed = HashCommon.long2int(blockEntity.getBlockPos().asLong());
        ItemStack itemstack = items.getFirst();
        if (!itemstack.isEmpty()) {
            ItemStackRenderState itemStackRenderState = new ItemStackRenderState();
            this.itemModelResolver.updateForTopItem(itemStackRenderState, itemstack, ItemDisplayContext.GUI, blockEntity.level(), blockEntity, seed);
            state.items[0] = itemStackRenderState;
        }

    }


    @Override
    public void submit(StorageCrateRenderState state, @NotNull PoseStack poseStack,
                       @NotNull SubmitNodeCollector submitNodeCollector, @NotNull CameraRenderState camera) {
        float yRot = state.facing.getAxis().isHorizontal() ? -state.facing.toYRot() : 180.0F;

        for (int slot = 0; slot < state.items.length; slot++) {
            ItemStackRenderState itemStackRenderState = state.items[slot];
            if (itemStackRenderState != null) {
                this.submitItem(state, itemStackRenderState, poseStack, submitNodeCollector, slot, yRot);
            }
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
        poseStack.mulPose(Axis.YP.rotationDegrees(yRot));

        poseStack.translate(0.0, -0.1, 0.45);

        poseStack.scale(0.25F, 0.25F, 0.01F);
        AABB box = itemStackRenderState.getModelBoundingBox();
        double offsetY = -box.minY;
        poseStack.translate(0.0, offsetY, 0.0);

        itemStackRenderState.submit(poseStack, submitNodeCollector, state.lightCoords, OverlayTexture.NO_OVERLAY, 0);
        poseStack.popPose();

    }
}