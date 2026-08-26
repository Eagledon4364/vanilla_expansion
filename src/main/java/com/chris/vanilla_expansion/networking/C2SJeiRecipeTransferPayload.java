package com.chris.vanilla_expansion.networking;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public record C2SJeiRecipeTransferPayload(
        List<ItemStack> recipeGrid, // 9 elements matching the 3x3 layout (ItemStack.EMPTY for blanks)
        boolean maxTransfer
) implements CustomPacketPayload {

    public static final Type<C2SJeiRecipeTransferPayload> TYPE =
            new Type<>(Identifier.fromNamespaceAndPath("vanilla_expansion", "jei_recipe_transfer"));

    public static final StreamCodec<RegistryFriendlyByteBuf, C2SJeiRecipeTransferPayload> STREAM_CODEC = StreamCodec.composite(
            ItemStack.OPTIONAL_LIST_STREAM_CODEC, C2SJeiRecipeTransferPayload::recipeGrid,
            ByteBufCodecs.BOOL, C2SJeiRecipeTransferPayload::maxTransfer,
            C2SJeiRecipeTransferPayload::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}