package net.forcemaster_rpg.item;

import net.forcemaster_rpg.ForcemasterClassMod;
import net.minecraft.item.ItemGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;

public class ForcemasterGroup {
    public static Identifier ID = Identifier.of(ForcemasterClassMod.MOD_ID, "generic");
    public static RegistryKey<ItemGroup> FORCEMASTER_KEY = RegistryKey.of(Registries.ITEM_GROUP.getKey(),Identifier.of(ForcemasterClassMod.MOD_ID,"generic"));
    public static ItemGroup FORCEMASTER;

    public static void registerItemGroups() {
        ForcemasterClassMod.LOGGER.info("Registering Item Groups for " + ForcemasterClassMod.MOD_ID);
    }
}