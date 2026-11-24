package com.chris.vanilla_expansion_dragons.entity.server.util;

import com.chris.vanilla_expansion_dragons.menu.DragonMainMenu;
import com.google.common.collect.ImmutableList;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.Container;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.behavior.EntityTracker;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.AbstractMountInventoryMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class DragonAnimal extends TamableAnimal implements HasCustomInventoryScreen {

    public static final double ROLL_REORIENT_CUTOFF = Math.sqrt(10.0 / 3.0);
    private static final float MAX_RIDER_BANK_DEGREES = 65.0F;

    public enum CommandMode {
        ROAM,
        FOLLOW,
        SIT,
        GUARD // TODO: implement guard behavior logic
    }

    private static final EntityDataAccessor<@NotNull Integer> STATE =
            SynchedEntityData.defineId(DragonAnimal.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<@NotNull Boolean> IS_FLYING =
            SynchedEntityData.defineId(DragonAnimal.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<@NotNull Boolean> SLEEPING =
            SynchedEntityData.defineId(DragonAnimal.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<@NotNull Boolean> SADDLED =
            SynchedEntityData.defineId(DragonAnimal.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<@NotNull Float> DRAGON_PITCH =
            SynchedEntityData.defineId(DragonAnimal.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<@NotNull Float> DRAGON_ROLL =
            SynchedEntityData.defineId(DragonAnimal.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<@NotNull Integer> COMMAND_MODE =
            SynchedEntityData.defineId(DragonAnimal.class, EntityDataSerializers.INT);

    protected static final List<SensorType<? extends @NotNull Sensor<? super DragonAnimal>>> SENSORS =
            ImmutableList.of(
                    SensorType.NEAREST_LIVING_ENTITIES,
                    SensorType.NEAREST_PLAYERS,
                    SensorType.HURT_BY,
                    SensorType.FOOD_TEMPTATIONS
            );

    protected static final List<MemoryModuleType<?>> MEMORIES =
            ImmutableList.of(
                    MemoryModuleType.WALK_TARGET,
                    MemoryModuleType.LOOK_TARGET,
                    MemoryModuleType.ATTACK_TARGET,
                    MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES,
                    MemoryModuleType.HURT_BY_ENTITY,
                    MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE,
                    MemoryModuleType.TEMPTING_PLAYER,
                    MemoryModuleType.TEMPTATION_COOLDOWN_TICKS
            );

    protected SimpleContainer inventory;
    private int flightGraceTicks = 0;
    private int roamPauseTicks = 0;

    private double currentFlightSpeed = 0.0D;
    private double prevFlightSpeed = 0.0D;
    private float dynamicPitch = 0.0F;
    private float prevDynamicPitch = 0.0F;
    private float dynamicRoll = 0.0F;
    private float prevDynamicRoll = 0.0F;
    private float dynamicYawLean = 0.0F;
    private float prevDynamicYawLean = 0.0F;

    private float pendingRiderYawInput = 0.0F;
    private float pendingRiderPitchInput = 0.0F;


    protected DragonAnimal(EntityType<? extends @NotNull TamableAnimal> type, Level level) {
        super(type, level);
        this.createInventory();
        this.moveControl = new DragonMoveControl(this);

        this.setPathfindingMalus(PathType.WATER, 0.0F);
        this.setPathfindingMalus(PathType.LAVA, 0.0F);
    }

    public abstract boolean canFly();
    public abstract boolean canSwim();

    public void addRiderMouseInput(float yaw, float pitch) {
        this.pendingRiderYawInput += Mth.clamp(yaw, -100.0F, 100.0F);
        this.pendingRiderPitchInput += Mth.clamp(pitch, -100.0F, 100.0F);
    }

    public float getDynamicPitch() {
        return this.dynamicPitch;
    }

    public float getDynamicRoll() {
        return this.dynamicRoll;
    }

    public float getLerpedDynamicPitch(float partialTick) {
        return Mth.lerp(partialTick, this.prevDynamicPitch, this.dynamicPitch);
    }

    public float getDragonRoll() {
        return this.dynamicRoll;
    }

    public void setDragonRoll(float roll) {
        float bank = Mth.clamp(roll, -MAX_RIDER_BANK_DEGREES, MAX_RIDER_BANK_DEGREES);
        this.dynamicRoll = bank;
        this.entityData.set(DRAGON_ROLL, bank);
    }

    public float getLerpedRoll(float partialTick) {
        return Mth.lerp(partialTick, this.prevDynamicRoll, this.dynamicRoll);
    }

    private float computeReorientDeltaDeg(float currentRollDeg) {
        return Mth.wrapDegrees(currentRollDeg) * 0.06F;
    }
    public float getLerpedYawLean(float partialTick) {
        return Mth.lerp(partialTick, this.prevDynamicYawLean, this.dynamicYawLean);
    }

    // Brain Architecture

    protected Brain.Provider<DragonAnimal> brainProvider() {
        return Brain.provider(SENSORS, DragonBrain::createActivities);
    }

    @Override
    protected Brain<?> makeBrain(Brain.Packed packedBrain) {
        return this.brainProvider().makeBrain(this, packedBrain);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Brain<@NotNull DragonAnimal> getBrain() {
        return (Brain<DragonAnimal>) super.getBrain();
    }

    @Override
    protected void customServerAiStep(ServerLevel level) {
        this.getBrain().tick(level, this);
        super.customServerAiStep(level);
    }

    // Attributes & Synched Data

    public static AttributeSupplier.Builder createAttributes() {
        return TamableAnimal.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 60.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.3D)
                .add(Attributes.FLYING_SPEED, 0.8D)
                .add(Attributes.ATTACK_DAMAGE, 8.0D)
                .add(Attributes.FOLLOW_RANGE, 128.0D);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.@NotNull Builder builder) {
        super.defineSynchedData(builder);
        builder.define(STATE, DragonState.IDLE.ordinal());
        builder.define(IS_FLYING, false);
        builder.define(SADDLED, false);
        builder.define(SLEEPING, false);
        builder.define(DRAGON_PITCH, 0.0F);
        builder.define(DRAGON_ROLL, 0.0F);
        builder.define(COMMAND_MODE, CommandMode.ROAM.ordinal());
    }

    @Override
    public void addAdditionalSaveData(@NotNull ValueOutput output) {
        super.addAdditionalSaveData(output);
        output.putInt("DragonState", this.getDragonState().ordinal());
        output.putInt("CommandMode", this.getCommandMode().ordinal());
        ValueOutput.TypedOutputList<@NotNull ItemStack> itemList = output.list("Inventory", ItemStack.CODEC);
        for (int i = 0; i < this.inventory.getContainerSize(); i++) {
            ItemStack stack = this.inventory.getItem(i);
            if (!stack.isEmpty()) {
                itemList.add(stack);
            }
        }
    }

    @Override
    public void readAdditionalSaveData(@NotNull ValueInput input) {
        super.readAdditionalSaveData(input);
        int stateOrdinal = input.getIntOr("DragonState", DragonState.IDLE.ordinal());
        DragonState[] states = DragonState.values();
        this.setDragonState(states[Mth.clamp(stateOrdinal, 0, states.length - 1)]);

        CommandMode[] modes = CommandMode.values();
        this.setCommandMode(modes[Mth.clamp(input.getIntOr("CommandMode", 0), 0, modes.length - 1)]);

        input.list("Inventory", ItemStack.CODEC).ifPresent(list -> {
            int slot = 0;
            for (ItemStack stack : list) {
                if (slot < this.inventory.getContainerSize()) {
                    this.inventory.setItem(slot, stack);
                    slot++;
                }
            }
        });
        this.updateContainerEquipment();
    }

    // State & Command Mode Management

    public void setDragonState(DragonState state) {
        this.entityData.set(STATE, state.ordinal());
    }

    public DragonState getDragonState() {
        DragonState[] values = DragonState.values();
        return values[Mth.clamp(this.entityData.get(STATE), 0, values.length - 1)];
    }

    public CommandMode getCommandMode() {
        CommandMode[] values = CommandMode.values();
        return values[Mth.clamp(this.entityData.get(COMMAND_MODE), 0, values.length - 1)];
    }

    public void setCommandMode(CommandMode mode) {
        this.entityData.set(COMMAND_MODE, mode.ordinal());
        switch (mode) {
            case SIT -> {
                super.setOrderedToSit(true);
                this.setDragonState(DragonState.SIT);
                if (!this.level().isClientSide()) {
                    this.getBrain().eraseMemory(MemoryModuleType.WALK_TARGET);
                    this.navigation.stop();
                    this.setTarget(null);
                }
            }
            case FOLLOW, ROAM -> {
                super.setOrderedToSit(false);
                if (this.getDragonState() == DragonState.SIT) {
                    this.setDragonState(DragonState.IDLE);
                }
            }
            case GUARD -> {
                // TODO: implement GUARD mode AI / home position behavior
                super.setOrderedToSit(false);
                if (!this.level().isClientSide()) {
                    this.getBrain().eraseMemory(MemoryModuleType.WALK_TARGET);
                    this.navigation.stop();
                }
            }
        }
    }

    public CommandMode cycleCommandMode() {
        CommandMode[] values = CommandMode.values();
        CommandMode next = values[(this.getCommandMode().ordinal() + 1) % values.length];
        this.setCommandMode(next);
        return next;
    }

    public boolean isFlying() {
        return this.entityData.get(IS_FLYING);
    }

    public void setFlying(boolean flying) {
        if (!canFly() && flying) return;
        this.entityData.set(IS_FLYING, flying);

        if (flying) {
            this.flightGraceTicks = 10;
            this.navigation = createFlyingNavigation();
            this.setDragonState(DragonState.FLY_VELOCITY);
        } else {
            this.currentFlightSpeed = 0.0D;
            this.setDragonRoll(0.0F);
            this.navigation = createGroundNavigation();
            boolean isMoving = this.getDeltaMovement().horizontalDistanceSqr() > 0.005D;
            if (this.onGround()) {
                this.setDragonState(isMoving ? DragonState.WALK : DragonState.IDLE);
            } else {
                this.setDragonState(DragonState.IDLE);
            }
            this.getMoveControl().setWantedPosition(this.getX(), this.getY(), this.getZ(), 0.0D);
        }
    }

    protected PathNavigation createFlyingNavigation() {
        FlyingPathNavigation nav = new FlyingPathNavigation(this, this.level()) {
            @Override
            public boolean isStableDestination(BlockPos pos) {
                int groundY = level().getHeight(Heightmap.Types.MOTION_BLOCKING, pos.getX(), pos.getZ());
                if (pos.getY() > groundY + 70) {
                    return false;
                }
                return super.isStableDestination(pos);
            }
        };
        nav.setCanOpenDoors(false);
        nav.setCanFloat(true);
        return nav;
    }

    protected PathNavigation createGroundNavigation() {
        GroundPathNavigation nav = new GroundPathNavigation(this, this.level());
        nav.setCanOpenDoors(false);
        return nav;
    }

    @Override
    protected @NotNull PathNavigation createNavigation(Level level) {
        return this.isFlying() ? createFlyingNavigation() : createGroundNavigation();
    }

    public float getTurnSpeed() {
        float baseTurn = 8.0F;
        if (this.isFlying()) {
            if (this.isInWater()) return baseTurn * (float) getSwimMultiplier();
            double currentSpeed = this.getDeltaMovement().horizontalDistance();
            return Mth.lerp((float) currentSpeed / 1.2F, baseTurn, 2.5F);
        }
        return baseTurn;
    }

    public double getSwimMultiplier() {
        return 1.0D;
    }

    @Override
    public void setOrderedToSit(boolean sitting) {
        super.setOrderedToSit(sitting);
        this.setDragonState(sitting ? DragonState.SIT : DragonState.IDLE);
        if (this.level() instanceof ServerLevel) {
            this.navigation.stop();
        }
    }

    @Override
    public boolean shouldTryTeleportToOwner() {
        return false;
    }

    @Override
    public boolean isNoGravity() {
        return this.isFlying() || super.isNoGravity();
    }

    @Override
    public boolean causeFallDamage(double fallDistance, float damageModifier, DamageSource damageSource) {
        if (this.canFly()) {
            return false;
        }
        return super.causeFallDamage(fallDistance, damageModifier, damageSource);
    }

    @Override
    public void tick() {
        super.tick();

        if (this.flightGraceTicks > 0) {
            this.flightGraceTicks--;
        }

        if (!this.level().isClientSide()) {
            this.updateCommandBehaviors();

            if (!this.isSleeping() && !this.isVehicle()) {
                if (this.isFlying() && this.onGround() && this.flightGraceTicks == 0) {
                    this.setFlying(false);
                }
                if (!this.onGround() && this.getDeltaMovement().y < -0.4 && !this.isFlying() && this.canFly()) {
                    this.setFlying(true);
                }

                if (this.isFlying()) {
                    int groundY = this.level().getHeight(Heightmap.Types.MOTION_BLOCKING, this.getBlockX(), this.getBlockZ());
                    double maxAllowedY = groundY + 70.0D;

                    if (this.getY() > maxAllowedY) {
                        Vec3 currentVel = this.getDeltaMovement();
                        this.setDeltaMovement(currentVel.x, Math.min(currentVel.y, -0.15D), currentVel.z);

                        if (this.getNavigation().isInProgress() && this.getNavigation().getTargetPos() != null) {
                            if (this.getNavigation().getTargetPos().getY() > maxAllowedY) {
                                this.getNavigation().stop();
                            }
                        }
                    }
                }
            }
        }

        if (!this.isFlying() || this.getDragonState() == DragonState.HOVER) {
            this.dynamicPitch = Mth.lerp(0.1F, this.dynamicPitch, 0.0F);
            this.setXRot(Mth.lerp(0.1F, this.getXRot(), 0.0F));
        }

        if (this.level().isClientSide()) {
            float target = this.getDragonPitch();
            this.xRotO = this.getXRot();
            this.setXRot(Mth.lerp(0.2F, this.getXRot(), target));
        }
    }

    @SuppressWarnings("unchecked")
    protected void updateCommandBehaviors() {
        if (!(this.level() instanceof ServerLevel serverLevel)) {
            return;
        }
        if (this.isVehicle() || this.isSleeping() || this.isOrderedToSit() || this.getCommandMode() == CommandMode.SIT) {
            return;
        }

        Brain<DragonAnimal> brain = (Brain<DragonAnimal>) this.getBrain();
        CommandMode mode = this.getCommandMode();

        if (mode == CommandMode.FOLLOW) {
            LivingEntity owner = this.getOwner();
            if (owner != null && owner.level() == this.level() && owner.isAlive()) {
                double distSq = this.distanceToSqr(owner);
                brain.setMemory(MemoryModuleType.LOOK_TARGET, new EntityTracker(owner, true));

                if (distSq > 16.0D) {
                    if (distSq > 400.0D && !this.isFlying()) {
                        BlockPos targetPos = owner.blockPosition().offset(
                                this.random.nextInt(3) - 1,
                                0,
                                this.random.nextInt(3) - 1
                        );
                        this.teleportTo(targetPos.getX() + 0.5D, targetPos.getY(), targetPos.getZ() + 0.5D);
                        brain.eraseMemory(MemoryModuleType.WALK_TARGET);
                    } else {
                        brain.setMemory(MemoryModuleType.WALK_TARGET, new WalkTarget(owner, 1.0F, 3));
                    }
                } else if (distSq <= 9.0D) {
                    brain.eraseMemory(MemoryModuleType.WALK_TARGET);
                    this.navigation.stop();
                }
            }
        } else if (mode == CommandMode.ROAM) {
            if (this.getTarget() != null) {
                return;
            }

            if (this.roamPauseTicks > 0) {
                this.roamPauseTicks--;
                brain.eraseMemory(MemoryModuleType.WALK_TARGET);
                this.navigation.stop();
                if (this.getDragonState() != DragonState.IDLE && !this.isFlying()) {
                    this.setDragonState(DragonState.IDLE);
                }
                return;
            }

            if (!brain.hasMemoryValue(MemoryModuleType.WALK_TARGET) && this.navigation.isDone()) {
                if (this.random.nextInt(40) == 0) {
                    this.roamPauseTicks = 60 + this.random.nextInt(80);
                    this.setDragonState(DragonState.IDLE);
                    return;
                }

                for (int attempt = 0; attempt < 6; attempt++) {
                    int dx = this.random.nextInt(17) - 8;
                    int dz = this.random.nextInt(17) - 8;
                    BlockPos candidate = this.blockPosition().offset(dx, 0, dz);
                    int surfaceY = serverLevel.getHeight(
                            Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                            candidate.getX(),
                            candidate.getZ()
                    );
                    BlockPos dest = new BlockPos(candidate.getX(), surfaceY, candidate.getZ());
                    if (Math.abs(dest.getY() - this.getBlockY()) <= 4) {
                        brain.setMemory(MemoryModuleType.WALK_TARGET, new WalkTarget(dest, 0.75F, 1));
                        break;
                    }
                }
            }
        }
    }

    @Override
    public void aiStep() {
        super.aiStep();

        if (this.isFlying()) {
            this.resetFallDistance();

            if (this.getDeltaMovement().y < 0.0D && !this.onGround()) {
                this.setDeltaMovement(this.getDeltaMovement().multiply(1.0D, 0.8D, 1.0D));
            }
        }
    }

    // Rider Flight and Movement Handling





    @Override
    public void travel(@NotNull Vec3 travelVector) {
        if (!this.isAlive()) {
            return;
        }

        if (this.isVehicle() && this.getControllingPassenger() instanceof Player player) {
            this.prevDynamicPitch = this.dynamicPitch;
            this.prevDynamicRoll = this.dynamicRoll;
            this.prevDynamicYawLean = this.dynamicYawLean;

            boolean isJumpingInput = player.isJumping();

            if (isJumpingInput && !this.isFlying() && this.canFly()) {
                this.setFlying(true);
                this.dynamicPitch = -18.0F;

                double takeoffSpeed = Math.max(
                        this.currentFlightSpeed,
                        this.getAttributeValue(Attributes.FLYING_SPEED) * 0.85D
                );

                this.currentFlightSpeed = takeoffSpeed;
                this.setDeltaMovement(
                        this.getDeltaMovement().add(0.0D, 0.45D, 0.0D)
                );
            }

            if (this.isFlying()) {

                // A/D banking
                float bankInput = player.xxa * 4.5F;

                float nextRoll = Math.abs(player.xxa) > 0.01F
                        ? this.dynamicRoll + bankInput
                        : this.dynamicRoll - computeReorientDeltaDeg(this.dynamicRoll);

                this.setDragonRoll(nextRoll);

                // Camera direction is the flight direction.
                float targetYaw = player.getYRot();
                float targetPitch = player.getXRot();

                // Smoothly follow camera yaw.
                float yawDifference = Mth.wrapDegrees(
                        targetYaw - this.getYRot()
                );

                this.setYRot(
                        this.getYRot() + Mth.clamp(
                                yawDifference * 0.35F,
                                -12.0F,
                                12.0F
                        )
                );

                // Smoothly follow camera pitch.
                this.dynamicPitch = Mth.clamp(
                        Mth.lerp(0.30F, this.dynamicPitch, targetPitch),
                        -85.0F,
                        85.0F
                );

                // Visual lean follows the turning direction.
                float targetYawLean = Mth.clamp(
                        -yawDifference * 0.35F,
                        -14.0F,
                        14.0F
                );

                this.dynamicYawLean = Mth.lerp(
                        0.25F,
                        this.dynamicYawLean,
                        targetYawLean
                );

                this.setXRot(this.dynamicPitch);
                this.setDragonPitch(this.dynamicPitch);

                this.yRotO = this.getYRot();
                this.setRot(this.getYRot(), this.getXRot());

                this.yBodyRot = this.getYRot();
                this.yHeadRot = this.getYRot();

                this.handleRiderFlight(
                        player,
                        player.zza,
                        isJumpingInput
                );

                if (!this.level().isClientSide()) {
                    this.calculateEntityAnimation(false);
                }

                return;
            }

            // Ground riding
            this.pendingRiderYawInput = 0.0F;
            this.pendingRiderPitchInput = 0.0F;

            float nextGroundYaw = Mth.rotLerp(
                    0.55F,
                    this.getYRot(),
                    player.getYRot()
            );

            this.dynamicYawLean = Mth.lerp(
                    0.2F,
                    this.dynamicYawLean,
                    0.0F
            );

            this.setYRot(nextGroundYaw);
            this.yRotO = this.getYRot();

            this.setXRot(0.0F);
            this.setRot(this.getYRot(), 0.0F);

            this.yBodyRot = this.getYRot();
            this.yHeadRot = this.getYRot();

            this.dynamicPitch = Mth.lerp(
                    0.25F,
                    this.dynamicPitch,
                    0.0F
            );

            this.setDragonRoll(
                    Mth.lerp(0.25F, this.dynamicRoll, 0.0F)
            );

            this.setDragonPitch(0.0F);

            boolean moving = Math.abs(player.xxa) > 0.01F
                    || Math.abs(player.zza) > 0.01F;

            DragonState moveState;

            if (!moving) {
                moveState = DragonState.IDLE;
            } else if (player.isSprinting()) {
                moveState = DragonState.RUN;
            } else {
                moveState = DragonState.WALK;
            }

            if (this.getDragonState() != moveState) {
                this.setDragonState(moveState);
            }

            float movementSpeed = (float) this.getAttributeValue(
                    Attributes.MOVEMENT_SPEED
            );

            if (moveState == DragonState.WALK) {
                movementSpeed *= 2.0F;
            } else if (moveState == DragonState.RUN) {
                movementSpeed *= 3.0F;
            }

            this.setSpeed(movementSpeed);

            super.travel(new Vec3(
                    player.xxa * 0.5F,
                    travelVector.y,
                    player.zza
            ));

            return;
        }

        if (this.isFlying()) {
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.setDeltaMovement(this.getDeltaMovement().scale(0.95D));
        } else {
            super.travel(travelVector);
        }
    }


    private void handleRiderFlight(Player player, float zInput, boolean isAscending) {
        this.prevFlightSpeed = this.currentFlightSpeed;

        double flyingAttr = this.getAttributeValue(Attributes.FLYING_SPEED);
        double baseSpeed = flyingAttr * 1.15D;
        double maxSpeed = player.isSprinting() ? baseSpeed * 1.75D : baseSpeed;

        // Pitch/gravity energy exchange
        double pitchRad = this.dynamicPitch * Mth.DEG_TO_RAD;
        double gravityExchange = Math.sin(pitchRad) * 0.03D;

        // Acceleration and deceleration
        if (zInput > 0.01F) {
            if (this.currentFlightSpeed < baseSpeed * 0.75D) {
                this.currentFlightSpeed = baseSpeed * 0.75D;
            }

            double thrust = player.isSprinting() ? 0.10D : 0.07D;

            this.currentFlightSpeed = Math.min(
                    maxSpeed,
                    this.currentFlightSpeed + thrust
            );

        } else if (zInput < -0.01F) {
            // Airbrake
            this.currentFlightSpeed = Math.max(
                    0.0D,
                    this.currentFlightSpeed - 0.06D
            );

        } else {
            // Preserve speed while gliding
            this.currentFlightSpeed *= 0.995D;
        }

        this.currentFlightSpeed = Mth.clamp(
                this.currentFlightSpeed + gravityExchange,
                0.0D,
                maxSpeed * 1.5D
        );

        if (isAscending) {
            this.currentFlightSpeed = Math.max(
                    this.currentFlightSpeed,
                    baseSpeed * 0.65D
            );
        }

        // Dragon orientation
        float yawRad = this.getYRot() * Mth.DEG_TO_RAD;
        double rollRad = this.dynamicRoll * Mth.DEG_TO_RAD;

        // Forward direction follows the camera-controlled dragon rotation.
        Vec3 forwardDir = Vec3.directionFromRotation(
                this.dynamicPitch,
                this.getYRot()
        ).normalize();

        // Local right direction
        Vec3 rightDir = new Vec3(
                Math.cos(yawRad),
                0.0D,
                Math.sin(yawRad)
        ).normalize();

        Vec3 upDir = forwardDir.cross(rightDir).normalize();

        // Apply banking to the local right direction.
        rightDir = rightDir.scale(Math.cos(rollRad))
                .add(upDir.scale(Math.sin(rollRad)))
                .normalize();

        // Banking produces sideways movement.
        double bankDriftSpeed =
                Math.sin(rollRad)
                        * baseSpeed
                        * 0.22D
                        * Math.min(
                        1.0D,
                        Math.max(
                                0.25D,
                                this.currentFlightSpeed / Math.max(0.01D, baseSpeed)
                        )
                );

        Vec3 targetVel = forwardDir.scale(this.currentFlightSpeed)
                .add(rightDir.scale(bankDriftSpeed));

        if (isAscending) {
            targetVel = targetVel.add(0.0D, 0.45D, 0.0D);
        }

        double speedDelta = Math.abs(
                this.currentFlightSpeed - this.prevFlightSpeed
        );

        boolean stationaryInAir =
                this.currentFlightSpeed < 0.02D
                        && !isAscending
                        && this.getDeltaMovement().lengthSqr() < 0.0008D;

        if (stationaryInAir) {
            this.currentFlightSpeed = 0.0D;
            targetVel = Vec3.ZERO;

            if (this.getDragonState() != DragonState.HOVER) {
                this.setDragonState(DragonState.HOVER);
            }

        } else if (speedDelta > 0.005D || Math.abs(zInput) > 0.01F) {

            if (this.getDragonState() != DragonState.FLY_VELOCITY) {
                this.setDragonState(DragonState.FLY_VELOCITY);
            }

        } else {

            if (this.getDragonState() != DragonState.GLIDE) {
                this.setDragonState(DragonState.GLIDE);
            }
        }

        this.setDeltaMovement(
                this.getDeltaMovement().lerp(targetVel, 0.30D)
        );

        this.move(MoverType.SELF, this.getDeltaMovement());
        this.resetFallDistance();

        if (this.onGround() && this.flightGraceTicks == 0 && !isAscending) {
            this.setFlying(false);
        }
    }

    // Inventory & Interaction (Invoked when riding and pressing E)

    protected void createInventory() {
        this.inventory = new SimpleContainer(this.getInventorySize()) {
            @Override
            public void setChanged() {
                super.setChanged();
                DragonAnimal.this.updateContainerEquipment();
            }
        };
        this.updateContainerEquipment();
    }

    protected void updateContainerEquipment() {
        if (!this.level().isClientSide()) {
            this.setSaddled(!this.inventory.getItem(0).isEmpty() && this.inventory.getItem(0).is(Items.SADDLE));
        }
    }

    public final int getInventorySize() {
        return AbstractMountInventoryMenu.getInventorySize(this.getInventoryColumns());
    }

    public int getInventoryColumns() {
        return 1;
    }

    @Override
    public void openCustomInventoryScreen(@NotNull Player player) {
        if (!this.level().isClientSide() && (!this.isVehicle() || this.hasPassenger(player)) && this.isTame()) {
            Container mainContainer = this.inventory != null
                    ? this.inventory
                    : new SimpleContainer(DragonMainMenu.DRAGON_CONTAINER_SIZE);

            player.openMenu(new MenuProvider() {
                @Override
                public @NotNull Component getDisplayName() {
                    return DragonAnimal.this.getDisplayName();
                }

                @Override
                public AbstractContainerMenu createMenu(int id, @NotNull Inventory playerInv, @NotNull Player menuPlayer) {
                    return new DragonMainMenu(id, playerInv, mainContainer, DragonAnimal.this);
                }
            });
        }
    }

    @Override
    public @NotNull InteractionResult mobInteract(Player player, @NotNull InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);

        if (this.isSleeping()) {
            if (!this.level().isClientSide()) {
                this.setSleeping(false);
            }
            return InteractionResult.SUCCESS;
        }

        if (!this.isTame() && this.isFood(itemstack)) {
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
                    itemstack.shrink(1);
                }
            }
            return InteractionResult.SUCCESS;
        }

        if (this.isTame() && this.isOwnedBy(player)) {
            // Shift + Right-Click cycles command mode: ROAM -> FOLLOW -> SIT -> GUARD
            if (player.isSecondaryUseActive()) {
                if (!this.level().isClientSide()) {
                    CommandMode nextMode = this.cycleCommandMode();
                    player.sendOverlayMessage(Component.literal("Dragon Mode: " + nextMode.name()));
                }
                return InteractionResult.SUCCESS;
            }

            // Feeding when hurt
            if (this.isFood(itemstack)) {
                if (!this.level().isClientSide()) {
                    if (this.getHealth() < this.getMaxHealth()) {
                        this.heal(4.0F);
                    }
                    if (!player.getAbilities().instabuild) {
                        itemstack.shrink(1);
                    }
                }
                return InteractionResult.SUCCESS;
            }

            // Right-Click rides the dragon when saddled and adult
            if (this.isSaddled() && !this.isBaby()) {
                if (!this.level().isClientSide()) {
                    player.startRiding(this);
                }
                return InteractionResult.SUCCESS;
            }
        }

        return super.mobInteract(player, hand);
    }

    public void setSleeping(boolean sleeping) {
        this.entityData.set(SLEEPING, sleeping);
        if (sleeping) {
            this.setDragonState(DragonState.SLEEP);
            this.navigation.stop();
            this.setTarget(null);
        } else {
            this.setDragonState(this.isOrderedToSit() ? DragonState.SIT : DragonState.IDLE);
        }
    }

    public boolean isSleeping() {
        return this.entityData.get(SLEEPING);
    }

    public boolean isSaddled() {
        return this.entityData.get(SADDLED);
    }

    public void setSaddled(boolean saddled) {
        this.entityData.set(SADDLED, saddled);
    }

    public float getDragonPitch() {
        return this.entityData.get(DRAGON_PITCH);
    }

    public void setDragonPitch(float pitch) {
        this.entityData.set(DRAGON_PITCH, pitch);
    }

    @Override
    public boolean isFood(@NotNull ItemStack stack) {
        return false;
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(@NotNull ServerLevel level, @NotNull AgeableMob partner) {
        return null;
    }

    @Nullable
    @Override
    public LivingEntity getControllingPassenger() {
        return this.getFirstPassenger() instanceof LivingEntity entity ? entity : null;
    }

    @Override
    public boolean fireImmune() {
        return true;
    }

    @Override
    public boolean displayFireAnimation() {
        return false;
    }

    @Override
    public boolean canStandOnFluid(FluidState state) {
        return state.is(net.minecraft.tags.FluidTags.LAVA);
    }

    @Override
    protected void dropCustomDeathLoot(@NotNull ServerLevel level, @NotNull DamageSource source, boolean killedByPlayer) {
        super.dropCustomDeathLoot(level, source, killedByPlayer);
        if (this.inventory != null) {
            for (int i = 0; i < this.inventory.getContainerSize(); i++) {
                ItemStack stack = this.inventory.getItem(i);
                if (!stack.isEmpty()) {
                    this.spawnAtLocation(level, stack);
                }
            }
            this.inventory.clearContent();
        }
        if (this.isSaddled()) {
            this.spawnAtLocation(level, Items.SADDLE);
        }
    }
}