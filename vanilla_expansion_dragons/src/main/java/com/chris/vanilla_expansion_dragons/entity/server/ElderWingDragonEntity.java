package com.chris.vanilla_expansion_dragons.entity.server;

import com.chris.vanilla_expansion_dragons.config.DragonStatsConfig;
import com.chris.vanilla_expansion_dragons.entity.ModDragons;
import com.chris.vanilla_expansion_dragons.entity.server.util.CosmeticStyle;
import com.chris.vanilla_expansion_dragons.entity.server.util.DragonAnimal;
import com.chris.vanilla_expansion_dragons.entity.server.util.DragonState;
import com.chris.vanilla_expansion_dragons.entity.server.util.Gender;
import com.chris.vanilla_expansion_dragons.menu.DragonMainMenu;
import com.chris.vanilla_expansion_dragons.menu.DragonSkillMenu;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Mth;
import net.minecraft.world.Container;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.Nullable;

import java.util.List;

public class ElderWingDragonEntity extends DragonAnimal {

    public static final List<String> VARIANTS = List.of(
            "black", "blue", "cyan", "green", "magenta", "red", "white", "yellow"
    );

    public static final int COSMETIC_UNLOCK_LEVEL = 10;
    public static final int WINGS_UNLOCK_LEVEL = 20;

    public static final DragonStatsConfig STATS_CONFIG = new DragonStatsConfig(
            24.0f,   // baseHealth
            120.0f,  // baseStamina
            6.0f,    // baseStrength
            0.24,    // baseGroundSpeed
            0.22,    // baseSwimSpeed
            0.85,    // baseFlightSpeed
            2.5f,    // baseBreathDamage
            18.0f    // maxBreathDamage
    );

    public enum StatType {
        HEALTH,
        STAMINA,
        STRENGTH,
        SPEED,
        BREATH
    }

