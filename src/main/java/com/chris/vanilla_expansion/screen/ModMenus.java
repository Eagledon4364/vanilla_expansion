package com.chris.vanilla_expansion.screen;

import com.chris.vanilla_expansion.screen.backpack.BackpackMenu;
import com.chris.vanilla_expansion.screen.storage.CraftingInterfaceMenu;
import com.chris.vanilla_expansion.screen.storage.StorageCrateMenu;
import com.chris.vanilla_expansion.screen.storage.StorageInterfaceMenu;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import org.jetbrains.annotations.NotNull;


public class ModMenus {
    public static final MenuType<@NotNull DragonInventoryMenu> DRAGON_INVENTORY_MENU = register("dragon_inventory_menu", DragonInventoryMenu::new);


    public static final MenuType<@NotNull BackpackMenu> BACKPACK_MENU =
            register("backpack_menu", BackpackMenu::new);
    public static final MenuType<@NotNull ToolCraftingStationMenu> TOOL_CRAFTING_STATION_MENU =
            register("tool_crafting_station_menu", ToolCraftingStationMenu::new);


    public static final MenuType<@NotNull StorageInterfaceMenu> STORAGE_INTERFACE_MENU =
            register("storage_interface_menu", StorageInterfaceMenu::new);

    public static final MenuType<@NotNull CraftingInterfaceMenu> CRAFTING_INTERFACE_MENU =
            register("crafting_interface_menu", CraftingInterfaceMenu::new);


    public static final MenuType<@NotNull StorageCrateMenu> STORAGE_CRATE_MENU = Registry.register(
            BuiltInRegistries.MENU,
            Identifier.fromNamespaceAndPath("vanilla_expansion", "storage_crate_menu"),
            new MenuType<>(StorageCrateMenu::new, FeatureFlags.DEFAULT_FLAGS)
    );

    public static <T extends AbstractContainerMenu> MenuType<@NotNull T> register(
            String name,
            MenuType.MenuSupplier<@NotNull T> constructor
    ) {
        return Registry.register(BuiltInRegistries.MENU, name, new MenuType<>(constructor, FeatureFlagSet.of()));
    }

    public static void registerModMenus() {

    }
}
