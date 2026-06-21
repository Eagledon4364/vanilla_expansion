package com.chris.vanilla_expansion.item.custom;

import com.chris.vanilla_expansion.item.ModArmorMaterials;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.Equippable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class EnergyDragonArmorItem extends Item {

    public static boolean IS_APPLYING_CUSTOM_DAMAGE = false;

    public EnergyDragonArmorItem(Properties properties) {
        super(properties);
    }

    @Override
    public void inventoryTick(@NotNull ItemStack stack, ServerLevel level, @NotNull Entity entity, @Nullable EquipmentSlot slot) {
        if (!level.isClientSide()) {
            if (entity instanceof Player player) {
                if (hasFullSuitOfArmorOn(player) && isWearingEnergyArmor(player)) {
                    applyArmorEffects(player);
                }
                handleElytraFlight(player);
            }
        }
        super.inventoryTick(stack, level, entity, slot);
    }

    private void applyArmorEffects(Player player) {
        applyOrRefreshEffect(player, MobEffects.FIRE_RESISTANCE, 0);
        applyOrRefreshEffect(player, MobEffects.CONDUIT_POWER, 0);
        applyOrRefreshEffect(player, MobEffects.HASTE, 0);
    }

    private void handleElytraFlight(Player player) {
        ItemStack chestplate = player.getItemBySlot(EquipmentSlot.CHEST);

        // Custom 100-tick loop: Deals damage slowly ONLY while actively flying
        if (player.isFallFlying() && player.level() instanceof ServerLevel serverLevel) {
            if (player.tickCount % 100 == 0) {
                IS_APPLYING_CUSTOM_DAMAGE = true;
                chestplate.hurtAndBreak(1, serverLevel, player instanceof net.minecraft.server.level.ServerPlayer sp ? sp : null,
                        brokenItem -> player.onEquippedItemBroken(brokenItem, EquipmentSlot.CHEST)
                );
                IS_APPLYING_CUSTOM_DAMAGE = false;
            }
        }
    }

    private void applyOrRefreshEffect(Player player, Holder<@NotNull MobEffect> effect, int amplifier) {
        MobEffectInstance active = player.getEffect(effect);
        if (active == null || active.getDuration() < 220) {
            player.addEffect(new MobEffectInstance(effect, 400, amplifier, false, false, true));
        }
    }

    private boolean hasFullSuitOfArmorOn(Player player) {
        return !player.getItemBySlot(EquipmentSlot.HEAD).isEmpty() &&
                !player.getItemBySlot(EquipmentSlot.CHEST).isEmpty() &&
                !player.getItemBySlot(EquipmentSlot.LEGS).isEmpty() &&
                !player.getItemBySlot(EquipmentSlot.FEET).isEmpty();
    }

    public static boolean isWearingEnergyArmor(Player player) {
        ArmorMaterial mat = ModArmorMaterials.ENERGY_DRAGON_ARMOR_MATERIAL;

        Equippable boots = player.getItemBySlot(EquipmentSlot.FEET).getComponents().get(DataComponents.EQUIPPABLE);
        Equippable legs = player.getItemBySlot(EquipmentSlot.LEGS).getComponents().get(DataComponents.EQUIPPABLE);
        Equippable chest = player.getItemBySlot(EquipmentSlot.CHEST).getComponents().get(DataComponents.EQUIPPABLE);
        Equippable head = player.getItemBySlot(EquipmentSlot.HEAD).getComponents().get(DataComponents.EQUIPPABLE);

        if (boots == null || legs == null || chest == null || head == null) return false;

        Object bootsAsset = boots.assetId().orElse(null);
        Object legsAsset = legs.assetId().orElse(null);
        Object chestAsset = chest.assetId().orElse(null);
        Object headAsset = head.assetId().orElse(null);
        Object targetAsset = mat.assetId();

        if (bootsAsset == null || legsAsset == null || chestAsset == null || headAsset == null) {
            return false;
        }

        return bootsAsset.equals(targetAsset) &&
                legsAsset.equals(targetAsset) &&
                chestAsset.equals(targetAsset) &&
                headAsset.equals(targetAsset);
    }
}