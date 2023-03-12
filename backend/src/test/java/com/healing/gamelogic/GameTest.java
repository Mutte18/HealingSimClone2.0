package com.healing.gamelogic;

import static org.junit.jupiter.api.Assertions.*;

import com.healing.spell.spellbook.SpellBook;
import com.healing.spell.spellcast.GlobalCooldownHandler;
import com.healing.spell.spellcast.SpellCastingHandler;
import com.healing.state.StateService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
    gameLoopHelper = new GameLoopHelper(actionsQueue, bossHandler, raiderHandler, new SpellBook());
    buffHandler = new BuffHandler(raiderHandler, bossHandler, actionsQueue);
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

  @Test
  void gameShouldEndWhenAllRaidersAreDead() {
    raiderHandler.killAllRaiders();
    game.validateBossAndRaidersAliveStatus();
    assertFalse(game.isRunning());
  }

  @Test
  void gameShouldEndWhenBossIsDead() {
    bossHandler.getCurrentBoss().killEntity();
    game.validateBossAndRaidersAliveStatus();

    assertFalse(game.isRunning());
  }
}
