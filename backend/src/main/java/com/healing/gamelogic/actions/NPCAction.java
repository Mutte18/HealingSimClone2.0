package com.healing.gamelogic.actions;

import com.healing.entity.Dps;
import com.healing.entity.Entity;
import com.healing.spell.spellbook.Spell;

public class NPCAction extends Action {
  private final Dps dps;
  private final Spell spell;
  private final Entity target;
  private final String id;

  public NPCAction(Dps dps, Entity target, Spell spell, String id) {
    super(target, id);
    this.dps = dps;
    this.spell = spell;
    this.target = target;
    this.id = id;
  }

  @Override
  public void performAction() {
    target.reduceHealth(spell.getDamageAmount());
    System.out.println(
        "Performed NPC Action, "
            + dps.getId()
            + " casted "
            + spell.getName()
            + " for "
            + spell.getDamageAmount()
            + " damage on "
            + target);
  }
}
