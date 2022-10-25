package com.healing.gamelogic.actions;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.healing.entity.Boss;
import com.healing.entity.Dps;
import com.healing.spell.spellbook.FlashHeal;
import org.junit.jupiter.api.Test;

public class NPCActionTest {
  @Test
  void shouldReduceTargetHealthWhenNPCCastsSpell() {
    var dps = new Dps(0, 100, true);
    var boss = new Boss(1, 1000, true, "Defias Pillager");
    var action = new NPCAction(dps, boss, new FlashHeal(), "1");
    action.performAction();

    assertEquals(950, boss.getHealth());
  }
}
