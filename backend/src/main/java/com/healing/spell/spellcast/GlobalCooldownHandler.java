package com.healing.spell.spellcast;

import lombok.Getter;
import org.springframework.stereotype.Component;

@Component
@Getter
public class GlobalCooldownHandler {
  private boolean onCooldown = false;

  public void toggleGlobalCooldown(boolean value) {
    this.onCooldown = value;
  }
}
