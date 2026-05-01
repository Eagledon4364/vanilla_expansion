package com.chris.vanilla_expansion.mixin;

import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {
    @Shadow
    public abstract int getCount();

    @Inject(method = "limitSize", at = @At("HEAD"), cancellable = true)
    private void disableSizeCapping(int maxStackSize, CallbackInfo ci) {
        if (this.getCount() > 64 && maxStackSize == 64) {
            ci.cancel();
        }
    }

    @Inject(method = "getCount", at = @At("HEAD"), cancellable = true)
    private void forceCount(CallbackInfoReturnable<Integer> cir) {
    }
}