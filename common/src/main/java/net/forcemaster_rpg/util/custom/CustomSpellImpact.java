package net.forcemaster_rpg.util.custom;
import net.minecraft.util.Identifier;
import net.spell_engine.api.spell.event.SpellHandlers;

import static net.forcemaster_rpg.ForcemasterClassMod.MOD_ID;

public class CustomSpellImpact {

    public static void registerCustomImpacts(){
        SpellHandlers.registerCustomImpact(
                Identifier.of(MOD_ID, "fist_weapon_stun"),
                new FistWeaponOnlyStunImpact()
        );
    }
}
