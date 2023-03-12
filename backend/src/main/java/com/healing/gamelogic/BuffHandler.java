package com.healing.gamelogic;

import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class BuffHandler {
  private final RaiderHandler raiderHandler;
  private final ActionsQueue actionsQueue;

  public BuffHandler(RaiderHandler raiderHandler, ActionsQueue actionsQueue) {
    this.raiderHandler = raiderHandler;
    this.actionsQueue = actionsQueue;
  }

  public void processBuffs(double tenthOfSeconds) {
    var raiders = raiderHandler.getAliveRaiders();
    raiders.forEach(
        raider ->
            raider
                .getBuffs()
                .forEach(
                    buff -> {
                      buff.addAction(raider, actionsQueue);
                      buff.tick(tenthOfSeconds);
                    }));
  }

  public void cleanUpExpiredBuffs() {
    raiderHandler
        .getRaidGroup()
        .forEach(
            raider -> {
              if (!raider.getBuffs().isEmpty()) {
                raider.setBuffs(
                    raider.getBuffs().stream()
                        .filter(buff -> !buff.isExpired())
                        .collect(Collectors.toList()));
              }
            });
  }
}
