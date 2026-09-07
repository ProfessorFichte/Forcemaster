package com.forcemaster.neoforge;

import net.forcemaster_rpg.ForcemasterClassMod;
import net.forcemaster_rpg.client.particle.Particles;
import net.forcemaster_rpg.item.ForcemasterGroup;
import net.forcemaster_rpg.item.armor.Armors;
import net.forcemaster_rpg.item.weapons.WeaponsRegister;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKeys;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.RegisterEvent;

import java.util.ArrayList;


@Mod(ForcemasterClassMod.MOD_ID)
public final class NeoForgeMod {
    public NeoForgeMod(IEventBus modBus) {
        ForcemasterClassMod.init();
        modBus.addListener(RegisterEvent.class, NeoForgeMod::register);
        modBus.addListener(BuildCreativeModeTabContentsEvent.class, NeoForgeMod::buildTabContents);
    }


    public static void register(RegisterEvent event) {
        event.register(RegistryKeys.ITEM_GROUP, reg -> {
            ForcemasterGroup.FORCEMASTER = ItemGroup.builder()
                    .icon(ForcemasterGroup::icon)
                    .displayName(ForcemasterGroup.displayName())
                    .build();
            Registry.register(Registries.ITEM_GROUP, ForcemasterGroup.FORCEMASTER_KEY, ForcemasterGroup.FORCEMASTER);
        });
        event.register(RegistryKeys.SOUND_EVENT, reg -> {
            ForcemasterClassMod.registerSounds();
        });
        event.register(RegistryKeys.ITEM, reg -> {
            ForcemasterClassMod.registerItems();
        });
        event.register(RegistryKeys.STATUS_EFFECT, reg -> {
            ForcemasterClassMod.registerEffects();
        });
        event.register(RegistryKeys.PARTICLE_TYPE, reg -> {
            Particles.register();
        });
        event.register(RegistryKeys.ENTITY_TYPE, reg -> {
            ForcemasterClassMod.registerEntities();
        });
    }

    private static void buildTabContents(BuildCreativeModeTabContentsEvent event) {
        var key = event.getTabKey();
        if (key.equals(ForcemasterGroup.FORCEMASTER_KEY)) {
            for (var override : WeaponsRegister.groupOverrides.keySet()) {
                removeFromTab(event, override.item());
            }
            for (var override : Armors.groupOverrides.keySet()) {
                for (var piece : override.armorSet().pieces()) {
                    removeFromTab(event, (ArmorItem) piece);
                }
            }
        }

        for (var override : WeaponsRegister.groupOverrides.entrySet()) {
            if (override.getValue().equals(key)) {
                event.add(override.getKey().item());
            }
        }
        for (var override : Armors.groupOverrides.entrySet()) {
            if (override.getValue().equals(key)) {
                for (var piece : override.getKey().armorSet().pieces()) {
                    event.add((ArmorItem) piece);
                }
            }
        }
    }

    private static void removeFromTab(BuildCreativeModeTabContentsEvent event, Item item) {
        var toRemove = new ArrayList<ItemStack>();
        for (var stack : event.getParentEntries()) {
            if (stack.isOf(item)) {
                toRemove.add(stack);
            }
        }
        for (var stack : event.getSearchEntries()) {
            if (stack.isOf(item)) {
                toRemove.add(stack);
            }
        }
        for (var stack : toRemove) {
            event.remove(stack, ItemGroup.StackVisibility.PARENT_AND_SEARCH_TABS);
        }
    }
}
