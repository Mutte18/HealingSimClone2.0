package com.healing.entity.attacks;

import com.healing.buff.Buff;
import lombok.Getter;

@Getter
public abstract class NPCAttack {
  protected String name;
  protected Integer damageAmount;
  protected Integer maxTargets;
  protected Buff debuff;

  public NPCAttack(String name, Integer damageAmount, Integer maxTargets, Buff debuff) {
    this.name = name;
    this.damageAmount = damageAmount;
    this.maxTargets = maxTargets;
    this.debuff = debuff;
  }
}
