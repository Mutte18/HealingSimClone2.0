package com.healing.entity.attacks.specials;

import com.healing.buff.debuffs.BurningDebuff;
import com.healing.entity.attacks.NPCAttack;

public class MassPyroblast extends NPCAttack {
  public MassPyroblast() {
    super("Mass Pyroblast", 50, 18, new BurningDebuff());
  }
}
