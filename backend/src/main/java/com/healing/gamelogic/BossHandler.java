package com.healing.gamelogic;

import com.healing.entity.Boss;
import java.util.ArrayList;
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
}
