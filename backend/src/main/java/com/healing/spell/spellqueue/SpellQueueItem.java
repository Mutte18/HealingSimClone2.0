package com.healing.spell.spellqueue;

import com.healing.entity.Entity;
import com.healing.spell.spellbook.Spell;
import java.util.ArrayList;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class SpellQueueItem {
  private String id;
  private Spell spell;
  private Entity caster;
  private Entity target;
  private ArrayList<Entity> targets;
}
