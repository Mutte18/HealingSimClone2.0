package com.healing.gamelogic;

import com.healing.entity.Boss;
import com.healing.state.StateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Game implements Runnable {
  private static final long DELAY_PERIOD = 17;
  private final boolean gameRunning = true;
  private final RaiderHandler raiderHandler;
  private final BossHandler bossHandler;
  private final ActionsQueue actionsQueue;
  private final StateService stateService;

  @Autowired
  public Game(
      ActionsQueue actionsQueue,
      RaiderHandler raiderHandler,
      BossHandler bossHandler,
      StateService stateService) {
    this.raiderHandler = raiderHandler;
    this.actionsQueue = actionsQueue;
    this.bossHandler = bossHandler;
    this.stateService = stateService;
    System.out.println(actionsQueue + " Game");
    restartGame();

    this.bossHandler.createNewBoss(new Boss(0, 1000, true, "Defias Pillager"));
    new Thread(this).start();
    new Thread(this::bossActionLoop).start();
    // new MainWindow(this);
  }

  private void restartGame() {
    raiderHandler.resetRaidGroup();
  }

  private void bossActionLoop() {
    while (this.gameRunning) {
      try {
        setBossFocusTarget();
        actionsQueue.addActionToQueue(bossHandler.createBossAutoAttackAction());
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

  private void gameLoop() {
    while (this.gameRunning) {
      long beginTime = System.currentTimeMillis();

      long timeTaken = System.currentTimeMillis() - beginTime;
      long sleepTime = DELAY_PERIOD - timeTaken;
      processActionQueue();
      if (sleepTime >= 0) {
        try {
          Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    }
  }

  @Override
  public void run() {
    gameLoop();
  }

  private void processActionQueue() {
    var optionalAction = actionsQueue.getTopActionAndRemoveFromQueue();
    // Do stuff with action
    optionalAction.ifPresent(
        action -> {
          action.performAction();
          stateService.printState();
        });
    // Every X timeunit, go through action queue and perform the actions
    // Then send state update to frontend
  }

  private void setBossFocusTarget() {
    var currentTarget = bossHandler.getCurrentBoss().getCurrentTarget();
    if (currentTarget == null || !currentTarget.isAlive()) {
      var newTarget = raiderHandler.getNewTarget();
      newTarget.ifPresentOrElse(bossHandler::setNewTarget, () -> System.out.println("GAME OVER"));
    }
  }
}
