package com.healing.spell.spellbook;

import com.healing.buff.RenewBuff;
import com.healing.spell.spellbook.spellType.BuffSpell;

public class Renew extends BuffSpell {
  public Renew() {
    super("1", "Renew", 100, 0, new RenewBuff());
  }
}
