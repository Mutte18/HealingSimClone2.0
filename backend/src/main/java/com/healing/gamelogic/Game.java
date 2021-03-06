package com.healing.gamelogic;

import com.healing.gamelogic.actions.Action;
import com.healing.gamelogic.actions.PlayerAction;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Game implements Runnable {
    private static final long DELAY_PERIOD = 17;
    private final boolean gameRunning = true;
    private final RaiderHandler raiderHandler;
    private final ActionsQueue actionsQueue;

    @Autowired
    public Game(ActionsQueue actionsQueue, RaiderHandler raiderHandler) {
        this.raiderHandler = raiderHandler;
        this.actionsQueue = actionsQueue;
        restartGame();
    }

    private void restartGame() {
        raiderHandler.resetRaidGroup();
    }

    @SneakyThrows
    private void gameLoop() {
        while (this.gameRunning) {
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

    private void spellCastEventListener() {
        // Listen to incoming spell events
        // Add to action processing queue
    }

    void processActionQueue() {
        var optionalAction = actionsQueue.getTopActionAndRemoveFromQueue();
        // Do stuff with action
        optionalAction.ifPresent(this::performAction);
        // Every X timeunit, go through action queue and perform the actions
        // Then send state update to frontend
    }

    void performAction(Action action) {
        if (action instanceof PlayerAction) {
            performPlayerAction((PlayerAction) action);
        }
    }

    void performPlayerAction(PlayerAction action) {
        var player = action.getPlayer();
        var target = raiderHandler.getRaiderById(action.getTarget().getId());
        var spell = action.getSpell();

        if (target.isPresent()) {
            player.reduceMana(spell.getManaCost());
            target.get().increaseHealth(spell.getHealAmount());
        }
    }
}
