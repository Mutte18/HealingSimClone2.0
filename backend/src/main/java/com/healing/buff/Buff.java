package com.healing.buff;

import com.healing.entity.Entity;
import com.healing.gamelogic.ActionsQueue;
import lombok.Getter;

@Getter
public abstract class Buff {
  private final String name;
  protected double remainingDuration;
  private final double maxDuration;
  private final double tickInterval;
  private boolean isExpired = false;

  public Buff(double maxDuration, double tickInterval, String name) {
    this.maxDuration = maxDuration;
    this.remainingDuration = maxDuration;
    this.tickInterval = tickInterval;
    this.name = name;
  }

  public void tick(double tenthOfSecond) {
    if (!isExpired) {
      remainingDuration -= tenthOfSecond;
      if (remainingDuration <= 0.0) {
        isExpired = true;
      }
    }
  }

  public abstract void addAction(Entity entity, ActionsQueue actionsQueue);

  public abstract int getHealAmount();
}
