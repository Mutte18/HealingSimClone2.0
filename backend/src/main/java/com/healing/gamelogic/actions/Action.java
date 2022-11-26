package com.healing.gamelogic.actions;

import com.healing.entity.Entity;
import java.util.ArrayList;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public abstract class Action {
  protected Entity initiator;
  protected ArrayList<Entity> targets;
  protected String id;

  public Action(Entity initiator, ArrayList<Entity> targets, String id) {
    this.initiator = initiator;
    this.targets = targets;
    this.id = id;
  }

  public abstract void performAction();
}
