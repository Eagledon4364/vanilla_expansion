package com.chris.vanilla_expansion.entity.client;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.npc.InventoryCarrier;
import net.minecraft.world.entity.npc.Npc;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.Nullable;

public class NPCEntity extends AgeableMob implements Npc, InventoryCarrier {

    protected NPCEntity(EntityType<? extends AgeableMob> type, Level level) {
        super(type, level);
    }

    @Override
    public @Nullable AgeableMob getBreedOffspring(ServerLevel level, AgeableMob partner) {
        return null;
    }

    @Override
    public @NotNull SimpleContainer getInventory() {
        return new SimpleContainer(36);
    }
}
