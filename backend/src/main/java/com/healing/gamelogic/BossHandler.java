package com.healing.gamelogic;

import com.healing.entity.Boss;
import com.healing.entity.Entity;
import com.healing.entity.attacks.MeleeSwing;
import com.healing.gamelogic.actions.BossAction;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class BossHandler {
  private final ArrayList<Boss> bosses;
  private Boss currentBoss;

  public BossHandler() {
    bosses = new ArrayList<>();
  }

  public Boss getCurrentBoss() {
    return currentBoss;
  }

  public void createNewBoss(Boss boss) {
    bosses.add(boss);
    currentBoss = boss;
  }

  public void setNewTarget(Entity target) {
    currentBoss.setCurrentTarget(target);
  }

  public BossAction createBossAutoAttackAction() {
    return new BossAction(
        currentBoss,
        new ArrayList<>(List.of(currentBoss.getCurrentTarget())),
        new MeleeSwing("Melee Swing", 25),
        "0");
  }
}
