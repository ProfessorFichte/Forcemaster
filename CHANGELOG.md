# Changelog

All notable changes to Forcemaster are documented in this file.
The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/).

## [3.2.1+1.21.1]

### Added

- The Loot & Explore themed Knuckles count as Ender Dragon, Wither, Glacial and Elder Guardian weapons for Loot & Explore's smithing advancements

### Fixed

- Recipes from smelting Forcemaster gear down are now unlocked in the recipe book
- The Ender Dragon Knuckle recipe loads again, it used old Loot & Explore item ids
- Sound effects are now mono and 48 kHz, so they come from the right direction and get quieter with distance like vanilla sounds

### Removed

- The old spell book recipe, it only crafted a blank written book since the Spell Engine 1.10 update (spell books come from the Spell Binding Table)

## [3.2.0+1.21.1]

### Changed

- Adopt Spell Engine 1.10.9 & More RPG Library 2.8.0
- **BREAKING for modpack/datapack developers:** More RPG Library's namespace was renamed `more_rpg_classes` -> `mrpg_lib` (and its advancement root moved to `mrpg_lib:root`). If you have any data files, loot tables, tags or advancements that reference the old `more_rpg_classes:`/`more_rpg_content:` ids from this mod or MRPGL, update them to `mrpg_lib:`
- Updated internal advancement parents, mod dependency declarations (fabric.mod.json, neoforge.mods.toml)
- Forcemaster equipment is now affiliated loot: it drops more often for players carrying the matching spell book

## [3.1.1+1.21.1]

### Changed

- Drop Forgified Fabric API (FFAPI) as a required dependency
- Replace AzurelibArmor with Armor Model API

## [3.1.0+1.21.1]

### Changed

- Adopt Spell Engine 1.10 - Thanks Daedelus for the PR!

## [3.0.0+1.21.1]

**Spell Expansion**
**Balancing & Internal Changes:**

### Added

- Added uk_ua localization- #8 - thx Ch1sho

### Changed

- Adapt to Spell Engine 1.9.10+ API Changes
- T2 Baraqijal Esna: Launches a bolt of Baraqijal's light, dealing damage and stacking the target with the Light of Baraqijal Effect. 
- Lowering their resistance to arcane magic and damaging them over time.
- T3 Nen Sphere: Charge a magic sphere, unleashing it as a piercing beam. The longer you charge, the further and harder it strikes. 
- Dealing damage and overloading you with Arcane Overflow, scaling with your charge.
- T4 Sonic Hand: Unleashes 5 blindingly fast fist strikes, each dealing melee damage. Consumes Arcane Overflow.
- Clean up Code
- Changed the Color of Forcemaster Particles, Spell Textures and Effect Textures to bright blue, representing another type of "Arcane" Magic
- tweaked some Spell Power Coefficient Numbers and for some Spells used the new PowerBlend feature for better hybrid melee/arcane treatment
- Armory & Arsenal Compat Equipment is now added to a separate Item Group
- Forcemaster Spells are now divided in two Groups: Brawl & Might
- Nen Focus & Arcane Overflow now use Spell Engine's GlowingItemStatusEffect Renderer

### Fixed

- Fixed Burstcrack not triggering Arcane Overflow

## [2.6.0+1.21.1]

**Update to use Spell Engine 1.9.0**

### Changed

- DISCLAIMER: All spell books and spell scrolls will be reset, due to major API changes.
- The Forcemaster Spell Book now offers 3 spells only, to match other classes
-  **Burstcrack** is now the Weapon Skill for Knuckles.
- Slightly Nerfed Burstcrack damage numbers
- Stonehand is now the Tier 2 spell
- The Billporon Armor Set now reduces the cooldown of Belial Smashing
- Technically changed Belial Smashing & Asal to deliver melee hits
- Changed the Oriene Armor Recipes

## [2.5.5+1.21.1]

### Added

- add Spell Binding description for Forcemaster Spell Book

### Fixed

- fix broken item models

## [2.5.4+1.21.1]

### Added

- Add many Datagen methods, so most stuff gets generated

