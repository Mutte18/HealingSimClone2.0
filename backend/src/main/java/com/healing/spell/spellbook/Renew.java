package com.healing.spell.spellbook;

import com.healing.buff.RenewBuff;
import com.healing.entity.Entity;
import com.healing.entity.Player;
import com.healing.gamelogic.ActionsQueue;
import java.util.List;

public class Renew extends Spell {
  public Renew() {
    super(
        SpellList.RENEW.getName(),
        SpellList.RENEW.getId(),
        SpellList.RENEW.getManaCost(),
        SpellList.RENEW.getAdditionalTargets(),
        SpellList.RENEW.getHealAmount(),
        SpellList.RENEW.getDamageAmount(),
        new RenewBuff());
  }

  @Override
  public void createAction(
      ActionsQueue actionsQueue, Entity target, List<Entity> additionalTargets, Player player) {}

  @Override
  public void addBuff(Entity target) {
    target.getBuffs().add(this.getBuff());
  }
}
