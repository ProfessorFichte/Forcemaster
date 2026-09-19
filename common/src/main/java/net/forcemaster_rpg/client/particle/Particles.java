package net.forcemaster_rpg.client.particle;

import net.minecraft.particle.ParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.spell_engine.api.spell.fx.Easing;
import net.spell_engine.api.spell.fx.ParticleGroup.Appearance;
import net.spell_engine.api.spell.fx.ParticleGroup.Facing;
import net.spell_engine.api.spell.fx.ParticleGroup.Render;
import net.spell_engine.fx.SpellEngineParticles.Entry;
import net.spell_engine.fx.SpellEngineParticles.Texture;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import static net.forcemaster_rpg.ForcemasterClassMod.MOD_ID;

public class Particles {

    private static final List<Entry> entries = new ArrayList<>();

    public static List<Entry> entries() {
        return entries;
    }

    private static Entry add(String name, int frames, Consumer<Appearance> defaults) {
        var entry = new Entry(new Identifier(MOD_ID, name),
                new Texture(new Identifier(MOD_ID, name), frames))
                .defaults(defaults);
        entries.add(entry);
        return entry;
    }

    private static Entry add(String name, int frames, int lifetime, Consumer<Appearance> defaults) {
        var entry = add(name, frames, defaults);
        entry.lifetime(lifetime);
        return entry;
    }

    public static final Entry ASAL_EXPLODE = add("asal_explode", 4, p -> p
            .render(Render.LIT).colorVariance(0.6F).scale(2F).drag(0F)
            .playbackSpeed(0.53F).lifetimeVariance(0.2F));

    public static final Entry BARQ_ESNA_FLAME = add("barq_esna_flame", 4, p -> p
            .render(Render.LIT).scale(0.15F, 0.33F).scaleMultiplier(0.5F, Easing.EASE_IN_QUAD)
            .drag(0.96F).collides(true)
            .playbackSpeed(0.2F).lifetimeVariance(0.55F));

    public static final Entry SONICHAND_VACUUM = add("sonichand_vacuum", 8, p -> p
            .scale(0.8F).drag(0F).playbackSpeed(0.67F));

    public static final Entry PUNCH = add("punch", 1, 7, p -> p
            .render(Render.LIT).scale(0.65F).drag(0F));

    public static final Entry SONIC_PUNCH = add("sonic_punch", 7, p -> p
            .render(Render.LIT).scale(0.65F).drag(0F));

    public static final Entry GROUND_PUNCH = add("ground_punch", 1, 16, p -> p
            .facing(Facing.GROUND).glow(false).scale(0.75F, 0.33F).drag(0F)
            .lifetimeVariance(0.6F));

    public static void register() {
        particlesToRegister().forEach((id, type) -> Registry.register(Registries.PARTICLE_TYPE, id, type));
    }

    /// Every particle type that still needs registering, keyed by the id it registers under. Creation only -
    /// nothing is written here, so a loader that registers particle types itself (Forge, through the helper
    /// `RegisterEvent` hands out) iterates this instead of calling {@link #register}.
    public static Map<Identifier, ParticleType<?>> particlesToRegister() {
        var types = new LinkedHashMap<Identifier, ParticleType<?>>();
        for (var entry: entries) {
            if (Registries.PARTICLE_TYPE.containsId(entry.id())) { continue; }
            types.put(entry.id(), entry.type());
        }
        return types;
    }
}
