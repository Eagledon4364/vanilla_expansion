package com.chris.vanilla_expansion.block.storage.entity;

import com.chris.vanilla_expansion.VanillaExpansion;
import com.chris.vanilla_expansion.block.ModBlockEntities;
import com.chris.vanilla_expansion.block.inventory.ImplementedInventory;
import com.chris.vanilla_expansion.block.storage.block.StorageCrateBlock;
import com.chris.vanilla_expansion.item.ModItems;
import com.chris.vanilla_expansion.screen.storage.StorageCrateMenu;
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
import net.minecraft.world.entity.ItemOwner;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.TagValueOutput;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.Nullable;

public class StorageCrateBlockEntity extends BlockEntity implements ImplementedInventory, MenuProvider, ItemOwner{
    // Inventory size: slot 0 = stored item, slots 1-3 = upgrade slots
    private final NonNullList<@NotNull ItemStack> inventory = NonNullList.withSize(4, ItemStack.EMPTY);
    public int timeUpgraded = 0;

    public StorageCrateBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.STORAGE_CRATE_BE, pos, state);
    }

    public boolean insertUpgrade(ItemStack upgradeStack) {
        for (int i = 1; i < inventory.size(); i++) {
            if (inventory.get(i).isEmpty()) {
                inventory.set(i, upgradeStack.copyWithCount(1));
                this.setChanged();
                if (this.level != null && !this.level.isClientSide()) {
                    this.level.sendBlockUpdated(this.worldPosition, getBlockState(), getBlockState(), 3);
                }
                return true;
            }
        }
        return false;
    }

    // LOAD AND SAVE METHODS
    @Override
    protected void loadAdditional(@NotNull ValueInput input) {
        super.loadAdditional(input);
        this.timeUpgraded = input.getIntOr("Upgraded", 0);
        ItemContainerContents contents = input.read("Items", ItemContainerContents.CODEC).orElse(ItemContainerContents.EMPTY);
        contents.copyInto(this.inventory);
        if (this.level != null && this.level.isClientSide()) {
            this.level.sendBlockUpdated(this.worldPosition, getBlockState(), getBlockState(), 3);
        }
    }

    @Override
    public boolean canPlaceItem(int slot, @NotNull ItemStack stack) {
        if (slot == 0) {
            ItemStack current = this.getItem(0);
            if (current.isEmpty()) {
                return true;
            }
            return ItemStack.isSameItemSameComponents(current, stack) && current.getCount() < this.getMaxStackSize();
        } else {
            return stack.getItem() == ModItems.STORAGE_BLOCK_UPGRADE && this.getItem(slot).isEmpty();
        }
    }

    @Override
    protected void saveAdditional(@NotNull ValueOutput output) {
        super.saveAdditional(output);
        output.putInt("Upgraded", this.timeUpgraded);
        output.store("Items", ItemContainerContents.CODEC, ItemContainerContents.fromItems(this.inventory));
    }

    // DATA COMPONENTS
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

    // MENU SCREEN CREATOR
    @Override
    public @Nullable AbstractContainerMenu createMenu(int containerId, @NotNull Inventory inventory, @NotNull Player player) {
        return new StorageCrateMenu(containerId, inventory, this);
    }

    // GETTERS AND SETTERS
    @Override
    public @NotNull Vec3 position() {
        return Vec3.atCenterOf(this.getBlockPos());
    }

    @Override
    public float getVisualRotationYInDegrees() {
        return this.getBlockState().getValue(StorageCrateBlock.FACING).getOpposite().toYRot();
    }

    public int getTimesUpgraded() {
        return this.timeUpgraded;
    }

    public int setTimesUpgraded(int count){
        return this.timeUpgraded = count;
    }

    @Override
    public @Nullable Object getRenderData() {
        return this.getItem(0).getItem();
    }

    @Override
    public NonNullList<@NotNull ItemStack> getItems() {
        return this.inventory;
    }

    @Override
    public int getMaxStackSize(@NotNull ItemStack stack) {
        return this.getMaxStackSize();
    }

    @Override
    public void setItem(int slot, @NotNull ItemStack stack) {
        ItemStack oldStack = this.inventory.get(slot);
        this.inventory.set(slot, stack);
        this.setChanged();

        if (this.level != null && !this.level.isClientSide()) {
            if (!ItemStack.isSameItem(oldStack, stack)) {
                this.level.sendBlockUpdated(this.worldPosition, getBlockState(), getBlockState(), 3);
            }
        }
    }

    // CAPACITY IS DERIVED FROM HOW MANY UPGRADE SLOTS (1-3) ARE FILLED
    @Override
    public int getMaxStackSize() {
        return computeCapacity(-1);
    }

    public int getMaxStackSizeExcludingSlot(int excludeIndex) {
        return computeCapacity(excludeIndex);
    }

    private int computeCapacity(int excludeIndex) {
        int filled = 0;
        for (int i = 1; i < inventory.size(); i++) {
            if (i == excludeIndex) continue;
            if (!inventory.get(i).isEmpty()) filled++;
        }
        int capacity = 2048;
        for (int i = 0; i < filled; i++) {
            capacity = Math.min(capacity * 2, VanillaExpansion.MAX_STACK_SIZE);
        }
        return capacity;
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.literal("Storage Crate");
    }

    @Nullable
    @Override
    public Packet<@NotNull ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public @NotNull CompoundTag getUpdateTag(HolderLookup.@NotNull Provider registries) {
        var output = TagValueOutput.createWithContext(
                ProblemReporter.DISCARDING,
                registries
        );
        this.saveAdditional(output);
        return output.buildResult();
    }

    @Override
    public @NotNull Level level() {
        assert this.level != null;
        return this.level;
    }
}