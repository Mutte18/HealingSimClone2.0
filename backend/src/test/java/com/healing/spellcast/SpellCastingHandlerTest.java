package com.healing.spellcast;

import static org.junit.jupiter.api.Assertions.*;

import com.healing.gamelogic.ActionsQueue;
import com.healing.gamelogic.RaiderHandler;
import com.healing.spell.spellbook.ChainHeal;
import com.healing.spell.spellbook.FlashHeal;
import com.healing.spell.spellbook.Renew;
import com.healing.spell.spellbook.Riptide;
import com.healing.spell.spellcast.GlobalCooldownHandler;
import com.healing.spell.spellcast.SpellCastingHandler;
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

  /*
  1. Start globalCooldown
  2. Starts casting
  3. No cast time for instant spells
  4. Tick counts down cast time
  5. Cancels casting spell casts
  6. Adds buffs, performs actions, reduces mana on finished spell cast
   */
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
  void castingTheSameBuffSpellShouldGenerateAUniqueBuffPerCast() {
    var spell = new Renew();
    var target = raiderHandler.getRaiderById("PLAYER0").get();
    spellCastingHandler.startCastingSpell(spell, raiderHandler.getPlayer(), target);
    globalCooldownHandler.endGlobalCooldown();
    spellCastingHandler.startCastingSpell(spell, raiderHandler.getPlayer(), target);

    var buffs = target.getBuffs();
    var buff1 = buffs.get(0);
    var buff2 = buffs.get(1);

    assertNotEquals(buff1, buff2);
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
}
