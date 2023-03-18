package com.healing.entity;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.healing.buff.Buff;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
  private String id;
  private int health;
  private int maxHealth;
  private boolean alive;
  private EntityRole role;
  private List<Buff> buffs;

  public Entity(int id, int health, boolean alive, EntityRole role) {
    this.id = role.name() + id;
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
      getBuffs().clear();
    }
  }

  public abstract void tick(Double tenthOfSeconds);

  public int getHpInPercent() {
    double hp = health;
    double maxHp = maxHealth;
    return (int) ((hp / maxHp) * 100);
  }

  public void killEntity() {
    reduceHealth(maxHealth);
  }

  public void addBuff(Buff buff) {
    var existingBuff = isAlreadyAffectedByBuff(buff);
    existingBuff.ifPresent(this::removeBuff);
    this.buffs.add(buff);
  }

  public void removeBuff(Buff buff) {
    this.buffs.remove(buff);
  }

  private Optional<Buff> isAlreadyAffectedByBuff(Buff newBuff) {
    var existingBuffs = getBuffs();
    return existingBuffs.stream()
        .filter(oldBuff -> oldBuff.getName().equals(newBuff.getName()))
        .findAny();
  }
}
