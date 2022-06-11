package com.healing.gamelogic;

import com.healing.entity.Dps;
import com.healing.entity.Player;
import com.healing.gamelogic.actions.PlayerAction;
import com.healing.spell.spellbook.FlashHeal;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameTest {
    RaiderHandler raiderHandler = new RaiderHandler();
    Game game = new Game(new ActionsQueue(), raiderHandler);

    @Test
    void shouldReducePlayerManaWhenPlayerActionIsProcessed() {
        var player = new Player(0, 0, true, 100);
        game.performPlayerAction(new PlayerAction(player, new Dps(1, 1, true), new FlashHeal(), "1"));

        assertEquals(0, player.getMana());
    }

    @Test
    void shouldIncreaseTargetHealthWhenPlayerActionIsProcessed() {
        var player = raiderHandler.getPlayer().get();
        var dps = raiderHandler.getRaiderById(1).get();
        dps.setHealth(25);
        game.performPlayerAction(new PlayerAction(player, dps, new FlashHeal(), "1"));

        assertEquals(25, dps.getHealth());
    }


}
