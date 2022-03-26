package com.healing.spell;

import com.healing.gamelogic.RaiderHandler;
import com.healing.spell.spellbook.Spell;
import com.healing.spell.spellbook.SpellBook;
import com.healing.spell.spellcast.SpellCastService;
import com.healing.spell.spellqueue.SpellQueue;
import com.healing.spell.spellqueue.SpellQueueItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("spellcasting")
public class SpellCastController {
    private SpellCastService spellCastService;
    private SpellBook spellBook;
    private RaiderHandler raiderHandler;
    private SpellQueue spellQueue;

    @Autowired
    public SpellCastController() {

    }


    @PostMapping(value = "/{spellId}", produces = "application/json")
    public ResponseEntity.HeadersBuilder<?> castSpell(
            @PathVariable String spellId, String casterId, String targetId) {
        Optional<Spell> spell = spellBook.getSpell(spellId);
        if (spell.isEmpty()) {
            return ResponseEntity.notFound();
        }

        spellQueue.addSpellToQueue(SpellQueueItem.builder()
                .id(UUID.randomUUID().toString())
                .spell(spell.get())
                .caster(raiderHandler.));



        return ResponseEntity.ok();
    }


}
