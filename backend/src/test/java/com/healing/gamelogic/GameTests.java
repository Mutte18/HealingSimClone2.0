package com.healing.gamelogic;

import org.junit.jupiter.api.Test;

public class GameTests {
    @Test
    public void printRaidersListTableFormatTest() {
        Game game = new Game();
        game.printRaidersListDownwards();
        game.setEntitiesHealthTo50Percent();
        game.setEntitiesHealthTo0();
        game.printRaidersListDownwards();
    }
}
