package com.chris.vanilla_expansion.networking;

import com.chris.vanilla_expansion.entity.server.DragonAnimal;
import com.chris.vanilla_expansion.item.inventory.ItemStackUpgradeInventory;
import com.chris.vanilla_expansion.item.ModItems;
import com.chris.vanilla_expansion.component.ModDataComponentTypes;
import com.chris.vanilla_expansion.item.custom.BackpackItem;
import com.chris.vanilla_expansion.item.inventory.ItemStackInventory;
import com.chris.vanilla_expansion.screen.backpack.BackpackMenu;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class ModServerNetworking {

    public static void register() {
        ServerPlayNetworking.registerGlobalReceiver(BackpackOpenPayload.TYPE, (payload, context) -> {
            context.server().execute(() -> {
                ServerPlayer player = context.player();


                if (!player.isAlive() || player.isRemoved()) return;

                ItemStack backpackStack = player.getInventory().getItem(42);
                if (!(backpackStack.getItem() instanceof BackpackItem)) return;
                if (player.containerMenu instanceof BackpackMenu) {
                    player.closeContainer();
                    return;
                }

                if (backpackStack.getItem() instanceof BackpackItem) {
                    player.openMenu(new MenuProvider() {
                        @Override
                        public @NotNull Component getDisplayName() {
                            return Component.translatable("container.vanilla_expansion.backpack");
                        }

                        @Override
                        public AbstractContainerMenu createMenu(int id, @NotNull Inventory inv, Player p) {
                            ItemStackInventory mainInv = new ItemStackInventory(backpackStack, 54);
                            ItemStackUpgradeInventory upgradeInv = new ItemStackUpgradeInventory(backpackStack, 6);
                            return new BackpackMenu(id, inv, mainInv, upgradeInv);
                        }
                    });
                }
            });
        });



        ServerPlayNetworking.registerGlobalReceiver(MagnetTogglePayload.TYPE, (payload, context) -> {
            context.server().execute(() -> {
                // Your logic to find Slot 41 and toggle the component
                ItemStack stack = context.player().getInventory().getItem(41);
                if (stack.is(ModItems.MAGNET)) {
                    boolean active = stack.getOrDefault(ModDataComponentTypes.IS_ACTIVE, false);
                    stack.set(ModDataComponentTypes.IS_ACTIVE, !active);

                    // Overlay feedback to confirm it worked
                    context.player().sendOverlayMessage(
                            Component.literal("Magnet: " + (active ? "§cOFF" : "§aON"))

                    );
                    //System.out.println("Packet Received!");
                }
            });
        });

        ServerPlayNetworking.registerGlobalReceiver(DragonFirePayload.TYPE, (payload, context) -> {
            context.server().execute(() -> {
                ServerPlayer player = context.player();
                if (player.getVehicle() instanceof DragonAnimal dragon) {

                    if (dragon.isTame() && dragon.isSaddled()) {

                        dragon.performFireAttack(player);

                    } else {
                        player.sendOverlayMessage(Component.literal("§cYour dragon must be tamed and saddled!"));
                    }
                }
            });
        });
    }
}