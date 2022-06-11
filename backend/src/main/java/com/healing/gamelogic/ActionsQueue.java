package com.healing.gamelogic;

import com.healing.gamelogic.actions.Action;

import java.util.ArrayList;
import java.util.Optional;

public class ActionsQueue {

    private final ArrayList<Action> actionsQueueList;

    public ActionsQueue() {
        this.actionsQueueList = new ArrayList<>();
    }

    public void addActionToQueue(Action action) {
        this.actionsQueueList.add(action);
    }

    public Optional<Action> getTopActionAndRemoveFromQueue() {
        var optionalAction = this.actionsQueueList.stream().findFirst();
        optionalAction.ifPresent(this.actionsQueueList::remove);
        return optionalAction;
    }
}
