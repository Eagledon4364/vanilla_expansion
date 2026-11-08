package com.chris.vanilla_expansion_storage.menu;

import com.chris.vanilla_expansion.VanillaExpansion;
import com.chris.vanilla_expansion_storage.VanillaExpansionStorage;
import com.chris.vanilla_expansion_storage.menu.storage.*;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import org.jetbrains.annotations.NotNull;

public class ModStorageMenus {

    public static final MenuType<@NotNull StorageInterfaceMenu> STORAGE_INTERFACE_MENU =
            register("storage_interface_menu", StorageInterfaceMenu::new);

    public static final MenuType<@NotNull CraftingInterfaceMenu> CRAFTING_INTERFACE_MENU =
            register("crafting_interface_menu", CraftingInterfaceMenu::new);

    public static final MenuType<@NotNull StorageImporterMenu> STORAGE_IMPORTER_MENU =
            register("storage_importer_menu", StorageImporterMenu::new);

    public static final MenuType<@NotNull StorageExporterMenu> STORAGE_EXPORTER_MENU =
            register("storage_exporter_menu", StorageExporterMenu::new);

    public static final MenuType<@NotNull StorageCrateMenu> STORAGE_CRATE_MENU = Registry.register(
            BuiltInRegistries.MENU,
            Identifier.fromNamespaceAndPath(VanillaExpansion.MOD_ID, "storage_crate_menu"),
            new MenuType<>(StorageCrateMenu::new, FeatureFlags.DEFAULT_FLAGS)
    );

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

    public static void registerModStorageMenus() {

    }

    public static void registerModMenus() {
    }
}
