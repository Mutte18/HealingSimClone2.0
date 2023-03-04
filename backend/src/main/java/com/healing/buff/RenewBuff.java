package com.healing.buff;

import com.healing.config.TimeKeeping;
import com.healing.entity.Entity;
import com.healing.gamelogic.ActionsQueue;
import com.healing.gamelogic.actions.HoTAction;

public class RenewBuff extends Buff {
  private static final int duration = 10 * TimeKeeping.TENTH_OF_SECOND;
  private static final double interval = 1.5 * TimeKeeping.TENTH_OF_SECOND;
  private static final int healAmount = 45;

  public RenewBuff() {
    super(duration, interval, "Renew");
  }

  @Override
  public void addAction(Entity entity, ActionsQueue actionsQueue) {
    if (!isExpired()) {
      if (timeElapsed % interval == 0) {
        actionsQueue.addActionToQueue(new HoTAction(entity, healAmount, this));
      }
    }
  }

  public int getHealAmount() {
    return healAmount;
  }
}
