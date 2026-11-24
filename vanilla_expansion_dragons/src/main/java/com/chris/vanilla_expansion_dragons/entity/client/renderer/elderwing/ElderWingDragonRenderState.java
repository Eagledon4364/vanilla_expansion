package com.chris.vanilla_expansion_dragons.entity.client.renderer.elderwing;

import com.chris.vanilla_expansion_dragons.entity.server.util.CosmeticStyle;
import com.chris.vanilla_expansion_dragons.entity.server.util.Gender;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.item.ItemStack;

import java.util.HashSet;
import java.util.Set;

public class ElderWingDragonRenderState extends LivingEntityRenderState {
    public ItemStack saddle = ItemStack.EMPTY;
    public String variant = "black";
    public Gender gender = Gender.MALE;
    public CosmeticStyle cosmeticStyle = CosmeticStyle.NONE;
    public Set<String> unlockedParts = new HashSet<>();

    public float flightPitch;
    public float flightRoll;
    public float flightYawLean;

    public boolean isRidden;
    public boolean isRunning;
    public boolean canFly;
    public boolean isFlying;
    public boolean isSleeping;
    public boolean isSaddled;
    public boolean isSitting;
    public boolean hasWingsUnlocked;
    public boolean isThrustingFlight;


    public final AnimationState walkAnimationState = new AnimationState();
    public final AnimationState runAnimationState = new AnimationState();
    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState flyAnimationState = new AnimationState();
    public final AnimationState hoverAnimationState = new AnimationState();
    public final AnimationState glideAnimationState = new AnimationState();
    public final AnimationState sleepingAnimationState = new AnimationState();
    public final AnimationState sitAnimationState = new AnimationState();
    public final AnimationState breathAnimationState = new AnimationState();
    public final AnimationState breathAttackAnimationState = new AnimationState();
}