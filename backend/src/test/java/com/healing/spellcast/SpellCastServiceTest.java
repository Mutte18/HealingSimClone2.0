package com.healing.spellcast;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import com.healing.gamelogic.ActionsQueue;
import com.healing.gamelogic.RaiderHandler;
import com.healing.spell.exceptions.InsufficientManaException;
import com.healing.spell.exceptions.InvalidSpellNameException;
import com.healing.spell.exceptions.NoTargetException;
import com.healing.spell.spellbook.FlashHeal;
import com.healing.spell.spellbook.Spell;
import com.healing.spell.spellbook.SpellBook;
import com.healing.spell.spellcast.SpellCastService;
import org.junit.jupiter.api.Assertions;
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
    var spell = getSpellByName("Renew");
    var target = "PLAYER0";
    spellCastService.castSpell(spell.getSpellId(), target);

    var expectedTarget = raiderHandler.getRaiderById(target).get();
    assertEquals(1, expectedTarget.getBuffs().size());
  }

  @Test
  void castingSpellWithHealAmountAndBuffShouldProduceActionAndAddBuff() throws NoTargetException {
    var spell = getSpellByName("Riptide");
    var target = "PLAYER0";
    spellCastService.castSpell(spell.getSpellId(), target);

    var expectedTarget = raiderHandler.getRaiderById(target).get();
    assertEquals(1, expectedTarget.getBuffs().size());

    assertEquals(1, actionsQueue.size());
  }

  @Test
  void castingSpellThatHasNoBuffShouldNotAddBuffToTarget() throws NoTargetException {
    var spell = getSpellByName("Flash Heal");
    var target = "PLAYER0";
    spellCastService.castSpell(spell.getSpellId(), target);

    var expectedTarget = raiderHandler.getRaiderById(target).get();
    assertEquals(0, expectedTarget.getBuffs().size());
  }

  @Test
  void getTargetsShouldGetTheMostInjuredRaiders() throws NoTargetException {
    var spell = getSpellByName("Chain Heal");
    var target = "PLAYER0";
    var raiderToBeHealed1 = raiderHandler.getRaiderById("DPS0").get();
    var raiderToBeHealed2 = raiderHandler.getRaiderById("DPS1").get();
    var raiderNotPicked = raiderHandler.getRaiderById("DPS2").get();

    raiderToBeHealed1.setHealth(20);
    raiderToBeHealed2.setHealth(40);
    raiderNotPicked.setHealth(80);

    spellCastService.castSpell(spell.getSpellId(), target);
    actionsQueue.processActionQueue();

    assertEquals(100, raiderToBeHealed1.getHealth());
    assertEquals(100, raiderToBeHealed2.getHealth());
    assertEquals(80, raiderNotPicked.getHealth());
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

  @Test
  void castingSpellWithoutSufficientManaShouldThrowException() {
    var target = "DPS0";
    raiderHandler.getPlayer().setMana(0);

    Assertions.assertThrows(
        InsufficientManaException.class,
        () -> spellCastService.castSpell(new FlashHeal().getSpellId(), target));
  }

  @Test
  void castingBuffSpellShouldGenerateUniqueBuff() throws NoTargetException {
    var spell = getSpellByName("Riptide");
    var target = "PLAYER0";
    spellCastService.castSpell(spell.getSpellId(), target);
    spellCastService.castSpell(spell.getSpellId(), target);

    var buffs = raiderHandler.getRaiderById("PLAYER0").get().getBuffs();
    var buff1 = buffs.get(0);
    var buff2 = buffs.get(1);

    assertNotEquals(buff1, buff2);
  }

  private Spell getSpellByName(String spellName) {
    return spellBook.stream()
        .filter(spell -> spell.getName().equals(spellName))
        .findAny()
        .orElse(null);
  }
}
