package com.healing.gamelogic;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

import java.util.ArrayList;

public class ActionsQueue extends ArrayList<Action> {

    @Bean
    @Scope("singleton")
    public ActionsQueue actionsQueueSingleton() {
        return new ActionsQueue();
    }

    public void addActionToQueue(Action action) {
        this.add(action);
    }

    public void removeActionFromQueue(Action action) {
        this.remove(action);
    }
}
