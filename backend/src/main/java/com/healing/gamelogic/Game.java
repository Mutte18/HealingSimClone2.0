package com.healing.gamelogic;

import com.healing.gamelogic.actions.Action;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Game implements Runnable {
  private static final long DELAY_PERIOD = 17;
  private final boolean gameRunning = true;
  private final RaiderHandler raiderHandler;
  private final ActionsQueue actionsQueue;

  @Autowired
  public Game(ActionsQueue actionsQueue, RaiderHandler raiderHandler) {
    this.raiderHandler = raiderHandler;
    this.actionsQueue = actionsQueue;
    System.out.println(actionsQueue + " Game");
    restartGame();

    new Thread(this).start();
    // new MainWindow(this);
  }

  private void restartGame() {
    raiderHandler.resetRaidGroup();
  }

  @SneakyThrows
  private void gameLoop() {
    while (this.gameRunning) {
      long beginTime = System.currentTimeMillis();

      long timeTaken = System.currentTimeMillis() - beginTime;
      long sleepTime = DELAY_PERIOD - timeTaken;
      processActionQueue();
      if (sleepTime >= 0) {
        Thread.sleep(sleepTime);
      }
    }
  }

  @Override
  public void run() {
    gameLoop();
  }

  void processActionQueue() {
    var optionalAction = actionsQueue.getTopActionAndRemoveFromQueue();
    // Do stuff with action
    optionalAction.ifPresent(Action::performAction);
    // Every X timeunit, go through action queue and perform the actions
    // Then send state update to frontend
  }
}
