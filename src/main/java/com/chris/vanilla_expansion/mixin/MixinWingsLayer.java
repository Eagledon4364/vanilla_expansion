package com.chris.vanilla_expansion.mixin;

import com.chris.vanilla_expansion.item.ModItems;
import net.minecraft.client.renderer.entity.layers.WingsLayer;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.equipment.EquipmentAsset;
import net.minecraft.world.item.equipment.Equippable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Optional;

@Mixin(WingsLayer.class)
public class MixinWingsLayer {

    @Redirect(
            method = "submit",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/item/equipment/Equippable;assetId()Ljava/util/Optional;"
            )
    )
    private Optional<ResourceKey<EquipmentAsset>> redirectAssetId(Equippable equippable,
                                                                  com.mojang.blaze3d.vertex.PoseStack poseStack,
                                                                  net.minecraft.client.renderer.SubmitNodeCollector submitNodeCollector,
                                                                  int lightCoords,
                                                                  HumanoidRenderState state) {

        ItemStack itemStack = state.chestEquipment;

        if (itemStack.is(ModItems.ENERGY_DRAGON_CHESTPLATE)) {
            Equippable vanillaElytraEquippable = Items.ELYTRA.getDefaultInstance().getComponents().get(DataComponents.EQUIPPABLE);
            if (vanillaElytraEquippable != null) {
                return vanillaElytraEquippable.assetId();
            }
        }

        return equippable.assetId();
    }
}