package com.healing.gui;

import com.healing.inputmanager.InputManager;
import com.healing.state.StateService;
import javax.swing.*;

public class MainWindow extends JFrame {
  public MainWindow(StateService stateService) {
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setSize(300, 300);
    this.setVisible(true);
    this.addKeyListener(new InputManager(stateService));
  }
}
