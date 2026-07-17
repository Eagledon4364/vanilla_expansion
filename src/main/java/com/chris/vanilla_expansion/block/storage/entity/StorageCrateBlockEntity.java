package com.chris.vanilla_expansion.block.storage.entity;

import com.chris.vanilla_expansion.block.ModBlockEntities;
import com.chris.vanilla_expansion.block.inventory.ImplementedInventory;
import com.chris.vanilla_expansion.block.storage.block.StorageCrateBlock;
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
    // Inventory size
    private final NonNullList<@NotNull ItemStack> inventory = NonNullList.withSize(1, ItemStack.EMPTY);

    public StorageCrateBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.STORAGE_CRATE_BE, pos, state);
    }

    // LOAD AND SAVE METHODS
    @Override
    protected void loadAdditional(@NotNull ValueInput input) {
        super.loadAdditional(input);
        this.inventory.set(0, input.read("StoredItem", ItemStack.CODEC).orElse(ItemStack.EMPTY));
        if (this.level != null && this.level.isClientSide()) {
            this.level.sendBlockUpdated(this.worldPosition, getBlockState(), getBlockState(), 3);
        }
    }

    @Override
    protected void saveAdditional(@NotNull ValueOutput output) {
        super.saveAdditional(output);
        ItemStack stack = this.inventory.getFirst();
        if (!stack.isEmpty()) {
            output.store("StoredItem", ItemStack.CODEC, stack);
        }
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
        output.discard("StoredItem");
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

    @Override
    public @Nullable Object getRenderData() {
        return this.getItem(0).getItem();
    }

    @Override
    public NonNullList<@NotNull ItemStack> getItems() {
        return this.inventory;
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
    @Override
    public int getMaxStackSize() {
        return 2048;
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