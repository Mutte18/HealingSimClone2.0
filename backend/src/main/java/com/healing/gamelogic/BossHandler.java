package com.healing.gamelogic;

import com.healing.entity.Boss;
import com.healing.entity.Entity;
import com.healing.entity.attacks.MeleeSwing;
import com.healing.entity.attacks.specials.MassPyroblast;
import com.healing.gamelogic.actions.BossAction;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BossHandler {
  private final ArrayList<Boss> bosses;
  private Boss currentBoss;
  private final RaiderHandler raiderHandler;

  public BossHandler(RaiderHandler raiderHandler) {
    bosses = new ArrayList<>();
    this.raiderHandler = raiderHandler;
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
        new MeleeSwing(25),
        "0");
  }

  public BossAction createBossSpecialAttackAction() {
    var specialAttack = new MassPyroblast();
    return new BossAction(currentBoss, raiderHandler.getTargets(specialAttack.getMaxTargets()), specialAttack, "0");
  }
}
