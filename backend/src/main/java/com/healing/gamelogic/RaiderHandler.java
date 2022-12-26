package com.healing.gamelogic;

import com.healing.entity.Entity;
import com.healing.entity.Player;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class RaiderHandler {
  private RaidGroup raidGroup;

  public RaiderHandler() {}

  public void resetRaidGroup() {
    this.raidGroup = new RaidGroup();
    this.raidGroup.fillRaidGroup();
  }

  public Optional<Entity> getRaiderById(int id) {
    return raidGroup.stream().filter(raider -> raider.getId() == id).findAny();
  }

  public Optional<Entity> getPlayer() {
    return raidGroup.stream().filter(player -> player.getClass() == Player.class).findAny();
  }

  public RaidGroup getRaidGroup() {
    return this.raidGroup;
  }

  public List<Entity> getDpsers() {
    return raidGroup.stream()
        .filter(raider -> raider.getRole().equals("DPS"))
        .collect(Collectors.toList());
  }

  public List<Entity> getTanks() {
    return raidGroup.stream()
        .filter(raider -> raider.getRole().equals("TANK"))
        .collect(Collectors.toList());
  }

  public Optional<Entity> getNewTarget() {
    var tankTarget =
        raidGroup.stream()
            .filter(raider -> raider.getRole().equals("TANK") && raider.isAlive())
            .findAny();
    if (tankTarget.isPresent()) {
      return tankTarget;
    } else {
      var randomTarget = raidGroup.stream().filter(Entity::isAlive).findFirst();
      if (randomTarget.isPresent()) {
        return randomTarget;
      }
    }
    return Optional.empty();
  }

  public List<Entity> getTargets(Integer nrOfTargets) {
    return raidGroup.stream().filter(raider -> !raider.getRole().equals("TANK") && raider.isAlive()).limit(nrOfTargets).collect(Collectors.toList());
  }
}
