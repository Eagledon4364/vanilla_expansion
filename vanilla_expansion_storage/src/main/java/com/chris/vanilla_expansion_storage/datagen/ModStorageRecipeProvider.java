package com.chris.vanilla_expansion_storage.datagen;

import com.chris.vanilla_expansion_storage.block.ModStorageBlocks;
import com.chris.vanilla_expansion_storage.item.ModStorageItems;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.advancements.Advancement;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Recipe;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class ModStorageRecipeProvider extends FabricRecipeProvider {

    public ModStorageRecipeProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected RecipeProvider createRecipeProvider(HolderLookup.Provider provider, BootstrapContext<Recipe<?>> bootstrapContext, BootstrapContext<Advancement> bootstrapContext1) {
        return new RecipeProvider(bootstrapContext, bootstrapContext1) {
            @Override
            public void buildRecipes() {
                shaped(RecipeCategory.MISC, ModStorageBlocks.STORAGE_CONTROLLER, 1)
                        .pattern("SES")
                        .pattern("ENE")
                        .pattern("SES")
                        .define('N', Items.NETHERITE_INGOT)
                        .define('E', Items.REDSTONE_BLOCK)
                        .define('S', Items.IRON_BLOCK)
                        .unlockedBy(getHasName(Items.IRON_BLOCK), has(Items.IRON_BLOCK))
                        .save(output);

                shaped(RecipeCategory.MISC, ModStorageBlocks.STORAGE_TRIM, 4)
                        .pattern("SES")
                        .pattern("EIE")
                        .pattern("SES")
                        .define('I', Items.IRON_NUGGET)
                        .define('E', Items.REDSTONE)
                        .define('S', Items.IRON_INGOT)
                        .unlockedBy(getHasName(Items.IRON_INGOT), has(Items.IRON_INGOT))
                        .save(output);

                shaped(RecipeCategory.MISC, ModStorageBlocks.STORAGE_INTERFACE, 1)
                        .pattern("SES")
                        .pattern("EIE")
                        .pattern("SES")
                        .define('I', Items.DIAMOND)
                        .define('E', Items.COPPER_INGOT)
                        .define('S', Items.REDSTONE_BLOCK)
                        .unlockedBy(getHasName(Items.REDSTONE), has(Items.REDSTONE))
                        .save(output);

                shaped(RecipeCategory.MISC, ModStorageItems.STORAGE_BLOCK_UPGRADE, 1)
                        .pattern("SES")
                        .pattern("EDE")
                        .pattern("SES")
                        .define('D', Items.IRON_INGOT)
                        .define('E', Items.GOLD_INGOT)
                        .define('S', Items.LEATHER)
                        .unlockedBy(getHasName(Items.LEATHER), has(Items.LEATHER))
                        .save(output);

                shaped(RecipeCategory.MISC, ModStorageItems.KEY, 1)
                        .pattern("N")
                        .pattern("N")
                        .pattern("I")
                        .define('N', Items.GOLD_NUGGET)
                        .define('I', Items.GOLD_INGOT)
                        .unlockedBy(getHasName(Items.GOLD_INGOT), has(Items.GOLD_INGOT))
                        .save(output);

                shaped(RecipeCategory.MISC, ModStorageBlocks.CRAFTING_INTERFACE, 1)
                        .pattern("N")
                        .pattern("S")
                        .pattern("C")
                        .define('S', ModStorageBlocks.STORAGE_INTERFACE)
                        .define('C', Items.CRAFTING_TABLE)
                        .define('N', Items.NETHERITE_INGOT)
                        .unlockedBy(getHasName(ModStorageBlocks.STORAGE_INTERFACE), has(ModStorageBlocks.STORAGE_INTERFACE))
                        .save(output);

                shaped(RecipeCategory.MISC, ModStorageBlocks.STORAGE_CABLE, 6)
                        .pattern("R")
                        .pattern("C")
                        .pattern("G")
                        .define('R', Items.REDSTONE)
                        .define('C', Items.COPPER_INGOT)
                        .define('G', Items.GLASS)
                        .unlockedBy(getHasName(Items.REDSTONE), has(Items.REDSTONE))
                        .save(output);

                shaped(RecipeCategory.MISC, ModStorageBlocks.STORAGE_IMPORTER, 1)
                        .pattern(" H ")
                        .pattern("CSC")
                        .pattern(" R ")
                        .define('R', Items.REDSTONE)
                        .define('C', Items.COPPER_INGOT)
                        .define('H', Items.HOPPER)
                        .define('S', ModStorageBlocks.STORAGE_CABLE)
                        .unlockedBy(getHasName(Items.REDSTONE), has(Items.REDSTONE))
                        .save(output);

                shaped(RecipeCategory.MISC, ModStorageBlocks.STORAGE_EXPORTER, 1)
                        .pattern(" R ")
                        .pattern("CSC")
                        .pattern(" H ")
                        .define('R', Items.REDSTONE)
                        .define('H', Items.HOPPER)
                        .define('C', Items.COPPER_INGOT)
                        .define('S', ModStorageBlocks.STORAGE_CABLE)
                        .unlockedBy(getHasName(Items.REDSTONE), has(Items.REDSTONE))
                        .save(output);

            }
        };
    }






    @Override
    public @NotNull String getName() {
        return "Vanilla Expansion Storage Recipes";
    }
}