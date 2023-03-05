package com.healing.spell.spellbook;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SpellBookTests {
  private SpellBook spellBook;

  @BeforeEach
  void setup() {
    spellBook = new SpellBook();
  }

  @Test
  void spellShouldBeOffCooldownWhenCooldownRemainingIs0() {
    var riptide = spellBook.getSpell("3").get();
    riptide.startCooldown();
    riptide.tick(riptide.getCoolDownTime());

    Assertions.assertEquals(false, riptide.getOnCooldown());
  }

  @Test
  void spellShouldBeOnCooldownWhenCooldownRemainingIsGreaterThan0() {
    var riptide = spellBook.getSpell("3").get();
    riptide.startCooldown();

    Assertions.assertEquals(true, riptide.getOnCooldown());
  }
}
