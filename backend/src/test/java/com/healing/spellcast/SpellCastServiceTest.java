package com.healing.spellcast;

import com.healing.gamelogic.ActionsQueue;
import com.healing.gamelogic.RaiderHandler;
import com.healing.spell.spellbook.SpellBook;
import com.healing.spell.spellcast.SpellCastService;
import org.junit.jupiter.api.BeforeEach;

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
    raiderHandler.resetRaidGroup();

    spellCastService = new SpellCastService(actionsQueue, spellBook, raiderHandler);
  }
}
