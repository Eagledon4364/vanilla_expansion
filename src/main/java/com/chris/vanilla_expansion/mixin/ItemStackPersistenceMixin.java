package com.chris.vanilla_expansion.mixin;

import com.chris.vanilla_expansion.VanillaExpansion;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemStack.class)
public class ItemStackPersistenceMixin {

    @Inject(method = "getCount", at = @At("RETURN"), cancellable = true)
    private void allowHighCountForSaving(CallbackInfoReturnable<Integer> cir) {
        if (cir.getReturnValue() > 99 && cir.getReturnValue() <= VanillaExpansion.MAX_STACK_SIZE) {
        }
    }
}