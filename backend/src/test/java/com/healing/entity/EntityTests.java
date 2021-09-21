package com.healing.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EntityTests {
    private Dps dps;
    @BeforeEach
    void setUp() {
        this.dps = new Dps(0, 200, true);
    }

    @Test
    @DisplayName("Given Entity health increased When health increased more than max Then health is not greater than max")
    void shouldNotIncreaseHealthGreaterThanMax() {
        dps.increaseHealth(100);
        assertEquals(200, dps.getHealth());
    }

    @Test
    @DisplayName("Given Entity health reduced When Entity health is reduced more than remaining Then alive is false")
    void shouldSetAliveToFalseWhenHealthReducedLessThan0() {
        dps.reduceHealth(250);
        assertFalse(dps.isAlive());
    }

    @Test
    @DisplayName("Given Entity health reduced When Entity health is reduced more than remaining Then health is 0")
    void shouldSetHealthTo0WhenReducedMoreThan0() {
        dps.reduceHealth(250);
        assertEquals(0, dps.getHealth());
    }

    @Test
    @DisplayName("Given Entity health reduced When Entity health is exactly 0 Then alive is false")
    void shouldSetAliveToFalseWhenHealthReducedToExactly0() {
        dps.reduceHealth(200);
        assertFalse(dps.isAlive());
    }

    @Test
    @DisplayName("Given Entity health reduced When Entity health is above 0 Then alive still true")
    void shouldNotChangeAliveWhenHealthRemainAbove0() {
        dps.reduceHealth(150);
        assertTrue(dps.isAlive());
    }
}
