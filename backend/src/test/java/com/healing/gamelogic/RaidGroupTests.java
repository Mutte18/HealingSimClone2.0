package com.healing.gamelogic;

import com.healing.entity.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RaidGroupTests {

    private RaidGroup raidGroup;

    @BeforeEach
    void setUp() {
        raidGroup = new RaidGroup();
    }

    @Test
    void shouldExistOnePlayerInRaidGroup() {
        assertEquals(1, countOccurencesOfEntity(raidGroup, Player.class));
    }

    @Test
    void shouldExistFourHealersInRaidGroup() {
        assertEquals(4, countOccurencesOfEntity(raidGroup, Healer.class));
    }

    @Test
    void shouldExistTwoTanksInRaidGroup() {
        assertEquals(2, countOccurencesOfEntity(raidGroup, Tank.class));
    }

    @Test
    void shouldExistThirteenDpsInRaidGroup() {
        assertEquals(13, countOccurencesOfEntity(raidGroup, Dps.class));
    }

    @Test
    void shouldExist20RaidersInRaidGroup() {
        assertEquals(20, raidGroup.size());
    }

    private int countOccurencesOfEntity(RaidGroup raidGroup, Object entity) {
        return (int) raidGroup.stream()
                .filter(raider -> raider.getClass().equals(entity))
                .count();
    }
}
