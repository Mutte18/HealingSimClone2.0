package com.healing.inputmanager;

import com.healing.gamelogic.Game;

import java.awt.event.KeyEvent;

public class InputManager implements java.awt.event.KeyListener {
    private final Game game;

    public InputManager(Game game) {
        this.game = game;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_X) {
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
