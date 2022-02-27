package com.healing.spellcast;

import com.healing.spell.events.publishers.SpellCastPublisher;
import com.healing.spell.exceptions.AlreadyCastingException;
import com.healing.spell.spellcast.SpellCastService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class SpellCastServiceTest {

    private SpellCastService spellCastService;

    @BeforeEach
    void setup() {
        this.spellCastService = new SpellCastService(new SpellCastPublisher());
    }

    @Test
    void shouldStartCastingWhenNotCastingSpell() {
        spellCastService.castSpell(1);
        assertTrue(spellCastService.isCasting());
    }

    @Test
    void shouldFinishCastWhenCastTimeFulfilled() throws InterruptedException {
        spellCastService.castSpell(100);
        Thread.sleep(200);
        assertFalse(spellCastService.isCasting());
    }

    @Test
    void shouldThrowAlreadyCastingExceptionWhenAlreadyCasting() {
        spellCastService.castSpell(100);
        Assertions.assertThrows(AlreadyCastingException.class, () -> {
            spellCastService.castSpell(100);
        });
    }

    @Test
    void shouldFinishCastingInstantlyWithCastTime0() {
        spellCastService.castSpell(0);
        assertFalse(spellCastService.isCasting());
    }

    @Test
    void shouldStopCastingWhenCancellingSpellCast() throws InterruptedException {
        spellCastService.castSpell(1000);
        Thread.sleep(200);
        spellCastService.cancelSpellCast();
        assertFalse(spellCastService.isCasting());

    }
}
