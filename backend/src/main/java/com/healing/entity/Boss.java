package com.healing.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Boss extends Entity {
  private String name;
  private Entity currentTarget;

  @Builder
  public Boss(int id, int health, boolean alive, String name) {
    super(id, health, alive, EntityRole.BOSS);
    this.name = name;
  }

  @Override
  public void tick(Double tenthOfSeconds) {}
}
