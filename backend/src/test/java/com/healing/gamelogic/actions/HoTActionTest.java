package com.healing.gamelogic.actions;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.healing.buff.Buff;
import com.healing.buff.buffs.RenewBuff;
import com.healing.buff.buffs.RiptideBuff;
import com.healing.entity.Dps;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class HoTActionTest {

  @ParameterizedTest
  @MethodSource("hots")
  void hotsShouldIncreaseHealthWhenProcessed(Buff buff) {
    var target = new Dps(0, 100, true);
    target.setHealth(10);
    var healthBefore = target.getHealth();
    var hoTAction = new HoTAction(target, buff.getHealingAmount(), buff);

    hoTAction.performAction();
    assertEquals(healthBefore + buff.getHealingAmount(), target.getHealth());
  }

  private static Stream<Arguments> hots() {
    return Stream.of(Arguments.of(new RenewBuff()), Arguments.of(new RiptideBuff()));
  }
}
