package com.healing.gamelogic;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.healing.entity.Entity;
import org.springframework.stereotype.Component;

@Component
public class BuffHandler {
  private final RaiderHandler raiderHandler;
  private final BossHandler bossHandler;
  private final ActionsQueue actionsQueue;

  public BuffHandler(RaiderHandler raiderHandler, BossHandler bossHandler, ActionsQueue actionsQueue) {
    this.raiderHandler = raiderHandler;
    this.bossHandler = bossHandler;
    this.actionsQueue = actionsQueue;
  }

  public void processBuffs(double tenthOfSeconds) {
    var raiders = raiderHandler.getAliveRaiders();
    var boss = bossHandler.getCurrentBoss();
      var entities = new ArrayList<>(raiders);
    entities.add(boss);
    entities.forEach(
        entity -> entity
        .getBuffs()
        .forEach(
            buff -> {
              buff.addAction(entity, actionsQueue);
              buff.tick(tenthOfSeconds);
            }));
  }

  public void cleanUpExpiredBuffs() {
      var raiders = raiderHandler.getRaidGroup();
      var boss = bossHandler.getCurrentBoss();
      var entities = new ArrayList<>(raiders);
    entities
        .forEach(
            entity -> {
              if (!entity.getBuffs().isEmpty()) {
                  entity.setBuffs(
                          entity.getBuffs().stream()
                        .filter(buff -> !buff.isExpired())
                        .collect(Collectors.toList()));
              }
            }

            );
  }
}
