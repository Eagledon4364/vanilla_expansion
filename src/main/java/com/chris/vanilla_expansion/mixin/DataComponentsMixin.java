package com.chris.vanilla_expansion.mixin;

import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import java.util.function.UnaryOperator;

@Mixin(DataComponents.class)
public class DataComponentsMixin {

    @Inject(method = "register", at = @At("HEAD"), cancellable = true)
    private static <T> void overwriteStackLimit(String name, UnaryOperator<DataComponentType.Builder<T>> builderOperator, CallbackInfoReturnable<DataComponentType<T>> cir) {
        if ("max_stack_size".equals(name)) {
            DataComponentType<Integer> overwritten = Registry.register(
                    BuiltInRegistries.DATA_COMPONENT_TYPE,
                    Identifier.withDefaultNamespace(name),
                    DataComponentType.<Integer>builder()
                            .persistent(Codec.INT)
                            .networkSynchronized(net.minecraft.network.codec.ByteBufCodecs.VAR_INT)
                            .build()
            );
            cir.setReturnValue((DataComponentType<T>) overwritten);
        }
    }
}