package com.healing.gamelogic;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
class RaiderHandlerTests {
    @Test
    void createRaiders_returnsListWith20Raiders() {
        var raiderList = new RaiderHandler().createRaid();
        assertEquals(20, raiderList.size());
    }
}
