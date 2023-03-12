package com.healing.spell.spellcast;

import lombok.Getter;
import org.springframework.stereotype.Component;

@Component
@Getter
public class GlobalCooldownHandler {
  private boolean onCooldown = false;
  private final Double globalCooldownTimer = 1.0;
  private Double remainingTime;

  public void startGlobalCooldown() {
    this.onCooldown = true;
    this.remainingTime = globalCooldownTimer;
    System.err.println("Started Global cooldown with " + remainingTime + " seconds to go");
  }

  public void endGlobalCooldown() {
    this.onCooldown = false;
    System.err.println("Global cooldown finished");
  }

  public void tick(double tenthOfSeconds) {
    if (onCooldown) {
      remainingTime -= tenthOfSeconds;
      System.err.println(
          "Global cooldown with " + String.format("%.1f", remainingTime) + " seconds to go");

      if (remainingTime <= 0) {
        endGlobalCooldown();
      }
    }
  }
}
