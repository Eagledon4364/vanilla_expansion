package com.chris.vanilla_expansion.mixin;

import com.chris.vanilla_expansion.util.ArmorCraftingContext;
import net.minecraft.core.Holder;
import net.minecraft.world.item.enchantment.Enchantment;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Enchantment.class)
public class EnchantmentMixin {

    @Inject(method = "areCompatible", at = @At("HEAD"), cancellable = true)
    private static void bypassEnergyArmorExclusivity(Holder<@NotNull Enchantment> enchantment, Holder<@NotNull Enchantment> other, CallbackInfoReturnable<Boolean> cir) {
        if (ArmorCraftingContext.isEnergyDragonArmorActive()) {
            if (!enchantment.equals(other)) {
                cir.setReturnValue(true);
            }
        }
    }
}