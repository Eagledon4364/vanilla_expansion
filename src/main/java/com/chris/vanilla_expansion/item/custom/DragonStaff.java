package com.chris.vanilla_expansion.item.custom;

import com.chris.vanilla_expansion.entity.server.DragonAnimal;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.UUID;

public class DragonStaff extends Item {
    public DragonStaff(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull InteractionResult useOn(@NotNull UseOnContext context) {
        Level level = context.getLevel();
        if (!level.isClientSide() && level instanceof ServerLevel serverLevel) {
            Player player = context.getPlayer();
            if (player != null) {
                teleportDragon(player, serverLevel, context.getClickedPos().above());
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public @NotNull InteractionResult use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand hand) {
        if (!level.isClientSide() && level instanceof ServerLevel serverLevel) {
            teleportDragon(player, serverLevel, player.blockPosition());
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.SUCCESS;
    }

    private void teleportDragon(Player player, ServerLevel serverLevel, BlockPos targetPos) {
        ItemStack stack = player.getMainHandItem().is(this) ? player.getMainHandItem() : player.getOffhandItem();
        CustomData customData = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
        CompoundTag tag = customData.copyTag();

        DragonAnimal targetDragon = null;

        if (tag.contains("BoundDragonUUID")) {
            try {
                UUID dragonUUID = UUID.fromString(String.valueOf(tag.getString("BoundDragonUUID")));
                Entity entity = serverLevel.getEntity(dragonUUID);
                if (entity instanceof DragonAnimal dragon && dragon.isTame() && dragon.isOwnedBy(player)) {
                    targetDragon = dragon;
                }
            } catch (IllegalArgumentException ignored) {}
        }

        if (targetDragon == null) {
            AABB searchBox = player.getBoundingBox().inflate(128.0D);
            List<DragonAnimal> dragons = serverLevel.getEntitiesOfClass(DragonAnimal.class, searchBox,
                    dragon -> dragon.isTame() && dragon.isOwnedBy(player));

            if (!dragons.isEmpty()) {
                targetDragon = dragons.stream()
                        .min((d1, d2) -> Double.compare(d1.distanceToSqr(player), d2.distanceToSqr(player)))
                        .orElse(null);

                if (targetDragon != null) {
                    tag.putString("BoundDragonUUID", targetDragon.getUUID().toString());
                    DragonAnimal finalTargetDragon = targetDragon;
                    CustomData.update(DataComponents.CUSTOM_DATA, stack, t -> t.putString("BoundDragonUUID", finalTargetDragon.getUUID().toString()));
                }
            }
        }

        if (targetDragon != null) {
            targetDragon.stopRiding();
            if (targetDragon.isSleeping()) {
                targetDragon.setSleeping(false);
            }

            targetDragon.teleportTo(targetPos.getX() + 0.5, targetPos.getY(), targetPos.getZ() + 0.5);

            targetDragon.getNavigation().stop();
            targetDragon.setTarget(null);

        }
    }
}