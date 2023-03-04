package com.healing.spell.spellbook;

import com.healing.entity.Entity;
import com.healing.entity.Player;
import com.healing.gamelogic.ActionsQueue;
import com.healing.gamelogic.actions.PlayerAction;
import java.util.List;

public class FlashHeal extends Spell {
  public FlashHeal() {
    super(
        SpellList.FLASH_HEAL.getName(),
        SpellList.FLASH_HEAL.getId(),
        SpellList.FLASH_HEAL.getManaCost(),
        SpellList.FLASH_HEAL.getAdditionalTargets(),
        SpellList.FLASH_HEAL.getHealAmount(),
        SpellList.FLASH_HEAL.getDamageAmount(),
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
