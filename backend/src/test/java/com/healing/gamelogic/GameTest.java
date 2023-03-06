package com.healing.gamelogic;

import com.healing.spell.spellbook.SpellBook;
import com.healing.spell.spellcast.GlobalCooldownHandler;
import com.healing.spell.spellcast.SpellCastingHandler;
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
  SpellCastingHandler spellCastingHandler;

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
    spellCastingHandler = new SpellCastingHandler(actionsQueue, raiderHandler, new GlobalCooldownHandler());
    game =
        new Game(
            actionsQueue, raiderHandler, bossHandler, stateService, gameLoopHelper, buffHandler, spellCastingHandler);
    game.toggleIsRunning(false);
  }
}
