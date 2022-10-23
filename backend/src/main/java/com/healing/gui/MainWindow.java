package com.healing.gui;

import com.healing.gamelogic.Game;
import com.healing.inputmanager.InputManager;
import javax.swing.*;

public class MainWindow extends JFrame {
  public MainWindow(Game game) {
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setSize(300, 300);
    this.setVisible(true);
    this.addKeyListener(new InputManager(game));
  }
}
