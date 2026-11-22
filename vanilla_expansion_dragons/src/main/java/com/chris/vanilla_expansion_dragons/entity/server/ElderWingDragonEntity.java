package com.chris.vanilla_expansion_dragons.entity.server;


import com.chris.vanilla_expansion.entity.server.DragonAnimal;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.HasCustomInventoryScreen;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.Nullable;

public class ElderWingDragonEntity extends TamableAnimal implements HasCustomInventoryScreen {
    private static final EntityDataAccessor<@NotNull Integer> STATE = SynchedEntityData.defineId(ElderWingDragonEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<@NotNull Boolean> IS_FLYING = SynchedEntityData.defineId(ElderWingDragonEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<@NotNull Boolean> SLEEPING = SynchedEntityData.defineId(ElderWingDragonEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<@NotNull Boolean> SADDLED = SynchedEntityData.defineId(ElderWingDragonEntity.class, EntityDataSerializers.BOOLEAN);

    protected ElderWingDragonEntity(EntityType<? extends TamableAnimal> type, Level level) {
        super(type, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return TamableAnimal.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 20.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.3D)
                .add(Attributes.FLYING_SPEED, 1.0D)
                .add(Attributes.ATTACK_DAMAGE, 2.0D)
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
        builder.define(STATE, DragonAnimal.DragonState.IDLE.ordinal());
        builder.define(IS_FLYING, false);
        builder.define(SADDLED, false);
        builder.define(SLEEPING, false);
    }

    @Override
    protected void addAdditionalSaveData(ValueOutput output) {
        super.addAdditionalSaveData(output);
    }

    @Override
    protected void readAdditionalSaveData(ValueInput input) {
        super.readAdditionalSaveData(input);
    }

    @Override
    public boolean isFood(ItemStack itemStack) {
        return itemStack.is(ItemTags.WOLF_FOOD);
    }

    @Override
    public @Nullable AgeableMob getBreedOffspring(ServerLevel level, AgeableMob partner) {
        return null;
    }

    @Override
    public void openCustomInventoryScreen(Player player) {

    }

}