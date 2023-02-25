package com.healing.spell.spellbook;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class SpellBookTests {

  @Test
  void createNewSpellBook_ContainsUniqueSpells() {
    SpellBook spellBook = new SpellBook();
    assertEquals(2, spellBook.size());
  }
}
