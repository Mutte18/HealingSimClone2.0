package com.healing.gamelogic;

import com.healing.spell.spellbook.SpellBook;
import com.healing.spell.spellcast.GlobalCooldownHandler;
import com.healing.state.StateService;
import org.junit.jupiter.api.BeforeEach;

public class GameTest {
  RaiderHandler raiderHandler;
  BossHandler bossHandler;
  ActionsQueue actionsQueue;
  StateService stateService;
  GameLoopHelper gameLoopHelper;
  Game game;
  BuffHandler buffHandler;

  @BeforeEach
  void setup() {
    raiderHandler = new RaiderHandler();
    bossHandler = new BossHandler(raiderHandler);
    actionsQueue = new ActionsQueue();
    stateService = new StateService(bossHandler, raiderHandler);
    gameLoopHelper =
        new GameLoopHelper(
            actionsQueue, bossHandler, raiderHandler, new SpellBook(), new GlobalCooldownHandler());
    buffHandler = new BuffHandler(raiderHandler, actionsQueue);
    game =
        new Game(
            actionsQueue, raiderHandler, bossHandler, stateService, gameLoopHelper, buffHandler);
    game.toggleIsRunning(false);
  }
}
