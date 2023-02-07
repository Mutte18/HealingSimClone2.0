# Game Idea:

You are the player, acting as a healer for a raid group attempting to kill raid bosses.
Your objective is to keep the raid alive by casting different healing spells, all the while managing your mana.

At your arsenal you will have single target and AoE healing spells. Some of them instant, and some with a cast time.
Spells may or may not have a cooldown, limiting how often they can be cast. Aswell as having different mana costs and differ in the amount that they heal.

There will possibly also be healing over time effects that can be placed on the raiders. These will heal a small amount every X seconds over Y period.

# Raid group: 
The raid group consists of 20 raiders. They are two tanks, 4 healers, one player and the rest DPS.
The tanks has a large health pool, and when alive, will be given priority to be auto attacked by the boss.
The healers has a healing aura that will heal all raiders slightly when they are alive.
Finally, the DPS do damage to the boss when they are alive.

If a healer or DPS dies, they will no longer contribute to healing, or damage.

# Bosses
The main objective of the game is to kill bosses. These bosses have a very large health bar and will attack the raid group.
The boss will target either tank if they are alive. Otherwise another raider will be targeted. 
The boss can also have special attacks that they will perform occasionally. Often dealing damage to multiple targets.

# Spell casting
As the player, you have multiple spells at your disposal, with a global cooldown of 1 second between each spell cast.
If a spell has a cast time, it will take X time after starting the cast until the cast is finished and the spell performs its effect.
During this cast time, you as the player may click ESC to cancel the cast. When starting the cast, you must have enough mana. But only when the cast is finished is the mana actually reduced.
If cancelling the cast, no mana is reduced.

# Life and Death
When a raider is healed, they have to be alive. Meaning that their health bar is above 0. If the health bar is reduced to 0, they are tagged as DEAD.
Healing a target for more than their maximum health will cause an overheal, and will only heal up to the max health. The over heal amount is "wasted".
Likewise, a raider taking damage that would cause them to go beyond 0 in health, will result in an overkill, still just causing them to die.

A dead raider cannot be brought back to life. They are dead and will no longer contribute with healing or damage. Making it ultimately harder to defeat the boss.

# Type of spells
## Instant spells
* Holy Light - 
 Instantly heals the target for medium health. 5s cooldown. Instant cast. Cost 200 Mana

* Riptide - Instantly heals the target for small amount. Also places a HoT on the target healing every 2s for low health. Chain heal casted on this target consumes the HoT and increases Chain Heal amount by 25%. 6s cooldown. Instant cast. Cost 75 mana
## Cast time spells
* Flash Heal - Heals a single target for medium to high health. No Cooldown. 2s Cast time. Cost 100 Mana
* Chain Heal - Heals the primary target for medium health, then bounces to two additional injured allies. No cooldown. 2s Cast time. Cost 400 mana
* Circle of Healing - Heals five injured targets for low to medium health. 10s Cooldown. 1s Cast time. Cost 350 mana

## HoT spells
* Renew - Places a HoT on the target, healing every 2s for low health. No cooldown. Instant cast. Cost 75 mana
## Absorb spells
* Power word: Shield

## Channeling spells
* Tranquility - Channels a healing rain for 8 seconds, healing every raider every 1s for medium health. 2 Min cooldown. 8s Channel time. Cost 500 Mana

