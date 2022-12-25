package com.healing.gamelogic;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RaiderHandlerTest {
  private RaiderHandler raiderHandler;

  @BeforeEach
  void setup() {
    raiderHandler = new RaiderHandler();
    raiderHandler.resetRaidGroup();
  }

  @Test
  void shouldReturnTankAsNewTargetIfTankIsAlive() {
    var newTarget = raiderHandler.getNewTarget().get();
    assertEquals("TANK", newTarget.getRole());
  }

  @Test
  void shouldReturnTankAsNewTargetWhenOnlyOneTankIsDead() {
    var tanks = raiderHandler.getTanks();
    tanks.get(0).reduceHealth(999999);
    var newTarget = raiderHandler.getNewTarget().get();
    assertEquals("TANK", newTarget.getRole());
  }

  @Test
  void shouldNotReturnATankWhenTanksAreDead() {
    var tanks = raiderHandler.getTanks();
    tanks.get(0).reduceHealth(999999);
    tanks.get(1).reduceHealth(999999);

    var newTarget = raiderHandler.getNewTarget().get();
    assertNotEquals("TANK", newTarget.getRole());
  }

  @Test
  void shouldNotReturnATargetWhenAllRaidersAreDead() {
    raiderHandler.getRaidGroup().forEach(raider -> raider.reduceHealth(9999999));
    var newTarget = raiderHandler.getNewTarget();

    assertTrue(newTarget.isEmpty());
  }
}
