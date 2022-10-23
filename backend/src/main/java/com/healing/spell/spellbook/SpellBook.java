package com.healing.spell.spellbook;

import java.util.ArrayList;
import java.util.Optional;
import org.springframework.stereotype.Component;

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
