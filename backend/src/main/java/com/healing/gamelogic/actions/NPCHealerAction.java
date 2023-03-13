package com.healing.gamelogic.actions;

import com.healing.entity.Entity;
import com.healing.entity.Healer;
import java.util.List;
import lombok.Getter;

@Getter
public class NPCHealerAction extends Action {
  private final Healer healer;
  private final Integer healAmount = 10;

  public NPCHealerAction(Healer healer, List<Entity> targets, String id) {
    super(healer, targets, id);
    this.healer = healer;
  }

  @Override
  public void performAction() {
    for (var target : targets) {
      target.increaseHealth(healAmount);
    }
    if (shouldLog) {
      System.out.println(
          "Performed NPC Heal Action, "
              + healer.getId()
              + " casted Heal Aura for "
              + healAmount
              + " healing on "
              + getTargetsInfo());
    }
  }
}
