package com.healing.inputmanager;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.healing.gui.MainWindow;
import com.healing.spell.spellbook.*;
import com.healing.spell.spellcast.request.SpellCastRequest;
import com.healing.state.StateService;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Set;
import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JRootPane;
import javax.swing.KeyStroke;
import lombok.SneakyThrows;

public class InputManager {
  private final StateService stateService;
  private final MainWindow mainWindow;

  public InputManager(StateService stateService, MainWindow mainWindow) {
    this.stateService = stateService;
    this.mainWindow = mainWindow;
  }

  public void registerKeyBindings(JRootPane rootPane) {
    int[] keyCodes = {
      KeyEvent.VK_A, KeyEvent.VK_P, KeyEvent.VK_R, KeyEvent.VK_B,
      KeyEvent.VK_1, KeyEvent.VK_2, KeyEvent.VK_3, KeyEvent.VK_4,
      KeyEvent.VK_5, KeyEvent.VK_6, KeyEvent.VK_7, KeyEvent.VK_X,
      KeyEvent.VK_Z, KeyEvent.VK_M, KeyEvent.VK_N, KeyEvent.VK_F
    };

    for (int keyCode : keyCodes) {
      String actionName = "keyboard-action-" + keyCode;
      rootPane
          .getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
          .put(KeyStroke.getKeyStroke(keyCode, 0), actionName);
      rootPane
          .getActionMap()
          .put(
              actionName,
              new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent event) {
                  keyPressed(
                      new KeyEvent(
                          mainWindow,
                          KeyEvent.KEY_PRESSED,
                          System.currentTimeMillis(),
                          0,
                          keyCode,
                          KeyEvent.CHAR_UNDEFINED));
                }
              });
    }
  }

  @SneakyThrows
  public void keyPressed(KeyEvent e) {
    var currentTarget = stateService.getBossHandler().getCurrentBoss().getCurrentTarget();
    var targetId = currentTarget != null ? currentTarget.getId() : "PLAYER0";
    switch (e.getKeyCode()) {
      case KeyEvent.VK_A -> stateService.printState("all");
      case KeyEvent.VK_P -> stateService.printState("player");
      case KeyEvent.VK_R -> stateService.printState("raid");
      case KeyEvent.VK_B -> stateService.printState("boss");
      case KeyEvent.VK_1 -> castSpell(new FlashHeal().getSpellId(), targetId);
      case KeyEvent.VK_2 -> castSpell(new Renew().getSpellId(), targetId);
      case KeyEvent.VK_3 -> castSpell(new ChainHeal().getSpellId(), targetId);
      case KeyEvent.VK_4 -> castSpell(new Riptide().getSpellId(), targetId);
      case KeyEvent.VK_5 -> castSpell(new HolyShock().getSpellId(), targetId);
      case KeyEvent.VK_6 -> castSpell(new HolyNova().getSpellId(), targetId);
      case KeyEvent.VK_7 -> castSpell(new MassRenew().getSpellId(), targetId);
      case KeyEvent.VK_X -> cancelSpellCast();
      case KeyEvent.VK_Z -> resetGame();
      case KeyEvent.VK_M -> pauseGame();
      case KeyEvent.VK_N -> {
        Set<Thread> threads = Thread.getAllStackTraces().keySet();
        System.out.printf(
            "%-15s \t %-15s \t %-15s \t %s\n", "Name", "State", "Priority", "isDaemon");
        for (Thread t : threads) {
          System.out.printf(
              "%-15s \t %-15s \t %-15d \t %s\n",
              t.getName(), t.getState(), t.getPriority(), t.isDaemon());
        }
      }
      case KeyEvent.VK_F -> mainWindow.showUI();
    }
  }

  private void castSpell(String spellId, String target) throws JsonProcessingException {
    var spellCastJson =
        new ObjectMapper().writeValueAsString(new SpellCastRequest(spellId, target));
    HttpClient httpClient = HttpClient.newHttpClient();
    HttpRequest request =
        HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:8090/spellcasting"))
            .POST(HttpRequest.BodyPublishers.ofString(spellCastJson))
            .header("Content-Type", "application/json")
            .build();
    try {
      httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    } catch (IOException ex) {
      ex.printStackTrace();
    } catch (InterruptedException ex) {
      ex.printStackTrace();
    }
  }

  private void cancelSpellCast() {
    HttpClient httpClient = HttpClient.newHttpClient();
    HttpRequest request =
        HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:8090/spellcasting/cancel"))
            .POST(HttpRequest.BodyPublishers.ofString(""))
            .header("Content-Type", "application/json")
            .build();
    try {
      httpClient.send(request, HttpResponse.BodyHandlers.ofString());
      System.out.println("GGs");
    } catch (IOException ex) {
      ex.printStackTrace();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  private void resetGame() {
    HttpClient httpClient = HttpClient.newHttpClient();
    HttpRequest request =
        HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:8090/game/reset"))
            .POST(HttpRequest.BodyPublishers.ofString(""))
            .header("Content-Type", "application/json")
            .build();
    try {
      httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    } catch (IOException | InterruptedException ex) {
      ex.printStackTrace();
    }
  }

  private void pauseGame() {
    HttpClient httpClient = HttpClient.newHttpClient();
    HttpRequest request =
        HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:8090/game/pause"))
            .POST(HttpRequest.BodyPublishers.ofString(""))
            .header("Content-Type", "application/json")
            .build();
    try {
      httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    } catch (IOException | InterruptedException ex) {
      ex.printStackTrace();
    }
  }
}
