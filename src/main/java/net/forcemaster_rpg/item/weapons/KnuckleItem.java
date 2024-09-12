package net.forcemaster_rpg.item.weapons;

import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.spell_engine.api.item.weapon.SpellWeaponItem;

import java.util.List;

public class KnuckleItem extends SpellWeaponItem {
    public KnuckleItem(ToolMaterial material, Settings settings) {
        super(material, settings);
    }

    public void appendTooltip(ItemStack stack, List<Text> tooltip, TooltipContext context, TooltipType type) {
        tooltip.add(Text.translatable("item.forcemaster_rpg.knuckle.description_1").formatted(Formatting.RED));
        super.appendTooltip(stack, context, tooltip, type);
    }
}
