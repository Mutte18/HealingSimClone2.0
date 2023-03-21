package com.healing.buff.debuffs;

import static com.healing.RoundingHelper.roundToOneDecimal;

import com.healing.buff.Buff;
import com.healing.buff.BuffType;
import com.healing.entity.Entity;
import com.healing.gamelogic.ActionsQueue;
import com.healing.gamelogic.actions.DoTAction;

public class BurningDebuff extends Buff {
  private static final double duration = 12.0;
  private static final double interval = 2.0;
  private static final int damageAmount = 50;

  public BurningDebuff() {
    super(duration, interval, "Burning", BuffType.DEBUFF, true);
  }

  @Override
  public void addAction(Entity entity, ActionsQueue actionsQueue) {
    if (!isExpired()) {
      if (roundToOneDecimal(remainingDuration) % interval == 0) {
        actionsQueue.addActionToQueue(new DoTAction(entity, damageAmount, this));
      }
    }
  }

  @Override
  public int getDamageAmount() {
    return damageAmount;
  }

  @Override
  public int getHealingAmount() {
    return 0;
  }
}
