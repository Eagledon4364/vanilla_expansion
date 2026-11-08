package com.chris.vanilla_expansion.mixin;

import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Inventory.class)
public class PlayerInventoryMixin {

    @Redirect(
            method = "<init>",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/core/NonNullList;withSize(ILjava/lang/Object;)Lnet/minecraft/core/NonNullList;")
    )
    private NonNullList<@NotNull ItemStack> expandInventory(int size, Object defaultValue) {
        if (size == 41) {
            return NonNullList.withSize(43, (ItemStack) defaultValue);
        }
        return NonNullList.withSize(size, (ItemStack) defaultValue);
    }
}