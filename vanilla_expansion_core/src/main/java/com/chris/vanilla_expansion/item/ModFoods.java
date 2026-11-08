package com.chris.vanilla_expansion.item;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.component.Consumable;
import net.minecraft.world.item.component.Consumables;
import net.minecraft.world.item.consume_effects.ApplyStatusEffectsConsumeEffect;

public class ModFoods {
    public static final FoodProperties BLUEBERRY = new FoodProperties.Builder().nutrition(1).saturationModifier(0.5f).build();
    public static final Consumable BLUEBERRY_CONSUMABLE = Consumables.defaultFood().consumeSeconds(0.8f)
            .onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.SPEED, 10), 0.5f)).build();


}
