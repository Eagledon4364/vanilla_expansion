package com.chris.vanilla_expansion.mixin;

import com.chris.vanilla_expansion.VanillaExpansion;
import com.mojang.serialization.DataResult;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemStack.class)
public class ItemStackValidationMixin {

    @Inject(method = "validateStrict", at = @At("HEAD"), cancellable = true)
    private static void bypassStrictValidation(ItemStack itemStack, CallbackInfoReturnable<DataResult<ItemStack>> cir) {
        if (itemStack.getCount() <= VanillaExpansion.MAX_STACK_SIZE) {
            cir.setReturnValue(DataResult.success(itemStack));
        }
    }
}