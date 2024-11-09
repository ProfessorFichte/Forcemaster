package net.forcemaster_rpg.sounds;

import net.minecraft.client.render.entity.model.BreezeEntityModel;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

import static net.forcemaster_rpg.ForcemasterClassMod.MOD_ID;

public class ModSounds {
    public static final Identifier KNUCKLE_ATTACK_ID = Identifier.of(MOD_ID, "knuckle_attack");
    public static SoundEvent KNUCKLE_ATTACK_EVENT = SoundEvent.of(KNUCKLE_ATTACK_ID);
    public static final Identifier ASAL_RELEASE_ID = Identifier.of(MOD_ID, "asal_release");
    public static SoundEvent ASAL_RELEASE_EVENT = SoundEvent.of(ASAL_RELEASE_ID);
    public static final Identifier STONEHAND_CAST_ID = Identifier.of(MOD_ID, "stonehand_cast");
    public static SoundEvent STONEHAND_CAST_EVENT = SoundEvent.of(STONEHAND_CAST_ID);
    public static final Identifier KNUCKLE_SPELL_CAST_ID = Identifier.of(MOD_ID, "knuckle_spell_cast");
    public static SoundEvent KNUCKLE_SPELL_CAST_EVENT = SoundEvent.of(KNUCKLE_SPELL_CAST_ID);
    public static final Identifier KNUCKLE_SPELL_IMPACT_ID = Identifier.of(MOD_ID, "knuckle_spell_impact");
    public static SoundEvent KNUCKLE_SPELL_IMPACT_EVENT = SoundEvent.of(KNUCKLE_SPELL_IMPACT_ID);
    public static final Identifier SONIC_HAND_ID = Identifier.of(MOD_ID, "sonic_hand");
    public static SoundEvent SONIC_HAND_EVENT = SoundEvent.of(SONIC_HAND_ID);

    public static void register() {
        Registry.register(Registries.SOUND_EVENT, KNUCKLE_ATTACK_ID, KNUCKLE_ATTACK_EVENT);
        Registry.register(Registries.SOUND_EVENT, ASAL_RELEASE_ID, ASAL_RELEASE_EVENT);
        Registry.register(Registries.SOUND_EVENT, STONEHAND_CAST_ID, STONEHAND_CAST_EVENT);
        Registry.register(Registries.SOUND_EVENT, KNUCKLE_SPELL_CAST_ID, KNUCKLE_SPELL_CAST_EVENT);
        Registry.register(Registries.SOUND_EVENT, KNUCKLE_SPELL_IMPACT_ID, KNUCKLE_SPELL_IMPACT_EVENT);
        Registry.register(Registries.SOUND_EVENT, SONIC_HAND_ID, SONIC_HAND_EVENT);

    }
}