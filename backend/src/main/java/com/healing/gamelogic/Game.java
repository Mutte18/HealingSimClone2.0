package com.healing.gamelogic;

import com.healing.entity.Boss;
import com.healing.entity.Dps;
import com.healing.entity.EntityRole;
import com.healing.entity.Healer;
import com.healing.state.StateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;

@Component
public class Game implements Runnable {
  private static final long DELAY_PERIOD = 17;
  private final boolean gameRunning = true;
  private final RaiderHandler raiderHandler;
  private final BossHandler bossHandler;
  private final ActionsQueue actionsQueue;
  private final StateService stateService;
  private int ticks = 0;
  private int secondsElapsed;

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
    // new Thread(this::bossSpecialAttackActionLoop).start();
    new Thread(this::dpsActionLoop).start();
    new Thread(this::healerAutoHealLoop).start();
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
                healer -> {
                  if (healer.isAlive()) {
                    actionsQueue.addActionToQueue(
                        raiderHandler.createHealerAutoHealAction((Healer) healer));
                  }
                });
        Thread.sleep(5000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

  private void gameLoop() {
    Instant startTime = Instant.now();


    long lastime = System.nanoTime();
    double AmountOfTicks = 60;
    double ns = 1000000000 / AmountOfTicks;
    double delta = 0;
    int frames = 0;
    double time = System.currentTimeMillis();

    while (this.gameRunning) {

      /*long beginTime = System.currentTimeMillis();

      long timeTaken = System.currentTimeMillis() - beginTime;
      long sleepTime = DELAY_PERIOD - timeTaken;
      ticks++;
      if (ticks >= 58) {
        var timeTo60 = Duration.between(startTime, Instant.now());
        System.out.println("TIIIIIME " + timeTo60);
        ticks = 0;
        secondsElapsed++;
        System.out.println("Seconds elapsed: " + secondsElapsed);
        startTime = Instant.now();
        //System.out.println(ticks);
      //processActionQueue();
      }

      if (sleepTime >= 0) {
        try {
          Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }*/

      long now = System.nanoTime();
      delta += (now - lastime) / ns;
      lastime = now;
      if(delta >= 1) {
        frames++;
        delta--;
        if (System.currentTimeMillis() - time >= 1000) {
          System.out.println("fps:" + frames);
          time += 1000;
          secondsElapsed++;
          System.out.println("Seconds Elapsed " + secondsElapsed);
          frames = 0;
        }
      }

      // https://www.reddit.com/r/learnprogramming/comments/o2aet5/i_cant_understand_the_notch_game_loop_java/



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
            //stateService.printState();
          });
      // Every X timeunit, go through action queue and perform the actions
      // Then send state update to frontend
    }
  }
}
