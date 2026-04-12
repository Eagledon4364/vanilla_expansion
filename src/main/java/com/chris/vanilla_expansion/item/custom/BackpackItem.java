package com.chris.vanilla_expansion.item.custom;

import com.chris.vanilla_expansion.block.entity.BackpackBlockEntity;
import com.chris.vanilla_expansion.item.inventory.ItemStackInventory;
import com.chris.vanilla_expansion.item.inventory.ItemStackUpgradeInventory;
import com.chris.vanilla_expansion.screen.backpack.BackpackMenu;
import com.chris.vanilla_expansion.component.ModComponents;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class BackpackItem extends BlockItem {
    public BackpackItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    public int getDefaultMaxStackSize() {
        return 1;
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (!level.isClientSide()) {
            player.openMenu(new MenuProvider() {
                @Override
                public Component getDisplayName() {
                    return Component.translatable("container.vanilla_expansion.backpack");
                }

                @Override
                public AbstractContainerMenu createMenu(int id, Inventory inv, Player p) {
                    ItemStackInventory mainInv = new ItemStackInventory(stack, 54);
                    ItemStackUpgradeInventory upgradeInv = new ItemStackUpgradeInventory(stack, 6);

                    return new BackpackMenu(id, inv, mainInv, upgradeInv);
                }
            });
        }

        return InteractionResult.SUCCESS;
    }

    @Override
    public boolean canFitInsideContainerItems() {
        return false;
    }


    @Override
    protected boolean updateCustomBlockEntityTag(BlockPos pos, Level level, @Nullable Player player, ItemStack stack, BlockState state) {
        boolean superResult = super.updateCustomBlockEntityTag(pos, level, player, stack, state);

        BlockEntity be = level.getBlockEntity(pos);
        if (be instanceof BackpackBlockEntity backpackBe) {
            backpackBe.loadFromItemStack(stack);
            return true;
        }

        return superResult;
    }


    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay display, Consumer<Component> builder, TooltipFlag flag) {
        ItemContainerContents upgrades = stack.get(ModComponents.UPGRADE_DATA);

        if (upgrades != null) {
            if (upgrades.nonEmptyItems().iterator().hasNext()) {
                builder.accept(Component.translatable("tooltip.vanilla_expansion.upgrades").withStyle(ChatFormatting.GOLD));

                for (ItemStackTemplate upgradeStack : upgrades.nonEmptyItems()) {
                    ItemStack realStack = upgradeStack.create();
                    builder.accept(Component.literal(" - ")
                            .append(realStack.getHoverName())
                            .withStyle(ChatFormatting.GRAY));
                }
            }
        }

    }

}