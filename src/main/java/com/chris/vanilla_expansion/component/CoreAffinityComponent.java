package com.chris.vanilla_expansion.component;

import com.chris.vanilla_expansion.VanillaExpansion;
import com.mojang.serialization.Codec;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipProvider;

import java.util.function.Consumer;
import java.util.function.IntFunction;

public record CoreAffinityComponent(Affinity affinity) implements TooltipProvider {

    public enum Affinity implements StringRepresentable {
        FIRE(0, "fire", ChatFormatting.RED),
        EARTH(1, "earth", ChatFormatting.GOLD),
        WATER(2, "water", ChatFormatting.DARK_BLUE),
        AIR(3, "air", ChatFormatting.BLUE),
        ENERGY(4, "energy", ChatFormatting.GREEN);

        public static final Codec<Affinity> CODEC = StringRepresentable.fromEnum(Affinity::values);

        private static final IntFunction<Affinity> BY_ID = ByIdMap.continuous(
                Affinity::getId,
                values(),
                ByIdMap.OutOfBoundsStrategy.ZERO
        );

        public static final StreamCodec<RegistryFriendlyByteBuf, Affinity> STREAM_CODEC =
                ByteBufCodecs.idMapper(BY_ID, Affinity::getId).cast();

        private final int id;
        private final String name;
        private final ChatFormatting color;

        Affinity(int id, String name, ChatFormatting color) {
            this.id = id;
            this.name = name;
            this.color = color;
        }

        public int getId() {
            return this.id;
        }

        @Override
        public String getSerializedName() {
            return this.name;
        }

        public ChatFormatting getColor() {
            return this.color;
        }
    }

    public static final Codec<CoreAffinityComponent> CODEC = Affinity.CODEC.xmap(CoreAffinityComponent::new, CoreAffinityComponent::affinity);

    public static final StreamCodec<RegistryFriendlyByteBuf, CoreAffinityComponent> STREAM_CODEC = Affinity.STREAM_CODEC.map(
            CoreAffinityComponent::new,
            CoreAffinityComponent::affinity
    );

    public static final DataComponentType<CoreAffinityComponent> KEY = DataComponentType.<CoreAffinityComponent>builder()
            .persistent(CODEC)
            .networkSynchronized(STREAM_CODEC)
            .build();

    public static void register() {
        Registry.register(
                BuiltInRegistries.DATA_COMPONENT_TYPE,
                Identifier.fromNamespaceAndPath(VanillaExpansion.MOD_ID, "core_affinity"),
                KEY
        );
    }

    @Override
    public void addToTooltip(Item.TooltipContext context, Consumer<Component> tooltipAdder, TooltipFlag flag, DataComponentGetter components) {
        String nameKey = "tooltip.vanilla_expansion.core_affinity." + this.affinity.getSerializedName();
        String effectKey = "tooltip.vanilla_expansion.affinity_effect." + this.affinity.getSerializedName();

        // Line 1: Title (e.g., "Fire Affinity")
        tooltipAdder.accept(Component.translatable(nameKey).withStyle(this.affinity.getColor()));

        // Line 2: Effect description (e.g., " Auto-Smelting")
        tooltipAdder.accept(Component.translatable(effectKey).withStyle(ChatFormatting.GRAY));
    }
}