package net.forcemaster_rpg.item.armor;

import net.minecraft.item.ArmorMaterial;
import net.minecraft.registry.entry.RegistryEntry;
import net.spell_engine.rpg_series.item.Armor;

public class PhaslebArmor extends Armor.CustomItem {
    public PhaslebArmor(RegistryEntry<ArmorMaterial> material, Type slot, Settings settings) {
        super(material, slot, settings);
    }
}
