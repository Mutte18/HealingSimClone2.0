package com.healing.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Boss extends Entity {
  private String name;

  public Boss(int id, int health, boolean alive, String name) {
    super(id, health, alive, "BOSS");
    this.name = name;
  }
}
