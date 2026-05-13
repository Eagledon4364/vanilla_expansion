package com.chris.vanilla_expansion.mixin;

import com.chris.vanilla_expansion.util.slot.BackpackSlot;
import com.chris.vanilla_expansion.util.slot.MagnetSlot;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.RecipeBookMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InventoryMenu.class)
public abstract class InventoryMenuMixin extends RecipeBookMenu {

    public InventoryMenuMixin(MenuType<?> type, int containerId) {
        super(type, containerId);
    }

    @Inject(method = "<init>", at = @At("RETURN"))
    private void addMagnetAndUtilitySlots(Inventory inventory, boolean active, Player player, CallbackInfo ci) {
        AbstractContainerMenuAccessor accessor = (AbstractContainerMenuAccessor) this;

        accessor.callAddSlot(new MagnetSlot(inventory, 41, 77, 26));
        accessor.callAddSlot(new BackpackSlot(inventory, 42, 77, 44));
    }
}