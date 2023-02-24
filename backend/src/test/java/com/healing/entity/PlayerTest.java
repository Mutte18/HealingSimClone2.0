package com.healing.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PlayerTest {

  private Player player;

  @BeforeEach
  void setup() {
    player = Player.builder().health(100).alive(true).mana(100).build();
  }

  @Test
  void shouldIncreaseManaEveryTwoSecondsByManaRegAmount() {
    player.setMana(0);
    player.tick(2);

    Assertions.assertEquals(player.getManaRegenAmount(), player.getMana());
  }

  @Test
  void shouldNotIncreaseManaOnUnevenSecondTicks() {
    player.setMana(0);
    player.tick(1);

    Assertions.assertEquals(0, player.getMana());
  }

  @Test
  void increasingManaShouldNotCauseManaToExceedMax() {
    player.increaseMana(9999);

    Assertions.assertEquals(player.getMaxMana(), player.getMana());
  }

  @Test
  void reducingManaBelowZeroShouldNotCauseManaToIntoNegative() {
    player.reduceMana(9999);

    Assertions.assertEquals(0, player.getMana());
  }
}
