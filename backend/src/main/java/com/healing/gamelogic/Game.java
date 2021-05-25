package com.healing.gamelogic;

import com.healing.entity.Entity;
import lombok.SneakyThrows;

import java.util.ArrayList;

public class Game implements Runnable {
    private boolean gameRunning = true;
    private ArrayList<Entity> raidersList;
    private RaiderHandler raiderHandler;
    private static final long DELAY_PERIOD = 17;
    public Game() {
        raiderHandler = new RaiderHandler();
        restartGame();


    }

    private void restartGame() {
        raidersList = raiderHandler.createRaid();
    }

    @SneakyThrows
    private void gameLoop() {
        while(this.gameRunning) {
            long beginTime = System.currentTimeMillis();

            long timeTaken = System.currentTimeMillis() - beginTime;
            long sleepTime = DELAY_PERIOD - timeTaken;
            if (sleepTime >= 0) {
                Thread.sleep(sleepTime);
            }
        }
    }

    @Override
    public void run() {
        System.out.println(raiderHandler);
        gameLoop();
    }

    public void printRaidersListDownwards() {
        raidersList.forEach(raider -> {
            System.out.println("-------------------");
            System.out.println("TYPE: " + raider.getClass().getSimpleName().toUpperCase());
            System.out.println("HEALTH: " + raider.getHealth() + " / " + raider.getMaxHealth());
            System.out.println("ALIVE: " + raider.isAlive());
        });
    }

    public void setEntitiesHealthTo50Percent() {
        raidersList.forEach(raider -> raider.setHealth(raider.getHealth() / 2));
    }

    public void setEntitiesHealthTo0() {
        raidersList.forEach(raider -> raider.reduceHealth(raider.getMaxHealth()));
    }
}