**New Content: Tier 5 Knuckles**
- Federhorn Knuckle:
- Knuckle of Madness:
- Artwork Done by Slepykat!
- Knuckle of Madness uses the Carve Passive from MRPGLib (Chacne to reduce Armor and increase incoming damage)
- New Passive Nen Focus - for the Federhorn Knuckle
- Nen Focus: When you kill a target with Forcemaster Spells, you get Nen Focus, stacking Arcane Overflow with each Melee Hit

## [2.5.3+1.21.1]

### Fixed

- fix minecraft armor tags not loading when armory compat sets are not loading

## [2.5.2+1.21.1]

### Changed

- Item Frames now also show the inventory texture of knuckles
- Update Weapon Spell Power

### Fixed

- fix equipment set bonus resourcepack not loading (for billporon armor set)

## [2.5.1+1.21.1]

### Added

- Add Smithing Recipes for the Billporon Set with new Upgrade Crystals
- Add Blue Aura to Wither Knuckle Model

### Changed

- Update Knuckle Inventory Textures (created by Emil)

### Removed

- remove billporon armor set from rpg-series loot tags

## [2.5.0+1.21.1]

### Added

- Add LNE Knuckle Variants "Fist of Tides","Wyrm's Claw","Glacial Fist","Wither's Bash"

### Changed

- Move to Architectury Enviroment for Multiloader
- NeoForge Beta!
- Changed Spell Book Texture to be more in line with the RPG-Series Books (by SirGhaith, Thanks!)
- LNE-Knuckle Models are created by Pedravila
- Tweak Knuckle Passives a bit
- tweak some inventory knuckle textures (Will become a complete overhaul in the future)

## [2.4.16+1.21.1]

### Changed

- Update Spell Textures
- Update Project Icon
- Change Creative Tab Icon to Phasleb Armor head
- The passive Knock Up Knuckle Effect is now handled via passive spells
- Knuckles now also have a passive that gives Arcane Overflow when casting Forcemaster Spells

### Removed

- Removed Knuckles from RPG Series Weapon Type Tag, uses its own Knuckle Tag "forcemaster_rpg:knuckles"

### Fixed

- Fix Knuckle Model in offhand

## [2.4.15+1.21.1]

### Fixed

- Fix Crash With Companions and Critters

## [2.4.14+1.21.1]

### Changed

- BUG FIX: Fix Crashing when leaving multiplayer server
- Overhaul Advancements
- Change Creative Tab Phasleb Chestplate to Head

### Fixed

- fix lang file

## [2.4.13+1.21.1]

### Added

- Added Belial Smashing as the new T3 Spell

### Changed

- change sound registry due to possible crash

### Removed

- Removed Baraqijal Esna as T3 Spell

### Fixed

- Fix some Knuckle Models

## [2.4.12+1.21.1]

### Changed

**Visual Enhancements**
- Update 3D Knuckle Models

### Fixed

- fix minecraft armor tag problem with the Billporon Armor Set

## [2.4.11+1.21.1]

### Added

- Add Datagen

### Changed

- License -> ARR
- Update fabric.mod.json
- Internal Technical Changes -> Stonehand
- Now is a Stash Effect and the Stun via melee impacts is handled with a custom impact
- Stonehand Stun is only possible if you use a Fist-Weapon (Compat via Tag: forcemaster_rpg:fist_weapons)

### Fixed

- Fix Crash with newest AzureLib Version Crash when rendering tier 5 armors #6

## [2.4.10+1.21.1]

### Added

- New Armor Set (T5) Billporon is available with passive modifiers
- Added all Forcemaster Armor to magical armor type (Spell Engine related)

### Changed

- The Armor gets enabled when Armory (RPG-Series) is installed
- You can also enable Billporon without Armory with the tweaksconfig -> ignore_items_required_mods

## [2.4.9+1.21.1]

### Changed

- KnockUp Impact does not work on #c:bosses

## [2.4.8+1.21.1]

### Changed

- Update to MRPGLib 2.3.0
- Use new Knock Custom Impact Type from MRPGLib
- Nerf Arcane Fuse Effect

## [2.4.7+1.21.1]

### Changed

**Visual Enhancements**
- Update Inventory Textures
- Update Armor Model Assets

