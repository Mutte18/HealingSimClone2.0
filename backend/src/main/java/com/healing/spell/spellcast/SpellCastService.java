package com.healing.spell.spellcast;

import com.healing.gamelogic.ActionsQueue;
import com.healing.gamelogic.RaiderHandler;
import com.healing.gamelogic.actions.PlayerAction;
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
    System.out.println(actionsQueue + " SpellCastService");
  }

  public PlayerAction castSpell(String spellId, String targetId) {
    var player = raiderHandler.getPlayer();
    var spell = spellBook.getSpell(spellId);
    var target = raiderHandler.getRaiderById(Integer.parseInt(targetId));

    if (player != null && spell.isPresent() && target.isPresent()) {
      return (PlayerAction)
          actionsQueue.addActionToQueue(
              new PlayerAction(player, new ArrayList<>(List.of(target.get())), spell.get(), "1"));
    }
    return null;

    // Retrieve spell to be cast
    // If spell was found, trigger event to Game and return OK cast to controller

    // If something wrong, return false to controller

  }
}
