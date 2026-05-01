package com.chris.vanilla_expansion.screen;

import com.chris.vanilla_expansion.block.storage.StorageCrateBlockEntity;
import com.chris.vanilla_expansion.screen.backpack.BackpackMenu;
import com.chris.vanilla_expansion.screen.storage.StorageAccessMenu;
import com.chris.vanilla_expansion.screen.storage.StorageCrateMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.Nullable;


public class ModMenus {
    public static final MenuType<@NotNull DragonInventoryMenu> DRAGON_INVENTORY_MENU = register("dragon_inventory_menu", DragonInventoryMenu::new);


    public static final MenuType<@NotNull BackpackMenu> BACKPACK_MENU = register("backpack_menu", BackpackMenu::new);

    public static final MenuType<StorageAccessMenu> STORAGE_ACCESS_MENU =
            register("storage_access_menu", StorageAccessMenu::new);

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
