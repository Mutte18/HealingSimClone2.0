package com.healing.gamelogic;

import com.healing.gamelogic.actions.Action;
import java.util.ArrayList;
import java.util.Optional;

public class ActionsQueue extends ArrayList<Action> {

  public ActionsQueue() {}

  public Action addActionToQueue(Action action) {
    this.add(action);
    return action;
  }

  public Optional<Action> getTopActionAndRemoveFromQueue() {
    var optionalAction = this.stream().findFirst();
    optionalAction.ifPresent(this::remove);
    return optionalAction;
  }
}
