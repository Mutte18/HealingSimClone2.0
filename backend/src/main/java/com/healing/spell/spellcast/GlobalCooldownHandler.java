package com.healing.spell.spellcast;

import com.healing.config.TimeKeeping;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Component
@Getter
public class GlobalCooldownHandler {
  private boolean onCooldown = false;
  private final Double globalCooldownTimer = 1.0 * TimeKeeping.TENTH_OF_SECOND;
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
      System.err.println("Global cooldown with " + remainingTime + " tenths to go");

      if (remainingTime <= 0.0) {
        endGlobalCooldown();
      }
    }
  }
}
