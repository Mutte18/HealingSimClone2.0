package com.healing.gamelogic;

import com.healing.entity.Boss;
import com.healing.entity.EntityRole;
import com.healing.gamelogic.actions.*;
import com.healing.spell.spellbook.SpellBook;
import com.healing.spell.spellcast.GlobalCooldownHandler;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GameLoopHelperTest {
  private GameLoopHelper gameLoopHelper;
  private RaiderHandler raiderHandler;
  private BossHandler bossHandler;
  private ActionsQueue actionsQueue;
  private SpellBook spellBook;
  private GlobalCooldownHandler globalCooldownHandler;

  @BeforeEach
  void setup() {
    raiderHandler = new RaiderHandler();
    raiderHandler.resetRaidGroup();
    bossHandler = new BossHandler(raiderHandler);
    bossHandler.createNewBoss(new Boss(0, 1000, true, "Defias Pillager"));
    actionsQueue = new ActionsQueue();
    spellBook = new SpellBook();
    globalCooldownHandler = new GlobalCooldownHandler();
    gameLoopHelper =
        new GameLoopHelper(
            actionsQueue, bossHandler, raiderHandler, spellBook, globalCooldownHandler);
  }

  @Test
  void shouldAddDpsAutoAttackActionEveryFiveSeconds() {
    gameLoopHelper.tick(5);

    var numberOfDps = raiderHandler.getRaidersOfType(EntityRole.DPS);
    var dpsActions = getActionsOfType(NPCAction.class);
    Assertions.assertEquals(numberOfDps.size(), dpsActions.size());
  }

  @Test
  void shouldNotAddDpsAutoAttackForDeadDps() {
    var numberOfDps = raiderHandler.getRaidersOfType(EntityRole.DPS);
    numberOfDps.get(0).reduceHealth(9999);
    numberOfDps.get(1).reduceHealth(9999);

    gameLoopHelper.tick(5);

    var dpsActions = getActionsOfType(NPCAction.class);
    Assertions.assertEquals(numberOfDps.size() - 2, dpsActions.size());
  }

  @Test
  void shouldAddHealerAutoHealActionEveryFiveSeconds() {
    gameLoopHelper.tick(5);

    var numberOfHealers = raiderHandler.getRaidersOfType(EntityRole.HEALER);
    var healerActions = getActionsOfType(NPCHealerAction.class);
    Assertions.assertEquals(numberOfHealers.size(), healerActions.size());
  }

  @Test
  void shouldNotAddAutoHealActionForDeadHealers() {
    var numberOfHealers = raiderHandler.getRaidersOfType(EntityRole.HEALER);
    numberOfHealers.get(0).reduceHealth(9999);
    numberOfHealers.get(1).reduceHealth(9999);
    gameLoopHelper.tick(5);

    var healerActions = getActionsOfType(NPCHealerAction.class);
    Assertions.assertEquals(numberOfHealers.size() - 2, healerActions.size());
  }

  @Test
  void shouldAddBossAutoAttackEveryTwoSeconds() {
    gameLoopHelper.tick(2);

    var bossActions = getBossActionsOfType(ActionType.NORMAL);
    Assertions.assertEquals(1, bossActions.size());
  }

  @Test
  void shouldAddBossSpecialAttackEveryTenSeconds() {
    gameLoopHelper.tick(10);

    var bossActions = getBossActionsOfType(ActionType.SPECIAL);
    Assertions.assertEquals(1, bossActions.size());
  }

  @Test
  void shouldSetGlobalCooldownToFalseEachTick() {
    globalCooldownHandler.toggleGlobalCooldown(true);
    gameLoopHelper.tick(1);

    Assertions.assertFalse(globalCooldownHandler.isOnCooldown());
  }

  private List<Action> getActionsOfType(Class actionTypeToFind) {
    return actionsQueue.stream()
        .filter(action -> action.getClass().equals(actionTypeToFind))
        .collect(Collectors.toList());
  }

  private List<Action> getBossActionsOfType(ActionType actionType) {
    return actionsQueue.stream()
        .filter(
            action ->
                action.getClass().equals(BossAction.class)
                    && ((BossAction) action).getActionType().equals(actionType))
        .collect(Collectors.toList());
  }
}
