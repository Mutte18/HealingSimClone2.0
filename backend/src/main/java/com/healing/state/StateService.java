package com.healing.state;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.healing.entity.Boss;
import com.healing.entity.EntityRole;
import com.healing.entity.Player;
import com.healing.gamelogic.BossHandler;
import com.healing.gamelogic.RaidGroup;
import com.healing.gamelogic.RaiderHandler;
import com.healing.spell.spellbook.SpellBook;
import com.healing.spell.spellcast.GlobalCooldownHandler;
import com.healing.spell.spellcast.SpellCastingHandler;
import com.healing.state.model.StateModel;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Getter
public class StateService {
  private final BossHandler bossHandler;
  private final RaiderHandler raiderHandler;
  private final SpellCastingHandler spellCastingHandler;
  private final GlobalCooldownHandler globalCooldownHandler;
  private final SpellBook spellBook;
  private final ObjectMapper mapper;

  @Autowired
  public StateService(
      BossHandler bossHandler,
      RaiderHandler raiderHandler,
      SpellCastingHandler spellCastingHandler,
      SpellBook spellBook,
      GlobalCooldownHandler globalCooldownHandler) {
    this.bossHandler = bossHandler;
    this.raiderHandler = raiderHandler;
    this.spellCastingHandler = spellCastingHandler;
    this.spellBook = spellBook;
    this.globalCooldownHandler = globalCooldownHandler;
    this.mapper = new ObjectMapper();
  }

  public StateModel getState() {
    var boss = bossHandler.getCurrentBoss();
    var raidGroup = raiderHandler.getRaidGroup();
    return new StateModel(boss, raidGroup);
  }

  public void printState(String state) {
    var raidGroup = raiderHandler.getRaidGroup();
    var boss = bossHandler.getCurrentBoss();
    var player = raiderHandler.getPlayer();

    switch (state) {
      case "all" -> {
        printRaidGroupState(raidGroup);
        System.out.println();
        printBossState(boss);
        printPlayerState(player);
      }
      case "player" -> printPlayerState(player);
      case "boss" -> printBossState(boss);
      case "raid" -> printRaidGroupState(raidGroup);
    }
  }

  public void printBossState(Boss boss) {
    System.out.println("***BOSS***");
    try {
      var json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(boss);
      System.out.println(json);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
  }

  private void printRaidGroupState(RaidGroup raidGroup) {
    System.out.print("***RAIDERS***");
    for (int i = 0; i < raidGroup.size(); i++) {
      var raider = raidGroup.get(i);
      if (i % 5 == 0) {
        System.out.println();
      }
      try {
        var json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(raider);
        System.out.println(json);
      } catch (JsonProcessingException e) {
        e.printStackTrace();
      }
    }
    printAliveRaidersCount(raidGroup);
  }

  private void printPlayerState(Player player) {
    System.out.println("***PLAYER***");
    try {
      var json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(player);
      System.out.println(json);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
  }

  private void printAliveRaidersCount(RaidGroup raidGroup) {
    var aliveRaidersCount = raiderHandler.getAliveRaiders().size();
    System.out.println("Raiders Alive: " + aliveRaidersCount + "/" + raidGroup.size());

    printAliveRoleCount(EntityRole.DPS);
    printAliveRoleCount(EntityRole.HEALER);
    printAliveRoleCount(EntityRole.TANK);
    printAliveRoleCount(EntityRole.PLAYER);
  }

  private void printAliveRoleCount(EntityRole entityRole) {
    var aliveRoleCount =
        raiderHandler.getAliveRaiders().stream()
            .filter(raider -> raider.getRole() == entityRole)
            .count();
    System.out.println(
        entityRole.toString()
            + " Alive: "
            + aliveRoleCount
            + "/"
            + raiderHandler.getRaidersOfType(entityRole).size());
  }
}
