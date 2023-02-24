package com.healing.gamelogic;

import com.healing.entity.Boss;
import com.healing.entity.EntityRole;
import com.healing.gamelogic.actions.Action;
import com.healing.gamelogic.actions.BossAction;
import com.healing.gamelogic.actions.NPCAction;
import com.healing.gamelogic.actions.NPCHealerAction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

public class GameLoopHelperTest {
    private GameLoopHelper gameLoopHelper;
    private RaiderHandler raiderHandler;
    private BossHandler bossHandler;
    private ActionsQueue actionsQueue;

    @BeforeEach
    void setup() {
        raiderHandler = new RaiderHandler();
        raiderHandler.resetRaidGroup();
        bossHandler = new BossHandler(raiderHandler);
        bossHandler.createNewBoss(new Boss(0, 1000, true, "Defias Pillager"));
        actionsQueue = new ActionsQueue();
        gameLoopHelper = new GameLoopHelper(actionsQueue, bossHandler, raiderHandler);
    }

    @Test
    void shouldAddDpsAutoAttackActionEveryFiveSeconds() {
        gameLoopHelper.incrementSecondsElapsed(5);

        var numberOfDps = raiderHandler.getRaidersOfType(EntityRole.DPS);
        var dpsActions = getActionsOfType(NPCAction.class);
        Assertions.assertEquals(numberOfDps.size(), dpsActions.size());
    }

    @Test
    void shouldAddHealerAutoHealActionEveryFiveSeconds() {
        gameLoopHelper.incrementSecondsElapsed(5);

        var numberOfHealers = raiderHandler.getRaidersOfType(EntityRole.HEALER);
        var healerActions = getActionsOfType(NPCHealerAction.class);
        Assertions.assertEquals(numberOfHealers.size(), healerActions.size());
    }

    @Test
    void shouldAddBossAutoAttackEveryTwoSeconds() {
        gameLoopHelper.incrementSecondsElapsed(2);

        var bossActions = getActionsOfType(BossAction.class);
        Assertions.assertEquals(1, bossActions.size());
    }

    @Test
    void shouldAddBossSpecialAttackEveryTenSeconds() {
        gameLoopHelper.incrementSecondsElapsed(10);

        var bossActions = getActionsOfType(BossAction.class);
        Assertions.assertEquals(0, bossActions.size());
    }

    private List<Action> getActionsOfType(Class actionTypeToFind) {
        return actionsQueue.stream().filter(action -> action.getClass().equals(actionTypeToFind)).collect(Collectors.toList());
    }
}
