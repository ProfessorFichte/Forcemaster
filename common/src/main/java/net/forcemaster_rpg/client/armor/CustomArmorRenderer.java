package net.forcemaster_rpg.client.armor;

import mod.azure.azurelibarmor.common.render.AzRendererConfig;
import mod.azure.azurelibarmor.common.render.armor.AzArmorRenderer;
import mod.azure.azurelibarmor.common.render.armor.AzArmorRendererConfig;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

import static net.forcemaster_rpg.ForcemasterClassMod.MOD_ID;

public class CustomArmorRenderer extends AzArmorRenderer {

    public static CustomArmorRenderer oriene_armor() {
        return new CustomArmorRenderer("oriene_armor", "oriene_armor");
    }
    public static CustomArmorRenderer phasleb_armor() {
        return new CustomArmorRenderer("phasleb_armor", "phasleb_armor");
    }
    public static CustomArmorRenderer aken_armor() {
        return new CustomArmorRenderer("aken_armor", "aken_armor");
    }
    public static CustomArmorRenderer billporon_armor() {
        return new CustomArmorRenderer("billporon_armor", "billporon_armor");
    }


    public CustomArmorRenderer(String modelName, String textureName) {
        super(AzArmorRendererConfig.builder(
                Identifier.of(MOD_ID, "geo/" + modelName + ".geo.json"),
                Identifier.of(MOD_ID, "textures/armor/" + textureName + ".png")
        ).build());
    }
}
