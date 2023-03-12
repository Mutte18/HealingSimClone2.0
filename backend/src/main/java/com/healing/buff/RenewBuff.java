package com.healing.buff;

import com.healing.entity.Entity;
import com.healing.gamelogic.ActionsQueue;
import com.healing.gamelogic.actions.HoTAction;

public class RenewBuff extends Buff {
  private static final double duration = 9.0;
  private static final double interval = 1.5;
  private static final int healAmount = 45;

  public RenewBuff() {
    super(duration, interval, "Renew");
  }

  @Override
  public void addAction(Entity entity, ActionsQueue actionsQueue) {
    if (!isExpired()) {
      if (remainingDuration % interval == 0.0) {
        actionsQueue.addActionToQueue(new HoTAction(entity, healAmount, this));
      }
    }
  }
}
