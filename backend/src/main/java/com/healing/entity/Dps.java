package com.healing.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Dps extends Entity {
  public Dps(int id, int health, boolean alive) {
    super(id, health, alive, "DPS");
  }
}
