package com.healing.gamelogic;

import com.healing.entity.Entity;
import com.healing.spell.spellbook.Spell;
import lombok.*;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Setter
@Getter
@NoArgsConstructor
public class Action {
    private Entity source;
    private Entity target;
    private Spell spell;
    private int id;
}
