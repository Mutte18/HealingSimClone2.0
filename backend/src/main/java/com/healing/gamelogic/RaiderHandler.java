package com.healing.gamelogic;

import com.healing.entity.*;

import java.util.ArrayList;
import java.util.UUID;

class RaiderHandler {
    RaiderHandler() {

    }



    ArrayList<Entity> createRaid() {
        int nrOfTanks = 2;
        int nrOfHealers = 4;
        int raidSize = 20;
        int nrOfDps = raidSize - nrOfHealers - nrOfTanks - 1;


        var raiders = new ArrayList<Entity>();
        raiders = createTanks(nrOfTanks, raiders);
        raiders = createHealers(nrOfHealers, raiders);
        raiders = createDps(nrOfDps, raiders);
        raiders = createPlayer(raiders);
        return raiders;
    }

    private ArrayList<Entity> createPlayer(ArrayList<Entity> raiders) {
        raiders.add(new Player(0, 500, true));
        return raiders;
    }
    private ArrayList<Entity> createTanks(int nrOfTanks, ArrayList<Entity> raiders) {
        for (int i = 0; i < nrOfTanks; i++) {
            raiders.add(new Tank(i, 2000, true));
        }
        return raiders;
    }
    private ArrayList<Entity> createHealers(int nrOfHealers, ArrayList<Entity> raiders) {
        for (int i = 0; i < nrOfHealers; i++) {
            raiders.add(new Healer(i, 400, true));
        }
        return raiders;
    }
    private ArrayList<Entity> createDps(int nrOfDps, ArrayList<Entity> raiders) {
        for (int i = 0; i < nrOfDps; i++) {
            raiders.add(new Dps(i, 200, true));
        }
        return raiders;
    }
}
