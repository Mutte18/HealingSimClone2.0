package com.healing.state.model;

import com.healing.entity.Boss;
import com.healing.gamelogic.RaidGroup;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StateModel {
    private Boss boss;
    private RaidGroup raidGroup;

    public StateModel(Boss boss, RaidGroup raidGroup) {
        this.boss = boss;
        this.raidGroup = raidGroup;
    }
}
