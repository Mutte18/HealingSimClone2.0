package com.healing.spellcast;

import static org.junit.jupiter.api.Assertions.*;

import com.healing.exceptionhandling.exceptions.InsufficientManaException;
import com.healing.gamelogic.ActionsQueue;
import com.healing.gamelogic.RaiderHandler;
import com.healing.spell.spellbook.ChainHeal;
import com.healing.spell.spellbook.FlashHeal;
import com.healing.spell.spellbook.Renew;
import com.healing.spell.spellbook.Riptide;
import com.healing.spell.spellcast.GlobalCooldownHandler;
import com.healing.spell.spellcast.SpellCastingHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SpellCastingHandlerTest {
  private SpellCastingHandler spellCastingHandler;
  private RaiderHandler raiderHandler;
  private ActionsQueue actionsQueue;
  private GlobalCooldownHandler globalCooldownHandler;

  @BeforeEach
  void setup() {
    actionsQueue = new ActionsQueue();
    raiderHandler = new RaiderHandler();
    raiderHandler.resetRaidGroup();
    globalCooldownHandler = new GlobalCooldownHandler();
    spellCastingHandler =
        new SpellCastingHandler(actionsQueue, raiderHandler, globalCooldownHandler);
  }

  @Test
  void finishingCastingSpellShouldReducePlayersMana() {
    var player = raiderHandler.getPlayer();
    var manaBeforeCast = player.getMana();
    var spell = new Renew();

    spellCastingHandler.startCastingSpell(
        spell, raiderHandler.getPlayer(), raiderHandler.getRaiderById("DPS0").get());

    assertEquals(manaBeforeCast - spell.getManaCost(), player.getMana());
  }

  @Test
  void finishingCastingBuffSpellShouldAddBuffToTarget() {
    var spell = new Renew();
    var expectedTarget = raiderHandler.getRaiderById("PLAYER0").get();
    spellCastingHandler.startCastingSpell(spell, raiderHandler.getPlayer(), expectedTarget);

    assertEquals(1, expectedTarget.getBuffs().size());
  }

  @Test
  void finishingCastingSpellThatHasNoBuffShouldNotAddBuffToTarget() {
    var spell = new FlashHeal();
    var expectedTarget = raiderHandler.getRaiderById("PLAYER0").get();
    spellCastingHandler.startCastingSpell(spell, raiderHandler.getPlayer(), expectedTarget);
    spellCastingHandler.tick(spell.getCastTime());

    assertEquals(0, expectedTarget.getBuffs().size());
  }

  @Test
  void finishingCastingSpellShouldAddAnActionWithCorrectTarget() {
    var spell = new ChainHeal();
    var expectedTarget = raiderHandler.getRaiderById("PLAYER0").get();
    spellCastingHandler.startCastingSpell(spell, raiderHandler.getPlayer(), expectedTarget);
    spellCastingHandler.tick(spell.getCastTime());

    var actualTarget = actionsQueue.get(0).getTargets().get(0);

    assertEquals(expectedTarget.getId(), actualTarget.getId());
    assertEquals(1, actionsQueue.size());
  }

  @Test
  void finishingCastingSpellWithHealAmountAndBuffShouldProduceActionAndAddBuff() {
    var spell = new Riptide();
    var expectedTarget = raiderHandler.getRaiderById("PLAYER0").get();
    spellCastingHandler.startCastingSpell(spell, raiderHandler.getPlayer(), expectedTarget);

    assertEquals(1, expectedTarget.getBuffs().size());

    assertEquals(1, actionsQueue.size());
  }

  @Test
  void getAdditionalTargetsShouldChooseTheMostInjuredRaiders() {
    var spell = new ChainHeal();
    var expectedTarget = raiderHandler.getRaiderById("PLAYER0").get();

    var additionalTargetToBeHealed1 = raiderHandler.getRaiderById("DPS0").get();
    var additionalTargetToBeHealed2 = raiderHandler.getRaiderById("DPS1").get();
    var raiderNotPicked = raiderHandler.getRaiderById("DPS2").get();

    additionalTargetToBeHealed1.setHealth(20);
    additionalTargetToBeHealed2.setHealth(40);
    raiderNotPicked.setHealth(80);

    spellCastingHandler.startCastingSpell(spell, raiderHandler.getPlayer(), expectedTarget);
    spellCastingHandler.tick(spell.getCastTime());

    actionsQueue.processActionQueue();

    assertEquals(100, additionalTargetToBeHealed1.getHealth());
    assertEquals(100, additionalTargetToBeHealed2.getHealth());
    assertEquals(80, raiderNotPicked.getHealth());
  }

  @Test
  void cancelSpellCastWhenCastingASpellShouldStopCasting() {
    var spell = new FlashHeal();
    var target = raiderHandler.getRaiderById("PLAYER0").get();
    spellCastingHandler.startCastingSpell(spell, raiderHandler.getPlayer(), target);
    spellCastingHandler.cancelSpellCast();

    assertFalse(spellCastingHandler.isCasting());
  }

  @Test
  void cancelSpellCastingWhenNotCastingShouldDoNothing() {
    spellCastingHandler.cancelSpellCast();

    assertFalse(spellCastingHandler.isCasting());
  }

  @Test
  void castingSpellWithCastTimeShouldStillBeCastingWhenCastTimeNotFinished() {
    var spell = new FlashHeal();
    var target = raiderHandler.getRaiderById("PLAYER0").get();
    spellCastingHandler.startCastingSpell(spell, raiderHandler.getPlayer(), target);

    spellCastingHandler.tick(1.0);
    assertEquals(spell, spellCastingHandler.getCastingSpell());
    assertTrue(spellCastingHandler.isCasting());
    assertEquals(0.5, spellCastingHandler.getCastTimeRemaining());
  }

  @Test
  void castingInstantSpellsShouldNotTriggerACastTime() {
    var spell = new Renew();
    var target = raiderHandler.getRaiderById("PLAYER0").get();
    spellCastingHandler.startCastingSpell(spell, raiderHandler.getPlayer(), target);

    assertNull(spellCastingHandler.getCastingSpell());
    assertFalse(spellCastingHandler.isCasting());
  }

  @Test
  void finishingCastingSpellWithInsufficientManaShouldThrowException() {
    var spell = new FlashHeal();
    var target = raiderHandler.getRaiderById("PLAYER0").get();
    spellCastingHandler.startCastingSpell(spell, raiderHandler.getPlayer(), target);
    raiderHandler.getPlayer().setMana(0);

    Assertions.assertThrows(
        InsufficientManaException.class, () -> spellCastingHandler.tick(spell.getCastTime()));
  }
}
