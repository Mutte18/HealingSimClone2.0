package com.healing.spell.events;

import org.springframework.context.ApplicationEvent;

public class CancelSpellCastEvent extends ApplicationEvent {
    public CancelSpellCastEvent(Object source) {
        super(source);
    }
}
