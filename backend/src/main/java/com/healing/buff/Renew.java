package com.healing.buff;

import com.healing.entity.Entity;
import com.healing.gamelogic.ActionsQueue;
import com.healing.gamelogic.actions.HoTAction;

public class Renew extends Buff {
    private final static int duration = 10 * TENTH_OF_SECOND;
    private final static double interval = 1.5 * TENTH_OF_SECOND;
    private final static int healAmount = 45;
    public Renew() {
        super(duration);
    }

    @Override
    public void addAction(Entity entity, ActionsQueue actionsQueue) {
        if (!isExpired()) {
            if (timeElapsed % interval == 0) {
                actionsQueue.addActionToQueue(new HoTAction(entity, healAmount, this));
            }
        }
    }


    public int getHealAmount() {
        return healAmount;
    }
}