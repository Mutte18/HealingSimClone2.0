package com.healing.gamelogic;

import com.healing.buff.Renew;
import com.healing.entity.Boss;
import com.healing.gui.MainWindow;
import com.healing.state.StateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Game implements Runnable {
  private boolean gameRunning = true;
  private final RaiderHandler raiderHandler;
  private final BossHandler bossHandler;
  private final ActionsQueue actionsQueue;
  private final StateService stateService;
  private final GameLoopHelper gameLoopHelper;
  private final BuffHandler buffHandler;

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
      BuffHandler buffHandler) {
    this.raiderHandler = raiderHandler;
    this.actionsQueue = actionsQueue;
    this.bossHandler = bossHandler;
    this.stateService = stateService;
    this.gameLoopHelper = gameLoopHelper;
    this.buffHandler = buffHandler;

    new MainWindow(stateService);
    restartGame();
    new Thread(this).start();
  }

  @Override
  public void run() {
    gameLoop();
  }

  private void restartGame() {
    raiderHandler.resetRaidGroup();
    this.bossHandler.createNewBoss(new Boss(0, 1000, true, "Defias Pillager"));
  }

  private void gameLoop() {
    raiderHandler.getPlayer().getBuffs().add(new Renew());
    raiderHandler.getPlayer().getBuffs().add(new Renew());
    raiderHandler.getPlayer().setMaxHealth(1000);

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

        buffHandler.processBuffs(1);
        buffHandler.cleanUpExpiredBuffs();

      } else if (tenthOfSecond == 10) {
        frames = 0;
        tenthOfSecond = 0;
        gameLoopHelper.incrementSecondsElapsed(1);
        gameLoopHelper.processActionLoops();

      }
    }
  }

  void toggleIsRunning(boolean isRunning) {
    this.gameRunning = isRunning;
  }
}
