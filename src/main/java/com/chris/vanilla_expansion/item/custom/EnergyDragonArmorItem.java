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
import org.jetbrains.annotations.Nullable;

public class EnergyDragonArmorItem extends Item {

    public EnergyDragonArmorItem(Properties properties) {
        super(properties);
    }

    @Override
    public void inventoryTick(ItemStack stack, ServerLevel level, Entity entity, @Nullable EquipmentSlot slot) {
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
        applyOrRefreshEffect(player, MobEffects.FIRE_RESISTANCE, 400, 0);

        applyOrRefreshEffect(player, MobEffects.CONDUIT_POWER, 400, 0);

        applyOrRefreshEffect(player, MobEffects.HASTE, 400, 0);
    }

    private void handleElytraFlight(Player player) {
        ItemStack chestplate = player.getItemBySlot(EquipmentSlot.CHEST);
        Equippable equippable = chestplate.getComponents().get(DataComponents.EQUIPPABLE);

        if (equippable != null && equippable.assetId().get().equals(ModArmorMaterials.ENERGY_DRAGON_ARMOR_MATERIAL.assetId())) {
        }
    }

    private void applyOrRefreshEffect(Player player, Holder<MobEffect> effect, int duration, int amplifier) {
        MobEffectInstance active = player.getEffect(effect);
        if (active == null || active.getDuration() < 220) {
            player.addEffect(new MobEffectInstance(effect, duration, amplifier, false, false, true));
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

        return boots.assetId().get().equals(mat.assetId()) &&
                legs.assetId().get().equals(mat.assetId()) &&
                chest.assetId().get().equals(mat.assetId()) &&
                head.assetId().get().equals(mat.assetId());
    }
}