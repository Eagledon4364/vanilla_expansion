package com.chris.vanilla_expansion_dragons.behavior;

import com.chris.vanilla_expansion_dragons.util.DragonValues;

public class DragonBehaviorManager {

    private final DragonValues values;
    private final DragonNeeds needs;
    private DragonActivityState currentState = DragonActivityState.IDLE;

    private int decisionCooldown = 0;

    public DragonBehaviorManager(DragonValues values, DragonNeeds needs) {
        this.values = values;
        this.needs = needs;
    }

    /**
     * Evaluates conditions and updates the dragon's current activity state.
     *
     * @param isNight         True if world time is night
     * @param isRaining       True if weather is stormy/raining
     * @param isHealthLow     True if entity health is below 30%
     * @param nearHighGround  True if the dragon is standing on a peak, ledge, or roof
     */
    public void updateBehavior(boolean isNight, boolean isRaining, boolean isHealthLow, boolean nearHighGround) {
        // Prevent re-evaluating every single tick for performance optimization
        if (decisionCooldown > 0) {
            decisionCooldown--;
            return;
        }
        decisionCooldown = 40; // Re-evaluate every 2 seconds (40 ticks)

        // Priority 1: Survival & Defense
        if (isHealthLow) {
            setState(DragonActivityState.FLEEING);
            return;
        }

        // Priority 2: Rest & Sleep
        if (needs.isExhausted() || (isNight && needs.getEnergy() < 70.0f)) {
            setState(DragonActivityState.SLEEPING);
            return;
        }

        // Priority 3: Weather shelter
        if (isRaining && currentState != DragonActivityState.PERCHING) {
            setState(DragonActivityState.RESTING);
            return;
        }

        // Priority 4: Foraging / Hunting for food
        if (needs.isHungry()) {
            setState(DragonActivityState.FORAGING);
            return;
        }

        // Priority 5: Natural Animal Instincts (Perching / Scouting)
        if (nearHighGround && Math.random() < 0.4) {
            setState(DragonActivityState.PERCHING);
            return;
        }

        // Priority 6: Default Ambient Behavior
        if (Math.random() < 0.5) {
            setState(DragonActivityState.WANDERING);
        } else {
            setState(DragonActivityState.IDLE);
        }
    }

    private void setState(DragonActivityState newState) {
        if (this.currentState != newState) {
            this.currentState = newState;
        }
    }

    public DragonActivityState getCurrentState() {
        return currentState;
    }

    public DragonNeeds getNeeds() {
        return needs;
    }
}