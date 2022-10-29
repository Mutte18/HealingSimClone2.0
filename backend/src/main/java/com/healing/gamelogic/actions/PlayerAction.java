package com.healing.gamelogic.actions;

import com.healing.entity.Entity;
import com.healing.entity.Player;
import com.healing.spell.spellbook.Spell;
import java.util.ArrayList;
import lombok.Getter;

@Getter
public class PlayerAction extends Action {

  private Player player;

  public PlayerAction(Player player, ArrayList<Entity> targets, Spell spell, String id) {
    super(player, targets, spell, id);
    this.player = player;
  }

  @Override
  public void performAction() {
    if (!targets.isEmpty()) {
      player.reduceMana(spell.getManaCost());
      for (var target : targets) {
        target.increaseHealth(spell.getHealAmount());
      }
    }
    System.out.println(
        "Performed action, "
            + player
            + " casted Spell: "
            + spell.getName()
            + " on Targets: "
            + targets);
  }
}
