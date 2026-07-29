package com.chris.vanilla_expansion.mixin;

import com.chris.vanilla_expansion.render.BackpackRenderState;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(AvatarRenderState.class)
public class AvatarRenderStateMixin implements BackpackRenderState {
    @Unique
    private ItemStack backpackStack = ItemStack.EMPTY;

    @Override
    public ItemStack vanillaExpansion$getBackpack() {
        return this.backpackStack;
    }

    @Override
    public void vanillaExpansion$setBackpack(ItemStack stack) {
        this.backpackStack = stack;
    }
}