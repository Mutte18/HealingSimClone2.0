package com.healing.spell.spellcast;

import com.healing.entity.Entity;
import com.healing.entity.Player;
import com.healing.gamelogic.ActionsQueue;
import com.healing.gamelogic.RaiderHandler;
import com.healing.spell.spellbook.Spell;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Component
@Getter
public class SpellCastingHandler {
  private Spell castingSpell = null;
  private Double castTime = 0.0;
  private Double castTimeRemaining = 0.0;
  private Player player;
  private Entity target;
  private boolean isCasting = false;

  private final ActionsQueue actionsQueue;
  private final RaiderHandler raiderHandler;
  private final GlobalCooldownHandler globalCooldownHandler;

  public SpellCastingHandler(
      ActionsQueue actionsQueue,
      RaiderHandler raiderHandler,
      GlobalCooldownHandler globalCooldownHandler) {
    this.actionsQueue = actionsQueue;
    this.raiderHandler = raiderHandler;
    this.globalCooldownHandler = globalCooldownHandler;
  }

  public void startCastingSpell(Spell spell, Player player, Entity target) {
    this.castingSpell = spell;
    this.castTime = spell.getCastTime();
    this.castTimeRemaining = spell.getCastTime();
    this.isCasting = true;
    this.player = player;
    this.target = target;
    globalCooldownHandler.startGlobalCooldown();
    spell.startCooldown();
    System.out.println(
        "Started casting " + spell.getName() + " time remaining: " + castTimeRemaining);

    if (castTime == 0) {
      finishSpellCast();
    }
  }

  public void tick(Double tenthOfSeconds) {
    if (isCasting) {
      this.castTimeRemaining -= tenthOfSeconds;
      System.out.println(castTimeRemaining);
      if (castTimeRemaining <= 0) {
        finishSpellCast();
      }
    }
  }

  public void cancelSpellCast() {
    if (isCasting && castingSpell != null) {
      System.out.println(
          "Cancelled casting "
              + castingSpell.getName()
              + " with "
              + castTimeRemaining
              + " seconds remaining");
      resetSpellCastState();
    }
  }

  private void finishSpellCast() {
    castingSpell.createAction(
        actionsQueue,
        target,
        getAdditionalTargets(target, castingSpell.getAdditionalTargets()),
        player);
    castingSpell.addBuff(target);
    player.reduceMana(castingSpell.getManaCost());
    System.err.println("Casted " + castingSpell.getName() + " on " + target.getId());

    resetSpellCastState();
  }

  private List<Entity> getAdditionalTargets(Entity target, Integer additionalTargets) {
    var targets = new ArrayList<Entity>();
    targets.add(target);
    targets.addAll(raiderHandler.getLeastHealthyRaiders(target, additionalTargets));
    return targets;
  }

  private void resetSpellCastState() {
    this.isCasting = false;
    this.castingSpell = null;
  }
}
