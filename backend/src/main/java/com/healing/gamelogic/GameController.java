package com.healing.gamelogic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("game")
public class GameController {
  private final Game game;

  @Autowired
  public GameController(Game game) {
    this.game = game;
  }

  @PostMapping("/reset")
  public ResponseEntity<String> resetGame() throws InterruptedException {
    this.game.resetGame();
    System.err.println("GAME HAS BEEN RESET!");
    return ResponseEntity.ok().body("Game has been reset!");
  }
}