**Internal Changes**
- Spell Engine 1.7
- Rebalance some Spells, mostly buffed them
- fix Light of Baraqijal Status Effect not decreasing Attack Power

## [2.4.6+1.21.1]

### Fixed

- Fix Ruby Knuckle Missing Armor Stat #5

## [2.4.5+1.21.1]

### Changed

- Update for newest Spell Engine API
- Buff Asal Spell Range and Area
- tweaked some cooldowns and effect durations

## [2.4.4+1.21.1]

### Added

- add armor meta type tags

## [2.4.3+1.21.1]

### Fixed

- fix tag messing up with models

## [2.4.2+1.21.1]

### Added

- Add Spell Scroll Textures
- Add smelting recipes for disassembling weapons and armor pieces
- Add Datagen

### Removed

- Removed the special knuckles, they will be reintroduced with the 1.21 Loot & Explore Mod

## [2.4.1+1.21.1]

### Changed

- Spell Engine 1.6 Update!
- changed some tags
- Oriene Armor Model Update & Texture Improvement (Done by HaxTheCharizard, Thanks!)

## [2.4.0+1.21.1]

### Added

- Add TweaksConfig, so special Weapons can also be used without Better End & Nether and the Aether

### Changed

- Spell Engine 1.5 Update!
- **BALANCING Update!**
- Buffed the Barq Esna Effect
- Nerfed Stonehand & Burst Crack a bit
- Burst Crack now also deals additional Arcane damage
- Reduced Arcane Spell Power on Knuckles
- Increased Generic Attack Damage on Knuckles a bit
- Changed Forcemaster Book Spell Tier List: Stonehand -> Burst Crack -> Baraqijal Esna -> Asalraalaikum
- Update to Fabric Loom 1.9

### Removed

- Removed Spell Haste from Knuckles, they now give Armor because they're heavy gauntlets

## [2.3.0+1.21.1]

**- Spell Engine 1.4 Update!**
2.2.1
2.2.0
2.1.3
2.1.2
2.1.1
2.1.0
2.0.3
2.0.2
2.0.1
2.0.0

### Added

- added the Holy Knuckle (Aether Variant)

- added tweaks config for stone hand stun chance and knuckle knock
- added a knock up immunity entity type tag
- added Gallant Gauntlets to the Fist Weapons Tag (Compat for Forcemaster Effects like Stonehand)
- added MC Dungeons Weapons Gauntlets to the Fist Weapons Tag (Compat for Forcemaster Effects like Stonehand)

- Added 3rd tier Armor, Aken Armor (Netherite Tier) for the Forcemaster Class
- Added all the other Knuckles to the Knuckle Advancement

- added compat for stonehand for other fist or martial art weapons with the "fist_weapons"-tag
- added a tooltip for the knuckle knock up ability

- added a dual wielding animation and mechanic for knuckles with better combat

### Changed

- AzureLib Armor 3.0 Update!

- Update for Spell Scrolls
- Sonic Hand, Belial Smashing & Arcane Blast are disabled as passive spells for the special axes
- The spells will return in the future
- made casting spells with gallant gauntlets possible
- stunning targets with spells while stone hand is active is now possible

- Spell Engine 1.2 Update
- Mipmap Issue on 1.21.1 #3 Fix
- make Special Knuckles also fireproof (SpellEngineTiersMixin)
- changed the netherite_aken armor set to just aken

- Changed balancing of Armor & Weapons, to match the new RPG Series balancing
- Spell Engine API Update (1.1.0 + 1.21.1)
- Burst Crack & Belial Smashing now stuns targets if the caster has the StoneHand Effect
- made stun vanilla particles disappear when hit by stonehand
- forgot to change item to id in the advancement section
- for phasleb armor the arcane bonus was addition instead of multiply
- 1.21 update
- forgot to update the license in the past

### Removed

- Removed the Straight Punch Spell, because its obsolete

### Fixed

- fixed some tag errors with the aken armor (3rd tier)
- fixed an error with aken armor set recipes
- fixed damage translation error of some spells

- fixed Tooltips for Knuckles
- fixed translation file damage calculating-translation for spells
- fixed low durability of phasleb armor

- fixed an error that made the game crash while attacking the ender dragon with knuckles
