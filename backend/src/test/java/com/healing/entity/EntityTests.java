package com.healing.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EntityTests {
    @Test
    void increaseHealth_increaseMoreThanMaxHP_SetHealthToMax() {
        var dps = new Dps(0, 200, true);
        dps.setHealth(150);
        dps.increaseHealth(100);

        assertEquals(200, dps.getHealth());
    }

    @Test
    void reduceHealth_HealthDropsBelow0_SetAliveToFalse() {
        var dps = new Dps(0, 200, true);
        dps.reduceHealth(250);

        assertEquals(0, dps.getHealth());
        assertFalse(dps.isAlive());
    }

    @Test
    void reduceHealth_HealthDropsToExactly0_SetAliveToFalse() {
        var dps = new Dps(0, 200, true);
        dps.reduceHealth(200);

        assertEquals(0, dps.getHealth());
        assertFalse(dps.isAlive());
    }

    @Test
    void reduceHealth_HealthRemainsOver0_SetAliveToFalse() {
        var dps = new Dps(0, 200, true);
        dps.reduceHealth(150);

        assertEquals(50, dps.getHealth());
        assertTrue(dps.isAlive());
    }
}
