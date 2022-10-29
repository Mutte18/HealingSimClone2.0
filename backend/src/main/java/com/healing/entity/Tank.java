package com.healing.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Tank extends Entity {
  public Tank(int id, int health, boolean alive) {
    super(id, health, alive);
  }
}
