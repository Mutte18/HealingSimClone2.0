package com.healing.spell.spellbook;

import com.healing.entity.Entity;
import com.healing.entity.Player;
import com.healing.gamelogic.ActionsQueue;
import com.healing.gamelogic.actions.PlayerAction;
import java.util.List;

public class HolyShock extends Spell {
  public HolyShock() {
    super("Holy Shock", "4", 100, 0, 150, 0, 6.0, 0.0);
  }

  @Override
  public void createAction(
      ActionsQueue actionsQueue, Entity target, List<Entity> additionalTargets, Player player) {
    actionsQueue.addActionToQueue(new PlayerAction(player, additionalTargets, this, "1"));
  }

  @Override
  public void addBuff(Entity target) {}
}
