package com.chris.vanilla_expansion;

import com.chris.vanilla_expansion.block.ModBlockEntities;
import com.chris.vanilla_expansion.block.ModBlocks;
import com.chris.vanilla_expansion.component.ModComponents;
import com.chris.vanilla_expansion.component.ModDataComponentTypes;
import com.chris.vanilla_expansion.entity.ModEntities;
import com.chris.vanilla_expansion.item.ModArmorMaterials;
import com.chris.vanilla_expansion.item.ModItemGroups;
import com.chris.vanilla_expansion.item.ModItems;
import com.chris.vanilla_expansion.item.custom.DragonArmorItem;
import com.chris.vanilla_expansion.item.custom.EnergyDragonArmorItem;
import com.chris.vanilla_expansion.networking.ModServerNetworking;
import com.chris.vanilla_expansion.screen.ModMenus;
import com.chris.vanilla_expansion.sound.ModSounds;
import com.chris.vanilla_expansion.world.gen.ModEntitySpawns;
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
    public static int MAX_STACK_SIZE = 16384;
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
        ModServerNetworking.register();
        ModSounds.registerSounds();

        ModEntities.registerModEntities();
        ModEntities.registerAttributes();
        ModEntitySpawns.registerModEntitySpawns();
        ModComponents.registerComponents();
        ModItems.registerModItems();
        ModItemGroups.register();
        ModDataComponentTypes.registerDataComponentTypes();

        ModItems.registerModItems();
        ModBlocks.registerModBlocks();
        ModBlockEntities.register();

        ModMenus.registerModMenus();
        ServerLivingEntityEvents.ALLOW_DAMAGE.register((entity, source, amount) -> {
            if (entity instanceof Player player) {
                if (source.is(DamageTypes.FALL)) {
                    if (DragonArmorItem.hasCorrectArmorOn(ModArmorMaterials.AIR_DRAGON_ARMOR_MATERIAL, player)) {
                        return false;
                    }
                }
            }
            return true;
        });
        EntityElytraEvents.CUSTOM.register((entity, tickElytra) -> {
            if (entity instanceof net.minecraft.world.entity.player.Player player) {
                ItemStack chest = player.getItemBySlot(EquipmentSlot.CHEST);

                if (chest.getItem() instanceof EnergyDragonArmorItem) {
                    if (chest.getDamageValue() < chest.getMaxDamage() - 1) {
                        return true;
                    }
                }
            }
            return false;
        });
    }
}