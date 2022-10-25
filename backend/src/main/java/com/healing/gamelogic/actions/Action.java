package com.healing.gamelogic.actions;

import com.healing.entity.Entity;
import java.util.ArrayList;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public abstract class Action {
  private Entity target;
  private ArrayList<Entity> targets;
  private String id;

  public Action(Entity target, String id) {
    this.target = target;
    this.id = id;
  }

  public abstract void performAction();
}
