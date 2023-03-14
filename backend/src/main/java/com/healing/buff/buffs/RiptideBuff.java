package com.healing.buff.buffs;

import static com.healing.RoundingHelper.roundToOneDecimal;

import com.healing.buff.Buff;
import com.healing.buff.BuffType;
import com.healing.entity.Entity;
import com.healing.gamelogic.ActionsQueue;
import com.healing.gamelogic.actions.HoTAction;

public class RiptideBuff extends Buff {
  private static final double duration = 9;
  private static final double interval = 1.5;
  private static final int healAmount = 45;

  public RiptideBuff() {
    super(duration, interval, "Riptide", BuffType.BUFF);
  }

  @Override
  public void addAction(Entity entity, ActionsQueue actionsQueue) {
    if (!isExpired()) {
      if (roundToOneDecimal(remainingDuration) % interval == 0) {
        actionsQueue.addActionToQueue(new HoTAction(entity, healAmount, this));
      }
    }
  }

  @Override
  public int getDamageAmount() {
    return 0;
  }

  @Override
  public int getHealingAmount() {
    return healAmount;
  }
}
