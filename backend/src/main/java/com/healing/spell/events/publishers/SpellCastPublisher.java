package com.healing.spell.events.publishers;

import com.healing.spell.events.CancelSpellCastEvent;
import com.healing.spell.events.StartSpellCastEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class SpellCastPublisher {
    private ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    public SpellCastPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void publishStartSpellCastEvent() {
        System.out.println("Publishing StartSpellCastEvent");
        applicationEventPublisher.publishEvent(new StartSpellCastEvent(this));
    }

    public void publishCancelSpellCastEvent() {
        System.out.println("Publishing CancelSpellCastEvent");
        applicationEventPublisher.publishEvent(new CancelSpellCastEvent(this));
    }
}
