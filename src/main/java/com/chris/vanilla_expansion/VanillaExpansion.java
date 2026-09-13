package com.chris.vanilla_expansion;

import com.chris.vanilla_expansion.block.ModBlockEntities;
import com.chris.vanilla_expansion.block.ModBlocks;
import com.chris.vanilla_expansion.component.CoreAffinityComponent;
import com.chris.vanilla_expansion.component.ModDataComponentTypes;
import com.chris.vanilla_expansion.config.VanillaExpansionConfig;
import com.chris.vanilla_expansion.entity.ModEntities;
import com.chris.vanilla_expansion.event.ModLootTableEvents;
import com.chris.vanilla_expansion.item.ModArmorMaterials;
import com.chris.vanilla_expansion.item.ModItemGroups;
import com.chris.vanilla_expansion.item.ModItems;
import com.chris.vanilla_expansion.item.custom.DragonArmorItem;
import com.chris.vanilla_expansion.item.custom.EnergyDragonArmorItem;
import com.chris.vanilla_expansion.networking.ModServerNetworking;
import com.chris.vanilla_expansion.recipe.ModRecipes;
import com.chris.vanilla_expansion.screen.ModMenus;
import com.chris.vanilla_expansion.sound.ModSounds;
import com.chris.vanilla_expansion.util.ComponentEffects;
import com.chris.vanilla_expansion.util.DynamicAttributeHandler;
import com.chris.vanilla_expansion.world.gen.ModEntitySpawns;
import com.chris.vanilla_expansion.world.gen.ModWorldGeneration;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.EntityElytraEvents;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VanillaExpansion implements ModInitializer {
    public static final String MOD_ID = "vanilla_expansion";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static int MAX_STACK_SIZE = 16384;

    @Override
    public void onInitialize() {
        LOGGER.info("Initializing {}", MOD_ID);

        VanillaExpansionConfig.get();
        ModDataComponentTypes.registerComponents();
        CoreAffinityComponent.register();

        ModSounds.registerSounds();
        ModServerNetworking.register();

        ModItems.registerModItems();
        ModBlocks.registerModBlocks();
        ModBlockEntities.register();
        ModMenus.registerModMenus();
        ModItemGroups.register();
        ModRecipes.registerRecipes();

        ModEntities.registerModEntities();
        ModEntities.registerAttributes();
        ModEntitySpawns.registerModEntitySpawns();

        ComponentEffects.register();
        DynamicAttributeHandler.register();

        ModWorldGeneration.generateModWorldGen();
        ModLootTableEvents.registerEvents();

        registerEventCallbacks();
    }

    private void registerEventCallbacks() {
        // Fall Damage Cancellation via Dragon Armor
        ServerLivingEntityEvents.ALLOW_DAMAGE.register((entity, source, amount) -> {
            if (entity instanceof Player player && source.is(DamageTypes.FALL)) {
                if (DragonArmorItem.hasCorrectArmorOn(ModArmorMaterials.AIR_DRAGON_ARMOR_MATERIAL, player)) {
                    return false;
                }
            }
            return true;
        });

        // Custom Elytra Flight via Energy Dragon Armor
        EntityElytraEvents.CUSTOM.register((entity, tickElytra) -> {
            if (entity instanceof Player player) {
                ItemStack chest = player.getItemBySlot(EquipmentSlot.CHEST);
                if (chest.getItem() instanceof EnergyDragonArmorItem) {
                    return chest.getDamageValue() < chest.getMaxDamage() - 1;
                }
            }
            return false;
        });
    }
}