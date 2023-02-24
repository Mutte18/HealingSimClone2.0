package com.healing.gamelogic;

import com.healing.entity.Dps;
import com.healing.entity.EntityRole;
import com.healing.entity.Healer;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class GameLoopHelper {
  private final RaiderHandler raiderHandler;
  private final BossHandler bossHandler;
  private final ActionsQueue actionsQueue;

  private int secondsElapsed = 0;
  private final List<Integer> dpsLoopExecutions = new ArrayList<>();
  private final List<Integer> healLoopExecutions = new ArrayList<>();
  private final List<Integer> bossAutoLoopExecutions = new ArrayList<>();
  private final List<Integer> bossSpecialLoopExecutions = new ArrayList<>();

  public GameLoopHelper(
      ActionsQueue actionsQueue, BossHandler bossHandler, RaiderHandler raiderHandler) {
    this.raiderHandler = raiderHandler;
    this.actionsQueue = actionsQueue;
    this.bossHandler = bossHandler;
  }

  public void incrementSecondsElapsed(Integer secondsElapsed) {
    this.secondsElapsed += secondsElapsed;
    //clearAutoLoopExecutions();
    tick(secondsElapsed);
  }

  private void tick(Integer secondsElapsed) {
    dpsActionLoop(secondsElapsed);
    healerAutoHealLoop(secondsElapsed);
    bossAutoAttackActionLoop(secondsElapsed);
    bossSpecialAttackActionLoop(secondsElapsed);
    raiderHandler.getRaidGroup().forEach(raider -> raider.tick(secondsElapsed));
  }

  public void processActionLoops() {

  }

  private void bossAutoAttackActionLoop(Integer secondsElapsed) {
    if (secondsElapsed % 2 == 0
        && secondsElapsed > 0
        && !bossAutoLoopExecutions.contains(secondsElapsed)) {
      bossAutoLoopExecutions.add(secondsElapsed);
      var bossAction = bossHandler.createBossAutoAttackAction();
      bossAction.ifPresent(actionsQueue::addActionToQueue);
    }
  }

  private void bossSpecialAttackActionLoop(Integer secondsElapsed) {
    if (secondsElapsed % 10 == 0
        && secondsElapsed > 0
        && !bossSpecialLoopExecutions.contains(secondsElapsed)) {
      bossSpecialLoopExecutions.add(secondsElapsed);
      actionsQueue.addActionToQueue(bossHandler.createBossSpecialAttackAction());
    }
  }

  private void dpsActionLoop(Integer secondsElapsed) {
    if (secondsElapsed % 5 == 0) {
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
    }

    /*
    if (secondsElapsed % 5 == 0
        && secondsElapsed > 0
        && !dpsLoopExecutions.contains(secondsElapsed)) {
      dpsLoopExecutions.add(secondsElapsed);
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
    }
     */
  }

  private void healerAutoHealLoop(Integer secondsElapsed) {
    if (secondsElapsed % 5 == 0
        && secondsElapsed > 0
        && !healLoopExecutions.contains(secondsElapsed)) {
      healLoopExecutions.add(secondsElapsed);
      raiderHandler
          .getRaidersOfType(EntityRole.HEALER)
          .forEach(
              healer -> {
                if (healer.isAlive()) {
                  actionsQueue.addActionToQueue(
                      raiderHandler.createHealerAutoHealAction((Healer) healer));
                }
              });
    }
  }

  private void clearAutoLoopExecutions() {
    this.dpsLoopExecutions.clear();
    this.healLoopExecutions.clear();
    this.bossAutoLoopExecutions.clear();
    this.bossSpecialLoopExecutions.clear();
  }
}
