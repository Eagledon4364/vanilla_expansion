package com.chris.vanilla_expansion.datagen;

import com.chris.vanilla_expansion.VanillaExpansion;
import com.chris.vanilla_expansion.block.ModBlocks;
import com.chris.vanilla_expansion.item.ModItems;
import com.chris.vanilla_expansion.recipe.CorelessToolCraftingRecipe;
import com.chris.vanilla_expansion.recipe.ToolCraftingRecipe;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.NotNull;

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
                shapeless(RecipeCategory.FOOD, ModItems.BLUEBERRY, 1)
                        .requires(Items.SWEET_BERRIES).unlockedBy(getHasName(Items.SWEET_BERRIES), has(Items.SWEET_BERRIES)).save(output);

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

                shaped(RecipeCategory.MISC, ModBlocks.SAND_GENERATOR_BLOCK, 1)
                        .pattern("SES")
                        .pattern("EIE")
                        .pattern("SES")
                        .define('I', Items.IRON_INGOT)
                        .define('E', Blocks.OAK_PLANKS)
                        .define('S', Items.COPPER_INGOT)
                        .unlockedBy(getHasName(Items.IRON_INGOT), has(Items.IRON_INGOT))
                        .save(output);

                shaped(RecipeCategory.MISC, ModBlocks.STORAGE_CONTROLLER, 1)
                        .pattern("SES")
                        .pattern("ENE")
                        .pattern("SES")
                        .define('N', Items.NETHERITE_BLOCK)
                        .define('E', Items.REDSTONE_BLOCK)
                        .define('S', Items.IRON_BLOCK)
                        .unlockedBy(getHasName(Items.IRON_BLOCK), has(Items.IRON_BLOCK))
                        .save(output);

                shaped(RecipeCategory.MISC, ModBlocks.STORAGE_TRIM, 4)
                        .pattern("SES")
                        .pattern("EIE")
                        .pattern("SES")
                        .define('I', Items.DIAMOND)
                        .define('E', Items.REDSTONE)
                        .define('S', Items.IRON_INGOT)
                        .unlockedBy(getHasName(Items.IRON_INGOT), has(Items.IRON_INGOT))
                        .save(output);

                shaped(RecipeCategory.MISC, ModBlocks.STORAGE_INTERFACE, 1)
                        .pattern("SES")
                        .pattern("EIE")
                        .pattern("SES")
                        .define('I', Items.DIAMOND_BLOCK)
                        .define('E', Items.COPPER_INGOT)
                        .define('S', Items.REDSTONE_BLOCK)
                        .unlockedBy(getHasName(Items.REDSTONE), has(Items.REDSTONE))
                        .save(output);

                shaped(RecipeCategory.MISC, ModItems.STORAGE_BLOCK_UPGRADE, 1)
                        .pattern("SES")
                        .pattern("EDE")
                        .pattern("SES")
                        .define('D', Items.DIAMOND)
                        .define('E', Items.GOLD_INGOT)
                        .define('S', Items.LEATHER)
                        .unlockedBy(getHasName(Items.LEATHER), has(Items.LEATHER))
                        .save(output);

                shaped(RecipeCategory.MISC, ModItems.KEY, 1)
                        .pattern("N")
                        .pattern("N")
                        .pattern("I")
                        .define('N', Items.GOLD_NUGGET)
                        .define('I', Items.GOLD_INGOT)
                        .unlockedBy(getHasName(Items.GOLD_INGOT), has(Items.GOLD_INGOT))
                        .save(output);

                shaped(RecipeCategory.MISC, ModBlocks.CRAFTING_INTERFACE, 1)
                        .pattern("N")
                        .pattern("S")
                        .pattern("C")
                        .define('S', ModBlocks.STORAGE_INTERFACE)
                        .define('C', Items.CRAFTING_TABLE)
                        .define('N', Items.NETHERITE_INGOT)
                        .unlockedBy(getHasName(ModBlocks.STORAGE_INTERFACE), has(ModBlocks.STORAGE_INTERFACE))
                        .save(output);

                shaped(RecipeCategory.MISC, ModBlocks.TOOL_CRAFTING_STATION, 1)
                        .pattern("WIW")
                        .pattern("SWS")
                        .pattern("SWS")
                        .define('I', Items.IRON_INGOT)
                        .define('S', ModItems.STEEL_INGOT)
                        .define('W', ItemTags.PLANKS)
                        .unlockedBy(getHasName(ModItems.STEEL_INGOT), has(ModItems.STEEL_INGOT))
                        .save(output);

                shaped(RecipeCategory.MISC, ModItems.PAXEL_TOOL_HEAD, 1)
                        .pattern("SFS")
                        .pattern("ACE")
                        .pattern("SWS")
                        .define('C', ModItems.ENERGY_CORE)
                        .define('S', ModBlocks.STEEL_BLOCK)
                        .define('F', ModItems.FIRE_CORE)
                        .define('E', ModItems.EARTH_CORE)
                        .define('A', ModItems.AIR_CORE)
                        .define('W', ModItems.WATER_CORE)
                        .unlockedBy(getHasName(ModItems.STEEL_INGOT), has(ModItems.STEEL_INGOT))
                        .save(output);

                shaped(RecipeCategory.MISC, ModItems.STEEL_HOE_TOOL_HEAD, 1)
                        .pattern("CC")
                        .define('C', ModItems.STEEL_INGOT)
                        .unlockedBy(getHasName(ModItems.STEEL_INGOT), has(ModItems.STEEL_INGOT))
                        .save(output);

                shaped(RecipeCategory.MISC, ModItems.STEEL_SHOVEL_TOOL_HEAD, 1)
                        .pattern("C")
                        .define('C', ModItems.STEEL_INGOT)
                        .unlockedBy(getHasName(ModItems.STEEL_INGOT), has(ModItems.STEEL_INGOT))
                        .save(output);

                shaped(RecipeCategory.MISC, ModItems.STEEL_SWORD_TOOL_HEAD, 1)
                        .pattern("C")
                        .pattern("C")
                        .define('C', ModItems.STEEL_INGOT)
                        .unlockedBy(getHasName(ModItems.STEEL_INGOT), has(ModItems.STEEL_INGOT))
                        .save(output);

                shaped(RecipeCategory.MISC, ModItems.STEEL_AXE_TOOL_HEAD, 1)
                        .pattern("CC")
                        .pattern("C ")
                        .define('C', ModItems.STEEL_INGOT)
                        .unlockedBy(getHasName(ModItems.STEEL_INGOT), has(ModItems.STEEL_INGOT))
                        .save(output);

                shaped(RecipeCategory.MISC, ModItems.STEEL_PICKAXE_TOOL_HEAD, 1)
                        .pattern("CCC")
                        .define('C', ModItems.STEEL_INGOT)
                        .unlockedBy(getHasName(ModItems.STEEL_INGOT), has(ModItems.STEEL_INGOT))
                        .save(output);

                // Basic Tool Recipes (No Core Needed)
                offerCorelessToolCraftingRecipe(output, ModItems.STEEL_SWORD_TOOL_HEAD, Items.STICK, ModItems.STEEL_SWORD);
                offerCorelessToolCraftingRecipe(output, ModItems.STEEL_PICKAXE_TOOL_HEAD, Items.STICK, ModItems.STEEL_PICKAXE);
                offerCorelessToolCraftingRecipe(output, ModItems.STEEL_AXE_TOOL_HEAD, Items.STICK, ModItems.STEEL_AXE);
                offerCorelessToolCraftingRecipe(output, ModItems.STEEL_SHOVEL_TOOL_HEAD, Items.STICK, ModItems.STEEL_SHOVEL);
                offerCorelessToolCraftingRecipe(output, ModItems.STEEL_HOE_TOOL_HEAD, Items.STICK, ModItems.STEEL_HOE);
                offerCorelessToolCraftingRecipe(output, ModItems.PAXEL_TOOL_HEAD, Items.STICK, ModItems.PAXEL);

                // Core Tool Recipes (Generates distinct recipe IDs per core type)
                ItemLike[] cores = {
                        ModItems.FIRE_CORE, ModItems.EARTH_CORE, ModItems.WATER_CORE,
                        ModItems.AIR_CORE, ModItems.ENERGY_CORE
                };

                for (ItemLike core : cores) {
                    offerToolCraftingRecipe(output, ModItems.STEEL_SWORD_TOOL_HEAD, Items.STICK, core, ModItems.STEEL_SWORD);
                    offerToolCraftingRecipe(output, ModItems.STEEL_PICKAXE_TOOL_HEAD, Items.STICK, core, ModItems.STEEL_PICKAXE);
                    offerToolCraftingRecipe(output, ModItems.STEEL_AXE_TOOL_HEAD, Items.STICK, core, ModItems.STEEL_AXE);
                    offerToolCraftingRecipe(output, ModItems.STEEL_SHOVEL_TOOL_HEAD, Items.STICK, core, ModItems.STEEL_SHOVEL);
                    offerToolCraftingRecipe(output, ModItems.STEEL_HOE_TOOL_HEAD, Items.STICK, core, ModItems.STEEL_HOE);
                    offerToolCraftingRecipe(output, ModItems.PAXEL_TOOL_HEAD, Items.STICK, core, ModItems.PAXEL);
                }
            }

            private void offerCorelessToolCraftingRecipe(RecipeOutput recipeOutput, ItemLike head, ItemLike handle, ItemLike result) {
                CorelessToolCraftingRecipe recipe = new CorelessToolCraftingRecipe(
                        Ingredient.of(head),
                        Ingredient.of(handle),
                        new ItemStackTemplate(result.asItem())
                );

                recipeOutput.accept(
                        ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(VanillaExpansion.MOD_ID, getItemName(result) + "_from_coreless_tool_crafting")),
                        recipe,
                        null
                );
            }

            private void offerToolCraftingRecipe(RecipeOutput recipeOutput, ItemLike head, ItemLike handle, ItemLike core, ItemLike result) {
                ToolCraftingRecipe recipe = new ToolCraftingRecipe(
                        Ingredient.of(head),
                        Ingredient.of(handle),
                        Ingredient.of(core),
                        new ItemStackTemplate(result.asItem())
                );

                // Include the core name in the ResourceKey to guarantee unique recipe IDs per core combination
                String recipePath = getItemName(result) + "_with_" + getItemName(core) + "_from_tool_crafting";

                recipeOutput.accept(
                        ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(VanillaExpansion.MOD_ID, recipePath)),
                        recipe,
                        null
                );
            }
        };
    }

    @Override
    public String getName() {
        return "Vanilla Expansion Recipes";
    }
}