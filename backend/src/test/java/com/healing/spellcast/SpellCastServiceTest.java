package com.healing.spellcast;

import com.healing.gamelogic.ActionsQueue;
import com.healing.gamelogic.actions.Action;
import com.healing.spell.spellbook.FlashHeal;
import com.healing.spell.spellbook.Spell;
import com.healing.spell.spellbook.SpellBook;
import com.healing.spell.spellcast.SpellCastService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class SpellCastServiceTest {

    @InjectMocks
    SpellCastService spellCastService;

    @Mock
    private ActionsQueue actionsQueue;
    @Mock
    private SpellBook spellBook;

    private AutoCloseable closeable;


    @BeforeEach
    public void setup() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    public void cleanup() throws Exception {
        closeable.close();
    }

    @Test
    public void shouldAddActionToQueueWhenCastingSpell() {
        when(spellBook.getSpell("0")).thenReturn(Optional.of(createSpell()));
        //spellCastService.castSpell("0");
        verify(actionsQueue).addActionToQueue(any(Action.class));
    }

    private Spell createSpell() {
        return new FlashHeal();
    }
}
