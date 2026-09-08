package net.forcemaster_rpg.item;

import net.forcemaster_rpg.ForcemasterClassMod;
import net.forcemaster_rpg.item.armor.Armors;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ForcemasterGroup {
    public static Identifier ID = new Identifier(ForcemasterClassMod.MOD_ID, "generic");
    public static RegistryKey<ItemGroup> FORCEMASTER_KEY = RegistryKey.of(Registries.ITEM_GROUP.getKey(),new Identifier(ForcemasterClassMod.MOD_ID,"generic"));
    public static ItemGroup FORCEMASTER;

    public static ItemStack icon() {
        return new ItemStack(Armors.phaslebArmorSet.armorSet().head.asItem());
    }

    public static Text displayName() {
        return Text.translatable("itemGroup." + ForcemasterClassMod.MOD_ID + ".general");
    }

    /// The group is built here rather than per platform: `ItemGroup.builder()` is a Fabric injection and
    /// `FabricItemGroup` is Fabric-only, but the vanilla `ItemGroup.Builder` constructor works on both
    /// loaders. `ITEM_GROUP` is a vanilla-only registry (Forge does not wrap it), so it stays writable for
    /// the whole `RegisterEvent` phase and this may be called from the `ITEM` window on Forge.
    public static void registerItemGroups() {
        ForcemasterClassMod.LOGGER.info("Registering Item Groups for " + ForcemasterClassMod.MOD_ID);
        FORCEMASTER = new ItemGroup.Builder(ItemGroup.Row.TOP, 0)
                .icon(ForcemasterGroup::icon)
                .displayName(displayName())
                .build();
        Registry.register(Registries.ITEM_GROUP, FORCEMASTER_KEY, FORCEMASTER);
    }
}