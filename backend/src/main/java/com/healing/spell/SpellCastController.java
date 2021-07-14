package com.healing.spell;

import com.healing.entity.Player;
import com.healing.gamelogic.RaiderHandler;
import com.healing.spell.exceptions.InvalidSpellNameException;
import com.healing.spell.spellbook.Spell;
import com.healing.spell.spellbook.SpellBook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("spellcasting")
public class SpellCastController {
    private SpellCastService spellCastService;
    private SpellBook spellBook;
    private RaiderHandler raiderHandler;

    @PostMapping(value = "/{spellName}", produces = "application/json")
    public ResponseEntity.HeadersBuilder<?> castSpell(@PathVariable String spellName) {
        Spell spell = spellBook.getSpell(spellName);
        if (spell == null) {
            return ResponseEntity.notFound();
        }

        Player player = raiderHandler.getPlayer();
        if (player == null) {
            return ResponseEntity.notFound();
        }


        spellCastService.startCastSpell(spell, player);

        return ResponseEntity.ok();
    }

    private void validateSpell() {

    }


}
