package com.chris.vanilla_expansion.mixin;

import com.chris.vanilla_expansion.util.ArmorCraftingContext;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.ItemCombinerMenu;
import net.minecraft.world.inventory.ItemCombinerMenuSlotDefinition;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AnvilMenu.class)
public abstract class AnvilMenuMixin extends ItemCombinerMenu {

    protected AnvilMenuMixin(@Nullable MenuType<?> menuType, int containerId, Inventory inventory,
                             ContainerLevelAccess access, ItemCombinerMenuSlotDefinition itemInputSlots) {
        super(menuType, containerId, inventory, access, itemInputSlots);
    }

    @Inject(method = "createResult", at = @At("HEAD"))
    private void captureAnvilInputStack(CallbackInfo ci) {
        ItemStack baseItem = this.inputSlots.getItem(0);
        ArmorCraftingContext.setActiveStack(baseItem);
    }

    @Inject(method = "createResult", at = @At("TAIL"))
    private void clearAnvilInputStack(CallbackInfo ci) {
        ArmorCraftingContext.clear();
    }
}