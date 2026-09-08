package net.forcemaster_rpg;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.forcemaster_rpg.datagen.*;
import net.forcemaster_rpg.effect.ForcemasterEffects;
import net.forcemaster_rpg.entity.NenSphereBeamEntity;
import net.forcemaster_rpg.item.armor.Armors;
import net.forcemaster_rpg.item.tag.ModItemTags;
import net.forcemaster_rpg.item.weapons.WeaponsRegister;
import net.forcemaster_rpg.sounds.ModSounds;
import net.forcemaster_rpg.spell.ForcemasterSpells;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryBuilder;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.spell_engine.api.datagen.SimpleSoundGeneratorV2;
import net.spell_engine.api.datagen.SpellGenerator;
import net.spell_engine.api.spell.Spell;
import net.spell_engine.api.spell.registry.SpellRegistry;
import net.spell_engine.api.tags.SpellEngineItemTags;
import net.spell_engine.api.tags.SpellTags;
import net.spell_engine.rpg_series.item.Armor;
import net.spell_engine.rpg_series.item.Weapon;
import net.spell_engine.rpg_series.datagen.RPGSeriesDataGen;
import net.spell_engine.rpg_series.tags.RPGSeriesItemTags;
import net.spell_power.api.SpellPowerTags;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import static net.forcemaster_rpg.ForcemasterClassMod.MOD_ID;

