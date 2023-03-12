package com.healing.gamelogic.actions;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.healing.entity.Dps;
import com.healing.entity.Healer;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

public class NPCHealerActionTest {
  @Test
  void shouldIncreaseTargetHealthWithHealerAction() {
    var healer = new Healer(0, 100, true);
    var targetToBeHealed = new Dps(0, 100, true);
    targetToBeHealed.reduceHealth(50);
    var targetHealthBefore = targetToBeHealed.getHealth();
    var action = new NPCHealerAction(healer, new ArrayList<>(List.of(targetToBeHealed)), "1");
    action.performAction();

    assertEquals(targetHealthBefore + action.getHealAmount(), targetToBeHealed.getHealth());
  }
}
