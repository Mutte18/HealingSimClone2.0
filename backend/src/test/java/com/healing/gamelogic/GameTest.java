package com.healing.gamelogic;

import static org.junit.Assert.assertEquals;

import com.healing.entity.Boss;
import com.healing.entity.Dps;
import com.healing.gamelogic.actions.Action;
import com.healing.gamelogic.actions.NPCAction;
import com.healing.spell.spellbook.FlashHeal;
import org.junit.Test;

public class GameTest {
  RaiderHandler raiderHandler = new RaiderHandler();
  ActionsQueue actionsQueue = new ActionsQueue();

  @Test
  public void shouldProcessMultipleActions() throws InterruptedException {

    actionsQueue.addActionToQueue(getAction());
    actionsQueue.addActionToQueue(getAction());
    actionsQueue.addActionToQueue(getAction());

    Game game = new Game(actionsQueue, raiderHandler);
    Thread.sleep(500);

    assertEquals(0, actionsQueue.size());
  }

  private Action getAction() {
    var dps = new Dps(0, 100, true);
    var boss = new Boss(1, 1000, true, "Defias Pillager");
    return new NPCAction(dps, boss, new FlashHeal(), "1");
  }
}
