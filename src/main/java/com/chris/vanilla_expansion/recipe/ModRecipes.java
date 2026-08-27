package com.chris.vanilla_expansion.recipe;

import com.chris.vanilla_expansion.VanillaExpansion;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

public class ModRecipes {

    // --- Standard Core Tool Crafting Registration ---
    public static final RecipeType<ToolCraftingRecipe> TOOL_CRAFTING_TYPE = Registry.register(
            BuiltInRegistries.RECIPE_TYPE,
            Identifier.fromNamespaceAndPath(VanillaExpansion.MOD_ID, "tool_crafting"),
            new RecipeType<ToolCraftingRecipe>() {
                @Override
                public String toString() {
                    return VanillaExpansion.MOD_ID + ":tool_crafting";
                }
            }
    );

    private static final MapCodec<ToolCraftingRecipe> CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(
                    Ingredient.CODEC.fieldOf("head").forGetter(ToolCraftingRecipe::getToolHead),
                    Ingredient.CODEC.fieldOf("handle").forGetter(ToolCraftingRecipe::getHandle),
                    Ingredient.CODEC.fieldOf("core").forGetter(ToolCraftingRecipe::getElementCore),
                    ItemStackTemplate.MAP_CODEC.fieldOf("result").forGetter(ToolCraftingRecipe::getResultTemplate)
            ).apply(instance, ToolCraftingRecipe::new)
    );

    private static final StreamCodec<RegistryFriendlyByteBuf, ToolCraftingRecipe> STREAM_CODEC = StreamCodec.of(
            (buf, recipe) -> {
                Ingredient.CONTENTS_STREAM_CODEC.encode(buf, recipe.getToolHead());
                Ingredient.CONTENTS_STREAM_CODEC.encode(buf, recipe.getHandle());
                Ingredient.CONTENTS_STREAM_CODEC.encode(buf, recipe.getElementCore());
                ItemStackTemplate.STREAM_CODEC.encode(buf, recipe.getResultTemplate());
            },
            buf -> new ToolCraftingRecipe(
                    Ingredient.CONTENTS_STREAM_CODEC.decode(buf),
                    Ingredient.CONTENTS_STREAM_CODEC.decode(buf),
                    Ingredient.CONTENTS_STREAM_CODEC.decode(buf),
                    ItemStackTemplate.STREAM_CODEC.decode(buf)
            )
    );

    public static final RecipeSerializer<ToolCraftingRecipe> TOOL_CRAFTING_SERIALIZER = Registry.register(
            BuiltInRegistries.RECIPE_SERIALIZER,
            Identifier.fromNamespaceAndPath(VanillaExpansion.MOD_ID, "tool_crafting"),
            new RecipeSerializer<>(CODEC, STREAM_CODEC)
    );


    // --- Coreless Tool Crafting Registration ---
    public static final RecipeType<CorelessToolCraftingRecipe> CORELESS_TOOL_CRAFTING_TYPE = Registry.register(
            BuiltInRegistries.RECIPE_TYPE,
            Identifier.fromNamespaceAndPath(VanillaExpansion.MOD_ID, "coreless_tool_crafting"),
            new RecipeType<CorelessToolCraftingRecipe>() {
                @Override
                public String toString() {
                    return VanillaExpansion.MOD_ID + ":coreless_tool_crafting";
                }
            }
    );

    private static final MapCodec<CorelessToolCraftingRecipe> CORELESS_CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(
                    Ingredient.CODEC.fieldOf("head").forGetter(CorelessToolCraftingRecipe::getToolHead),
                    Ingredient.CODEC.fieldOf("handle").forGetter(CorelessToolCraftingRecipe::getHandle),
                    ItemStackTemplate.MAP_CODEC.fieldOf("result").forGetter(CorelessToolCraftingRecipe::getResultTemplate)
            ).apply(instance, CorelessToolCraftingRecipe::new)
    );

    private static final StreamCodec<RegistryFriendlyByteBuf, CorelessToolCraftingRecipe> CORELESS_STREAM_CODEC = StreamCodec.of(
            (buf, recipe) -> {
                Ingredient.CONTENTS_STREAM_CODEC.encode(buf, recipe.getToolHead());
                Ingredient.CONTENTS_STREAM_CODEC.encode(buf, recipe.getHandle());
                ItemStackTemplate.STREAM_CODEC.encode(buf, recipe.getResultTemplate());
            },
            buf -> new CorelessToolCraftingRecipe(
                    Ingredient.CONTENTS_STREAM_CODEC.decode(buf),
                    Ingredient.CONTENTS_STREAM_CODEC.decode(buf),
                    ItemStackTemplate.STREAM_CODEC.decode(buf)
            )
    );

    public static final RecipeSerializer<CorelessToolCraftingRecipe> CORELESS_TOOL_CRAFTING_SERIALIZER = Registry.register(
            BuiltInRegistries.RECIPE_SERIALIZER,
            Identifier.fromNamespaceAndPath(VanillaExpansion.MOD_ID, "coreless_tool_crafting"),
            new RecipeSerializer<>(CORELESS_CODEC, CORELESS_STREAM_CODEC)
    );

    public static void registerRecipes() {}
}