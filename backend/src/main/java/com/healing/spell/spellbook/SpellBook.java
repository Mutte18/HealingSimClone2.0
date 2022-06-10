package com.healing.spell.spellbook;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Optional;

@Component
public class SpellBook extends ArrayList<Spell> {
    public SpellBook() {
        this.addSpells();
    }

    public Optional<Spell> getSpell(String spellName) {
        return this.stream().filter(spell -> spell.getClass().getSimpleName().equals(spellName)).findAny();
    }

    private void addSpells() {
        this.add(FlashHeal.builder().name("Flash Heal").castTime(1.5).cooldownTime(0).damageAmount(0).manaCost(100)
                .healAmount(150).build());
    }

}
