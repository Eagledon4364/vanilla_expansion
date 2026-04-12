package com.chris.vanilla_expansion.item.custom;

import com.chris.vanilla_expansion.component.ModDataComponentTypes;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;


public class MagnetItem extends Item {

    public MagnetItem(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull InteractionResult use(Level level, Player player, @NotNull InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (!level.isClientSide()) {
            boolean currentState = stack.getOrDefault(ModDataComponentTypes.IS_ACTIVE, false);
            stack.set(ModDataComponentTypes.IS_ACTIVE, !currentState);

            ChatFormatting color = !currentState ? ChatFormatting.GREEN : ChatFormatting.RED;
            String stateText = !currentState ? "ENABLED" : "DISABLED";

            // Using your exact overlay message method
            player.sendOverlayMessage(
                    Component.literal("Magnet ").append(Component.literal(stateText).withStyle(color))
            );
        }
        return InteractionResult.SUCCESS;
    }


    @Override
    public void inventoryTick(@NotNull ItemStack itemStack, @NotNull ServerLevel level, @NotNull Entity owner, @Nullable EquipmentSlot slot) {
        super.inventoryTick(itemStack, level, owner, slot);
        if (!level.isClientSide() && owner instanceof Player player) {
            if (itemStack.getOrDefault(ModDataComponentTypes.IS_ACTIVE, false)) {
                // Check if it's in Slot 41 OR if the player is currently holding it
                if (player.getInventory().getItem(41) == itemStack || player.getMainHandItem() == itemStack || player.getOffhandItem() == itemStack) {
                    pullItems(level, player);
                }
            }
        }
    }


    private void pullItems(Level level, Player player) {
        double range = 8.0;
        AABB area = player.getBoundingBox().inflate(range);
        List<ItemEntity> items = level.getEntitiesOfClass(ItemEntity.class, area);

        for (ItemEntity item : items) {
            if (item.isRemoved() || !item.isAlive()) continue;
            Vec3 playerPos = player.position().add(0, 0, 0);
            Vec3 itemPos = item.position();
            Vec3 pullDir = playerPos.subtract(itemPos).normalize();
            double strength = 0.15;
            Vec3 currentVelocity = item.getDeltaMovement();
            item.setDeltaMovement(currentVelocity.add(pullDir.scale(strength)));
        }
    }

    @Override
    public void appendHoverText(ItemStack itemStack, TooltipContext context, TooltipDisplay display, Consumer<Component> builder, TooltipFlag tooltipFlag) {
        super.appendHoverText(itemStack, context, display, builder, tooltipFlag);
        boolean active = itemStack.getOrDefault(ModDataComponentTypes.IS_ACTIVE, false);

        ChatFormatting color = active ? ChatFormatting.GREEN : ChatFormatting.RED;
        String status = active ? "ACTIVE" : "INACTIVE";

        builder.accept(Component.literal("Status: ").withStyle(ChatFormatting.GRAY)
                .append(Component.literal(status).withStyle(color)));

        builder.accept(Component.literal("Right-click to toggle").withStyle(ChatFormatting.DARK_GRAY));

    }


    @Override
    public boolean isFoil(ItemStack stack) {
        // Enchantment glow when active
        return stack.getOrDefault(ModDataComponentTypes.IS_ACTIVE, false);
    }
}