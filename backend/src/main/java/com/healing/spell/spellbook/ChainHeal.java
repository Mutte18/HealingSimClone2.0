package com.healing.spell.spellbook;

import com.healing.entity.Entity;
import com.healing.entity.Player;
import com.healing.gamelogic.ActionsQueue;
import com.healing.gamelogic.actions.PlayerAction;
import java.util.List;

public class ChainHeal extends Spell {
  public ChainHeal() {
    super(
        SpellList.CHAIN_HEAL.getName(),
        SpellList.CHAIN_HEAL.getId(),
        SpellList.CHAIN_HEAL.getManaCost(),
        SpellList.CHAIN_HEAL.getAdditionalTargets(),
        SpellList.CHAIN_HEAL.getHealAmount(),
        SpellList.CHAIN_HEAL.getDamageAmount(),
        null);
  }

  @Override
  public void createAction(
      ActionsQueue actionsQueue, Entity target, List<Entity> additionalTargets, Player player) {
    actionsQueue.addActionToQueue(new PlayerAction(player, additionalTargets, this, "1"));
  }

  @Override
  public void addBuff(Entity target) {}
}
