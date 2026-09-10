package com.chris.vanilla_expansion.mixin;

import com.chris.vanilla_expansion.util.AnvilAffinityRecipeHandler;
import com.chris.vanilla_expansion.util.ArmorCraftingContext;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.ItemCombinerMenu;
import net.minecraft.world.inventory.ItemCombinerMenuSlotDefinition;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AnvilMenu.class)
public abstract class AnvilMenuMixin extends ItemCombinerMenu {

    @Unique
    public int repairItemCountSpent;

    protected AnvilMenuMixin(@Nullable MenuType<?> menuType, int containerId, Inventory inventory,
                             ContainerLevelAccess access, ItemCombinerMenuSlotDefinition itemInputSlots) {
        super(menuType, containerId, inventory, access, itemInputSlots);
    }

    @Inject(method = "createResult", at = @At("HEAD"), cancellable = true)
    private void injectAffinityCombine(CallbackInfo ci) {
        // 1. Capture context safely
        ItemStack baseItem = this.inputSlots.getItem(0);
        ArmorCraftingContext.setActiveStack(baseItem);

        AnvilMenu menu = (AnvilMenu) (Object) this;
        ItemStack left = menu.getSlot(0).getItem();
        ItemStack right = menu.getSlot(1).getItem();

        // 2. Process Custom Recipe
        if (AnvilAffinityRecipeHandler.processAnvilCombine(left, right, menu)) {
            // Tell the anvil to consume 1 item from the second slot on click
            this.repairItemCountSpent = 1;

            menu.broadcastChanges();

            // Clean context before cancelling execution
            ArmorCraftingContext.clear();
            ci.cancel();
        }
    }

    @Inject(method = "createResult", at = @At("TAIL"))
    private void clearAnvilInputStack(CallbackInfo ci) {
        // Clears context if vanilla recipe execution finishes
        ArmorCraftingContext.clear();
    }
}