package com.healing.spell;

import com.healing.entity.Player;
import com.healing.spell.exceptions.AlreadyCastingException;
import com.healing.spell.exceptions.InsufficientManaException;
import com.healing.spell.spellbook.SpellBook;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SpellCastServiceTests {
    private SpellCastService spellCastService;
    private SpellBook spellBook = new SpellBook();


    @Test
    void startCastSpellWhenNotCasting() {
        spellCastService = new SpellCastService(spellBook);
        Player player = new Player(0, 100, true, 100);

        spellCastService.startCastSpell(spellBook.get(0), player);
        assertTrue(spellCastService.isCasting());
        assertEquals(spellBook.get(0), spellCastService.getSpellBeingCast());
    }

    @Test
    void startCastSpell_WhenInsufficientMana_ThrowsInsufficientManaException() {
        spellCastService = new SpellCastService(spellBook);
        Player player = new Player(0, 100, true, 50);

        assertThrows(InsufficientManaException.class, () -> {
            spellCastService.startCastSpell(spellBook.get(0), player);
        });
    }

    @Test
    void startCastSpell_WhenAlreadyCasting_ThrowsAlreadyCastingException() {
        spellCastService = new SpellCastService(spellBook);
        Player player = new Player(0, 100, true, 100);

        spellCastService.startCastSpell(spellBook.get(0), player);
        assertThrows(AlreadyCastingException.class, () -> {
            spellCastService.startCastSpell(spellBook.get(0), player);
        });
    }

    @Test
    void interupptSpell_WhenCasting_StopsCasting() {
        spellCastService = new SpellCastService(spellBook);
        Player player = new Player(0, 100, true, 100);


        spellCastService.startCastSpell(spellBook.get(0), player);
        spellCastService.interupptSpell();
        assertFalse(spellCastService.isCasting());
        assertNull(spellCastService.getSpellBeingCast());
    }

    @Test
    void finishSpellCast_StopsCastingAndConsumesMana() {
        spellCastService = new SpellCastService(spellBook, spellBook.get(0), true);
        Player player = new Player(0, 100, true, 100);

        Integer expectedRemainingMana = player.getMana() - spellBook.get(0).getManaCost();

        spellCastService.finishSpellCast(player);

        assertFalse(spellCastService.isCasting());
        assertNull(spellCastService.getSpellBeingCast());
        assertEquals(expectedRemainingMana, player.getMana());
    }

}
