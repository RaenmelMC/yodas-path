package dev.raenmel.yodaspath.sound;

import dev.raenmel.yodaspath.YodaSPath;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class ModSounds {

    public static final SoundEvent LIGHT_SABER_HIT =
            registerSoundEvent("light_saber_hit");

    public static final SoundEvent LIGHT_SABER_ON =
            registerSoundEvent("light_saber_on");

    public static final SoundEvent LIGHT_SABER_OFF =
            registerSoundEvent("light_saber_off");

    public static final SoundEvent LIGHT_SABER_DEFLECT =
            registerSoundEvent("light_saber_deflect");

    public static final SoundEvent BLASTER_SHOOT =
            registerSoundEvent("blaster_shoot");

    public static final SoundEvent JAWA_AMBIENT =
            registerSoundEvent("entity.jawa.ambient");

    private static SoundEvent registerSoundEvent(String name) {
        Identifier id = Identifier.of(YodaSPath.MOD_ID, name);

        return Registry.register(
                Registries.SOUND_EVENT,
                id,
                SoundEvent.of(id)
        );
    }


    public static void initialize() {
        YodaSPath.LOGGER.info(
                "Registering sounds for {}",
                YodaSPath.MOD_ID
        );
    }
}