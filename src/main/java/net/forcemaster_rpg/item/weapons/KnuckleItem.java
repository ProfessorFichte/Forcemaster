package net.forcemaster_rpg.item.weapons;

import net.minecraft.client.gui.screen.Screen;
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

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        if(Screen.hasShiftDown()) {
            tooltip.add(Text.translatable("item.forcemaster_rpg.knuckle.description_1").formatted(Formatting.RED));
        } else {
            tooltip.add(Text.translatable("tooltip.forcemaster_rpg.shift_down"));

        }
        super.appendTooltip(stack, context, tooltip, type);
    }
}
