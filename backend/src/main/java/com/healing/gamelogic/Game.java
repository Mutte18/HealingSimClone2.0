package com.healing.gamelogic;

import com.healing.entity.Entity;
import com.healing.spell.spellqueue.SpellQueue;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class Game implements Runnable {
    private boolean gameRunning = true;
    private RaiderHandler raiderHandler;
    private SpellQueue spellQueue;

    private static final long DELAY_PERIOD = 17;

    @Autowired
    public Game() {
        raiderHandler = new RaiderHandler();
        restartGame();
    }

    private void restartGame() {
        raiderHandler.resetRaidGroup();
    }

    private void processSpellQueue() {
        var spellQueueItem = spellQueue.processFirstSpellQueueItem();
        if (spellQueueItem.isPresent()) {
            // perform logic of spell
        }
    }

    @SneakyThrows
    private void gameLoop() {
        while (this.gameRunning) {
            long beginTime = System.currentTimeMillis();

            long timeTaken = System.currentTimeMillis() - beginTime;
            long sleepTime = DELAY_PERIOD - timeTaken;

            processSpellQueue();
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
        raidGroup.forEach(raider -> {
            System.out.println("-------------------");
            System.out.println("TYPE: " + raider.getClass().getSimpleName().toUpperCase());
            System.out.println("HEALTH: " + raider.getHealth() + " / " + raider.getMaxHealth());
            System.out.println("ALIVE: " + raider.isAlive());
        });
    }

    public void setEntitiesHealthTo50Percent() {
        raidGroup.forEach(raider -> raider.setHealth(raider.getHealth() / 2));
    }

    public void setEntitiesHealthTo0() {
        raidGroup.forEach(raider -> raider.reduceHealth(raider.getMaxHealth()));
    }

    private void spellCastEventListener() {
        // Listen to incoming spell events
        // Add to action processing queue
    }

    private void processActionQueue() {
        // Every X timeunit, go through action queue and perform the actions
        // Then send state update to frontend
    }
}
