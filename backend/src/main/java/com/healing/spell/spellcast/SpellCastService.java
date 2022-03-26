package com.healing.spell.spellcast;

import com.healing.entity.Player;
import com.healing.spell.events.CancelSpellCastEvent;
import com.healing.spell.events.StartSpellCastEvent;
import com.healing.spell.events.publishers.SpellCastPublisher;
import com.healing.spell.exceptions.AlreadyCastingException;
import com.healing.spell.exceptions.InsufficientManaException;
import com.healing.spell.spellbook.Spell;
import com.healing.spell.spellbook.SpellBook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class SpellCastService {
    private boolean isCasting = false;

    @Autowired
    public SpellCastService() {

    }

}
