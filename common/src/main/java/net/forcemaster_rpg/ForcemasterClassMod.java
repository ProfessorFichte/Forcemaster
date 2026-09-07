package net.forcemaster_rpg;

import net.spell_engine.Platform;
import net.forcemaster_rpg.client.particle.Particles;
import net.forcemaster_rpg.config.Default;
import net.forcemaster_rpg.config.TweaksConfig;
import net.forcemaster_rpg.effect.ForcemasterEffects;
import net.forcemaster_rpg.entity.ForcemasterEntities;
import net.forcemaster_rpg.item.ForcemasterGroup;
import net.forcemaster_rpg.item.ForcemasterItems;
import net.forcemaster_rpg.item.weapons.WeaponsRegister;
import net.forcemaster_rpg.item.armor.Armors;
import net.forcemaster_rpg.sounds.ModSounds;
import net.forcemaster_rpg.util.custom.CustomSpellImpact;
import net.minecraft.util.Identifier;
import net.spell_engine.rpg_series.config.ConfigFile;
import net.spell_engine.rpg_series.config.ConfigFile.Effects;
import net.tiny_config.ConfigManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ForcemasterClassMod{
	public static final String MOD_ID = "forcemaster_rpg";
    public static final Logger LOGGER = LoggerFactory.getLogger("forcemaster_rpg");

	public static ConfigManager<ConfigFile.Equipment> itemConfig = new ConfigManager<>
			("equipment_v2", Default.itemConfig)
			.builder()
			.setDirectory(MOD_ID)
			.sanitize(true)
			.build();
	public static ConfigManager<ConfigFile.Effects> effectsConfig = new ConfigManager<>
			("effects_v6", new ConfigFile.Effects())
			.builder()
			.setDirectory(MOD_ID)
			.sanitize(true)
			.build();
	public static ConfigManager<TweaksConfig> tweaksConfig = new ConfigManager<TweaksConfig>
			("tweaks_v1", new TweaksConfig())
			.builder()
			.setDirectory(MOD_ID)
			.sanitize(true)
			.build();


	public static void init() {
		itemConfig.refresh();
		tweaksConfig.refresh();
		if (Platform.util().isDevelopmentEnvironment()) {
			tweaksConfig.value.ignore_items_required_mods = true;
		}
		effectsConfig.refresh();
		CustomSpellImpact.registerCustomImpacts();
		CustomSpellImpact.registerCustomDeliveries();
	}

	public static void registerItems() {
		ForcemasterItems.registerModItems();
		ForcemasterGroup.registerItemGroups();

		WeaponsRegister.register(itemConfig.value.weapons);
		Armors.register(itemConfig.value.armor_sets);
		itemConfig.save();
	}
	public static void registerSounds() {
		ModSounds.register();
	}
	public static void registerEffects() {
		ForcemasterEffects.register(effectsConfig.value);
		effectsConfig.save();
	}
	public static void registerParticles() {
		Particles.register();
	}
	public static void registerEntities() {
		ForcemasterEntities.registerEntities();
	}
	public static Identifier id(String path) {
		return Identifier.of(MOD_ID, path);
	}
}