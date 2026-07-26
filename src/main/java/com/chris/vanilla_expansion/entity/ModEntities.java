package com.chris.vanilla_expansion.entity;

import com.chris.vanilla_expansion.VanillaExpansion;
import com.chris.vanilla_expansion.entity.server.dragons.AirDragonEntity;
import com.chris.vanilla_expansion.entity.server.dragons.EnergyDragonEntity;
import com.chris.vanilla_expansion.entity.server.dragons.FireDragonEntity;
import com.chris.vanilla_expansion.entity.server.dragons.WaterDragonEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import org.jetbrains.annotations.NotNull;

public class ModEntities {

    public static final EntityType<@NotNull EnergyDragonEntity> ENERGY_DRAGON = register(
            "energy_dragon",
            EntityType.Builder.<EnergyDragonEntity>of(EnergyDragonEntity::new, MobCategory.CREATURE)
                    .sized(1.5f, 1.875f)
    );
    public static final EntityType<@NotNull AirDragonEntity> AIR_DRAGON = register(
            "air_dragon",
            EntityType.Builder.<AirDragonEntity>of(AirDragonEntity::new, MobCategory.CREATURE)
                    .sized(1f, 1.25f)
    );
    public static final EntityType<@NotNull FireDragonEntity> FIRE_DRAGON = register(
            "fire_dragon",
            EntityType.Builder.<FireDragonEntity>of(FireDragonEntity::new, MobCategory.CREATURE)
                    .sized(2f, 2f)
    );
    public static final EntityType<@NotNull WaterDragonEntity> WATER_DRAGON = register(
            "water_dragon",
            EntityType.Builder.<WaterDragonEntity>of(WaterDragonEntity::new, MobCategory.CREATURE)
                    .sized(1.125f, 1f)
    );

    private static <T extends Entity> EntityType<@NotNull T> register(String name, EntityType.Builder<@NotNull T> builder) {
        ResourceKey<@NotNull EntityType<?>> key = ResourceKey.create(Registries.ENTITY_TYPE, Identifier.fromNamespaceAndPath(VanillaExpansion.MOD_ID, name));
        return Registry.register(BuiltInRegistries.ENTITY_TYPE, key, builder.build(key));
    }

    public static void registerModEntities() {

    }
    public static void registerAttributes() {
        FabricDefaultAttributeRegistry.register(ENERGY_DRAGON, EnergyDragonEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(AIR_DRAGON, AirDragonEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(FIRE_DRAGON, FireDragonEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(WATER_DRAGON, WaterDragonEntity.createAttributes());
    }
}
