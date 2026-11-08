package com.chris.vanilla_expansion.item.custom;

import com.chris.vanilla_expansion.entity.server.DragonAnimal;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;

import java.util.List;

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
                useStaff(player, serverLevel, context.getClickedPos().above());
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public @NotNull InteractionResult use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand hand) {
        if (!level.isClientSide() && level instanceof ServerLevel serverLevel) {
            useStaff(player, serverLevel, player.blockPosition());
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.SUCCESS;
    }

    public void cycleTargetMode(Player player, ItemStack stack, int direction) {
        if (!(player.level() instanceof ServerLevel serverLevel)) return;

        List<DragonAnimal> nearbyDragons = getNearbyOwnedDragons(player, serverLevel);

        if (nearbyDragons.isEmpty()) {
            player.sendSystemMessage(Component.literal("§cNo tamed dragons found nearby."));
            return;
        }

        CustomData customData = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
        CompoundTag tag = customData.copyTag();

        int currentIndex = tag.getInt("TargetIndex").orElse(-1);

        int totalModes = nearbyDragons.size() + 1;

        int currentShifted = currentIndex + 1;
        int newShifted = (currentShifted + direction) % totalModes;
        if (newShifted < 0) {
            newShifted += totalModes;
        }

        int newIndex = newShifted - 1;

        int finalNewIndex = newIndex;
        CustomData.update(DataComponents.CUSTOM_DATA, stack, t -> t.putInt("TargetIndex", finalNewIndex));

        if (newIndex == -1) {
            player.sendSystemMessage(Component.literal("§a[Dragon Staff Mode] §eAll Dragons (" + nearbyDragons.size() + ")"));
        } else {
            if (newIndex >= nearbyDragons.size()) {
                newIndex = 0;
            }
            DragonAnimal selectedDragon = nearbyDragons.get(newIndex);
            Component dragonName = selectedDragon.hasCustomName() ? selectedDragon.getCustomName() : selectedDragon.getDisplayName();
            player.sendSystemMessage(Component.literal("§a[Dragon Staff Mode] §f(" + (newIndex + 1) + "/" + nearbyDragons.size() + ") ").append(dragonName));
        }
    }

    private void useStaff(Player player, ServerLevel serverLevel, BlockPos targetPos) {
        ItemStack stack = player.getMainHandItem().is(this) ? player.getMainHandItem() : player.getOffhandItem();
        CustomData customData = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
        CompoundTag tag = customData.copyTag();

        List<DragonAnimal> dragons = getNearbyOwnedDragons(player, serverLevel);

        if (dragons.isEmpty()) {
            player.sendSystemMessage(Component.literal("§cNo tamed dragons found nearby to teleport."));
            return;
        }

        int targetIndex = tag.getInt("TargetIndex").orElse(-1);

        if (targetIndex == -1) {
            for (DragonAnimal dragon : dragons) {
                teleportSingleDragon(dragon, serverLevel, targetPos);
            }
            player.sendSystemMessage(Component.literal("§aTeleported all " + dragons.size() + " dragons to you!"));
        } else {
            if (targetIndex >= dragons.size()) {
                targetIndex = 0;
                int finalTargetIndex = targetIndex;
                CustomData.update(DataComponents.CUSTOM_DATA, stack, t -> t.putInt("TargetIndex", finalTargetIndex));
            }

            DragonAnimal targetDragon = dragons.get(targetIndex);
            teleportSingleDragon(targetDragon, serverLevel, targetPos);

            Component dragonName = targetDragon.hasCustomName() ? targetDragon.getCustomName() : targetDragon.getDisplayName();
            player.sendSystemMessage(Component.literal("§aTeleported ").append(dragonName).append(" to you!"));
        }
    }

    private void teleportSingleDragon(DragonAnimal dragon, ServerLevel serverLevel, BlockPos targetPos) {
        dragon.stopRiding();
        if (dragon.isSleeping()) {
            dragon.setSleeping(false);
        }

        dragon.teleportTo(targetPos.getX() + 0.5, targetPos.getY(), targetPos.getZ() + 0.5);
        dragon.getNavigation().stop();
        dragon.setTarget(null);
    }

    private List<DragonAnimal> getNearbyOwnedDragons(Player player, ServerLevel level) {
        AABB searchBox = player.getBoundingBox().inflate(256.0D);
        return level.getEntitiesOfClass(DragonAnimal.class, searchBox,
                dragon -> dragon.isTame() && dragon.isOwnedBy(player));
    }
}