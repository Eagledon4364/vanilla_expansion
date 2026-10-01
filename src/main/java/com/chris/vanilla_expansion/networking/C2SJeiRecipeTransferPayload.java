package com.chris.vanilla_expansion.networking;

import com.chris.vanilla_expansion.screen.storage.CraftingInterfaceMenu;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public record C2SJeiRecipeTransferPayload(List<ItemStack> grid, boolean maxTransfer) implements CustomPacketPayload {
    public static final Type<C2SJeiRecipeTransferPayload> TYPE =
            new Type<>(Identifier.fromNamespaceAndPath("vanilla_expansion", "jei_recipe_transfer"));

    public static final StreamCodec<RegistryFriendlyByteBuf, C2SJeiRecipeTransferPayload> CODEC = StreamCodec.composite(
            ItemStack.OPTIONAL_STREAM_CODEC.apply(ByteBufCodecs.list()),
            C2SJeiRecipeTransferPayload::grid,
            ByteBufCodecs.BOOL,
            C2SJeiRecipeTransferPayload::maxTransfer,
            C2SJeiRecipeTransferPayload::new
    );

    @Override
    public Type<C2SJeiRecipeTransferPayload> type() {
        return TYPE;
    }

    public static void handle(C2SJeiRecipeTransferPayload payload, ServerPlayNetworking.Context context) {
        context.server().execute(() -> {
            if (context.player().containerMenu instanceof CraftingInterfaceMenu menu) {
                menu.handleJeiRecipeTransfer(context.player(), payload.grid(), payload.maxTransfer());
            }
        });
    }
}