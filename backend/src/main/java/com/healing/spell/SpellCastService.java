package com.healing.spell;

import com.healing.entity.Player;
import com.healing.spell.exceptions.AlreadyCastingException;
import com.healing.spell.exceptions.InsufficientManaException;
import com.healing.spell.spellbook.Spell;
import com.healing.spell.spellbook.SpellBook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SpellCastService {
    private Spell spellBeingCast = null;
    private boolean isCasting = false;
    private Thread castingThread = null;

    @Autowired
    public SpellCastService(SpellBook spellBook) {
    }

    public SpellCastService(SpellBook spellBook, Spell spellBeingCast, boolean isCasting) {
        this.spellBeingCast = spellBeingCast;
        this.isCasting = isCasting;
    }

    void startCastSpell(Spell spellToCast, Player player) {
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
