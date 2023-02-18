package com.healing.gamelogic.actions;

import com.healing.buff.Buff;
import com.healing.entity.Entity;

import java.util.List;

public class HoTAction extends Action {
    private final Entity entity;
    private final Integer healAmount;
    private final Buff buff;

    public HoTAction(Entity entity, Integer healAmount, Buff buff) {
        super(entity, List.of(entity), "0");
        this.entity = entity;
        this.healAmount = healAmount;
        this.buff = buff;
    }
    @Override
    public void performAction() {
        entity.increaseHealth(healAmount);
        System.out.println(buff.getClass().getName() + " ticked for " + buff.getHealAmount() + " on " + entity);
    }
}
