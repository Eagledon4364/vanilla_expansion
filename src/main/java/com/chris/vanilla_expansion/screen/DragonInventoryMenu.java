package com.chris.vanilla_expansion.screen;

import com.chris.vanilla_expansion.entity.server.DragonAnimal;
import com.chris.vanilla_expansion.item.ModItems;
import net.minecraft.resources.Identifier;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractMountInventoryMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DragonInventoryMenu extends AbstractMountInventoryMenu {


    private final Container dragonInventory;
    private final DragonAnimal dragon;
    private static final Identifier SADDLE_SLOT_SPRITE = Identifier.withDefaultNamespace("container/slot/saddle");

    public DragonInventoryMenu(int id, Inventory playerInv) {
        this(id, playerInv, new SimpleContainer(2), null);
    }
    public DragonInventoryMenu(int id, Inventory playerInv, Container dragonInv, final DragonAnimal dragon) {
        super(id, playerInv, dragonInv, dragon);
        this.dragonInventory = dragonInv;
        this.dragon = dragon;

        this.addSlot(new Slot(dragonInv, 0, 8, 18) {
            @Override
            public boolean mayPlace(@NotNull ItemStack stack) {
                return stack.is(Items.SADDLE);
            }

            @Override
            public @NotNull Identifier getNoItemIcon() {
                return SADDLE_SLOT_SPRITE;
            }
        });

        this.addSlot(new Slot(dragonInv, 1, 8, 36));
        for (int row = 0; row < 3; ++row) {
            for (int col = 0; col < 9; ++col) {
                this.addSlot(new Slot(playerInv, col + row * 9 + 9, 8 + col * 18, 84 + row * 18));
            }
        }

        for (int col = 0; col < 9; ++col) {
            this.addSlot(new Slot(playerInv, col, 8 + col * 18, 142));
        }
    }


    @Override
    public @NotNull ItemStack quickMoveStack(@NotNull Player player, int slotIndex) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(slotIndex);

        if (slot != null && slot.hasItem()) {
            ItemStack itemStack2 = slot.getItem();
            itemStack = itemStack2.copy();

            // If the click is in the Dragon's inventory (Slots 0 or 1)
            if (slotIndex < 2) {
                // Try to move it to the Player's inventory (Slots 2 to 37)
                if (!this.moveItemStackTo(itemStack2, 2, 38, true)) {
                    return ItemStack.EMPTY;
                }
            }
            // If the click is in the Player's inventory
            else {
                // Check if the item is a Saddle for Slot 0
                if (itemStack2.is(Items.SADDLE)) {
                    if (!this.moveItemStackTo(itemStack2, 0, 1, false)) {
                        return ItemStack.EMPTY;
                    }
                }
                // Check if it's armor/core for Slot 1 (adjust Items.IRON_HORSE_ARMOR to your needs)
                else if (itemStack2.is(ModItems.ENERGY_CORE)) {
                    if (!this.moveItemStackTo(itemStack2, 1, 2, false)) {
                        return ItemStack.EMPTY;
                    }
                }
                // Logic for regular inventory moving (if slot 0 and 1 are full or item doesn't fit)
                else {
                    return ItemStack.EMPTY;
                }
            }

            if (itemStack2.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (itemStack2.getCount() == itemStack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(player, itemStack2);
        }

        return itemStack;
    }

    @Override
    public boolean stillValid(@NotNull Player player) {
        return this.dragonInventory.stillValid(player) && this.dragon.isAlive() && this.dragon.distanceTo(player) < 8.0F;
    }
    @Override
    protected boolean hasInventoryChanged(@NotNull Container container) {
        return false;
    }

    public DragonAnimal getDragon() {
        return dragon;
    }
    @Override
    public @NotNull MenuType<?> getType() {
        return ModMenus.DRAGON_INVENTORY_MENU;
    }
}
