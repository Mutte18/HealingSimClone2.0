package com.healing.buff;

import com.healing.entity.Dps;
import com.healing.gamelogic.ActionsQueue;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BuffTest {

  private Renew renew;

  @BeforeEach
  void setup() {
    renew = new Renew();
  }

  @Test
  void shouldOnlyIncreaseTimeElapsedWhenBuffNotExpired() {
    renew.tick(1);

    Assertions.assertEquals(1, renew.getTimeElapsed());
  }

  @Test
  void shouldSetExpiredWhenTimeElapsedExceedsDuration() {
    renew.tick(999);

    Assertions.assertTrue(renew.isExpired());
  }

  @Test
  void shouldNotIncreaseTimeElapsedWhenAlreadyExpired() {
    renew.tick(renew.getDuration());
    renew.tick(1);

    Assertions.assertEquals(renew.getDuration(), renew.getTimeElapsed());
  }

  @Test
  void shouldNotTriggerActionWhenElapsedIsNotOnTickInterval() {
    var actionsQueue = new ActionsQueue();
    var dps = Dps.builder().build();
    renew.tick(1);

    renew.addAction(dps, actionsQueue);

    Assertions.assertEquals(0, actionsQueue.size());
  }

  @Test
  void shouldNotTriggerActionWhenBuffIsExpired() {
    var actionsQueue = new ActionsQueue();
    var dps = Dps.builder().build();
    renew.tick(9999);

    renew.addAction(dps, actionsQueue);

    Assertions.assertEquals(0, actionsQueue.size());
  }

  @Test
  void shouldTriggerActionWhenElapsedIsOnIntervalTickAndNotExpired() {
    var actionsQueue = new ActionsQueue();
    var dps = Dps.builder().build();

    renew.tick(15);

    renew.addAction(dps, actionsQueue);

    Assertions.assertEquals(1, actionsQueue.size());
  }
}
