package com.healing.spell.spellbook;

import com.healing.buff.buffs.RiptideBuff;
import com.healing.entity.Entity;
import com.healing.entity.Player;
import com.healing.gamelogic.ActionsQueue;
import com.healing.gamelogic.actions.PlayerAction;
import java.util.List;

public class Riptide extends Spell {
  public Riptide() {
    super("Riptide", "3", 150, 0, 75, 0, 4.0, 0.0);
  }

  @Override
  public void createAction(
      ActionsQueue actionsQueue, Entity target, List<Entity> additionalTargets, Player player) {
    actionsQueue.addActionToQueue(new PlayerAction(player, additionalTargets, this, "1"));
  }

  @Override
  public void addBuff(Entity target) {
    target.addBuff(new RiptideBuff());
  }
}
