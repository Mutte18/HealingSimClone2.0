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
  private BuffType buffType;

  public Buff(double maxDuration, double tickInterval, String name, BuffType buffType) {
    this.maxDuration = maxDuration;
    this.remainingDuration = maxDuration;
    this.tickInterval = tickInterval;
    this.name = name;
    this.buffType = buffType;
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
}
