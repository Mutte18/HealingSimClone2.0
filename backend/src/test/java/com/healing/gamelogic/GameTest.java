package com.healing.gamelogic;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.healing.entity.Dps;
import com.healing.entity.Player;
import com.healing.entity.attacks.MeleeSwing;
import com.healing.gamelogic.actions.Action;
import com.healing.gamelogic.actions.NPCAction;
import com.healing.gamelogic.actions.PlayerAction;
import com.healing.spell.spellbook.FlashHeal;
import com.healing.state.StateService;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GameTest {
  RaiderHandler raiderHandler;
  BossHandler bossHandler;
  ActionsQueue actionsQueue;
  StateService stateService;
  Game game;

  @BeforeEach
  void setup() {
    raiderHandler = new RaiderHandler();
    bossHandler = new BossHandler();
    actionsQueue = new ActionsQueue();
    stateService = new StateService(bossHandler, raiderHandler);
    game = new Game(actionsQueue, raiderHandler, bossHandler, stateService);
  }

  @Test
  public void shouldProcessMultipleActions() throws InterruptedException {
    actionsQueue.addActionToQueue(getAction());
    actionsQueue.addActionToQueue(getAction());
    actionsQueue.addActionToQueue(getAction());

    Thread.sleep(100);

    assertEquals(0, actionsQueue.size());
  }

  @Test
  public void processingPlayerActionShouldHealInjuredAllies() throws InterruptedException {
    var action = getPlayerHealAction();
    for (var target : action.getTargets()) {
      target.reduceHealth(50);
    }
    actionsQueue.addActionToQueue(action);

    Thread.sleep(100);
    for (var target : action.getTargets()) {
      assertEquals(target.getMaxHealth(), target.getHealth());
    }
  }

  private Action getAction() {
    // var dps = new Dps(0, 100, true);
    // var boss = new Boss(1, 1000, true, "Defias Pillager");
    var dps = raiderHandler.getDpsers().get(0);
    var boss = bossHandler.getCurrentBoss();
    return new NPCAction(
        (Dps) dps, new ArrayList<>(List.of(boss)), new MeleeSwing("Melee Swing", 100), "1");
  }

  private Action getPlayerHealAction() {
    var player = raiderHandler.getPlayer().get();
    var target = raiderHandler.getRaidGroup().get(1);
    var secondTarget = raiderHandler.getRaidGroup().get(2);
    return new PlayerAction(
        (Player) player, new ArrayList<>(List.of(target, secondTarget)), new FlashHeal(), "1");
  }
}
