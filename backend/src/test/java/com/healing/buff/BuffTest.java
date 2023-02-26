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
  void shouldOnlyIncreaseTimeElapsedWhenBuffNotExpired() {
    renewBuff.tick(1);

    Assertions.assertEquals(1, renewBuff.getTimeElapsed());
  }

  @Test
  void shouldSetExpiredWhenTimeElapsedExceedsDuration() {
    renewBuff.tick(999);

    Assertions.assertTrue(renewBuff.isExpired());
  }

  @Test
  void shouldNotIncreaseTimeElapsedWhenAlreadyExpired() {
    renewBuff.tick(renewBuff.getDuration());
    renewBuff.tick(1);

    Assertions.assertEquals(renewBuff.getDuration(), renewBuff.getTimeElapsed());
  }

  @Test
  void shouldNotTriggerActionWhenElapsedIsNotOnTickInterval() {
    var actionsQueue = new ActionsQueue();
    var dps = Dps.builder().build();
    renewBuff.tick(1);

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
  void shouldTriggerActionWhenElapsedIsOnIntervalTickAndNotExpired() {
    var actionsQueue = new ActionsQueue();
    var dps = Dps.builder().build();

    renewBuff.tick(15);

    renewBuff.addAction(dps, actionsQueue);

    Assertions.assertEquals(1, actionsQueue.size());
  }
}
