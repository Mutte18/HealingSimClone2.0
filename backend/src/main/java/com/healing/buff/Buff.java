package com.healing.buff;

import com.healing.entity.Entity;
import com.healing.gamelogic.ActionsQueue;
import lombok.Getter;

@Getter
public abstract class Buff {
  protected int timeElapsed;
  private final int duration;
  private final double tickInterval;
  private boolean isExpired = false;

  public Buff(int duration, double tickInterval) {
    this.duration = duration;
    this.tickInterval = tickInterval;
  }

  public void incrementTimeElapsed(int timeIncrease) {
    if (!isExpired) {
      timeElapsed += timeIncrease;
      if (timeElapsed >= duration) {
        isExpired = true;
      }
    }
  }

  public abstract void addAction(Entity entity, ActionsQueue actionsQueue);

  public abstract int getHealAmount();
}
