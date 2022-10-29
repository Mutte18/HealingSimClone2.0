package com.healing.gamelogic.actions;

import com.healing.entity.Entity;
import com.healing.spell.spellbook.Spell;
import java.util.ArrayList;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public abstract class Action {
  protected Entity initiator;
  protected ArrayList<Entity> targets;
  protected Spell spell;
  protected String id;

  public Action(Entity initiator, ArrayList<Entity> targets, Spell spell, String id) {
    this.initiator = initiator;
    this.targets = targets;
    this.spell = spell;
    this.id = id;
  }

  public abstract void performAction();
}
