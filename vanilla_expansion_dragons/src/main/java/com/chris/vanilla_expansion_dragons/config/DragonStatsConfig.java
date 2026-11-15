package com.chris.vanilla_expansion_dragons.config;

public class DragonStatsConfig {
    public float baseHealth = 20.0f;
    public float baseStamina = 100.0f;
    public float baseStrength = 5.0f;
    public double baseGroundSpeed = 0.50625;
    public double baseSwimSpeed = 0.2;
    public double baseFlightSpeed = 0.5;
    public float baseBreathDamage = 2.0f;
    public float maxBreathDamage = 16.0f;
    public int pointsPerLevel = 2;
    public int maxLevel = 50;

    public DragonStatsConfig() {}

    public DragonStatsConfig(float health, float stamina, float strength,
                             double groundSpeed, double swimSpeed, double flightSpeed,
                             float baseBreath, float maxBreath) {
        this.baseHealth = health;
        this.baseStamina = stamina;
        this.baseStrength = strength;
        this.baseGroundSpeed = groundSpeed;
        this.baseSwimSpeed = swimSpeed;
        this.baseFlightSpeed = flightSpeed;
        this.baseBreathDamage = baseBreath;
        this.maxBreathDamage = maxBreath;
    }
}