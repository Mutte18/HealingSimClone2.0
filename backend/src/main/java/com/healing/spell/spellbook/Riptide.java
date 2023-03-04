package com.healing.spell.spellbook;

import com.healing.buff.RiptideBuff;
import com.healing.entity.Entity;
import com.healing.entity.Player;
import com.healing.gamelogic.ActionsQueue;
import com.healing.gamelogic.actions.PlayerAction;
import java.util.List;

public class Riptide extends Spell {
  public Riptide() {
    super(
        SpellList.RIPTIDE.getName(),
        SpellList.RIPTIDE.getId(),
        SpellList.RIPTIDE.getManaCost(),
        SpellList.RIPTIDE.getAdditionalTargets(),
        SpellList.RIPTIDE.getHealAmount(),
        SpellList.RIPTIDE.getDamageAmount(),
        new RiptideBuff());
  }

  @Override
  public void createAction(
      ActionsQueue actionsQueue, Entity target, List<Entity> additionalTargets, Player player) {
    actionsQueue.addActionToQueue(new PlayerAction(player, additionalTargets, this, "1"));
  }

  @Override
  public void addBuff(Entity target) {
    target.getBuffs().add(this.getBuff());
  }
}
