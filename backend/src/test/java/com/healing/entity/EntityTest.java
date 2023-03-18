package com.healing.entity;

import static org.junit.jupiter.api.Assertions.*;

import com.healing.buff.buffs.RenewBuff;
import com.healing.buff.buffs.RiptideBuff;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class EntityTest {
  private Dps dps;

  @BeforeEach
  void setUp() {
    this.dps = new Dps(0, 200, true);
  }

  @Test
  @DisplayName(
      "Given Entity health increased When health increased more than max Then health is not greater than max")
  void shouldNotIncreaseHealthGreaterThanMax() {
    dps.increaseHealth(100);
    assertEquals(200, dps.getHealth());
  }

  @Test
  @DisplayName(
      "Given Entity health reduced When Entity health is reduced more than remaining Then alive is false")
  void shouldSetAliveToFalseWhenHealthReducedLessThan0() {
    dps.reduceHealth(250);
    assertFalse(dps.isAlive());
  }

  @Test
  @DisplayName(
      "Given Entity health reduced When Entity health is reduced more than remaining Then health is 0")
  void shouldSetHealthTo0WhenReducedMoreThan0() {
    dps.reduceHealth(250);
    assertEquals(0, dps.getHealth());
  }

  @Test
  @DisplayName("Given Entity health reduced When Entity health is exactly 0 Then alive is false")
  void shouldSetAliveToFalseWhenHealthReducedToExactly0() {
    dps.reduceHealth(200);
    assertFalse(dps.isAlive());
  }

  @Test
  @DisplayName("Given Entity health reduced When Entity health is above 0 Then alive still true")
  void shouldNotChangeAliveWhenHealthRemainAbove0() {
    dps.reduceHealth(150);
    assertTrue(dps.isAlive());
  }

  @Test
  void idShouldReturnRoleAndIdCombination() {
    assertEquals("DPS0", dps.getId());
  }

  @Test
  void shouldReturnPercentageInHumanReadable() {
    dps = new Dps(0, 100, true);

    dps.reduceHealth(50);

    assertEquals(50, dps.getHpInPercent());
  }

  @Test
  void shouldReplaceBuffWhenAddingSame() {
    dps = new Dps(0, 100, true);
    dps.addBuff(new RenewBuff());
    dps.addBuff(new RenewBuff());

    assertEquals(1, dps.getBuffs().size());
  }

  @Test
  void shouldAddBuffWhenBuffsAreUnique() {
    dps = new Dps(0, 100, true);
    dps.addBuff(new RenewBuff());
    dps.addBuff(new RiptideBuff());

    assertEquals(2, dps.getBuffs().size());
  }
}
