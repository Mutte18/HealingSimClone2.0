package com.healing.gamelogic.actions;

import com.healing.buff.Buff;
import com.healing.entity.Entity;

import java.util.List;

public class DoTAction extends Action {
  private final Entity entity;
  private final Integer damageAmount;
  private final Buff buff;

  public DoTAction(Entity entity, Integer damageAmount, Buff buff) {
    super(entity, List.of(entity), "0");
    this.entity = entity;
    this.damageAmount = damageAmount;
    this.buff = buff;
  }

  @Override
  public void performAction() {
    entity.reduceHealth(damageAmount);
    /*System.out.println(
    buff.getClass().getName() + " ticked for " + buff.getHealAmount() + " on " + entity);*/
  }
}
