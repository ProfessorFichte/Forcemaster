# 2.4.14 - 1.21.1
- fix lang file

# 2.4.13 - 1.21.1
- change sound registry due to possible crash
- Fix some Knuckle Models
- Removed Baraqijal Esna as T3 Spell
- Added Belial Smashing as the new T3 Spell

# 2.4.12 - 1.21.1
### Visual Enhancements
- Update 3D Knuckle Models
### Bug Fixes
- fix minecraft armor tag problem with the Billporon Armor Set

# 2.4.11 - 1.21.1
- Fix Crash with newest AzureLib Version Crash when rendering tier 5 armors #6
- License -> ARR
- Update fabric.mod.json
- Add Datagen
- Internal Technical Changes -> Stonehand
- Now is a Stash Effect and the Stun via melee impacts is handled with a custom impact
- Stonehand Stun is only possible if you use a Fist-Weapon (Compat via Tag: forcemaster_rpg:fist_weapons)

# 2.4.10 - 1.21.1
- New Armor Set (T5) Billporon is available with passive modifiers
- The Armor gets enabled when Armory (RPG-Series) is installed
- You can also enable Billporon without Armory with the tweaksconfig -> ignore_items_required_mods
- Added all Forcemaster Armor to magical armor type (Spell Engine related)

# 2.4.9 - 1.21.1
- KnockUp Impact does not work on #c:bosses

# 2.4.8 - 1.21.1
- Update to MRPGLib 2.3.0
- Use new Knock Custom Impact Type from MRPGLib
- Nerf Arcane Fuse Effect

# 2.4.7 - 1.21.1
### Visual Enhancements
- Update Inventory Textures
- Update Armor Model Assets
### Internal Changes
- Spell Engine 1.7
- Rebalance some Spells, mostly buffed them
- fix Light of Baraqijal Status Effect not decreasing Attack Power

# 2.4.6 - 1.21.1
- Fix Ruby Knuckle Missing Armor Stat #5

# 2.4.5 - 1.21.1
- Update for newest Spell Engine API
- Buff Asal Spell Range and Area
- tweaked some cooldowns and effect durations

# 2.4.4 - 1.21.1
- add armor meta type tags

# 2.4.3 - 1.21.1
- fix tag messing up with models

# 2.4.2 - 1.21.1
- Add Spell Scroll Textures
- Add smelting recipes for disassembling weapons and armor pieces
- Add Datagen
- Removed the special knuckles, they will be reintroduced with the 1.21 Loot & Explore Mod

# 2.4.1 - 1.21.1
- Spell Engine 1.6 Update!
- changed some tags
- Oriene Armor Model Update & Texture Improvement (Done by HaxTheCharizard, Thanks!)

# 2.4.0 - 1.21.1
- Spell Engine 1.5 Update!
- **BALANCING Update!**
- Buffed the Barq Esna Effect
- Nerfed Stonehand & Burst Crack a bit
- Burst Crack now also deals additional Arcane damage
- Removed Spell Haste from Knuckles, they now give Armor because they're heavy gauntlets
- Reduced Arcane Spell Power on Knuckles
- Increased Generic Attack Damage on Knuckles a bit
- Changed Forcemaster Book Spell Tier List: Stonehand -> Burst Crack -> Baraqijal Esna -> Asalraalaikum
- Update to Fabric Loom 1.9
- Add TweaksConfig, so special Weapons can also be used without Better End & Nether and the Aether

# 2.3.0 - 1.21.1
**- Spell Engine 1.4 Update!**
- AzureLib Armor 3.0 Update!

2.2.1
- Update for Spell Scrolls
- Removed the Straight Punch Spell, because its obsolete
- Sonic Hand, Belial Smashing & Arcane Blast are disabled as passive spells for the special axes
- The spells will return in the future
- made casting spells with gallant gauntlets possible
- stunning targets with spells while stone hand is active is now possible

2.2.0
- Spell Engine 1.2 Update
- added the Holy Knuckle (Aether Variant)

2.1.3
- Mipmap Issue on 1.21.1 #3 Fix
- fixed some tag errors with the aken armor (3rd tier)
- fixed an error with aken armor set recipes
- added tweaks config for stone hand stun chance and knuckle knock
- added a knock up immunity entity type tag
- added Gallant Gauntlets to the Fist Weapons Tag (Compat for Forcemaster Effects like Stonehand)
- added MC Dungeons Weapons Gauntlets to the Fist Weapons Tag (Compat for Forcemaster Effects like Stonehand)

2.1.2
- make Special Knuckles also fireproof (SpellEngineTiersMixin)
- changed the netherite_aken armor set to just aken

2.1.1
- fixed damage translation error of some spells

2.1.0
- Added 3rd tier Armor, Aken Armor (Netherite Tier) for the Forcemaster Class
- Changed balancing of Armor & Weapons, to match the new RPG Series balancing
- Added all the other Knuckles to the Knuckle Advancement

2.0.3
- Spell Engine API Update (1.1.0 + 1.21.1)
- Burst Crack & Belial Smashing now stuns targets if the caster has the StoneHand Effect
- fixed Tooltips for Knuckles
- fixed translation file damage calculating-translation for spells
- fixed low durability of phasleb armor

2.0.2
- made stun vanilla particles disappear when hit by stonehand
- fixed an error that made the game crash while attacking the ender dragon with knuckles

2.0.1
- forgot to change item to id in the advancement section
- for phasleb armor the arcane bonus was addition instead of multiply
- added compat for stonehand for other fist or martial art weapons with the "fist_weapons"-tag
- added a tooltip for the knuckle knock up ability

2.0.0
- 1.21 update
- forgot to update the license in the past
- added a dual wielding animation and mechanic for knuckles with better combat