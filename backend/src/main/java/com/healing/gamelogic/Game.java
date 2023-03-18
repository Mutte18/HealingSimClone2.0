package com.healing.gamelogic;

import com.healing.gui.MainWindow;
import com.healing.spell.spellbook.SpellBook;
import com.healing.spell.spellcast.GlobalCooldownHandler;
import com.healing.spell.spellcast.SpellCastingHandler;
import com.healing.state.StateService;
import java.awt.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Game implements Runnable {
  private boolean gameRunning = false;
  private final RaiderHandler raiderHandler;
  private final BossHandler bossHandler;
  private final ActionsQueue actionsQueue;
  private final StateService stateService;
  private final GameLoopHelper gameLoopHelper;
  private final BuffHandler buffHandler;
  private final SpellCastingHandler spellCastingHandler;
  private final GlobalCooldownHandler globalCooldownHandler;
  private final SpellBook spellBook;
  private MainWindow mainWindow;

  /** Time keeping variables */
  private long lasttime = System.nanoTime();

  private double delta = 0;
  private int frames = 0;
  private double time = System.currentTimeMillis();
  private int tenthOfSecond = 0;

  @Autowired
  public Game(
      ActionsQueue actionsQueue,
      RaiderHandler raiderHandler,
      BossHandler bossHandler,
      StateService stateService,
      GameLoopHelper gameLoopHelper,
      BuffHandler buffHandler,
      SpellCastingHandler spellCastingHandler,
      GlobalCooldownHandler globalCooldownHandler,
      SpellBook spellBook) {
    this.raiderHandler = raiderHandler;
    this.actionsQueue = actionsQueue;
    this.bossHandler = bossHandler;
    this.stateService = stateService;
    this.gameLoopHelper = gameLoopHelper;
    this.buffHandler = buffHandler;
    this.spellCastingHandler = spellCastingHandler;
    this.globalCooldownHandler = globalCooldownHandler;
    this.spellBook = spellBook;

    if (!GraphicsEnvironment.isHeadless()) {
      mainWindow = new MainWindow(stateService);
    }
    resetGame();
  }

  @Override
  public void run() {
    gameLoop();
  }

  public void resetGame() {
    toggleIsRunning(false);
    raiderHandler.resetRaidGroup();
    actionsQueue.resetActionsQueue();
    spellCastingHandler.resetSpellCastState();
    gameLoopHelper.resetSecondsElapsed();
    this.bossHandler.resetBosses();
    resetTimeKeepingValues();
    toggleIsRunning(true);
    new Thread(this).start();
  }

  private void resetTimeKeepingValues() {
    lasttime = System.nanoTime();

    delta = 0;
    frames = 0;
    time = System.currentTimeMillis();
    tenthOfSecond = 0;
  }

  private void gameLoop() {
    raiderHandler
        .getRaidGroup()
        .forEach(
            raider -> {
              raider.setHealth(1000);
              raider.setMaxHealth(1000);
            });

    while (this.gameRunning) {
      processTime();
      actionsQueue.processActionQueue();
    }
  }

  private void processTime() {
    // https://www.reddit.com/r/learnprogramming/comments/o2aet5/i_cant_understand_the_notch_game_loop_java/
    long now = System.nanoTime();
    double amountOfTicks = 60;
    double ns = 1000000000 / amountOfTicks;

    delta += (now - lasttime) / ns;
    lasttime = now;
    if (delta >= 1) {
      frames++;
      delta--;

      var elapsedTime = System.currentTimeMillis() - time;
      if (elapsedTime >= 100) {
        time += 100;
        tenthOfSecond++;

        buffHandler.processBuffs(0.1);
        spellCastingHandler.tick(0.1);
        globalCooldownHandler.tick(0.1);
        raiderHandler.getPlayer().tick(0.1);
        spellBook.forEach(spell -> spell.tick(0.1));
        // raiderHandler.getRaidGroup().forEach(raider -> raider.tick(0.1));
        validateBossAndRaidersAliveStatus();
        buffHandler.cleanUpExpiredBuffs();
        mainWindow.showUI();
      }
      if (tenthOfSecond % 10 == 0 && tenthOfSecond > 0) {
        frames = 0;
        tenthOfSecond = 0;
        gameLoopHelper.tick(1);
      }
    }
  }

  void toggleIsRunning(boolean isRunning) {
    this.gameRunning = isRunning;
  }

  public boolean isRunning() {
    return this.gameRunning;
  }

  void validateBossAndRaidersAliveStatus() {
    var bossIsAlive = bossHandler.getCurrentBoss().isAlive();
    var raidersAlive = raiderHandler.getAliveRaiders().size();

    if (!bossIsAlive) {
      toggleIsRunning(false);
      System.err.println(
          bossHandler.getCurrentBoss().getName() + " has been slain! Well done. You Win!");
    } else if (raidersAlive <= 0) {
      toggleIsRunning(false);
      System.err.println(
          "You and your fellow raiders have died. You failed to keep them alive. Good job.");
    }
  }
}
