package com.healing.inputmanager;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.healing.spell.spellcast.request.SpellCastRequest;
import com.healing.state.StateService;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import lombok.SneakyThrows;

public class InputManager implements java.awt.event.KeyListener {
  private final StateService stateService;

  public InputManager(StateService stateService) {
    this.stateService = stateService;
  }

  @Override
  public void keyTyped(KeyEvent e) {}

  @SneakyThrows
  @Override
  public void keyPressed(KeyEvent e) {
    switch (e.getKeyCode()) {
      case KeyEvent.VK_A -> stateService.printState("all");
      case KeyEvent.VK_P -> stateService.printState("player");
      case KeyEvent.VK_R -> stateService.printState("raid");
      case KeyEvent.VK_B -> stateService.printState("boss");
      case KeyEvent.VK_G -> castSpell("3", "PLAYER0");
      case KeyEvent.VK_H -> castSpell("1", "DPS0");
    }
  }

  @Override
  public void keyReleased(KeyEvent e) {}

  private void castSpell(String spellId, String target) throws JsonProcessingException {
    var spellCastJson =
        new ObjectMapper().writeValueAsString(new SpellCastRequest(spellId, target));
    HttpClient httpClient = HttpClient.newHttpClient();
    HttpRequest request =
        HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:8080/spellcasting"))
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
}
