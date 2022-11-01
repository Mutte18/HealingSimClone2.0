package com.healing.gamelogic;

import com.healing.entity.Entity;
import com.healing.entity.Player;
import java.util.Optional;
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
}
