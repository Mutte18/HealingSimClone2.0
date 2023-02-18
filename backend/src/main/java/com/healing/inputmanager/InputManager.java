package com.healing.inputmanager;

import com.healing.state.StateService;
import java.awt.event.KeyEvent;

public class InputManager implements java.awt.event.KeyListener {
  private final StateService stateService;

  public InputManager(StateService stateService) {
    this.stateService = stateService;
  }

  @Override
  public void keyTyped(KeyEvent e) {}

  @Override
  public void keyPressed(KeyEvent e) {
    switch (e.getKeyCode()) {
      case KeyEvent.VK_A -> stateService.printState("all");
      case KeyEvent.VK_P -> stateService.printState("player");
      case KeyEvent.VK_R -> stateService.printState("raid");
      case KeyEvent.VK_B -> stateService.printState("boss");
    }
  }

  @Override
  public void keyReleased(KeyEvent e) {}
}
