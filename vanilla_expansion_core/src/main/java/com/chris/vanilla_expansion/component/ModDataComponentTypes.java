package com.chris.vanilla_expansion.component;

import com.chris.vanilla_expansion.VanillaExpansion;
import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.component.ItemContainerContents;
import org.jetbrains.annotations.NotNull;

import java.util.function.UnaryOperator;

public class ModDataComponentTypes {



    public static final DataComponentType<@NotNull ItemContainerContents> UPGRADE_DATA =
            register("upgrade_data", builder -> builder.persistent(ItemContainerContents.CODEC).cacheEncoding());


    public static final DataComponentType<@NotNull Boolean> IS_ENABLED =
            register("is_enabled", builder -> builder
                    .persistent(Codec.BOOL)
                    .networkSynchronized(ByteBufCodecs.BOOL)
            );


    //Used for the magnet item
    public static final DataComponentType<@NotNull Boolean> IS_ACTIVE = Registry.register(
            BuiltInRegistries.DATA_COMPONENT_TYPE,
            Identifier.fromNamespaceAndPath(VanillaExpansion.MOD_ID, "is_active"),
            DataComponentType.<Boolean>builder()
                    .persistent(Codec.BOOL)
                    .networkSynchronized(ByteBufCodecs.BOOL)
                    .build()
    );

    private static <T> DataComponentType<@NotNull T> register(String name, UnaryOperator<DataComponentType.Builder<@NotNull T>> builderOperator) {
        return Registry.register(BuiltInRegistries.DATA_COMPONENT_TYPE,
                Identifier.fromNamespaceAndPath(VanillaExpansion.MOD_ID, name),
                (builderOperator.apply(DataComponentType.builder())).build());
    }

    public static void registerComponents() {

    }
}