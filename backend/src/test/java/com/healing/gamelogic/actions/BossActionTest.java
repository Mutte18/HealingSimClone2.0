package com.healing.gamelogic.actions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import com.healing.entity.Boss;
import com.healing.entity.Player;
import com.healing.spell.spellbook.FlashHeal;
import org.junit.jupiter.api.Test;

public class BossActionTest {
  @Test
  void shouldReduceTargetHealthWhenBossCastsSpell() {
    var player = new Player(0, 100, true, 100);
    var boss = new Boss(1, 1000, true, "Defias Pillager");
    var action = new BossAction(boss, player, new FlashHeal(), "1");
    action.performAction();

    assertEquals(50, player.getHealth());
  }

  @Test
  void shouldKillTargetWhenBossCastsDoubleSpells() {
    var player = new Player(0, 100, true, 100);
    var boss = new Boss(1, 1000, true, "Defias Pillager");
    var action = new BossAction(boss, player, new FlashHeal(), "1");
    action.performAction();
    action.performAction();

    assertEquals(0, player.getHealth());
    assertFalse(player.isAlive());
  }
}
