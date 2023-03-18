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
    this.add(new Renew());
    this.add(new ChainHeal());
    this.add(new Riptide());
    this.add(new HolyShock());
    this.add(new HolyNova());
    this.add(new MassRenew());
  }

  /*public Optional<Spell> getSpell(String spellId) {
    Spell spell =
        switch (spellId) {
          case "0" -> new FlashHeal();
          case "1" -> new Renew();
          case "2" -> new ChainHeal();
          case "3" -> new Riptide();
          default -> null;
        };
    return Optional.ofNullable(spell);
  }*/
}
