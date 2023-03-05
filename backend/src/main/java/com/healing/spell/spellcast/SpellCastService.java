package com.healing.spell.spellcast;

import com.healing.entity.Entity;
import com.healing.gamelogic.ActionsQueue;
import com.healing.gamelogic.RaiderHandler;
import com.healing.spell.exceptions.*;
import com.healing.spell.spellbook.SpellBook;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SpellCastService {

  private final ActionsQueue actionsQueue;
  private final SpellBook spellBook;
  private final RaiderHandler raiderHandler;
  private final GlobalCooldownHandler globalCooldownHandler;

  @Autowired
  public SpellCastService(
      ActionsQueue actionsQueue,
      SpellBook spellBook,
      RaiderHandler raiderHandler,
      GlobalCooldownHandler globalCooldownHandler) {
    this.actionsQueue = actionsQueue;
    this.spellBook = spellBook;
    this.raiderHandler = raiderHandler;
    this.globalCooldownHandler = globalCooldownHandler;
  }

  public void castSpell(String spellId, String targetId) throws NoTargetException {
    var player = raiderHandler.getPlayer();
    var optionalSpell = spellBook.getSpell(spellId);
    var optionalTarget = raiderHandler.getRaiderById(targetId);

    if (optionalTarget.isEmpty()) {
      throw new NoTargetException();
    }
    if (optionalSpell.isEmpty()) {
      throw new InvalidSpellNameException();
    }
    var target = optionalTarget.get();
    var spell = optionalSpell.get();

    if (player != null) {
      if (globalCooldownHandler.isOnCooldown()) {
        throw new GlobalCooldownException();
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
      globalCooldownHandler.toggleGlobalCooldown(true);
      spell.startCooldown();
      spell.createAction(
          actionsQueue, target, getAdditionalTargets(target, spell.getAdditionalTargets()), player);
      System.out.println(spell);
      spell.addBuff(target);

      player.reduceMana(spell.getManaCost());
      System.err.println("Casted " + spell.getName() + " on " + target.getId());
    }
  }

  private List<Entity> getAdditionalTargets(Entity target, Integer additionalTargets) {
    var targets = new ArrayList<Entity>();
    targets.add(target);
    targets.addAll(raiderHandler.getLeastHealthyRaiders(target, additionalTargets));
    return targets;
  }
}
