package com.healing.gamelogic;

import com.healing.entity.Entity;
import com.healing.entity.Player;

import java.util.Optional;

public class RaiderHandler {
    private RaidGroup raidGroup;

    public RaiderHandler() {
        this.raidGroup = new RaidGroup();
    }

    public void resetRaidGroup() {
        this.raidGroup = new RaidGroup();
    }


    public Optional<Entity> getRaiderById(int id) {
        return raidGroup.stream().filter(raider -> raider.getId() == id).findAny();
    }

    public Player getPlayer() {
        var result = raidGroup.stream().filter(player -> player.getClass() == Player.class).findAny();
        if (result.isPresent()) {
            return (Player) result;
        }
    }
}
