package com.healing.gamelogic;

import com.healing.entity.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class RaiderHandler {
    private RaidGroup raidGroup;

    public RaiderHandler() {
        this.raidGroup = new RaidGroup();
    }

    @Bean
    @Scope("singleton")
    public RaiderHandler raiderHandlerSingleton() {
        return new RaiderHandler();
    }

    public void resetRaidGroup() {
        this.raidGroup = new RaidGroup();
    }


    public Optional<Entity> getRaiderById(int id) {
        return raidGroup.stream().filter(raider -> raider.getId() == id).findAny();
    }
}
