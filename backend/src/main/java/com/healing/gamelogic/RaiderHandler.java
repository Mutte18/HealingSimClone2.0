package com.healing.gamelogic;

import com.healing.entity.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class RaiderHandler {

    public RaidGroup clearRaid(RaidGroup raidGroup) {
        raidGroup.clear();
        return raidGroup;
    }

    public RaidGroup createRaid() {
        int nrOfTanks = 2;
        int nrOfHealers = 4;
        int raidSize = 20;
        int nrOfDps = raidSize - nrOfHealers - nrOfTanks - 1;

        var raiders = new RaidGroup();
        addTanks(nrOfTanks, raiders);
        addHealers(nrOfHealers, raiders);
        addDps(nrOfDps, raiders);
        addPlayer(raiders);
        return raiders;
    }

    private RaidGroup addPlayer(RaidGroup raidGroup) {
        raidGroup.add(new Player(0, 500, true, 500));
        return raidGroup;
    }

    private RaidGroup addTanks(int nrOfTanks, RaidGroup raidGroup) {
        for (int i = 0; i < nrOfTanks; i++) {
            raidGroup.add(new Tank(i, 2000, true));
        }
        return raidGroup;
    }

    private RaidGroup addHealers(int nrOfHealers, RaidGroup raidGroup) {
        for (int i = 0; i < nrOfHealers; i++) {
            raidGroup.add(new Healer(i, 400, true));
        }
        return raidGroup;
    }

    private RaidGroup addDps(int nrOfDps, RaidGroup raidGroup) {
        for (int i = 0; i < nrOfDps; i++) {
            raidGroup.add(new Dps(i, 200, true));
        }
        return raidGroup;
    }
}
