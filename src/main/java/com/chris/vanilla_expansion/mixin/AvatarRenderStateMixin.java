package com.chris.vanilla_expansion.mixin;

import com.chris.vanilla_expansion.render.BackpackRenderState;
import com.chris.vanilla_expansion.render.GeckoShoulderState;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import java.util.Optional;

@Mixin(AvatarRenderState.class)
public class AvatarRenderStateMixin implements BackpackRenderState, GeckoShoulderState {
    @Unique private Optional<Object> vanillaExpansion$leftGecko = Optional.empty();
    @Unique private Optional<Object> vanillaExpansion$rightGecko = Optional.empty();

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

    @Override
    public Optional<Object> vanillaExpansion$getLeftShoulderGeckoVariant() { return this.vanillaExpansion$leftGecko; }
    @Override
    public void vanillaExpansion$setLeftShoulderGeckoVariant(Optional<Object> variant) { this.vanillaExpansion$leftGecko = variant; }

    @Override
    public Optional<Object> vanillaExpansion$getRightShoulderGeckoVariant() { return this.vanillaExpansion$rightGecko; }
    @Override
    public void vanillaExpansion$setRightShoulderGeckoVariant(Optional<Object> variant) { this.vanillaExpansion$rightGecko = variant; }
}