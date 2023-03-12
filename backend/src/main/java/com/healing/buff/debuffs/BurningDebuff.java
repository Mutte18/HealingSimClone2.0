package com.healing.buff.debuffs;

import com.healing.buff.Buff;
import com.healing.buff.BuffType;
import com.healing.entity.Entity;
import com.healing.gamelogic.ActionsQueue;
import com.healing.gamelogic.actions.DoTAction;
import com.healing.gamelogic.actions.HoTAction;

public class BurningDebuff extends Buff {
    private static final double duration = 12.0;
    private static final double interval = 2.0;
    private static final int damageAmount = 45;

    public BurningDebuff() {
        super(duration, interval, "Burning", BuffType.DEBUFF);
    }

    @Override
    public void addAction(Entity entity, ActionsQueue actionsQueue) {
        if (!isExpired()) {
            if (remainingDuration % interval == 0) {
                actionsQueue.addActionToQueue(new DoTAction(entity, damageAmount, this));
            }
        }
    }
}
