package com.chris.vanilla_expansion_storage.item;

import com.chris.vanilla_expansion.VanillaExpansion;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.ItemLore;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Function;

public class ModStorageItems {



    public static final Item KEY = registerItem("key", properties -> new Item(properties.stacksTo(1)
            .component(DataComponents.LORE, new ItemLore(List.of(
                    Component.literal("Used to lock the item type in a storage crate")
                            .withStyle(ChatFormatting.GRAY)
            )))
    ));

    public static final Item STORAGE_BLOCK_UPGRADE = registerItem("storage_block_upgrade", properties -> new Item(properties.stacksTo(1)));




    public static ResourceKey<@NotNull Item> getRK(Item item) {
        return BuiltInRegistries.ITEM.getResourceKey(item).get();
    }

    private static Item registerItem(String name, Function<Item.Properties, Item> function) {
        return Registry.register(BuiltInRegistries.ITEM, Identifier.fromNamespaceAndPath(VanillaExpansion.MOD_ID, name),
                function.apply(new Item.Properties().setId(ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(VanillaExpansion.MOD_ID, name)))));
    }

    public static void registerModItems() {
    }
}
