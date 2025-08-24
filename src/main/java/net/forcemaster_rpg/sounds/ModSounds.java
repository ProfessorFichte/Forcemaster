package net.forcemaster_rpg.sounds;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

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
            this(Identifier.of(MOD_ID, name));
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

    public static void register() {
        for (var entry: entries) {
            entry.entry = Registry.registerReference(Registries.SOUND_EVENT, entry.id(), entry.soundEvent());
        }
    }
}