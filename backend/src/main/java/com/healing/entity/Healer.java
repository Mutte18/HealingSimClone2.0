package com.healing.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Healer extends Entity {
  public Healer(int id, int health, boolean alive) {
    super(id, health, alive);
  }
}
