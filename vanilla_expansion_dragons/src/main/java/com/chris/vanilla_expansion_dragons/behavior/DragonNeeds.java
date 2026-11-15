package com.chris.vanilla_expansion_dragons.behavior;

public class DragonNeeds {

    // Scales from 0.0 (starving/exhausted) to 100.0 (fully satisfied)
    private float hunger = 100.0f;
    private float energy = 100.0f; // Sleepiness/Fatigue

    // Trust scales from -100.0 (hostile/wild) to 100.0 (fully bonded/tamed)
    private float trust = 0.0f;

    /**
     * Ticks down needs over time. Called every second or tick from the entity.
     */
    public void tick() {
        // Hunger slowly decreases (e.g., loses ~0.05 per tick)
        this.hunger = Math.max(0.0f, this.hunger - 0.005f);

        // Energy decreases during wakefulness, drops faster if active
        this.energy = Math.max(0.0f, this.energy - 0.002f);
    }

    // Hunger Management
    public float getHunger() { return hunger; }
    public void feed(float amount) {
        this.hunger = Math.min(100.0f, this.hunger + amount);
    }
    public boolean isHungry() {
        return this.hunger < 30.0f;
    }
    public boolean isStarving() {
        return this.hunger <= 5.0f;
    }

    // Energy / Sleep Management
    public float getEnergy() { return energy; }
    public void rest(float amount) {
        this.energy = Math.min(100.0f, this.energy + amount);
    }
    public boolean isExhausted() {
        return this.energy < 15.0f;
    }

    // Trust & Temperament
    public float getTrust() { return trust; }
    public void modifyTrust(float amount) {
        this.trust = Math.max(-100.0f, Math.min(100.0f, this.trust + amount));
    }
    public boolean isTrusting() {
        return this.trust >= 50.0f;
    }
}