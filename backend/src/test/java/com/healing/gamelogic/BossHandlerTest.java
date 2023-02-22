package com.healing.gamelogic;

import com.healing.entity.Boss;
import com.healing.entity.EntityRole;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BossHandlerTest {
  BossHandler bossHandler;
  RaiderHandler raiderHandler;

  @BeforeEach
  void setup() {
    raiderHandler = new RaiderHandler();
    bossHandler = new BossHandler(raiderHandler);
  }

  @Test
  void shouldCreateBossAutoAttackOnTank() {
    bossHandler.createNewBoss(new Boss(0, 1000, true, "Defias Pillager"));
    raiderHandler.resetRaidGroup();
    var tanks = raiderHandler.getRaidersOfType(EntityRole.TANK);

    var optionalAction = bossHandler.createBossAutoAttackAction();
    optionalAction.ifPresent(
        action -> {
          Assertions.assertEquals(bossHandler.getCurrentBoss(), action.getInitiator());
          Assertions.assertEquals(tanks.get(0), action.getTargets().get(0));
          Assertions.assertEquals(1, action.getTargets().size());
        });
  }

  @Test
  void shouldPickANewFocusTargetWhenLastTargetIsDead() {
    bossHandler.createNewBoss(new Boss(0, 1000, true, "Defias Pillager"));
    var boss = bossHandler.getCurrentBoss();
    raiderHandler.resetRaidGroup();
    var tanks = raiderHandler.getRaidersOfType(EntityRole.TANK);
    bossHandler.createBossAutoAttackAction();

    var firstFocusTarget = boss.getCurrentTarget();
    tanks.forEach(tank -> tank.reduceHealth(99999));
    var optionalAction = bossHandler.createBossAutoAttackAction();
    var newFocusTarget = boss.getCurrentTarget();

    optionalAction.ifPresent(
        action -> {
          Assertions.assertNotEquals(firstFocusTarget, newFocusTarget);
          Assertions.assertEquals(newFocusTarget, action.getTargets().get(0));
        });
  }

  @Test
  void currentTargetShouldBeNullWhenNoRaiderIsAlive() {
    bossHandler.createNewBoss(Boss.builder().id(0).health(1000).alive(true).name("Defias Pillager").build());

    raiderHandler.resetRaidGroup();
    raiderHandler.killAllRaiders();

    var optionalAction = bossHandler.createBossAutoAttackAction();
    Assertions.assertTrue(optionalAction.isEmpty());
  }
}
