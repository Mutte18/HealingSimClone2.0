package com.healing.spell.events;

import org.springframework.context.ApplicationEvent;

public class StartSpellCastEvent extends ApplicationEvent {
    public StartSpellCastEvent(Object source) {
        super(source);
    }
}
