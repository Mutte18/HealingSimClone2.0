package com.healing.gamelogic.actions;

import com.healing.entity.Boss;
import com.healing.entity.Entity;
import com.healing.spell.spellbook.Spell;

public class BossAction extends Action {
  private final Boss boss;
  private final Spell spell;
  private final Entity target;
  private final String id;

  public BossAction(Boss boss, Entity target, Spell spell, String id) {
    super(target, id);
    this.boss = boss;
    this.target = target;
    this.spell = spell;
    this.id = id;
  }

  @Override
  public void performAction() {
    target.reduceHealth(spell.getDamageAmount());
    System.out.println(
        "Performed Boss Action, "
            + boss.getName()
            + " casted "
            + spell.getName()
            + " for "
            + spell.getDamageAmount()
            + " damage on "
            + target);
  }
}
