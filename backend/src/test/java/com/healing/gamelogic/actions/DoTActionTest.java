package com.healing.gamelogic.actions;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.healing.buff.Buff;
import com.healing.buff.debuffs.BurningDebuff;
import com.healing.entity.Dps;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class DoTActionTest {

  @ParameterizedTest
  @MethodSource("dots")
  void dotsShouldReduceHealthWhenProcessed(Buff buff) {
    var target = new Dps(0, 100, true);
    var healthBefore = target.getHealth();
    var dotAction = new DoTAction(target, buff.getDamageAmount(), buff);

    dotAction.performAction();
    assertEquals(healthBefore - buff.getDamageAmount(), target.getHealth());
  }

  private static Stream<Arguments> dots() {
    return Stream.of(Arguments.of(new BurningDebuff()));
  }
}
