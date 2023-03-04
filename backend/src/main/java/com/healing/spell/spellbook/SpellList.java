package com.healing.spell.spellbook;

import lombok.Getter;

@Getter
public enum SpellList {
  FLASH_HEAL("Flash Heal", "0", 100, 0, 100, 0),
  RENEW("Renew", "1", 100, 0, 0, 0),
  CHAIN_HEAL("Chain Heal", "2", 250, 2, 150, 0),
  RIPTIDE("Riptide", "3", 100, 0, 50, 0);

  private final String name;
  private final String id;
  private final Integer manaCost;
  private final Integer additionalTargets;
  private final Integer healAmount;
  private final Integer damageAmount;

  SpellList(
      String name,
      String id,
      Integer manaCost,
      Integer additionalTargets,
      Integer healAmount,
      Integer damageAmount) {

    this.name = name;
    this.id = id;
    this.manaCost = manaCost;
    this.additionalTargets = additionalTargets;
    this.healAmount = healAmount;
    this.damageAmount = damageAmount;
  }
}
