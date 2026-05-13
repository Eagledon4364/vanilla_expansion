package com.chris.vanilla_expansion.datagen;

import com.chris.vanilla_expansion.item.ModItems;
import com.chris.vanilla_expansion.util.ModTags;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.ItemTags;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends FabricTagsProvider.ItemTagsProvider {

    public ModItemTagProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registryLookupFuture) {
        super(output, registryLookupFuture);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider registries) {
        valueLookupBuilder(ModTags.Items.DRAGON_FOOD)
                .forceAddTag(ItemTags.WOLF_FOOD);

        valueLookupBuilder(ModTags.Items.STEEL_REPAIRABLE)
                .add(ModItems.STEEL_INGOT);
        valueLookupBuilder(ModTags.Items.PAXEL_REPAIR)
                .add(ModItems.STEEL_INGOT);

        valueLookupBuilder(ItemTags.NETHERITE_TOOL_MATERIALS)
                .add(ModItems.ENERGY_DRAGON_SCALE)
                .add(ModItems.FIRE_DRAGON_SCALE)
                .add(ModItems.AIR_DRAGON_SCALE)
                .add(ModItems.WATER_DRAGON_SCALE)
                .add(ModItems.EARTH_DRAGON_SCALE);
    }
}
