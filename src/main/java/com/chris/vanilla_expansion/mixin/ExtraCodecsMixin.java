package com.chris.vanilla_expansion.mixin;

import net.minecraft.util.ExtraCodecs;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import com.mojang.serialization.Codec;

@Mixin(ExtraCodecs.class)
public class ExtraCodecsMixin {

    @ModifyVariable(
            method = "intRange(II)Lcom/mojang/serialization/Codec;",
            at = @At("HEAD"),
            argsOnly = true,
            ordinal = 1,
            remap = false
    )
    private static int increaseComponentLimit(int max) {
        if (max == 64 || max == 99) {
            return 128;
        }
        return max;
    }
}