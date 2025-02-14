package net.forcemaster_rpg.config;

import net.forcemaster_rpg.item.armor.Armors;
import net.forcemaster_rpg.item.weapons.WeaponsRegister;
import net.spell_engine.api.config.ConfigFile;

public class Default {
    public static final ConfigFile.Equipment itemConfig;

    static {
        itemConfig = new ConfigFile.Equipment();
        for (var armorSet : Armors.entries) {
            itemConfig.armor_sets.put(armorSet.name(), armorSet.defaults());
        }
        for (var weapon : WeaponsRegister.entries) {
            itemConfig.weapons.put(weapon.name(), weapon.defaults());
        }
    }
}