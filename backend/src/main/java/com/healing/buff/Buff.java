package com.healing.buff;

import static com.healing.RoundingHelper.roundToOneDecimal;

import com.healing.entity.Entity;
import com.healing.gamelogic.ActionsQueue;
import lombok.Getter;

@Getter
public abstract class Buff implements Cloneable {
  private final String name;
  protected double remainingDuration;
  private final double maxDuration;
  private final double tickInterval;
  private final boolean allowMultiple;
  private boolean isExpired = false;
  private BuffType buffType;

  public Buff(
      double maxDuration,
      double tickInterval,
      String name,
      BuffType buffType,
      Boolean allowMultiple) {
    this.maxDuration = maxDuration;
    this.remainingDuration = maxDuration;
    this.tickInterval = tickInterval;
    this.name = name;
    this.buffType = buffType;
    this.allowMultiple = allowMultiple;
  }

  public void tick(double tenthOfSecond) {
    if (!isExpired) {
      remainingDuration = roundToOneDecimal(remainingDuration) - tenthOfSecond;
      if (remainingDuration <= 0) {
        isExpired = true;
      }
    }
  }

  public abstract void addAction(Entity entity, ActionsQueue actionsQueue);

  public abstract int getDamageAmount();

  public abstract int getHealingAmount();

  @Override
  public Buff clone() {
    try {
      return (Buff) super.clone();
    } catch (CloneNotSupportedException e) {
      throw new AssertionError();
    }
  }
}
