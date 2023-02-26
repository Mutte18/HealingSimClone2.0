package com.healing.spell.spellcast;

import com.healing.entity.Entity;
import com.healing.gamelogic.ActionsQueue;
import com.healing.gamelogic.RaiderHandler;
import com.healing.gamelogic.actions.PlayerAction;
import com.healing.spell.exceptions.InsufficientManaException;
import com.healing.spell.exceptions.InvalidSpellNameException;
import com.healing.spell.exceptions.NoTargetException;
import com.healing.spell.spellbook.SpellBook;
import com.healing.spell.spellbook.spellType.BuffSpell;
import com.healing.spell.spellbook.spellType.HealSpell;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SpellCastService {

  private final ActionsQueue actionsQueue;
  private final SpellBook spellBook;
  private final RaiderHandler raiderHandler;

  @Autowired
  public SpellCastService(
      ActionsQueue actionsQueue, SpellBook spellBook, RaiderHandler raiderHandler) {
    this.actionsQueue = actionsQueue;
    this.spellBook = spellBook;
    this.raiderHandler = raiderHandler;
  }

  public void castSpell(String spellId, String targetId) throws NoTargetException {
    var player = raiderHandler.getPlayer();
    var spell = spellBook.getSpell(spellId);
    var target = raiderHandler.getRaiderById(targetId);

    if (target.isEmpty()) {
      throw new NoTargetException();
    }

    if (spell.isEmpty()) {
      throw new InvalidSpellNameException();
    }

    if (player != null) {
      if (player.getMana() < spell.get().getManaCost()) {
        throw new InsufficientManaException();
      }
      switch (spell.get()) {
        case BuffSpell buffSpell -> target.get().getBuffs().add(buffSpell.getBuff());
        case HealSpell healSpell -> actionsQueue.addActionToQueue(
                new PlayerAction(player, getTargets(target.get(), healSpell.getAdditionalTargets()), healSpell, "1"));
        default -> throw new IllegalStateException("Unexpected value: " + spell.get());
      }
      /*switch (spell.get().getSpellType()) {
        case BUFF -> target.get().getBuffs().add(spell.get().get);
        case HEAL -> actionsQueue.addActionToQueue(
            new PlayerAction(player, new ArrayList<>(List.of(target.get())), spell.get(), "1"));
      }

       */
      player.reduceMana(spell.get().getManaCost());
      System.err.println("Casted " + spell.get().getName() + " on " + target.get().getId());
    }
    // Retrieve spell to be cast
    // If spell was found, trigger event to Game and return OK cast to controller

    // If something wrong, return false to controller

  }

  private List<Entity> getTargets(Entity target, Integer additionalTargets) {
    var targets = new ArrayList<Entity>();
    targets.add(target);
    targets.addAll(raiderHandler.getLeastHealthyRaiders(target, additionalTargets));
  return targets;
  }
}
