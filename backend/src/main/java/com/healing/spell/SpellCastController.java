package com.healing.spell;

import com.healing.gamelogic.RaiderHandler;
import com.healing.spell.spellbook.Spell;
import com.healing.spell.spellbook.SpellBook;
import com.healing.spell.spellcast.SpellCastService;
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

    @Autowired
    public SpellCastController() {

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
        return ResponseEntity.ok();
    }

    @GetMapping(value = "/cancelcast", produces = "application/json")
    public ResponseEntity.HeadersBuilder<?> cancelSpellCast() {
        return ResponseEntity.ok();
    }


        private void validateSpell() {

    }


}
