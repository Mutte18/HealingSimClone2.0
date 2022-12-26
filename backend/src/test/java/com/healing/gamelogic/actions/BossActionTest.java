package com.healing.gamelogic.actions;

import com.healing.entity.*;
import com.healing.entity.attacks.MeleeSwing;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BossActionTest {
  @Test
  void shouldReduceTargetHealthWhenBossDoesAttackOnOneTarget() {
    var player = new Player(0, 100, true, 100);
    var boss = new Boss(1, 1000, true, "Defias Pillager");
    var action =
        new BossAction(
            boss, new ArrayList<>(List.of(player)), new MeleeSwing(50), "1");
    action.performAction();

    assertEquals(50, player.getHealth());
  }

  @Test
  void shouldReduceTargetHealthWhenBossCastsSpellOnMultipleTargets() {
    var player = new Player(0, 100, true, 100);
    var dps = new Dps(1, 150, true);
    var healer = new Healer(2, 200, true);
    var boss = new Boss(1, 1000, true, "Defias Pillager");

    var action =
        new BossAction(
            boss,
            new ArrayList<>(List.of(player, dps, healer)),
            new MeleeSwing(50),
            "1");
    action.performAction();

    assertEquals(50, player.getHealth());
    assertEquals(100, dps.getHealth());
    assertEquals(150, healer.getHealth());
  }

  @Test
  void shouldKillTargetWhenBossCastsDoubleSpells() {
    var player = new Player(0, 100, true, 100);
    var boss = new Boss(1, 1000, true, "Defias Pillager");
    var action =
        new BossAction(
            boss, new ArrayList<>(List.of(player)), new MeleeSwing(100), "1");
    action.performAction();
    action.performAction();

    assertEquals(0, player.getHealth());
    assertFalse(player.isAlive());
  }

  @Test
  void shouldOnlyKillTargetsWhenHealthIsZero() {
    var player = new Player(0, 100, true, 100);
    var healer = new Healer(0, 200, true);

    var boss = new Boss(1, 1000, true, "Defias Pillager");
    var action =
        new BossAction(
            boss, new ArrayList<>(List.of(player, healer)), new MeleeSwing(50), "1");
    action.performAction();
    action.performAction();

    assertEntityIsDead(player);
    assertEntityIsAlive(healer);
  }

  private void assertEntityIsDead(Entity entity) {
    assertEquals(0, entity.getHealth());
    assertFalse(entity.isAlive());
  }

  private void assertEntityIsAlive(Entity entity) {
    assertEquals(100, entity.getHealth());
    assertTrue(entity.isAlive());
  }
}
