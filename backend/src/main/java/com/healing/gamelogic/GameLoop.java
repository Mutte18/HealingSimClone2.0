package com.healing.gamelogic;

import com.healing.entity.Entity;

import java.util.ArrayList;

public class GameLoop {
    private boolean gameRunning = false;
    private ArrayList<Entity> raidersList;
    private RaiderHandler raiderHandler;
    public GameLoop() {
        raiderHandler = new RaiderHandler();

    }

    private void restartGame() {

    }
}
