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
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.client.gui.Font;
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

        // 1. Fallback to lock filter if slot 0 is empty
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

        // 2. Extract Count Text
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
            // Render Item
            this.submitItem(state, itemStackRenderState, poseStack, submitNodeCollector, 0, yRot);

            // Render Count Text
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
        // 1. Position at exact center of the block face (0.5, 0.5, 0.5)
        poseStack.translate(0.5F, 0.5F, 0.5F);
        poseStack.mulPose(Axis.YP.rotationDegrees(yRot));

        // 2. Move outward toward the front face
        poseStack.translate(0.0, 0.0, 0.45);

        // 3. Scale item
        poseStack.scale(0.25F, 0.25F, 0.01F);

        // 4. Center model using bounding box midpoint rather than bottom edge
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

        // 1. Center on Block Face
        poseStack.translate(0.5F, 0.5F, 0.5F);
        poseStack.mulPose(Axis.YP.rotationDegrees(yRot));

        // 2. Position text (Changed Y from -0.30F to -0.22F to lift it off the frame border)
        poseStack.translate(0.0F, -0.22F, 0.455F);

        // 3. Flip Y text matrix so text isn't upside down
        poseStack.scale(0.012F, -0.012F, 0.012F);

        // 4. Center text alignment calculation
        float width = state.font.width(state.itemCountText);
        float xOffset = -width / 2.0F;

        FormattedCharSequence textSequence = Component.literal(state.itemCountText).getVisualOrderText();

        // 5. Submit text
        submitNodeCollector.submitText(
                poseStack,
                xOffset,                  // X offset for centering
                0.0F,                     // Y offset
                textSequence,             // Formatted text sequence
                true,                     // Drop shadow
                Font.DisplayMode.NORMAL,  // Font display mode
                state.lightCoords,        // Light coords
                0xFFFFFFFF,               // Color (ARGB white)
                0,                        // Background color (0 = none)
                0                         // Outline color (0 = none)
        );

        poseStack.popPose();
    }

    // Optional helper to format large quantities cleanly
    private String formatCount(int count) {
        if (count >= 1_000_000) {
            return String.format("%.1fM", count / 1_000_000.0F);
        } else if (count >= 10_000) {
            return String.format("%.1fk", count / 1_000.0F);
        }
        return String.valueOf(count);
    }
}