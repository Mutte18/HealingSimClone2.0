package com.healing.spell.spellcast;

import com.healing.gamelogic.Action;
import com.healing.gamelogic.ActionsQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SpellCastService {

    private final ActionsQueue actionsQueue;

    @Autowired
    public SpellCastService(ActionsQueue actionsQueue) {
        this.actionsQueue = actionsQueue;
    }

    public void castSpell() {
        actionsQueue.addActionToQueue(new Action().setSource(null).setTarget(null).setSpell(null).setId(1));
        // Retrieve spell to be cast
        // If spell was found, trigger event to Game and return OK cast to controller

        // If something wrong, return false to controller

    }

}
