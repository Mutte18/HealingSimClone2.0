package com.healing.entity.attacks;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class NPCAttack {
  protected String name;
  protected Integer damageAmount;
  protected Integer maxTargets;

  public NPCAttack(String name, Integer damageAmount, Integer maxTargets) {
    this.name = name;
    this.damageAmount = damageAmount;
    this.maxTargets = maxTargets;
  }
}
