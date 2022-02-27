package com.healing.spell.events;

import org.springframework.context.ApplicationEvent;

public class FinishSpellCastEvent extends ApplicationEvent {
    public FinishSpellCastEvent(Object source) {
        super(source);
    }
}
