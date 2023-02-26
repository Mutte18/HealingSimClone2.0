package com.healing.gamelogic.actions;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.healing.entity.Dps;
import com.healing.entity.Healer;
import com.healing.entity.Player;
import com.healing.spell.spellbook.FlashHeal;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

public class PlayerActionTest {
  @Test
  void shouldIncreaseTargetHealthWhenTargetIsHealed() {
    var player = new Player(0, 0, true, 100);
    var dps = new Dps(1, 100, true);
    var action = new PlayerAction(player, new ArrayList<>(List.of(dps)), new FlashHeal(), "1");
    dps.setHealth(25);
    action.performAction();

    assertEquals(100, dps.getHealth());
  }

  @Test
  void shouldIncreaseTargetHealthOfAllTargetsHealed() {
    var player = new Player(0, 0, true, 100);
    var dps = new Dps(1, 100, true);
    var healer = new Healer(1, 100, true);
    var action =
        new PlayerAction(player, new ArrayList<>(List.of(dps, healer)), new FlashHeal(), "1");
    dps.setHealth(25);
    healer.setHealth(75);

    action.performAction();

    assertEquals(100, dps.getHealth());
    assertEquals(100, healer.getHealth());
  }
}
