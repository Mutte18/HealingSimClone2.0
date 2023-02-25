package com.healing.gamelogic;

import com.healing.entity.Dps;
import com.healing.entity.EntityRole;
import com.healing.entity.Healer;
import org.springframework.stereotype.Component;

@Component
public class GameLoopHelper {
  private final RaiderHandler raiderHandler;
  private final BossHandler bossHandler;
  private final ActionsQueue actionsQueue;

  private int secondsElapsed = 0;

  public GameLoopHelper(
      ActionsQueue actionsQueue, BossHandler bossHandler, RaiderHandler raiderHandler) {
    this.raiderHandler = raiderHandler;
    this.actionsQueue = actionsQueue;
    this.bossHandler = bossHandler;
  }

  public void tick(Integer seconds) {
    this.secondsElapsed += seconds;

    /*dpsAutoAttack(secondsElapsed);
    npcHealerAoEHeal(secondsElapsed);
    bossAutoAttack(secondsElapsed);
    bossSpecialAttack(secondsElapsed);

     */
    raiderHandler.getRaidGroup().forEach(raider -> raider.tick(secondsElapsed));
  }

  private void bossAutoAttack(Integer secondsElapsed) {
    if (secondsElapsed % 2 == 0) {
      var bossAction = bossHandler.createBossAutoAttackAction();
      bossAction.ifPresent(actionsQueue::addActionToQueue);
    }
  }

  private void bossSpecialAttack(Integer secondsElapsed) {
    if (secondsElapsed % 10 == 0) {
      actionsQueue.addActionToQueue(bossHandler.createBossSpecialAttackAction());
    }
  }

  private void dpsAutoAttack(Integer secondsElapsed) {
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
  }

  private void npcHealerAoEHeal(Integer secondsElapsed) {
    if (secondsElapsed % 5 == 0) {
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
}
