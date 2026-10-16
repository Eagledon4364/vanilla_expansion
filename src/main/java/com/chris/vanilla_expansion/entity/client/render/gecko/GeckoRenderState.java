package com.chris.vanilla_expansion.entity.client.render.gecko;


import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.AnimationState;

public class GeckoRenderState extends LivingEntityRenderState {
    public int variant;
    public boolean isSleeping;
    public boolean isRidingShoulder;

    public final AnimationState walkAnimationState = new AnimationState();
    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState sleepStartAnimationState = new AnimationState();
    public final AnimationState sleepAnimationState = new AnimationState();
    public final AnimationState sleepStopAnimationState = new AnimationState();
    public final AnimationState mountAnimationState = new AnimationState();
}