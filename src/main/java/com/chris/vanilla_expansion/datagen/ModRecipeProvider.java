package com.chris.vanilla_expansion.datagen;

import com.chris.vanilla_expansion.block.ModBlocks;
import com.chris.vanilla_expansion.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.StonecutterRecipe;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends FabricRecipeProvider {
    public ModRecipeProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected @NotNull RecipeProvider createRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
        return new RecipeProvider(registries, output) {
            @Override
            public void buildRecipes() {
                Block[] markers = {
                        ModBlocks.RED_MARKER,    ModBlocks.YELLOW_MARKER, ModBlocks.BLUE_MARKER,
                        ModBlocks.CYAN_MARKER,  ModBlocks.GREEN_MARKER,  ModBlocks.MAGENTA_MARKER
                };
                for (Block marker : markers) {
                    stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, marker, Items.WHEAT_SEEDS, 4);

                }
                shaped(RecipeCategory.MISC, ModItems.ENERGY_DRAGON_ARMOR_UPGRADE, 4)
                        .pattern("SES")
                        .pattern("NNN")
                        .pattern("SCS")
                        .define('N', Items.NETHERITE_BLOCK)
                        .define('E', Items.ELYTRA)
                        .define('S', ModItems.ENERGY_DRAGON_SCALE)
                        .define('C', ModItems.ENERGY_CORE)
                        .unlockedBy(getHasName(ModItems.ENERGY_DRAGON_SCALE), has(ModItems.ENERGY_DRAGON_SCALE))
                        .save(output);

                shaped(RecipeCategory.MISC, ModItems.FIRE_DRAGON_ARMOR_UPGRADE, 4)
                        .pattern("SSS")
                        .pattern("SNS")
                        .pattern("SCS")
                        .define('N', Items.NETHERITE_INGOT)
                        .define('S', ModItems.FIRE_DRAGON_SCALE)
                        .define('C', ModItems.FIRE_CORE)
                        .unlockedBy(getHasName(ModItems.FIRE_DRAGON_SCALE), has(ModItems.FIRE_DRAGON_SCALE))
                        .save(output);
                shaped(RecipeCategory.MISC, ModItems.AIR_DRAGON_ARMOR_UPGRADE, 4)
                        .pattern("SSS")
                        .pattern("SNS")
                        .pattern("SCS")
                        .define('N', Items.NETHERITE_INGOT)
                        .define('S', ModItems.AIR_DRAGON_SCALE)
                        .define('C', ModItems.AIR_CORE)
                        .unlockedBy(getHasName(ModItems.AIR_DRAGON_SCALE), has(ModItems.AIR_DRAGON_SCALE))
                        .save(output);
                shaped(RecipeCategory.MISC, ModItems.WATER_DRAGON_ARMOR_UPGRADE, 4)
                        .pattern("SSS")
                        .pattern("SNS")
                        .pattern("SCS")
                        .define('N', Items.NETHERITE_INGOT)
                        .define('S', ModItems.WATER_DRAGON_SCALE)
                        .define('C', ModItems.WATER_CORE)
                        .unlockedBy(getHasName(ModItems.WATER_DRAGON_SCALE), has(ModItems.WATER_DRAGON_SCALE))
                        .save(output);
                shaped(RecipeCategory.MISC, ModItems.EARTH_DRAGON_ARMOR_UPGRADE, 4)
                        .pattern("SSS")
                        .pattern("SNS")
                        .pattern("SCS")
                        .define('N', Items.NETHERITE_INGOT)
                        .define('S', ModItems.EARTH_DRAGON_SCALE)
                        .define('C', ModItems.EARTH_CORE)
                        .unlockedBy(getHasName(ModItems.EARTH_DRAGON_SCALE), has(ModItems.EARTH_DRAGON_SCALE))
                        .save(output);


            }
        };
    }

    @Override
    public String getName() {
        return "Vanilla Expansion Recipes";
    }
}
