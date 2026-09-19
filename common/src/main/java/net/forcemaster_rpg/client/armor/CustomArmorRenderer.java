package net.forcemaster_rpg.client.armor;

import net.minecraft.util.Identifier;
import net.rpg_foundation.armor_api.client.GeoArmorRenderer;

import static net.forcemaster_rpg.ForcemasterClassMod.MOD_ID;

public final class CustomArmorRenderer {
    private CustomArmorRenderer() { }

    public static GeoArmorRenderer oriene_armor() {
        return make("oriene_armor", "oriene_armor");
    }
    public static GeoArmorRenderer phasleb_armor() {
        return make("phasleb_armor", "phasleb_armor");
    }
    public static GeoArmorRenderer aken_armor() {
        return make("aken_armor", "aken_armor");
    }
    public static GeoArmorRenderer billporon_armor() {
        return make("billporon_armor", "billporon_armor");
    }

    private static GeoArmorRenderer make(String modelName, String textureName) {
        return GeoArmorRenderer.of(
                new Identifier(MOD_ID, "geo/" + modelName + ".geo.json"),
                new Identifier(MOD_ID, "textures/armor/" + textureName + ".png"));
    }
}