    private static final EntityDataAccessor<@NotNull String> VARIANT =
            SynchedEntityData.defineId(ElderWingDragonEntity.class, EntityDataSerializers.STRING);
    private static final EntityDataAccessor<@NotNull Integer> LEVEL =
            SynchedEntityData.defineId(ElderWingDragonEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<@NotNull Integer> XP =
            SynchedEntityData.defineId(ElderWingDragonEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<@NotNull Integer> SKILL_POINTS =
            SynchedEntityData.defineId(ElderWingDragonEntity.class, EntityDataSerializers.INT);

    private static final EntityDataAccessor<@NotNull Integer> HEALTH_POINTS =
            SynchedEntityData.defineId(ElderWingDragonEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<@NotNull Integer> STAMINA_POINTS =
            SynchedEntityData.defineId(ElderWingDragonEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<@NotNull Integer> STRENGTH_POINTS =
            SynchedEntityData.defineId(ElderWingDragonEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<@NotNull Integer> SPEED_POINTS =
            SynchedEntityData.defineId(ElderWingDragonEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<@NotNull Integer> BREATH_POINTS =
            SynchedEntityData.defineId(ElderWingDragonEntity.class, EntityDataSerializers.INT);

    private static final EntityDataAccessor<@NotNull Float> STAMINA =
            SynchedEntityData.defineId(ElderWingDragonEntity.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<@NotNull Integer> GENDER =
            SynchedEntityData.defineId(ElderWingDragonEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<@NotNull Integer> COSMETIC_STYLE =
            SynchedEntityData.defineId(ElderWingDragonEntity.class, EntityDataSerializers.INT);

    // Client-synced animation states read by ElderWingDragonRenderer
    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState sitAnimationState = new AnimationState();
    public final AnimationState sleepingAnimationState = new AnimationState();
    public final AnimationState flyAnimationState = new AnimationState();
    public final AnimationState hoverAnimationState = new AnimationState();
    public final AnimationState glideAnimationState = new AnimationState();
    public final AnimationState breathAnimationState = new AnimationState();
    public final AnimationState breathAttackAnimationState = new AnimationState();

    public ElderWingDragonEntity(EntityType<? extends @NotNull ElderWingDragonEntity> type, Level level) {
        super(type, level);
        if (!level.isClientSide()) {
            this.randomizeAppearance();
            this.recalculateAttributes(true);
            this.setStamina(this.getMaxStamina());
        }
    }

    private void randomizeAppearance() {
        this.setVariant(VARIANTS.get(this.random.nextInt(VARIANTS.size())));
        this.setGender(this.random.nextBoolean() ? Gender.MALE : Gender.FEMALE);
        this.setCosmeticStyle(CosmeticStyle.NONE);
    }

    @Override
    public @Nullable SpawnGroupData finalizeSpawn(@NotNull ServerLevelAccessor level,
                                                  @NotNull DifficultyInstance difficulty,
                                                  @NotNull EntitySpawnReason spawnReason,
                                                  @Nullable SpawnGroupData spawnData) {
        SpawnGroupData data = super.finalizeSpawn(level, difficulty, spawnReason, spawnData);
        this.randomizeAppearance();
        this.recalculateAttributes(true);
        this.setStamina(this.getMaxStamina());
        return data;
    }

    @Override
    public boolean canFly() {
        return this.hasWingsUnlocked();
    }

    @Override
    public boolean canSwim() {
        return true;
    }

    public boolean hasWingsUnlocked() {
        return this.getDragonLevel() >= WINGS_UNLOCK_LEVEL;
    }

    @Override
    public double getSwimMultiplier() {
        return STATS_CONFIG.baseSwimSpeed / Math.max(0.01, STATS_CONFIG.baseGroundSpeed);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return DragonAnimal.createAttributes()
                .add(Attributes.MAX_HEALTH, STATS_CONFIG.baseHealth)
                .add(Attributes.MOVEMENT_SPEED, STATS_CONFIG.baseGroundSpeed)
                .add(Attributes.FLYING_SPEED, STATS_CONFIG.baseFlightSpeed)
                .add(Attributes.ATTACK_DAMAGE, STATS_CONFIG.baseStrength)
                .add(Attributes.WATER_MOVEMENT_EFFICIENCY, 1.0D)
                .add(Attributes.SAFE_FALL_DISTANCE, 312.0D)
                .add(Attributes.OXYGEN_BONUS, 20.0D)
                .add(Attributes.STEP_HEIGHT, 1.5D)
                .add(Attributes.FALL_DAMAGE_MULTIPLIER, 0.0D)
                .add(Attributes.TEMPT_RANGE, 32.0D)
                .add(Attributes.FOLLOW_RANGE, 64.0D);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.@NotNull Builder builder) {
        super.defineSynchedData(builder);
        builder.define(VARIANT, "black");
        builder.define(LEVEL, 1);
        builder.define(XP, 0);
        builder.define(SKILL_POINTS, 0);

        builder.define(HEALTH_POINTS, 0);
        builder.define(STAMINA_POINTS, 0);
        builder.define(STRENGTH_POINTS, 0);
        builder.define(SPEED_POINTS, 0);
        builder.define(BREATH_POINTS, 0);

        builder.define(STAMINA, STATS_CONFIG.baseStamina);
        builder.define(GENDER, Gender.MALE.ordinal());
        builder.define(COSMETIC_STYLE, CosmeticStyle.NONE.ordinal());
    }

    // --- Variant, Gender & Cosmetics ---

    public String getVariant() {
        String v = this.entityData.get(VARIANT);
        return (v == null || v.isBlank()) ? "black" : v;
    }

    public void setVariant(String variant) {
        String normalized = (variant == null || variant.isBlank()) ? "black" : variant.toLowerCase();
        this.entityData.set(VARIANT, VARIANTS.contains(normalized) ? normalized : "black");
    }

    public Gender getGender() {
        Gender[] values = Gender.values();
        return values[Mth.clamp(this.entityData.get(GENDER), 0, values.length - 1)];
    }

    public void setGender(Gender gender) {
        this.entityData.set(GENDER, gender.ordinal());
    }

    public CosmeticStyle getCosmeticStyle() {
        CosmeticStyle[] values = CosmeticStyle.values();
        return values[Mth.clamp(this.entityData.get(COSMETIC_STYLE), 0, values.length - 1)];
    }

    public void setCosmeticStyle(CosmeticStyle style) {
        this.entityData.set(COSMETIC_STYLE, style.ordinal());
    }

    public CosmeticStyle getActiveCosmeticStyle() {
        return this.getCosmeticStyle();
    }

    public boolean tryUnlockCosmetic(Player player, ItemStack stack) {
        if (this.level().isClientSide()) return false;
        if (this.getDragonLevel() < COSMETIC_UNLOCK_LEVEL) return false;
        if (this.getCosmeticStyle() != CosmeticStyle.NONE) return false;

        CosmeticStyle chosen = null;
        if (stack.is(Items.BONE)) {
            chosen = CosmeticStyle.SPIKES;
        } else if (stack.is(Items.PHANTOM_MEMBRANE)) {
            chosen = CosmeticStyle.FINS;
        }

        if (chosen == null) return false;

        this.setCosmeticStyle(chosen);
        if (!player.getAbilities().instabuild) {
            stack.shrink(1);
        }
        this.addExperience(25);
        return true;
    }

    public boolean tryEquipSaddle(Player player, ItemStack stack) {
        if (!stack.is(Items.SADDLE) || this.isSaddled() || this.isBaby()) {
            return false;
        }
        if (!this.level().isClientSide()) {
            ItemStack saddleCopy = stack.copyWithCount(1);
            this.inventory.setItem(0, saddleCopy);
            this.updateContainerEquipment();
            if (!player.getAbilities().instabuild) {
                stack.shrink(1);
            }
        }
        return true;
    }

    // --- Leveling, Feeding & Skill Points ---

    public int getDragonLevel() {
        return this.entityData.get(LEVEL);
    }

    public void setDragonLevel(int level) {
        this.entityData.set(LEVEL, Mth.clamp(level, 1, STATS_CONFIG.maxLevel));
    }

    public int getDragonXp() {
        return this.entityData.get(XP);
    }

    public void setDragonXp(int xp) {
        this.entityData.set(XP, Math.max(0, xp));
    }

    public int getSkillPoints() {
        return this.entityData.get(SKILL_POINTS);
    }

    public void setSkillPoints(int points) {
        this.entityData.set(SKILL_POINTS, Math.max(0, points));
    }

    public int getXpForNextLevel() {
        return getXpRequiredForLevel(this.getDragonLevel());
    }

    public static int getXpRequiredForLevel(int currentLevel) {
        return 100 + (currentLevel - 1) * 50;
    }

    public void addExperience(int amount) {
        if (this.level().isClientSide() || amount <= 0) return;
        if (this.getDragonLevel() >= STATS_CONFIG.maxLevel) {
            this.setDragonXp(0);
            return;
        }

        int currentXp = this.getDragonXp() + amount;
        int currentLevel = this.getDragonLevel();
        int gainedPoints = this.getSkillPoints();

        while (currentLevel < STATS_CONFIG.maxLevel) {
            int needed = getXpRequiredForLevel(currentLevel);
            if (currentXp >= needed) {
                currentXp -= needed;
                currentLevel++;
                gainedPoints += STATS_CONFIG.pointsPerLevel;
            } else {
                break;
            }
        }

        if (currentLevel >= STATS_CONFIG.maxLevel) {
            currentXp = 0;
        }

        this.setDragonLevel(currentLevel);
        this.setDragonXp(currentXp);
        this.setSkillPoints(gainedPoints);
        this.recalculateAttributes(false);
    }

    @Override
    public @NotNull InteractionResult mobInteract(@NotNull Player player, @NotNull InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (this.isSleeping()) {
            if (!this.level().isClientSide()) {
                this.setSleeping(false);
            }
            return InteractionResult.SUCCESS;
        }

        if (!this.isTame() && this.isFood(stack)) {
            if (!this.level().isClientSide()) {
                if (this.random.nextInt(3) == 0) {
                    this.tame(player);
                    this.navigation.stop();
                    this.setTarget(null);
                    this.level().broadcastEntityEvent(this, (byte) 7);
                } else {
                    this.level().broadcastEntityEvent(this, (byte) 6);
                }
                if (!player.getAbilities().instabuild) {
                    stack.shrink(1);
                }
            }
            return InteractionResult.SUCCESS;
        }

        if (this.isTame() && this.isOwnedBy(player)) {
            // Shift + Right-Click cycles command mode (ROAM -> FOLLOW -> SIT -> GUARD) via DragonAnimal
            if (player.isSecondaryUseActive()) {
                return super.mobInteract(player, hand);
            }

            // Level 10+ Bone / Phantom Membrane cosmetic unlock (mutually exclusive)
            if (this.tryUnlockCosmetic(player, stack)) {
                return InteractionResult.SUCCESS;
            }

            // Equip saddle directly on interaction
            if (this.tryEquipSaddle(player, stack)) {
                return InteractionResult.SUCCESS;
            }

            // Feeding grants XP, heals, and enters love mode for breeding when an adult
            if (this.isFood(stack)) {
                if (!this.level().isClientSide()) {
                    if (this.getHealth() < this.getMaxHealth()) {
                        this.heal(4.0F);
                    }
                    this.addExperience(20);
                    if (!this.isBaby() && this.canFallInLove()) {
                        this.setInLove(player);
                    }
                    if (!player.getAbilities().instabuild) {
                        stack.shrink(1);
                    }
                }
                return InteractionResult.SUCCESS;
            }

            // Right-Click rides if saddled and adult
            if (this.isSaddled() && !this.isBaby()) {
                if (!this.level().isClientSide()) {
                    player.startRiding(this);
                }
                return InteractionResult.SUCCESS;
            }
        }

        return super.mobInteract(player, hand);
    }

    // --- Breeding ---

    @Override
    public boolean canMate(@NotNull Animal otherAnimal) {
        if (otherAnimal == this) return false;
        if (!(otherAnimal instanceof ElderWingDragonEntity partner)) return false;
        if (!this.isTame() || !partner.isTame()) return false;
        if (this.isOrderedToSit() || partner.isOrderedToSit()) return false;
        if (this.getGender() == partner.getGender()) return false;
        return this.isInLove() && partner.isInLove();
    }

    @Override
    public @Nullable AgeableMob getBreedOffspring(@NotNull ServerLevel level, @NotNull AgeableMob partner) {
        ElderWingDragonEntity baby = ModDragons.ELDERWING_DRAGON.create(level, EntitySpawnReason.BREEDING);
        if (baby != null) {
            baby.setBaby(true);
            if (partner instanceof ElderWingDragonEntity otherDragon) {
                String chosenVariant = this.random.nextBoolean() ? this.getVariant() : otherDragon.getVariant();
                baby.setVariant(chosenVariant);
            } else {
                baby.setVariant(this.getVariant());
            }
            baby.setGender(this.random.nextBoolean() ? Gender.MALE : Gender.FEMALE);
            baby.setCosmeticStyle(CosmeticStyle.NONE);
            baby.recalculateAttributes(true);
            baby.setStamina(baby.getMaxStamina());

            if (this.getOwner() instanceof Player ownerPlayer) {
                baby.tame(ownerPlayer);
            }
        }
        return baby;
    }

    public int getStatPoints(StatType stat) {
        return switch (stat) {
            case HEALTH -> this.entityData.get(HEALTH_POINTS);
            case STAMINA -> this.entityData.get(STAMINA_POINTS);
            case STRENGTH -> this.entityData.get(STRENGTH_POINTS);
            case SPEED -> this.entityData.get(SPEED_POINTS);
            case BREATH -> this.entityData.get(BREATH_POINTS);
        };
    }

    private void setStatPoints(StatType stat, int value) {
        int clamped = Math.max(0, value);
        switch (stat) {
            case HEALTH -> this.entityData.set(HEALTH_POINTS, clamped);
            case STAMINA -> this.entityData.set(STAMINA_POINTS, clamped);
            case STRENGTH -> this.entityData.set(STRENGTH_POINTS, clamped);
            case SPEED -> this.entityData.set(SPEED_POINTS, clamped);
            case BREATH -> this.entityData.set(BREATH_POINTS, clamped);
        }
    }

    public boolean allocateSkillPoint(StatType stat) {
        if (this.level().isClientSide()) return false;
        if (this.getSkillPoints() <= 0) return false;

        this.setSkillPoints(this.getSkillPoints() - 1);
        this.setStatPoints(stat, this.getStatPoints(stat) + 1);
        this.recalculateAttributes(false);

        if (stat == StatType.HEALTH) {
            this.heal(4.0F);
        } else if (stat == StatType.STAMINA) {
            this.setStamina(this.getMaxStamina());
        }
        return true;
    }

    public float getMaxStamina() {
        return STATS_CONFIG.baseStamina + (this.getStatPoints(StatType.STAMINA) * 15.0F);
    }

    public float getStamina() {
        return this.entityData.get(STAMINA);
    }

    public void setStamina(float stamina) {
        this.entityData.set(STAMINA, Mth.clamp(stamina, 0.0F, this.getMaxStamina()));
    }

    public float getBreathDamage() {
        int maxPoints = Math.max(1, STATS_CONFIG.maxLevel * STATS_CONFIG.pointsPerLevel);
        float progress = Mth.clamp((float) this.getStatPoints(StatType.BREATH) / maxPoints, 0.0F, 1.0F);
        return Mth.lerp(progress, STATS_CONFIG.baseBreathDamage, STATS_CONFIG.maxBreathDamage);
    }

    public void recalculateAttributes(boolean healToMax) {
        var maxHealthAttr = this.getAttribute(Attributes.MAX_HEALTH);
        if (maxHealthAttr != null) {
            double newMaxHealth = STATS_CONFIG.baseHealth + (this.getStatPoints(StatType.HEALTH) * 4.0D);
            maxHealthAttr.setBaseValue(newMaxHealth);
            if (healToMax) {
                this.setHealth((float) newMaxHealth);
            } else if (this.getHealth() > newMaxHealth) {
                this.setHealth((float) newMaxHealth);
            }
        }

        var moveSpeedAttr = this.getAttribute(Attributes.MOVEMENT_SPEED);
        if (moveSpeedAttr != null) {
            moveSpeedAttr.setBaseValue(STATS_CONFIG.baseGroundSpeed + (this.getStatPoints(StatType.SPEED) * 0.01D));
        }

        var flySpeedAttr = this.getAttribute(Attributes.FLYING_SPEED);
        if (flySpeedAttr != null) {
            flySpeedAttr.setBaseValue(STATS_CONFIG.baseFlightSpeed + (this.getStatPoints(StatType.SPEED) * 0.02D));
        }

        var attackAttr = this.getAttribute(Attributes.ATTACK_DAMAGE);
        if (attackAttr != null) {
            attackAttr.setBaseValue(STATS_CONFIG.baseStrength + (this.getStatPoints(StatType.STRENGTH) * 1.5D));
        }
    }

    // --- Tick, Animations & Stamina ---

    @Override
    public void tick() {
        super.tick();

        if (this.level().isClientSide()) {
            this.updateClientAnimations();
        } else {
            if (!this.isVehicle() && this.getTarget() == null) {
                this.setSprinting(false);
            }

            if (this.isFlying() && this.isVehicle()) {
                float drain = this.isSprinting() ? 0.35F : 0.12F;
                this.setStamina(this.getStamina() - drain);
            } else {
                float regen = this.isSleeping() ? 0.8F : 0.3F;
                this.setStamina(this.getStamina() + regen);
            }
        }
    }

    private void updateClientAnimations() {
        boolean sleeping = this.isSleeping();
        boolean sitting = !sleeping && (this.isInSittingPose() || this.getCommandMode() == CommandMode.SIT);
        boolean flying = !sleeping && !sitting && this.isFlying();
        DragonState state = this.getDragonState();

        boolean groundedIdle = !sleeping && !sitting && !flying
                && this.getDeltaMovement().horizontalDistanceSqr() < 0.0025D;

        this.sleepingAnimationState.animateWhen(sleeping, this.tickCount);
        this.sitAnimationState.animateWhen(sitting, this.tickCount);
        this.hoverAnimationState.animateWhen(flying && state == DragonState.HOVER, this.tickCount);
        this.flyAnimationState.animateWhen(flying && state == DragonState.FLY_VELOCITY, this.tickCount);
        this.glideAnimationState.animateWhen(flying && state == DragonState.GLIDE, this.tickCount);
        this.idleAnimationState.animateWhen(groundedIdle, this.tickCount);
    }

    @Override
    public boolean isFood(@NotNull ItemStack itemStack) {
        return itemStack.is(ItemTags.WOLF_FOOD);
    }

    // --- Save / Load ---

    @Override
    public void addAdditionalSaveData(@NotNull ValueOutput output) {
        super.addAdditionalSaveData(output);
        output.putString("Variant", this.getVariant());
        output.putInt("DragonLevel", this.getDragonLevel());
        output.putInt("DragonXp", this.getDragonXp());
        output.putInt("SkillPoints", this.getSkillPoints());

        output.putInt("HealthPoints", this.getStatPoints(StatType.HEALTH));
        output.putInt("StaminaPoints", this.getStatPoints(StatType.STAMINA));
        output.putInt("StrengthPoints", this.getStatPoints(StatType.STRENGTH));
        output.putInt("SpeedPoints", this.getStatPoints(StatType.SPEED));
        output.putInt("BreathPoints", this.getStatPoints(StatType.BREATH));

        output.putFloat("DragonStamina", this.getStamina());
        output.putInt("DragonGender", this.getGender().ordinal());
        output.putInt("CosmeticStyle", this.getCosmeticStyle().ordinal());
    }

    @Override
    public void readAdditionalSaveData(@NotNull ValueInput input) {
        super.readAdditionalSaveData(input);
        this.setVariant(input.getStringOr("Variant", "black"));
        this.setDragonLevel(input.getIntOr("DragonLevel", 1));
        this.setDragonXp(input.getIntOr("DragonXp", 0));
        this.setSkillPoints(input.getIntOr("SkillPoints", 0));

        this.setStatPoints(StatType.HEALTH, input.getIntOr("HealthPoints", 0));
        this.setStatPoints(StatType.STAMINA, input.getIntOr("StaminaPoints", 0));
        this.setStatPoints(StatType.STRENGTH, input.getIntOr("StrengthPoints", 0));
        this.setStatPoints(StatType.SPEED, input.getIntOr("SpeedPoints", 0));
        this.setStatPoints(StatType.BREATH, input.getIntOr("BreathPoints", 0));

        this.recalculateAttributes(false);
        this.setStamina(input.getFloatOr("DragonStamina", this.getMaxStamina()));
        this.setGender(Gender.values()[Mth.clamp(input.getIntOr("DragonGender", 0), 0, Gender.values().length - 1)]);
        this.setCosmeticStyle(CosmeticStyle.values()[Mth.clamp(input.getIntOr("CosmeticStyle", 0), 0, CosmeticStyle.values().length - 1)]);
    }

    public void openMainMenu(@NotNull Player player) {
        if (!this.level().isClientSide() && (!this.isVehicle() || this.hasPassenger(player)) && this.isTame()) {
            Container mainContainer = this.inventory != null
                    ? this.inventory
                    : new SimpleContainer(DragonMainMenu.DRAGON_CONTAINER_SIZE);

            player.openMenu(new MenuProvider() {
                @Override
                public @NotNull Component getDisplayName() {
                    return ElderWingDragonEntity.this.getDisplayName();
                }

                @Override
                public AbstractContainerMenu createMenu(int id, @NotNull Inventory playerInv, @NotNull Player menuPlayer) {
                    return new DragonMainMenu(
                            id,
                            playerInv,
                            mainContainer,
                            ElderWingDragonEntity.this
                    );
                }
            });
        }
    }

    public void openSkillMenu(@NotNull Player player) {
        if (!this.level().isClientSide() && (!this.isVehicle() || this.hasPassenger(player)) && this.isTame()) {
            player.openMenu(new MenuProvider() {
                @Override
                public @NotNull Component getDisplayName() {
                    return ElderWingDragonEntity.this.getDisplayName();
                }

                @Override
                public AbstractContainerMenu createMenu(int id, @NotNull Inventory playerInv, @NotNull Player menuPlayer) {
                    return new DragonSkillMenu(
                            id,
                            playerInv,
                            ElderWingDragonEntity.this
                    );
                }
            });
        }
    }

    @Override
    public void openCustomInventoryScreen(@NotNull Player player) {
        this.openMainMenu(player);
    }
}