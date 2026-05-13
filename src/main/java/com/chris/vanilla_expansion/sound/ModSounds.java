package com.chris.vanilla_expansion.sound;


import com.chris.vanilla_expansion.VanillaExpansion;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;

public class ModSounds {


    public static final SoundEvent ENERGY_DRAGON_FIRE = registerSoundEvent("energy_dragon_fire");

    public static final SoundEvent DRAGON_WING_FLAP_1 = registerSoundEvent("dragon_wing_flap_1");
    public static final SoundEvent DRAGON_WING_FLAP_2 = registerSoundEvent("dragon_wing_flap_2");

    public static final SoundEvent DRAGON_ROAR = registerSoundEvent("dragon_roar");
    public static final SoundEvent DEEP_DRAGON_ROAR = registerSoundEvent("deep_dragon_roar");

    public static final SoundEvent DRAGON_GROWL = registerSoundEvent("dragon_growl");
    public static final SoundEvent DRAGON_GROWL1 = registerSoundEvent("dragon_growl_1");



    private static SoundEvent registerSoundEvent(String name) {
        Identifier id = Identifier.fromNamespaceAndPath(VanillaExpansion.MOD_ID, name);
        return Registry.register(BuiltInRegistries.SOUND_EVENT, id, SoundEvent.createVariableRangeEvent(id));
    }


    public static void registerSounds() {

    }
}
