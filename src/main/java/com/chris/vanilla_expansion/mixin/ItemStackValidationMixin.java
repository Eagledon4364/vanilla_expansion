package com.chris.vanilla_expansion.mixin;

import com.mojang.serialization.DataResult;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemStack.class)
public class ItemStackValidationMixin {

    @Inject(method = "validateStrict", at = @At("HEAD"), cancellable = true)
    private static void bypassStrictValidation(ItemStack stack, CallbackInfoReturnable<DataResult<ItemStack>> cir) {
        if (stack.getCount() <= 1024) {
            cir.setReturnValue(DataResult.success(stack));
        }
    }
}