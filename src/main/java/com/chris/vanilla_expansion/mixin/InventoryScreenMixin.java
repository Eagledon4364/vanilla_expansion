package com.chris.vanilla_expansion.mixin;

import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(InventoryScreen.class)
public abstract class InventoryScreenMixin {

    private static final Identifier CUSTOM_INVENTORY_TEXTURE = Identifier.fromNamespaceAndPath("vanilla_expansion", "textures/gui/custom_inventory.png");

    @Redirect(
            method = "extractBackground",
            at = @At(value = "FIELD", target = "Lnet/minecraft/client/gui/screens/inventory/InventoryScreen;INVENTORY_LOCATION:Lnet/minecraft/resources/Identifier;")
    )
    private Identifier useMyCustomTexture() {
        return CUSTOM_INVENTORY_TEXTURE;
    }
}