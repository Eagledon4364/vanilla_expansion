package com.chris.vanilla_expansion.item.custom;

import com.chris.vanilla_expansion.item.ModArmorMaterials;
import com.google.common.collect.ImmutableMap;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerLevel;
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

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class DragonArmorItem extends Item {
    private static final Map<ArmorMaterial, List<MobEffectInstance>> MATERIAL_TO_EFFECT_MAP =
            (new ImmutableMap.Builder<@NotNull ArmorMaterial, @NotNull List<MobEffectInstance>>())
                    .put(ModArmorMaterials.FIRE_DRAGON_ARMOR_MATERIAL,
                            List.of(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 400, 0, false, false, true)))
                    .put(ModArmorMaterials.WATER_DRAGON_ARMOR_MATERIAL,
                            List.of(new MobEffectInstance(MobEffects.WATER_BREATHING, 400, 0, false, false, true)))
                    .put(ModArmorMaterials.EARTH_DRAGON_ARMOR_MATERIAL,
                            List.of(new MobEffectInstance(MobEffects.HASTE, 400, 0, false, false, true)))
                    .build();

    public DragonArmorItem(Properties properties) {
        super(properties);
    }

    @Override
    public void inventoryTick(@NotNull ItemStack stack, ServerLevel level, @NotNull Entity entity, @Nullable EquipmentSlot slot) {
        if(!level.isClientSide()) {
            if(entity instanceof Player player) {
                if(hasFullSuitOfArmorOn(player)) {
                    evaluateArmorEffects(player);
                }
            }
        }
        super.inventoryTick(stack, level, entity, slot);
    }

    private void evaluateArmorEffects(Player player) {
        for (Map.Entry<ArmorMaterial, List<MobEffectInstance>> entry : MATERIAL_TO_EFFECT_MAP.entrySet()) {
            ArmorMaterial mapArmorMaterial = entry.getKey();
            List<MobEffectInstance> mapStatusEffects = entry.getValue();

            if(hasCorrectArmorOn(mapArmorMaterial, player)) {
                addStatusEffectForMaterial(player, mapStatusEffects);
            }
        }
    }

    private void addStatusEffectForMaterial(Player player, List<MobEffectInstance> mapStatusEffect) {
        boolean needsRefresh = mapStatusEffect.stream().anyMatch(effect
                -> !player.hasEffect(effect.getEffect()) || Objects.requireNonNull(player.getEffect(effect.getEffect())).getDuration() < 220);

        if(needsRefresh) {
            for (MobEffectInstance instance : mapStatusEffect) {
                player.addEffect(new MobEffectInstance(instance));
            }
        }
    }

    private boolean hasFullSuitOfArmorOn(Player player) {
        ItemStack boots = player.getItemBySlot(EquipmentSlot.FEET);
        ItemStack leggings = player.getItemBySlot(EquipmentSlot.LEGS);
        ItemStack chestplate = player.getItemBySlot(EquipmentSlot.CHEST);
        ItemStack helmet = player.getItemBySlot(EquipmentSlot.HEAD);

        return !helmet.isEmpty() && !chestplate.isEmpty()
                && !leggings.isEmpty() && !boots.isEmpty();
    }

    public static boolean hasCorrectArmorOn(ArmorMaterial material, Player player) {
        ItemStack boots = player.getItemBySlot(EquipmentSlot.FEET);
        ItemStack leggings = player.getItemBySlot(EquipmentSlot.LEGS);
        ItemStack chestplate = player.getItemBySlot(EquipmentSlot.CHEST);
        ItemStack helmet = player.getItemBySlot(EquipmentSlot.HEAD);

        Equippable equippableComponentBoots = boots.getComponents().get(DataComponents.EQUIPPABLE);
        Equippable equippableComponentLeggings = leggings.getComponents().get(DataComponents.EQUIPPABLE);
        Equippable equippableComponentBreastplate = chestplate.getComponents().get(DataComponents.EQUIPPABLE);
        Equippable equippableComponentHelmet = helmet.getComponents().get(DataComponents.EQUIPPABLE);

        if (equippableComponentBoots == null || equippableComponentLeggings == null ||
                equippableComponentBreastplate == null || equippableComponentHelmet == null) {
            return false;
        }

        return equippableComponentBoots.assetId().get().equals(material.assetId()) &&
                equippableComponentLeggings.assetId().get().equals(material.assetId()) &&
                equippableComponentBreastplate.assetId().get().equals(material.assetId()) &&
                equippableComponentHelmet.assetId().get().equals(material.assetId());
    }
}