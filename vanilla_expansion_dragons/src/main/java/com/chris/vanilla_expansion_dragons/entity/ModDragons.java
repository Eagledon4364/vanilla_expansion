package com.chris.vanilla_expansion_dragons.entity;

import com.chris.vanilla_expansion.VanillaExpansion;
import com.chris.vanilla_expansion_dragons.VanillaExpansionDragons;
import com.chris.vanilla_expansion_dragons.entity.server.ElderWingDragonEntity;
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

public class ModDragons {
    public static final EntityType<@NotNull ElderWingDragonEntity> ELDERWING_DRAGON = register(
            "elderwing_dragon",
            EntityType.Builder.<ElderWingDragonEntity>of(ElderWingDragonEntity::new, MobCategory.CREATURE)
                    .sized(1.5f, 1.875f)
    );


    private static <T extends Entity> EntityType<@NotNull T> register(String name, EntityType.Builder<@NotNull T> builder) {
        ResourceKey<@NotNull EntityType<?>> key = ResourceKey.create(Registries.ENTITY_TYPE, Identifier.fromNamespaceAndPath(VanillaExpansionDragons.MOD_ID, name));
        return Registry.register(BuiltInRegistries.ENTITY_TYPE, key, builder.build(key));
    }

    public static void registerModEntities() {
    }

    public static void registerAttributes() {
        FabricDefaultAttributeRegistry.register(ELDERWING_DRAGON, ElderWingDragonEntity.createAttributes());
    }
}