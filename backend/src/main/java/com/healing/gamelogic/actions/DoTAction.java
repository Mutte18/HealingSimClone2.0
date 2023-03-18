package com.healing.gamelogic.actions;

import com.healing.buff.Buff;
import com.healing.entity.Entity;
import java.util.List;

public class DoTAction extends Action {
  private final Entity target;
  private final Integer damageAmount;
  private final Buff buff;

  public DoTAction(Entity target, Integer damageAmount, Buff buff) {
    super(target, List.of(target), "0");
    this.target = target;
    this.damageAmount = damageAmount;
    this.buff = buff;
  }

  @Override
  public void performAction() {
    target.reduceHealth(damageAmount);
    if (shouldLog) {
      System.out.println(
          buff.getClass().getName() + " ticked for " + damageAmount + " on " + target);
    }
  }
}
