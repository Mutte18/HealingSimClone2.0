package com.healing.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Entity {
    private int id;
    private int health;
    private int maxHealth;
    private boolean alive;

    public Entity(int id, int health, boolean alive) {
        this.id = id;
        this.health = health;
        this.maxHealth = health;
        this.alive = alive;
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
