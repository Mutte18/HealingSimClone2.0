package com.healing.state;

import com.healing.entity.Boss;
import com.healing.gamelogic.BossHandler;
import com.healing.gamelogic.RaidGroup;
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

    printRaidGroupState(raidGroup);
    System.out.println();
    printBossState(boss);

  }

  private void printBossState(Boss boss) {
    System.out.println("***Boss***");
    System.out.println("{ " + boss.getName() + " HP: " + boss.getHealth() + "/" + boss.getMaxHealth() + " - " + getAliveText(boss.isAlive()) + " }");
  }

  private void printRaidGroupState(RaidGroup raidGroup) {
    System.out.print("***Raiders***");
    for (int i = 0; i < raidGroup.size(); i++) {
      var raider = raidGroup.get(i);
      if (i % 4 == 0) {
        System.out.println();
      }
      System.out.print(" [ " + raider.getRole() + raider.getId() +
              " HP: " + raider.getHealth() + "/" + raider.getMaxHealth() + " - " + getAliveText(raider.isAlive()) + " ] ");
    }
  }

  private String getAliveText(Boolean isAlive) {
    return isAlive ? "Alive" : "DEAD";
  }
}
