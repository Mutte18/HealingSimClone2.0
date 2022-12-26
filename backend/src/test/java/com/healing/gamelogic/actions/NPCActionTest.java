package com.healing.gamelogic.actions;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.healing.entity.Boss;
import com.healing.entity.Dps;
import com.healing.entity.attacks.MeleeSwing;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

public class NPCActionTest {
  @Test
  void shouldReduceTargetHealthWhenNPCCastsSpell() {
    var dps = new Dps(0, 100, true);
    var boss = new Boss(1, 1000, true, "Defias Pillager");
    var action = new NPCAction(dps, new ArrayList<>(List.of(boss)), new MeleeSwing(50), "1");
    action.performAction();

    assertEquals(950, boss.getHealth());
  }

  @Test
  void shouldReduceAllTargetedBossesHealthWhenNPCCastsSpell() {
    var dps = new Dps(0, 100, true);
    var boss = new Boss(1, 1000, true, "Defias Pillager");
    var boss2 = new Boss(2, 750, true, "Defias Rogue");
    var action = new NPCAction(dps, new ArrayList<>(List.of(boss, boss2)), new MeleeSwing(50), "1");
    action.performAction();

    assertEquals(950, boss.getHealth());
    assertEquals(700, boss2.getHealth());
  }
}
