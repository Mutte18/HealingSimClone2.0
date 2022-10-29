package com.healing.gamelogic.actions;

import com.healing.entity.Boss;
import com.healing.entity.Entity;
import com.healing.spell.spellbook.Spell;
import java.util.ArrayList;

public class BossAction extends Action {
  private final Boss boss;

  public BossAction(Boss boss, ArrayList<Entity> targets, Spell spell, String id) {
    super(boss, targets, spell, id);
    this.boss = boss;
  }

  @Override
  public void performAction() {
    for (var target : targets) {
      target.reduceHealth(spell.getDamageAmount());
    }

    System.out.println(
        "Performed Boss Action, "
            + boss
            + " casted "
            + spell.getName()
            + " for "
            + spell.getDamageAmount()
            + " damage on "
            + targets);
  }
}
