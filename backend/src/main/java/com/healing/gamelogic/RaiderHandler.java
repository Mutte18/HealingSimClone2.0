package com.healing.gamelogic;

import com.healing.entity.*;
import com.healing.entity.attacks.MeleeSwing;
import com.healing.gamelogic.actions.NPCAction;
import com.healing.gamelogic.actions.NPCHealerAction;
import java.util.*;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class RaiderHandler {
  private RaidGroup raidGroup;

  public RaiderHandler() {}

  public void resetRaidGroup() {
    this.raidGroup = new RaidGroup();
    this.raidGroup.fillRaidGroup();
  }

  public Optional<Entity> getRaiderById(String id) {
    return raidGroup.stream().filter(raider -> raider.getId().equals(id)).findAny();
  }

  public Player getPlayer() {
    return (Player)
        raidGroup.stream()
            .filter(player -> player.getRole().equals(EntityRole.PLAYER))
            .findAny()
            .orElse(null);
  }

  public RaidGroup getRaidGroup() {
    return this.raidGroup;
  }

  public List<Entity> getRaidersOfType(EntityRole role) {
    return raidGroup.stream()
        .filter(raider -> raider.getRole().equals(role))
        .collect(Collectors.toList());
  }

  public List<Entity> getAliveRaiders() {
    return raidGroup.stream().filter(Entity::isAlive).collect(Collectors.toList());
  }

  public Optional<Entity> getNewTarget() {
    var tankTarget =
        raidGroup.stream()
            .filter(raider -> raider.getRole().equals(EntityRole.TANK) && raider.isAlive())
            .findAny();
    if (tankTarget.isPresent()) {
      return tankTarget;
    } else {
      var randomTarget = raidGroup.stream().filter(Entity::isAlive).findFirst();
      if (randomTarget.isPresent()) {
        return randomTarget;
      }
    }
    return Optional.empty();
  }

  public List<Entity> getTargets(Integer nrOfTargets) {
    return raidGroup.stream()
        .filter(raider -> !raider.getRole().equals(EntityRole.TANK) && raider.isAlive())
        .limit(nrOfTargets)
        .collect(Collectors.toList());
  }

  public NPCAction createDPSAutoAttackAction(Dps dps, Boss currentBoss) {
    return new NPCAction(dps, new ArrayList<>(List.of(currentBoss)), new MeleeSwing(5), "0");
  }

  public NPCHealerAction createHealerAutoHealAction(Healer healer) {
    return new NPCHealerAction(healer, getAliveRaiders(), "0");
  }

  void killAllRaiders() {
    raidGroup.forEach(raider -> raider.reduceHealth(999999));
  }

  public List<Entity> getLeastHealthyRaiders(Entity initialTarget, Integer desiredTargets) {
    var damagedRaiders =
        raidGroup.stream()
            .filter(
                raider ->
                    raider.isAlive()
                        && !raider.getId().equals(initialTarget.getId())
                        && !raider.getRole().equals(EntityRole.TANK)
                        && raider.getHealth() != raider.getMaxHealth())
            .sorted(Comparator.comparingInt(Entity::getHpInPercent))
            .toList();

    return damagedRaiders.stream().limit(desiredTargets).collect(Collectors.toList());
  }
}
