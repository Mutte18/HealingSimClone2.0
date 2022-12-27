package com.healing.entity;

public class Healer extends Entity {
  public Healer(int id, int health, boolean alive) {
    super(id, health, alive, EntityRole.HEALER);
  }
}
