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
    private Thread castingThread = null;
    private SpellCastPublisher spellCastPublisher;

    @Autowired
    public SpellCastService(SpellCastPublisher spellCastPublisher) {
        this.spellCastPublisher = spellCastPublisher;
    }

    public boolean castSpell(int castTime) {
        if (isCasting) {
            throw new AlreadyCastingException();
        }
        if (isInstantSpell(castTime)) {
            finishSpellCast();
        }
        else {
            isCasting = true;
            this.castingThread = startCastTimer(castTime);
            this.castingThread.start();
            return isCasting;
        }
    }

    void finishSpellCast() {
        isCasting = false;
        emitFinishSpellCast();
    }

    public void cancelSpellCast() {
        if (isCasting) {
            System.out.println(this.castingThread);
            this.castingThread.interrupt();
            this.isCasting = false;
        }
    }

    private boolean isInstantSpell(int castTime) {
        return castTime == 0;
    }

    private Thread startCastTimer(int castTime) {
        return new Thread(() -> {
            try {
                Thread.sleep(castTime);
                finishSpellCast();
            } catch (InterruptedException ignored) {
                System.out.println("CastingThread was interuppted");
            }
        });
    }

    private void emitFinishSpellCast() {
        this.spellCastPublisher.publishFinishSpellCastEvent();
    }
}
