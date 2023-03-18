package com.healing.spell.spellbook;

import com.healing.buff.buffs.RenewBuff;
import com.healing.entity.Entity;
import com.healing.entity.Player;
import com.healing.gamelogic.ActionsQueue;
import java.util.List;

public class MassRenew extends Spell {
  public MassRenew() {
    super("Mass Renew", "6", 400, 4, 0, 0, 15.0, 2.0);
  }

  @Override
  public void createAction(
      ActionsQueue actionsQueue, Entity target, List<Entity> additionalTargets, Player player) {}

  @Override
  public void addBuff(Entity target) {
    target.addBuff(new RenewBuff());
  }
}
