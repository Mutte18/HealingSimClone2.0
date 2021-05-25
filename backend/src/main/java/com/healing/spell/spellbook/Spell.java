package com.healing.spell.spellbook;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public abstract class Spell {
    private String name;
    private Integer manaCost;
    private Double castTime;
    private Integer healAmount;
    private Integer damageAmount;
    private Integer cooldownTime;
}
