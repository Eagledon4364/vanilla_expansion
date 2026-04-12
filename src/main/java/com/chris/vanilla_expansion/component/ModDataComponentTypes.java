package com.chris.vanilla_expansion.component;
import com.chris.vanilla_expansion.VanillaExpansion;
import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.resources.Identifier;

public class ModDataComponentTypes {

    public static final DataComponentType<Boolean> IS_ACTIVE = Registry.register(
            BuiltInRegistries.DATA_COMPONENT_TYPE,
            Identifier.fromNamespaceAndPath("yourmodid", "is_active"),
            DataComponentType.<Boolean>builder()
                    .persistent(Codec.BOOL)    // This replaces .codec()
                    .networkSynchronized(ByteBufCodecs.BOOL) // Ensures the client knows the state
                    .build()
    );
    public static void registerDataComponentTypes() {
        VanillaExpansion.LOGGER.info("Registering Data Component Types for " + VanillaExpansion.MOD_ID);
    }
}