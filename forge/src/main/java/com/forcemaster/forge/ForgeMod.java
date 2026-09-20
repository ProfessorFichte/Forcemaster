package com.forcemaster.forge;

import com.forcemaster.forge.client.ForgeClient;
import net.forcemaster_rpg.ForcemasterClassMod;
import net.forcemaster_rpg.client.particle.Particles;
import net.forcemaster_rpg.effect.ForcemasterEffects;
import net.forcemaster_rpg.entity.ForcemasterEntities;
import net.forcemaster_rpg.item.ForcemasterGroup;
import net.forcemaster_rpg.item.ForcemasterItems;
import net.forcemaster_rpg.item.armor.Armors;
import net.forcemaster_rpg.item.weapons.WeaponsRegister;
import net.forcemaster_rpg.sounds.ModSounds;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKeys;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.registries.RegisterEvent;
import net.spell_engine.api.effect.Effects;

@Mod(ForcemasterClassMod.MOD_ID)
public final class ForgeMod {
    @SuppressWarnings("removal")
    public ForgeMod() {
        ForcemasterClassMod.init();

        var modBus = FMLJavaModLoadingContext.get().getModEventBus();
        modBus.addListener(EventPriority.NORMAL, false, RegisterEvent.class, ForgeMod::register);
        modBus.addListener(EventPriority.NORMAL, false, BuildCreativeModeTabContentsEvent.class,
                ForgeMod::buildTabContents);

        if (FMLEnvironment.dist == Dist.CLIENT) {
            ForgeClient.register(modBus);
        }
    }

    // Goes through the helper on purpose, on Forge 47.0-47.3 a plain Registry.register throws "Can not register to a locked registry".
    public static void register(RegisterEvent event) {
        event.register(RegistryKeys.SOUND_EVENT, helper -> {
            ModSounds.soundsToRegister().forEach(helper::register);
            ModSounds.linkEntries();
        });

        event.register(RegistryKeys.STATUS_EFFECT, helper -> {
            ForcemasterEffects.effectsToRegister(ForcemasterClassMod.effectsConfig.value)
                    .forEach(helper::register);
            Effects.linkEntries(ForcemasterEffects.entries);
            ForcemasterClassMod.effectsConfig.save();
        });

        event.register(RegistryKeys.PARTICLE_TYPE, helper ->
                Particles.particlesToRegister().forEach(helper::register));

        event.register(RegistryKeys.ITEM, helper -> {
            ForcemasterItems.registerModItems();
            WeaponsRegister.itemsToRegister(ForcemasterClassMod.itemConfig.value.weapons)
                    .forEach(helper::register);
            Armors.itemsToRegister(ForcemasterClassMod.itemConfig.value.armor_sets)
                    .forEach(helper::register);
            ForcemasterClassMod.itemConfig.save();
        });

        event.register(RegistryKeys.ENTITY_TYPE, helper ->
                ForcemasterEntities.entityTypesToRegister().forEach(helper::register));

        event.register(RegistryKeys.ITEM_GROUP, helper ->
                helper.register(ForcemasterGroup.ID, ForcemasterGroup.create()));
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
                var item = override.getKey().item();
                event.accept(() -> item);
            }
        }
        for (var override : Armors.groupOverrides.entrySet()) {
            if (override.getValue().equals(key)) {
                for (var piece : override.getKey().armorSet().pieces()) {
                    var armorItem = (ArmorItem) piece;
                    event.accept(() -> armorItem);
                }
            }
        }
    }

    private static void removeFromTab(BuildCreativeModeTabContentsEvent event, Item item) {
        var iterator = event.getEntries().iterator();
        while (iterator.hasNext()) {
            ItemStack stack = iterator.next().getKey();
            if (stack.isOf(item)) {
                iterator.remove();
            }
        }
    }
}
