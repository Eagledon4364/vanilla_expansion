package com.chris.vanilla_expansion.item.custom;

import com.chris.vanilla_expansion.util.ModTags;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.component.BlockTransformer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.GameEvent.Context;

import org.jetbrains.annotations.NotNull;

public class PaxelItem extends Item {

    public PaxelItem(ToolMaterial material, float attackDamage, float attackSpeed, Properties properties) {
        super(properties.tool(material, ModTags.Blocks.PAXEL_MINEABLE, attackDamage, attackSpeed, 0.0f));
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        Player player = context.getPlayer();
        BlockState state = level.getBlockState(pos);
        ItemStack stack = context.getItemInHand();

        Holder<BlockTransformer> transformerHolder = stack.get(DataComponents.BLOCK_TRANSFORMER);
        if (transformerHolder != null) {
            BlockTransformer transformer = transformerHolder.value();

            InteractionResult result = transformer.transformBlock(context);
            if (result.consumesAction()) {
                return result;
            }
        }

        if (context.getClickedFace() != Direction.DOWN && state.getBlock() instanceof CampfireBlock && state.getValue(CampfireBlock.LIT)) {
            level.playSound(player, pos, SoundEvents.GENERIC_EXTINGUISH_FIRE, SoundSource.BLOCKS, 0.5F, 2.6F);

            if (!level.isClientSide()) {
                level.levelEvent(null, 1009, pos, 0);
                CampfireBlock.douse(player, level, pos, state);
                BlockState newState = state.setValue(CampfireBlock.LIT, false);
                level.setBlock(pos, newState, 11);
                level.gameEvent(GameEvent.BLOCK_CHANGE, pos, Context.of(player, newState));

                if (player != null) {
                    stack.hurtAndBreak(1, player, context.getHand().asEquipmentSlot());
                }
            }
            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }
}