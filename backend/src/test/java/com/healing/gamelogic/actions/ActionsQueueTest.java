package com.healing.gamelogic.actions;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.healing.entity.Dps;
import com.healing.entity.Player;
import com.healing.gamelogic.ActionsQueue;
import com.healing.spell.spellbook.FlashHeal;
import org.junit.jupiter.api.Test;

public class ActionsQueueTest {
  @Test
  void shouldRemoveTopActionFromQueueWhenProcessed() {
    var actionsQueue = new ActionsQueue();

    var action1 =
        new PlayerAction(new Player(0, 0, true, 0), new Dps(1, 1, true), new FlashHeal(), "1");
    var action2 =
        new PlayerAction(new Player(0, 0, true, 0), new Dps(1, 1, true), new FlashHeal(), "2");

    actionsQueue.addActionToQueue(action1);
    actionsQueue.addActionToQueue(action2);

    var actual1 = actionsQueue.getTopActionAndRemoveFromQueue();
    assertEquals(action1.getId(), actual1.get().getId());

    var actual2 = actionsQueue.getTopActionAndRemoveFromQueue();
    assertEquals(action2.getId(), actual2.get().getId());
  }
}
