package com.healing.spell.spellbook.spellType;

import com.healing.spell.spellbook.Spell;
import lombok.Getter;

@Getter
public abstract class HealSpell extends Spell {
  private final Integer healAmount;

  public HealSpell(
      String spellId, String name, Integer manaCost, Integer additionalTargets, Integer healAmount) {
    super(spellId, name, manaCost, additionalTargets);
    this.healAmount = healAmount;
  }
}
