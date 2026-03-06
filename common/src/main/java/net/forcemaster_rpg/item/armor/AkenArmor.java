package net.forcemaster_rpg.item.armor;

import net.minecraft.item.ArmorMaterial;
import net.minecraft.registry.entry.RegistryEntry;
import net.spell_engine.rpg_series.item.Armor;

public class AkenArmor extends Armor.CustomItem {
    public AkenArmor(RegistryEntry<ArmorMaterial> material, Type slot, Settings settings) {
        super(material, slot, settings);
    }
}
