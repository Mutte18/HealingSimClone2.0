package com.healing.gamelogic;

import com.healing.entity.Entity;
import com.healing.entity.Player;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

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
}
