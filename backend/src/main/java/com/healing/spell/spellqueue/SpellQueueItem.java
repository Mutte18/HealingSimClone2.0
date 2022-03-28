package com.healing.spell.spellqueue;

import com.healing.entity.Entity;
import com.healing.spell.spellbook.Spell;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;

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
