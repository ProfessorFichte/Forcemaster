package net.forcemaster_rpg.client.particle;

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
import java.util.List;
import java.util.function.Consumer;

import static net.forcemaster_rpg.ForcemasterClassMod.MOD_ID;

/// Registry of Forcemaster's own particles.
///
/// Ported from the V1 hand-written / vanilla particle factories to Spell Engine 1.10's
/// single generic factory: each entry is a texture plus the defaults of the particle
/// drawn from it, and [net.spell_engine.client.particle.SpellParticle] resolves entry
/// defaults + per-spawn `ParticleGroup.Appearance` payload into the final look.
///
/// This is what makes colour / scale / motion set on a `ParticleGroup` actually apply
/// to these ids — a vanilla or hand-written factory ignores the payload entirely.
///
/// Sprites still come from `assets/forcemaster_rpg/particles/<name>.json`; the
/// [Texture] frame count here only drives the entry's natural lifetime.
///
/// ### Lifetime
/// An animated entry lives for its frame count, stretched by `playbackSpeed`:
/// `effective maxAge = frames / playback_speed`. Single-frame entries take
/// `lifetime(ticks)` instead. Each entry below reproduces the V1 `maxAge` that way —
/// the number in the comment is the V1 value.
///
/// ### Scale
/// V1 mixed two conventions and they do *not* mean the same thing: `this.scale = X`
/// was absolute, while `this.scale(X)` *multiplied* vanilla's random `0.1..0.2` base
/// (mean `0.15`). V2 `scale` is absolute, so the latter ports as `0.15 * X` with the
/// base's own +/-33% as variance.
///
/// ### Standing still
/// Every V1 factory here threw its spawn velocity away — `ExplosionLargeParticle`
/// passes `0,0,0` to `super`, and the ground particle zeroed the three fields by hand.
/// `Appearance` has no "ignore spawn velocity" switch, so those entries carry
/// `drag(0F)`: the particle moves for a single tick and then stops dead. At the
/// `0.1..0.3` speeds these batches use that is a sub-block offset — the closest
/// expressible match, and the only approximation in this file besides the
/// randomised lifetimes noted per entry.
public class Particles {

    private static final List<Entry> entries = new ArrayList<>();

    /// Every entry owned by this mod. Registered here (server + client) and bound to
    /// the generic factory per platform — see `ForcemasterClient`.
    public static List<Entry> entries() {
        return entries;
    }

    private static Entry add(String name, int frames, Consumer<Appearance> defaults) {
        var entry = new Entry(Identifier.of(MOD_ID, name),
                new Texture(Identifier.of(MOD_ID, name), frames))
                .defaults(defaults);
        entries.add(entry);
        return entry;
    }

    private static Entry add(String name, int frames, int lifetime, Consumer<Appearance> defaults) {
        var entry = add(name, frames, defaults);
        entry.lifetime(lifetime);
        return entry;
    }

    /// V1 vanilla `ExplosionLargeParticle.Factory`: `PARTICLE_SHEET_LIT`, full-bright
    /// (`getBrightness` is a constant), and — unlike the custom factories below — it
    /// keeps vanilla's random darkening of `0.4..1.0`, hence `colorVariance(0.6)`.
    /// V1 scale was the absolute `2.0 * (1 - vx * 0.5)`, i.e. it shrank with the
    /// x-velocity it was spawned with; at these batch speeds that is `~1.85..2.0`,
    /// taken here as the `vx = 0` nominal `2.0`.
    public static final Entry ASAL_EXPLODE = add("asal_explode", 4, p -> p
            .render(Render.LIT).colorVariance(0.6F).scale(2F).drag(0F)
            .playbackSpeed(0.53F).lifetimeVariance(0.2F));            // V1 maxAge 6..9

    /// V1 vanilla `FlameParticle.Factory` (`AbstractSlowingParticle`): `0.96` drag,
    /// no gravity, collides, random `0.1..0.2` size, and a life of `8/(rand*0.8+0.2)+4`
    /// ticks — the same particle Spell Engine's own `flame` entry ports as lifetime `20`
    /// with `0.55` variance, mirrored here over this entry's 4 frames. Vanilla draws it
    /// on `PARTICLE_SHEET_OPAQUE`; `Render.LIT` is what SE's flame port uses and is
    /// identically unblended. `scaleMultiplier(0.5, EASE_IN_QUAD)` reproduces
    /// `FlameParticle.getSize`'s `scale * (1 - f*f*0.5)` shrink exactly.
    public static final Entry BARQ_ESNA_FLAME = add("barq_esna_flame", 4, p -> p
            .render(Render.LIT).scale(0.15F, 0.33F).scaleMultiplier(0.5F, Easing.EASE_IN_QUAD)
            .drag(0.96F).collides(true)
            .playbackSpeed(0.2F).lifetimeVariance(0.55F));            // V1 maxAge 12..44, median ~17

    /// V1 `CustomSpellExplosionParticle` (`ExplosionLargeParticle` re-sheeted to
    /// `PARTICLE_SHEET_TRANSLUCENT`, the default here): absolute scale `0.8`, and
    /// `red/green/blue` forced to `1`, so vanilla's random darkening is gone.
    /// Still full-bright via the inherited `getBrightness`. Sprites are the vanilla
    /// `minecraft:sonic_boom_8..15` frames, named by the particle json.
    public static final Entry SONICHAND_VACUUM = add("sonichand_vacuum", 8, p -> p
            .scale(0.8F).drag(0F).playbackSpeed(0.67F));              // V1 maxAge 12

    /// V1 `PunchParticle` (`ExplosionLargeParticle` on `PARTICLE_SHEET_LIT`):
    /// absolute scale `0.65`, undarkened, full-bright.
    public static final Entry PUNCH = add("punch", 1, 7, p -> p
            .render(Render.LIT).scale(0.65F).drag(0F));               // V1 maxAge 7

    /// Same V1 `PunchParticle.Factory` as [#PUNCH], over a 7 frame sheet — its V1
    /// `maxAge` of 7 is exactly one tick per frame, so playback stays at `1`.
    public static final Entry SONIC_PUNCH = add("sonic_punch", 7, p -> p
            .render(Render.LIT).scale(0.65F).drag(0F));               // V1 maxAge 7

    /// V1 `GroundParticle.DefaultFactory`, which despite the name built a
    /// `CircleGroundParticle`: a `SpriteBillboardParticle` whose whole `buildGeometry`
    /// override existed to lay the quad flat on the ground plane — exactly what
    /// [Facing#GROUND] replaces. World-lit rather than full-bright, hence `glow(false)`,
    /// and drawn translucent (the default). V1 did `scale *= 5.0 + rand/10 * modifier`
    /// on the `0.1..0.2` base with `modifier` in `1..30`; the `5x` term gives `0.75`
    /// and the remaining random growth is folded into the size variance.
    /// Its `maxAge = 8 + rand(modifier)` spread just as widely — `8..37`, median ~13 —
    /// approximated as 16 ticks with a `0.6` spread.
    public static final Entry GROUND_PUNCH = add("ground_punch", 1, 16, p -> p
            .facing(Facing.GROUND).glow(false).scale(0.75F, 0.33F).drag(0F)
            .lifetimeVariance(0.6F));                                 // V1 maxAge 8..37

    public static void register() {
        for (var entry: entries) {
            Registry.register(Registries.PARTICLE_TYPE, entry.id(), entry.type());
        }
    }
}
