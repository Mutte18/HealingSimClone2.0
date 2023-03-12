package com.healing.buff;

import com.healing.entity.Entity;
import com.healing.gamelogic.ActionsQueue;
import com.healing.gamelogic.actions.HoTAction;

public class RiptideBuff extends Buff {
  private static final double duration = 10.0;
  private static final double interval = 1.5;
  private static final int healAmount = 45;

  public RiptideBuff() {
    super(duration, interval, "Riptide");
  }

  @Override
  public void addAction(Entity entity, ActionsQueue actionsQueue) {
    if (!isExpired()) {
      if (remainingDuration % interval == 0) {
        actionsQueue.addActionToQueue(new HoTAction(entity, healAmount, this));
      }
    }
  }

  public int getHealAmount() {
    return healAmount;
  }
}
