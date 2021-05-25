package com.healing.spell.spellbook;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SpellBookTests {

    @Test
    void createNewSpellBook_ContainsUniqueSpells() {
        SpellBook spellBook = new SpellBook();
        assertEquals(1, spellBook.size());
    }
}
