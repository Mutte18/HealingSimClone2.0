package com.healing.spellcast;

import com.healing.gamelogic.ActionsQueue;
import com.healing.gamelogic.RaiderHandler;
import com.healing.spell.exceptions.InvalidSpellNameException;
import com.healing.spell.exceptions.NoTargetException;
import com.healing.spell.spellbook.FlashHeal;
import com.healing.spell.spellbook.Spell;
import com.healing.spell.spellbook.SpellBook;
import com.healing.spell.spellcast.SpellCastService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

  @Test
  void castingSpellWithTargetIdShouldAddAnActionWithCorrectTarget() throws NoTargetException {
    var spell = spellBook.get(0);
    spellCastService.castSpell(spell.getSpellId(), "DPS0");
    var expectedTarget = raiderHandler.getRaiderById("DPS0").get();
    var actualTarget = actionsQueue.get(0).getTargets().get(0);

    assertEquals(expectedTarget.getId(), actualTarget.getId());
    assertEquals(1, actionsQueue.size());
  }

  @Test
  void castingSpellShouldReducePlayersMana() throws NoTargetException {
    var spell = spellBook.get(0);
    var player = raiderHandler.getPlayer();
    var manaBeforeCast = player.getMana();
    spellCastService.castSpell(spell.getSpellId(), "DPS0");

    assertEquals(manaBeforeCast - spell.getManaCost(), player.getMana());
  }

  @Test
  void castingBuffSpellShouldAddBuffToTarget() throws NoTargetException {
    var spell = getSpellByName("Renew").get();
    var target = "PLAYER0";
    spellCastService.castSpell(spell.getSpellId(), target);

    var expectedTarget = raiderHandler.getRaiderById(target).get();
    assertEquals(1, expectedTarget.getBuffs().size());
  }

  @Test
  void castingSpellWithInvalidIdShouldThrowInvalidSpellNameException() {
    var target = "PLAYER0";

    Assertions.assertThrows(
        InvalidSpellNameException.class, () -> spellCastService.castSpell("99", target));
  }

  @Test
  void castingSpellWithNoTargetShouldThrowNoTargetException() {
    var target = "GG123";

    Assertions.assertThrows(
        NoTargetException.class,
        () -> spellCastService.castSpell(new FlashHeal().getSpellId(), target));
  }

  private Optional<Spell> getSpellByName(String spellName) {
    return spellBook.stream().filter(spell -> spell.getName().equals(spellName)).findAny();
  }
}
