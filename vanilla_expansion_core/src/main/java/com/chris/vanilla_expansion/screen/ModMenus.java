package com.chris.vanilla_expansion.screen;

import com.chris.vanilla_expansion.VanillaExpansion;
import com.chris.vanilla_expansion.screen.backpack.BackpackMenu;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import org.jetbrains.annotations.NotNull;

public class ModMenus {

    public static final MenuType<@NotNull BackpackMenu> BACKPACK_MENU =
            register("backpack_menu", BackpackMenu::new);
    public static final MenuType<@NotNull ToolCraftingStationMenu> TOOL_CRAFTING_STATION_MENU =
            register("tool_crafting_station_menu", ToolCraftingStationMenu::new);


    public static <T extends AbstractContainerMenu> MenuType<@NotNull T> register(
            String name,
            MenuType.MenuSupplier<@NotNull T> constructor
    ) {
        return Registry.register(
                BuiltInRegistries.MENU,
                Identifier.fromNamespaceAndPath(VanillaExpansion.MOD_ID, name),
                new MenuType<>(constructor, FeatureFlagSet.of())
        );
    }

    public static void registerModMenus() {

    }
}