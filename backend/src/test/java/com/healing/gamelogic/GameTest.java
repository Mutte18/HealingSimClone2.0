package com.healing.gamelogic;

import com.healing.entity.Dps;
import com.healing.entity.attacks.MeleeSwing;
import com.healing.gamelogic.actions.Action;
import com.healing.gamelogic.actions.NPCAction;
import com.healing.state.StateService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

    Thread.sleep(500);
    stateService.printState();

    assertEquals(0, actionsQueue.size());
  }

  private Action getAction() {
    //var dps = new Dps(0, 100, true);
    //var boss = new Boss(1, 1000, true, "Defias Pillager");
    var dps = raiderHandler.getDpsers().get(0);
    var boss = bossHandler.getCurrentBoss();
    return new NPCAction((Dps) dps, new ArrayList<>(List.of(boss)), new MeleeSwing("Melee Swing", 100), "1");
  }
}
