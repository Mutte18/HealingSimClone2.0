package com.healing.buff;

import com.healing.entity.Dps;
import com.healing.gamelogic.ActionsQueue;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BuffTest {

  private RenewBuff renewBuff;

  @BeforeEach
  void setup() {
    renewBuff = new RenewBuff();
  }

  @Test
  void shouldOnlyReduceRemainingDurationWhenBuffNotExpired() {
    renewBuff.tick(1.0);

    Assertions.assertEquals(renewBuff.getMaxDuration() - 1, renewBuff.getRemainingDuration());
  }

  @Test
  void shouldSetExpiredWhenRemainingDurationIs0() {
    renewBuff.tick(999);

    Assertions.assertTrue(renewBuff.isExpired());
  }

  @Test
  void shouldNotReduceRemainingDurationWhenAlreadyExpired() {
    renewBuff.tick(renewBuff.getMaxDuration());
    renewBuff.tick(1);

    Assertions.assertEquals(0, renewBuff.getRemainingDuration());
  }

  @Test
  void shouldNotTriggerActionWhenRemainingDurationIsNotOnTickInterval() {
    var actionsQueue = new ActionsQueue();
    var dps = Dps.builder().build();
    renewBuff.tick(2);

    renewBuff.addAction(dps, actionsQueue);

    Assertions.assertEquals(0, actionsQueue.size());
  }

  @Test
  void shouldNotTriggerActionWhenBuffIsExpired() {
    var actionsQueue = new ActionsQueue();
    var dps = Dps.builder().build();
    renewBuff.tick(9999);

    renewBuff.addAction(dps, actionsQueue);

    Assertions.assertEquals(0, actionsQueue.size());
  }

  @Test
  void shouldTriggerActionWhenRemainingDurationIsOnIntervalTickAndNotExpired() {
    var actionsQueue = new ActionsQueue();
    var dps = Dps.builder().build();

    renewBuff.tick(1.5);

    renewBuff.addAction(dps, actionsQueue);

    Assertions.assertEquals(1, actionsQueue.size());
  }
}
