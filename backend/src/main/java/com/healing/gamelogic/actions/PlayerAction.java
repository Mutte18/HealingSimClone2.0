package com.healing.gamelogic.actions;

import com.healing.entity.Entity;
import com.healing.entity.Player;
import com.healing.spell.spellbook.Spell;
import lombok.Getter;

@Getter
public class PlayerAction extends Action {
    private final Player player;
    private final Spell spell;
    private final Entity target;
    private final String id;

    public PlayerAction(Player player, Entity target, Spell spell, String id) {
        super(target, id);
        this.player = player;
        this.spell = spell;
        this.target = target;
        this.id = id;
    }

}
