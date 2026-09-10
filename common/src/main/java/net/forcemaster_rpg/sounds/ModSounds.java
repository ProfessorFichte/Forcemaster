package net.forcemaster_rpg.sounds;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static net.forcemaster_rpg.ForcemasterClassMod.MOD_ID;

public class ModSounds {
    public static class Entry {
        private final Identifier id;
        private final SoundEvent soundEvent;
        private RegistryEntry<SoundEvent> entry;
        private int variants = 1;

        public Entry(Identifier id, SoundEvent soundEvent) {
            this.id = id;
            this.soundEvent = soundEvent;
        }

        public Entry(String name) {
            this(new Identifier(MOD_ID, name));
        }

        public Entry(Identifier id) {
            this(id, SoundEvent.of(id));
        }

        public Entry travelDistance(float distance) {
            return new Entry(id, SoundEvent.of(id, distance));
        }

        public Entry variants(int variants) {
            this.variants = variants;
            return this;
        }

        public Identifier id() {
            return id;
        }

        public SoundEvent soundEvent() {
            return soundEvent;
        }

        public RegistryEntry<SoundEvent> entry() {
            return entry;
        }

        public int variants() {
            return variants;
        }
    }
    public static final List<Entry> entries = new ArrayList<>();
    public static Entry add(Entry entry) {
        entries.add(entry);
        return entry;
    }
    public static final Entry KNUCKLE_ATTACK = add(new Entry("knuckle_attack"));
    public static final Entry ASAL_RELEASE = add(new Entry("asal_release"));
    public static final Entry STONEHAND_CAST = add(new Entry("stonehand_cast"));
    public static final Entry KNUCKLE_SPELL_CAST = add(new Entry("knuckle_spell_cast"));
    public static final Entry KNUCKLE_SPELL_IMPACT = add(new Entry("knuckle_spell_impact"));
    public static final Entry SONIC_HAND = add(new Entry("sonic_hand"));
    public static final Entry NEN_SPHERE_SHOT = add(new Entry("nen_sphere_shot"));

    public static void register() {
        soundsToRegister().forEach((id, soundEvent) -> Registry.register(Registries.SOUND_EVENT, id, soundEvent));
        linkEntries();
    }

    /// Every sound event that still needs registering, keyed by the id it registers under. Creation only -
    /// nothing is written here, so a loader that registers sounds itself (Forge, through the helper
    /// `RegisterEvent` hands out) iterates this instead of calling {@link #register}. Follow it with
    /// {@link #linkEntries}.
    public static Map<Identifier, SoundEvent> soundsToRegister() {
        var sounds = new LinkedHashMap<Identifier, SoundEvent>();
        for (var entry: entries) {
            if (entry.entry != null || Registries.SOUND_EVENT.containsId(entry.id())) { continue; }
            sounds.put(entry.id(), entry.soundEvent());
        }
        return sounds;
    }

    /// Fills in every `Entry#entry` from the registry. `RegisterEvent`'s helper returns void where
    /// `Registry.registerReference` returned the reference, so Forge calls this right after the loop.
    public static void linkEntries() {
        for (var entry: entries) {
            if (entry.entry != null) { continue; }
            entry.entry = Registries.SOUND_EVENT.getEntry(RegistryKey.of(RegistryKeys.SOUND_EVENT, entry.id()))
                    .orElseThrow(() -> new IllegalStateException(
                            "Sound event " + entry.id() + " is not in the registry - register it first"));
        }
    }
}