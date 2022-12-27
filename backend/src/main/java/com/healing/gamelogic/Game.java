package com.healing.gamelogic;

import com.healing.entity.Boss;
import com.healing.entity.Dps;
import com.healing.entity.EntityRole;
import com.healing.state.StateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Game implements Runnable {
  private static final long DELAY_PERIOD = 17;
  private final boolean gameRunning = false;
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
    restartGame();

    this.bossHandler.createNewBoss(new Boss(0, 1000, true, "Defias Pillager"));
    new Thread(this).start();
    new Thread(this::bossAutoAttackActionLoop).start();
    new Thread(this::bossSpecialAttackActionLoop).start();
    new Thread(this::dpsActionLoop).start();
  }

  private void restartGame() {
    raiderHandler.resetRaidGroup();
  }

  private void bossAutoAttackActionLoop() {
    while (this.gameRunning) {
      try {
        actionsQueue.addActionToQueue(bossHandler.createBossAutoAttackAction());
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

  private void bossSpecialAttackActionLoop() {
    while (this.gameRunning) {
      try {
        actionsQueue.addActionToQueue(bossHandler.createBossSpecialAttackAction());
        Thread.sleep(10000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

  private void dpsActionLoop() {
    while (this.gameRunning) {
      try {
        raiderHandler
            .getRaidersOfType(EntityRole.DPS)
            .forEach(
                raider -> {
                  if (raider.isAlive()) {
                    actionsQueue.addActionToQueue(
                        raiderHandler.createDPSAutoAttackAction(
                            (Dps) raider, bossHandler.getCurrentBoss()));
                  }
                });
        Thread.sleep(2000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

  private void healerAutoHealLoop() {
    while (this.gameRunning) {
      try {
        raiderHandler
            .getRaidersOfType(EntityRole.HEALER)
            .forEach(
                raider -> {
                  if (raider.isAlive()) {
                    actionsQueue.addActionToQueue(
                        raiderHandler.createDPSAutoAttackAction(
                            (Dps) raider, bossHandler.getCurrentBoss()));
                  }
                });
        Thread.sleep(2000);
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

  void processActionQueue() {
    while (actionsQueue.size() > 0) {
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
  }
}
