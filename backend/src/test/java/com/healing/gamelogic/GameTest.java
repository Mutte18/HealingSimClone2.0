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
  GlobalCooldownHandler globalCooldownHandler;

  @BeforeEach
  void setup() {
    raiderHandler = new RaiderHandler();
    bossHandler = new BossHandler(raiderHandler);
    actionsQueue = new ActionsQueue();
    stateService = new StateService(bossHandler, raiderHandler);
    globalCooldownHandler = new GlobalCooldownHandler();
    gameLoopHelper =
        new GameLoopHelper(
            actionsQueue, bossHandler, raiderHandler, new SpellBook());
    buffHandler = new BuffHandler(raiderHandler, actionsQueue);
    spellCastingHandler =
        new SpellCastingHandler(actionsQueue, raiderHandler, globalCooldownHandler);
    game =
        new Game(
            actionsQueue,
            raiderHandler,
            bossHandler,
            stateService,
            gameLoopHelper,
            buffHandler,
            spellCastingHandler,
                globalCooldownHandler);
    game.toggleIsRunning(false);
  }
}
