package com.healing.gamelogic.actions;

import com.healing.entity.Entity;
import com.healing.entity.Player;
import com.healing.spell.spellbook.spellType.HealSpell;
import lombok.Getter;

import java.util.List;

@Getter
public class PlayerAction extends Action {

  private final Player player;
  private final HealSpell spell;

  public PlayerAction(Player player, List<Entity> targets, HealSpell spell, String id) {
    super(player, targets, id);
    this.player = player;
    this.spell = spell;
  }

  @Override
  public void performAction() {
    if (!targets.isEmpty()) {
      for (var target : targets) {
        target.increaseHealth(spell.getHealAmount());
      }
    }
    System.out.println(
        "Performed action, "
            + player.getRole()
            + player.getId()
            + " casted Spell: "
            + spell.getName()
            + " on Targets: "
            + getTargetsInfo());
  }
}
