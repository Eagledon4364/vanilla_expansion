package com.chris.vanilla_expansion_dragons.util;

import com.chris.vanilla_expansion_dragons.config.DragonStatsConfig;

import java.util.HashMap;
import java.util.Map;

public class DragonValues {

    // Gender Constants
    public static final int FEMALE = 0;
    public static final int MALE = 1;

    // Decoration & Posture Constants
    public static final int DECORATION_FINS = 0;
    public static final int DECORATION_HORNS = 1;

    public static final int POSTURE_QUADRUPEDAL = 0;
    public static final int POSTURE_BIPEDAL = 1;

    // Texture Array
    public static final int[] TEXTURE_VARIANTS = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

    // Stat Identifiers
    public enum StatType {
        HEALTH, STAMINA, STRENGTH, GROUND_SPEED, FLIGHT_SPEED, SWIM_SPEED, BREATH_DAMAGE
    }

    // Species JSON Config
    private final DragonStatsConfig config;

    // Instance State
    private int gender = FEMALE;
    private int level = 1;
    private int availableStatPoints = 0;

    private int decorations = DECORATION_FINS;
    private int textureVariant = TEXTURE_VARIANTS[0];
    private int posture = POSTURE_QUADRUPEDAL;

    private final Map<StatType, Float> statBoosts = new HashMap<>();

    public DragonValues(DragonStatsConfig config) {
        this.config = config;
    }

    // --- Leveling & Stat Point System ---

    /**
     * Increases level by 1 (up to config.maxLevel) and awards config.pointsPerLevel stat points.
     */
    public void levelUp() {
        if (this.level < config.maxLevel) {
            this.level++;
            this.availableStatPoints += config.pointsPerLevel;
        }
    }

    /**
     * Sets level directly and grants points per level gained above the current level.
     */
    public void setLevel(int newLevel) {
        int clampedLevel = Math.max(1, Math.min(newLevel, config.maxLevel));
        if (clampedLevel > this.level) {
            int levelsGained = clampedLevel - this.level;
            this.availableStatPoints += (levelsGained * config.pointsPerLevel);
        }
        this.level = clampedLevel;
    }

    /**
     * Allows the player to spend a chosen number of stat points on a specific stat.
     *
     * @param stat               The stat to boost
     * @param pointsToSpend      How many points the player wants to allocate
     * @param percentagePerPoint The percentage boost per point (e.g., 0.05f = +5% per point)
     * @return true if the points were successfully spent, false if not enough points
     */
    public boolean investStatPoints(StatType stat, int pointsToSpend, float percentagePerPoint) {
        if (pointsToSpend <= 0 || pointsToSpend > this.availableStatPoints) {
            return false;
        }

        this.availableStatPoints -= pointsToSpend;
        float totalBonus = pointsToSpend * percentagePerPoint;
        float currentBonus = statBoosts.getOrDefault(stat, 0.0f);
        statBoosts.put(stat, currentBonus + totalBonus);
        return true;
    }

    public int getAvailableStatPoints() {
        return availableStatPoints;
    }

    public void setAvailableStatPoints(int points) {
        this.availableStatPoints = Math.max(0, points);
    }

    public float getStatMultiplier(StatType stat) {
        return 1.0f + statBoosts.getOrDefault(stat, 0.0f);
    }

    // --- Level Milestone Checks ---
    public boolean canChangePosture() { return level >= 10; }
    public boolean canFly() { return level >= 20; }
    public boolean hasStatBoost() { return level >= 30; }
    public boolean hasElementEvolution() { return level >= 40; }
    public boolean hasElementAbilities() { return level >= 50; }

    // --- Dynamic Stat Calculations ---

    public float getHealth() {
        return config.baseHealth * getStatMultiplier(StatType.HEALTH);
    }

    public float getStamina() {
        return config.baseStamina * getStatMultiplier(StatType.STAMINA);
    }

    public float getStrength() {
        return config.baseStrength * getStatMultiplier(StatType.STRENGTH);
    }

    public double getGroundSpeed() {
        double speed = config.baseGroundSpeed * getStatMultiplier(StatType.GROUND_SPEED);
        return (posture == POSTURE_QUADRUPEDAL) ? speed * 1.2 : speed;
    }

    public double getSwimSpeed() {
        double speed = config.baseSwimSpeed * getStatMultiplier(StatType.SWIM_SPEED);
        return (posture == POSTURE_QUADRUPEDAL) ? speed * 1.2 : speed;
    }

    public double getFlightSpeed() {
        double speed = config.baseFlightSpeed * getStatMultiplier(StatType.FLIGHT_SPEED);
        double effectiveSpeed = (posture == POSTURE_BIPEDAL) ? speed * 1.2 : speed;
        return Math.min(effectiveSpeed, 2.5); // Caps at 50 blocks/sec
    }

    public float getBreathAttackDamage() {
        float levelProgress = (float) (level - 1) / Math.max(1, (config.maxLevel - 1));
        float baseScaledDamage = config.baseBreathDamage + (levelProgress * (config.maxBreathDamage - config.baseBreathDamage));
        return baseScaledDamage * getStatMultiplier(StatType.BREATH_DAMAGE);
    }

    // --- Getters & Setters ---

    public DragonStatsConfig getConfig() { return config; }

    public int getLevel() { return level; }

    public int getGender() { return gender; }
    public void setGender(int gender) { this.gender = (gender == FEMALE) ? FEMALE : MALE; }
    public boolean isMale() { return this.gender == MALE; }
    public boolean isFemale() { return this.gender == FEMALE; }

    public int getDecorations() { return decorations; }
    public void setDecorations(int decorations) {
        this.decorations = (decorations == DECORATION_HORNS) ? DECORATION_HORNS : DECORATION_FINS;
    }
    public boolean hasFins() { return this.decorations == DECORATION_FINS; }
    public boolean hasHorns() { return this.decorations == DECORATION_HORNS; }

    public int getTextureVariant() { return textureVariant; }
    public void setTextureVariant(int variant) {
        int min = TEXTURE_VARIANTS[0];
        int max = TEXTURE_VARIANTS[TEXTURE_VARIANTS.length - 1];
        this.textureVariant = Math.max(min, Math.min(variant, max));
    }

    public int getPosture() { return posture; }
    public void setPosture(int posture) {
        if (canChangePosture()) {
            this.posture = (posture == POSTURE_BIPEDAL) ? POSTURE_BIPEDAL : POSTURE_QUADRUPEDAL;
        }
    }
}