package com.healing.spell.spellcast;

import com.healing.entity.Player;
import com.healing.gamelogic.ActionsQueue;
import com.healing.gamelogic.RaiderHandler;
import com.healing.gamelogic.actions.PlayerAction;
import com.healing.spell.spellbook.SpellBook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SpellCastService {

    private final ActionsQueue actionsQueue;
    private final SpellBook spellBook;
    private final RaiderHandler raiderHandler;

    @Autowired
    public SpellCastService(ActionsQueue actionsQueue, SpellBook spellBook, RaiderHandler raiderHandler) {
        this.actionsQueue = actionsQueue;
        this.spellBook = spellBook;
        this.raiderHandler = raiderHandler;
    }

    public void castSpell(String spellId, String targetId) {
        var player = raiderHandler.getPlayer();
        var spell = spellBook.getSpell(spellId);
        var target = raiderHandler.getRaiderById(Integer.parseInt(targetId));

        if (player.isPresent() && spell.isPresent() && target.isPresent()) {
            actionsQueue.addActionToQueue(new PlayerAction((Player) player.get(), target.get(), spell.get(), "1"));
        }


        // Retrieve spell to be cast
        // If spell was found, trigger event to Game and return OK cast to controller

        // If something wrong, return false to controller

    }

}
