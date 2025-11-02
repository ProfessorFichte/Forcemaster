package net.forcemaster_rpg;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.forcemaster_rpg.client.particle.Particles;
import net.forcemaster_rpg.config.Default;
import net.forcemaster_rpg.config.EffectsConfig;
import net.forcemaster_rpg.config.TweaksConfig;
import net.forcemaster_rpg.effect.Effects;
import net.forcemaster_rpg.item.ForcemasterGroup;
import net.forcemaster_rpg.item.ForcemasterItems;
import net.forcemaster_rpg.item.weapons.WeaponsRegister;
import net.forcemaster_rpg.item.armor.Armors;
import net.forcemaster_rpg.sounds.ModSounds;
import net.forcemaster_rpg.util.custom.CustomSpellImpact;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;

import net.minecraft.util.Identifier;
import net.spell_engine.api.config.ConfigFile;
import net.tiny_config.ConfigManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ForcemasterClassMod{
	public static final String MOD_ID = "forcemaster_rpg";
    public static final Logger LOGGER = LoggerFactory.getLogger("forcemaster_rpg");

	public static ConfigManager<ConfigFile.Equipment> itemConfig = new ConfigManager<>
			("equipment_v1", Default.itemConfig)
			.builder()
			.setDirectory(MOD_ID)
			.sanitize(true)
			.build();
	public static ConfigManager<EffectsConfig> effectsConfig = new ConfigManager<EffectsConfig>
			("effects_v4", new EffectsConfig())
			.builder()
			.setDirectory(MOD_ID)
			.sanitize(true)
			.build();
	public static ConfigManager<TweaksConfig> tweaksConfig = new ConfigManager<TweaksConfig>
			("tweaks", new TweaksConfig())
			.builder()
			.setDirectory(MOD_ID)
			.sanitize(true)
			.build();


	public static void init() {
		itemConfig.refresh();
		tweaksConfig.refresh();
		if (FabricLoader.getInstance().isDevelopmentEnvironment()) {
			tweaksConfig.value.ignore_items_required_mods = true;
		}
		effectsConfig.refresh();
		CustomSpellImpact.registerCustomImpacts();
	}

	public static void registerItems() {
		ForcemasterGroup.FORCEMASTER = FabricItemGroup.builder()
				.icon(() -> new ItemStack(Armors.phaslebArmorSet.head.asItem()))
				.displayName(Text.translatable("itemGroup." + MOD_ID + ".general"))
				.build();
		Registry.register(Registries.ITEM_GROUP, ForcemasterGroup.FORCEMASTER_KEY, ForcemasterGroup.FORCEMASTER);
		ForcemasterItems.registerModItems();
		ForcemasterGroup.registerItemGroups();

		WeaponsRegister.register(itemConfig.value.weapons);
		Armors.register(itemConfig.value.armor_sets);
		if (FabricLoader.getInstance().isModLoaded("armory_rpgs") || ForcemasterClassMod.tweaksConfig.value.ignore_items_required_mods) {
			FabricLoader.getInstance().getModContainer(MOD_ID).ifPresent(modContainer -> {
				ResourceManagerHelper.registerBuiltinResourcePack(
						Identifier.of(MOD_ID, "forcemaster_armory_compat"),
						modContainer,
						ResourcePackActivationType.ALWAYS_ENABLED
				);
			});
		}
		itemConfig.save();
	}
	public static void registerSounds() {
		ModSounds.register();
	}
	public static void registerEffects() {
		Effects.register();
		effectsConfig.save();
	}
	public static void registerParticles() {
		Particles.register();
	}
	public static Identifier id(String path) {
		return Identifier.of(MOD_ID, path);
	}
}