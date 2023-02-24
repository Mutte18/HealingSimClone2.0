package com.healing.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Dps extends Entity {
  @Builder
  public Dps(int id, int health, boolean alive) {
    super(id, health, alive, EntityRole.DPS);
  }

  @Override
  public void tick(Integer secondsElapsed) {}
}
