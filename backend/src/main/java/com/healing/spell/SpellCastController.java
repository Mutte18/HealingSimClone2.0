package com.healing.spell;

import com.healing.entity.Player;
import com.healing.gamelogic.RaiderHandler;
import com.healing.spell.events.publishers.SpellCastPublisher;
import com.healing.spell.spellbook.Spell;
import com.healing.spell.spellbook.SpellBook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("spellcasting")
public class SpellCastController {
    private SpellCastService spellCastService;
    private SpellBook spellBook;
    private RaiderHandler raiderHandler;
    private SpellCastPublisher spellCastPublisher;

    @Autowired
    public SpellCastController(SpellCastPublisher spellCastPublisher) {
        this.spellCastPublisher = spellCastPublisher;
    }


    @PostMapping(value = "/{spellName}", produces = "application/json")
    public ResponseEntity.HeadersBuilder<?> castSpell(@PathVariable String spellName) {
        Optional<Spell> spell = spellBook.getSpell(spellName);
        if (spell.isEmpty()) {
            return ResponseEntity.notFound();
        }


        return ResponseEntity.ok();
    }

    @GetMapping(value = "/startcast", produces = "application/json")
    public ResponseEntity.HeadersBuilder<?> startSpellCast() {
        spellCastPublisher.publishStartSpellCastEvent();
        return ResponseEntity.ok();
    }

    @GetMapping(value = "/cancelcast", produces = "application/json")
    public ResponseEntity.HeadersBuilder<?> cancelSpellCast() {
        spellCastPublisher.publishCancelSpellCastEvent();
        return ResponseEntity.ok();
    }


        private void validateSpell() {

    }


}
