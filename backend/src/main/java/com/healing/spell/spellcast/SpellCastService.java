package com.healing.spell.spellcast;

import com.healing.entity.Player;
import com.healing.exceptionhandling.exceptions.*;
import com.healing.gamelogic.RaiderHandler;
import com.healing.spell.spellbook.Spell;
import com.healing.spell.spellbook.SpellBook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SpellCastService {

  private final SpellBook spellBook;
  private final GlobalCooldownHandler globalCooldownHandler;
  private final SpellCastingHandler spellCastingHandler;
  private final RaiderHandler raiderHandler;

  @Autowired
  public SpellCastService(
      SpellBook spellBook,
      RaiderHandler raiderHandler,
      GlobalCooldownHandler globalCooldownHandler,
      SpellCastingHandler spellCastingHandler) {
    this.spellBook = spellBook;
    this.raiderHandler = raiderHandler;
    this.globalCooldownHandler = globalCooldownHandler;
    this.spellCastingHandler = spellCastingHandler;
  }

  public void castSpell(String spellId, String targetId) throws TargetNotFoundException {
    var player = raiderHandler.getPlayer();
    var optionalSpell = spellBook.getSpell(spellId);
    var optionalTarget = raiderHandler.getRaiderById(targetId);

    if (optionalTarget.isEmpty()) {
      throw new TargetNotFoundException();
    }
    if (optionalSpell.isEmpty()) {
      throw new SpellNotFoundException();
    }
    var target = optionalTarget.get();
    var spell = optionalSpell.get();

    if (player != null) {
      validationChecks(spell, player);
      spellCastingHandler.startCastingSpell(spell, player, target);
    }
  }

  private void validationChecks(Spell spell, Player player) {
    if (globalCooldownHandler.isOnCooldown()) {
      throw new GlobalCooldownException();
    }
    if (spellCastingHandler.isCasting()) {
      throw new AlreadyCastingException();
    }
    if (spell.getOnCooldown()) {
      throw new SpellOnCooldownException(
          spell.getName()
              + " is not ready yet. Cooldown remaining: "
              + spell.getRemainingCooldown()
              + " seconds ");
    }
    if (player.getMana() < spell.getManaCost()) {
      throw new InsufficientManaException();
    }
  }
}
