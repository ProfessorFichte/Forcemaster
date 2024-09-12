package net.forcemaster_rpg.item;

import net.forcemaster_rpg.ForcemasterClassMod;
import net.forcemaster_rpg.item.armor.Armors;
import net.forcemaster_rpg.item.weapons.WeaponsRegister;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.spell_engine.api.item.trinket.SpellBooks;

import java.util.HashMap;

public class ForcemasterItems {
    public static final HashMap<String, Item> entries;
    static {
        entries = new HashMap<>();
        for(var weaponEntry: WeaponsRegister.entries) {
            entries.put(weaponEntry.id().toString(), weaponEntry.item());
        }
        for(var entry: Armors.entries) {
            var set = entry.armorSet();
            for (var piece: set.pieces()) {
                var armorItem = (ArmorItem) piece;
                entries.put(set.idOf(armorItem).toString(), armorItem);
            }
        }
    }



    public static void registerModItems(){

        SpellBooks.createAndRegister(Identifier.of(ForcemasterClassMod.MOD_ID,"forcemaster"), ForcemasterGroup.FORCEMASTER_KEY);
        ItemGroupEvents.modifyEntriesEvent(ForcemasterGroup.FORCEMASTER_KEY).register((content) -> {
        });

        ForcemasterClassMod.LOGGER.info("Registering Mod Items for " + ForcemasterClassMod.MOD_ID);

    }
}

