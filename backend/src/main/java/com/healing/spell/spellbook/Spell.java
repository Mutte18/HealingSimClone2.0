package com.healing.spell.spellbook;

import com.healing.buff.Buff;
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
  private Buff buff;

  public Spell(
      String name,
      String spellId,
      Integer manaCost,
      Integer additionalTargets,
      Integer healAmount,
      Integer damageAmount,
      Buff buff) {
    this.name = name;
    this.spellId = spellId;
    this.manaCost = manaCost;
    this.additionalTargets = additionalTargets;
    this.healAmount = healAmount;
    this.damageAmount = damageAmount;
    this.buff = buff;
    // new UUID(1,1);
  }

  public abstract void createAction(
      ActionsQueue actionsQueue, Entity target, List<Entity> additionalTargets, Player player);

  public abstract void addBuff(Entity target);

  public void setBuff(Buff buff) {
    this.buff = buff;
  }
}
