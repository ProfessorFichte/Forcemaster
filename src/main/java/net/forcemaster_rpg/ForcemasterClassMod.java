package net.forcemaster_rpg;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.loader.api.FabricLoader;
import net.forcemaster_rpg.client.particle.Particles;
import net.forcemaster_rpg.config.Default;
import net.forcemaster_rpg.config.EffectsConfig;
import net.forcemaster_rpg.config.TweaksConfig;
import net.forcemaster_rpg.custom.custom_spells.CustomSpells;
import net.forcemaster_rpg.effect.Effects;
import net.forcemaster_rpg.item.ForcemasterGroup;
import net.forcemaster_rpg.item.ForcemasterItems;
import net.forcemaster_rpg.item.weapons.WeaponsRegister;
import net.forcemaster_rpg.item.armor.Armors;
import net.forcemaster_rpg.sounds.ModSounds;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;

import net.minecraft.util.Identifier;
import net.spell_engine.api.item.ItemConfig;
import net.tinyconfig.ConfigManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ForcemasterClassMod implements ModInitializer {
	public static final String MOD_ID = "forcemaster_rpg";
    public static final Logger LOGGER = LoggerFactory.getLogger("forcemaster_rpg");


	public static ConfigManager<ItemConfig> itemConfig = new ConfigManager<ItemConfig>
			("items_v7", Default.itemConfig)
			.builder()
			.setDirectory(MOD_ID)
			.sanitize(true)
			.build();
	public static ConfigManager<EffectsConfig> effectsConfig = new ConfigManager<EffectsConfig>
			("effects_v3", new EffectsConfig())
			.builder()
			.setDirectory(MOD_ID)
			.sanitize(true)
			.build();
	public static ConfigManager<TweaksConfig> tweaksConfig = new ConfigManager<>
			("tweaks", new TweaksConfig())
			.builder()
			.setDirectory(MOD_ID)
			.sanitize(true)
			.build();

	private void registerItemGroup() {
		ForcemasterGroup.FORCEMASTER = FabricItemGroup.builder()
				.icon(() -> new ItemStack(Armors.orieneArmorSet.chest.asItem()))
				.displayName(Text.translatable("itemGroup." + MOD_ID + ".general"))
				.build();
		Registry.register(Registries.ITEM_GROUP, ForcemasterGroup.FORCEMASTER_KEY, ForcemasterGroup.FORCEMASTER);
	}


	@Override
	public void onInitialize() {
		itemConfig.refresh();
		tweaksConfig.refresh();
		if (FabricLoader.getInstance().isDevelopmentEnvironment()) {
			tweaksConfig.value.ignore_items_required_mods = true;
		}
		Effects.register();
		effectsConfig.refresh();
		Particles.register();
		ForcemasterItems.registerModItems();
		ForcemasterGroup.registerItemGroups();
		CustomSpells.register();
		WeaponsRegister.register(itemConfig.value.weapons);
		Armors.register(itemConfig.value.armor_sets);
		itemConfig.save();
		registerItemGroup();
		ModSounds.register();
		tweaksConfig.save();
	}
	public static Identifier id(String path) {
		return Identifier.of(MOD_ID, path);
	}
}