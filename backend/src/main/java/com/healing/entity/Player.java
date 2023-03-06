package com.healing.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Player extends Entity {
  private int mana;
  private int maxMana;
  private double manaRegenTickInterval = 2;
  private int manaRegenAmount = 100;

  @Builder
  public Player(int id, int health, boolean alive, int mana) {
    super(id, health, alive, EntityRole.PLAYER);
    this.mana = mana;
    this.maxMana = mana;
  }

  public void increaseMana(int updateManaValue) {
    this.mana += updateManaValue;
    if (this.mana > this.maxMana) {
      this.mana = this.maxMana;
    }
  }

  public void reduceMana(int updateManaValue) {
    this.mana -= updateManaValue;
    if (this.mana <= 0) {
      this.mana = 0;
    }
  }

  @Override
  public void tick(Integer secondsElapsed) {
    if (isAlive()) {
      if (secondsElapsed % manaRegenTickInterval == 0) {
        increaseMana(manaRegenAmount);
      }
    }
  }
}
