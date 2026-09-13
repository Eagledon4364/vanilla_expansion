package com.chris.vanilla_expansion.entity.server;

import com.chris.vanilla_expansion.screen.DragonInventoryMenu;
import com.chris.vanilla_expansion.util.DragonMoveControl;
import com.google.common.collect.ImmutableList;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
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
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class DragonAnimal extends TamableAnimal implements HasCustomInventoryScreen {
    private static final EntityDataAccessor<@NotNull Integer> STATE = SynchedEntityData.defineId(DragonAnimal.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<@NotNull Boolean> IS_FLYING = SynchedEntityData.defineId(DragonAnimal.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<@NotNull Boolean> SLEEPING = SynchedEntityData.defineId(DragonAnimal.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<@NotNull Boolean> SADDLED = SynchedEntityData.defineId(DragonAnimal.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<@NotNull Float> DRAGON_PITCH = SynchedEntityData.defineId(DragonAnimal.class, EntityDataSerializers.FLOAT);

    protected static final List<SensorType<? extends Sensor<? super DragonAnimal>>> SENSORS =
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
                    MemoryModuleType.TEMPTATION_COOLDOWN_TICKS,
                    MemoryModuleType.IS_TEMPTED
            );

    protected SimpleContainer inventory;
    private int flightGraceTicks = 0;
    protected boolean playerJumpPending = false;

    protected DragonAnimal(EntityType<? extends @NotNull TamableAnimal> type, Level level) {
        super(type, level);
        this.createInventory();
        this.moveControl = new DragonMoveControl(this);

        this.setPathfindingMalus(PathType.WATER, 0.0F);
        this.setPathfindingMalus(PathType.LAVA, 0.0F);
    }

    public abstract boolean canFly();
    public abstract boolean canSwim();

    public enum DragonState { IDLE, WALK, SIT, SLEEP, FLY, HOVER }

    // --- Brain Architecture ---

    protected Brain.Provider<DragonAnimal> brainProvider() {
        return Brain.provider(MEMORIES, SENSORS, DragonBrain::createActivities);
    }

    @Override
    protected Brain<?> makeBrain(Brain.Packed packedBrain) {
        return this.brainProvider().makeBrain(this, packedBrain);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Brain<DragonAnimal> getBrain() {
        return (Brain<DragonAnimal>) super.getBrain();
    }

    @Override
    protected void customServerAiStep(ServerLevel level) {
        this.getBrain().tick(level, this);
        super.customServerAiStep(level);
    }

    // --- Attributes & Synched Data ---

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
    }

    @Override
    public void addAdditionalSaveData(@NotNull ValueOutput output) {
        super.addAdditionalSaveData(output);
        output.putInt("DragonState", this.getDragonState().ordinal());
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
        this.setDragonState(DragonState.values()[stateOrdinal]);
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

    // --- State Management ---

    public void setDragonState(DragonState state) {
        this.entityData.set(STATE, state.ordinal());
    }

    public DragonState getDragonState() {
        return DragonState.values()[this.entityData.get(STATE)];
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
            setDragonState(DragonState.FLY);
        } else {
            this.navigation = createGroundNavigation();
            setDragonState(this.getDeltaMovement().horizontalDistanceSqr() > 0.005D ? DragonState.WALK : DragonState.IDLE);
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
    protected PathNavigation createNavigation(Level level) {
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
            this.setXRot(Mth.lerp(0.1F, this.getXRot(), 0.0F));
        }

        if (this.level().isClientSide()) {
            float target = this.getDragonPitch();
            this.xRotO = this.getXRot();
            this.setXRot(Mth.lerp(0.2F, this.getXRot(), target));
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

    // --- Rider Flight and Movement Handling ---

    @Override
    public void travel(@NotNull Vec3 travelVector) {
        if (this.isAlive()) {
            if (this.isVehicle() && this.getControllingPassenger() instanceof Player player) {
                this.setYRot(player.getYRot());
                this.yRotO = this.getYRot();

                float clampedPitch = Mth.clamp(player.getXRot(), -15.0F, 40.0F);
                this.setDragonPitch(clampedPitch);

                this.setRot(this.getYRot(), this.getXRot());
                this.yBodyRot = this.getYRot();
                this.yHeadRot = this.yBodyRot;

                boolean isJumpingInput = player.isJumping();

                if (isJumpingInput && !this.isFlying() && this.canFly()) {
                    this.setFlying(true);
                    this.setDeltaMovement(this.getDeltaMovement().add(0, 0.5D, 0));
                }

                if (this.isFlying()) {
                    this.handleRiderFlight(player, player.xxa, player.zza, isJumpingInput);
                    if (!this.level().isClientSide()) {
                        this.calculateEntityAnimation(false);
                    }
                } else {
                    if (player.xxa != 0 || player.zza != 0) {
                        if (this.getDragonState() != DragonState.WALK) {
                            this.setDragonState(DragonState.WALK);
                        }
                    } else {
                        if (this.getDragonState() != DragonState.IDLE && this.getDragonState() != DragonState.SIT) {
                            this.setDragonState(DragonState.IDLE);
                        }
                    }

                    this.setSpeed((float) this.getAttributeValue(Attributes.MOVEMENT_SPEED));
                    super.travel(new Vec3(player.xxa * 0.5F, travelVector.y, player.zza));
                }
            } else if (this.isFlying()) {
                this.move(MoverType.SELF, this.getDeltaMovement());
                this.setDeltaMovement(this.getDeltaMovement().scale(0.9D));
            } else {
                super.travel(travelVector);
            }
        }
    }

    private void handleRiderFlight(Player player, float xInput, float zInput, boolean isAscending) {
        Vec3 lookVec = player.getLookAngle();

        boolean isSprinting = player.isSprinting();
        double baseSpeed = (float) this.getAttributeValue(Attributes.FLYING_SPEED) * 0.5D;
        double speedMultiplier = isSprinting ? (baseSpeed * 1.8D) : baseSpeed;
        double acceleration = isSprinting ? 0.2D : 0.1D;

        if (zInput > 0) {
            // Apply speed along player look vector
            Vec3 targetVel = lookVec.scale(speedMultiplier);
            if (isAscending) {
                targetVel = targetVel.add(0, 0.5D, 0);
            }

            this.setDeltaMovement(this.getDeltaMovement().lerp(targetVel, acceleration));

            if (this.getDragonState() != DragonState.FLY) {
                this.setDragonState(DragonState.FLY);
            }
        } else {
            double verticalMovement = isAscending ? 0.5D : -0.15D; // Slight steady descent when idling in air
            Vec3 currentVel = this.getDeltaMovement();

            this.setDeltaMovement(new Vec3(
                    currentVel.x * 0.9D,
                    Mth.lerp(0.1D, currentVel.y, verticalMovement),
                    currentVel.z * 0.9D
            ));

            if (this.getDragonState() != DragonState.HOVER) {
                this.setDragonState(DragonState.HOVER);
            }
        }

        // Standard physics resolution: moves dragon and updates onGround state
        this.move(MoverType.SELF, this.getDeltaMovement());
        this.resetFallDistance();

        // Check landing condition: touch down on solid ground when grace period expires and player isn't forcing ascend
        if (this.onGround() && this.flightGraceTicks == 0 && !isAscending) {
            this.setFlying(false);
        }
    }

    // --- Inventory & Interaction ---

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
            player.openMenu(new MenuProvider() {
                @Override
                public @NotNull Component getDisplayName() {
                    return DragonAnimal.this.getDisplayName();
                }

                @Override
                public AbstractContainerMenu createMenu(int id, @NotNull Inventory playerInv, @NotNull Player player) {
                    return new DragonInventoryMenu(id, playerInv, DragonAnimal.this.inventory, DragonAnimal.this);
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
            if (this.isFood(itemstack) && this.getHealth() < this.getMaxHealth()) {
                this.feed(player, hand, itemstack, 2.0F, 2.0F);
                return InteractionResult.SUCCESS;
            }
            if (itemstack.is(Items.STICK) && !player.isSecondaryUseActive()) {
                if (!this.level().isClientSide()) {
                    boolean currentState = this.isOrderedToSit();
                    this.setOrderedToSit(!currentState);
                    this.setDragonState(!currentState ? DragonState.SIT : DragonState.IDLE);
                    this.navigation.stop();
                    this.setTarget(null);
                }
                return InteractionResult.SUCCESS;
            }
            if (player.isSecondaryUseActive()) {
                this.openCustomInventoryScreen(player);
                return InteractionResult.SUCCESS;
            }
            if (this.isSaddled() && !this.isBaby() && !itemstack.is(Items.STICK)) {
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
    public boolean canStandOnFluid(net.minecraft.world.level.material.FluidState state) {
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