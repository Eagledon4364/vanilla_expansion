package com.chris.vanilla_expansion.block.storage.entity;

import com.chris.vanilla_expansion.block.ModBlockEntities;
import com.chris.vanilla_expansion.block.inventory.ImplementedInventory;
import com.chris.vanilla_expansion.block.storage.block.StorageImporter;
import com.chris.vanilla_expansion.block.storage.util.StorageNetworkHelper;
import com.chris.vanilla_expansion.screen.storage.StorageImporterMenu;
import net.fabricmc.fabric.api.transfer.v1.item.ItemStorage;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.storage.StorageView;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.Nullable;

public class StorageImporterBlockEntity extends BlockEntity implements ImplementedInventory, MenuProvider {
    // 18 slots: 0-8 = Filters, 9-17 = Buffer/Inventory slots
    private final NonNullList<@NotNull ItemStack> inventory = NonNullList.withSize(18, ItemStack.EMPTY);
    private int tickCount = 0;

    public StorageImporterBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.STORAGE_IMPORTER_BE, pos, state);
    }

    @Override
    public NonNullList<@NotNull ItemStack> getItems() {
        return this.inventory;
    }

    public static void tick(Level level, BlockPos pos, BlockState state, StorageImporterBlockEntity blockEntity) {
        if (level.isClientSide()) return;
        if (level.hasNeighborSignal(pos)) return;

        blockEntity.tickCount++;
        if (blockEntity.tickCount % 10 != 0) return;

        StorageNetworkHelper.NetworkScanResult scan = StorageNetworkHelper.scanNetwork(level, pos);
        if (scan.controllerPos == null) {
            System.out.println("[StorageImporter] Tick: No controller found in network from pos " + pos);
            return;
        }

        BlockEntity controllerBe = level.getBlockEntity(scan.controllerPos);
        if (!(controllerBe instanceof StorageControllerBlockEntity controller)) {
            System.out.println("[StorageImporter] Tick: Controller entity invalid at " + scan.controllerPos);
            return;
        }

        Direction facing = state.getValue(StorageImporter.FACING);
        BlockPos targetPos = pos.relative(facing);
        BlockEntity targetBe = level.getBlockEntity(targetPos);

        if (targetBe instanceof Container targetContainer) {
            pullFromContainer(controller, targetContainer, blockEntity);
        } else {
            pullFromFabricStorage(level, targetPos, controller, blockEntity);
        }
    }

    private static void pullFromContainer(StorageControllerBlockEntity controller, Container container, StorageImporterBlockEntity importer) {
        for (int i = 0; i < container.getContainerSize(); i++) {
            ItemStack stack = container.getItem(i);
            if (stack.isEmpty()) continue;

            if (!importer.matchesFilter(stack)) {
                System.out.println("[StorageImporter] Item " + stack.getItem().getDescriptionId() + " rejected by filter.");
                continue;
            }

            ItemStack remainder = controller.insertItem(stack.copy());
            int insertedAmount = stack.getCount() - remainder.getCount();

            if (insertedAmount > 0) {
                System.out.println("[StorageImporter] Successfully pulled " + insertedAmount + "x " + stack.getItem().getDescriptionId());
                stack.shrink(insertedAmount);
                container.setChanged();
                break;
            } else {
                System.out.println("[StorageImporter] Controller rejected insertion of " + stack.getItem().getDescriptionId());
            }
        }
    }

    private static void pullFromFabricStorage(Level level, BlockPos targetPos, StorageControllerBlockEntity controller, StorageImporterBlockEntity importer) {
        for (Direction dir : Direction.values()) {
            Storage<ItemVariant> storage = ItemStorage.SIDED.find(level, targetPos, dir);
            if (storage != null) {
                try (Transaction tx = Transaction.openOuter()) {
                    for (StorageView<ItemVariant> view : storage) {
                        if (view.isResourceBlank() || view.getAmount() <= 0) continue;

                        ItemStack stack = view.getResource().toStack((int) Math.min(64, view.getAmount()));

                        if (!importer.matchesFilter(stack)) continue;

                        long extracted = storage.extract(view.getResource(), stack.getCount(), tx);
                        if (extracted > 0) {
                            ItemStack extractedStack = view.getResource().toStack((int) extracted);
                            ItemStack remainder = controller.insertItem(extractedStack);
                            long successfullyMoved = extracted - remainder.getCount();

                            if (successfullyMoved > 0) {
                                if (remainder.getCount() > 0) {
                                    storage.insert(view.getResource(), remainder.getCount(), tx);
                                }
                                tx.commit();
                                System.out.println("[StorageImporter] Successfully pulled fabric storage items.");
                                break;
                            }
                        }
                    }
                }
                break;
            }
        }
    }

    private boolean matchesFilter(ItemStack stack) {
        boolean hasAnyFilter = false;
        for (int i = 0; i < 9; i++) {
            ItemStack filter = this.getItem(i);
            if (!filter.isEmpty()) {
                hasAnyFilter = true;
                if (ItemStack.isSameItemSameComponents(filter, stack)) {
                    return true;
                }
            }
        }
        return !hasAnyFilter;
    }

    @Override
    public boolean canPlaceItem(int slot, @NotNull ItemStack stack) {
        return true;
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
    public @NotNull Component getDisplayName() {
        return Component.literal("Storage Importer");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int containerId, @NotNull Inventory playerInventory, @NotNull Player player) {
        return new StorageImporterMenu(containerId, playerInventory, this);
    }
}