package com.healing.spellcast;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.healing.gamelogic.ActionsQueue;
import com.healing.gamelogic.RaiderHandler;
import com.healing.spell.spellbook.SpellBook;
import com.healing.spell.spellcast.SpellCastService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SpellCastServiceTest {

  SpellCastService spellCastService;

  private ActionsQueue actionsQueue;
  private SpellBook spellBook;
  private RaiderHandler raiderHandler;

  @BeforeEach
  public void setup() {
    actionsQueue = new ActionsQueue();
    spellBook = new SpellBook();
    raiderHandler = new RaiderHandler();

    spellCastService = new SpellCastService(actionsQueue, spellBook, raiderHandler);
  }

  @Test
  public void shouldAddActionToQueueWhenCastingSpell() {
    var action = spellCastService.castSpell("1", "1");
    assertEquals(1, action.getTargets().get(0).getId());
    assertEquals("Flash Heal", action.getSpell().getName());
  }
}
