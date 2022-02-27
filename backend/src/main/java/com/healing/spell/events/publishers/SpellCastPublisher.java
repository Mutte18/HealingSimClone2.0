package com.healing.spell.events.publishers;

import com.healing.spell.events.CancelSpellCastEvent;
import com.healing.spell.events.FinishSpellCastEvent;
import com.healing.spell.events.StartSpellCastEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class SpellCastPublisher implements ApplicationEventPublisher{

    @Autowired
    public SpellCastPublisher() {
    }

    public void publishStartSpellCastEvent() {
        System.out.println("Publishing StartSpellCastEvent");
        publishEvent(new StartSpellCastEvent(this));
    }

    public void publishCancelSpellCastEvent() {
        System.out.println("Publishing CancelSpellCastEvent");
        publishEvent(new CancelSpellCastEvent(this));
    }

    public void publishFinishSpellCastEvent() {
        System.out.println("Publishing FinishSpellCastEvent");
        publishEvent(new FinishSpellCastEvent(this));
    }

    @Override
    public void publishEvent(Object o) {

    }
}
