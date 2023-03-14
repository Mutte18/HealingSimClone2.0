package com.healing.gamelogic.actions;

import com.healing.entity.Boss;
import com.healing.entity.Entity;
import com.healing.entity.attacks.NPCAttack;
import java.util.List;
import lombok.Getter;

@Getter
public class BossAction extends Action {
  private final Boss boss;
  private final NPCAttack npcAttack;
  private final ActionType actionType;

  public BossAction(
      Boss boss, List<Entity> targets, NPCAttack npcAttack, String id, ActionType actionType) {
    super(boss, targets, id);
    this.boss = boss;
    this.npcAttack = npcAttack;
    this.actionType = actionType;
  }

  @Override
  public void performAction() {
    for (var target : targets) {
      target.reduceHealth(npcAttack.getDamageAmount());
      if (npcAttack.getDebuff() != null) {
        target.addBuff(npcAttack.getDebuff().clone());
      }
    }

    if (shouldLog) {
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
}
