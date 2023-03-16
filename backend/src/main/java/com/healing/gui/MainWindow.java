package com.healing.gui;

import com.healing.RoundingHelper;
import com.healing.buff.Buff;
import com.healing.entity.Entity;
import com.healing.inputmanager.InputManager;
import com.healing.state.StateService;
import java.awt.*;
import javax.swing.*;

public class MainWindow extends JFrame {
  private final StateService stateService;

  public MainWindow(StateService stateService) {
    this.stateService = stateService;
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setBounds(300, 300, 1600, 900);
    // this.setSize(1600, 900);

    // this.setLayout(new BorderLayout());
    this.setVisible(true);

    new Thread(() -> this.addKeyListener(new InputManager(stateService, this))).start();
  }

  public void showUI() {
    var pane = this.getContentPane();

    // boss
    var boss = stateService.getState().getBoss();
    var bossPanel = new JPanel();
    bossPanel.setLayout(new BoxLayout(bossPanel, BoxLayout.Y_AXIS));
    var bossName = new JLabel(boss.getName());
    var bossHpBar = new JPanel();
    var bossHpBarText = new JLabel("HP: " + boss.getHealth() + " / " + boss.getMaxHealth());
    bossHpBar.add(bossHpBarText);
    bossHpBar.setBackground(Color.green);

    bossPanel.add(bossName);
    bossPanel.add(bossHpBar);

    // raid
    var raid = stateService.getState().getRaidGroup();
    var raidPanel = new JPanel();
    raidPanel.setLayout(new GridLayout(5, 4));
    for (Entity raider : raid) {
      var raidFrame = new JPanel();
      raidFrame.setLayout(new BoxLayout(raidFrame, BoxLayout.Y_AXIS));

      // Name
      var label = new JLabel(raider.getId());
      label.setAlignmentX(Component.CENTER_ALIGNMENT);
      label.setAlignmentY(Component.CENTER_ALIGNMENT);
      raidFrame.add(label);

      // HP
      var hpText =
          raider.isAlive() ? "HP: " + raider.getHealth() + " / " + raider.getMaxHealth() : "DEAD";
      label = new JLabel(hpText);
      label.setAlignmentX(Component.CENTER_ALIGNMENT);
      label.setAlignmentY(Component.CENTER_ALIGNMENT);
      raidFrame.add(label);

      // Buffs
      var buffs = raider.getBuffs();
      for (Buff buff : buffs) {
        label = new JLabel("Buffs: " + buff.getName() + " Duration: " + RoundingHelper.roundToOneDecimal(buff.getRemainingDuration()));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setAlignmentY(Component.CENTER_ALIGNMENT);
        raidFrame.add(label);
      }

      raidFrame.setBackground(Color.white);
      raidFrame.setBorder(BorderFactory.createLineBorder(Color.black));

      raidPanel.add(raidFrame);
    }

    // Mana bar
    var player = stateService.getRaiderHandler().getPlayer();
    var manaBar = new JPanel();
    var text = new JPanel();
    text.setLayout(new BoxLayout(text, BoxLayout.Y_AXIS));
    var label = new JLabel("Mana: " + player.getMana() + " / " + player.getMaxMana());
    text.add(label);
    label = new JLabel("Regen rate: " + player.getManaRegenAmount() + " mana every " + player.getManaRegenTickInterval() + " sec");
    text.add(label);
    manaBar.setBackground(Color.blue);
    manaBar.add(text);

    pane.add(bossPanel, BorderLayout.NORTH);
    pane.add(raidPanel, BorderLayout.CENTER);
    pane.add(manaBar, BorderLayout.SOUTH);
    this.setVisible(true);
  }

  /*public void paint(Graphics g) {
    g.setColor(Color.red);
    g.fillOval(100, 100, 200, 200);

  }*/
}
