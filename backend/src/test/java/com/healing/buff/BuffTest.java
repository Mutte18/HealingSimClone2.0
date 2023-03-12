package com.healing.buff;

import com.healing.entity.Dps;
import com.healing.gamelogic.ActionsQueue;
import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class BuffTest {

  @ParameterizedTest
  @MethodSource("buffs")
  void shouldOnlyReduceRemainingDurationWhenBuffNotExpired(Buff buff) {
    buff.tick(1.0);

    Assertions.assertEquals(buff.getMaxDuration() - 1, buff.getRemainingDuration());
  }

  @ParameterizedTest
  @MethodSource("buffs")
  void shouldSetExpiredWhenRemainingDurationIs0(Buff buff) {
    buff.tick(999);

    Assertions.assertTrue(buff.isExpired());
  }

  @ParameterizedTest
  @MethodSource("buffs")
  void shouldNotReduceRemainingDurationWhenAlreadyExpired(Buff buff) {
    buff.tick(buff.getMaxDuration());
    buff.tick(1);

    Assertions.assertEquals(0, buff.getRemainingDuration());
  }

  @ParameterizedTest
  @MethodSource("buffs")
  void shouldNotTriggerActionWhenRemainingDurationIsNotOnTickInterval(Buff buff) {
    var actionsQueue = new ActionsQueue();
    var dps = Dps.builder().build();
    buff.tick(2);

    buff.addAction(dps, actionsQueue);

    Assertions.assertEquals(0, actionsQueue.size());
  }

  @ParameterizedTest
  @MethodSource("buffs")
  void shouldNotTriggerActionWhenBuffIsExpired(Buff buff) {
    var actionsQueue = new ActionsQueue();
    var dps = Dps.builder().build();
    buff.tick(9999);

    buff.addAction(dps, actionsQueue);

    Assertions.assertEquals(0, actionsQueue.size());
  }

  @ParameterizedTest
  @MethodSource("buffs")
  void shouldTriggerActionWhenRemainingDurationIsOnIntervalTickAndNotExpired(Buff buff) {
    var actionsQueue = new ActionsQueue();
    var dps = Dps.builder().build();

    buff.tick(buff.getTickInterval());

    buff.addAction(dps, actionsQueue);

    Assertions.assertEquals(1, actionsQueue.size());
  }

  private static Stream<Arguments> buffs() {
    return Stream.of(Arguments.of(new RiptideBuff()), Arguments.of(new RenewBuff()));
  }
}
