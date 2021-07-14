package com.healing.gamelogic;

import com.healing.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class RaiderHandler {
    private ArrayList<Entity> raiders;

    @Autowired
    RaiderHandler() {
        this.raiders = new ArrayList<>();
    }

    public ArrayList<Entity> clearRaid() {
        this.raiders.clear();
        return this.raiders;
    }

    ArrayList<Entity> createRaid() {
        int nrOfTanks = 2;
        int nrOfHealers = 4;
        int raidSize = 20;
        int nrOfDps = raidSize - nrOfHealers - nrOfTanks - 1;

        createTanks(nrOfTanks);
        createHealers(nrOfHealers);
        createDps(nrOfDps);
        createPlayer();
        return raiders;
    }

    private ArrayList<Entity> createPlayer() {
        raiders.add(new Player(0, 500, true, 500));
        return raiders;
    }
    private ArrayList<Entity> createTanks(int nrOfTanks) {
        for (int i = 0; i < nrOfTanks; i++) {
            raiders.add(new Tank(i, 2000, true));
        }
        return raiders;
    }
    private ArrayList<Entity> createHealers(int nrOfHealers) {
        for (int i = 0; i < nrOfHealers; i++) {
            raiders.add(new Healer(i, 400, true));
        }
        return raiders;
    }
    private ArrayList<Entity> createDps(int nrOfDps) {
        for (int i = 0; i < nrOfDps; i++) {
            raiders.add(new Dps(i, 200, true));
        }
        return raiders;
    }

    public Player getPlayer() {
        Optional<Player> player = this.raiders.stream()
                .filter(raider -> raider instanceof Player)
                .map(raider -> (Player) raider).findFirst();
        return player.orElse(null);
    }
}
