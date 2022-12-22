package com.healing.gamelogic.actions;

import com.healing.entity.Boss;
import com.healing.entity.Entity;
import com.healing.entity.attacks.NPCAttack;
import java.util.ArrayList;

public class BossAction extends Action {
  private final Boss boss;
  private final NPCAttack npcAttack;

  public BossAction(Boss boss, ArrayList<Entity> targets, NPCAttack npcAttack, String id) {
    super(boss, targets, id);
    this.boss = boss;
    this.npcAttack = npcAttack;
  }

  @Override
  public void performAction() {
    for (var target : targets) {
      target.reduceHealth(npcAttack.getDamageAmount());
    }

    System.out.println(
        "Performed Boss Action, "
            + boss.getName()
            + " casted "
            + npcAttack.getName()
            + " for "
            + npcAttack.getDamageAmount()
            + " damage on "
            + getTargetsInfo());
  }
}
