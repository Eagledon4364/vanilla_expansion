package com.chris.vanilla_expansion.util;


import com.chris.vanilla_expansion.VanillaExpansion;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;


public class ModTags {
    public static class Blocks {
        public static final TagKey<Block> PAXEL_MINEABLE = createTag("paxel_mineable");


        private static TagKey<@NotNull Block> createTag(String name) {
            return TagKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(VanillaExpansion.MOD_ID, name));
        }
    }


    public static class Items {
        public static final TagKey<@NotNull Item> PAXEL_REPAIR = createTag("paxel_repair");

        private static TagKey<@NotNull Item> createTag(String name) {
            return TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(VanillaExpansion.MOD_ID, name));
        }
        public static final TagKey<@NotNull Item> STEEL_REPAIRABLE = createTag("steel_repairable");
    }
}
