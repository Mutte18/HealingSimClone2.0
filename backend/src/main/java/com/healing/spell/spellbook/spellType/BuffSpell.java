package com.healing.spell.spellbook.spellType;

import com.healing.buff.Buff;
import com.healing.spell.spellbook.Spell;
import lombok.Getter;

@Getter
public abstract class BuffSpell extends Spell {
  private final Buff buff;

  public BuffSpell(String spellId, String name, Integer manaCost, Integer additionalTargets, Buff buff) {
    super(spellId, name, manaCost, additionalTargets);
    this.buff = buff;
  }
}
