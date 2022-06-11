package com.healing.spell.spellbook;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Optional;

@Component
public class SpellBook extends ArrayList<Spell> {
    public SpellBook() {
        this.addSpells();
    }

    public Optional<Spell> getSpell(String spellId) {
        return this.stream().filter(spell -> spell.getSpellId().equals(spellId)).findAny();
    }

    private void addSpells() {
        this.add(new FlashHeal());
    }

}
