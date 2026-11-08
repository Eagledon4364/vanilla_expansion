package com.chris.vanilla_expansion.block.dragon;

import com.chris.vanilla_expansion.entity.server.DragonAnimal;
import net.minecraft.core.BlockPos;
import net.minecraft.core.UUIDUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class DragonEggBlockEntity extends BlockEntity {
    private int hatchTimeRemaining;
    private final EntityType<? extends @NotNull DragonAnimal> dragonEntityType;
    private @Nullable UUID ownerUuid;

    public DragonEggBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state, EntityType<? extends @NotNull DragonAnimal> dragonEntityType, int initialHatchTime) {
        super(type, pos, state);
        this.dragonEntityType = dragonEntityType;
        this.hatchTimeRemaining = initialHatchTime;
    }

    public void setOwnerUuid(@Nullable UUID ownerUuid) {
        this.ownerUuid = ownerUuid;
        setChanged();
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, DragonEggBlockEntity blockEntity) {
        if (level.isClientSide()) return;

        if (blockEntity.hatchTimeRemaining > 0) {
            blockEntity.hatchTimeRemaining--;
            blockEntity.setChanged();

            if (blockEntity.hatchTimeRemaining <= 0) {
                blockEntity.hatch(level, pos);
            }
        }
    }

    private void hatch(Level level, BlockPos pos) {
        if (dragonEntityType != null && level instanceof ServerLevel serverLevel) {
            DragonAnimal dragon = dragonEntityType.create(level, net.minecraft.world.entity.EntitySpawnReason.EVENT);
            if (dragon != null) {
                dragon.setBaby(true);
                dragon.setPos(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
                dragon.setYRot(level.getRandom().nextFloat() * 360F);

                if (this.ownerUuid != null) {
                    Player player = serverLevel.getPlayerByUUID(this.ownerUuid);
                    if (player != null && dragon instanceof TamableAnimal tamable) {
                        tamable.setOwner(player);
                    }
                }

                level.addFreshEntity(dragon);
            }
        }
        level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
    }

    public void showTimeToPlayer(Player player) {
        int totalSeconds = hatchTimeRemaining / 20;
        int minutes = totalSeconds / 60;
        int seconds = totalSeconds % 60;

        String timeStr = String.format("%d minutes, %d seconds", minutes, seconds);
        player.sendOverlayMessage(Component.literal("Hatching in: " + timeStr));
    }

    @Override
    protected void saveAdditional(@NotNull ValueOutput output) {
        super.saveAdditional(output);
        output.putInt("HatchTimeRemaining", this.hatchTimeRemaining);
        if (this.ownerUuid != null) {
            output.store("OwnerUUID", UUIDUtil.CODEC, this.ownerUuid);
        }
    }

    @Override
    protected void loadAdditional(@NotNull ValueInput input) {
        super.loadAdditional(input);
        this.hatchTimeRemaining = input.getIntOr("HatchTimeRemaining", 24000);
        this.ownerUuid = input.read("OwnerUUID", UUIDUtil.CODEC).orElse(null);
    }
}