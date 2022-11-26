package com.healing.gamelogic.actions;

import com.healing.entity.Dps;
import com.healing.entity.Entity;
import com.healing.entity.attacks.NPCAttack;
import java.util.ArrayList;

public class NPCAction extends Action {
  private final Dps dps;
  private final NPCAttack npcAttack;

  public NPCAction(Dps dps, ArrayList<Entity> targets, NPCAttack npcAttack, String id) {
    super(dps, targets, id);
    this.dps = dps;
    this.npcAttack = npcAttack;
  }

  @Override
  public void performAction() {
    for (var target : targets) {
      target.reduceHealth(npcAttack.getDamageAmount());
    }
    System.out.println(
        "Performed NPC Action, "
            + dps
            + " casted "
            + npcAttack.getName()
            + " for "
            + npcAttack.getDamageAmount()
            + " damage on "
            + targets);
  }
}
