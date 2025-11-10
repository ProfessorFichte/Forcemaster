package net.forcemaster_rpg;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.forcemaster_rpg.item.armor.Armors;
import net.forcemaster_rpg.item.tag.ModItemTags;
import net.forcemaster_rpg.item.weapons.WeaponsRegister;
import net.forcemaster_rpg.sounds.ModSounds;
import net.forcemaster_rpg.spell.ForcemasterSpells;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.spell_engine.api.datagen.SimpleSoundGeneratorV2;
import net.spell_engine.api.datagen.SpellGenerator;
import net.spell_engine.api.item.armor.Armor;
import net.spell_engine.api.item.weapon.Weapon;
import net.spell_engine.api.tags.SpellEngineItemTags;
import net.spell_engine.rpg_series.datagen.RPGSeriesDataGen;
import net.spell_engine.rpg_series.tags.RPGSeriesItemTags;
import net.spell_power.api.SpellPowerTags;

import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static net.forcemaster_rpg.ForcemasterClassMod.MOD_ID;

public class ForcemasterClassModDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
		pack.addProvider(ItemTagGenerator::new);
		pack.addProvider(UnsmeltGenerator::new);
		pack.addProvider(SpellGen::new);
		pack.addProvider(SoundGen::new);
	}

	public static class SoundGen extends SimpleSoundGeneratorV2 {
		public SoundGen(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
			super(dataOutput, registryLookup);
		}

		@Override
		public void generateSounds(Builder builder) {
			builder.entries.add(new Entry(MOD_ID,
							ModSounds.entries.stream()
									.map(entry -> SoundEntry.withVariants(entry.id().getPath(), entry.variants()))
									.toList()
					)
			);
		}
	}

	public static class SpellGen extends SpellGenerator {
		public SpellGen(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
			super(dataOutput, registryLookup);
		}

		@Override
		public void generateSpells(Builder builder) {
			for (var entry: ForcemasterSpells.entries) {
				builder.add(entry.id(), entry.spell());
			}
		}
	}

	public static class ItemTagGenerator extends RPGSeriesDataGen.ItemTagGenerator {
		public ItemTagGenerator(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
			super(dataOutput, registryLookup);
		}

		public void armorTags(List<Armor.Entry> armors) {
			this.armorTags(armors, EnumSet.noneOf(RPGSeriesItemTags.ArmorMetaType.class));
		}

		public void armorTags(List<Armor.Entry> armors, RPGSeriesItemTags.ArmorMetaType metaType) {
			this.armorTags(armors, EnumSet.of(metaType));
		}

		public void armorTags(List<Armor.Entry> armors, EnumSet<RPGSeriesItemTags.ArmorMetaType> metaTypes) {
			Iterator var3 = armors.iterator();

			while(var3.hasNext()) {
				Armor.Entry armor = (Armor.Entry)var3.next();
				Armor.Set set = armor.armorSet();
				FabricTagProvider<Item>.FabricTagBuilder headTag = this.getOrCreateTagBuilder(ItemTags.HEAD_ARMOR);
				headTag.addOptional(set.idOf(set.head));
				FabricTagProvider<Item>.FabricTagBuilder chestTag = this.getOrCreateTagBuilder(ItemTags.CHEST_ARMOR);
				chestTag.addOptional(set.idOf(set.chest));
				FabricTagProvider<Item>.FabricTagBuilder legsTag = this.getOrCreateTagBuilder(ItemTags.LEG_ARMOR);
				legsTag.addOptional(set.idOf(set.legs));
				FabricTagProvider<Item>.FabricTagBuilder feetTag = this.getOrCreateTagBuilder(ItemTags.FOOT_ARMOR);
				feetTag.addOptional(set.idOf(set.feet));
				int tier = armor.lootProperties().tier();
				Iterator var12;
				if (tier >= 0) {
					FabricTagProvider<Item>.FabricTagBuilder tierTag = this.getOrCreateTagBuilder(RPGSeriesItemTags.LootTiers.get(tier, RPGSeriesItemTags.LootCategory.ARMORS));
					var12 = armor.armorSet().pieceIds().iterator();

					while(var12.hasNext()) {
						Object id = var12.next();
						tierTag.addOptional((Identifier)id);
					}
				}

				String lootTheme = armor.lootProperties().theme();
				if (lootTheme != null && !lootTheme.isEmpty()) {
					FabricTagProvider<Item>.FabricTagBuilder themeTag = this.getOrCreateTagBuilder(RPGSeriesItemTags.LootThemes.get(lootTheme));
					Iterator var19 = armor.armorSet().pieceIds().iterator();

					while(var19.hasNext()) {
						Object id = var19.next();
						themeTag.addOptional((Identifier)id);
					}
				}

				var12 = metaTypes.iterator();

				while(var12.hasNext()) {
					RPGSeriesItemTags.ArmorMetaType metaType = (RPGSeriesItemTags.ArmorMetaType)var12.next();
					FabricTagProvider<Item>.FabricTagBuilder metaTag = this.getOrCreateTagBuilder(RPGSeriesItemTags.ArmorType.get(metaType));
					Iterator var15 = armor.armorSet().pieceIds().iterator();

					while(var15.hasNext()) {
						Object id = var15.next();
						metaTag.addOptional((Identifier)id);
					}
				}
			}

		}

		public void generateKnuckleTags(List<Weapon.Entry> weapons) {
			Iterator var2 = weapons.iterator();

			while(var2.hasNext()) {
				Weapon.Entry weapon = (Weapon.Entry)var2.next();
				FabricTagProvider<Item>.FabricTagBuilder tag = this.getOrCreateTagBuilder(ModItemTags.KNUCKLES);
				tag.addOptional(weapon.id());
				int tier = weapon.lootProperties().tier();
				if (tier >= 0) {
					FabricTagProvider<Item>.FabricTagBuilder tierTag = this.getOrCreateTagBuilder(RPGSeriesItemTags.LootTiers.get(tier, RPGSeriesItemTags.LootCategory.WEAPONS));
					tierTag.addOptional(weapon.id());
				}
				String lootTheme = weapon.lootProperties().theme();
				if (lootTheme != null && !lootTheme.isEmpty()) {
					FabricTagProvider<Item>.FabricTagBuilder themeTag = this.getOrCreateTagBuilder(RPGSeriesItemTags.LootThemes.get(lootTheme));
					themeTag.addOptional(weapon.id());
				}
			}

		}

		@Override
		protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
			var armorTagOptions1 = new ArmorOptions(false, true);
			var armorTagOptions2 = new ArmorOptions(true, true);
			generateArmorTags(
					Armors.entries.stream().filter(entry -> entry.name().contains("billporon")).toList(),
					RPGSeriesItemTags.ArmorMetaType.MAGIC,
					armorTagOptions1
			);
			generateArmorTags(
					Armors.entries.stream().filter(entry -> !entry.name().contains("billporon")).toList(),
					RPGSeriesItemTags.ArmorMetaType.MAGIC,
					armorTagOptions2
			);

			generateKnuckleTags(WeaponsRegister.entries);
			var spellInfinityTag = getOrCreateTagBuilder(SpellEngineItemTags.ENCHANTABLE_SPELL_INFINITY);
			spellInfinityTag.addTag(ModItemTags.KNUCKLES);
			var spellHasteTag = getOrCreateTagBuilder(SpellPowerTags.Items.Enchantable.HASTE);
			spellHasteTag.addTag(ModItemTags.KNUCKLES);
			var criticalDamageTag  = getOrCreateTagBuilder(SpellPowerTags.Items.Enchantable.CRITICAL_DAMAGE);
			criticalDamageTag .addTag(ModItemTags.KNUCKLES);
			var spellPowerTag  = getOrCreateTagBuilder(SpellPowerTags.Items.Enchantable.SPELL_POWER_GENERIC);
			spellPowerTag .addTag(ModItemTags.KNUCKLES);
			var unbreakingTag = getOrCreateTagBuilder(ItemTags.DURABILITY_ENCHANTABLE);
			unbreakingTag.addTag(ModItemTags.KNUCKLES);
			var sharpnessTag = getOrCreateTagBuilder(ItemTags.SHARP_WEAPON_ENCHANTABLE);
			sharpnessTag.addTag(ModItemTags.KNUCKLES);
			var meleeTag = getOrCreateTagBuilder(ItemTags.SWORDS);
			meleeTag.addTag(ModItemTags.KNUCKLES);

		}
	}

	public static class UnsmeltGenerator extends FabricRecipeProvider {
		public UnsmeltGenerator(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
			super(output, registriesFuture);
		}

		public static int UNSMELT_TIME = 300;

		@Override
		public void generate(RecipeExporter exporter) {
			disassembleArmor(exporter, Armors.orieneArmorSet, Items.LEATHER);
			disassembleArmor(exporter, Armors.phaslebArmorSet, Items.GOLD_NUGGET);
			disassembleArmor(exporter, Armors.akenArmorSet, Items.NETHERITE_SCRAP);

			disassemble(exporter,
					WeaponsRegister.entries.stream()
							.filter(entry -> entry.id().getPath().contains("gold"))
							.map(entry -> (ItemConvertible) entry.item()).toList(),
					Items.GOLD_NUGGET);
			disassemble(exporter,
					WeaponsRegister.entries.stream()
							.filter(entry -> entry.id().getPath().contains("iron"))
							.map(entry -> (ItemConvertible) entry.item()).toList(),
					Items.IRON_NUGGET);
			disassemble(exporter,
					WeaponsRegister.entries.stream()
							.filter(entry -> entry.id().getPath().contains("netherite"))
							.map(entry -> (ItemConvertible) entry.item()).toList(),
					Items.NETHERITE_SCRAP);
		}

		private static void disassembleArmor(RecipeExporter exporter, Armor.Set armorSet, Item output) {
			FabricRecipeProvider.offerSmelting(exporter,
					armorSet.pieces(),
					RecipeCategory.MISC,
					output,
					0.1f,
					UNSMELT_TIME,
					"disassemble"
			);
			FabricRecipeProvider.offerBlasting(exporter,
					armorSet.pieces(),
					RecipeCategory.MISC,
					output,
					0.1f,
					UNSMELT_TIME / 2,
					"disassemble"
			);
		}

		private static void disassemble(RecipeExporter exporter, List<ItemConvertible> items, Item output) {
			FabricRecipeProvider.offerSmelting(exporter,
					items,
					RecipeCategory.MISC,
					output,
					0.1f,
					UNSMELT_TIME,
					"disassemble"
			);
			FabricRecipeProvider.offerBlasting(exporter,
					items,
					RecipeCategory.MISC,
					output,
					0.1f,
					UNSMELT_TIME / 2,
					"disassemble"
			);
		}
	}
}
