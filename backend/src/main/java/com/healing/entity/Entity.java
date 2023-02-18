package com.healing.entity;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.healing.buff.Buff;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
  @JsonSubTypes.Type(value = Boss.class, name = "boss"),
  @JsonSubTypes.Type(value = Dps.class, name = "dps"),
  @JsonSubTypes.Type(value = Healer.class, name = "healer"),
  @JsonSubTypes.Type(value = Player.class, name = "player"),
  @JsonSubTypes.Type(value = Tank.class, name = "tank")
})
public abstract class Entity {
  private int id;
  private int health;
  private int maxHealth;
  private boolean alive;
  private EntityRole role;
  private List<Buff> buffs;

  public Entity(int id, int health, boolean alive, EntityRole role) {
    this.id = id;
    this.health = health;
    this.maxHealth = health;
    this.alive = alive;
    this.role = role;
    this.buffs = new ArrayList<>();
  }

  public void increaseHealth(int updateHealthValue) {
    this.health += updateHealthValue;
    if (this.health > this.maxHealth) {
      this.health = this.maxHealth;
    }
  }

  public void reduceHealth(int updateHealthValue) {
    this.health -= updateHealthValue;
    if (this.health <= 0) {
      this.setAlive(false);
      this.health = 0;
    }
  }
}
