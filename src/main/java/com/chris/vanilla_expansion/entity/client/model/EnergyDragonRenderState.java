package com.chris.vanilla_expansion.entity.client.model;

import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.item.ItemStack;

public class EnergyDragonRenderState extends LivingEntityRenderState {
    public ItemStack saddle = ItemStack.EMPTY;
    public boolean isRidden;
    public boolean isFlying;
    public boolean isSleeping;
    public boolean isSaddled;



    public final AnimationState walkAnimationState = new AnimationState();
    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState flyAnimationState = new AnimationState();
    public final AnimationState hoverAnimationState = new AnimationState();
    public final AnimationState blinkAnimationState = new AnimationState();
    public final AnimationState sleepingAnimationState = new AnimationState();
}
