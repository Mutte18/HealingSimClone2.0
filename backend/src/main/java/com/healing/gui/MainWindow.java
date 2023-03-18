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

    // raid
    var castingSpell = stateService.getSpellCastingHandler().getCastingSpell();
    var globalCooldownActive = stateService.getGlobalCooldownHandler().isOnCooldown();
    var topPanel = jPanelWithBorderLayout();
    var midPanel = jPanelWithBorderLayout();
    // var bottomPanel = jPanelWithBorderLayout();
    var bottomPanel = new JPanel();
    bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));

    topPanel.add(createBossFrame());
    midPanel.add(createRaidFrame());
    if (globalCooldownActive) {
      bottomPanel.add(createGlobalCooldownBar());
    }
    if (castingSpell != null) {
      bottomPanel.add(createCastBar());
    }

    bottomPanel.add(createManaBar());
    bottomPanel.add(createSpellFrame());

    pane.add(topPanel, BorderLayout.NORTH);
    pane.add(midPanel, BorderLayout.CENTER);
    pane.add(bottomPanel, BorderLayout.SOUTH);
    this.setVisible(true);
  }

  /*public void paint(Graphics g) {
    g.setColor(Color.red);
    g.fillOval(100, 100, 200, 200);

  }*/

  private JPanel jPanelWithBorderLayout() {
    var panel = new JPanel();
    panel.setLayout(new BorderLayout());
    return panel;
  }

  private JPanel createBossFrame() {
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
    return bossPanel;
  }

  private JPanel createRaidFrame() {
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
        label =
            new JLabel(
                "Buffs: "
                    + buff.getName()
                    + " Duration: "
                    + RoundingHelper.roundToOneDecimal(buff.getRemainingDuration()));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setAlignmentY(Component.CENTER_ALIGNMENT);
        raidFrame.add(label);
      }

      raidFrame.setBackground(Color.white);
      raidFrame.setBorder(BorderFactory.createLineBorder(Color.black));

      raidPanel.add(raidFrame);
    }
    return raidPanel;
  }

  private JPanel createCastBar() {
    var castTimeRemaining = stateService.getSpellCastingHandler().getCastTimeRemaining();
    var castingSpell = stateService.getSpellCastingHandler().getCastingSpell();

    var castBar = new JPanel();
    var castBarText =
        new JLabel(
            "Casting "
                + castingSpell.getName()
                + " - Cast time remaining: "
                + RoundingHelper.roundToOneDecimal(castTimeRemaining)
                + " sec");
    castBar.add(castBarText);
    castBar.setBackground(Color.yellow);
    castBar.setPreferredSize(new Dimension(50, 50));
    return castBar;
  }

  private JPanel createGlobalCooldownBar() {
    var globalCooldownRemaining = stateService.getGlobalCooldownHandler().getRemainingTime();

    var globalCooldownBar = new JPanel();
    var globalCooldownText =
        new JLabel(
            "Global cooldown: "
                + RoundingHelper.roundToOneDecimal(globalCooldownRemaining)
                + " sec");
    globalCooldownBar.add(globalCooldownText);
    globalCooldownBar.setBackground(Color.GRAY);
    globalCooldownBar.setPreferredSize(new Dimension(50, 50));
    return globalCooldownBar;
  }

  private JPanel createManaBar() {
    var player = stateService.getRaiderHandler().getPlayer();
    var manaBar = new JPanel();
    var text = new JPanel();
    text.setLayout(new BoxLayout(text, BoxLayout.Y_AXIS));
    var label = new JLabel("Mana: " + player.getMana() + " / " + player.getMaxMana());
    text.add(label);
    label =
        new JLabel(
            "Regen rate: "
                + player.getManaRegenAmount()
                + " mana every "
                + player.getManaRegenTickInterval()
                + " sec");
    text.add(label);
    manaBar.setBackground(Color.blue);
    manaBar.add(text);

    return manaBar;
  }

  private JPanel createSpellFrame() {
    var spellFrame = new JPanel();
    var spellBook = stateService.getSpellBook();
    spellBook.forEach(
        spell -> {
          var spellPanel = new JPanel();
          spellPanel.setLayout(new BoxLayout(spellPanel, BoxLayout.Y_AXIS));

          var castTimeText = spell.getCastTime() == 0 ? "Instant" : spell.getCastTime() + " sec";
          var coolDownText = spell.getCoolDownTime() + " sec";

          spellPanel.add(new JLabel(spell.getName(), SwingConstants.CENTER));
          spellPanel.add(new JLabel("Heal amount: " + spell.getHealAmount()));
          spellPanel.add(new JLabel("Cast time: " + castTimeText));
          spellPanel.add(new JLabel("Mana cost: " + spell.getManaCost()));
          if (spell.getOnCooldown()) {
            spellPanel.add(
                new JLabel("Remaining Cooldown: " + spell.getRemainingCooldown() + " sec"));
          } else {
            spellPanel.add(new JLabel("Cooldown: " + coolDownText));
          }
          spellPanel.setBackground(Color.PINK);
          spellFrame.add(spellPanel);
        });
    return spellFrame;
  }
}
