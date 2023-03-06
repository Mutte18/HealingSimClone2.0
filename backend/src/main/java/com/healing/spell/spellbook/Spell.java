package com.healing.spell.spellbook;

import com.healing.entity.Entity;
import com.healing.entity.Player;
import com.healing.gamelogic.ActionsQueue;
import java.util.List;
import lombok.Getter;

@Getter
public abstract class Spell {
  private final String name;
  private final String spellId;
  private final Integer manaCost;
  private final Integer healAmount;
  private final Integer damageAmount;
  private final Integer additionalTargets;
  private final Integer coolDownTime;
  private final Double castTime;
  private Integer remainingCooldown = 0;
  private Boolean onCooldown = false;

  public Spell(
      String name,
      String spellId,
      Integer manaCost,
      Integer additionalTargets,
      Integer healAmount,
      Integer damageAmount,
      Integer coolDownTime,
      Double castTime) {
    this.name = name;
    this.spellId = spellId;
    this.manaCost = manaCost;
    this.additionalTargets = additionalTargets;
    this.healAmount = healAmount;
    this.damageAmount = damageAmount;
    this.coolDownTime = coolDownTime;
    this.castTime = castTime;
  }

  public abstract void createAction(
      ActionsQueue actionsQueue, Entity target, List<Entity> additionalTargets, Player player);

  public abstract void addBuff(Entity target);

  public void tick(Integer seconds) {
    this.remainingCooldown -= seconds;
    if (this.remainingCooldown <= 0) {
      this.onCooldown = false;
    }
  }

  public void startCooldown() {
    if (coolDownTime > 0) {
      this.onCooldown = true;
      this.remainingCooldown = this.coolDownTime;
    }
  }
}
