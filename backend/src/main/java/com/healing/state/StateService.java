package com.healing.state;

import com.healing.gamelogic.Game;
import com.healing.state.model.StateModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StateService {
  private Game game;

  @Autowired
  public StateService(Game game) {
    this.game = game;
  }

  public StateModel getState() {
    var boss = game.getCurrentBoss();
    var raidGroup = game.getRaidGroup();

    return new StateModel(boss, raidGroup);
  }
}
