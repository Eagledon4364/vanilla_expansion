package com.chris.vanilla_expansion.item.custom;

import com.chris.vanilla_expansion.util.ModTags;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import net.fabricmc.fabric.api.registry.StrippableBlockRegistry;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.Tool;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.ChestType;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.GameEvent.Context;

import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Optional;

public class PaxelItem extends Item {

    // ===== SHOVEL LOGIC =====
    protected static final Map<Block, BlockState> FLATTENABLES = Maps.newHashMap(
            new ImmutableMap.Builder<Block, BlockState>()
                    .put(Blocks.GRASS_BLOCK, Blocks.DIRT_PATH.defaultBlockState())
                    .put(Blocks.DIRT, Blocks.DIRT_PATH.defaultBlockState())
                    .put(Blocks.PODZOL, Blocks.DIRT_PATH.defaultBlockState())
                    .put(Blocks.COARSE_DIRT, Blocks.DIRT_PATH.defaultBlockState())
                    .put(Blocks.MYCELIUM, Blocks.DIRT_PATH.defaultBlockState())
                    .put(Blocks.ROOTED_DIRT, Blocks.DIRT_PATH.defaultBlockState())
                    .build()
    );

    public PaxelItem(ToolMaterial material, float attackDamage, float attackSpeed, Properties properties) {
        super(properties.tool(material, ModTags.Blocks.PAXEL_MINEABLE, attackDamage, attackSpeed, 0.0f)); // paxel acts like all tools
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        Player player = context.getPlayer();
        BlockState state = level.getBlockState(pos);

        if (!playerHasBlockingItemUseIntent(context)) {
            Optional<BlockState> axeResult = evaluateAxeState(level, pos, player, state);

            if (axeResult.isPresent()) {
                BlockState newState = axeResult.get();
                ItemStack stack = context.getItemInHand();

                if (player instanceof ServerPlayer sp) {
                    CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger(sp, pos, stack);
                }

                level.setBlock(pos, newState, 11);
                level.gameEvent(GameEvent.BLOCK_CHANGE, pos, Context.of(player, newState));

                if (player != null) {
                    stack.hurtAndBreak(1, player, context.getHand().asEquipmentSlot());
                }

                return InteractionResult.SUCCESS;
            }
        }

        if (context.getClickedFace() == Direction.DOWN) {
            return InteractionResult.PASS;
        }

        BlockState flatten = FLATTENABLES.get(state.getBlock());
        BlockState newState = null;

        if (flatten != null && level.getBlockState(pos.above()).isAir()) {
            level.playSound(player, pos, SoundEvents.SHOVEL_FLATTEN, SoundSource.BLOCKS, 1.0F, 1.0F);
            newState = flatten;
        } else if (state.getBlock() instanceof CampfireBlock && state.getValue(CampfireBlock.LIT)) {

            if (!level.isClientSide()) {
                level.levelEvent(null, 1009, pos, 0);
            }

            CampfireBlock.dowse(player, level, pos, state);
            newState = state.setValue(CampfireBlock.LIT, false);
        }

        if (newState != null) {
            if (!level.isClientSide()) {
                level.setBlock(pos, newState, 11);
                level.gameEvent(GameEvent.BLOCK_CHANGE, pos, Context.of(player, newState));

                if (player != null) {
                    context.getItemInHand().hurtAndBreak(1, player, context.getHand().asEquipmentSlot());
                }
            }

            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }

    private Optional<BlockState> evaluateAxeState(Level level, BlockPos pos, @Nullable Player player, BlockState state) {

        // Strip logs
        Optional<BlockState> stripped = Optional.ofNullable(
                StrippableBlockRegistry.getStrippedBlockState(state)
        ).map(block -> block.getBlock().withPropertiesOf(state));

        if (stripped.isPresent()) {
            level.playSound(player, pos, SoundEvents.AXE_STRIP, SoundSource.BLOCKS, 1.0F, 1.0F);
            return stripped;
        }
        Optional<BlockState> scraped = WeatheringCopper.getPrevious(state);
        if (scraped.isPresent()) {
            spawnEffects(level, pos, player, state, SoundEvents.AXE_SCRAPE, 3005);
            return scraped;
        }
        Optional<BlockState> waxOff = Optional.ofNullable(
                HoneycombItem.WAX_OFF_BY_BLOCK.get().get(state.getBlock())
        ).map(block -> block.withPropertiesOf(state));

        if (waxOff.isPresent()) {
            spawnEffects(level, pos, player, state, SoundEvents.AXE_WAX_OFF, 3004);
            return waxOff;
        }

        return Optional.empty();
    }

    private static void spawnEffects(Level level, BlockPos pos, @Nullable Player player,
                                     BlockState state, net.minecraft.sounds.SoundEvent sound, int event) {

        level.playSound(player, pos, sound, SoundSource.BLOCKS, 1.0F, 1.0F);
        level.levelEvent(player, event, pos, 0);

        if (state.getBlock() instanceof ChestBlock && state.getValue(ChestBlock.TYPE) != ChestType.SINGLE) {
            BlockPos other = ChestBlock.getConnectedBlockPos(pos, state);
            level.gameEvent(GameEvent.BLOCK_CHANGE, other, Context.of(player, level.getBlockState(other)));
            level.levelEvent(player, event, other, 0);
        }
    }

    private static boolean playerHasBlockingItemUseIntent(UseOnContext context) {
        Player player = context.getPlayer();
        return context.getHand() == InteractionHand.MAIN_HAND
                && player != null
                && player.getOffhandItem().has(DataComponents.BLOCKS_ATTACKS)
                && !player.isSecondaryUseActive();
    }
}