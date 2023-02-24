package com.healing.gamelogic.actions;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.healing.entity.Boss;
import com.healing.entity.Dps;
import com.healing.entity.Player;
import com.healing.entity.attacks.MeleeSwing;
import com.healing.gamelogic.ActionsQueue;
import com.healing.spell.spellbook.FlashHeal;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ActionsQueueTest {

  private ActionsQueue actionsQueue;

  @BeforeEach
  void setup() {
    actionsQueue = new ActionsQueue();
  }

  @Test
  void shouldRemoveTopActionFromQueueWhenProcessed() {
    var action1 =
        new PlayerAction(
            new Player(0, 0, true, 0),
            new ArrayList<>(List.of(new Dps(1, 1, true))),
            new FlashHeal(),
            "1");
    var action2 =
        new PlayerAction(
            new Player(0, 0, true, 0),
            new ArrayList<>(List.of(new Dps(1, 1, true))),
            new FlashHeal(),
            "2");

    actionsQueue.addActionToQueue(action1);
    actionsQueue.addActionToQueue(action2);

    var actual1 = actionsQueue.getTopActionAndRemoveFromQueue();
    assertEquals(action1.getId(), actual1.get().getId());

    var actual2 = actionsQueue.getTopActionAndRemoveFromQueue();
    assertEquals(action2.getId(), actual2.get().getId());
  }

  @Test
  public void processActionQueueShouldHandleAllQueuedActions() {
    actionsQueue.addActionToQueue(getAction());
    actionsQueue.addActionToQueue(getAction());
    actionsQueue.addActionToQueue(getAction());

    actionsQueue.processActionQueue();

    assertEquals(0, actionsQueue.size());
  }

  @Test
  public void processingPlayerActionShouldHealInjuredAllies() {
    var action = getPlayerHealAction();
    for (var target : action.getTargets()) {
      target.reduceHealth(50);
    }
    actionsQueue.addActionToQueue(action);
    actionsQueue.processActionQueue();
    for (var target : action.getTargets()) {
      assertEquals(target.getMaxHealth(), target.getHealth());
    }
  }

  private PlayerAction getPlayerHealAction() {
    var player = Player.builder().alive(true).health(100).build();
    var target = Dps.builder().id(0).health(100).alive(true).build();
    var secondTarget = Dps.builder().id(1).health(100).alive(true).build();
    return new PlayerAction(
        player, new ArrayList<>(List.of(target, secondTarget)), new FlashHeal(), "1");
  }

  private Action getAction() {
    var dps = Dps.builder().id(0).health(100).alive(true).build();
    var boss = Boss.builder().id(0).health(100).name("greg").alive(true).build();
    return new NPCAction(dps, new ArrayList<>(List.of(boss)), new MeleeSwing(100), "1");
  }
}
