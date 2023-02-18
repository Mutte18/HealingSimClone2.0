package com.healing.buff;

import com.healing.entity.Entity;
import com.healing.gamelogic.ActionsQueue;
import lombok.Getter;

@Getter
public abstract class Buff {
    protected int timeElapsed;
    private final int duration;
    private boolean isExpired = false;
    protected final static int TENTH_OF_SECOND = 10;

    public Buff(int duration) {
        this.duration = duration;
    }

    public void incrementTimeElapsed(int timeIncrease) {
        if (!isExpired) {
            timeElapsed += timeIncrease;
            if (timeElapsed >= duration) {
                isExpired = true;
            }
        }
    }

    public abstract void addAction(Entity entity, ActionsQueue actionsQueue);
    public abstract int getHealAmount();
}
