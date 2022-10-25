package com.healing.gamelogic.actions;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.healing.entity.Dps;
import com.healing.entity.Player;
import com.healing.spell.spellbook.FlashHeal;
import org.junit.jupiter.api.Test;

public class PlayerActionTest {
  @Test
  void shouldReducePlayerManaWhenPlayerActionIsProcessed() {
    var player = new Player(0, 0, true, 100);
    var action = new PlayerAction(player, new Dps(1, 1, true), new FlashHeal(), "1");
    action.performAction();

    assertEquals(0, player.getMana());
  }

  @Test
  void shouldIncreaseTargetHealthWhenPlayerActionIsProcessed() {
    var player = new Player(0, 0, true, 100);
    var dps = new Dps(1, 100, true);
    var action = new PlayerAction(player, dps, new FlashHeal(), "1");
    dps.setHealth(25);
    action.performAction();

    assertEquals(100, dps.getHealth());
  }
}
