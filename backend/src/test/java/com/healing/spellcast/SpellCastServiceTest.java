package com.healing.spellcast;

import com.healing.gamelogic.ActionsQueue;
import com.healing.gamelogic.RaiderHandler;
import com.healing.spell.exceptions.*;
import com.healing.spell.spellbook.*;
import com.healing.spell.spellcast.GlobalCooldownHandler;
import com.healing.spell.spellcast.SpellCastService;
import com.healing.spell.spellcast.SpellCastingHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SpellCastServiceTest {

  private SpellCastService spellCastService;
  private RaiderHandler raiderHandler;
  private GlobalCooldownHandler globalCooldownHandler;

  @BeforeEach
  public void setup() {
    raiderHandler = new RaiderHandler();
    raiderHandler.resetRaidGroup();
    globalCooldownHandler = new GlobalCooldownHandler();

    spellCastService =
        new SpellCastService(
            new SpellBook(),
            raiderHandler,
            globalCooldownHandler,
            new SpellCastingHandler(new ActionsQueue(), raiderHandler, globalCooldownHandler));
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
  void castingSpellWhenSpellIsOnCooldownShouldThrowException() throws NoTargetException {
    var spell = new Riptide();
    var target = "PLAYER0";
    spellCastService.castSpell(spell.getSpellId(), target);
    globalCooldownHandler.endGlobalCooldown();

    Assertions.assertThrows(
        SpellOnCooldownException.class,
        () -> spellCastService.castSpell(spell.getSpellId(), target));
  }

  @Test
  void castingSpellWhenAlreadyCastingShouldThrowException() throws NoTargetException {
    var spell = new ChainHeal();
    var target = "PLAYER0";
    spellCastService.castSpell(spell.getSpellId(), target);
    globalCooldownHandler.endGlobalCooldown();

    Assertions.assertThrows(
        AlreadyCastingException.class,
        () -> spellCastService.castSpell(spell.getSpellId(), target));
  }

  @Test
  void castingSpellDuringGlobalCooldownShouldThrowException() throws NoTargetException {
    var firstSpell = new Riptide();
    var secondSpell = new Renew();
    var target = "PLAYER0";
    spellCastService.castSpell(firstSpell.getSpellId(), target);

    Assertions.assertThrows(
        GlobalCooldownException.class,
        () -> spellCastService.castSpell(secondSpell.getSpellId(), target));
  }
}
