package com.chris.vanilla_expansion.block.entity;

import com.chris.vanilla_expansion.block.ModBlockEntities;
import com.chris.vanilla_expansion.block.inventory.ImplementedInventory;
import com.chris.vanilla_expansion.screen.ToolCraftingStationMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.TagValueOutput;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.Nullable;

public class ToolCraftingStationBLockEntity extends BlockEntity implements ImplementedInventory, MenuProvider {

    private final NonNullList<ItemStack> inventory = NonNullList.withSize(3, ItemStack.EMPTY);

    public ToolCraftingStationBLockEntity(BlockPos worldPosition, BlockState blockState) {
        super(ModBlockEntities.TOOL_CRAFTING_STATION_BE, worldPosition, blockState);
    }

    @Override
    public NonNullList<ItemStack> getItems() {
        return inventory;
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Tool Crafting Station");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int containerId, Inventory playerInventory, Player player) {
        return new ToolCraftingStationMenu(containerId, playerInventory, this);
    }

    @Override
    protected void loadAdditional(@NotNull ValueInput input) {
        super.loadAdditional(input);
        ItemContainerContents contents = input.read("Items", ItemContainerContents.CODEC).orElse(ItemContainerContents.EMPTY);
        contents.copyInto(this.inventory);
    }

    @Override
    protected void saveAdditional(@NotNull ValueOutput output) {
        super.saveAdditional(output);
        output.store("Items", ItemContainerContents.CODEC, ItemContainerContents.fromItems(this.inventory));
    }

    @Override
    protected void applyImplicitComponents(@NotNull DataComponentGetter components) {
        super.applyImplicitComponents(components);
        ItemContainerContents contents = components.get(DataComponents.CONTAINER);
        if (contents != null) {
            contents.copyInto(this.inventory);
        }
    }

    @Override
    protected void collectImplicitComponents(DataComponentMap.@NotNull Builder builder) {
        super.collectImplicitComponents(builder);
        builder.set(DataComponents.CONTAINER, ItemContainerContents.fromItems(this.inventory));
    }

    @Override
    public void removeComponentsFromTag(@NotNull ValueOutput output) {
        super.removeComponentsFromTag(output);
        output.discard("Items");
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public @NotNull CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        var output = TagValueOutput.createWithContext(ProblemReporter.DISCARDING, registries);
        this.saveAdditional(output);
        return output.buildResult();
    }

    @Override
    public void setChanged() {
        super.setChanged();
        if (this.level != null && !this.level.isClientSide()) {
            this.level.sendBlockUpdated(this.worldPosition, getBlockState(), getBlockState(), 3);
        }
    }
}