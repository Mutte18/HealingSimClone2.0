package com.healing.spell;

import com.healing.entity.Player;
import com.healing.spell.events.CancelSpellCastEvent;
import com.healing.spell.events.StartSpellCastEvent;
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
    private Spell spellBeingCast = null;
    private boolean isCasting = false;
    private Thread castingThread = null;
    private boolean spellHasBeenCancelled = false;

    @Autowired
    public SpellCastService(SpellBook spellBook) {
    }

    public SpellCastService(SpellBook spellBook, Spell spellBeingCast, boolean isCasting) {
        this.spellBeingCast = spellBeingCast;
        this.isCasting = isCasting;
    }

    public Spell startCastSpell(Spell spellToCast, Player player) {
        if (isCasting && spellBeingCast != null) {
            throw new AlreadyCastingException();
        }
        else if (player.getMana() < spellToCast.getManaCost()) {
            throw new InsufficientManaException();
        }
        spellBeingCast = spellToCast;
        isCasting = true;

        castingThread = startCastTimer(spellToCast, player);
        castingThread.start();
        return spellToCast;
    }

    @EventListener
    @Async
    public void handleCancelSpellCast(CancelSpellCastEvent event) throws InterruptedException {
        System.out.println("A CancelSpellCastEvent was received" + event);
        this.spellHasBeenCancelled = true;
    }

    @EventListener
    @Async
    public void handleStartSpellCast(StartSpellCastEvent event) throws InterruptedException {
        this.spellHasBeenCancelled = false;
        Thread.sleep(3000);
        if (!spellHasBeenCancelled) {
            System.out.println("Casting spell wohoo!");
        }
        else {
            System.out.println("Did not cast spell, as it was cancelled");
        }

    }

    private Thread startCastTimer(Spell spellToCast, Player player) {
        return new Thread(() -> {
            try {
                Thread.sleep(spellToCast.getCastTime().intValue() * 1000);
                finishSpellCast(player);
            } catch (InterruptedException e) {
            }
        });
    }

    void interupptSpell() {
        if (isCasting && spellBeingCast != null) {
            castingThread.interrupt();
            isCasting = false;
            spellBeingCast = null;
        }
    }

    void finishSpellCast(Player player) {
        if (isCasting && spellBeingCast != null) {
            System.out.println("Finished casting");
            player.reduceMana(spellBeingCast.getManaCost());
            isCasting = false;
            spellBeingCast = null;
        }

    }

    Spell getSpellBeingCast() {
        return this.spellBeingCast;
    }
    boolean isCasting() {
        return this.isCasting;
    }
}