public class ForcemasterClassModDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		ForcemasterVanillaAdvancementProvider.init();
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
		pack.addProvider(ItemTagGenerator::new);
		pack.addProvider(UnsmeltGenerator::new);
		pack.addProvider(SpellGen::new);
		pack.addProvider(SoundGen::new);
		pack.addProvider(ModModelProvider::new);
		pack.addProvider(WeaponAttributesGenerator::new);
		pack.addProvider(ForcemasterAdvancementDataGen::new);
		pack.addProvider(ForcemasterVanillaAdvancementProvider::new);
		pack.addProvider(LangGenerator::new);
		pack.addProvider(SpellTagGenerator::new);
		pack.addProvider(ForcemasterCraftingRecipes::new);
		pack.addProvider(ForcemasterSmithingRecipes::new);
		pack.addProvider(ConditionalCraftingRecipes::new);
	}

	/// Fabric's datagen `WrapperLookup` is assembled per entrypoint, so a `FabricTagProvider<Spell>` throws
	/// `Registry spell_engine:spell not found` unless the entrypoint contributes the registry itself.
	@Override
	public void buildRegistry(RegistryBuilder registryBuilder) {
		RPGSeriesDataGen.buildRegistry(registryBuilder);
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

	public static class LangGenerator extends FabricLanguageProvider {
		protected LangGenerator(FabricDataOutput dataOutput) {
			super(dataOutput, "en_us");
		}

		@Override
		public void generateTranslations(TranslationBuilder translationBuilder) {
			translationBuilder.add("itemGroup.forcemaster_rpg.general", "Forcemaster");

			translationBuilder.add("item.forcemaster_rpg.spell_book/forcemaster", "Force Mastery");
			translationBuilder.add("item.forcemaster_rpg.spell_book/forcemaster.spell_binding.description",
					"Spell Book of Forcemasters, using martial weapons, the knuckle. Dealing melee and arcane damage with the power of the force.\n- Strengths: Fast attacks dealing physical and magical damage.\n- Weaknesses: Ranged Enemies\n- Equipment: Light Armor");
			translationBuilder.add("item.forcemaster_rpg.spell_scroll/forcemaster", "Forcemaster Skill Scroll");

			WeaponsRegister.entries.forEach(entry -> {
				if (entry.item() != null && entry.translatedName() != null && !entry.translatedName().isEmpty()) {
					translationBuilder.add(entry.item(), entry.translatedName());
				}
			});

			Armors.entries.forEach(entry -> {
				var set = entry.armorSet();
				if (set.headTranslation != null && !set.headTranslation.isEmpty()) {
					translationBuilder.add(((Item) set.head).getTranslationKey(), set.headTranslation);
				}
				if (set.chestTranslation != null && !set.chestTranslation.isEmpty()) {
					translationBuilder.add(((Item) set.chest).getTranslationKey(), set.chestTranslation);
				}
				if (set.legsTranslation != null && !set.legsTranslation.isEmpty()) {
					translationBuilder.add(((Item) set.legs).getTranslationKey(), set.legsTranslation);
				}
				if (set.feetTranslation != null && !set.feetTranslation.isEmpty()) {
					translationBuilder.add(((Item) set.feet).getTranslationKey(), set.feetTranslation);
				}
			});

			ForcemasterEffects.entries.forEach(entry -> {
				translationBuilder.add(entry.effect.getTranslationKey(), entry.title);
				if (!entry.description.isEmpty()) {
					translationBuilder.add(entry.effect.getTranslationKey() + ".description", entry.description);
				}
			});

			ForcemasterSpells.entries.forEach(entry -> {
				var id = entry.id();
				translationBuilder.add("spell." + id.getNamespace() + "." + id.getPath() + ".name", entry.title());
				translationBuilder.add("spell." + id.getNamespace() + "." + id.getPath() + ".description", entry.description());
			});

			translationBuilder.add(NenSphereBeamEntity.ENTITY_TYPE, "Nen Sphere");

			translationBuilder.add("equipment_set.forcemaster_rpg.billporon", "Billporon's Focus");

			translationBuilder.add("tag.item.forcemaster_rpg.knuckles", "Knuckles");
			translationBuilder.add("tag.item.forcemaster_rpg.fist_weapons", "Fist Weapons");

			for (var entry : ForcemasterAdvancementDataGen.getEntries()) {
				translationBuilder.add(entry.titleKey(), entry.title());
				translationBuilder.add(entry.descriptionKey(), entry.description());
			}
			for (var entry : ForcemasterVanillaAdvancementProvider.getEntries()) {
				translationBuilder.add(entry.titleKey(), entry.title());
				translationBuilder.add(entry.descriptionKey(), entry.description());
			}
		}
	}

	public static class ItemTagGenerator extends RPGSeriesDataGen.ItemTagGenerator {
		public ItemTagGenerator(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
			super(dataOutput, registryLookup);
		}
		public void armoryTags(List<Armor.Entry> armors) {
			this.armoryTags(armors, EnumSet.noneOf(RPGSeriesItemTags.ArmorMetaType.class));
		}

		public void armoryTags(List<Armor.Entry> armors, RPGSeriesItemTags.ArmorMetaType metaType) {
			this.armoryTags(armors, EnumSet.of(metaType));
		}

		public void armoryTags(List<Armor.Entry> armors, EnumSet<RPGSeriesItemTags.ArmorMetaType> metaTypes) {
			Iterator var3 = armors.iterator();

			while(var3.hasNext()) {
				Armor.Entry armor = (Armor.Entry)var3.next();
				Armor.Set set = armor.armorSet();
				// 1.20.1 has no `minecraft:{head,chest,leg,foot}_armor` item tags (1.20.5 additions).
				// On 1.20.5+ those feed `#minecraft:trimmable_armor` implicitly, which is what made RPG armor
				// trimmable there; here that tag is an explicit list, so the pieces opt into it directly -
				// the same thing SpellEngine's own `generateArmorTags` does.
				FabricTagProvider<Item>.FabricTagBuilder trimmableTag = this.getOrCreateTagBuilder(ItemTags.TRIMMABLE_ARMOR);
				for (var pieceId : armor.armorSet().pieceIds()) {
					trimmableTag.addOptional((Identifier) pieceId);
				}
				Iterator var12;

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

		List<String> armoryKeywords = List.of("billporon");
		@Override
		protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
			var armorTagOptions1 = new ArmorOptions(false, true);
			var armorTagOptions2 = new ArmorOptions(true, true);
			armoryTags(
					Armors.entries.stream().filter(entry -> armoryKeywords.stream().anyMatch(entry.name()::contains)).toList(),
					RPGSeriesItemTags.ArmorMetaType.MAGIC
			);
			generateArmorTags(
					Armors.entries.stream().filter(entry -> armoryKeywords.stream().noneMatch(entry.name()::contains)).toList(),
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
			var criticalChanceTag  = getOrCreateTagBuilder(SpellPowerTags.Items.Enchantable.CRITICAL_CHANCE);
			criticalChanceTag .addTag(ModItemTags.KNUCKLES);
			var spellPowerTag  = getOrCreateTagBuilder(SpellPowerTags.Items.Enchantable.SPELL_POWER_GENERIC);
			spellPowerTag .addTag(ModItemTags.KNUCKLES);
			// `#minecraft:durability_enchantable` / `#minecraft:sharp_weapon_enchantable` do not exist before
			// 1.21 - vanilla enchantability is decided by `Enchantment#isAcceptableItem` there, and the
			// knuckles are `SwordItem`s, so Unbreaking and Sharpness already apply without a tag.
			var meleeTag = getOrCreateTagBuilder(ItemTags.SWORDS);
			meleeTag.addTag(ModItemTags.KNUCKLES);

			var rpgSeriesMeleeWeaponTag = getOrCreateTagBuilder(RPGSeriesItemTags.Archetype.tag(RPGSeriesItemTags.RoleArchetype.MELEE_DAMAGE));
			rpgSeriesMeleeWeaponTag.addTag(ModItemTags.KNUCKLES);
			var rpgSeriesMagicWeaponTag = getOrCreateTagBuilder(RPGSeriesItemTags.Archetype.tag(RPGSeriesItemTags.RoleArchetype.MAGIC_DAMAGE));
			rpgSeriesMagicWeaponTag.addTag(ModItemTags.KNUCKLES);

		}
	}

	public static class SpellTagGenerator extends FabricTagProvider<Spell> {
		public SpellTagGenerator(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
			super(output, SpellRegistry.KEY, registriesFuture);
		}

		@Override
		protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
			var namespace = MOD_ID;
			var treasureTagBuilder = getOrCreateTagBuilder(SpellTags.TREASURE);
			var processedBooks = new HashSet<ForcemasterSpells.Book>();
			ForcemasterSpells.entries.forEach(entry -> {
				if (entry.book() != null) {
					var bookTagKey = SpellTags.spellBook(namespace, entry.book().toString().toLowerCase());
					var bookTag = getOrCreateTagBuilder(bookTagKey);
					bookTag.addOptional(entry.id());
					var scrollTagKey = SpellTags.spellScroll(namespace, entry.book().toString().toLowerCase());
					var scrollTag = getOrCreateTagBuilder(scrollTagKey);
					scrollTag.addOptional(entry.id());
					if (processedBooks.add(entry.book())) {
						treasureTagBuilder.addOptionalTag(scrollTagKey);
					}
				}
			});

			var knuckleArcaneOverflowKey = TagKey.of(SpellRegistry.KEY, new Identifier(MOD_ID, "arcane_overflow_triggers"));
			var knuckleArcaneOverflowTag = getOrCreateTagBuilder(knuckleArcaneOverflowKey);
			knuckleArcaneOverflowTag.addOptionalTag(new Identifier(MOD_ID, "spell_book/forcemaster"));
			knuckleArcaneOverflowTag.addOptional(new Identifier("more_rpg_classes", "burstcrack"));
		}
	}

	public static class UnsmeltGenerator extends FabricRecipeProvider {
		public UnsmeltGenerator(FabricDataOutput output) {
			super(output);
		}

		public static int UNSMELT_TIME = 300;

		@Override
		public void generate(Consumer<RecipeJsonProvider> exporter) {
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

		private static void disassembleArmor(Consumer<RecipeJsonProvider> exporter, Armor.Entry armorEntry, Item output) {
			FabricRecipeProvider.offerSmelting(exporter,
					armorEntry.armorSet().pieces(),
					RecipeCategory.MISC,
					output,
					0.1f,
					UNSMELT_TIME,
					"disassemble"
			);
			FabricRecipeProvider.offerBlasting(exporter,
					armorEntry.armorSet().pieces(),
					RecipeCategory.MISC,
					output,
					0.1f,
					UNSMELT_TIME / 2,
					"disassemble"
			);
		}

		private static void disassemble(Consumer<RecipeJsonProvider> exporter, List<ItemConvertible> items, Item output) {
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
