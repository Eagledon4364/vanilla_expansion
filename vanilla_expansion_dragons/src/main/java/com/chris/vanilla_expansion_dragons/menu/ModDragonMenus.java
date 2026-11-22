package com.chris.vanilla_expansion_dragons.menu;

import com.chris.vanilla_expansion_dragons.VanillaExpansionDragons;
import com.chris.vanilla_expansion_dragons.entity.server.ElderWingDragonEntity;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.SimpleContainerData;
import org.jetbrains.annotations.NotNull;

public class ModDragonMenus {

    public static MenuType<DragonMainMenu> DRAGON_MAIN_MENU;
    public static MenuType<DragonSkillMenu> DRAGON_SKILL_MENU;

    public static <T extends AbstractContainerMenu> MenuType<@NotNull T> register(
            String name,
            MenuType.MenuSupplier<@NotNull T> constructor
    ) {
        return Registry.register(
                BuiltInRegistries.MENU,
                Identifier.fromNamespaceAndPath(VanillaExpansionDragons.MOD_ID, name),
                new MenuType<>(constructor, FeatureFlagSet.of())
        );
    }

    public static SimpleContainerData createMainData(ElderWingDragonEntity dragon) {
        SimpleContainerData data = new SimpleContainerData(4);
        //data.set(0, dragon.getDragonLevel());
        //data.set(1, dragon.getXp());
        //data.set(2, dragon.getXpForNextLevel(dragon.getDragonLevel()));
        //data.set(3, dragon.getSkillPoints());
        return data;
    }

    public static SimpleContainerData createSkillData(ElderWingDragonEntity dragon) {
        SimpleContainerData data = new SimpleContainerData(7);
        //data.set(0, dragon.getDragonLevel());
        //data.set(1, dragon.getSkillPoints());
        //data.set(2, dragon.getUpgradeCount(UpgradeStat.HEALTH));
        //data.set(3, dragon.getUpgradeCount(UpgradeStat.STAMINA));
        //data.set(4, dragon.getUpgradeCount(UpgradeStat.STRENGTH));
        //data.set(5, dragon.getUpgradeCount(UpgradeStat.SPEED));
        //data.set(6, dragon.getUpgradeCount(UpgradeStat.BREATH));
        return data;
    }

    public static void registerModMenus() {
        if (DRAGON_MAIN_MENU == null) {
            //DRAGON_MAIN_MENU = register("dragon_main_menu", DragonMainMenu::new);
        }
        if (DRAGON_SKILL_MENU == null) {
            //DRAGON_SKILL_MENU = register("dragon_skill_menu", DragonSkillMenu::new);
        }
    }
}