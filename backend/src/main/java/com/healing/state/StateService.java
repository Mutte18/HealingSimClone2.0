package com.healing.state;

import com.healing.gamelogic.BossHandler;
import com.healing.gamelogic.RaiderHandler;
import com.healing.state.model.StateModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StateService {
  private final BossHandler bossHandler;
  private final RaiderHandler raiderHandler;

  @Autowired
  public StateService(BossHandler bossHandler, RaiderHandler raiderHandler) {
    this.bossHandler = bossHandler;
    this.raiderHandler = raiderHandler;
  }

  public StateModel getState() {
    var boss = bossHandler.getCurrentBoss();
    var raidGroup = raiderHandler.getRaidGroup();
    return new StateModel(boss, raidGroup);
  }

  public void printState() {
    var raidGroup = raiderHandler.getRaidGroup();
    var boss = bossHandler.getCurrentBoss();

    for (int i = 0; i < raidGroup.size(); i++) {
      if (i % 4 == 0) {
        System.out.println();
        System.out.println();
      }
      var raider = raidGroup.get(i);
      System.out.print(" || ");
      System.out.print(raider.getRole() + raider.getId() +
              "- Health: " + raider.getHealth() + " / " + raider.getMaxHealth());
    }
  }
}
