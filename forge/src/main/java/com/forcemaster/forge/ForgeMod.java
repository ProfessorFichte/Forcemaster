package com.forcemaster.forge;

import com.forcemaster.forge.client.ForgeClient;
import net.forcemaster_rpg.ForcemasterClassMod;
import net.forcemaster_rpg.client.particle.Particles;
import net.forcemaster_rpg.item.ForcemasterGroup;
import net.forcemaster_rpg.item.armor.Armors;
import net.forcemaster_rpg.item.weapons.WeaponsRegister;
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

/// Forge 47 entrypoint (1.20.1 port of the NeoForge entrypoint).
///
/// Forge locks every vanilla registry outside its own `RegisterEvent` window, so each `registerX()` call
/// sits inside the window of the registry it writes to. `ITEM_GROUP` is *not* a Forge-wrapped registry, so
/// it never gets its own `RegisterEvent` - the group is registered from the `ITEM` window instead (it stays
/// unfrozen for the whole phase), which is what `ForcemasterClassMod.registerItems()` does.
@Mod(ForcemasterClassMod.MOD_ID)
public final class ForgeMod {
    // FMLJavaModLoadingContext.get() is flagged for removal by late 47.x builds, but the
    // constructor-injected replacement doesn't exist on early 47.x; get() works on all of [47,).
    @SuppressWarnings("removal")
    public ForgeMod() {
        ForcemasterClassMod.init();

        var modBus = FMLJavaModLoadingContext.get().getModEventBus();
        // Explicit event classes: Forge 47's plain addListener(Consumer) infers the event type from the
        // lambda via TypeTools, which is fragile; the 4-arg overload takes it directly.
        modBus.addListener(EventPriority.NORMAL, false, RegisterEvent.class, ForgeMod::register);
        modBus.addListener(EventPriority.NORMAL, false, BuildCreativeModeTabContentsEvent.class,
                ForgeMod::buildTabContents);

        if (FMLEnvironment.dist == Dist.CLIENT) {
            ForgeClient.register(modBus);
        }
    }

    public static void register(RegisterEvent event) {
        event.register(RegistryKeys.SOUND_EVENT, reg -> ForcemasterClassMod.registerSounds());
        event.register(RegistryKeys.ITEM, reg -> {
            // Also registers the `forcemaster_rpg:generic` item group, see the class doc.
            ForcemasterClassMod.registerItems();
        });
        event.register(RegistryKeys.STATUS_EFFECT, reg -> ForcemasterClassMod.registerEffects());
        event.register(RegistryKeys.PARTICLE_TYPE, reg -> Particles.register());
        event.register(RegistryKeys.ENTITY_TYPE, reg -> ForcemasterClassMod.registerEntities());
    }

    /// Forge fires this per creative tab, on the logical client only. Unlike NeoForge there is no
    /// `remove(...)` / `getParentEntries()` / `getSearchEntries()`: the tab is one
    /// `MutableHashedLinkedMap<ItemStack, StackVisibility>` covering both the parent and the search tab, so
    /// removing a key removes it from both. `event.accept(Supplier)` adds.
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
