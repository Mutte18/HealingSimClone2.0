package com.healing.gamelogic;

import com.healing.entity.Boss;
import com.healing.entity.Entity;
import com.healing.entity.attacks.MeleeSwing;
import com.healing.entity.attacks.specials.MassPyroblast;
import com.healing.gamelogic.actions.ActionType;
import com.healing.gamelogic.actions.BossAction;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Component;

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

  public void resetBosses() {
    createNewBoss(new Boss(0, 1000, true, "Defias Pillager"));
  }

  public void createNewBoss(Boss boss) {
    bosses.add(boss);
    currentBoss = boss;
  }

  public Optional<BossAction> createBossAutoAttackAction() {
    setBossFocusTarget();
    if (currentBoss.getCurrentTarget() != null) {
      return Optional.of(
          new BossAction(
              currentBoss,
              new ArrayList<>(List.of(currentBoss.getCurrentTarget())),
              new MeleeSwing(25),
              "0",
              ActionType.NORMAL));
    }
    return Optional.empty();
  }

  public BossAction createBossSpecialAttackAction() {
    var specialAttack = new MassPyroblast();
    return new BossAction(
        currentBoss,
        raiderHandler.getSpecialAttackTargets(specialAttack.getMaxTargets()),
        specialAttack,
        "0",
        ActionType.SPECIAL);
  }

  private void setBossFocusTarget() {
    var currentTarget = currentBoss.getCurrentTarget();
    if (currentTarget == null || !currentTarget.isAlive()) {
      var newTarget = raiderHandler.getNewTarget();
      newTarget.ifPresentOrElse(this::setNewTarget, () -> System.out.println("GAME OVER"));
    }
  }

  private void setNewTarget(Entity target) {
    currentBoss.setCurrentTarget(target);
  }
}
