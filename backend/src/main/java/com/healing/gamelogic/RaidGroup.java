package com.healing.gamelogic;

import com.healing.entity.*;

import java.util.ArrayList;

public class RaidGroup extends ArrayList<Entity> {

    public RaidGroup() {
        this.addPlayer();
        this.addHealers();
        this.addTanks();
        this.addDps();
    }

    private void addPlayer() {
        this.add(new Player(0, 100, true, 1000));
    }

    private void addHealers() {
        for (int i = 0; i < 4; i++) {
            this.add(new Healer(i, 100, true));
        }
    }

    private void addTanks() {
        for (int i = 0; i < 2; i++) {
            this.add(new Tank(i, 100, true));
        }
    }

    private void addDps() {
        for (int i = 0; i < 13; i++) {
            this.add(new Dps(i, 100, true));
        }
    }

}
