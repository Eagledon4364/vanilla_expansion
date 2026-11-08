package com.chris.vanilla_expansion.mixin;

import com.chris.vanilla_expansion.item.custom.EnergyDragonArmorItem;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import java.util.function.Consumer;

@Mixin(ItemStack.class)
public class ItemStackGliderMixin {

    @Inject(
            method = "hurtAndBreak(ILnet/minecraft/server/level/ServerLevel;Lnet/minecraft/server/level/ServerPlayer;Ljava/util/function/Consumer;)V",
            at = @At("HEAD"),
            cancellable = true
    )
    private void blockVanillaGliderDecay(int amount, ServerLevel level, ServerPlayer player, Consumer<Item> onBreak, CallbackInfo ci) {
        ItemStack self = (ItemStack) (Object) this;

        if (self.getItem() instanceof EnergyDragonArmorItem) {
            if (player != null && player.isFallFlying()) {

                ci.cancel();
            }
        }
    }
}