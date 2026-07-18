package com.chris.vanilla_expansion.mixin;

import com.chris.vanilla_expansion.VanillaExpansion;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.util.ExtraCodecs;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;


import java.util.function.UnaryOperator;

@Mixin(DataComponents.class)
public class DataComponentsMixin {

    @ModifyArg(
            method = "<clinit>",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/core/component/DataComponents;register(Ljava/lang/String;Ljava/util/function/UnaryOperator;)Lnet/minecraft/core/component/DataComponentType;"
            ),
            index = 1
    )
    private static UnaryOperator<DataComponentType.Builder<@NotNull Integer>> modifyMaxStackSize(String name, UnaryOperator<DataComponentType.Builder<@NotNull Integer>> original) {
        if ("max_stack_size".equals(name)) {
            return builder -> builder.persistent(ExtraCodecs.intRange(1, VanillaExpansion.MAX_STACK_SIZE))
                    .networkSynchronized(ByteBufCodecs.VAR_INT);
        }
        return original;
    }
}