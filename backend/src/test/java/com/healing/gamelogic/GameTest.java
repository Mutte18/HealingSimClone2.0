package com.healing.gamelogic;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.healing.entity.Dps;
import com.healing.entity.EntityRole;
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
  GameLoopHelper gameLoopHelper;
  Game game;

  @BeforeEach
  void setup() {
    raiderHandler = new RaiderHandler();
    bossHandler = new BossHandler(raiderHandler);
    actionsQueue = new ActionsQueue();
    stateService = new StateService(bossHandler, raiderHandler);
    gameLoopHelper = new GameLoopHelper(actionsQueue, bossHandler, raiderHandler);
    game = new Game(actionsQueue, raiderHandler, bossHandler, stateService, gameLoopHelper);
    game.toggleIsRunning(false);
  }

  @Test
  public void processActionQueueShouldHandleAllQueuedActions() {
    actionsQueue.addActionToQueue(getAction());
    actionsQueue.addActionToQueue(getAction());
    actionsQueue.addActionToQueue(getAction());

    game.processActionQueue();

    assertEquals(0, actionsQueue.size());
  }

  @Test
  public void processingPlayerActionShouldHealInjuredAllies() {
    var action = getPlayerHealAction();
    for (var target : action.getTargets()) {
      target.reduceHealth(50);
    }
    actionsQueue.addActionToQueue(action);
    game.processActionQueue();
    for (var target : action.getTargets()) {
      assertEquals(target.getMaxHealth(), target.getHealth());
    }
  }

  private Action getAction() {
    var dps = raiderHandler.getRaidersOfType(EntityRole.DPS).get(0);
    var boss = bossHandler.getCurrentBoss();
    return new NPCAction((Dps) dps, new ArrayList<>(List.of(boss)), new MeleeSwing(100), "1");
  }

  private PlayerAction getPlayerHealAction() {
    var player = raiderHandler.getPlayer();
    var target = raiderHandler.getRaidGroup().get(1);
    var secondTarget = raiderHandler.getRaidGroup().get(2);
    return new PlayerAction(
        player, new ArrayList<>(List.of(target, secondTarget)), new FlashHeal(), "1");
  }
}
