package com.healing.gamelogic;

import com.healing.buff.RenewBuff;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BuffHandlerTest {
  private BuffHandler buffHandler;
  private RaiderHandler raiderHandler;
  private ActionsQueue actionsQueue;

  @BeforeEach
  void setup() {
    raiderHandler = new RaiderHandler();
    raiderHandler.resetRaidGroup();
    actionsQueue = new ActionsQueue();
    buffHandler = new BuffHandler(raiderHandler, actionsQueue);
  }

  @Test
  void shouldAddAnActionWhenBuffIsProcessed() {
    addBuffToRaider();
    buffHandler.processBuffs(1);

    Assertions.assertEquals(1, actionsQueue.size());
  }

  @Test
  void shouldReduceBuffRemainingDurationWhenProcessed() {
    addBuffToRaider();
    buffHandler.processBuffs(1);
    var durationRemainingAfter =
        raiderHandler.getRaidGroup().get(0).getBuffs().get(0).getRemainingDuration();

    Assertions.assertEquals(8, durationRemainingAfter);
  }

  @Test
  void shouldCleanUpExpiredBuffs() {
    addBuffToRaider();
    buffHandler.processBuffs(9999);
    buffHandler.cleanUpExpiredBuffs();

    var raiderBuffs = raiderHandler.getRaidGroup().get(0).getBuffs();

    Assertions.assertEquals(0, raiderBuffs.size());
  }

  @Test
  void shouldNotCleanUpNotYetExpiredBuffs() {
    addBuffToRaider();
    buffHandler.processBuffs(1);
    buffHandler.cleanUpExpiredBuffs();

    var raiderBuffs = raiderHandler.getRaidGroup().get(0).getBuffs();

    Assertions.assertEquals(1, raiderBuffs.size());
  }

  private void addBuffToRaider() {
    raiderHandler.getRaidGroup().get(0).setBuffs(List.of(new RenewBuff()));
  }
}
