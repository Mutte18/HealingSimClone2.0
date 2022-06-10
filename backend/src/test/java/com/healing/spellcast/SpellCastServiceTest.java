package com.healing.spellcast;

import com.healing.gamelogic.Action;
import com.healing.gamelogic.ActionsQueue;
import com.healing.spell.spellcast.SpellCastService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

public class SpellCastServiceTest {

    @InjectMocks
    SpellCastService spellCastService;

    @Mock
    private ActionsQueue actionsQueue;

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
    public void shouldAddActionWhenCastingSpell() {
        spellCastService.castSpell();
        verify(actionsQueue).addActionToQueue(any(Action.class));
    }
}
