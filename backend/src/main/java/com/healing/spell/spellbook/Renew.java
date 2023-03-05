package com.healing.spell.spellbook;

import com.healing.buff.RenewBuff;
import com.healing.entity.Entity;
import com.healing.entity.Player;
import com.healing.gamelogic.ActionsQueue;
import java.util.List;

public class Renew extends Spell {
  public Renew() {
    super("Renew", "1", 100, 0, 0, 0, 0);
  }

  @Override
  public void createAction(
      ActionsQueue actionsQueue, Entity target, List<Entity> additionalTargets, Player player) {}

  @Override
  public void addBuff(Entity target) {
    target.getBuffs().add(new RenewBuff());
  }
}
