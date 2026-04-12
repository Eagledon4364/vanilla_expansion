package com.chris.vanilla_expansion.entity.server.dragons;

import com.chris.vanilla_expansion.entity.ModEntities;
import com.chris.vanilla_expansion.entity.goals.DragonSleepGoal;
import com.chris.vanilla_expansion.entity.server.DragonAnimal;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.Nullable;

public class EnergyDragonEntity extends DragonAnimal {
    public final AnimationState idleAnimationState = new AnimationState();
    public int idleAnimationTimeout = 0;
    public final AnimationState walkAnimationState = new AnimationState();
    public int walkingAnimationTimeout = 0;
    public final AnimationState hoverAnimationState = new AnimationState();
    public int hoverAnimationTimeout = 0;
    public final AnimationState flyAnimationState = new AnimationState();
    public int flyAnimationTimeout = 0;
    public final AnimationState blinkAnimationState = new AnimationState();
    public int blinkTimer = 0;
    public final AnimationState sleepingAnimationState = new AnimationState();
    public int sleepingAnimationTimeout = 0;




    public EnergyDragonEntity(EntityType<? extends @NotNull EnergyDragonEntity> type, Level level) {
        super(type, level);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));

        this.goalSelector.addGoal(1, new BreedGoal(this, 1.1f));
        this.goalSelector.addGoal(2, new TemptGoal(this, 1.25D, Ingredient.of(Items.COD), false));

        this.goalSelector.addGoal(3, new FollowParentGoal(this, 1.1D));

        this.goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(5, new DragonSleepGoal(this));

    }

    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 60)
                .add(Attributes.MOVEMENT_SPEED, 0.3f)
                .add(Attributes.ATTACK_DAMAGE, 5)
                .add(Attributes.TEMPT_RANGE, 25)
                .add(Attributes.FOLLOW_RANGE, 20);
    }

    private void setupAnimationStates() {
        if (this.isFlying()) {
            // --- FLIGHT ANIMATIONS ---
            // Stop ground animations
            this.idleAnimationState.stop();
            this.walkAnimationState.stop();

            Vec3 velocity = this.getDeltaMovement();
            // Use a small threshold to check for active movement
            boolean isMovingInAir = velocity.horizontalDistanceSqr() > 1.0E-6D || Math.abs(velocity.y) > 1.0E-6D;

            if (isMovingInAir) {
                this.flyAnimationState.startIfStopped(this.age);
                this.hoverAnimationState.stop();
            } else {
                this.hoverAnimationState.startIfStopped(this.age);
                this.flyAnimationState.stop();
            }
        } else {
            // --- GROUND ANIMATIONS ---
            // Stop flight animations
            this.flyAnimationState.stop();
            this.hoverAnimationState.stop();

            boolean isMovingOnGround = this.getDeltaMovement().horizontalDistanceSqr() > 1.0E-6D;

            if (isMovingOnGround) {
                this.walkAnimationState.startIfStopped(this.age);
                this.idleAnimationState.stop();
            } else {
                this.idleAnimationState.startIfStopped(this.age);
                this.walkAnimationState.stop();
            }
        }
    }

    @Override
    public void onSyncedDataUpdated(@NotNull EntityDataAccessor<?> data) {
        super.onSyncedDataUpdated(data);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide()) {
            // 1. If the timer is at 0, roll the dice for a blink
            if (blinkTimer <= 0) {
                // Adjust the 100 to make blinks more or less frequent
                if (this.random.nextInt(100) == 0) {
                    this.blinkAnimationState.start(this.tickCount);
                    this.blinkTimer = 20; // Prevent re-triggering for 1 second (20 ticks)
                }
            } else {
                blinkTimer--;
            }
        }
        if (this.level().isClientSide()) {
            this.setupAnimationStates();
        }
        if (this.isVehicle() && this.getControllingPassenger() instanceof LivingEntity driver) {
            // 1. Get the driver's current pitch
            float driverPitch = driver.getXRot();

            // 2. Clamp that value so it stays between -50 and 50
            // -50 is looking UP, 50 is looking DOWN
            float clampedPitch = net.minecraft.util.Mth.clamp(driverPitch, -50.0F, 50.0F);

            // 3. Set the dragon's rotation to the clamped value
            this.setXRot(clampedPitch);
            this.xRotO = clampedPitch; // Prevents "jitter" between frames
        }
        if (this.level().isClientSide()) {
            this.setupAnimationStates();
        }
    }

    @Override
    protected void positionRider(@NotNull Entity passenger, Entity.@NotNull MoveFunction moveFunction) {
        super.positionRider(passenger, moveFunction);

        if (this.hasPassenger(passenger)) {
            float yawRad = this.yBodyRot * ((float)Math.PI / 180F);

            double heightOffset = 1.0D;
            double forwardOffset = 0.3125D;

            double x = Math.sin(yawRad) * -forwardOffset;
            double z = Math.cos(yawRad) * forwardOffset;

            moveFunction.accept(passenger, this.getX() + x, this.getY() + heightOffset, this.getZ() + z);
        }
    }


    @Override
    public boolean isFood(ItemStack itemStack) {
        return itemStack.is(Items.COD);
    }

    @Override
    public @Nullable AgeableMob getBreedOffspring(@NotNull ServerLevel level, @NotNull AgeableMob partner) {
        return ModEntities.ENERGY_DRAGON.create(level, EntitySpawnReason.BREEDING);
    }
}
