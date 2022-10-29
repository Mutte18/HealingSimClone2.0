package com.healing.gamelogic.actions;

import com.healing.entity.Dps;
import com.healing.entity.Entity;
import com.healing.spell.spellbook.Spell;
import java.util.ArrayList;

public class NPCAction extends Action {
  private final Dps dps;

  public NPCAction(Dps dps, ArrayList<Entity> targets, Spell spell, String id) {
    super(dps, targets, spell, id);
    this.dps = dps;
  }

  @Override
  public void performAction() {
    for (var target : targets) {
      target.reduceHealth(spell.getDamageAmount());
    }
    System.out.println(
        "Performed NPC Action, "
            + dps
            + " casted "
            + spell.getName()
            + " for "
            + spell.getDamageAmount()
            + " damage on "
            + targets);
  }
}
