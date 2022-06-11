package com.healing.gamelogic.actions;

import com.healing.entity.Entity;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;

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
}
