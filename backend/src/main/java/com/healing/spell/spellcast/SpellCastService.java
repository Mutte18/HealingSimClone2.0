package com.healing.spell.spellcast;

import com.healing.buff.Renew;
import com.healing.gamelogic.ActionsQueue;
import com.healing.gamelogic.RaiderHandler;
import com.healing.gamelogic.actions.PlayerAction;
import com.healing.spell.exceptions.InvalidSpellNameException;
import com.healing.spell.exceptions.NoTargetException;
import com.healing.spell.spellbook.SpellBook;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SpellCastService {

  private final ActionsQueue actionsQueue;
  private final SpellBook spellBook;
  private final RaiderHandler raiderHandler;

  @Autowired
  public SpellCastService(
      ActionsQueue actionsQueue, SpellBook spellBook, RaiderHandler raiderHandler) {
    this.actionsQueue = actionsQueue;
    this.spellBook = spellBook;
    this.raiderHandler = raiderHandler;
  }

  public void castSpell(String spellId, String targetId) throws NoTargetException {
    var player = raiderHandler.getPlayer();
    var spell = spellBook.getSpell(spellId);
    var target = raiderHandler.getRaiderById(targetId);

    if (target.isEmpty()) {
      throw new NoTargetException();
    }

    if (spell.isEmpty()) {
      throw new InvalidSpellNameException();
    }

    if (player != null) {
      switch (spell.get().getSpellType()) {
        case BUFF -> target.get().getBuffs().add(new Renew());
        case HEAL -> actionsQueue.addActionToQueue(
            new PlayerAction(player, new ArrayList<>(List.of(target.get())), spell.get(), "1"));
      }
    }
    // Retrieve spell to be cast
    // If spell was found, trigger event to Game and return OK cast to controller

    // If something wrong, return false to controller

  }
}
