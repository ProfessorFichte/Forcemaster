package com.forcemaster.fabric;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.forcemaster_rpg.ForcemasterClassMod;
import net.forcemaster_rpg.item.ForcemasterGroup;
import net.forcemaster_rpg.item.armor.Armors;
import net.forcemaster_rpg.item.weapons.WeaponsRegister;
import net.minecraft.item.ArmorItem;

public final class FabricMod implements ModInitializer {
    @Override
    public void onInitialize() {
        ForcemasterClassMod.init();
        ForcemasterClassMod.registerItems();
        ForcemasterClassMod.registerSounds();
        ForcemasterClassMod.registerEffects();
        ForcemasterClassMod.registerParticles();
        ForcemasterClassMod.registerEntities();
        registerItemGroupContent();
    }

    private void registerItemGroupContent() {
        for (var override : WeaponsRegister.groupOverrides.entrySet()) {
            var item = override.getKey().item();
            var key = override.getValue();
            ItemGroupEvents.modifyEntriesEvent(ForcemasterGroup.FORCEMASTER_KEY).register(content -> {
                content.getDisplayStacks().removeIf(stack -> stack.isOf(item));
                content.getSearchTabStacks().removeIf(stack -> stack.isOf(item));
            });
            ItemGroupEvents.modifyEntriesEvent(key).register(content -> content.add(item));
        }

        for (var override : Armors.groupOverrides.entrySet()) {
            var pieces = override.getKey().armorSet().pieces();
            var key = override.getValue();
            ItemGroupEvents.modifyEntriesEvent(ForcemasterGroup.FORCEMASTER_KEY).register(content -> {
                content.getDisplayStacks().removeIf(stack -> pieces.stream().anyMatch(p -> stack.isOf((ArmorItem) p)));
                content.getSearchTabStacks().removeIf(stack -> pieces.stream().anyMatch(p -> stack.isOf((ArmorItem) p)));
            });
            ItemGroupEvents.modifyEntriesEvent(key).register(content -> {
                for (var piece : pieces) {
                    content.add((ArmorItem) piece);
                }
            });
        }
    }
}
