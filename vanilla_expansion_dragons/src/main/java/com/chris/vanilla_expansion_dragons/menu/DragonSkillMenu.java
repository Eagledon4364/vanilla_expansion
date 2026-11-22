package com.chris.vanilla_expansion_dragons.menu;

import com.chris.vanilla_expansion.entity.server.DragonAnimal;
import com.chris.vanilla_expansion_dragons.entity.server.ElderWingDragonEntity;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.Nullable;

public class DragonSkillMenu extends AbstractContainerMenu {

    public static final int BUTTON_OPEN_MAIN = 0;
    public static final int BUTTON_UPGRADE_HEALTH = 1;
    public static final int BUTTON_UPGRADE_STAMINA = 2;
    public static final int BUTTON_UPGRADE_STRENGTH = 3;
    public static final int BUTTON_UPGRADE_SPEED = 4;
    public static final int BUTTON_UPGRADE_BREATH = 5;

    private final @Nullable MenuType<@NotNull DragonSkillMenu> menuType;
    private final @Nullable DragonAnimal dragon;

    private int skillPoints = 0;
    private int healthPoints = 0;
    private int staminaPoints = 0;
    private int strengthPoints = 0;
    private int speedPoints = 0;
    private int breathPoints = 0;

    public DragonSkillMenu(int containerId, Inventory playerInv) {
        this(containerId, playerInv, null);
    }

    public DragonSkillMenu(int containerId,
                           Inventory playerInv,
                           @Nullable DragonAnimal dragon) {
        super(ModDragonMenus.DRAGON_SKILL_MENU, containerId);
        this.menuType = ModDragonMenus.DRAGON_SKILL_MENU;
        this.dragon = dragon;

        // Player inventory (3 rows x 9 cols)
        for (int row = 0; row < 3; ++row) {
            for (int col = 0; col < 9; ++col) {
                this.addSlot(new Slot(playerInv, col + row * 9 + 9, 8 + col * 18, 109 + row * 18));
            }
        }

        // Player hotbar (1 row x 9 cols)
        for (int col = 0; col < 9; ++col) {
            this.addSlot(new Slot(playerInv, col, 8 + col * 18, 167));
        }

        this.addSkillDataSlots();
    }

    private void addSkillDataSlots() {
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

        this.addDataSlot(new DataSlot() {
            @Override
            public int get() {
                return dragon instanceof ElderWingDragonEntity elder ? elder.getStatPoints(ElderWingDragonEntity.StatType.HEALTH) : healthPoints;
            }

            @Override
            public void set(int value) {
                healthPoints = value;
            }
        });

        this.addDataSlot(new DataSlot() {
            @Override
            public int get() {
                return dragon instanceof ElderWingDragonEntity elder ? elder.getStatPoints(ElderWingDragonEntity.StatType.STAMINA) : staminaPoints;
            }

            @Override
            public void set(int value) {
                staminaPoints = value;
            }
        });

        this.addDataSlot(new DataSlot() {
            @Override
            public int get() {
                return dragon instanceof ElderWingDragonEntity elder ? elder.getStatPoints(ElderWingDragonEntity.StatType.STRENGTH) : strengthPoints;
            }

            @Override
            public void set(int value) {
                strengthPoints = value;
            }
        });

        this.addDataSlot(new DataSlot() {
            @Override
            public int get() {
                return dragon instanceof ElderWingDragonEntity elder ? elder.getStatPoints(ElderWingDragonEntity.StatType.SPEED) : speedPoints;
            }

            @Override
            public void set(int value) {
                speedPoints = value;
            }
        });

        this.addDataSlot(new DataSlot() {
            @Override
            public int get() {
                return dragon instanceof ElderWingDragonEntity elder ? elder.getStatPoints(ElderWingDragonEntity.StatType.BREATH) : breathPoints;
            }

            @Override
            public void set(int value) {
                breathPoints = value;
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
        if (!(this.dragon instanceof ElderWingDragonEntity elder)) {
            return super.clickMenuButton(player, id);
        }

        if (id == BUTTON_OPEN_MAIN) {
            if (!player.level().isClientSide()) {
                elder.openMainMenu(player);
            }
            return true;
        }

        ElderWingDragonEntity.StatType targetStat = switch (id) {
            case BUTTON_UPGRADE_HEALTH -> ElderWingDragonEntity.StatType.HEALTH;
            case BUTTON_UPGRADE_STAMINA -> ElderWingDragonEntity.StatType.STAMINA;
            case BUTTON_UPGRADE_STRENGTH -> ElderWingDragonEntity.StatType.STRENGTH;
            case BUTTON_UPGRADE_SPEED -> ElderWingDragonEntity.StatType.SPEED;
            case BUTTON_UPGRADE_BREATH -> ElderWingDragonEntity.StatType.BREATH;
            default -> null;
        };

        if (targetStat != null) {
            if (!player.level().isClientSide()) {
                return elder.allocateSkillPoint(targetStat);
            }
            return this.skillPoints > 0;
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

            int playerInvStart = 0;
            int playerInvEnd = 27;
            int hotbarEnd = 36;

            if (index >= playerInvStart && index < playerInvEnd) {
                if (!this.moveItemStackTo(slotStack, playerInvEnd, hotbarEnd, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (index >= playerInvEnd && index < hotbarEnd) {
                if (!this.moveItemStackTo(slotStack, playerInvStart, playerInvEnd, false)) {
                    return ItemStack.EMPTY;
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
    public boolean stillValid(@NotNull Player player) {
        return this.dragon != null
                && this.dragon.isAlive()
                && this.dragon.distanceTo(player) < 8.0F;
    }

    @Override
    public @Nullable MenuType<?> getType() {
        return this.menuType;
    }

    public @Nullable DragonAnimal getDragon() {
        return this.dragon;
    }

    public int getSkillPoints() {
        return this.skillPoints;
    }

    public int getStatPoints(ElderWingDragonEntity.StatType stat) {
        return switch (stat) {
            case HEALTH -> this.healthPoints;
            case STAMINA -> this.staminaPoints;
            case STRENGTH -> this.strengthPoints;
            case SPEED -> this.speedPoints;
            case BREATH -> this.breathPoints;
        };
    }
}