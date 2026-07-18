package com.chris.vanilla_expansion.mixin;

import com.chris.vanilla_expansion.VanillaExpansion;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.util.ExtraCodecs;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemStackTemplate.class)
public class ItemStackTemplateMixin {

    private static final MapCodec<ItemStackTemplate> EXPANDED_MAP_CODEC = RecordCodecBuilder.mapCodec(
            i -> i.group(
                            Item.CODEC.fieldOf("id").forGetter(ItemStackTemplate::item),
                            ExtraCodecs.intRange(1, VanillaExpansion.MAX_STACK_SIZE).optionalFieldOf("count", 1).forGetter(ItemStackTemplate::count),
                            DataComponentPatch.CODEC.optionalFieldOf("components", DataComponentPatch.EMPTY).forGetter(ItemStackTemplate::components)
                    )
                    .apply(i, ItemStackTemplate::new)
    );

    @Unique
    private static final Codec<ItemStackTemplate> EXPANDED_CODEC = Codec.withAlternative(
            EXPANDED_MAP_CODEC.codec(),
            Item.CODEC,
            item -> new ItemStackTemplate((Item)item.value())
    );

    @Inject(method = "validate", at = @At("HEAD"), cancellable = true)
    private void forceValidate(ItemStack result, CallbackInfoReturnable<ItemStack> cir) {
        if (!result.isEmpty() && result.getCount() > 1) {
            cir.setReturnValue(result);
        }
    }

    @Redirect(
            method = "validate",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;validateStrict(Lnet/minecraft/world/item/ItemStack;)Lcom/mojang/serialization/DataResult;")
    )
    private DataResult<ItemStack> bypassStrict(ItemStack stack) {
        return DataResult.success(stack);
    }
}