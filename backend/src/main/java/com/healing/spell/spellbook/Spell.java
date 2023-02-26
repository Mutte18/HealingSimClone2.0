package com.healing.spell.spellbook;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Spell {
  private String spellId;
  private String name;
  private Integer manaCost;
  private Integer additionalTargets;

  public Spell(String spellId, String name, Integer manaCost, Integer additionalTargets) {
    this.spellId = spellId;
    this.name = name;
    this.manaCost = manaCost;
    this.additionalTargets = additionalTargets;
  }
}
