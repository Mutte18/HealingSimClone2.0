package com.healing.gamelogic.actions;

import com.healing.entity.Entity;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Getter
public abstract class Action {
  protected Entity initiator;
  protected List<Entity> targets;
  protected String id;

  public Action(Entity initiator, List<Entity> targets, String id) {
    this.initiator = initiator;
    this.targets = targets;
    this.id = id;
  }

  public abstract void performAction();

  protected ArrayList<String> getTargetsInfo() {
    var targetsInfoList = new ArrayList<String>();
    for (var target : targets) {
      targetsInfoList.add(target.getId());
    }
    return targetsInfoList;
  }
}
