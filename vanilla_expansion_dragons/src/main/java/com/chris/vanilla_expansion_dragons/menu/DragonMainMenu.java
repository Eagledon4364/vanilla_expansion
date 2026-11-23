package com.chris.vanilla_expansion_dragons.menu;

import com.chris.vanilla_expansion_dragons.entity.server.util.DragonAnimal;
import com.chris.vanilla_expansion_dragons.entity.server.ElderWingDragonEntity;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.Identifier;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.Nullable;

public class DragonMainMenu extends AbstractContainerMenu {

    public static final int SADDLE_SLOT_INDEX = 0;
    public static final int DRAGON_CONTAINER_SIZE = 1;
    public static final int BUTTON_OPEN_SKILLS = 0;

    private static final Identifier SADDLE_SLOT_SPRITE =
            Identifier.withDefaultNamespace("container/slot/saddle");

    private final @Nullable MenuType<@NotNull DragonMainMenu> menuType;
    private final Container dragonInventory;
    private final @Nullable DragonAnimal dragon;

    private int dragonLevel = 1;
    private int dragonXp = 0;
    private int xpForNextLevel = 100;
    private int skillPoints = 0;

    public DragonMainMenu(int containerId, Inventory playerInv) {
        this(containerId, playerInv, new SimpleContainer(DRAGON_CONTAINER_SIZE), null);
    }

    public DragonMainMenu(int containerId,
                          Inventory playerInv,
                          Container dragonInv,
                          @Nullable DragonAnimal dragon) {
        super(ModDragonMenus.DRAGON_MAIN_MENU, containerId);
        this.menuType = ModDragonMenus.DRAGON_MAIN_MENU;
        this.dragonInventory = dragonInv;
        this.dragon = dragon;

        checkContainerSize(dragonInv, DRAGON_CONTAINER_SIZE);
        dragonInv.startOpen(playerInv.player);

        // Saddle slot (adjust x, y to match dragon_main_gui.png)
        this.addSlot(new Slot(dragonInv, SADDLE_SLOT_INDEX, 44, 76) {
            @Override
            public boolean mayPlace(@NotNull ItemStack stack) {
                return stack.is(Items.SADDLE);
            }

            @Override
            public @NotNull Identifier getNoItemIcon() {
                return SADDLE_SLOT_SPRITE;
            }
        });
        for (int row = 0; row < 3; ++row) {
            for (int col = 0; col < 9; ++col) {
                this.addSlot(new Slot(playerInv, col + row * 9 + 9, 8 + col * 18, 111 + row * 18));
            }
        }

        for (int col = 0; col < 9; ++col) {
            this.addSlot(new Slot(playerInv, col, 8 + col * 18, 169));
        }

        this.addLevelDataSlots();
    }


    private void addLevelDataSlots() {
        this.addDataSlot(new DataSlot() {
            @Override
            public int get() {
                return dragon instanceof ElderWingDragonEntity elder ? elder.getDragonLevel() : dragonLevel;
            }

            @Override
            public void set(int value) {
                dragonLevel = value;
            }
        });

        this.addDataSlot(new DataSlot() {
            @Override
            public int get() {
                return dragon instanceof ElderWingDragonEntity elder ? elder.getDragonXp() : dragonXp;
            }

            @Override
            public void set(int value) {
                dragonXp = value;
            }
        });

        this.addDataSlot(new DataSlot() {
            @Override
            public int get() {
                return dragon instanceof ElderWingDragonEntity elder ? elder.getXpForNextLevel() : xpForNextLevel;
            }

            @Override
            public void set(int value) {
                xpForNextLevel = value;
            }
        });

        this.addDataSlot(new DataSlot() {
            @Override
            public int get() {
                return dragon instanceof ElderWingDragonEntity elder ? elder.getSkillPoints() : skillPoints;
            }

            @Override
            public void set(int value) {
                skillPoints = value;
            }
        });
    }

    private static @Nullable DragonAnimal findDragon(Inventory playerInv, FriendlyByteBuf buf) {
        if (buf == null) return null;
        int entityId = buf.readVarInt();
        Entity entity = playerInv.player.level().getEntity(entityId);
        return entity instanceof DragonAnimal d ? d : null;
    }

    @Override
    public boolean clickMenuButton(@NotNull Player player, int id) {
        if (id == BUTTON_OPEN_SKILLS && this.dragon instanceof ElderWingDragonEntity elder) {
            if (!player.level().isClientSide()) {
                elder.openSkillMenu(player);
            }
            return true;
        }
        return super.clickMenuButton(player, id);
    }

    @Override
    public @NotNull ItemStack quickMoveStack(@NotNull Player player, int index) {
        ItemStack copied = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);

        if (slot.hasItem()) {
            ItemStack slotStack = slot.getItem();
            copied = slotStack.copy();

            int playerInvStart = DRAGON_CONTAINER_SIZE;
            int playerInvEnd = playerInvStart + 27;
            int hotbarEnd = playerInvEnd + 9;

            if (index < DRAGON_CONTAINER_SIZE) {
                if (!this.moveItemStackTo(slotStack, playerInvStart, hotbarEnd, true)) {
                    return ItemStack.EMPTY;
                }
            } else {
                if (slotStack.is(Items.SADDLE)) {
                    if (!this.moveItemStackTo(slotStack, SADDLE_SLOT_INDEX, SADDLE_SLOT_INDEX + 1, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index >= playerInvStart && index < playerInvEnd) {
                    if (!this.moveItemStackTo(slotStack, playerInvEnd, hotbarEnd, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index >= playerInvEnd && index < hotbarEnd) {
                    if (!this.moveItemStackTo(slotStack, playerInvStart, playerInvEnd, false)) {
                        return ItemStack.EMPTY;
                    }
                }
            }

            if (slotStack.isEmpty()) {
                slot.setByPlayer(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (slotStack.getCount() == copied.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(player, slotStack);
        }

        return copied;
    }

    @Override
    public void removed(@NotNull Player player) {
        super.removed(player);
        this.dragonInventory.stopOpen(player);
    }

    @Override
    public boolean stillValid(@NotNull Player player) {
        return this.dragon != null
                && this.dragon.isAlive()
                && this.dragonInventory.stillValid(player)
                && this.dragon.distanceTo(player) < 8.0F;
    }

    @Override
    public @Nullable MenuType<?> getType() {
        return this.menuType;
    }

    public @Nullable DragonAnimal getDragon() {
        return this.dragon;
    }

    public int getDragonLevel() {
        return this.dragonLevel;
    }

    public int getDragonXp() {
        return this.dragonXp;
    }

    public int getXpForNextLevel() {
        return Math.max(1, this.xpForNextLevel);
    }

    public int getSkillPoints() {
        return this.skillPoints;
    }
}