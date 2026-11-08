package com.chris.vanilla_expansion_storage;

import com.chris.vanilla_expansion_storage.datagen.*;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import org.jetbrains.annotations.NotNull;

public class VanillaExpansionStorageDataGenerator implements DataGeneratorEntrypoint {

    @Override
    public void onInitializeDataGenerator(@NotNull FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
        pack.addProvider(ModStorageBlockTagProvider::new);
        pack.addProvider(ModStorageModelProvider::new);
        pack.addProvider(ModStorageRecipeProvider::new);
        pack.addProvider(ModStorageBlockLootTableProvider::new);

    }

}