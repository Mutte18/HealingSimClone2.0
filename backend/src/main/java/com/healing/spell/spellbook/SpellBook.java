package com.healing.spell.spellbook;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class SpellBook extends ArrayList<Spell> {
    public SpellBook() {
        this.addSpells();
    }

    private void addSpells() {
        this.add(
                FlashHeal.builder()
                        .name("Flash Heal")
                        .castTime(1.5)
                        .cooldownTime(0)
                        .damageAmount(0)
                        .manaCost(100)
                        .healAmount(150)
                        .build());
    }
}
