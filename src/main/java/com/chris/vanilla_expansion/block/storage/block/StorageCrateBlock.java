package com.chris.vanilla_expansion.block.storage.block;


import com.chris.vanilla_expansion.block.storage.entity.StorageCrateBlockEntity;
import com.chris.vanilla_expansion.item.ModItems;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.Nullable;

import java.util.List;

public class StorageCrateBlock extends BaseEntityBlock {
    // CODEC FOR REGISTRATION AND FACING DIRECTION
    public static final EnumProperty<@NotNull Direction> FACING = HorizontalDirectionalBlock.FACING;
    public static final MapCodec<StorageCrateBlock> CODEC = simpleCodec(StorageCrateBlock::new);
    public static final BooleanProperty LOCKED = BooleanProperty.create("locked");

    public StorageCrateBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(LOCKED, false));
    }
    // CODEC
    @Override
    protected @NotNull MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }
    // GETTERS AND SETTERS
    @Override
    public @NotNull RenderShape getRenderShape(@NotNull BlockState state) {
        return RenderShape.MODEL;
    }
    // VISUAL AND REGISTRATION
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, LOCKED);
    }
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState()
                .setValue(FACING, context.getHorizontalDirection().getOpposite())
                .setValue(LOCKED, false);
    }

    @Override
    public @NotNull List<ItemStack> getDrops(@NotNull BlockState state, LootParams.Builder builder) {
        BlockEntity be = builder.getOptionalParameter(LootContextParams.BLOCK_ENTITY);

        if (be instanceof StorageCrateBlockEntity crate) {
            ItemStack itemStack = new ItemStack(this);
            itemStack.set(DataComponents.CONTAINER,
                    ItemContainerContents.fromItems(crate.getItems()));

            return List.of(itemStack);
        }

        return super.getDrops(state, builder);
    }

    @Override
    protected boolean hasAnalogOutputSignal(@NotNull BlockState state) {
        return true;
    }

    @Override
    protected int getAnalogOutputSignal(@NotNull BlockState state, Level level, @NotNull BlockPos pos, @NotNull Direction direction) {
        return AbstractContainerMenu.getRedstoneSignalFromBlockEntity(level.getBlockEntity(pos));
    }

    // USE ON

    @Override
    protected @NotNull InteractionResult useWithoutItem(@NotNull BlockState state,
                                                        @NotNull Level level, @NotNull BlockPos pos,
                                                        @NotNull Player player, @NotNull BlockHitResult hitResult) {
        if (hitResult.getDirection() != state.getValue(FACING)) {
            return InteractionResult.PASS; // not the front face - no special behavior
        }
        if (!level.isClientSide()) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof StorageCrateBlockEntity crate) {
                player.openMenu(crate);
            }
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    protected @NotNull InteractionResult useItemOn(@NotNull ItemStack itemStack,
                                                   BlockState state, @NotNull Level level,
                                                   @NotNull BlockPos pos, @NotNull Player player,
                                                   @NotNull InteractionHand hand, BlockHitResult hitResult) {
        if (hitResult.getDirection() != state.getValue(FACING)) {
            return InteractionResult.PASS; // Not front face
        }

        // 1. KEY INTERACTION (MUST BE FIRST)
        if (itemStack.getItem() == ModItems.KEY) {
            if (!level.isClientSide()) {
                BlockEntity blockEntity = level.getBlockEntity(pos);
                if (blockEntity instanceof StorageCrateBlockEntity crate) {
                    boolean success = crate.toggleLock();

                    if (!success) {
                        player.sendSystemMessage(Component.literal("Cannot lock an empty crate!"));
                        return InteractionResult.FAIL;
                    }
                }
            }
            return InteractionResult.SUCCESS;
        }

        // 2. UPGRADE ITEM LOGIC
        if (itemStack.getItem() == ModItems.STORAGE_BLOCK_UPGRADE) {
            if (player.isSecondaryUseActive()) {
                return InteractionResult.PASS;
            }
            if (!level.isClientSide()) {
                BlockEntity blockEntity = level.getBlockEntity(pos);
                if (blockEntity instanceof StorageCrateBlockEntity crate) {
                    boolean inserted = crate.insertUpgrade(itemStack);
                    if (inserted) {
                        if (!player.isCreative()) {
                            itemStack.shrink(1);
                        }
                    } else {
                        return InteractionResult.FAIL;
                    }
                }
            }
            return InteractionResult.SUCCESS;
        }

        if (itemStack.isEmpty()) {
            return this.useWithoutItem(state, level, pos, player, hitResult); // Open GUI with empty hand
        }

        // 3. RIGHT-CLICK QUICK DEPOSIT LOGIC
        BlockEntity be = level.getBlockEntity(pos);
        if (be instanceof StorageCrateBlockEntity crate) {
            ItemStack current = crate.getItem(0);

            boolean matches;
            if (crate.isLocked()) {
                matches = ItemStack.isSameItemSameComponents(crate.getLockFilter(), itemStack);
            } else {
                matches = current.isEmpty() || ItemStack.isSameItemSameComponents(current, itemStack);
            }

            if (!matches) {
                // Unmatching item: fallback to opening GUI
                return this.useWithoutItem(state, level, pos, player, hitResult);
            }

            if (!level.isClientSide()) {
                int max = crate.getMaxStackSize();
                int currentCount = current.isEmpty() ? 0 : current.getCount();
                int room = max - currentCount;

                if (room > 0) {
                    int wanted = itemStack.getCount();
                    int toDeposit = Math.min(room, wanted);

                    if (current.isEmpty()) {
                        crate.setItem(0, itemStack.copyWithCount(toDeposit));
                    } else {
                        current.grow(toDeposit);
                        crate.updateBlockAndRender(); // Triggers client render sync
                    }
                    itemStack.shrink(toDeposit);
                }
            }
            return InteractionResult.SUCCESS;
        }

        return InteractionResult.SUCCESS;
    }


    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return new StorageCrateBlockEntity(pos, state);
    }


    @Override
    protected void attack(BlockState state, Level level, BlockPos pos, Player player) {
        if (level.isClientSide()) return;

        Direction facing = state.getValue(FACING);
        Direction playerFacing = player.getDirection();

        if (playerFacing != facing.getOpposite()) {
            return;
        }

        BlockEntity be = level.getBlockEntity(pos);
        if (!(be instanceof StorageCrateBlockEntity crate)) return;

        ItemStack stored = crate.getItem(0);
        if (stored.isEmpty()) return;

        if (player.isShiftKeyDown()) {
            // TAKE STACK
            int amount = Math.min(stored.getCount(), stored.getMaxStackSize());
            ItemStack extracted = stored.copyWithCount(amount);

            player.getInventory().placeItemBackInInventory(extracted);
            stored.shrink(amount);

        } else {
            // TAKE ONE
            ItemStack extracted = stored.copyWithCount(1);

            player.getInventory().placeItemBackInInventory(extracted);
            stored.shrink(1);
        }

        crate.updateBlockAndRender(); // Ensures immediate client sync upon punching out items
    }
}